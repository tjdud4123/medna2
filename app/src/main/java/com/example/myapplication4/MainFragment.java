package com.example.myapplication4;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.adapter.EfficacyTeaAdapter;
import com.example.myapplication4.adapter.EfficacyTitleAdapter;
import com.example.myapplication4.adapter.SubjectAdapter;
import com.example.myapplication4.api.RetrofitClient;
import com.example.myapplication4.model.Chapter;
import com.example.myapplication4.model.EfficacyTitle;
import com.example.myapplication4.model.Subject;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment {

    //teagroup
    private RecyclerView rvSubject;
    private SubjectAdapter subjectAdapter;
    private List<Subject> subjects;
    List<TeaGetByResponse> teaGetbyResponsesList = new ArrayList<>();
    
    //teaefficacy
    private RecyclerView rvEfficacy;
    private EfficacyTitleAdapter efficacyTitleAdapter;
    private List<EfficacyTitle> efficacyTitles;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main,container,false);

        TabHost tabHost2 = (TabHost) v.findViewById(R.id.tabHost2);
        tabHost2.setup();

        // 그룹별 티캡슐
        TabHost.TabSpec ts1 = tabHost2.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.group);
        ts1.setIndicator("카테고리별 티캡슐");
        tabHost2.addTab(ts1);

        initComponents(v);
        // prepareData() 메서드 만들고 목록 호출
        subjects = prepareData();

        //rvSubject = findViewById(R.id.rvChapters);

        subjectAdapter = new SubjectAdapter(subjects, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvSubject.setLayoutManager(manager);
        rvSubject.setAdapter(subjectAdapter);

        ImageView imageUrl = (ImageView) v.findViewById(R.id.ivChapter);
        TextView teaName = (TextView) v.findViewById(R.id.tvChapterName);
        TextView category = (TextView) v.findViewById(R.id.tvSubjectName);

        //TeaGetResponse teagroupInfo = new TeaGetResponse();


        // 효능별 티캡슐
        TabHost.TabSpec ts2 = tabHost2.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.efficacy);
        ts2.setIndicator("건강상태별 티캡슐");
        tabHost2.addTab(ts2);

        return v;
    }

    private void initComponents(View v) {
        rvSubject = v.findViewById(R.id.rvSubject);
    }

    @Getter
    @NoArgsConstructor
    public static class TeaGetResponse{
        String imageUrl;
        String teaName;
        Long teaId;
    }

    @Getter
    @NoArgsConstructor
    public static class TeaGetByResponse{
        String category;
        List<TeaGetResponse> teaList = new ArrayList<>();
    }

    public List<Subject> prepareData(){

        Call<List<TeaGetByResponse>> call = RetrofitClient.getInstance().getApi().teagroupInfo();
        call.enqueue(new Callback<List<TeaGetByResponse>>() {
            @Override
            public void onResponse(Call<List<TeaGetByResponse>> call, Response<List<TeaGetByResponse>> response) {
                //Log.i("{}", response.body().get(0).teaName);
                if(response.isSuccessful()){
                    teaGetbyResponsesList = response.body();
                    if(teaGetbyResponsesList != null){
                        //List<TeaGetResponse> teaGetResponses = teaGetbyResponsesList.


                    }

                }
            }
            @Override
            public void onFailure(Call<List<TeaGetByResponse>> call, Throwable t) {
                Log.i("sdfsdfsdf{}", t.getMessage());
            }
        });

        List<Subject> subjects = new ArrayList<>();
        List<TeaGetByResponse> r = new ArrayList<>();  // 요청받은거 보여주기

        for(TeaGetByResponse response : r){     //
            Subject subject = new Subject();

            subject.id = 1;
            subject.subjectName = response.getCategory();
            subject.chapters = new ArrayList<>();

            for(TeaGetResponse teaGetResponse : response.teaList){
                Chapter chapter = new Chapter();
                chapter.id = teaGetResponse.teaId;
                chapter.chapterName = teaGetResponse.teaName;
                chapter.imageUrl = teaGetResponse.getImageUrl();

                subject.chapters.add(chapter);
            }

            subjects.add(subject);
        }

        return subjects;

    }


    // 효능별 티캡슐

    private void initComponents2(View v) {
        rvEfficacy = v.findViewById(R.id.rvEfficacy);
    }

}
