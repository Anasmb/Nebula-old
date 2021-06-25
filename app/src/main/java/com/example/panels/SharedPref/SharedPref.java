package com.example.panels.SharedPref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.panels.Model.Pallete;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SharedPref
{



    public static void SavePalletesList(Context ac,ArrayList<Pallete> toSave)
    {
        SharedPreferences sharedPreferences = ac.getSharedPreferences("PalleteList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(toSave);
        editor.putString("PalleteList", json);
        editor.apply();
    }

    public static ArrayList<Pallete> getPalleteList(Context ac)
    {
        SharedPreferences sharedPreferences = ac.getSharedPreferences("PalleteList",  Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("PalleteList", null);
        Type type = new TypeToken<ArrayList<Pallete>>() {}.getType();
        ArrayList<Pallete> Pallete = gson.fromJson(json, type);
        if (Pallete == null)
        {
            Pallete = new ArrayList<>();
        }
        return Pallete;
    }





}
