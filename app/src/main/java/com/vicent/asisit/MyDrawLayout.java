package com.vicent.asisit;

import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

public class MyDrawLayout {

    public static final String TAG = "MyDraw";

    Application mApplication;
    //要引用的布局文件.
    ConstraintLayout toucherLayout;
    //布局参数.
    WindowManager.LayoutParams params;
    //实例化的WindowManager.
    WindowManager windowManager;
    boolean isDrawed = false;
    DrawView view;
    int mHeroType = 0;

    MyDrawLayout(Application app){
        mApplication = app;
        initFloatLayout();
    }
    public void setHeroType(int heroType){
        mHeroType = heroType;
    }
    public void drawFloatLayout(){
        //通知view组件重绘
        if(!isDrawed) {
            isDrawed = true;
            initFloatView(mHeroType);
            view.invalidate();
            toucherLayout.addView(view);
            windowManager.addView(toucherLayout, params);
            view.invalidate();
        }
    }

    public void clearFloatLayout(){
        if(isDrawed) {
            isDrawed = false;
            toucherLayout.removeView(view);
            windowManager.removeViewImmediate(toucherLayout);
        }
    }

    private void initFloatLayout()
    {
        //赋值WindowManager&LayoutParam.
        params = new WindowManager.LayoutParams();
        windowManager = (WindowManager) mApplication.getSystemService(Context.WINDOW_SERVICE);
        //设置type.系统提示型窗口，一般都在应用程序窗口之上.
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;//TYPE_SYSTEM_ALERT;
        //设置效果为背景透明.
        params.format = PixelFormat.RGBA_8888;
        //设置flags.不可聚焦及不可使用按钮对悬浮窗进行操控.
        params.flags = FLAG_NOT_TOUCH_MODAL|FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCHABLE | FLAG_FULLSCREEN | FLAG_LAYOUT_IN_SCREEN;

        //设置窗口初始停靠位置.
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;

        //设置悬浮窗口长宽数据.
        params.width = 1920;
        params.height = 1080;
        //params.screenOrientation = SCREEN_ORIENTATION_LANDSCAPE;

        LayoutInflater inflater = LayoutInflater.from(mApplication);
        //获取浮动窗口视图所在布局.
        toucherLayout = (ConstraintLayout) inflater.inflate(R.layout.toucherlayout,null);
        //添加toucherlayout

        //initFloatView

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        Log.i(TAG,"freemem:" + runtime.freeMemory()+"use  mem:"+runtime.totalMemory());
        //其他代码...
    }
    private void initFloatView(int heroType){
        //曲线
        view=new DrawView(mApplication, heroType);
        view.setMinimumHeight(500);
        view.setMinimumWidth(300);
    }
}
