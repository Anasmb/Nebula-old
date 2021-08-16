package com.example.panels;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.panels.Adapter.CustomizeScheduleAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SetScheduleFragment extends AppCompatActivity  {

    private static final String LOG_TAG =  "SetScheduleFragment";
    private Slider brightnessSlider;
    private SwitchMaterial schedulePowerSwitch;
    private TimePicker timePicker;

    private LinearLayout palleteLinearLayout;
    private LinearLayout modeLinearLayout;
    private LinearLayout repeatLinearLayout;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_schedule);

        brightnessSlider = findViewById(R.id.scheduleBrightnessSlider);
        schedulePowerSwitch = findViewById(R.id.scheduleSwitchBtn);
        palleteLinearLayout = findViewById(R.id.schedule_pallete_layout);
        modeLinearLayout = findViewById(R.id.schedule_mode_layout);
        repeatLinearLayout = findViewById(R.id.schedule_repeat_layout);

        palleteLinearLayout.setOnClickListener(palleteLayoutListener);
        modeLinearLayout.setOnClickListener(modeLayoutListener);
        repeatLinearLayout.setOnClickListener(repeatLayoutListener);

        schedulePowerSwitch.setOnCheckedChangeListener(switchListener);
    }

    CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if(b) {
                  schedulePowerSwitch.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.thumb_color_on)));
                  schedulePowerSwitch.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.track_color_on)));
                  brightnessSlider.setEnabled(true);
              }
              else if(!b){
                  schedulePowerSwitch.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.thumb_color_off)));
                  schedulePowerSwitch.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.track_color_off)));
                  brightnessSlider.setEnabled(false);
                  brightnessSlider.setValue(0);
              }
        }
    };

    View.OnClickListener palleteLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(),ScheduleCustomizationActivity.class);
            intent.putExtra("selected" , "pallete");
            startActivity(intent);
        }
    };

    View.OnClickListener modeLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(),ScheduleCustomizationActivity.class);
            intent.putExtra("selected" , "mode");
            startActivity(intent);
        }
    };

    View.OnClickListener repeatLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(),ScheduleCustomizationActivity.class);
            intent.putExtra("selected" , "repeat");
            startActivity(intent);
        }
    };

}
