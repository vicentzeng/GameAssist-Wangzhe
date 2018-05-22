package com.vicent.asisit;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import static com.vicent.asisit.ServerThread.MSG_DRAW_CHENGYAOJING;
import static com.vicent.asisit.ServerThread.MSG_DRAW_HOUYI;
import static com.vicent.asisit.ServerThread.MSG_DRAW_MENGQI;

public class DrawView extends View {
    public static int HOUYI = 1;
    public static int CHENGYAOJING = 2;
    public final int SKILL_A = 0x0F;
    public final int SKILL_AE = 0x10;
    public final int SKILL_1 = 0x11;
    public final int SKILL_1E = 0x12;
    public final int SKILL_2 = 0x13;
    public final int SKILL_2E = 0x14;
    public final int SKILL_3 = 0x15;
    public final int SKILL_3E = 0x16;
    public final int SKILL_P = 0x17;


    RectF oval_rect;    //画椭圆用的矩形
    int mHeroType = 0;
    int mColor = 0;
    int mAlpha = 150;
    //last oval
    int mcenter_w;
    int mcenter_h;
    int ma;
    int mb;

    public DrawView(Context context, int HeroType) {
        super(context);
        oval_rect = new RectF(0,0,0,0);
        mHeroType = HeroType;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 创建画笔
        Paint p = new Paint();

        //画椭圆，把oval改一下
        p.setStyle(Paint.Style.STROKE);//设置空心
        p.setStrokeWidth(4);
        p.setAntiAlias(true);
        Log.i("vicent", "mHeroType:"+mHeroType);
        switch(mHeroType){
            case MSG_DRAW_HOUYI:
                drawHouyi(canvas, p);
                break;
            case MSG_DRAW_CHENGYAOJING:
                drawYaojin(canvas, p);
                break;
            case MSG_DRAW_MENGQI:
                drawMengqi(canvas, p);
                break;

            default:break;
        }
    }

    public void drawHouyi(Canvas canvas, Paint p){
        //1.平A椭圆
        int a = 1480;
        int b = 1044;
        int center_w = 1920/2;

        drawMyOval(center_w,720,1480,1044, SKILL_A, canvas, p);
        drawMyOval(center_w,790,1716,1252, SKILL_1, canvas, p);
        drawMyOval(center_w,790,2230,1452, SKILL_2, canvas, p);

        drawMyOval(center_w,624,765,530, SKILL_P, canvas, p);

    }

    public void drawYaojin(Canvas canvas, Paint p){
        //1.平A椭圆
        int a = 1480;
        int b = 1044;
        int center_w = 1920/2;
        drawMyOval(center_w,601,473,328, SKILL_A, canvas, p);
        drawMyOval(center_w,624,765,530, SKILL_2, canvas, p);
        drawMyOval(center_w,690,1273,890, SKILL_1, canvas, p);
        drawMyOval(center_w,690,1654,1032, SKILL_1E, canvas, p);
    }


    public void drawMengqi(Canvas canvas, Paint p){
        //1.平A椭圆
        int a = 1480;
        int b = 1044;
        int center_w = 1920/2;
        drawMyRectOval(730,445,1192,758, SKILL_A, canvas, p);
        drawMyOval(center_w,624,765,530, SKILL_AE, canvas, p);  //同闪现
        drawMyOval( mcenter_w, mcenter_h-10, (int)(ma *0.8), (int)(mb *0.8), SKILL_1, canvas, p);
        drawMyRectOval(616,378,1305,860, SKILL_1E, canvas, p);
        drawMyRectOval2(111,158,center_w,765, SKILL_2, canvas, p);
        drawMyOval( mcenter_w, mcenter_h, (int)(ma *1.21), (int)(mb *1.11), SKILL_2E, canvas, p);

        drawMyOval( mcenter_w, mcenter_h, (int)(ma *1.21), (int)(mb *1.11), SKILL_3, canvas, p);

        drawMyOval(center_w,624,765,530, SKILL_P, canvas, p);
    }

    //新矩形
    public void drawMyOval(int center_w, int center_h,int a, int b, int skill_type, Canvas canvas, Paint p){

        int alpha = 150;
        setColor(skill_type);
        Log.i("vicent", center_w+","+center_h+","+ a + ","+b);
        int left = center_w -a/2;
        int top = center_h - b/2;
        int right = center_w + a/2;
        int bottom = center_h +b/2;
        oval_rect.set(left,top,right,bottom);    //新矩形
        p.setColor(mColor);// 设置红色
        p.setAlpha(mAlpha);
        canvas.drawOval(oval_rect, p);
        updateLastOvalInfo();
    }
    public void drawMyRectOval(int left, int top, int right, int bottom, int skill_type, Canvas canvas, Paint p){

        setColor(skill_type);
        oval_rect.set(left,top,right,bottom);    //新矩形
        p.setColor(mColor);// 设置红色
        p.setAlpha(mAlpha);
        canvas.drawOval(oval_rect, p);
        updateLastOvalInfo();
    }

    public void drawMyRectOval2(int left, int top, int center_w, int center_h, int skill_type, Canvas canvas, Paint p){

        setColor(skill_type);
        int right = left + (center_w - left)*2;
        int bottom = top + (center_h - top)*2;
        oval_rect.set(left,top,right,bottom);    //新矩形
        p.setColor(mColor);// 设置红色
        p.setAlpha(mAlpha);
        canvas.drawOval(oval_rect, p);
        updateLastOvalInfo();
    }

    public void setColor(int skill_type){
        mAlpha = 150;
        switch(skill_type){
            case SKILL_P:
            {
                mColor = 0xf3d91e;//gold
                mAlpha = 150;
            }break;
            case SKILL_A:
            {
                mColor = Color.RED;
            }break;
            case SKILL_AE:
            {
                mColor = Color.RED;
            }break;
            case SKILL_1:
            {
                mColor = Color.BLUE;
            }break;
            case SKILL_1E:
            {
                mColor = Color.BLUE;
                mAlpha = 150/2;
            }break;
            case SKILL_2:
            {
                mColor = Color.CYAN;
            }break;
            case SKILL_2E:
            {
                mColor = Color.CYAN;
                mAlpha = 150/2;
            }break;
            case SKILL_3:
            {
                mColor = 0xE0FFFF;
            }break;
            case SKILL_3E:
            {
                mColor = 0xE0FFFF;
                mAlpha = 150/2;
            }break;
            default:break;
        }
    }
    public void updateLastOvalInfo(){
        ma = (int)( oval_rect.right - oval_rect.left);
        mb = (int)(oval_rect.bottom - oval_rect.top);
        mcenter_w = (int)(oval_rect.left + ma/2);
        mcenter_h = (int)(oval_rect.top + mb/2);
    }
}