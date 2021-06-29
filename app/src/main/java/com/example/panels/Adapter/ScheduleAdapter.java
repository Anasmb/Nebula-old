package com.example.panels.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panels.Model.Schedule;
import com.example.panels.R;
import com.example.panels.ScheduleFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>{

    private ArrayList<Schedule> scheduleArrayList;


    public ScheduleAdapter(ArrayList<Schedule> scheduleList) {
        scheduleArrayList = scheduleList;
    }

    @NonNull
    @NotNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedulelist, parent, false);
        ScheduleViewHolder scheduleViewHolder = new ScheduleViewHolder(view);
        return scheduleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ScheduleViewHolder holder, int position) {
        Schedule currentSchedule = scheduleArrayList.get(position);

        holder.time.setText(currentSchedule.getTime());
        holder.meridiem.setText(currentSchedule.getMeridiem());
        holder.day1.setText(currentSchedule.getDay1());
        holder.day2.setText(currentSchedule.getDay2());
    }

    @Override
    public int getItemCount() {
        return scheduleArrayList.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder{ //INNER CLASS

        public TextView time;
        public TextView meridiem;
        public TextView day1;
        public TextView day2;
        public TextView day3;
        public TextView day4;
        public TextView day5;
        public TextView day6;
        public SwitchMaterial switchMaterial;


        public ScheduleViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.txtTime);
            meridiem = itemView.findViewById(R.id.meridiemTxt);
            day1 = itemView.findViewById(R.id.dayTxt1);
            day2 = itemView.findViewById(R.id.dayTxt2);
            day3 = itemView.findViewById(R.id.dayTxt3);
            day4 = itemView.findViewById(R.id.dayTxt4);
            day5 = itemView.findViewById(R.id.dayTxt5);
            day6 = itemView.findViewById(R.id.dayTxt6);
            switchMaterial = itemView.findViewById(R.id.swichBtn);

        }
    }

}
