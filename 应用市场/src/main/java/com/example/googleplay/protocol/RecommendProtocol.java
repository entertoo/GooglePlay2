package com.example.googleplay.protocol;

import com.example.googleplay.base.BaseProtocol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class RecommendProtocol extends BaseProtocol<List<String>> {

    @Override
    public String getInterfaceKey() {
        return "recommend";
    }

    @Override
    public List<String> parseJson(String jsonString) {
        Gson gson = new Gson();
        List<String> recommendList = gson.fromJson(jsonString, new TypeToken<List<String>>() {
        }.getType());
        return recommendList;
    }

}
