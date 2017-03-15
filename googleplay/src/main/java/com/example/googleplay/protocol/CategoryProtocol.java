package com.example.googleplay.protocol;

import com.example.googleplay.base.BaseProtocol;
import com.example.googleplay.bean.CategoryInfoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryProtocol extends BaseProtocol<List<CategoryInfoBean>> {

    @Override
    public String getInterfaceKey() {
        return "category";
    }

    @Override
    public List<CategoryInfoBean> parseJson(String jsonString) {
        // android自带节点解析
        List<CategoryInfoBean> categoryList = new ArrayList<>();
        try {
            // 获取根节点,为2数组
            JSONArray rootJsonArray = new JSONArray(jsonString);

            for (int i = 0; i < rootJsonArray.length(); i++) {
                // 每个数组value都是Object
                JSONObject jsonObject = rootJsonArray.getJSONObject(i);

                // key=i 获取title节点的value数据
                String title = jsonObject.getString("title");

                // 创建CategoryInfoBean保存数据
                CategoryInfoBean titleBean = new CategoryInfoBean();

                titleBean.title = title;
                titleBean.isTitle = true;

                categoryList.add(titleBean);

                // key=i 获取infos节点的value数据
                JSONArray infosJsonArray = jsonObject.getJSONArray("infos");
                for (int j = 0; j < infosJsonArray.length(); j++) {
                    JSONObject infoJsonObject = infosJsonArray.getJSONObject(j);
                    String name1 = infoJsonObject.getString("name1");
                    String name2 = infoJsonObject.getString("name2");
                    String name3 = infoJsonObject.getString("name3");
                    String url1 = infoJsonObject.getString("url1");
                    String url2 = infoJsonObject.getString("url2");
                    String url3 = infoJsonObject.getString("url3");

                    // 创建CategoryInfoBean保存数据
                    CategoryInfoBean infoBean = new CategoryInfoBean();

                    infoBean.name1 = name1;
                    infoBean.name2 = name2;
                    infoBean.name3 = name3;
                    infoBean.url1 = url1;
                    infoBean.url2 = url2;
                    infoBean.url3 = url3;

                    categoryList.add(infoBean);
                }
            }
            return categoryList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

}
