package com.easy.fly.flyeasy.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.easy.fly.flyeasy.activities.HomeActivity;
import com.easy.fly.flyeasy.activities.LoginActivity;

import java.time.chrono.IsoChronology;
import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;

    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREFERENCE_NAME = "FlyApp";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_PASSWORD = "password";

    public static final String KEY_ID = "ID";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,PRIVATE_MODE);
        editor  = sharedPreferences.edit();
    }

    public void createLoginSessiong(String email,String password,long id){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PASSWORD,password);
        editor.putLong(KEY_ID,id);

        editor.commit();
    }

    public HashMap<String,String> getUserDeatails(){

        HashMap<String,String> user = new HashMap<>();

        user.put(KEY_EMAIL,sharedPreferences.getString(KEY_EMAIL,null));

        user.put(KEY_PASSWORD,sharedPreferences.getString(KEY_PASSWORD,null));

        user.put(KEY_ID,String.valueOf(sharedPreferences.getLong(KEY_ID,0)));

        return user;

    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent intent = new Intent(this.context, LoginActivity.class);

            //close all activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //add flag to start new activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            this.context.startActivity(intent);
        }else{
            Intent intent = new Intent(this.context, HomeActivity.class);
            this.context.startActivity(intent);
        }
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN,false);
    }

    public void logoutUser(){

        editor.clear();
        editor.commit();

        Intent intent = new Intent(this.context,LoginActivity.class);
        //close all activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //add flag to start new activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        this.context.startActivity(intent);
    }

}
