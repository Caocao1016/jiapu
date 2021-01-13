package com.demo.jiapu.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class RoundRelativeLayout extends RelativeLayout {
    private final RectF roundRect = new RectF();
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();
    private Context mContext;
    private float rect_adius = 8;  //单位为像素


    public RoundRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(Context context) {
        this.mContext = context;
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.WHITE);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        roundRectSet(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        canvasSetLayer(canvas);
        super.draw(canvas);
        canvas.restore();
    }


    /**
     * 从新设置圆角
     *
     * @param adius
     */
    public void setRectAdius(float adius) {
        rect_adius = adius;
        invalidate();
    }

    /**
     * 圆角区域设置
     *
     * @param width
     * @param height
     */
    private void roundRectSet(int width, int height) {
        roundRect.set(0, 0, width, height);
    }

    /**
     * 画布区域裁剪
     *
     * @param canvas
     */
    private void canvasSetLayer(Canvas canvas) {
        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(roundRect, rect_adius, rect_adius, zonePaint);
        //
        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
    }
}

