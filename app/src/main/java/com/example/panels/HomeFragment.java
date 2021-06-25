package com.example.panels;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panels.Adapter.PalleteAdapter;
import com.example.panels.Model.Pallete;
import com.example.panels.SharedPref.SharedPref;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeListener listener;
    private ImageButton emitButton; // connected with isOn
    private boolean isOn = true;

    public interface FragmentHomeListener{ //USE THIS TO SEND DATA TO ACTIVITY
        void onInputHomeSent(CharSequence input);
    }

    RecyclerView palleteList;
    PalleteAdapter adapter;
    ArrayList<Pallete> list=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        emitButton = view.findViewById(R.id.emitButton);




        return view;
    }

    @Override
    public void onViewCreated ( View view ,  Bundle savedInstanceState) {
        super.onViewCreated(view , savedInstanceState);

        list.clear();
        list.addAll(SharedPref.getPalleteList(getActivity()));


        palleteList=view.findViewById(R.id.recyclerView);
        palleteList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new PalleteAdapter(list , getActivity() , new PalleteAdapter.OnNoteListener()
        {
            @Override
            public void OnNoteClick (int position) {

            }
        });
        palleteList.setAdapter(adapter);
    }


    @Override
    public void setMenuVisibility (boolean menuVisible) {


        if(menuVisible)
        {
            list.clear();
            list.addAll(SharedPref.getPalleteList(getActivity()));
            adapter.notifyDataSetChanged();
        }
        super.setMenuVisibility(menuVisible);
    }



    public void receiveFromActivity (){ //THIS IS USED TO RECEIVE DATA FROM ACTIVITY

    }

    @Override
    public void onAttach(@NonNull Context context) { //THIS IS USED WHEN IF THE ACTIVITY ATTACH THE INTERFACE
        super.onAttach(context);
        if(context instanceof FragmentHomeListener){
            listener = (FragmentHomeListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " Must implement FragmentHomeListener");
        }
    }

    @Override
    public void onResume () {
        super.onResume();
        list.clear();
        list.addAll(SharedPref.getPalleteList(getActivity()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {// THIS IS CALLED WHEN WE REMOVE THIS FRAGMENT FROM THE ACITVITY
        super.onDetach();
        listener = null;
    }

    public void setEmitButton(View view) {
        isOn = !isOn;
                if (isOn) {
                    emitButton.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.emitColor));
                    //new Thread(new MainActivity.Thread3("all on")).start();
                } else {
                    emitButton.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.defult_8));
                    // new Thread(new MainActivity.Thread3("all off")).start();
                }

    }


}
