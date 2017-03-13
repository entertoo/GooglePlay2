package com.example.googleplay.holder;

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

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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

	// 反射view 注解在一个view声明上
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

	AppInfoBean mDada;

	/**
	 * @des 初始化viewHolder/根视图
	 * @call BaseHolder初始化被调用
	 */
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
				DownloadInfoBean mDownloadInfo = DownloadManager.getInstance().getDownloadInfo(mDada);
				mCircleProgressView.setMax(mDownloadInfo.max);

				// 获取用户下载状态，根据不同的状态，点击产生不同的用户行为
				switch (mDownloadInfo.state)
				{
				case DownloadManager.STATE_UNDOWNLOAD:
					// 去下载
					DownloadManager.getInstance().doDownload(mDownloadInfo);
					break;
				case DownloadManager.STATE_DOWNLOADING:
					// 暂停下载
					DownloadManager.getInstance().pauseDownload(mDownloadInfo);
					break;
				case DownloadManager.STATE_PAUSEDDOWNLOAD:
					// 继续下载
					DownloadManager.getInstance().doDownload(mDownloadInfo);
					break;
				case DownloadManager.STATE_WAITINGDOWNLOAD:
					// 取消下载
					DownloadManager.getInstance().cancelDownload(mDownloadInfo);
					break;
				case DownloadManager.STATE_DOWNLOADFAILED:
					// 重试下载
					DownloadManager.getInstance().doDownload(mDownloadInfo);
					break;
				case DownloadManager.STATE_DOWNLOADED:
					// 安装应用
					DownloadManager.getInstance().installApk(mDownloadInfo);

					break;
				case DownloadManager.STATE_INSTALLED:
					// 打开应用
					DownloadManager.getInstance().openApp(mDownloadInfo);
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
		// 清除复用convertView之后的progress效果
		mCircleProgressView.setProgress(0);
		
		mDada = data;

		mTvDes.setText(data.des);
		mTvSize.setText(StringUtils.formatFileSize(data.size));
		mTvTitle.setText(data.name);
		mRbStars.setRating(data.stars);

		// 默认图片
		mIvIcon.setImageResource(R.drawable.ic_default);
		// 获取图片完整路径
		String uri = URLS.IMAGEBASEURL + data.iconUrl;
		// 使用工具类展示图片
		BitmapHelper.display(mIvIcon, uri);

		DownloadInfoBean downloadInfo = DownloadManager.getInstance().getDownloadInfo(mDada);

		// 刷新环形下载视图
		refreshCircleUI(downloadInfo);
		
	}
	
	// 刷新环形下载视图
	public void refreshCircleUI(DownloadInfoBean downloadInfo)
	{
		switch (downloadInfo.state)
		{
		case DownloadManager.STATE_UNDOWNLOAD:
			mCircleProgressView.setIcon(R.drawable.ic_download);
			mCircleProgressView.setNote("下载");
			break;
		case DownloadManager.STATE_DOWNLOADING:
			mCircleProgressView.setProgressEnable(true);
			mCircleProgressView.setIcon(R.drawable.ic_pause);
			if(downloadInfo != null)
			{
				// 设置下载环形进度条
				mCircleProgressView.setProgress(downloadInfo.curProgress);
				// 设置下载进度百分数
				int progress = (int) (downloadInfo.curProgress * 100.f / downloadInfo.max + .5f);
				mCircleProgressView.setNote(progress + "%");
			}
			break;
		case DownloadManager.STATE_PAUSEDDOWNLOAD:
			mCircleProgressView.setIcon(R.drawable.ic_resume);
			mCircleProgressView.setNote("继续");
			break;
		case DownloadManager.STATE_WAITINGDOWNLOAD:
			mCircleProgressView.setIcon(R.drawable.ic_pause);
			mCircleProgressView.setNote("等待");
			break;
		case DownloadManager.STATE_DOWNLOADFAILED:
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

		default:
			break;
		}
	}

	@Override
	public void onDownloadInfoChange(final DownloadInfoBean downloadInfo)
	{
		/*
		 * 过滤掉downloadInfo 当前bottomHolder的数据为=mDada.packageName
		 * 观察者被通知传过来的数据为=downloadInfo.packageName
		 */
		if (!downloadInfo.packageName.equals(mDada.packageName))
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
