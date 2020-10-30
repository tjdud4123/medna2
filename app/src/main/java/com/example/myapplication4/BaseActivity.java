package com.example.myapplication4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication4.modules.HealthResponse;


public class BaseActivity extends AppCompatActivity {
    private RelativeLayout mRlContent;
    private ImageButton mBtnLeft;
    private TextView mTvTtite;

    private RelativeLayout mRlPopup;
    private TextView mTvPopupTitle;
    private Button mBtnPopupOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
        LayoutInflater inflater = getLayoutInflater();
        this.setContentView(inflater.inflate(R.layout.activity_base, null, true));
        mRlContent = (RelativeLayout) findViewById(R.id.rl_content);
        mBtnLeft = (ImageButton) findViewById(R.id.btn_header_left);
        mTvTtite = (TextView) findViewById(R.id.tv_header_title);

        mRlPopup = (RelativeLayout) findViewById(R.id.rl_base_popup);
        mTvPopupTitle = (TextView) findViewById(R.id.tv_base_popup_title);
        mBtnPopupOK = (Button) findViewById(R.id.btn_base_popup_ok);

        mBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRlPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getVisibility() == View.VISIBLE){
                    mRlPopup.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(layoutResID, mRlContent, true);
    }

    protected void setTitle(String title){
        this.mTvTtite.setText(title);
    }

    protected void hideHeaderLeftBtn() {
        this.mBtnLeft.setVisibility(View.INVISIBLE);
    }

    protected void showHeaderLeftBtn() {
        this.mBtnLeft.setVisibility(View.VISIBLE);
    }

    protected void showPopup(String title, View.OnClickListener callback) {
        mRlPopup.setVisibility(View.VISIBLE);
        mBtnPopupOK.setOnClickListener(callback);
        mTvPopupTitle.setText(title);
    }

    protected void hidePopup() {
        mRlPopup.setVisibility(View.GONE);
    }
}

