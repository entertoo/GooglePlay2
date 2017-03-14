package com.example.googleplay.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.bean.AppInfoBean;
import com.example.googleplay.bean.DownloadInfoBean;
import com.example.googleplay.conf.Constants.URLS;
import com.example.googleplay.manager.DownloadManager;
import com.example.googleplay.manager.DownloadManager.DownloadStateObserver;
import com.example.googleplay.utils.BitmapHelper;
import com.example.googleplay.utils.StringUtils;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.views.CircleProgressView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 初始化显示并刷新ListView中的一条APP信息
 * @author haopi
 *
 */
public class AppItemHolder extends BaseHolder<AppInfoBean> implements DownloadStateObserver
{
	/*
	 * des 产品介绍：google市场app测试。 downloadUrl app/com.itheima.www/com.itheima.www
	 * iconUrl app/com.itheima.www/icon.jpg id 1525489 name 黑马程序员 packageName
	 * com.itheima.www size 91767 stars 5
	 */
	@ViewInject(R.id.item_appinfo_iv_icon)
	ImageView mIvIcon;
	@ViewInject(R.id.item_appinfo_tv_title)
	TextView mTvTitle;
	@ViewInject(R.id.item_appinfo_rb_stars)
	RatingBar mRbStars;
	@ViewInject(R.id.item_appinfo_tv_size)
	TextView mTvSize;
	@ViewInject(R.id.item_appinfo_tv_des)
	TextView mTvDes;
	@ViewInject(R.id.item_appinfo_circleprogress)
	CircleProgressView mCircleProgressView;

	private AbsListView absListView;
	private DownloadInfoBean downloadInfo;

	public AppItemHolder(AbsListView absListView) {
		super();
		this.absListView = absListView;
	}

	/**
	 * @des 初始化viewHolder/根视图
	 * @call BaseHolder初始化被调用
	 */
	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_info, null);
		// 注入
		ViewUtils.inject(this, view);
		// 点击环形下载视图，根据不同的下载状态显示不同的视图
		mCircleProgressView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// 获取用户下载状态，根据不同的状态给用户不同的提示
				mCircleProgressView.setMax(downloadInfo.max);
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

			}
		});
		return view;
	}

	/**
	 * @des 刷新视图
	 * @call 父类BaseHolder中setDataAndRefreshHolderView被调用的时候调用
	 */
	@Override
	public void refreshHolderView(AppInfoBean data)
	{
		downloadInfo = DownloadManager.getInstance().getDownloadInfo(mData);
		mCircleProgressView.setTag(downloadInfo.packageName);
		// 清除复用convertView之后的progress效果
		mCircleProgressView.setProgress(0);
		mTvDes.setText(mData.des);
		mTvSize.setText(StringUtils.formatFileSize(mData.size));
		mTvTitle.setText(mData.name);
		mRbStars.setRating(mData.stars);
		// 默认图片
		mIvIcon.setImageResource(R.drawable.ic_default);
		// 获取图片完整路径
		String uri = URLS.IMAGE_BASE_URL + mData.iconUrl;
		// 使用工具类展示图片
		BitmapHelper.display(mIvIcon, uri);
		// 刷新环形下载视图
		refreshCircleUI(downloadInfo);
		
	}

	/**
	 * 刷新环形下载视图
	 * @param downloadInfo 下载信息
	 */
	@NonNull
	private void refreshCircleUI(DownloadInfoBean downloadInfo)
	{
		int progress;
		if(mData.packageName.equals(mCircleProgressView.getTag())){
			switch (downloadInfo.state)
			{
				case DownloadManager.STATE_UN_DOWNLOAD:
					mCircleProgressView.setIcon(R.drawable.ic_download);
					mCircleProgressView.setNote("下载");
					break;
				case DownloadManager.STATE_DOWNLOADING:
					mCircleProgressView.setProgressEnable(true);
					mCircleProgressView.setIcon(R.drawable.ic_pause);
					// 设置下载环形进度条
					mCircleProgressView.setMax(downloadInfo.max);
					mCircleProgressView.setProgress(downloadInfo.curProgress);
					// 设置下载进度百分数
					progress = (int) (downloadInfo.curProgress * 100.f / downloadInfo.max + .5f);
					mCircleProgressView.setNote(progress + "%");
					break;
				case DownloadManager.STATE_PAUSED_DOWNLOAD:
					mCircleProgressView.setIcon(R.drawable.ic_resume);
					mCircleProgressView.setNote("继续");
					break;
				case DownloadManager.STATE_WAITING_DOWNLOAD:
					mCircleProgressView.setIcon(R.drawable.ic_pause);
					mCircleProgressView.setNote("等待");
					break;
				case DownloadManager.STATE_DOWNLOAD_FAILED:
					mCircleProgressView.setProgressEnable(false);
					mCircleProgressView.setIcon(R.drawable.ic_redownload);
					mCircleProgressView.setNote("重试");
					break;
				case DownloadManager.STATE_DOWNLOADED:
					mCircleProgressView.setProgressEnable(false);
					mCircleProgressView.setIcon(R.drawable.ic_install);
					mCircleProgressView.setNote("安装");
					break;
				case DownloadManager.STATE_INSTALLED:
					mCircleProgressView.setIcon(R.drawable.ic_install);
					mCircleProgressView.setNote("打开");
					break;
			}
		}

	}

	@Override
	public void onDownloadInfoChange(final DownloadInfoBean downloadInfo)
	{
		/*
		 * 过滤掉downloadInfo 当前bottomHolder的数据为=mData.packageName
		 * 观察者被通知传过来的数据为=downloadInfo.packageName
		 */
		if (!downloadInfo.packageName.equals(mData.packageName))
		{
			return;
		}

		UIUtils.postTaskSafely(new Runnable()
		{
			public void run()
			{
				refreshCircleUI(downloadInfo);
			}
		});
	}

}
