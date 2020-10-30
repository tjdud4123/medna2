package com.example.myapplication4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TeaDetailActivity extends AppCompatActivity {
    private Intent intent;
    private TextView teadetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tea_detail);

        intent = getIntent();
        teadetail = findViewById(R.id.teadetail);
    }
}
