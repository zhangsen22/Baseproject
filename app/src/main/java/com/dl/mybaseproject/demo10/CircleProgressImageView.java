package com.dl.mybaseproject.demo10;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.dl.mybaseproject.R;

public class CircleProgressImageView extends View {

    private int totalW, totalH;
    private Paint paint;
    private Bitmap bitmap;
    private RectF circleRectF;
    private PorterDuffXfermode xfermode;

    private int resId;

    // 扇形角度
    private int angle;


    public CircleProgressImageView(Context context) {
        this(context, null);
    }

    public CircleProgressImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.CircleProgressImageView);
        resId=array.getResourceId(R.styleable.CircleProgressImageView_imageRes,R.mipmap.icon_spot_light_off);
        array.recycle();
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setDither(true);//设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        paint.setFilterBitmap(true);//加快显示速度，本设置项依赖于dither和xfermode的设置
        bitmap = BitmapFactory.decodeResource(getResources(), resId);//从资源文件中解析获取Bitmap
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        circleRectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 设置View的离屏缓冲。在绘图的时候新建一个“层”，所有的操作都在该层而不会影响该层以外的图像
         * 必须设置，否则设置的PorterDuffXfermode会无效，具体原因不明
         */
        int sc = canvas.saveLayer(0, 0, totalW, totalH, paint, Canvas.ALL_SAVE_FLAG);
        //现在画布上把进度图片画上去
        canvas.drawBitmap(bitmap, 0, 0, null);
        //将画布坐标原点移到中心位置
        canvas.translate(totalW / 2f, totalH / 2f);
        float r = (float) (Math.min(totalW, totalH) / 2);     //饼状图半径(取宽高里最小的值)
        //设置将要用来画扇形的矩形的轮廓
        circleRectF.set(-r, -r, r, r);

        paint.setXfermode(xfermode);
        paint.setColor(Color.WHITE);
        // 绘制 dst 扇形
        canvas.drawArc(circleRectF, -90, angle, true, paint);
        paint.setXfermode(null);
        /**
         * 还原画布，与canvas.saveLayer配套使用
         */
        canvas.restoreToCount(sc);
    }

    public void startAnimator() {
        // 扇形进度动画
        ValueAnimator angleAnimator = ValueAnimator.ofInt(0, 360);
        angleAnimator.setDuration(10000);
        angleAnimator.setInterpolator(new LinearInterpolator());
        angleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                angle = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        angleAnimator.start();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        totalW = w;
        totalH = h;
    }

    public void recycleBitmap() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }
}