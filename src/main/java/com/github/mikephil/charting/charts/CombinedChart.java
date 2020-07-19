
package com.github.mikephil.charting.charts;

import com.github.mikephil.charting.highlight.CombinedHighlighter;
import com.github.mikephil.charting.interfaces.*;
import com.github.mikephil.charting.renderer.CombinedChartRenderer;

/**
 * This chart class allows the combination of lines, bars, scatter and candle
 * data all displayed in one chart area.
 * 
 * @author Philipp Jahoda
 */
public class CombinedChart extends BarLineChartBase<com.github.mikephil.charting.data.CombinedData> implements LineDataProvider,
        BarDataProvider, ScatterDataProvider, CandleDataProvider, BubbleDataProvider {

    /** flag that enables or disables the highlighting arrow */
    private boolean mDrawHighlightArrow = false;

    /**
     * if set to true, all values are drawn above their bars, instead of below
     * their top
     */
    private boolean mDrawValueAboveBar = true;

    /**
     * if set to true, a grey area is drawn behind each bar that indicates the
     * maximum value
     */
    private boolean mDrawBarShadow = false;

    protected com.github.mikephil.charting.charts.CombinedChart.DrawOrder[] mDrawOrder = new com.github.mikephil.charting.charts.CombinedChart.DrawOrder[] {
            com.github.mikephil.charting.charts.CombinedChart.DrawOrder.BAR, com.github.mikephil.charting.charts.CombinedChart.DrawOrder.BUBBLE, com.github.mikephil.charting.charts.CombinedChart.DrawOrder.LINE, com.github.mikephil.charting.charts.CombinedChart.DrawOrder.CANDLE, com.github.mikephil.charting.charts.CombinedChart.DrawOrder.SCATTER
    };

    /**
     * enum that allows to specify the order in which the different data objects
     * for the combined-chart are drawn
     */
    public enum DrawOrder {
        BAR, BUBBLE, LINE, CANDLE, SCATTER
    }

    public CombinedChart(android.content.Context context) {
        super(context);
    }

    public CombinedChart(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public CombinedChart(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mHighlighter = new CombinedHighlighter(this);

        // mRenderer = new CombinedChartRenderer(this, mAnimator,
        // mViewPortHandler);
    }

    @Override
    protected void calcMinMax() {
        super.calcMinMax();
        
        if (getBarData() != null || getCandleData() != null || getBubbleData() != null) {
            mXChartMin = -0.5f;
            mXChartMax = mData.getXVals().size() - 0.5f;

            if (getBubbleData() != null) {

                for (com.github.mikephil.charting.data.BubbleDataSet set : getBubbleData().getDataSets()) {

                    final float xmin = set.getXMin();
                    final float xmax = set.getXMax();

                    if (xmin < mXChartMin)
                        mXChartMin = xmin;

                    if (xmax > mXChartMax)
                        mXChartMax = xmax;
                }
            }
        }

        mDeltaX = Math.abs(mXChartMax - mXChartMin);

        if (mDeltaX == 0.f && getLineData() != null && getLineData().getYValCount() > 0) {
            mDeltaX = 1.f;
        }
    }

    @Override
    public void setData(com.github.mikephil.charting.data.CombinedData data) {
        mData = null;
        mRenderer = null;
        super.setData(data);
        mRenderer = new CombinedChartRenderer(this, mAnimator, mViewPortHandler);
        mRenderer.initBuffers();
    }

    @Override
    public com.github.mikephil.charting.data.LineData getLineData() {
        if (mData == null)
            return null;
        return mData.getLineData();
    }

    @Override
    public com.github.mikephil.charting.data.BarData getBarData() {
        if (mData == null)
            return null;
        return mData.getBarData();
    }

    @Override
    public com.github.mikephil.charting.data.ScatterData getScatterData() {
        if (mData == null)
            return null;
        return mData.getScatterData();
    }

    @Override
    public com.github.mikephil.charting.data.CandleData getCandleData() {
        if (mData == null)
            return null;
        return mData.getCandleData();
    }

    @Override
    public com.github.mikephil.charting.data.BubbleData getBubbleData() {
        if (mData == null)
            return null;
        return mData.getBubbleData();
    }

    @Override
    public boolean isDrawBarShadowEnabled() {
        return mDrawBarShadow;
    }

    @Override
    public boolean isDrawValueAboveBarEnabled() {
        return mDrawValueAboveBar;
    }

    @Override
    public boolean isDrawHighlightArrowEnabled() {
        return mDrawHighlightArrow;
    }

    /**
     * set this to true to draw the highlightning arrow
     * 
     * @param enabled
     */
    public void setDrawHighlightArrow(boolean enabled) {
        mDrawHighlightArrow = enabled;
    }

    /**
     * If set to true, all values are drawn above their bars, instead of below
     * their top.
     * 
     * @param enabled
     */
    public void setDrawValueAboveBar(boolean enabled) {
        mDrawValueAboveBar = enabled;
    }


    /**
     * If set to true, a grey area is drawn behind each bar that indicates the
     * maximum value. Enabling his will reduce performance by about 50%.
     * 
     * @param enabled
     */
    public void setDrawBarShadow(boolean enabled) {
        mDrawBarShadow = enabled;
    }

    /**
     * Returns the currently set draw order.
     * 
     * @return
     */
    public com.github.mikephil.charting.charts.CombinedChart.DrawOrder[] getDrawOrder() {
        return mDrawOrder;
    }

    /**
     * Sets the order in which the provided data objects should be drawn. The
     * earlier you place them in the provided array, the further they will be in
     * the background. e.g. if you provide new DrawOrer[] { DrawOrder.BAR,
     * DrawOrder.LINE }, the bars will be drawn behind the lines.
     * 
     * @param order
     */
    public void setDrawOrder(com.github.mikephil.charting.charts.CombinedChart.DrawOrder[] order) {
        if (order == null || order.length <= 0)
            return;
        mDrawOrder = order;
    }
}
