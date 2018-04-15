package com.easy.fly.flyeasy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormater {

    public static String formatDate(String date){
        if(!date.equals("")){
            SimpleDateFormat dateFormat = new SimpleDateFormat( "MMM dd, yyyy");

            Date newDate = null;
            try {
                newDate =  dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat requiredDateFormat = new SimpleDateFormat("dd-MM-yyyy");

            return requiredDateFormat.format(newDate);
        }

        return null;

    }

    public static String getHourFromDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm");
        Date newDate = null;

        try {
            newDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat requiredDateFormat = new SimpleDateFormat("h:mm a");
        return requiredDateFormat.format(newDate);
    }

    public static String formatDateForUI(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm");

        Date newDate = null;

        try {
            newDate = dateFormat.parse(date);
            String newD = newDate.toString();
            System.out.println(newD);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat requiredDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return requiredDateFormat.format(newDate);
    }
}
