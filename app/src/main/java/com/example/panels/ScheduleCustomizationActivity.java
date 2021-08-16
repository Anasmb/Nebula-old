package com.example.panels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.panels.Adapter.CustomizeScheduleAdapter;
import com.example.panels.Model.Pallete;
import com.example.panels.SharedPref.SharedPref;

import java.util.ArrayList;

public class ScheduleCustomizationActivity extends AppCompatActivity {

    private static final String LOG_TAG =  "ScheduleCustomization";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<String> arrayList = new ArrayList<>();;
    private String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_customization);

      selected = getIntent().getExtras().getString("selected"); // receive Extras from previous activity

        checkSelected(selected);

        recyclerView = findViewById(R.id.customizedScheduleRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CustomizeScheduleAdapter(arrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);




    }

    private void checkSelected(String selected){
        switch (selected){
            case "pallete":
                putPalleteInList();
                break;
            case "mode":
                putModeInList();
                break;
            case "repeat":
                putRepeatInList();
                break;
        }
    }

    private void putPalleteInList(){

        for (int i = 0 ; i < SharedPref.getPalleteList(this).size() ; i++){
            arrayList.add(SharedPref.getPalleteList(this).get(i).getPalleteName());
            Log.d(LOG_TAG, "add to arrayList: " + SharedPref.getPalleteList(this).get(i).getPalleteName());
        }
      //  adapter.notifyDataSetChanged();
    }

    private void putModeInList(){
        arrayList.add("Fade");
        arrayList.add("Rainbow");
        arrayList.add("Static");
        arrayList.add("Split");
        arrayList.add("Up");
        arrayList.add("Down");
        arrayList.add("Right");
        arrayList.add("Left");
       // adapter.notifyDataSetChanged();
    }

    private void putRepeatInList(){
        arrayList.add("Once");
        arrayList.add("Daily");
        arrayList.add("Monday");
        arrayList.add("Tuesday");
        arrayList.add("Wednesday");
        arrayList.add("Thursday");
        arrayList.add("Friday");
        arrayList.add("Saturday");
        arrayList.add("Sunday");
     //   adapter.notifyDataSetChanged();
    }
}