package com.example.myapplication4;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication4.exportcert.CertListActivity;

public class DataFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_data,container,false);

        TabHost tabHost1 = (TabHost) v.findViewById(R.id.tabHost1);
        tabHost1.setup();

        // 라이트 요금제
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.light);
        ts1.setIndicator("라이트");
        tabHost1.addTab(ts1);

        // 노멀 요금제
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.normal);
        ts2.setIndicator("노멀");
        tabHost1.addTab(ts2);

        Button btn_cert_main = (Button)v.findViewById(R.id.btn_certmain);
        btn_cert_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(), CertListActivity.class);
                startActivity(intent1);
            }
        });

        // 프리미엄 요금제
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.premium);
        ts3.setIndicator("프리미엄");
        tabHost1.addTab(ts3);

        Button btn_cert_main2 = (Button)v.findViewById(R.id.btn_certmain2);
        btn_cert_main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getContext(), CertListActivity.class);
                startActivity(intent2);
            }
        });

        return v;
    }

}
