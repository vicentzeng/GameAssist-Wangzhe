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

public class DrawView extends View {
    public static int HOUYI = 1;
    public static int CHENGYAOJING = 2;

    RectF oval_rect;    //画椭圆用的矩形
    int mHeroType = 0;
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
            default:break;
        }
    }

    public void drawHouyi(Canvas canvas, Paint p){
        //1.平A椭圆
        int a = 1480;
        int b = 1044;
        drawMyOval( 1920/2, 720, a, b, Color.RED, 150, canvas, p);
        //2技能 中心椭圆
        a *=1.16;
        b *=1.20;
        drawMyOval( 1920/2, 790, a, b, Color.BLUE, 150, canvas, p);
        //2技能 边缘椭圆
        a *=1.30;
        b *=1.16;
        drawMyOval( 1920/2, 790, a, b, Color.CYAN, 150, canvas, p);
    }

    public void drawYaojin(Canvas canvas, Paint p){
        //1.平A椭圆
        int a = 1480;
        int b = 1044;
        a *=0.32;
        b *=0.315;
        drawMyOval( 1920/2, 601, a, b, Color.RED, 150, canvas, p);
        //2技能 中心椭圆
        a *=1.618;
        b *=1.618;
        drawMyOval( 1920/2, 624, a, b, Color.BLUE, 150, canvas, p);
        //2技能 边缘椭圆
        a *=1.665;
        b *=1.68;
        drawMyOval( 1920/2, 690, a, b, Color.CYAN, 150, canvas, p);
        //2技能 边缘椭圆
        a *=1.30;
        b *=1.16;
        drawMyOval( 1920/2, 690, a, b, Color.CYAN, 150, canvas, p);
    }

    //新矩形
    public void drawMyOval(int center_w, int center_h,int a, int b, int color, int alpha, Canvas canvas, Paint p){
        //center_w = 1920/2;
        //center_h = 790;
        int left = center_w -a/2;
        int top = center_h - b/2;
        int right = center_w + a/2;
        int bottom = center_h +b/2;
        oval_rect.set(left,top,right,bottom);    //新矩形
        p.setColor(color);// 设置红色
        p.setAlpha(alpha);
        canvas.drawOval(oval_rect, p);
    }
}