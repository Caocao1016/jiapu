package com.yskj.daishuguan.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mafanwei on 2018/6/5.
 */

public class SignView extends View implements View.OnTouchListener{
    Bitmap bitmap=null;
    Path path;
    Rect boundary;
    Canvas canvas1;
    boolean isdraw;
    public int bound,stroke;
    private int width,height;

    public float getBound() {
        return bound;
    }

    public void setBound(int bound) {
        this.bound = bound;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
    }

    public float getStroke() {
        return stroke;
    }

    public SignView(Context context) {
        super(context);
        init();
    }

    public SignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width=getWidth();
        height=getHeight();
        bitmap = Bitmap.createBitmap(width-bound, height-bound, Bitmap.Config.ARGB_8888);
        canvas1=new Canvas(bitmap);
        boundary=new Rect(bound,bound,width-bound,height-bound);
    }

    private void init() {
        path=new Path();
        isdraw=false;
        stroke=8;
        bound=8;
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(stroke);
        canvas.drawPath(path,paint);
        canvas1.drawPath(path,paint);
        canvas.drawRect(boundary,paint);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        isdraw=true;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(),event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(),event.getY());
                invalidate();
                break;
        }
        return true;
    }

    public Bitmap getBitmap(){
        if(!isdraw)
            return null;
        return bitmap;
    }

    public void clear(){
        path.reset();
        bitmap = Bitmap.createBitmap(width-bound, height-bound, Bitmap.Config.ARGB_8888);
        canvas1=new Canvas(bitmap);
        invalidate();
    }
}
