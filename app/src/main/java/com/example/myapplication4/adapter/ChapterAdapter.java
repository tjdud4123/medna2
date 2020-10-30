package com.example.myapplication4.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.R;
import com.example.myapplication4.TeaDetailActivity;
import com.example.myapplication4.model.Chapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.CustomViewHolder> {
    
    private Context context;
    private ArrayList<Chapter> chapters;
    private LayoutInflater inflater;
    private Intent intent;
    
    public ChapterAdapter(Context context, ArrayList<Chapter> chapters){
        this.context = context;
        this.chapters = chapters;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override   // 레이아웃의 사용자 지정보기 반환
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.single_chapter, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);
        holder.tvChapterName.setText(chapter.chapterName);
        Picasso.get().load(chapter.imageUrl).into(holder.ivChapter);

        holder.ivChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), TeaDetailActivity.class);
                //intent.putExtra("",);
                v.getContext().startActivity(intent);
            }
        });

    }

    // 목록의 크기 반환
    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivChapter;
        public TextView tvChapterName;
        public CustomViewHolder(View itemview) {
            super(itemview);
            tvChapterName = (TextView) itemview.findViewById(R.id.tvChapterName);
            ivChapter = (ImageView) itemview.findViewById(R.id.ivChapter);
        }
    }
}
