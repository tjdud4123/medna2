package com.example.myapplication4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.R;
import com.example.myapplication4.TeaRecommendActivity;
import com.example.myapplication4.model.RecommendTitle;
import com.example.myapplication4.model.Subject;

import java.security.AccessControlContext;
import java.util.List;

public class RecommendTitleAdapter extends RecyclerView.Adapter<RecommendTitleAdapter.ViewHolder> {

    public List<RecommendTitle> recommendTitles;
    private Context context;
    private LayoutInflater layoutInflater;

    public RecommendTitleAdapter(List<RecommendTitle> recommendTitles, TeaRecommendActivity context){
        this.recommendTitles = recommendTitles;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecommendTitleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_subject, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendTitleAdapter.ViewHolder holder, int position) {
        holder.recyclerView.setAdapter(new RecommendTeaAdapter(context, recommendTitles.get(position).recommendTeas));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        holder.recyclerView.setHasFixedSize(true);
        holder.tvHeading.setText(recommendTitles.get(position).recommendtitleName);

    }

    @Override
    public int getItemCount() {
        return recommendTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView tvHeading;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rvChapters);
            tvHeading = (TextView) itemView.findViewById(R.id.tvSubjectName);
        }
    }
}
