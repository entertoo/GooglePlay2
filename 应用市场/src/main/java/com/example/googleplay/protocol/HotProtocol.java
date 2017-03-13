package com.example.googleplay.protocol;

import java.util.List;

import com.example.googleplay.base.BaseProtocol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
		Gson gson = new Gson();
		List<String> hotList = gson.fromJson(jsonString, new TypeToken<List<String>>(){}.getType());
		return hotList;
	}

}
