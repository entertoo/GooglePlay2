package com.example.googleplay.protocol;

import com.example.googleplay.base.BaseProtocol;
import com.example.googleplay.bean.SubjectInfoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SubjectProtocol extends BaseProtocol<List<SubjectInfoBean>>
{

	@Override
	public String getInterfaceKey()
	{
		return "subject";
	}

	@Override
	public List<SubjectInfoBean> parseJson(String jsonString)
	{
		List<SubjectInfoBean> subjectBeanList;
		Gson gson = new Gson();
		// 泛型解析
		subjectBeanList = gson.fromJson(jsonString, new TypeToken<List<SubjectInfoBean>>(){}.getType());
		return subjectBeanList;
	}

}
