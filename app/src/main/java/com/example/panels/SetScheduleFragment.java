package com.example.panels;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SetScheduleFragment extends FragmentActivity {

    private Slider brightnessSlider;
    private SwitchMaterial schedulePowerSwitch;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_schedule);

        brightnessSlider = findViewById(R.id.scheduleBrightnessSlider);
        schedulePowerSwitch = findViewById(R.id.scheduleSwitchBtn);

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


}
