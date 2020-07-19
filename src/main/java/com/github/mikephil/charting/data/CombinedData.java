
package com.github.mikephil.charting.data;

/**
 * Data object that allows the combination of Line-, Bar-, Scatter-, Bubble- and
 * CandleData. Used in the CombinedChart class.
 * 
 * @author Philipp Jahoda
 */
public class CombinedData extends BarLineScatterCandleBubbleData<BarLineScatterCandleBubbleDataSet<?>> {

    private com.github.mikephil.charting.data.LineData mLineData;
    private com.github.mikephil.charting.data.BarData mBarData;
    private com.github.mikephil.charting.data.ScatterData mScatterData;
    private com.github.mikephil.charting.data.CandleData mCandleData;
    private com.github.mikephil.charting.data.BubbleData mBubbleData;

    public CombinedData() {
        super();
    }

    public CombinedData(java.util.List<String> xVals) {
        super(xVals);
    }

    public CombinedData(String[] xVals) {
        super(xVals);
    }

    public void setData(com.github.mikephil.charting.data.LineData data) {
        mLineData = data;
        mDataSets.addAll(data.getDataSets());
        init();
    }

    public void setData(com.github.mikephil.charting.data.BarData data) {
        mBarData = data;
        mDataSets.addAll(data.getDataSets());
        init();
    }

    public void setData(com.github.mikephil.charting.data.ScatterData data) {
        mScatterData = data;
        mDataSets.addAll(data.getDataSets());
        init();
    }

    public void setData(com.github.mikephil.charting.data.CandleData data) {
        mCandleData = data;
        mDataSets.addAll(data.getDataSets());
        init();
    }

    public void setData(com.github.mikephil.charting.data.BubbleData data) {
        mBubbleData = data;
        mDataSets.addAll(data.getDataSets());
        init();
    }

    public com.github.mikephil.charting.data.BubbleData getBubbleData() {
        return mBubbleData;
    }

    public com.github.mikephil.charting.data.LineData getLineData() {
        return mLineData;
    }

    public com.github.mikephil.charting.data.BarData getBarData() {
        return mBarData;
    }

    public com.github.mikephil.charting.data.ScatterData getScatterData() {
        return mScatterData;
    }

    public com.github.mikephil.charting.data.CandleData getCandleData() {
        return mCandleData;
    }

    /**
     * Returns all data objects in row: line-bar-scatter-candle-bubble if not null.
     * @return
     */
    public java.util.List<com.github.mikephil.charting.data.ChartData> getAllData() {

        java.util.List<com.github.mikephil.charting.data.ChartData> data = new java.util.ArrayList<com.github.mikephil.charting.data.ChartData>();
        if(mLineData != null)
            data.add(mLineData);
        if(mBarData != null)
            data.add(mBarData);
        if(mScatterData != null)
            data.add(mScatterData);
        if(mCandleData != null)
            data.add(mCandleData);
        if(mBubbleData != null)
            data.add(mBubbleData);

        return data;
    }

    @Override
    public void notifyDataChanged() {
        if (mLineData != null)
            mLineData.notifyDataChanged();
        if (mBarData != null)
            mBarData.notifyDataChanged();
        if (mCandleData != null)
            mCandleData.notifyDataChanged();
        if (mScatterData != null)
            mScatterData.notifyDataChanged();
        if (mBubbleData != null)
            mBubbleData.notifyDataChanged();

        init(); // recalculate everything
    }
}
