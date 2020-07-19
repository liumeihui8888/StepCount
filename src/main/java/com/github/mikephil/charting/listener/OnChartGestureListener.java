package com.github.mikephil.charting.listener;

/**
 * Listener for callbacks when doing gestures on the chart.
 *
 * @author Philipp Jahoda
 */
public interface OnChartGestureListener {

    /**
     * Callbacks when a touch-gesture has started on the chart (ACTION_DOWN)
     *
     * @param me
     * @param lastPerformedGesture
     */
    void onChartGestureStart(android.view.MotionEvent me, com.github.mikephil.charting.listener.ChartTouchListener.ChartGesture lastPerformedGesture);

    /**
     * Callbacks when a touch-gesture has ended on the chart (ACTION_UP, ACTION_CANCEL)
     *
     * @param me
     * @param lastPerformedGesture
     */
    void onChartGestureEnd(android.view.MotionEvent me, com.github.mikephil.charting.listener.ChartTouchListener.ChartGesture lastPerformedGesture);

    /**
     * Callbacks when the chart is longpressed.
     *
     * @param me
     */
    void onChartLongPressed(android.view.MotionEvent me);

    /**
     * Callbacks when the chart is double-tapped.
     *
     * @param me
     */
    void onChartDoubleTapped(android.view.MotionEvent me);

    /**
     * Callbacks when the chart is single-tapped.
     *
     * @param me
     */
    void onChartSingleTapped(android.view.MotionEvent me);

    /**
     * Callbacks then a fling gesture is made on the chart.
     *
     * @param me1
     * @param me2
     * @param velocityX
     * @param velocityY
     */
    void onChartFling(android.view.MotionEvent me1, android.view.MotionEvent me2, float velocityX, float velocityY);

    /**
     * Callbacks when the chart is scaled / zoomed via pinch zoom gesture.
     *
     * @param me
     * @param scaleX scalefactor on the x-axis
     * @param scaleY scalefactor on the y-axis
     */
    void onChartScale(android.view.MotionEvent me, float scaleX, float scaleY);

    /**
     * Callbacks when the chart is moved / translated via drag gesture.
     *
     * @param me
     * @param dX translation distance on the x-axis
     * @param dY translation distance on the y-axis
     */
    void onChartTranslate(android.view.MotionEvent me, float dX, float dY);
}
