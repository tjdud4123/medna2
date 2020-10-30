package com.example.myapplication4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication4.exportcert.CertListActivity;

public class AgreeActivity extends AppCompatActivity {
    AgreeSign mypinter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infoagree);

        mypinter = (AgreeSign) findViewById(R.id.painter);
        Button btn_agree = (Button) findViewById(R.id.btn_agree);
        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgreeActivity.this, CertListActivity.class);
            }
        });

    }


}
