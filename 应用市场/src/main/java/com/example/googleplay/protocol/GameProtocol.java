package com.example.googleplay.protocol;

import java.util.List;

import com.example.googleplay.base.BaseProtocol;
import com.example.googleplay.bean.AppInfoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GameProtocol extends BaseProtocol<List<AppInfoBean>>
{

	@Override
	public String getInterfaceKey()
	{
		return "game";
	}

	@Override
	public List<AppInfoBean> parseJson(String jsonString)
	{
		Gson gson = new Gson();
		
		/*=============== 泛型解析 ===============*/
		List<AppInfoBean> appList = gson.fromJson(jsonString, new TypeToken<List<AppInfoBean>>(){}.getType());
		
		return appList;
		
		/*=============== 结点解析 ===============*/
		/*List<AppInfoBean> appInfoBeans = new ArrayList<AppInfoBean>();
		// 获得json的解析器
		JsonParser parser = new JsonParser();

		JsonElement rootJsonElement = parser.parse(jsonString);

		// JsonElement-->转换成jsonArray
		JsonArray rootJsonArray = rootJsonElement.getAsJsonArray();

		// 遍历jsonArray
		for (JsonElement itemJsonElement : rootJsonArray) {
			// jsonElement-->转换成JsonObject
			JsonObject itemJsonObject = itemJsonElement.getAsJsonObject();

			// 得到具体的jsonPrimitivie
			JsonPrimitive desPrimitivie = itemJsonObject.getAsJsonPrimitive("des");
			// jsonPrimitivie-->转换成具体的类型
			String des = desPrimitivie.getAsString();

			// 得到具体的jsonPrimitivie
			JsonPrimitive downloadUrlPrimitivie = itemJsonObject.getAsJsonPrimitive("downloadUrl");
			// jsonPrimitivie-->转换成具体的类型
			String downloadUrl = downloadUrlPrimitivie.getAsString();

			String iconUrl = itemJsonObject.get("iconUrl").getAsString();
			
			long id = itemJsonObject.get("id").getAsLong();
			
			String name = itemJsonObject.get("name").getAsString();
			
			String packageName = itemJsonObject.get("packageName").getAsString();
			
			long size = itemJsonObject.get("size").getAsLong();
			
			float stars = itemJsonObject.get("stars").getAsFloat();
			// 保存数据
			AppInfoBean info = new AppInfoBean();
			info.des = des;
			info.downloadUrl = downloadUrl;
			info.iconUrl = iconUrl;
			info.id = id;
			info.name = name;
			info.packageName = packageName;
			info.size = size;
			info.stars = stars;
			// 添加到集合
			appInfoBeans.add(info);

		}
		return appInfoBeans;*/
	}

}
