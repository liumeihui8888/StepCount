package com.github.mikephil.charting.renderer;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.BarLineScatterCandleBubbleDataProvider;

/**
 * Renderer class that is responsible for rendering multiple different data-types.
 */
public class CombinedChartRenderer extends com.github.mikephil.charting.renderer.DataRenderer {

	/**
	 * all rederers for the different kinds of data this combined-renderer can draw
	 */
	protected java.util.List<DataRenderer> mRenderers;

	public CombinedChartRenderer(com.github.mikephil.charting.charts.CombinedChart chart, ChartAnimator animator, com.github.mikephil.charting.utils.ViewPortHandler viewPortHandler) {
		super(animator, viewPortHandler);

		createRenderers(chart, animator, viewPortHandler);
	}

	/**
	 * Creates the renderers needed for this combined-renderer in the required order. Also takes the DrawOrder into
	 * consideration.
	 * 
	 * @param chart
	 * @param animator
	 * @param viewPortHandler
	 */
	protected void createRenderers(com.github.mikephil.charting.charts.CombinedChart chart, ChartAnimator animator, com.github.mikephil.charting.utils.ViewPortHandler viewPortHandler) {

		mRenderers = new java.util.ArrayList<DataRenderer>();

		com.github.mikephil.charting.charts.CombinedChart.DrawOrder[] orders = chart.getDrawOrder();

		for (com.github.mikephil.charting.charts.CombinedChart.DrawOrder order : orders) {

			switch (order) {
			case BAR:
				if (chart.getBarData() != null)
					mRenderers.add(new com.github.mikephil.charting.renderer.BarChartRenderer(chart, animator, viewPortHandler));
				break;
			case BUBBLE:
				if (chart.getBubbleData() != null)
					mRenderers.add(new com.github.mikephil.charting.renderer.BubbleChartRenderer(chart, animator, viewPortHandler));
				break;
			case LINE:
				if (chart.getLineData() != null)
					mRenderers.add(new com.github.mikephil.charting.renderer.LineChartRenderer(chart, animator, viewPortHandler));
				break;
			case CANDLE:
				if (chart.getCandleData() != null)
					mRenderers.add(new CandleStickChartRenderer(chart, animator, viewPortHandler));
				break;
			case SCATTER:
				if (chart.getScatterData() != null)
					mRenderers.add(new com.github.mikephil.charting.renderer.ScatterChartRenderer(chart, animator, viewPortHandler));
				break;
			}
		}
	}

	@Override
	public void initBuffers() {

		for (com.github.mikephil.charting.renderer.DataRenderer renderer : mRenderers)
			renderer.initBuffers();
	}

	@Override
	public void drawData(android.graphics.Canvas c) {

		for (com.github.mikephil.charting.renderer.DataRenderer renderer : mRenderers)
			renderer.drawData(c);
	}

	@Override
	public void drawValues(android.graphics.Canvas c) {

		for (com.github.mikephil.charting.renderer.DataRenderer renderer : mRenderers)
			renderer.drawValues(c);
	}

	@Override
	public void drawExtras(android.graphics.Canvas c) {

		for (com.github.mikephil.charting.renderer.DataRenderer renderer : mRenderers)
			renderer.drawExtras(c);
	}

	@Override
	public void drawHighlighted(android.graphics.Canvas c, Highlight[] indices) {
		for (com.github.mikephil.charting.renderer.DataRenderer renderer : mRenderers)
			renderer.drawHighlighted(c, indices);
	}

	@Override
	public void calcXBounds(BarLineScatterCandleBubbleDataProvider chart, int xAxisModulus) {
		for (com.github.mikephil.charting.renderer.DataRenderer renderer : mRenderers)
			renderer.calcXBounds(chart, xAxisModulus);
	}

	/**
	 * Returns the sub-renderer object at the specified index.
	 * 
	 * @param index
	 * @return
	 */
	public com.github.mikephil.charting.renderer.DataRenderer getSubRenderer(int index) {
		if (index >= mRenderers.size() || index < 0)
			return null;
		else
			return mRenderers.get(index);
	}

	/**
	 * Returns all sub-renderers.
	 * 
	 * @return
	 */
	public java.util.List<DataRenderer> getSubRenderers() {
		return mRenderers;
	}

	public void setSubRenderers(java.util.List<DataRenderer> renderers) {
		this.mRenderers = renderers;
	}
}
