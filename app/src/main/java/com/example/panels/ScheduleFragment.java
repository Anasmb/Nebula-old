package com.example.panels;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panels.Adapter.ScheduleAdapter;
import com.example.panels.Model.Schedule;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.ArrayList;

public class ScheduleFragment extends Fragment {

    private RecyclerView scheduleRecyclerView;
    private RecyclerView.Adapter scheduleAdapter;
    private  RecyclerView.LayoutManager layoutManager;
    private DividerItemDecoration itemDecoration;
    private FloatingActionButton floatingActionButton;
    ArrayList<Schedule> scheduleArrayList ;
    Schedule schedule;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_schedule,container,false);


        //scheduleRecyclerView = getActivity().findViewById(R.id.scheduleRecyclerView);
        //scheduleRecyclerView.setHasFixedSize(true);
       // layoutManager = new LinearLayoutManager(getActivity());
      //  scheduleAdapter = new ScheduleAdapter(scheduleArrayList);

       // scheduleRecyclerView.setLayoutManager(layoutManager);
        //scheduleRecyclerView.setAdapter(scheduleAdapter);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scheduleArrayList = new ArrayList<>();
        itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);

        schedule = new Schedule("5:25" , "am" , "Sat" , "Sun" , "Mon" , "" , "" , "" , false);
        scheduleArrayList.add(schedule);
        schedule = new Schedule("9:00" , "pm" , "always" , "" , "" , "" , "" , "" , false);
        scheduleArrayList.add(schedule);
        schedule = new Schedule("12:00" , "am" , "Fri" , "" , "" , "" , "" , "" , false);
        scheduleArrayList.add(schedule);
        schedule = new Schedule("6:55" , "am" , "Sun" , "Mon" , "Tue" , "Wed" , "Thu" , "Fri" , false);
        scheduleArrayList.add(schedule);


        scheduleRecyclerView = view.findViewById(R.id.scheduleRecyclerView);
        floatingActionButton = view.findViewById(R.id.floatingAddScheduleButton);
        scheduleRecyclerView.addItemDecoration(itemDecoration);
        layoutManager = new LinearLayoutManager(getActivity());
        scheduleAdapter = new ScheduleAdapter(scheduleArrayList);

        scheduleRecyclerView.setLayoutManager(layoutManager);
        scheduleRecyclerView.setAdapter(scheduleAdapter);

        floatingActionButton.setOnClickListener(fablistener);

    }

    View.OnClickListener fablistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(),SetScheduleFragment.class);
            startActivity(intent);
        }
    };


}
