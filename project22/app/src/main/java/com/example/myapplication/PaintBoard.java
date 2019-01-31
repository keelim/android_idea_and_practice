package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PaintBoard extends View {

    Paint paint;
    Bitmap mbitmap;
    Canvas mcanvas;

    int oldX;
    int oldY;

    public PaintBoard(Context context) {
        super(context);
        init(context);
    }

    public PaintBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.BLACK);

    }

    public void setColor(int color){
        paint.setColor(color);
    }

    public void setStrokeWidth(float lineWidth){
        paint.setStrokeWidth(lineWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mcanvas = new Canvas();
        mcanvas.setBitmap(mbitmap);
        mcanvas.drawColor(Color.WHITE);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int curlX = (int) event.getX();
        int curlY = (int) event.getY();
        if (action == MotionEvent.ACTION_DOWN) {
            oldX = curlX;
            oldY = curlY;

        } else if (action == MotionEvent.ACTION_MOVE) {
            if (oldX > 0 || oldY > 0)
                mcanvas.drawLine(oldX, oldY, curlX, curlY, paint);
            oldX = curlX;
            oldY = curlY;
        } else if (action == MotionEvent.ACTION_UP) {

        }

        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mbitmap, 0, 0, null);

    }
}
