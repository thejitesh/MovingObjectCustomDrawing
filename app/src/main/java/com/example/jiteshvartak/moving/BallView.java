package com.example.jiteshvartak.moving;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jiteshvartak on 3/31/18.
 */

public class BallView extends View {

    private Context mContext;
    private Point viewDimension;
    private Paint mPaint;
    private PointF mPosition;
    private PointF mVelocity;
    private PointF mGravity;
    private Point ballDimension;
    private Handler handler;
    private Runnable runnable;

    public BallView(Context context) {
        super(context);
        init(context, null);
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    public void init(Context context, @Nullable AttributeSet attrs) {

        handler = new Handler();
        this.mContext = context;
        mPaint = new Paint();
        mPosition = new PointF();
        mVelocity= new PointF(10,6);
        mGravity= new PointF();
        ballDimension = new Point(70, 70);
        mGravity.x = 0.001f;
        mGravity.y = 0.03f;

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        viewDimension = new Point(w,h);

        onResume();
    }

    public void doPhysics(){

        mVelocity.x += mGravity.x;
        mVelocity.y += mGravity.y;

        if(mVelocity.x > 5){
            mVelocity.x = 5;
        }

        if(mVelocity.x < -5){
            mVelocity.x = -5;
        }

        if(mVelocity.y > 5){
            mVelocity.y = 5;
        }

        if(mVelocity.y < -5){
            mVelocity.y = -5;
        }

        if(mPosition.x + ballDimension.x >viewDimension.x){
            mVelocity.x = - Math.abs(mVelocity.x);
            mPosition.x = viewDimension.x - ballDimension.x;
        }else if(mPosition.x <0){
            mVelocity.x = Math.abs(mVelocity.x);
            mPosition.x = 0;

        }

        if(mPosition.y + ballDimension.y >viewDimension.y){
            mVelocity.y = - Math.abs(mVelocity.y);
            mPosition.y = viewDimension.y - ballDimension.y;

        }else if(mPosition.y <0){
            mVelocity.y = Math.abs(mVelocity.y);
            mPosition.y = 0;

        }

        mPosition.x += mVelocity.x;
        mPosition.y += mVelocity.y;
    }


    public void onResume() {

        if(runnable!= null) {
            handler.removeCallbacks(runnable);
        }
        runnable = new Runnable() {
            @Override
            public void run() {

                doPhysics();
                invalidate();

                handler.removeCallbacks(this);
                handler.postDelayed(this , 5);
            }
        };

            handler.postDelayed(runnable, 5);

    }

    float radius = 30;
    float horizontalDisplacement = 200;
    float factor = -1;
    float factor2 = 1;

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        radius += 0.4f * factor;
        horizontalDisplacement += 0.85f*factor2;

        if(radius<5){
            radius =5;
        }else if(radius>30){
            factor2 = -1;
            factor = 0;
        }

        if(factor2>=1 && horizontalDisplacement>600){
            factor = 1;
            factor2 = 1f;
        }else if(horizontalDisplacement < 200){
             factor = -1;
             factor2 = 1;
        }

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(horizontalDisplacement,500 , radius , mPaint);


    }



}
