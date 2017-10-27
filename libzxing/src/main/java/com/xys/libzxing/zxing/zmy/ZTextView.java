package com.xys.libzxing.zxing.zmy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/7/10 0010.
 */
public class ZTextView extends TextView {
    protected float progress;
    protected Paint mPaint;
    protected RectF rectF;

    public ZTextView(Context context) {
        this(context, null);
    }

    public ZTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        setProgress(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (progress >= 0) {
            if (rectF == null) {
                int width = getMeasuredWidth();
                int height = getMeasuredHeight();
                mPaint.setStrokeWidth(width * 0.04f);
                float radius = width * 0.48f;
                rectF = new RectF(width * 0.5f - radius, height * 0.5f - radius,
                        width * 0.5f + radius, height * 0.5f + radius);
            }
            mPaint.setColor(Color.WHITE);
            canvas.drawOval(rectF, mPaint);
            mPaint.setColor(Color.BLUE);
            canvas.drawArc(rectF, 90f, (3.6f) * progress, false, mPaint);
        }

    }

    /**
     * 设置程度
     *
     * @param progress 程度
     */
    public void setProgress(float progress) {
        this.progress = progress;
        setText("" + progress + "%");
        invalidate();
    }
}
