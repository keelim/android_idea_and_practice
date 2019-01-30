package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class CustomView extends View {
    Paint paint;
    private int deviceheight;
    private int devicewidth;
    private ShapeDrawable drawable;

    public CustomView(Context context) {
        super(context);
        init(context);

    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.RED);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        devicewidth = display.getWidth();
        deviceheight = display.getHeight();

        drawable = new ShapeDrawable();
        RectShape rect = new RectShape();
        rect.resize(devicewidth, deviceheight);
        drawable.setShape(rect);
        drawable.setBounds(0,0,devicewidth, deviceheight);

        LinearGradient gradient = new LinearGradient(0,0,0,deviceheight, Color.BLUE, Color.YELLOW, Shader.TileMode.CLAMP);

        Paint curlpaint = drawable.getPaint();
        curlpaint.setShader(gradient);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
////        canvas.drawRect(100,100, 200, 200, paint);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(4.0f);
//        paint.setColor(Color.GREEN);
//        canvas.drawRect(10, 10, 100, 100, paint);
//
//        paint.setStyle(Paint.Style.FILL);
//        paint.setARGB(128,0,0,255);
//        canvas.drawRect(120,10,210,100,paint);
//        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{5,5},1);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(5.0f); //파라민터를 float로
//        paint.setPathEffect(dashPathEffect);
//        paint.setColor(Color.GREEN);
//        canvas.drawRect(120,10,210,100,paint);
//
//        paint.setStyle(Paint.Style.FILL);
//        paint.setTextSize(40f);
//        canvas.drawText("안녕하세요", 20, 320, paint);
//    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();


        if(action == MotionEvent.ACTION_DOWN)
            Toast.makeText(getContext(), "눌렸어요", Toast.LENGTH_LONG).show();

        return true;
    }
}
