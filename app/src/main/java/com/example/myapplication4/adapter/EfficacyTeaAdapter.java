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
import com.example.myapplication4.model.Chapter;
import com.example.myapplication4.model.EfficacyTea;
import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.ArrayList;

public class EfficacyTeaAdapter extends RecyclerView.Adapter<EfficacyTeaAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<EfficacyTea> efficacyTeas;
    private LayoutInflater inflater;
    private Intent intent;

    public EfficacyTeaAdapter(Context context, ArrayList<EfficacyTea> efficacyTeas){
        this.context = context;
        this.efficacyTeas = efficacyTeas;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.single_chapter, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EfficacyTeaAdapter.CustomViewHolder holder, int position) {
        EfficacyTea efficacyTea = efficacyTeas.get(position);
        holder.tvChapterName.setText(efficacyTea.efficacyteaName);
        Picasso.get().load(efficacyTea.efficacyimageUrl).into(holder.ivChapter);

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
        return efficacyTeas.size();
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
