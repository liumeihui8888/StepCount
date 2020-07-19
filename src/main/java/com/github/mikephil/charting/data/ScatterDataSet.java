
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.charts.ScatterChart.ScatterShape;
import com.github.mikephil.charting.utils.Utils;

public class ScatterDataSet extends LineScatterCandleRadarDataSet<com.github.mikephil.charting.data.Entry> {

    /** the size the scattershape will have, in screen pixels */
    private float mShapeSize = 15f;

    /**
     * the type of shape that is set to be drawn where the values are at,
     * default ScatterShape.SQUARE
     */
    private ScatterShape mScatterShape = ScatterShape.SQUARE;

    /**
     * Custom path object the user can provide that is drawn where the values
     * are at. This is used when ScatterShape.CUSTOM is set for a DataSet.
     */
    private android.graphics.Path mCustomScatterPath = null;

    public ScatterDataSet(java.util.List<com.github.mikephil.charting.data.Entry> yVals, String label) {
        super(yVals, label);

        // mShapeSize = Utils.convertDpToPixel(8f);
    }

    @Override
    public com.github.mikephil.charting.data.DataSet<Entry> copy() {

        java.util.List<com.github.mikephil.charting.data.Entry> yVals = new java.util.ArrayList<com.github.mikephil.charting.data.Entry>();

        for (int i = 0; i < mYVals.size(); i++) {
            yVals.add(mYVals.get(i).copy());
        }

        com.github.mikephil.charting.data.ScatterDataSet copied = new com.github.mikephil.charting.data.ScatterDataSet(yVals, getLabel());
        copied.mColors = mColors;
        copied.mShapeSize = mShapeSize;
        copied.mScatterShape = mScatterShape;
        copied.mCustomScatterPath = mCustomScatterPath;
        copied.mHighLightColor = mHighLightColor;

        return copied;
    }

    /**
     * Sets the size in density pixels the drawn scattershape will have. This
     * only applies for non custom shapes.
     * 
     * @param size
     */
    public void setScatterShapeSize(float size) {
        mShapeSize = Utils.convertDpToPixel(size);
    }

    /**
     * returns the currently set scatter shape size
     * 
     * @return
     */
    public float getScatterShapeSize() {
        return mShapeSize;
    }

    /**
     * Sets the shape that is drawn on the position where the values are at. If
     * "CUSTOM" is chosen, you need to call setCustomScatterShape(...) and
     * provide a path object that is drawn as the custom scattershape.
     * 
     * @param shape
     */
    public void setScatterShape(ScatterShape shape) {
        mScatterShape = shape;
    }

    /**
     * returns all the different scattershapes the chart uses
     * 
     * @return
     */
    public ScatterShape getScatterShape() {
        return mScatterShape;
    }

    /**
     * Sets a path object as the shape to be drawn where the values are at. Do
     * not forget to call setScatterShape(...) and set the shape to
     * ScatterShape.CUSTOM.
     * 
     * @param shape
     */
    public void setCustomScatterShape(android.graphics.Path shape) {
        mCustomScatterPath = shape;
    }

    /**
     * returns the custom path / shape that is specified to be drawn where the
     * values are at
     * 
     * @return
     */
    public android.graphics.Path getCustomScatterShape() {
        return mCustomScatterPath;
    }
}
