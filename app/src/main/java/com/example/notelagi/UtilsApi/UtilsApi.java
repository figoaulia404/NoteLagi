package com.example.notelagi.UtilsApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UtilsApi {
//    public static final String BASE_URL_API = "http://bluestar.couries.id/public/api/";
//
//    public static BaseApiService getAPIBaseApiService(){
//        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
//    }

    private static Retrofit retrofit;
    private static final  String BASE_URL = "http://bluestar.couries.id/public/api/";

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit  = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
