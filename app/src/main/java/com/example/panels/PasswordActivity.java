package com.example.panels;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PasswordActivity extends FragmentActivity {

    InputMethodManager inputMethodManager;
    private WifiManager wifiManager;
    private TextView ssidtxt;
    private TextView readyTxt;
    private EditText pswd;
    private ProgressBar progressBar;
    private ImageView pswdBackground;
    private ImageView panelImage;
    private Button connectBtn;
    private Button doneButton;
    private ImageView backBtn;
    private Thread Thread1 = null;
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private String ssidName;
    private String ssidPswd;
    private String localAddress;
    private String SERVER_IP;
    private int SERVER_PORT;
    private CountDownTimer countDownTimer;
    private boolean nebulaConnected = false;
    private boolean connectionFailed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        progressBar = findViewById(R.id.connectingProgress);
        progressBar.setVisibility(View.INVISIBLE);
        ssidtxt = findViewById(R.id.ssidText);
        readyTxt = findViewById(R.id.readyToUseTxt);
        pswd = findViewById(R.id.pswdEditTxt);
        panelImage = findViewById(R.id.panelImage);
        connectBtn = findViewById(R.id.connectbtn);
        doneButton = findViewById(R.id.doneBtn);
        backBtn = findViewById(R.id.backBtnPass);
        backBtn.setColorFilter(getResources().getColor(R.color.back_button_color));
        ssidName = getIntent().getStringExtra("ssid");
        SERVER_IP = "192.168.4.1";
        SERVER_PORT = 3636;
        ssidtxt.setText(ssidName);
        connectBtn.setClickable(false);

        UIUtil.showKeyboard(PasswordActivity.this,pswd);

        countDownTimer = new CountDownTimer(10000 , 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                if(!nebulaConnected){
                    connectionFailed = true;
                    backBtn.setClickable(true);
                    Toast.makeText(getApplicationContext(), "Unable to connect to the network",Toast.LENGTH_SHORT).show();
                    connectBtn.setClickable(true);
                    progressBar.setVisibility(View.INVISIBLE);
                    connectBtn.setBackgroundColor(getResources().getColor(R.color.purple_200));
                    pswd.setEnabled(true);
                }
            }
        };

        pswd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 31){
                    Toast.makeText(getApplicationContext(), "Nebula can't support more than 31 Characters of password :(",Toast.LENGTH_SHORT).show();
                    connectBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    connectBtn.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 7){
                    connectBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_color)));
                    connectBtn.setClickable(true);
                    ssidPswd = editable.toString();
                }
                else if(editable.length() < 8){
                    connectBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    connectBtn.setClickable(false);
                }
            }
        });
    }

    public void manageWifiInfo(View view){
        if (checkConnectedWifi() && !connectionFailed){
            setUIOff();
            Thread1 = new Thread(new Thread1());
            Thread1.start();
            sendSSIDAndPass();
        }
        else if(connectionFailed){
            sendSSIDAndPass();
        }
        else openBeforeConfig();
    }

    private void goSleep(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setUIOff(){
        connectBtn.setClickable(false);
        pswd.setEnabled(false);
        backBtn.setClickable(false);
        connectBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
        UIUtil.hideKeyboard(PasswordActivity.this);
    }

    private void completedSetup(){
        backBtn.setVisibility(View.INVISIBLE);
        connectBtn.setVisibility(View.INVISIBLE);
        pswd.setVisibility(View.INVISIBLE);
        readyTxt.setVisibility(View.VISIBLE);
        doneButton.setVisibility(View.VISIBLE);
        panelImage.setVisibility(View.VISIBLE);
        ssidtxt.setText("Setup Complete");
    }

    private void sendSSIDAndPass(){
         setUIOff();
        goSleep(100);
        new Thread(new Thread3("ssid " + ssidName)).start();
        goSleep(100);
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Thread3("pswd " + ssidPswd)).start();
        goSleep(100);
        new Thread(new Thread3("connect")).start();
        countDownTimer.start();
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

    public void openBeforeConfig(){
        Toast.makeText(getApplicationContext(),"Connect To Nebula To Continue", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, BeforeConfigFragment.class);
        startActivity(intent);
        slideRight();
    }

    public void openPrevious(View view){
        Intent intent = new Intent(this, WifiConfig.class);
        startActivity(intent);
        slideRight();
    }

    public void slideRight(){
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void openMainActivity(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void checkReceivedData(String data) throws IOException {
        if(data.startsWith("Connected")){
            String ip = data.substring(10);
            PrefConfig.saveIP(getApplicationContext(),ip);
            Log.d("AndroidExample","Nebula IP address in local network is: " + ip);
            nebulaConnected = true;
            localAddress = data;
            progressBar.setVisibility(View.INVISIBLE);
        }
        else if(data.equals("finish")){
            completedSetup();
            socket.close();
        }
    }

    class Thread1 implements Runnable {
        public void run() {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      //  Toast.makeText(PasswordActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                    }
                });
                new Thread(new Thread2()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    class Thread2 implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    final String message = input.readLine();
                    if (message != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    checkReceivedData(message);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                              //  Toast.makeText(PasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Thread1 = new Thread(new Thread1());
                        Thread1.start();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Thread3 implements Runnable {
        private String message;

        Thread3(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            output.write(message);
            output.flush();
            SystemClock.sleep(1000);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }


}