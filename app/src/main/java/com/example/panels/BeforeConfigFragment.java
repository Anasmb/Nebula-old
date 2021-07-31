package com.example.panels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class BeforeConfigFragment extends Fragment {

    private BeforeConfigFragment.FragmentBeforeConfigListener listener;
    MainActivity mainActivity;
    private ImageButton backbtn;
    private Button nxtbtn;
    WifiManager wifiManager ;
    String esp32WifiName = "Esp32" ;     //TODO CHANGE

    public interface FragmentBeforeConfigListener{ //USE THIS TO SEND DATA TO ACTIVITY
        void onInputBeforeConfigSent(CharSequence input);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_before_config,container,false);

        mainActivity = new MainActivity();
        
        nxtbtn = view.findViewById(R.id.nextBtn);
        backbtn = view.findViewById(R.id.backBtn);

        nxtbtn.setOnClickListener(buttonListener);
        backbtn.setOnClickListener(buttonListener);

        checkIPAddress();
        
        return  view;
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getTag().toString()){
                case "nextBtn":
                    openNextFragment();
                    break;
                case "backBtn":
                    openPreviousFragment();
                    break;

            }

        }
    };

    public void openNextFragment(){
        //if(checkIPAddress()) {
       //     getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_empty, new WifiConfig()).commit();
            Intent intent = new Intent(getActivity(),WifiConfig.class);
            startActivity(intent);
            //Intent configIntent = new Intent(this, WifiConfig.class);
          //  startActivity(configIntent);
            //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
          //  finish();
       // }
       // else Toast.makeText(getActivity().getApplicationContext(),"Connect To Nebula To Continue", Toast.LENGTH_LONG).show();
    }

    private void openPreviousFragment(){
        listener.onInputBeforeConfigSent("back");
    }


    private boolean checkIPAddress(){
        wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        int ipNebula = dhcpInfo.gateway;
        String ipString = String.format("%d.%d.%d.%d", (ipNebula & 0xff), (ipNebula >> 8 & 0xff), (ipNebula >> 16 & 0xff), (ipNebula >> 24 & 0xff));
        if(ipString.equals("192.168.4.1")){
            nxtbtn.setBackgroundColor(getResources().getColor(R.color.purple_200));
            return true;
        }
        nxtbtn.setBackgroundColor(getResources().getColor(R.color.Off_Color));
        return false;
    }

    public void onAttach(@NonNull Context context) { //THIS IS USED WHEN IF THE ACTIVITY ATTACH THE INTERFACE
        super.onAttach(context);
        if(context instanceof BeforeConfigFragment.FragmentBeforeConfigListener){
            listener = (BeforeConfigFragment.FragmentBeforeConfigListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " Must implement FragmentBeforeConfigListener");
        }
    }

    @Override
    public void onDetach() {// THIS IS CALLED WHEN WE REMOVE THIS FRAGMENT FROM THE ACITVITY
        super.onDetach();
        listener = null;
    }

}