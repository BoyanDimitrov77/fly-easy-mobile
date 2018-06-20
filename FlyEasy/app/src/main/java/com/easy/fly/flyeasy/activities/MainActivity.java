package com.easy.fly.flyeasy.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.common.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @BindView(R.id.welcome_screen_layout)
    RelativeLayout relativeLayout;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private SessionManager sessionManager;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
                sessionManager.checkLogin();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);
        handler.postDelayed(runnable,2000);




    }

}
