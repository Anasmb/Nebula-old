package com.example.panels;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import com.example.panels.Adapter.PalleteAdapter;
import com.example.panels.Model.Pallete;
import com.example.panels.SharedPref.SharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private static final String LOG_TAG =  "HomeFragment";
    private FragmentHomeListener listener;
    private ImageButton emitButton; // connected with isOn
    private boolean isEmit;
    private DividerItemDecoration itemDecoration;
    private FloatingActionButton floatingActionButton;

    RecyclerView palleteList;
    PalleteAdapter adapter;
    ArrayList<Pallete> list=new ArrayList<>();
    Pallete deletedPallete = null;

    public interface FragmentHomeListener{ //USE THIS TO SEND DATA TO MainActivity
        void onInputHomeSent(CharSequence input, Pallete pallete);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

      //  emitButton = view.findViewById(R.id.emitButton);

        return view;
    }

    @Override
    public void onViewCreated ( View view ,  Bundle savedInstanceState) {
        super.onViewCreated(view , savedInstanceState);

        itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);

        list.clear();
        list.addAll(SharedPref.getPalleteList(getActivity()));

        emitButton = view.findViewById(R.id.emitButton);
        emitButton.setOnClickListener(emitButtonClickListener);
        isEmit = MainActivity.isOn;
        checkEmitStatus();
        floatingActionButton = view.findViewById(R.id.floatingAddPalleteButton);
        palleteList=view.findViewById(R.id.recyclerView);
        palleteList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new PalleteAdapter(list , getActivity() , new PalleteAdapter.OnNoteListener()
        {
            @Override
            public void OnNoteClick (int position) { //WHEN PALLETE LIST CLICKED THIS IS CALLED
                listener.onInputHomeSent("Pallete" , list.get(position)); //SEND TO MainActivity
            }
        });
        palleteList.setAdapter(adapter);
        palleteList.addItemDecoration(itemDecoration);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CreatePallete.class);
                startActivity(intent);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback); //FOR SWIPE DELETE
        itemTouchHelper.attachToRecyclerView(palleteList); //FOR SWIPE DELETE

        setStatusBarColor();

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.LEFT) { //WHEN THE USER SWIPE THE PALLETE THIS IS CALLED
        @Override
        public boolean onMove(@NonNull @org.jetbrains.annotations.NotNull RecyclerView recyclerView, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();
            switch (direction){
                
                case ItemTouchHelper.LEFT:
                //     String deletedName = list.get(position).getPalleteName(); // USE THIS FOR UNDO FUNCTIONALITY
                 //    deletedPallete = list.get(position);   // USE THIS FOR UNDO FUNCTIONALITY
                    list.remove(position);
                    adapter.notifyItemRemoved(position);
                    SharedPref.DeletePallete(getContext(), position);

                    //Snackbar will affect deletePallete SharedPref, NEED A FIX IF ENABLED
                  /*  Snackbar.make(palleteList, deletedName, Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {//LET THE USER UNDO DELETION IF HE DELETED THE PALLETE
                        @Override
                        public void onClick(View view) {
                            list.add(position,deletedPallete);
                            adapter.notifyItemInserted(position);

                        }
                    }).addCallback(new Snackbar.Callback(){

                        @Override
                        public void onShown(Snackbar sb) {
                            super.onShown(sb);
                        }

                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            Log.d("Debug", "SNACKBAR DISMISSED");
                            super.onDismissed(transientBottomBar, event);
                        }
                    }).show(); */

                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.background_color))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


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
    public void onDetach() {// THIS IS CALLED WHEN WE REMOVE THIS FRAGMENT FROM THE ACTIVITY
        super.onDetach();
        listener = null;
    }

    private void checkEmitStatus(){
        if (isEmit){
            emitButton.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.lighting_color));
        }
        else {
            emitButton.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.gray));
        }
    }

    View.OnClickListener emitButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isEmit = !isEmit;
            listener.onInputHomeSent("isOn",null);
            if (isEmit) {
                emitButton.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.lighting_color));
            } else {
                emitButton.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.gray));
            }
        }
    };



    private void setStatusBarColor(){
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.background_color));
    }


}
