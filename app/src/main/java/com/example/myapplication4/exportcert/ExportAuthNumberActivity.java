package com.example.myapplication4.exportcert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import io.codef.cfcertmanager.CFCertTransfer;
import io.codef.cfcertmanager.callback.CFCertExportInterface;
import io.codef.cfcertmanager.util.Base64;
import com.example.myapplication4.BaseActivity;
import com.example.myapplication4.MainActivity;
import com.example.myapplication4.R;

public class ExportAuthNumberActivity extends BaseActivity {
    private Button mBtnOK;

    private EditText mEtFirst;
    private EditText mEtSecond;
    private EditText mEtThrid;

    private String mPfxPath;
    private String mPfxBase64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        super.setTitle("인증서 관리");

        mBtnOK = (Button) findViewById(R.id.btn_export_ok);

        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = mEtFirst.getText().toString();
                String second = mEtSecond.getText().toString();
                String third = mEtThrid.getText().toString();

                if (first == null || first.length() != 4) {
                    Toast.makeText(ExportAuthNumberActivity.this, "인증번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (second == null || second.length() != 4) {
                    Toast.makeText(ExportAuthNumberActivity.this, "인증번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (third == null || third.length() != 4) {
                    Toast.makeText(ExportAuthNumberActivity.this, "인증번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return ;
                }

                CFCertTransfer transfer = CFCertTransfer.getInstatnce(ExportAuthNumberActivity.this);
                transfer.setToken("");
                boolean isSuccess = transfer.exportCertification(mPfxBase64, first + second + third, new CFCertExportInterface() {
                    @Override
                    public void onExport(final boolean isSuccess, final String code, final String message) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccess && code.equalsIgnoreCase("CF-00000")) {
                                    ExportAuthNumberActivity.super.showPopup(message, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ExportAuthNumberActivity.super.hidePopup();
                                            Intent mainIntent = new Intent(ExportAuthNumberActivity.this, MainActivity.class);
                                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            startActivity(mainIntent);
                                        }
                                    });
                                }else{
                                    ExportAuthNumberActivity.super.showPopup(message, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ExportAuthNumberActivity.super.hidePopup();
                                        }
                                    });
                                }
                            }
                        });

                    }

                    @Override
                    public void onFail(final String s, final String s1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ExportAuthNumberActivity.super.showPopup(s1, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ExportAuthNumberActivity.super.hidePopup();
                                    }
                                });
                            }
                        });

                    }
                });

                if (!isSuccess) {
                    ExportAuthNumberActivity.super.showPopup("codef에서 발급받은 token을 확인하여주세요", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ExportAuthNumberActivity.super.hidePopup();
                        }
                    });
                }

            }
        });

        mEtFirst = (EditText) findViewById(R.id.et_export_first);
        mEtSecond = (EditText) findViewById(R.id.et_export_second);
        mEtThrid = (EditText) findViewById(R.id.et_export_thrid);

        mPfxPath = getIntent().getStringExtra("CERT_PATH");

        if (mPfxPath != null && mPfxPath.length() > 1){
            mPfxBase64 = readPfxToBase64(mPfxPath);
        }

    }


    private String readPfxToBase64(String path) {
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
}
