package com.easy.fly.flyeasy.utils;

import android.util.Base64;

import com.easy.fly.flyeasy.common.SessionManager;

import java.util.HashMap;

public class UserUtil {

    public static String getUserAthenticationHeader(HashMap<String,String> userDetails){

        String email = userDetails.get(SessionManager.KEY_EMAIL);

        String password = userDetails.get(SessionManager.KEY_PASSWORD);

        String base = email + ":" + password;

        return "Basic " + Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
    }
}
