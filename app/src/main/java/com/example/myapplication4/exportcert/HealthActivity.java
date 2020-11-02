package com.example.myapplication4.exportcert;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication4.BaseActivity;
import com.example.myapplication4.MainActivity;
import com.example.myapplication4.MypageFragment;
import com.example.myapplication4.R;
import com.example.myapplication4.api.RetrofitClient;
import com.example.myapplication4.modules.HealthResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import io.codef.cfcertmanager.util.Base64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthActivity extends BaseActivity implements View.OnClickListener {
    private String mPfxPath;
    private String mPfxBase64;
    MypageFragment fragment3;
    private Button mBtnN;
    private EditText userID, userPassWord, IdentityNo, InquiryType, SearchStartYear, SearchEndYear, Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle("건강보험공단");
        setContentView(R.layout.activity_health);

        //화면 전환 프래그먼트
        //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.framelayout, MypageFragment.newInstance()).commit();

        fragment3 = new MypageFragment();

        mBtnN = (Button) findViewById(R.id.btn_certlist_Next);

        userID = (EditText) findViewById(R.id.et_userId);
        userPassWord = (EditText) findViewById(R.id.et_userPassword);
        IdentityNo = (EditText) findViewById(R.id.et_identity);
        InquiryType = (EditText) findViewById(R.id.et_inquiryType);
        SearchStartYear = (EditText) findViewById(R.id.et_searchStartYear);
        SearchEndYear = (EditText) findViewById(R.id.et_searchEndYear);
        Type = (EditText) findViewById(R.id.et_type);

        findViewById(R.id.btn_certlist_Next).setOnClickListener(this);

        // 건강보험공단 아이디/비밀번호 찾기 링크
        TextView find_id_pw = (TextView) findViewById(R.id.id_pw_find);
        find_id_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.nhis.or.kr/menu/retriveMenuSet.xx?menuId=M3000"));
                startActivity(intent);
            }
        });

        mPfxPath = getIntent().getStringExtra("CERT_PATH");

        if (mPfxPath != null && mPfxPath.length() > 1){
            mPfxBase64 = readPfxToBase64(mPfxPath);
        }

    }
    /* 프래그먼트 화면 전환
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment).commit();
    }*/

    // 버튼 클릭
    private void health() {

        final String userId = userID.getText().toString().trim();
        final String userPassword = userPassWord.getText().toString().trim();
        final String identityNo = IdentityNo.getText().toString().trim();
        final String inquiryType = InquiryType.getText().toString().trim();
        final String searchStartYear = SearchStartYear.getText().toString().trim();
        final String searchEndYear = SearchEndYear.getText().toString().trim();
        final String type = Type.getText().toString().trim();
        final String certFile = readPfxToBase64(mPfxPath);
        final String certPassword = getIntent().getStringExtra("certPassword");


        if (userId == null || userID.getText().toString().length() < 1) {
            userID.setError("건강보험공단 아이디를 입력해주세요");
            userID.requestFocus();
            return;
        }
        if (userPassword == null || userPassWord.getText().toString().length() < 1) {
            userPassWord.setError("건강보험공단 비밀번호를 입력해주세요");
            userPassWord.requestFocus();
            return;
        }
        if (!(identityNo.length() == 13)) {
            IdentityNo.setError("주민번호를 정확히 입력해주세요");
            IdentityNo.requestFocus();
            return;
        }
        if (inquiryType == null || InquiryType.getText().toString().length() < 1) {
            InquiryType.setError("조회 구분을 선택해 주세요");
            InquiryType.requestFocus();
            return;
        }
        if (searchStartYear == null || SearchStartYear.getText().toString().length() < 1) {
            SearchStartYear.setError("조회 시작 연도를 입력해주세요");
            SearchStartYear.requestFocus();
            return;
        }
        if (searchEndYear == null || SearchEndYear.getText().toString().length() < 1) {
            SearchEndYear.setError("조회 종료 연도를 입력해주세요");
            SearchEndYear.requestFocus();
            return;
        }
        if (type == null || Type.getText().toString().length() < 1) {
            Type.setError("조회 대상을 입력해주세요");
            Type.requestFocus();
            return;
        }


        HealthResponse healthInfo = new HealthResponse(certFile, certPassword, identityNo, inquiryType, searchEndYear, searchStartYear, type, userId, userPassword);
        /*healthResponse.setCertFile(certFile);
        healthResponse.setCertPassword(certPassword);*/
        Call<HealthResponse> call = RetrofitClient.getInstance().getApi().healthInfo(healthInfo);

        call.enqueue(new Callback<HealthResponse>() {
            @Override
            public void onResponse(Call<HealthResponse> call, Response<HealthResponse> response) {
                if(response.isSuccessful()){
                    final HealthResponse healthResponse = response.body();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(HealthActivity.this, MainActivity.class));
                        }
                    },700);
                } else {
                    Toast.makeText(HealthActivity.this,"정보가 잘못 입력되었습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HealthResponse> call, Throwable t) {
                //Toast.makeText(HealthActivity.this,"정보가 잘못 입력되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String readPfxToBase64(String path) {
        try {
            File file = new File(path);

            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                // buf 배열의 크기를 1024 로 지정한다.
                byte[] buf = new byte[1024];
                int readCount = 0;

                // "FileStream.txt" 에서 buf 크기(1024) 만큼 읽는 것을 반복한다.
                while((readCount = inputStream.read(buf)) != -1){
                    bos.write(buf, 0, readCount);
                }

                byte[] ret = bos.toByteArray();

                inputStream.close();
                bos.close();

                return new String(Base64.encode(ret));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_certlist_Next:
                health();
                break;
        }
    }
}
