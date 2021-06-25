package com.example.panels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.panels.Adapter.PalleteAdapter;
import com.example.panels.Model.Pallete;
import com.example.panels.SharedPref.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PalleteAdapter.OnNoteListener, HomeFragment.FragmentHomeListener, StartupFragment.FragmentStartupListener {

    HomeFragment homeFragment;

    private String IPESP32 ;
    private final int port  = 3636;
    private Thread Thread1 = null;
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    RecyclerView palleteList;
    PalleteAdapter adapter;
    ArrayList<Pallete> list=new ArrayList<>();

    @Override
    protected void onStart() {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        super.onStart();

        list.clear();
        list.addAll(SharedPref.getPalleteList(this));
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume () {
        super.onResume();
        list.clear();
        list.addAll(SharedPref.getPalleteList(this));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();

        IPESP32 = PrefConfig.loadIP(this);


        list.clear();
        list.addAll(SharedPref.getPalleteList(this));


        palleteList=findViewById(R.id.recyclerView);
        palleteList.setLayoutManager(new LinearLayoutManager(this));
        adapter=new PalleteAdapter(list,this ,this);
        palleteList.setAdapter(adapter);


        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        loadWifi();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_modify:
                    selectedFragment = new LayoutFragment();
                    break;
                case R.id.nav_more:
                    selectedFragment = new MoreFragment();
                    break;
                case R.id.nav_edit:
                    selectedFragment = new EditFragment();
                    break;
                case R.id.nav_schedule:
                    selectedFragment = new ScheduleFragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_empty,selectedFragment).commit();

            return true;
        }
    };

    public void fragmentSwitcher(String fragmentName){
        Fragment selectedFragment = null;

        switch (fragmentName) {
            case "startup":
                selectedFragment = new StartupFragment();
            break;
            case "edit":

            break;
            case "home":
                selectedFragment = new HomeFragment();
                break;

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_empty, selectedFragment).commit();
    }

    private void loadWifi(){
        if(PrefConfig.loadIP(this).equals("")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_empty, new StartupFragment()).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_empty, new HomeFragment()).commit();
            Thread1 = new Thread(new Thread1());
            Thread1.start();
        }
    }


    public void addPallete(View view){
        Intent intent = new Intent(this,CreatePallete.class);
        startActivity(intent);
    }

    @Override
    public void onInputHomeSent(CharSequence input) { //Receive data from the Home fragment

    }

    @Override
    public void onInputStartupSent(CharSequence input) { //Receive data from the Startup fragment
        if(input.equals("newBtn")){
            Intent intent = new Intent(this,EmptyActivity.class);
            intent.putExtra("fragmentName" , "beforeconfig");
            startActivity(intent);
        }
    }

    @Override
    public void OnNoteClick(int position) {

        int red = Color.red(list.get(position).getColor1());
        int green = Color.green(list.get(position).getColor1());
        int blue = Color.blue(list.get(position).getColor1());
        String sRed = String.format("%03d" , red);
        String sGreen = String.format("%03d" , green);
        String sBlue = String.format("%03d" , blue);
        new Thread(new Thread3("plt1" + " " + sRed + " " + sGreen + " " + sBlue)).start();
        sleep(150);

        red = Color.red(list.get(position).getColor2());
        green = Color.green(list.get(position).getColor2());
        blue = Color.blue(list.get(position).getColor2());
        sRed = String.format("%03d" , red);
        sGreen = String.format("%03d" , green);
         sBlue = String.format("%03d" , blue);
        new Thread(new Thread3("plt2" + " " + sRed + " " + sGreen + " " + sBlue)).start();
        sleep(150);

        red = Color.red(list.get(position).getColor3());
        green = Color.green(list.get(position).getColor3());
        blue = Color.blue(list.get(position).getColor3());
        sRed = String.format("%03d" , red);
        sGreen = String.format("%03d" , green);
        sBlue = String.format("%03d" , blue);
        new Thread(new Thread3("plt3" + " " + sRed + " " + sGreen + " " + sBlue)).start();
        sleep(150);

        red = Color.red(list.get(position).getColor4());
        green = Color.green(list.get(position).getColor4());
        blue = Color.blue(list.get(position).getColor4());
        sRed = String.format("%03d" , red);
        sGreen = String.format("%03d" , green);
        sBlue = String.format("%03d" , blue);
        new Thread(new Thread3("plt4" + " " + sRed + " " + sGreen + " " + sBlue)).start();
        sleep(150);


        new Thread(new Thread3("begin")).start();
        sleep(150);


    }

    public void sleep(int mill){
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    //______________________________________________________________________________________________________________________SOCKET SECTION
    class Thread1 implements Runnable {
        public void run() {
            try {
                socket = new Socket(IPESP32, port);
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(socket.isConnected())
                     Toast.makeText(getApplicationContext(),"Connected" , Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
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
    class Thread3 implements Runnable { //max data can send is 31
        private String message;
        Thread3(String message) {
            this.message = message;
        }
        @Override
        public void run() {
            output.write(message);
            output.flush();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  //  Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    }


