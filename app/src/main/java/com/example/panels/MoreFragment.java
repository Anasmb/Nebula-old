package com.example.panels;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MoreFragment extends Fragment {

    LinearLayout newDeviceLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more,container,false);

        newDeviceLayout = view.findViewById(R.id.newDeviceLayout);
        newDeviceLayout.setOnClickListener(newDeviceLayoutListener);



        return  view;
    }

    View.OnClickListener newDeviceLayoutListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),BeforeConfigFragment.class);
                    startActivity(intent);
                }
            };


}
