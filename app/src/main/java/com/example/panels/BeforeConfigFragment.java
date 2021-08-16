package com.example.panels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
import android.widget.ImageView;


public class BeforeConfigFragment extends FragmentActivity {

    private BeforeConfigFragment.FragmentBeforeConfigListener listener;
    private ImageView backbtn;
    private Button nxtbtn;
    private ImageView routerImg;
    private ImageView wifiImg;
    WifiManager wifiManager ;
    String esp32WifiName = "Esp32" ;     //TODO CHANGE

    public interface FragmentBeforeConfigListener{ //USE THIS TO SEND DATA TO ACTIVITY
        void onInputBeforeConfigSent(CharSequence input);
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_before_config);

        nxtbtn = findViewById(R.id.nextBtn); //FIXME COLOR CHANGE WHEN THE DEVICE IS CONNECTED TO ESP32
        backbtn = findViewById(R.id.back_img_button);
        routerImg = findViewById(R.id.routerImg);
        wifiImg = findViewById(R.id.wifiImg);

        routerImg.setColorFilter(getResources().getColor(R.color.foreground_color));
        wifiImg.setColorFilter(getResources().getColor(R.color.foreground_color));
        backbtn.setColorFilter(getResources().getColor(R.color.back_button_color));

        nxtbtn.setOnClickListener(buttonListener);
        backbtn.setOnClickListener(buttonListener);

        checkIPAddress();
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getTag().toString()){
                case "next":
                    openNextFragment();
                    break;
                case "previous":
                    finish();
                    break;

            }

        }
    };

    public void openNextFragment(){ //TODO uncomment below to make sure device is connected to esp32 wifi
        //if(checkIPAddress()) {
       //     getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_empty, new WifiConfig()).commit();
            Intent intent = new Intent(this,WifiConfig.class);
            startActivity(intent);
            //Intent configIntent = new Intent(this, WifiConfig.class);
          //  startActivity(configIntent);
            //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
          //  finish();
       // }
       // else Toast.makeText(getActivity().getApplicationContext(),"Connect To Nebula To Continue", Toast.LENGTH_LONG).show();
    }


    private boolean checkIPAddress(){
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
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
//        super.onAttach(context);
//        if(context instanceof BeforeConfigFragment.FragmentBeforeConfigListener){
//            listener = (BeforeConfigFragment.FragmentBeforeConfigListener) context;
//        }
//        else {
//            throw new RuntimeException(context.toString() + " Must implement FragmentBeforeConfigListener");
//        }
    }


}