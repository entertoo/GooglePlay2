package com.example.googleplay.bean;

import com.example.googleplay.manager.DownloadManager;

public class DownloadInfoBean
{
	public String downloadUrl;
	public String	savePathAbsolute;
	public String packageName;
	public int state = DownloadManager.STATE_UNDOWNLOAD;
	
	public long max;
	public long curProgress;
	public Runnable task;
	
}
