package com.example.googleplay.factory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by haoping on 17/3/14.
 * TODO
 */
public class OkHttpUtil {

    private static OkHttpClient client;

    public static OkHttpClient getOkHttpClient() {

        if (null == client) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
        }

        return client;
    }
}
