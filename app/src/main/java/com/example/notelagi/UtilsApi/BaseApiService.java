package com.example.notelagi.UtilsApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("login_driver")
    Call<ResponseBody> loginRequest(@Field("nik")String nik ,
                                    @Field("password")String password,
                                    @Field("regid")String regid);
}
