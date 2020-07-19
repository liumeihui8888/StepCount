
package com.github.mikephil.charting.data;

/**
 * Baseclass for all Line, Bar, Scatter, Candle and Bubble data.
 * 
 * @author Philipp Jahoda
 */
public abstract class BarLineScatterCandleBubbleData<T extends BarLineScatterCandleBubbleDataSet<? extends com.github.mikephil.charting.data.Entry>>
        extends com.github.mikephil.charting.data.ChartData<T> {
    
    public BarLineScatterCandleBubbleData() {
        super();
    }
    
    public BarLineScatterCandleBubbleData(java.util.List<String> xVals) {
        super(xVals);
    }
    
    public BarLineScatterCandleBubbleData(String[] xVals) {
        super(xVals);
    }

    public BarLineScatterCandleBubbleData(java.util.List<String> xVals, java.util.List<T> sets) {
        super(xVals, sets);
    }

    public BarLineScatterCandleBubbleData(String[] xVals, java.util.List<T> sets) {
        super(xVals, sets);
    }
}
