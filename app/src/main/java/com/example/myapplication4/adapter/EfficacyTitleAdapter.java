package com.example.myapplication4.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.R;
import com.example.myapplication4.model.EfficacyTitle;

import java.util.ArrayList;

public class EfficacyTitleAdapter extends RecyclerView.Adapter<EfficacyTitleAdapter.ViewHolder> {

    public ArrayList<EfficacyTitle> efficacyTitles;
    private Context context;
    private LayoutInflater layoutInflater;

    public EfficacyTitleAdapter(ArrayList<EfficacyTitle> efficacyTitles, Context context){
        this.efficacyTitles = efficacyTitles;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_subject, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.recyclerView.setAdapter(new ChapterAdapter(context, efficacyTitles.get(position).efficacyTeas));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        holder.recyclerView.setHasFixedSize(true);
        holder.tvHeading.setText(efficacyTitles.get(position).efficacytitleName);
    }

    @Override
    public int getItemCount() {
        return efficacyTitles.size();
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
