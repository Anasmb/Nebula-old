package com.example.panels;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class WifiConfig extends FragmentActivity {
 
    private static final String LOG_TAG =  "WifiConfig";
    private static final int MY_REQUEST_CODE = 123;
    private WifiManager wifiManager;
    LocationManager locationManager;
    private ListView listView;
    private List<ScanResult> results;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter adapter;
    private ProgressBar progress_circular;
    private CountDownTimer timer;
    private ImageView backbtn;
    private String ssidName;


    static  long REFRESHTIME = 10000;  // In Millis 10000 milis  = 10 Seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_config);

        checkLocationStatus();

        backbtn = findViewById(R.id.back_img_button);
        backbtn.setColorFilter(getResources().getColor(R.color.back_button_color));
        backbtn.setOnClickListener(backImgClickListener);
        progress_circular=findViewById(R.id.progress_circular);
        progress_circular.setVisibility(View.GONE);
        listView = findViewById(R.id.wifiList);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, arrayList);
        listView.setAdapter(adapter);
        askAndStartScanWifi();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // if(checkConnectedWifi()) {
                    ssidName = (String) adapterView.getItemAtPosition(i);
                    openNext();
           //     }
            //    else{
               //     Toast.makeText(getApplicationContext(),"Connect To Nebula To Continue", Toast.LENGTH_LONG).show(); TODO MAKE IT SNACKBAR
               //     openPrevious(view);
              //  }
            }
        });

        timer = new CountDownTimer( REFRESHTIME , REFRESHTIME )
        {
            @Override
            public void onTick(long l)
            {
                refresh();
            }

            @Override
            public void onFinish() {
                timer.start();
            }
        }.start();
    }

    private void checkLocationStatus() { // wifi scan only work if the location is on
         locationManager = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE); //FIXME toast displaying even if the location is enabled
        if(locationManager.isProviderEnabled(LOCATION_SERVICE)){
            Log.d(LOG_TAG, "Location Enabled");
        }
        else if(!(locationManager.isProviderEnabled(LOCATION_SERVICE))) {
            Log.d(LOG_TAG, "Location Disabled");
          //  Toast.makeText(this, "Enable location to scan Wi-Fi", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean checkConnectedWifi(){
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        int ipNebula = dhcpInfo.gateway;
        String ipString = String.format("%d.%d.%d.%d", (ipNebula & 0xff), (ipNebula >> 8 & 0xff), (ipNebula >> 16 & 0xff), (ipNebula >> 24 & 0xff));
        if(ipString.equals("192.168.4.1")){
            return true;
        }
        return false;
    }

    View.OnClickListener backImgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openPrevious();
        }
    };

    public void openPrevious(){
        onBackPressed();
      //  Fragment fragment = new BeforeConfigFragment();
      //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_empty,fragment).commit();
    }

    /*public void finish() {
        super.finish();
        slideRight();
    } */

    public void openNext(){
        Intent configIntent = new Intent(this, PasswordActivity.class);
        configIntent.putExtra("ssid","" + ssidName);
        startActivity(configIntent);
        finish();
    }

    private void refresh()
    {
        askAndStartScanWifi();
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void scanWifi() {
        arrayList.clear();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();

    }
    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            progress_circular.setVisibility(View.GONE);
            arrayList.clear();
            results = wifiManager.getScanResults();
            unregisterReceiver(this);
            for(ScanResult scanResult : results)
            {
                if(!TextUtils.isEmpty(scanResult.SSID)  && notInList(scanResult.SSID))
                {
                    if(!scanResult.SSID.equals("Esp32")) { //TODO change esp32 name and make it dynamic to the product name
                        arrayList.add(scanResult.SSID);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }

    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private boolean notInList(String ssid)
    {
        for(String val:arrayList)
        {
            if(val.equals(ssid))
                return false;
        }
        return true;
    }

    private void askAndStartScanWifi()  {
        // With Android Level >= 23, you have to ask the user
        // for permission to Call.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // 23
            int permission1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

            // Check for permissions
            if (permission1 != PackageManager.PERMISSION_GRANTED) {
                Log.d(LOG_TAG, "Requesting Permissions");
                // Request permissions
                ActivityCompat.requestPermissions(this,
                        new String[] {
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_WIFI_STATE,
                              //  Manifest.permission.ACCESS_NETWORK_STATE
                        }, MY_REQUEST_CODE);
                scanWifi();
                return;
            }
            scanWifi();
            Log.d(LOG_TAG, "Permissions Already Granted");
            checkLocationStatus();
        }
    }

}