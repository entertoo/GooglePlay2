package com.example.googleplay.protocol;

import com.example.googleplay.base.BaseProtocol;
import com.example.googleplay.bean.HomeBean;
import com.google.gson.Gson;

public class HomeProtocol extends BaseProtocol<HomeBean>
{
	@Override
	public String getInterfaceKey()
	{
		return "home";
	}

	/**父类BaseProtocol已经实现该方法进行泛型解析json数据*/
	@Override
	public HomeBean parseJson(String jsonString)
	{
		Gson gson = new Gson();
		
		HomeBean homeBean = gson.fromJson(jsonString, HomeBean.class);
		
		return homeBean;
	}
}
