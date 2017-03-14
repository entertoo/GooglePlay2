package com.example.googleplay.protocol;

import com.example.googleplay.base.BaseProtocol;
import com.example.googleplay.bean.AppInfoBean;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class DetailProtocol extends BaseProtocol<AppInfoBean>
{
	private String packageName;

	public DetailProtocol(String packageName)
	{
		super();
		this.packageName = packageName;
	}

	// http://localhost:8080/GooglePlayServer/home?index=0
	// http://localhost:8080/GooglePlayServer/detail?packageName=com.itheima.www
	
	@Override
	public String getInterfaceKey()
	{
		return "detail";
	}
	
	@Override
	public Map<String, String> getExtraParams()
	{
		Map<String, String> map = new HashMap<>();
		map.put("packageName", packageName);
		return map;
	}

	@Override
	public AppInfoBean parseJson(String jsonString)
	{
		Gson gson = new Gson();
		return gson.fromJson(jsonString, AppInfoBean.class);
	}

}
