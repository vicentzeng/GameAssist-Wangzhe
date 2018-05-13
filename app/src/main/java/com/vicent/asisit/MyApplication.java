package com.vicent.asisit;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import static com.vicent.asisit.ServerThread.MSG_ROTAION_LANDSCAPE;
import static com.vicent.asisit.ServerThread.MSG_ROTAION_PORTRAIT;

public class MyApplication extends Application {
    public static ServerThread mThread;
    @Override
    public void onCreate() {
        super.onCreate();
        mThread = new ServerThread(this);
        mThread.setHeroType(1);
        mThread.start();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if(mThread != null) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
                mThread.sendMsg(MSG_ROTAION_LANDSCAPE);
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
                mThread.sendMsg(MSG_ROTAION_PORTRAIT);
            }
        }
    }

}
