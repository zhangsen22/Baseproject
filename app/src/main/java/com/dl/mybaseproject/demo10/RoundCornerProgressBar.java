package com.dl.mybaseproject.demo10;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.dl.mybaseproject.R;
import com.dl.mybaseproject.Utils;

/**
 * 圆角矩形进度条
 *
 * Created by yinjianhua on 2017/11/6.
 */

public class RoundCornerProgressBar extends ProgressBar {

    private Paint mBackgroundPaint = new Paint();
    private Paint mProgressPaint = new Paint();
    private float mRadius;
    private int mPathWidth;
    private float mStartOffsetPercent;

    public RoundCornerProgressBar(Context context) {
        this(context, null);
    }

    public RoundCornerProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerProgressBarAttr);

        mBackgroundPaint = new Paint();
        mProgressPaint = new Paint();

        int backgroundColor = typedArray.getColor(R.styleable.RoundCornerProgressBarAttr_backgroundColor, Color.GRAY);
        int progressColor = typedArray.getColor(R.styleable.RoundCornerProgressBarAttr_progressColor, Color.RED);
        mPathWidth = typedArray.getDimensionPixelSize(R.styleable.RoundCornerProgressBarAttr_pathWidth, Utils.covertDp2Px(context,5));
        mRadius = typedArray.getDimension(R.styleable.RoundCornerProgressBarAttr_radius, Utils.covertDp2Px(context,15f));
        mStartOffsetPercent = typedArray.getFloat(R.styleable.RoundCornerProgressBarAttr_startOffset, 0);

        mBackgroundPaint.setColor(backgroundColor);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStrokeWidth(mPathWidth);

        mProgressPaint.setColor(progressColor);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStrokeWidth(mPathWidth);
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawProgress(canvas);
    }

    private void drawBackground(Canvas canvas){

        RectF rectF = new RectF(mPathWidth,mPathWidth,getWidth()-mPathWidth,getHeight()-mPathWidth);

        canvas.drawRoundRect(rectF, mRadius, mRadius, mBackgroundPaint);
    }

    private void drawProgress(Canvas canvas){

        RectF rectF = new RectF(mPathWidth,mPathWidth,getWidth()-mPathWidth,getHeight()-mPathWidth);
        Path path = new Path();


        path.addRoundRect(rectF, mRadius, mRadius, Path.Direction.CW);

        Path mRenderPaths = new Path();
        PathMeasure mPathMeasure = new PathMeasure(path, false);

        float pathOffset = mPathMeasure.getLength() * (getProgress()/100f);
        float startOffset = mPathMeasure.getLength() * mStartOffsetPercent;

        if(pathOffset + startOffset < mPathMeasure.getLength()){
            if (mPathMeasure.getSegment(startOffset, startOffset + pathOffset, mRenderPaths, true)) {
                canvas.drawPath(mRenderPaths, mProgressPaint);
            }
        } else {
            if (mPathMeasure.getSegment(startOffset, mPathMeasure.getLength(), mRenderPaths, true)) {
                canvas.drawPath(mRenderPaths, mProgressPaint);
            }
            if (mPathMeasure.getSegment(0, pathOffset + startOffset - mPathMeasure.getLength(), mRenderPaths, true)) {
                canvas.drawPath(mRenderPaths, mProgressPaint);
            }
        }
    }

}
