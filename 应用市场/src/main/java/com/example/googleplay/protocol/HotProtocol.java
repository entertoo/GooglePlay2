package com.example.googleplay.protocol;

import com.example.googleplay.base.BaseProtocol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class HotProtocol extends BaseProtocol<List<String>>
{

	@Override
	public String getInterfaceKey()
	{
		return "hot";
	}

	@Override
	public List<String> parseJson(String jsonString)
	{
		List<String> hotList;
		Gson gson = new Gson();
		hotList = gson.fromJson(jsonString, new TypeToken<List<String>>(){}.getType());
		return hotList;
	}

}
