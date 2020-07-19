
package com.github.mikephil.charting.listener;

import com.github.mikephil.charting.highlight.Highlight;

/**
 * Touchlistener for the PieChart.
 *
 * @author Philipp Jahoda
 */
public class PieRadarChartTouchListener extends com.github.mikephil.charting.listener.ChartTouchListener<com.github.mikephil.charting.charts.PieRadarChartBase<?>> {

    private android.graphics.PointF mTouchStartPoint = new android.graphics.PointF();

    /**
     * the angle where the dragging started
     */
    private float mStartAngle = 0f;

    private java.util.ArrayList<com.github.mikephil.charting.listener.PieRadarChartTouchListener.AngularVelocitySample> _velocitySamples = new java.util.ArrayList<com.github.mikephil.charting.listener.PieRadarChartTouchListener.AngularVelocitySample>();

    private long mDecelerationLastTime = 0;
    private float mDecelerationAngularVelocity = 0.f;

    public PieRadarChartTouchListener(com.github.mikephil.charting.charts.PieRadarChartBase<?> chart) {
        super(chart);
    }

    @android.annotation.SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(android.view.View v, android.view.MotionEvent event) {

        if (mGestureDetector.onTouchEvent(event))
            return true;

        // if rotation by touch is enabled
        if (mChart.isRotationEnabled()) {

            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {

                case android.view.MotionEvent.ACTION_DOWN:

                    startAction(event);

                    stopDeceleration();

                    resetVelocity();

                    if (mChart.isDragDecelerationEnabled())
                        sampleVelocity(x, y);

                    setGestureStartAngle(x, y);
                    mTouchStartPoint.x = x;
                    mTouchStartPoint.y = y;

                    break;
                case android.view.MotionEvent.ACTION_MOVE:

                    if (mChart.isDragDecelerationEnabled())
                        sampleVelocity(x, y);

                    if (mTouchMode == NONE
                            && distance(x, mTouchStartPoint.x, y, mTouchStartPoint.y)
                            > com.github.mikephil.charting.utils.Utils.convertDpToPixel(8f)) {
                        mLastGesture = com.github.mikephil.charting.listener.ChartTouchListener.ChartGesture.ROTATE;
                        mTouchMode = ROTATE;
                        mChart.disableScroll();
                    } else if (mTouchMode == ROTATE) {
                        updateGestureRotation(x, y);
                        mChart.invalidate();
                    }

                    endAction(event);

                    break;
                case android.view.MotionEvent.ACTION_UP:

                    if (mChart.isDragDecelerationEnabled()) {

                        stopDeceleration();

                        sampleVelocity(x, y);

                        mDecelerationAngularVelocity = calculateVelocity();

                        if (mDecelerationAngularVelocity != 0.f) {
                            mDecelerationLastTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();

                            com.github.mikephil.charting.utils.Utils.postInvalidateOnAnimation(mChart); // This causes computeScroll to fire, recommended for this by Google
                        }
                    }

                    mChart.enableScroll();
                    mTouchMode = NONE;

                    endAction(event);

                    break;
            }
        }

        return true;
    }

    @Override
    public void onLongPress(android.view.MotionEvent me) {

        mLastGesture = com.github.mikephil.charting.listener.ChartTouchListener.ChartGesture.LONG_PRESS;

        com.github.mikephil.charting.listener.OnChartGestureListener l = mChart.getOnChartGestureListener();

        if (l != null) {
            l.onChartLongPressed(me);
        }
    }

    @Override
    public boolean onSingleTapConfirmed(android.view.MotionEvent e) {
        return true;
    }

    @Override
    public boolean onSingleTapUp(android.view.MotionEvent e) {

        mLastGesture = com.github.mikephil.charting.listener.ChartTouchListener.ChartGesture.SINGLE_TAP;

        com.github.mikephil.charting.listener.OnChartGestureListener l = mChart.getOnChartGestureListener();

        if (l != null) {
            l.onChartSingleTapped(e);
        }

        if(!mChart.isHighlightPerTapEnabled()) {
            return false;
        }

        float distance = mChart.distanceToCenter(e.getX(), e.getY());

        // check if a slice was touched
        if (distance > mChart.getRadius()) {

            // if no slice was touched, highlight nothing

            if (mLastHighlighted == null)
                mChart.highlightValues(null); // no listener callback
            else
                mChart.highlightTouch(null); // listener callback

            mLastHighlighted = null;

        } else {

            float angle = mChart.getAngleForPoint(e.getX(), e.getY());

            if (mChart instanceof com.github.mikephil.charting.charts.PieChart) {
                angle /= mChart.getAnimator().getPhaseY();
            }

            int index = mChart.getIndexForAngle(angle);

            // check if the index could be found
            if (index < 0) {

                mChart.highlightValues(null);
                mLastHighlighted = null;

            } else {

                java.util.List<com.github.mikephil.charting.utils.SelectionDetail> valsAtIndex = mChart.getSelectionDetailsAtIndex(index);

                int dataSetIndex = 0;

                // get the dataset that is closest to the selection (PieChart
                // only
                // has one DataSet)
                if (mChart instanceof com.github.mikephil.charting.charts.RadarChart) {

                    dataSetIndex = com.github.mikephil.charting.utils.Utils.getClosestDataSetIndex(valsAtIndex, distance
                            / ((com.github.mikephil.charting.charts.RadarChart) mChart).getFactor(), null);
                }

                if (dataSetIndex < 0) {
                    mChart.highlightValues(null);
                    mLastHighlighted = null;
                } else {
                    Highlight h = new Highlight(index, dataSetIndex);
                    performHighlight(h, e);
                }
            }
        }

        return true;
    }

    private void resetVelocity() {
        _velocitySamples.clear();
    }

    private void sampleVelocity(float touchLocationX, float touchLocationY) {

        long currentTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();

        _velocitySamples.add(new com.github.mikephil.charting.listener.PieRadarChartTouchListener.AngularVelocitySample(currentTime, mChart.getAngleForPoint(touchLocationX, touchLocationY)));

        // Remove samples older than our sample time - 1 seconds
        for (int i = 0, count = _velocitySamples.size(); i < count - 2; i++) {
            if (currentTime - _velocitySamples.get(i).time > 1000) {
                _velocitySamples.remove(0);
                i--;
                count--;
            } else {
                break;
            }
        }
    }

    private float calculateVelocity() {

        if (_velocitySamples.isEmpty())
            return 0.f;

        com.github.mikephil.charting.listener.PieRadarChartTouchListener.AngularVelocitySample firstSample = _velocitySamples.get(0);
        com.github.mikephil.charting.listener.PieRadarChartTouchListener.AngularVelocitySample lastSample = _velocitySamples.get(_velocitySamples.size() - 1);

        // Look for a sample that's closest to the latest sample, but not the same, so we can deduce the direction
        com.github.mikephil.charting.listener.PieRadarChartTouchListener.AngularVelocitySample beforeLastSample = firstSample;
        for (int i = _velocitySamples.size() - 1; i >= 0; i--) {
            beforeLastSample = _velocitySamples.get(i);
            if (beforeLastSample.angle != lastSample.angle) {
                break;
            }
        }

        // Calculate the sampling time
        float timeDelta = (lastSample.time - firstSample.time) / 1000.f;
        if (timeDelta == 0.f) {
            timeDelta = 0.1f;
        }

        // Calculate clockwise/ccw by choosing two values that should be closest to each other,
        // so if the angles are two far from each other we know they are inverted "for sure"
        boolean clockwise = lastSample.angle >= beforeLastSample.angle;
        if (Math.abs(lastSample.angle - beforeLastSample.angle) > 270.0) {
            clockwise = !clockwise;
        }

        // Now if the "gesture" is over a too big of an angle - then we know the angles are inverted, and we need to move them closer to each other from both sides of the 360.0 wrapping point
        if (lastSample.angle - firstSample.angle > 180.0) {
            firstSample.angle += 360.0;
        } else if (firstSample.angle - lastSample.angle > 180.0) {
            lastSample.angle += 360.0;
        }

        // The velocity
        float velocity = Math.abs((lastSample.angle - firstSample.angle) / timeDelta);

        // Direction?
        if (!clockwise) {
            velocity = -velocity;
        }

        return velocity;
    }

    /**
     * sets the starting angle of the rotation, this is only used by the touch
     * listener, x and y is the touch position
     *
     * @param x
     * @param y
     */
    public void setGestureStartAngle(float x, float y) {
        mStartAngle = mChart.getAngleForPoint(x, y) - mChart.getRawRotationAngle();
    }

    /**
     * updates the view rotation depending on the given touch position, also
     * takes the starting angle into consideration
     *
     * @param x
     * @param y
     */
    public void updateGestureRotation(float x, float y) {
        mChart.setRotationAngle(mChart.getAngleForPoint(x, y) - mStartAngle);
    }

    /**
     * Sets the deceleration-angular-velocity to 0f
     */
    public void stopDeceleration() {
        mDecelerationAngularVelocity = 0.f;
    }

    public void computeScroll() {

        if (mDecelerationAngularVelocity == 0.f)
            return; // There's no deceleration in progress

        final long currentTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();

        mDecelerationAngularVelocity *= mChart.getDragDecelerationFrictionCoef();

        final float timeInterval = (float) (currentTime - mDecelerationLastTime) / 1000.f;

        mChart.setRotationAngle(mChart.getRotationAngle() + mDecelerationAngularVelocity * timeInterval);

        mDecelerationLastTime = currentTime;

        if (Math.abs(mDecelerationAngularVelocity) >= 0.001)
            com.github.mikephil.charting.utils.Utils.postInvalidateOnAnimation(mChart); // This causes computeScroll to fire, recommended for this by Google
        else
            stopDeceleration();
    }

    private class AngularVelocitySample {

        public long time;
        public float angle;

        public AngularVelocitySample(long time, float angle) {
            this.time = time;
            this.angle = angle;
        }
    }
}
