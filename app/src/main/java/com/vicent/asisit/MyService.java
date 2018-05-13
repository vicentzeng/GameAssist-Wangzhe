package com.vicent.asisit;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.media.Image;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

public class MyService extends Service {
    //Log用的TAG
    public static final String TAG = "MyService";

    //要引用的布局文件.
    static ConstraintLayout toucherLayout;
    //布局参数.
    static WindowManager.LayoutParams params;
    //实例化的WindowManager.
    static WindowManager windowManager;
    static boolean isDrawed = false;
    static DrawView view;

    static MyDrawLayout mMyLayout;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
        Log.i(TAG,"MainService Created");

        mMyLayout = new MyDrawLayout(getApplication());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        int heroType = intent.getIntExtra("hero_type", 0);
        mMyLayout.setHeroType(heroType);
        if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            mMyLayout.drawFloatLayout();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        Log.d(TAG, "onDestroy() executed");
        super.onDestroy();
        mMyLayout.clearFloatLayout();

        Exception e = new Exception();
        e.printStackTrace();
        Runtime runtime = Runtime.getRuntime();
        long usedMemory=runtime.totalMemory() - runtime.freeMemory();
        Log.i("vicent", "usedMemory"+usedMemory+" freeMemory" + runtime.freeMemory() + " totalMem" + runtime.totalMemory());
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(TAG, "landscape");
            mMyLayout.drawFloatLayout();
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.i(TAG, "portrait");
            mMyLayout.clearFloatLayout();
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        Log.e(TAG, " onTrimMemory ... level:" + level);
    }

    @Override
    public void onLowMemory() {
        Log.e(TAG, " onLowMemory ... ");
    }

}
