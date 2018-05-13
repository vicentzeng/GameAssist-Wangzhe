package com.vicent.asisit;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.vicent.asisit.ServerThread.MSG_DRAW_HOUYI;
import static com.vicent.asisit.ServerThread.MSG_DRAW_CHENGYAOJING;

public class MainActivity extends AppCompatActivity {

    Button button_houyi;
    Button button_yaojin;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        button_houyi = (Button) findViewById(R.id.button_houyi);
        button_houyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("vicent", "click");
                MyApplication.mThread.sendMsg(MSG_DRAW_HOUYI);
            }
        });

        button_yaojin = (Button) findViewById(R.id.button_chengyaojin);
        button_yaojin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("vicent", "click");
                MyApplication.mThread.sendMsg(MSG_DRAW_CHENGYAOJING);
            }
        });

        if (Settings.canDrawOverlays(MainActivity.this))
        {
            //finish();
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            Log.i("vicent", "canDrawOverlays");
        }else
        {
            //若没有权限，提示获取.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            Toast.makeText(MainActivity.this,"需要取得权限以使用悬浮窗",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            //finish();
            Log.i("vicent", "can not DrawOverlays");
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
