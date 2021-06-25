package com.example.panels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity implements BeforeConfigFragment.FragmentBeforeConfigListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        fragmentSwitcher(getIntent().getStringExtra("fragmentName"));
    }

    public void fragmentSwitcher(String fragmentName){
        Fragment selectedFragment = null;

        switch (fragmentName) {
            case "existing":

                break;
            case "beforeconfig":
                selectedFragment = new BeforeConfigFragment();
                break;
            case "wificonfig":
              //  selectedFragment =  new WifiConfig();
                break;

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_empty, selectedFragment).commit();
    }


    @Override
    public void onInputBeforeConfigSent(CharSequence input) {
        if(input.equals("back")){
            onBackPressed();
        }
    }
}