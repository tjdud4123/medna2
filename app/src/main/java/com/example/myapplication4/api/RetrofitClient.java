package com.example.myapplication4.api;

import com.example.myapplication4.modules.HealthResponse;

import java.net.URL;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.0.127:8080/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;
    private ApiService apiService ;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // 서버 url 설정
                .addConverterFactory(GsonConverterFactory.create()) // 데이터 파싱 설정
                .build(); // 객체정보 반환


    }

    public static synchronized RetrofitClient getInstance() {
        if(mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public ApiService getApi(){
        return retrofit.create(ApiService.class);
    }

}
