
package com.github.mikephil.charting.data;

/**
 * Subclass of Entry that holds a value for one entry in a BubbleChart. Bubble
 * chart implementation: Copyright 2015 Pierre-Marc Airoldi Licensed under
 * Apache License 2.0
 *
 * @author Philipp Jahoda
 */
public class BubbleEntry extends com.github.mikephil.charting.data.Entry {

    /** size value */
    private float mSize = 0f;

    /**
     * Constructor.
     *
     * @param xIndex The index on the x-axis.
     * @param val The value on the y-axis.
     * @param size The size of the bubble.
     */
    public BubbleEntry(int xIndex, float val, float size) {
        super(val, xIndex);

        this.mSize = size;
    }

    /**
     * Constructor.
     *
     * @param xIndex The index on the x-axis.
     * @param val The value on the y-axis.
     * @param size The size of the bubble.
     * @param data Spot for additional data this Entry represents.
     */
    public BubbleEntry(int xIndex, float val, float size, Object data) {
        super(val, xIndex, data);

        this.mSize = size;
    }

    public com.github.mikephil.charting.data.BubbleEntry copy() {

        com.github.mikephil.charting.data.BubbleEntry c = new com.github.mikephil.charting.data.BubbleEntry(getXIndex(), getVal(), mSize, getData());

        return c;
    }

    /**
     * Returns the size of this entry (the size of the bubble).
     *
     * @return
     */
    public float getSize() {
        return mSize;
    }

    public void setSize(float size) {
        this.mSize = size;
    }

}
