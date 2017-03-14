package com.example.googleplay.protocol;

import com.example.googleplay.base.BaseProtocol;
import com.example.googleplay.bean.AppInfoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class AppProtocol extends BaseProtocol<List<AppInfoBean>>
{
	@Override
	public String getInterfaceKey()
	{
		return "app";
	}

	@Override
	public List<AppInfoBean> parseJson(String jsonString)
	{
        List<AppInfoBean> appList;
		Gson gson = new Gson();
		// 泛型解析
		appList = gson.fromJson(jsonString, new TypeToken<List<AppInfoBean>>(){}.getType());
		return appList;
	}

}
