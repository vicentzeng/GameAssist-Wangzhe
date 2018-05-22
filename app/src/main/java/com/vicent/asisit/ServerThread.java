package com.vicent.asisit;


import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ServerThread extends Thread {

    public static final int MSG_CLERA_VIEW = 0x101;
    public static final int MSG_EXIT = 0x102;
    public static final int MSG_ROTAION_LANDSCAPE = 0x103;
    public static final int MSG_ROTAION_PORTRAIT = 0x104;

    public static final int MSG_DRAW_BEGIN = 0x200;
    public static final int MSG_DRAW_HOUYI = 0x201;
    public static final int MSG_DRAW_CHENGYAOJING = 0x202;
    public static final int MSG_DRAW_MENGQI = 0x203;
    //Log用的TAG
    public static final String TAG = "ServerThread";

    Application mApplication;
    MyDrawLayout mDrawLayout;
    int mHeroType = 0;
    public Handler uiHandler;
    Looper mLooper;

    //mutex and Condition
    private Lock mLock = new ReentrantLock();
    private Condition mCondition = mLock.newCondition();
    private int msgCount = 0;
    private int mMsgType = 0;

    ServerThread(Application app){
        mApplication = app;
        mDrawLayout = new MyDrawLayout(app);

    }
    public void setHeroType(int heroType){
        mHeroType = heroType;
        mDrawLayout.setHeroType(mHeroType);
    }
    @Override
    public void run() {
        Log.i(TAG, "run");
        Looper.prepare();
        mLooper = Looper.myLooper();

        uiHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.i(TAG, "uiHandler rec MsgType E:"+msg.what);
                switch (msg.what){
                    case MSG_DRAW_HOUYI:
                    case MSG_DRAW_CHENGYAOJING:
                    case MSG_DRAW_MENGQI:
                        mDrawLayout.setHeroType(msg.what);
                        break;
                    case MSG_CLERA_VIEW:
                        if(mDrawLayout != null) {
                            mDrawLayout.clearFloatLayout();
                        }break;
                    case MSG_ROTAION_LANDSCAPE:
                        mDrawLayout.clearFloatLayout();
                        mDrawLayout.drawFloatLayout();
                        break;
                    case MSG_ROTAION_PORTRAIT:
                        mDrawLayout.clearFloatLayout();
                        break;
                    default:
                        break;
                }
                Log.i(TAG, "uiHandler rec MsgType X:"+msg.what);
                mLock.lock();
                mCondition.signal();
                mLock.unlock();
                return false;
            }
        });
        mDrawLayout.drawFloatLayout();
        Looper.loop();

        Log.i(TAG, "run X");
    }

    public void sendMsg(int MsgType){
        Message msg = new Message();
        msg.what = MsgType;
        msg.setAsynchronous(false);
        uiHandler.sendMessage(msg);

        mLock.lock();
        try {
            mCondition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mLock.unlock();
    }

}
