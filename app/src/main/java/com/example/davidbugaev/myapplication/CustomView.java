package com.example.davidbugaev.myapplication;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by davidbugaev on 26/07/2017.
 */

public class CustomView extends View {

    private static final int SIZE_SEPARATOR = 4;
    private static final int SIZE_SQUARE = 4;
    private RectF mRectContainer;
    private Paint mPaint;


    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attributes) {
        super(context, attributes);
        //init paint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(SIZE_SEPARATOR);
        mPaint.setTextSize(50f);
        mRectContainer = new RectF();

    }

    //определяем размеры вьюихи
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //расставляем красоту внутри этой вьюхи
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = right - left;
        int height = bottom - top;
        int bufMinValue = Math.min(width, height);
        int widthRectangle = bufMinValue / SIZE_SQUARE;
        int heightRectangle = bufMinValue / SIZE_SQUARE;
        if (changed) {
            mRectContainer.set(width / 2 - widthRectangle, height / 2 - heightRectangle,
                    width / 2 + widthRectangle, height / 2 + heightRectangle);
        }
    }

    private int j = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 4; i++) {
            Path mArc = new Path();
            mArc.addArc(mRectContainer, 180 + 45 * i, 45);
            canvas.drawPath(mArc, mPaint);
            canvas.drawTextOnPath(String.valueOf(i), mArc, 70, 60, mPaint);
        }

        Path mLine = new Path();
        mLine.moveTo(mRectContainer.centerX(), mRectContainer.centerY());
        mLine.lineTo((float) Math.floor((mRectContainer.centerX() + (mRectContainer.top - mRectContainer.bottom) * (float) Math.cos(45 + j) / 2)),
                (float) Math.floor((mRectContainer.centerY() + (mRectContainer.top - mRectContainer.bottom) * (float) Math.sin(45 + j) / 2)));
        canvas.drawPath(mLine, mPaint);
    }

    private void customAnimate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        j++;
                        postInvalidate();
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                customAnimate();
                break;
        }
        return true;
    }


}
