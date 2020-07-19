
package com.github.mikephil.charting.data;

public class RadarDataSet extends LineRadarDataSet<com.github.mikephil.charting.data.Entry> {
    
    public RadarDataSet(java.util.List<com.github.mikephil.charting.data.Entry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public com.github.mikephil.charting.data.DataSet<Entry> copy() {

        java.util.List<com.github.mikephil.charting.data.Entry> yVals = new java.util.ArrayList<com.github.mikephil.charting.data.Entry>();

        for (int i = 0; i < mYVals.size(); i++) {
            yVals.add(mYVals.get(i).copy());
        }

        com.github.mikephil.charting.data.RadarDataSet copied = new com.github.mikephil.charting.data.RadarDataSet(yVals, getLabel());
        copied.mColors = mColors;
        copied.mHighLightColor = mHighLightColor;

        return copied;
    }
}
