package com.example.googleplay.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import com.example.googleplay.conf.Constants;
import com.example.googleplay.conf.Constants.URLS;
import com.example.googleplay.utils.FileUtils;
import com.example.googleplay.utils.IOUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public abstract class BaseProtocol<T>
{
	/** 加载数据 */
	public T loadData(int index) throws Exception {
		/** 先读取本地缓存，看看有没有数据，有数据就返回 */
		T localData = getDataFromLocal(index);
		if (localData != null) {
			return localData;
		}

		/** 无缓冲的话就发送网络请求获取数据 */
		String jsonString = getDataFromNet(index);

		/** json解析 */
		T jsonBean = parseJson(jsonString);

		return jsonBean;
	}

	/** 获取本地缓存数据 */
	public T getDataFromLocal(int index) {
		File cacheFile = getCacheFile(index);
		if (cacheFile.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(cacheFile));
				// 读取第一行插入时间
				String timeTimeMillis = reader.readLine();
				if ((System.currentTimeMillis() - Long.parseLong(timeTimeMillis)) < Constants.PROTOCOLOUTTIME) {
					// 读取缓存内容
					String jsonString = reader.readLine();
					// 返回 json解析内容
					return parseJson(jsonString);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.close(reader);
			}
		}
		return null;
	}

	/** 获取缓存文件 */
	public File getCacheFile(int index) {
		// 缓存存放目录：sdcard/Android/data/包名/json
		String dir = FileUtils.getDir("json");
		// 缓存文件名称：interfaceKey + "." + index / packageName;
		Map<String, String> extraParams = getExtraParams();
		String name = null;
		if (null == extraParams) {
			name = getInterfaceKey() + "." + index;
		} else {
			for (Map.Entry<String, String> entry : extraParams.entrySet()) {
				String packageName = entry.getValue(); // packageName
				name = getInterfaceKey() + "." + packageName;
			}
		}

		File cacheFile = new File(dir, name);
		return cacheFile;
	}

	/** 获取其他参数 */
	public Map<String, String> getExtraParams() {
		return null;
	}

	/** 获取网络数据 */
	public String getDataFromNet(int index) throws HttpException, IOException {
		// 发送网络请求
		HttpUtils httpUtils = new HttpUtils();

		// http://localhost:8080/GooglePlayServer/detail?packageName=com.itheima.www
		// http://localhost:8080/GooglePlayServer/home?index=
		String url = URLS.BASEURL + getInterfaceKey();
		RequestParams params = new RequestParams();

		// 获取拓展的参数，如果拓展参数为空，说明没有重写getExtraParams()方法，默认返回为空
		if (getExtraParams() == null) {
			params.addQueryStringParameter("index", index + "");
		} else {
			Map<String, String> extraParams = getExtraParams();
			for (Map.Entry<String, String> entry : extraParams.entrySet()) {
				String key = entry.getKey();// "packageName"
				String packageName = entry.getValue(); // packageName
				params.addQueryStringParameter(key, packageName);
			}
		}
		ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, params);
		// 读取网络数据
		String jsonString = responseStream.readString();

		/** 保存网络数据到本地 */
		File cacheFile = getCacheFile(index);
		BufferedWriter writer = null;
		try {
			/** 保存网络数据到本地 */
			writer = new BufferedWriter(new FileWriter(cacheFile));
			writer.write(System.currentTimeMillis() + "");// 第一行写入时间
			writer.write("\r\n");// 换行
			writer.write(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(writer);
		}

		return jsonString;
	}

	/** 获取访问URL名称例如home */
	public abstract String getInterfaceKey();

	/** 解析json数据 */
	// public abstract T parseJson(String jsonString);

	/** 泛型解析json数据 */
	public T parseJson(String jsonString) {
		// 反射
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		// 真实类型
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		// 传入泛型类型为可变数组，这里只有一个类型T，我们取第一个就行
		Type type = actualTypeArguments[0];

		return new Gson().fromJson(jsonString, type);
	}
}
