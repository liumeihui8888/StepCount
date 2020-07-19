package com.v210.widgets;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class CircleProgressBar extends View {
    private int progress = 0;
    private int max = 100;
    //绘制轨迹
    private Paint pathPaint = null;
    //绘制填充
    private Paint fillArcPaint = null;
    private RectF oval;
    //梯度渐变的填充颜色
    private int[] arcColors = new int[]{0xFF02C016, 0xFF3DF346, 0xFF40F1D5, 0xFF02C016};
    //    private int[] shadowsColors = new int[]{0xFF111111, 0x00AAAAAA, 0x00AAAAAA};
    //灰色轨迹
    //背景灰色
    private static final int PATHCOLOR = 0xFFF0EEDF;
    //边框灰色
    private int pathBorderColor = 0xFFD2D1C4;
    //环的路径宽度
    private int pathWidth = 35;
    private int width;
    private int height;
    //默认圆的半径
    private int radius = 120;
    // 指定了光源的方向和环境光强度来添加浮雕效果
    private EmbossMaskFilter emboss = null;
    // 设置光源的方向
    float[] direction = new float[]{1, 1, 1};
    //设置环境光亮度
    float light = 0.4f;
    // 选择要应用的反射等级
    float specular = 6;
    // 向 mask应用一定级别的模糊
    float blur = 3.5f;

    //指定了一个模糊的样式和半径来处理 Paint 的边缘
    private BlurMaskFilter mBlur = null;
    private OnCircleProgressListener mAbOnProgressListener = null;
    private boolean reset = false;

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        pathPaint = new Paint();
        // 设置是否抗锯齿
        pathPaint.setAntiAlias(true);
        // 帮助消除锯齿
        pathPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        // 设置中空的样式
        pathPaint.setStyle(Paint.Style.STROKE);
        //设置使用防抖动功能
        pathPaint.setDither(true);
        pathPaint.setStrokeJoin(Paint.Join.ROUND);

        fillArcPaint = new Paint();
        // 设置是否抗锯齿
        fillArcPaint.setAntiAlias(true);
        // 帮助消除锯齿
        fillArcPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        // 设置中空的样式
        fillArcPaint.setStyle(Paint.Style.STROKE);
        fillArcPaint.setDither(true);
        fillArcPaint.setStrokeJoin(Paint.Join.ROUND);
        this.oval = new RectF();
        this.emboss = new EmbossMaskFilter(this.direction, this.light, this.specular, this.blur);
        this.mBlur = new BlurMaskFilter(20.0F, BlurMaskFilter.Blur.NORMAL);
        sweepGradient = new SweepGradient((float) (this.width / 2), (float) (this.height / 2), this.arcColors, (float[]) null);
    }
   //梯度渲染
    private SweepGradient sweepGradient;

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.reset) {
            canvas.drawColor(0xFFFFFFFF);
            this.reset = false;
        }

        this.width = this.getMeasuredWidth();
        this.height = this.getMeasuredHeight();
        this.radius = this.getMeasuredWidth() / 2 - this.pathWidth;
        //画笔颜色
        this.pathPaint.setColor(this.PATHCOLOR);
        //设置画笔宽度
        this.pathPaint.setStrokeWidth((float) this.pathWidth);
        //添加浮雕效果
        this.pathPaint.setMaskFilter(this.emboss);
        // 在中心的地方画个半径为r的圆
        canvas.drawCircle((float) (this.width / 2), (float) (this.height / 2), (float) this.radius, this.pathPaint);
        this.pathPaint.setStrokeWidth(0.5F);
        this.pathPaint.setColor(this.pathBorderColor);
        canvas.drawCircle((float) (this.width / 2), (float) (this.height / 2), (float) (this.radius + this.pathWidth / 2) + 0.5F, this.pathPaint);
        canvas.drawCircle((float) (this.width / 2), (float) (this.height / 2), (float) (this.radius - this.pathWidth / 2) - 0.5F, this.pathPaint);
//        SweepGradient sweepGradient = new SweepGradient((float)(this.width / 2), (float)(this.height / 2), this.arcColors, (float[])null);
        this.fillArcPaint.setShader(sweepGradient);
        this.fillArcPaint.setMaskFilter(this.mBlur);
        this.fillArcPaint.setStrokeCap(Paint.Cap.ROUND);
        this.fillArcPaint.setStrokeWidth((float) this.pathWidth);
        this.oval.set((float) (this.width / 2 - this.radius), (float) (this.height / 2 - this.radius), (float) (this.width / 2 + this.radius), (float) (this.height / 2 + this.radius));
        canvas.drawArc(this.oval, -90.0F, (float) this.progress / (float) this.max * 360.0F, false, this.fillArcPaint);
    }

    public int getRadius() {
        return this.radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        this.invalidate();
        if (this.mAbOnProgressListener != null) {
            if (this.max <= this.progress) {
                this.mAbOnProgressListener.onComplete();
            } else {
                this.mAbOnProgressListener.onProgress(progress);
            }
        }

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        this.setMeasuredDimension(width, height);
    }

    public OnCircleProgressListener getAbOnProgressListener() {
        return this.mAbOnProgressListener;
    }

    public void setAbOnProgressListener(OnCircleProgressListener mAbOnProgressListener) {
        this.mAbOnProgressListener = mAbOnProgressListener;
    }

    public void reset() {
        this.reset = true;
        this.progress = 0;
        this.invalidate();
    }

    public void setReset(boolean reset) {
        this.reset = reset;
        if (reset) {
            this.progress = 0;
            this.invalidate();
        }
    }

}
