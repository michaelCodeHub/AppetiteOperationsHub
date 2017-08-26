package com.appetite.appetiteoperationsapp;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class App extends Application
{
    public static SharedPreferences shared;
    public static SharedPreferences.Editor editor;

    public static ArrayList<String> categories = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        shared=getSharedPreferences("appetiteapp", MODE_PRIVATE);
        editor=shared.edit();
    }
}
