package com.example.googleplay.manager;

import com.example.googleplay.bean.AppInfoBean;
import com.example.googleplay.bean.DownloadInfoBean;
import com.example.googleplay.conf.Constants.URLS;
import com.example.googleplay.factory.OkHttpUtil;
import com.example.googleplay.factory.ThreadPoolFactory;
import com.example.googleplay.utils.FileUtils;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.utils.apkUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载管理器，记录当前状态，暴露当前状态
 *
 * @author haopi
 */
public class DownloadManager {

    public static final int STATE_UN_DOWNLOAD = 0;// 未下载
    public static final int STATE_DOWNLOADING = 1;// 下载中
    public static final int STATE_PAUSED_DOWNLOAD = 2;// 暂停下载
    public static final int STATE_WAITING_DOWNLOAD = 3;// 等待下载
    public static final int STATE_DOWNLOAD_FAILED = 4;// 下载失败
    public static final int STATE_DOWNLOADED = 5;// 下载完成
    public static final int STATE_INSTALLED = 6;// 已安装

    private static DownloadManager instance;

    private Map<String, DownloadInfoBean> downloadInfoBeans = new HashMap<>();

    private DownloadManager() {

    }

    /**
     * 获取单例
     */
    public static DownloadManager getInstance() {
        if (instance == null) {
            instance = new DownloadManager();
        }
        return instance;
    }

    /**
     * 点击下载
     */
    public void doDownload(DownloadInfoBean downloadInfoBean) {
        // 保存数据
        downloadInfoBeans.put(downloadInfoBean.packageName, downloadInfoBean);
        // 下载状态：等待下载
        downloadInfoBean.state = STATE_WAITING_DOWNLOAD;
        // 通知观察者
        notifyObservers(downloadInfoBean);
        // 新建任务
        DownloadTask downloadTask = new DownloadTask(downloadInfoBean);
        // 保存任务
        downloadInfoBean.task = downloadTask;
        // 开启线程池下载
        ThreadPoolFactory.getDownLoadPool().execute(downloadTask);
    }

    private class DownloadTask implements Runnable {
        private void stop() {
            UIUtils.postTaskRemove(this);
        }

        DownloadInfoBean downloadInfoBean;

        DownloadTask(DownloadInfoBean downloadInfoBean) {
            super();
            this.downloadInfoBean = downloadInfoBean;
        }

        @Override
        public void run() {
            try {
                // 从绝对路径获取APP文件已经下载的大小
                long range = getRangeFromAbsolutePath(downloadInfoBean);
                downloadInfoBean.curProgress = range;
                // 下载状态：下载中
                downloadInfoBean.state = STATE_DOWNLOADING;
                // 通知观察者
                notifyObservers(downloadInfoBean);
                String url = URLS.DOWNLOAD_URL;
                //HttpUtils httpUtils = new HttpUtils();
                /*RequestParams params = new RequestParams();
                params.addQueryStringParameter("name", downloadInfoBean.downloadUrl);
                params.addQueryStringParameter("range", range + "");
                ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, params);*/

                // okHttp
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("name", downloadInfoBean.downloadUrl);
                builder.add("range", String.valueOf(range));
                Request request = new Request.Builder()
                        .url(url)
                        .post(builder.build())
                        .build();
                Response response = OkHttpUtil.getOkHttpClient().newCall(request).execute();
                if (response.code() == 200) {
                    // 获取输入流
                    InputStream is = response.body().byteStream();
                    // 追加写入文件
                    File saveFile = new File(downloadInfoBean.savePathAbsolute);
                    //RandomAccessFile accessFile = new RandomAccessFile(saveFile, "rw");
                    FileOutputStream fos = new FileOutputStream(saveFile, true);
                    //accessFile.seek(downloadInfoBean.curProgress);
                    byte[] buffer = new byte[1024 * 4];
                    int length;
                    while (-1 != (length = is.read(buffer))) {
                        if (downloadInfoBean.state == STATE_PAUSED_DOWNLOAD) {
                            break;
                        }
                        fos.write(buffer, 0, length);
                        // 进度条值
                        downloadInfoBean.curProgress += length;
                        // 下载状态：下载中
                        downloadInfoBean.state = STATE_DOWNLOADING;
                        // 通知观察者
                        notifyObservers(downloadInfoBean);
                        if (downloadInfoBean.curProgress == downloadInfoBean.max) {
                            // 下载状态：下载完成
                            downloadInfoBean.state = STATE_DOWNLOADED;
                            // 通知观察者
                            notifyObservers(downloadInfoBean);
                            break;
                        }

                    }
                    fos.close();

                } else {
                    // 下载状态：下载失败
                    downloadInfoBean.state = STATE_DOWNLOAD_FAILED;
                    // 通知观察者
                    notifyObservers(downloadInfoBean);
                }

            } catch (Exception e) {
                e.printStackTrace();

                // 下载状态：下载失败
                downloadInfoBean.state = STATE_DOWNLOAD_FAILED;
                // 通知观察者
                notifyObservers(downloadInfoBean);

            }
        }

    }

    /**
     * 在绝对路径中获取下载中的apk文件的大小
     */
    private long getRangeFromAbsolutePath(DownloadInfoBean downloadInfoBean) {
        File file = new File(downloadInfoBean.savePathAbsolute);
        if (file.exists()) {
            return file.length();
        }
        return 0;
    }

    /**
     * 暴露当前状态，返回downloadInfoBean，其中包含下载的APP的状态
     */
    public DownloadInfoBean getDownloadInfo(AppInfoBean data) {
        DownloadInfoBean downloadInfo = getGenerateDownloadInfo(data);
        /*
		 * 未下载 下载中 暂停下载 等待下载 下载失败 下载完成 已安装
		 */
        // 已安装
        if (apkUtils.isInstalled(UIUtils.getContext(), data.packageName)) {
            downloadInfo.state = STATE_INSTALLED;
            return downloadInfo;
        }
        // 下载完成
        // 根据apk下载的路径获取apk下载的文件
        File downloadFile = new File(downloadInfo.savePathAbsolute);
        if (downloadFile.exists() && downloadFile.length() == data.size) {
            downloadInfo.state = STATE_DOWNLOADED;
            return downloadInfo;
        }
        // 下载中 暂停下载 等待下载 下载失败
        DownloadInfoBean downloadInfoBean = downloadInfoBeans.get(data.packageName);
        if (downloadInfoBean != null) {
            return downloadInfoBean;
        }

        // 剩余情况：未下载
        downloadInfo.state = STATE_UN_DOWNLOAD;
        return downloadInfo;

    }

    /**
     * 进行一些常规的赋值，把AppInfoBean转换成DownloadInfoBean，除了state
     */
    private DownloadInfoBean getGenerateDownloadInfo(AppInfoBean data) {
        // apk文件下载的绝对路径
        String dir = FileUtils.getDir("download");
        String name = data.packageName + ".apk";
        File downloadFile = new File(dir, name);
        String downloadFileAbsolutePath = downloadFile.getAbsolutePath();

        DownloadInfoBean downloadInfo = new DownloadInfoBean();

        downloadInfo.savePathAbsolute = downloadFileAbsolutePath;
        downloadInfo.downloadUrl = data.downloadUrl;
        downloadInfo.packageName = data.packageName;
        downloadInfo.max = data.size;
        downloadInfo.curProgress = 0;

        return downloadInfo;
    }

    /**
     * 暂停下载
     */
    public void pauseDownload(DownloadInfoBean downloadInfo) {
        downloadInfo.state = STATE_PAUSED_DOWNLOAD;
        notifyObservers(downloadInfo);
        DownloadTask downloadTask = (DownloadTask) downloadInfo.task;
        downloadTask.stop();
    }

    /**
     * 取消下载，前提是还未开始下载
     */
    public void cancelDownload(DownloadInfoBean downloadInfo) {
        Runnable task = downloadInfo.task;
        ThreadPoolFactory.getDownLoadPool().remove(task);
        downloadInfo.state = STATE_UN_DOWNLOAD;
        notifyObservers(downloadInfo);
    }

    /**
     * 安装应用
     */
    public void installApk(DownloadInfoBean downloadInfo) {
        File apkFile = new File(downloadInfo.savePathAbsolute);
        apkUtils.installApp(UIUtils.getContext(), apkFile);
    }

    /**
     * 打开应用
     */
    public void openApp(DownloadInfoBean downloadInfo) {
        apkUtils.openApp(UIUtils.getContext(), downloadInfo.packageName);
    }

    // ================= 自定义设计模式-begin=================

    /**
     * 观察者接口
     */
    public interface DownloadStateObserver {
        void onDownloadInfoChange(DownloadInfoBean downloadInfo);
    }

    /**
     * 用于保存观察者
     */
    private List<DownloadStateObserver> observers = new LinkedList<>();

    /**
     * 添加观察者
     */
    public void addObserver(DownloadStateObserver observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!observers.contains(observer)) {
                observers.add(observer);
            }
        }
    }

    /**
     * 删除观察者
     */
    public synchronized void deleteObserver(DownloadStateObserver observer) {
        observers.remove(observer);
    }

    /**
     * 通知观察者
     */
    public void notifyObservers(DownloadInfoBean downloadInfo) {
        for (DownloadStateObserver observer : observers) {
            observer.onDownloadInfoChange(downloadInfo);
        }
    }
    // ================= 自定义设计模式-end=================

}
