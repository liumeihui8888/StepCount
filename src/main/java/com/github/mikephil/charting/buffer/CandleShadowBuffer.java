
package com.github.mikephil.charting.buffer;

public class CandleShadowBuffer extends com.github.mikephil.charting.buffer.AbstractBuffer<com.github.mikephil.charting.data.CandleEntry> {

    public CandleShadowBuffer(int size) {
        super(size);
    }

    private void addShadow(float x1, float y1, float x2, float y2) {

        buffer[index++] = x1;
        buffer[index++] = y1;
        buffer[index++] = x2;
        buffer[index++] = y2;
    }

    @Override
    public void feed(java.util.List<com.github.mikephil.charting.data.CandleEntry> entries) {

        int size = (int)Math.ceil((mTo - mFrom) * phaseX + mFrom);

        for (int i = mFrom; i < size; i++) {

            com.github.mikephil.charting.data.CandleEntry e = entries.get(i);
            addShadow(e.getXIndex(), e.getHigh() * phaseY, e.getXIndex(), e.getLow() * phaseY);
        }

        reset();
    }
}
