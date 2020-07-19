package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.components.YAxis;

/**
 * Created by Philipp Jahoda on 20/09/15.
 * Default formatter used for formatting labels of the YAxis. Uses a DecimalFormat with
 * pre-calculated number of digits (depending on max and min value).
 */
public class DefaultYAxisValueFormatter implements com.github.mikephil.charting.formatter.YAxisValueFormatter {

    /** decimalformat for formatting */
    private java.text.DecimalFormat mFormat;

    /**
     * Constructor that specifies to how many digits the value should be
     * formatted.
     *
     * @param digits
     */
    public DefaultYAxisValueFormatter(int digits) {

        StringBuffer b = new StringBuffer();
        for (int i = 0; i < digits; i++) {
            if (i == 0)
                b.append(".");
            b.append("0");
        }

        mFormat = new java.text.DecimalFormat("###,###,###,##0" + b.toString());
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        // avoid memory allocations here (for performance)
        return mFormat.format(value);
    }
}
