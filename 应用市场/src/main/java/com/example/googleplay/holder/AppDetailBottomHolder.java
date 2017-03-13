package com.example.googleplay.holder;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.bean.AppInfoBean;
import com.example.googleplay.bean.DownloadInfoBean;
import com.example.googleplay.manager.DownloadManager;
import com.example.googleplay.manager.DownloadManager.DownloadStateObserver;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.views.ProgressButton;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AppDetailBottomHolder extends BaseHolder<AppInfoBean> implements OnClickListener, DownloadStateObserver
{
	@ViewInject(R.id.app_detail_download_btn_favo)
	Button mBtnFavo;
	@ViewInject(R.id.app_detail_download_btn_share)
	Button mBtnShare;
	@ViewInject(R.id.app_detail_download_btn_download)
	ProgressButton mBtnDownload;

	private AppInfoBean mDada;

	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.app_detail_bottom, null);
		ViewUtils.inject(this, view);

		mBtnFavo.setOnClickListener(this);
		mBtnShare.setOnClickListener(this);
		mBtnDownload.setOnClickListener(this);
		return view;
	}

	@Override
	public void refreshHolderView(AppInfoBean data)
	{
		mDada = data;

		// 获取用户下载状态，根据不同的状态给用户不同的提示
		DownloadInfoBean downloadInfo = DownloadManager.getInstance().getDownloadInfo(data);

		// 观察者观察 状态改变
		refreshProgressBtnUI(downloadInfo);
	}

	// 刷新下载按钮视图
	public void refreshProgressBtnUI(DownloadInfoBean downloadInfo)
	{
		
		mBtnDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);
		/*
		 * 未下载 下载中 暂停下载 等待下载 下载失败 下载完成 已安装
		 */
		switch (downloadInfo.state)
		{
		case DownloadManager.STATE_UN_DOWNLOAD:
			mBtnDownload.setText("下载");
			break;
		case DownloadManager.STATE_DOWNLOADING:
			mBtnDownload.setProgressEnable(true);
			mBtnDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);
			mBtnDownload.setMax(downloadInfo.max);
			mBtnDownload.setProgress(downloadInfo.curProgress);

			int progress = (int) (downloadInfo.curProgress * 100.f / downloadInfo.max + .5f);
			mBtnDownload.setText(progress + "%");
			break;
		case DownloadManager.STATE_PAUSED_DOWNLOAD:
			mBtnDownload.setText("继续下载");
			break;
		case DownloadManager.STATE_WAITING_DOWNLOAD:
			mBtnDownload.setText("等待中...");
			break;
		case DownloadManager.STATE_DOWNLOAD_FAILED:
			mBtnDownload.setText("重试");
			break;
		case DownloadManager.STATE_DOWNLOADED:
			mBtnDownload.setProgressEnable(false);
			mBtnDownload.setText("安装");
			break;
		case DownloadManager.STATE_INSTALLED:
			mBtnDownload.setText("打开");
			break;

		default:
			break;
		}

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.app_detail_download_btn_favo:
			Toast.makeText(UIUtils.getContext(), "收藏", Toast.LENGTH_SHORT).show();

			break;
		case R.id.app_detail_download_btn_share:
			Toast.makeText(UIUtils.getContext(), "分享", Toast.LENGTH_SHORT).show();

			break;
		case R.id.app_detail_download_btn_download:
			
			DownloadInfoBean downloadInfo = DownloadManager.getInstance().getDownloadInfo(mDada);

			// 获取用户下载状态，根据不同的状态，点击产生不同的用户行为
			switch (downloadInfo.state)
			{
			case DownloadManager.STATE_UN_DOWNLOAD:
				// 去下载
				DownloadManager.getInstance().doDownload(downloadInfo);
				break;
			case DownloadManager.STATE_DOWNLOADING:
				// 暂停下载
				DownloadManager.getInstance().pauseDownload(downloadInfo);
				break;
			case DownloadManager.STATE_PAUSED_DOWNLOAD:
				// 继续下载
				DownloadManager.getInstance().doDownload(downloadInfo);
				break;
			case DownloadManager.STATE_WAITING_DOWNLOAD:
				// 取消下载
				DownloadManager.getInstance().cancelDownload(downloadInfo);
				break;
			case DownloadManager.STATE_DOWNLOAD_FAILED:
				// 重试下载
				DownloadManager.getInstance().doDownload(downloadInfo);
				break;
			case DownloadManager.STATE_DOWNLOADED:
				// 安装应用
				DownloadManager.getInstance().installApk(downloadInfo);

				break;
			case DownloadManager.STATE_INSTALLED:
				// 打开应用
				DownloadManager.getInstance().openApp(downloadInfo);
				break;

			default:
				break;
			}

			break;

		default:
			break;
		}
	}

	/** 观察者观察 状态改变 */
	@Override
	public void onDownloadInfoChange(final DownloadInfoBean downloadInfo)
	{
		/*
		 *  过滤掉downloadInfo
		 *  当前bottomHolder的数据为=mDada.packageName
		 *  观察者被通知传过来的数据为=downloadInfo.packageName
		 */
		if(!downloadInfo.packageName.equals(mDada.packageName))
		{
			return;
		}
		
		UIUtils.postTaskSafely(new Runnable()
		{
			public void run()
			{
				// 观察者观察后刷新按钮视图显示
				refreshProgressBtnUI(downloadInfo);
			}
		});
	}

	/** 添加观察者并刷新按钮提示 */
	public void addObserverAndRefreshBtn()
	{
		DownloadManager instance = DownloadManager.getInstance();

		instance.addObserver(this);

		// 开启监听的时候，手动的去获取最新的状态
		DownloadInfoBean Info = instance.getDownloadInfo(mDada);
		// 通知观察者方式一
		instance.notifyObservers(Info);
		// 通知观察者方式二
		//refreshProgressBtnUI(Info);
	}

}
