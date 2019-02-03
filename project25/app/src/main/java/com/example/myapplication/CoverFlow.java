package com.example.myapplication;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class CoverFlow extends Gallery {
    Camera camera = new Camera();

    public static int maxRotationAngle = 55;
    public static int maxZoom = -60;

    private int centerPoint;

    public CoverFlow(Context context) {
        super(context);
        init(context);
    }

    public CoverFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setStaticTransformationsEnabled(true); //각각의 아이템의 변형
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        int Childcenter = child.getLeft() + child.getWidth() / 2;
        int ChildWidth = child.getWidth();

        int tationAngle = 0;
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);
        if (Childcenter == centerPoint) {
            transformImageBitmap((ImageView) child, t, 0);
        } else {
            tationAngle = (int) (((float) (centerPoint - Childcenter) / ChildWidth) * maxRotationAngle);
            if (Math.abs(tationAngle) > maxRotationAngle) {
                tationAngle = (tationAngle < 0) ? -maxRotationAngle : maxRotationAngle;

            }
            transformImageBitmap((ImageView) child, t, tationAngle);


        }
        return true;
    }

    private void transformImageBitmap(ImageView child, Transformation t, int tationAngle) {
        camera.save();
        Matrix matrix = t.getMatrix();
        int imageHeight = child.getLayoutParams().height;
        int imageWidth = child.getLayoutParams().width;
        int rotation = Math.abs(tationAngle);

        camera.translate(0.0f, 0.0f, 0.0f);

        if(tationAngle < maxRotationAngle){
            float zoomAmount = (float) (maxZoom + (rotation*1.5));
            camera.translate(0.0f, 0.0f, zoomAmount);
        }

        camera.rotateZ(tationAngle);
        camera.getMatrix(matrix);


        matrix.preTranslate(-(imageWidth/2), -(imageHeight/2));
        matrix.postTranslate(imageWidth/2, imageHeight/2);

        camera.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        //centerpoint의 확인
        centerPoint = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
        //center point를 잡을 수 있다.
    }

}
