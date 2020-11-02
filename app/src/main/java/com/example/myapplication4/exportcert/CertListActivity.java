package com.example.myapplication4.exportcert;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.BaseActivity;
import com.example.myapplication4.MainActivity;
import com.example.myapplication4.R;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import io.codef.cfcertmanager.CFCertManager;

public class CertListActivity extends BaseActivity {
    private ArrayList<String> mCertpathListData;
    private Map<String, String> mMapFileName = new HashMap<String, String>();
    private Map<String, String> mMapCertSelect = new HashMap<String, String>();
    private RecyclerView mRcMain;
    private Button mBtnOK;
    private Button mbtnNext;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public EditText mEtCertPassword; //
    private EditText mEtidentity;
    private EditText mEtidentity2;
    private EditText mEtuserId;
    private EditText mEtuserPassword;

    private CFCertManager mCertManager;  // 인증서 변환 관리 객체

    private int mSelectIndex = -1;

    private JSONObject mCertPids;

    public static Context context_main; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certlist);

        super.setTitle("인증서 관리");
        mCertManager = CFCertManager.getInstatnce(CertListActivity.this); // getInstatnce : CFCertManager

        mRcMain = (RecyclerView) findViewById(R.id.rc_listmain);
        mEtCertPassword = (EditText) findViewById(R.id.et_certlist);
        mRcMain.setHasFixedSize(true);      // RecyclerView의 사이즈 고정?
        mLayoutManager = new LinearLayoutManager(this);   // 가로 형태로 아이템 배치
        mRcMain.setLayoutManager(mLayoutManager);

        // 권한 요청
        if (ContextCompat.checkSelfPermission(CertListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ContextCompat.checkSelfPermission(CertListActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(CertListActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(CertListActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1001);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
//                drawCertList();
            }
        }else {
//            drawCertList();
        }


        drawCertList();

        mBtnOK = (Button) findViewById(R.id.btn_certlist_OK);

        mBtnOK.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                String certPassword = mEtCertPassword.getText().toString();

                if (mSelectIndex < 0) {
                    Toast.makeText(CertListActivity.this, "인증서를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (certPassword == null || mEtCertPassword.getText().toString().length() < 1) {
                    Toast.makeText(CertListActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    mEtCertPassword.requestFocus();
                    return ;
                }

                Intent intent = new Intent(CertListActivity.this, HealthActivity.class);
                intent.putExtra("certPassword",certPassword);   //

                String basePath = mCertpathListData.get(mSelectIndex);

                String tempPfxPath = getCacheDir() + "/tempPfx.pfx";

                // der2Pfx : der path와 key path을 가지고 pfx파일 생성하여 해당 경로에 생성
                if ( !mCertManager.der2Pfx(basePath+"/signCert.der", basePath+"/signPri.key", tempPfxPath, certPassword, certPassword)) {
                    Toast.makeText(CertListActivity.this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                };

                intent.putExtra("CERT_PATH", tempPfxPath);
                startActivity(intent);
            }
        });

        String certPids = getString(R.string.cert_pids);

        try{
            mCertPids = new JSONObject(certPids);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void drawCertList() {
        try {
            mCertpathListData = new ArrayList<String>();
            // baseNPKI 경로
            // 29 부터 외부 저장소가 접근이 허용이 안된다.
            //String directory = "/data/data/io.codef.cfcertsample" + "/NPKI";
            String directory =  Environment.getExternalStorageDirectory() + "/NPKI";   // 최상위 폴더
            File file = new File(directory);


            if (file.exists()) {
                searchAllCertAndKeyFils(file.listFiles());
            }

            // 중복제거
            HashSet<String> set = new HashSet<String>(mCertpathListData);
            ArrayList<String> listWithoutDuplicateElements = new ArrayList<String>(set);

            mAdapter = new CertAdapter(listWithoutDuplicateElements);
            mRcMain.setAdapter(mAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 모든 키 델 파일 경로 찾기
     * @param files
     * @return
     */
    private File[] searchAllCertAndKeyFils(File[] files){
        try{
            if(files != null && files.length > 0){
                for(File file : files){
                    if(file.isDirectory()){
                        searchAllCertAndKeyFils(file.listFiles());
                    }else{
                        String fileName = file.getName();

                        if(fileName.toLowerCase().endsWith("signcert.der") == true || fileName.toLowerCase().endsWith("signpri.key")){
                            mMapFileName.put(file.getParentFile().getAbsolutePath(), file.getParentFile().getName());
                            mCertpathListData.add(file.getParentFile().getAbsolutePath());
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return null;
    }


    public class CertAdapter extends RecyclerView.Adapter<CertAdapter.CertViewHolder> {
        private ArrayList<String> mDataset;

        public class CertViewHolder extends RecyclerView.ViewHolder {

            public RelativeLayout rlMain;
            public TextView tvTitle;
            public TextView tvOrganization;
            public TextView tvExprie;
            public ImageView ivIcon;
            public CertViewHolder(RelativeLayout v) {
                super(v);
                rlMain = v;
            }
        }


        public CertAdapter(ArrayList<String> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public CertAdapter.CertViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            // create a new view
            RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cert_list, parent, false);

            CertViewHolder vh = new CertViewHolder(v);
            vh.tvTitle = v.findViewById(R.id.tv_cert_list_title);
            vh.tvOrganization = v.findViewById(R.id.tv_cert_list_organization);
            vh.tvExprie = v.findViewById(R.id.tv_cert_list_expire);
            vh.ivIcon = (ImageView)v.findViewById(R.id.iv_cert_list_icon);

            return vh;
        }


        @Override
        public void onBindViewHolder(final CertViewHolder holder, final int position) {
            final String path = mDataset.get(position) + "/signCert.der";

            holder.tvTitle.setText(mCertManager.getCertNameRFC2253(path));

            String policyID = mCertManager.getCertPolicyId(path);

            try{
                JSONObject obj = mCertPids.getJSONObject(policyID);

                holder.tvOrganization.setText(obj.getString("usetype") + "/" +obj.getString("organization"));
            }catch (Exception e){
                e.printStackTrace();
                holder.tvOrganization.setText(policyID);
            }

            long expireUnixTime = mCertManager.getCertNotAfter(path);


            holder.tvExprie.setText("만료일 : " + getTimestampToDate(expireUnixTime));

            if (mMapCertSelect.get(path) != null && mMapCertSelect.get(path).equalsIgnoreCase("1")) {
                holder.rlMain.setBackgroundColor(Color.parseColor("#ebf7ff"));
            }else {
                holder.rlMain.setBackgroundColor(Color.parseColor("#f3f4f4"));

            }

            holder.rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectIndex = position;

                    for (String f :
                            mMapCertSelect.keySet()) {
                        mMapCertSelect.put(f, "0");
                    }

                    mMapCertSelect.put(path, "1");

                    mAdapter.notifyDataSetChanged();
                }
            });

        }


        @Override
        public int getItemCount() {
            /*if(mDataset.size()==0){
                Toast.makeText(CertListActivity.this, "인증서가 존재하지 않습니다", Toast.LENGTH_LONG).show();
            }*/
            return mDataset.size();
        }
    }

    private static String getTimestampToDate(long timestampStr){

        Date date = new java.util.Date(timestampStr*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy.MM.dd");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }


}
