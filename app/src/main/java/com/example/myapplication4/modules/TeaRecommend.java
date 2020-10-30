package com.example.myapplication4.modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TeaRecommend {

    @SerializedName("efficacy")
    @Expose
    private String efficacy;
    @SerializedName("teaList")
    @Expose
    private List<TeaList> teaList = new ArrayList<>();

    public TeaRecommend(){}

    public String getEfficacy() {
        return efficacy;
    }

    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }

    public List<TeaList> getTeaList() {
        return teaList;
    }

    public void setTeaList(List<TeaList> teaList) {
        this.teaList = teaList;
    }

    public TeaRecommend(String efficacy, List<TeaList> teaList){
        this.efficacy = efficacy;
        this.teaList = teaList;
    }
}
