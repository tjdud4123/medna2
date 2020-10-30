package com.example.myapplication4.modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeaList {

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("teaId")
    @Expose
    private Long teaId;
    @SerializedName("teaName")
    @Expose
    private String teaName;

    public TeaList(){}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getTeaId() {
        return teaId;
    }

    public void setTeaId(Long teaId) {
        this.teaId = teaId;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public TeaList(String imageUrl, Long teaId, String teaName) {
        this.imageUrl = imageUrl;
        this.teaId = teaId;
        this.teaName = teaName;
    }
}
