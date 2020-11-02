package com.example.myapplication4.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.R;
import com.example.myapplication4.TeaDetailActivity;
import com.example.myapplication4.model.EfficacyTea;
import com.example.myapplication4.model.RecommendTea;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendTeaAdapter extends RecyclerView.Adapter<RecommendTeaAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<RecommendTea> recommendTeas;
    private LayoutInflater inflater;
    private Intent intent;

    public RecommendTeaAdapter(Context context, ArrayList<RecommendTea> recommendTeas){
        this.context = context;
        this.recommendTeas = recommendTeas;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecommendTeaAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.single_chapter, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendTeaAdapter.CustomViewHolder holder, int position) {
        RecommendTea recommendTea = recommendTeas.get(position);
        holder.tvChapterName.setText(recommendTea.recommendteaName);
        Picasso.get().load(recommendTea.recommendimageUrl).into(holder.ivChapter);

        holder.ivChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), TeaDetailActivity.class);
                //intent.putExtra("",);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivChapter;
        public TextView tvChapterName;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChapterName = (TextView) itemView.findViewById(R.id.tvChapterName);
            ivChapter = (ImageView) itemView.findViewById(R.id.ivChapter);
        }
    }
}
