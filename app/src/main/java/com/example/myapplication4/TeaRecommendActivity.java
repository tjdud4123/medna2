package com.example.myapplication4;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.adapter.RecommendTitleAdapter;
import com.example.myapplication4.model.RecommendTitle;


import java.util.List;

public class TeaRecommendActivity extends AppCompatActivity {

    private Intent intent;

    // recommend
    private RecyclerView rvrecommend;
    private RecommendTitleAdapter recommendTitleAdapter;
    private List<RecommendTitle> recommendTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tea_recommend);

        intent = getIntent();

        initComponents();

        recommendTitles = recommendData();

        recommendTitleAdapter = new RecommendTitleAdapter(recommendTitles, TeaRecommendActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(TeaRecommendActivity.this);
        rvrecommend.setLayoutManager(manager);
        rvrecommend.setAdapter(recommendTitleAdapter);
    }

    private void initComponents() {
        rvrecommend = findViewById(R.id.rvrecommend);
    }

    private List<RecommendTitle> recommendData() {
        return recommendTitles;
    }
}
