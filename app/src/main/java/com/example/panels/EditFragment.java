package com.example.panels;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

public class EditFragment extends Fragment {

    private EditFragment.FragmentEditListener listener;
    private final String LOG_TAG =  "EditFragment";
    private Slider brightnessSlider;
    private Slider speedSlider;

    public interface FragmentEditListener{ //USE THIS TO SEND DATA TO MainActivity
        void onInputEditSent(CharSequence input);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_edit,container,false);

        setStatusBarColor();

        brightnessSlider = view.findViewById(R.id.editBrightnessSlider);
        brightnessSlider.addOnSliderTouchListener(brightnessSliderTouchListener);
        brightnessSlider.setValue(MainActivity.brightnessValue);
        speedSlider = view.findViewById(R.id.speedSlider);
        speedSlider.addOnSliderTouchListener(speedSliderTouchListener);
        brightnessSlider.setValue(MainActivity.brightnessValue);

        return view;
    }

    public void onAttach(@NonNull Context context) { //THIS IS USED WHEN IF THE ACTIVITY ATTACH THE INTERFACE
        super.onAttach(context);
        if(context instanceof EditFragment.FragmentEditListener){
            listener = (EditFragment.FragmentEditListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " Must implement FragmentEditListener");
        }
    }

    private void setStatusBarColor(){
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.background_color));
    }

    Slider.OnSliderTouchListener speedSliderTouchListener = new Slider.OnSliderTouchListener() {
        @Override
        public void onStartTrackingTouch(@NonNull @NotNull Slider slider) {

        }

        @Override
        public void onStopTrackingTouch(@NonNull @NotNull Slider slider) {
            Log.d(LOG_TAG, "Speed slider value = " + slider.getValue());
            MainActivity.speedValue = (int) slider.getValue();
            listener.onInputEditSent("spd ");
        }
    };

    Slider.OnSliderTouchListener brightnessSliderTouchListener = new Slider.OnSliderTouchListener() {
        @Override
        public void onStartTrackingTouch(@NonNull @NotNull Slider slider) {

        }

        @Override
        public void onStopTrackingTouch(@NonNull @NotNull Slider slider) {
            Log.d(LOG_TAG, "Brightness slider value = " + slider.getValue());
            MainActivity.brightnessValue = (int) slider.getValue();
            listener.onInputEditSent("bn ");
        }
    };
}
