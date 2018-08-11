package com.render.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by huangxin on 2016/9/23.
 */
public class CustomView extends View {

    private Paint paint;
    private Handler handler;//

    private int textureHeight = 200;
    private int textureWidth = 200;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        handler = new Handler();
        setLayoutParams(new ViewGroup.LayoutParams(200, 200));
    }

    private float endX = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("CustomView", "onDraw--->");
        if(endX >= getWidth()){
            endX = 0;
        }

        canvas.drawLine(0, getHeight() / 2, endX, getHeight() / 2, paint);
        endX = endX + 5;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
                Log.e("CustomView", "invalidate--->");
            }
        }, 100);
    }
}
