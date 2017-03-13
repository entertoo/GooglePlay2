package com.example.googleplay.protocol;

import java.util.List;

import com.example.googleplay.base.BaseProtocol;
import com.example.googleplay.bean.AppInfoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AppProtocol extends BaseProtocol<List<AppInfoBean>>
{

	@Override
	public String getInterfaceKey()
	{
		return "app";
	}

	// 解析json数据
	@Override
	public List<AppInfoBean> parseJson(String jsonString)
	{
		Gson gson = new Gson();
		
		// 泛型解析
		List<AppInfoBean> appList = gson.fromJson(jsonString, new TypeToken<List<AppInfoBean>>(){}.getType());
		
		return appList;
	}

}
