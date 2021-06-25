package com.example.panels;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StartupFragment extends Fragment {


    private FragmentStartupListener listener;
    private Button newbtn ;
    private Button existingBtn;

    public interface FragmentStartupListener{ //USE THIS TO SEND DATA TO ACTIVITY
        void onInputStartupSent(CharSequence input);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_startup,container,false);

        newbtn = view.findViewById(R.id.newBtn);
        existingBtn = view.findViewById(R.id.existingBtn);

        newbtn.setOnClickListener(buttonListener);
        existingBtn.setOnClickListener(buttonListener);


        return view;
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getTag().toString()){
                case "newBtn":
                    listener.onInputStartupSent("newBtn");
                    break;

            }

        }
    };

    public void onAttach(@NonNull Context context) { //THIS IS USED WHEN IF THE ACTIVITY ATTACH THE INTERFACE
        super.onAttach(context);
        if(context instanceof StartupFragment.FragmentStartupListener){
            listener = (StartupFragment.FragmentStartupListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " Must implement FragmentStartupListener");
        }
    }

    @Override
    public void onDetach() {// THIS IS CALLED WHEN WE REMOVE THIS FRAGMENT FROM THE ACITVITY
        super.onDetach();
        listener = null;
    }




}
