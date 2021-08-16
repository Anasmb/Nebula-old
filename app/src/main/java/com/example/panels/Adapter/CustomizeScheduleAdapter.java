package com.example.panels.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panels.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CustomizeScheduleAdapter extends RecyclerView.Adapter<CustomizeScheduleAdapter.CutomizeScheduleHolder> {

    private ArrayList<String> arrayList;

    public CustomizeScheduleAdapter(ArrayList<String> arrayList){
            this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public CutomizeScheduleHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customschedulerow, parent, false);
        CutomizeScheduleHolder cutomizeScheduleHolder = new CutomizeScheduleHolder(view);
        return  cutomizeScheduleHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CutomizeScheduleHolder holder, int position) {
            String text = arrayList.get(position);
            holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class CutomizeScheduleHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public CutomizeScheduleHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.custom_text);
        }
    }
}
