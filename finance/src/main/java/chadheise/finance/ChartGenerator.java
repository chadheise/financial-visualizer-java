package chadheise.finance;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartGenerator {

    private static final double YEARLY_RETURN = 0.08;
    private static final boolean SHOW_LEGEND = true;

    private static final String ACTUAL_SERIES_KEY = "Actual";
    private static final String EXPECTED_SERIES_KEY = "Expected";
    private static final String ADDITIONS_SERIES_KEY = "Additions";
    private static final String X_AXIS_LABEL = "Date";
    private static final String Y_AXIS_LABEL = "Value";

    private final String outputFilePath;
    private final String title;
    private final int width;
    private final int height;

    public ChartGenerator(final String outputFilePath,
            final String title, final int width, final int height) {
        this.outputFilePath = outputFilePath;
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public void output(final FinanceData financeData) throws IOException {

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(getEndingBalancesSeries(financeData));
        dataset.addSeries(getExpectedBalancesSeries(financeData, YEARLY_RETURN));
        dataset.addSeries(getAdditionsSeries(financeData));

        JFreeChart lineGraph = ChartFactory.createXYLineChart(title,
                X_AXIS_LABEL, Y_AXIS_LABEL, dataset,
                PlotOrientation.VERTICAL, SHOW_LEGEND, false, false);

        // lineGraph.getXYPlot().getDomainAxis().setRange(40, 75);
        // lineGraph.getXYPlot().getRangeAxis().setRange(0, 100);

        File file = new File(outputFilePath);
        ChartUtilities.saveChartAsPNG(file, lineGraph, width, height);
    }

    private XYSeries getEndingBalancesSeries(final FinanceData financeData) {
        XYSeries series = new XYSeries(ACTUAL_SERIES_KEY);
        double[] endingBalances = Utils.getEndingBalances(financeData);
        for (int i = 0; i < endingBalances.length; i++) {
            series.add(i, endingBalances[i]);
        }

        return series;
    }

    private XYSeries getExpectedBalancesSeries(final FinanceData financeData, final double yearlyReturn) {
        XYSeries series = new XYSeries(EXPECTED_SERIES_KEY);
        double[] endingBalances = Utils.getExpectedBalances(financeData, yearlyReturn);
        for (int i = 0; i < endingBalances.length; i++) {
            series.add(i, endingBalances[i]);
        }

        return series;
    }

    private XYSeries getAdditionsSeries(final FinanceData financeData) {
        XYSeries series = new XYSeries(ADDITIONS_SERIES_KEY);
        double[] endingBalances = Utils.getTotalAdditions(financeData);
        for (int i = 0; i < endingBalances.length; i++) {
            series.add(i, endingBalances[i]);
        }

        return series;
    }

}
