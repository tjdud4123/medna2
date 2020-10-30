package com.example.myapplication4.api;

import com.example.myapplication4.MainFragment;
import com.example.myapplication4.modules.HealthResponse;
import com.example.myapplication4.modules.TeaEfficacy;
import com.example.myapplication4.modules.TeaRecommend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // 건강 검진 데이터 조회
    @FormUrlEncoded
    @POST("v1/api/health")
    Call<HealthResponse> healthInfo(@Body HealthResponse healthResponse); //
    // , @Header("accessToken") String accessToken


    //@FormUrlEncoded
    //@GET("v1/api/recommend")
    //Call<TeaImage> teaInfo(@Header("accessToken") String accessToken);
    //@GET("v1/api/tea/group")
    //Call<TeaImage> teaInfo(@Query("imageUrl") String imageUrl, @Query("teaName") String teaName);

    // 그룹별 차 정보 조회
    @GET("v1/api/tea/group")
    Call<List<MainFragment.TeaGetByResponse>> teagroupInfo();

    // 효능별 차 정보 조회
    @GET("v1/api/tea/efficacy")
    Call<TeaEfficacy> teaefficacyInfo(@Query("category") String category);

    // getRecommend
    @FormUrlEncoded
    @GET("v1/api/recommend")
    Call<TeaRecommend> recommendInfo(@Header("accessToken") String accessToken);
}
