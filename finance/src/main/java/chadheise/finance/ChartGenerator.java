package chadheise.finance;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class ChartGenerator {

    private static final boolean SHOW_LEGEND = true;

    private static final String ACTUAL_SERIES_KEY = "Balance";
    private static final String ADDITIONS_SERIES_KEY = "Invested";
    private static final String EXPECTED_SERIES_KEY = "Expected";
    private static final String X_AXIS_LABEL = "Date";
    private static final String Y_AXIS_LABEL = "Value";

    private final String outputFilePath;
    private final String title;
    private final int width;
    private final int height;
    private final double yearlyReturn;
    private final int startYear;

    public ChartGenerator(final String outputFilePath,
            final String title, final int width, final int height, final double yearlyReturn, final int startYear) {
        this.outputFilePath = outputFilePath;
        this.title = title;
        this.width = width;
        this.height = height;
        this.yearlyReturn = yearlyReturn;
        this.startYear = startYear;
    }

    public void output(final FinanceData financeData) throws IOException {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(getEndingBalanceSeries(financeData));
        dataset.addSeries(getAdditionsSeries(financeData));
        dataset.addSeries(getExpectedBalancesSeries(financeData, yearlyReturn));

        JFreeChart lineGraph = ChartFactory.createTimeSeriesChart(title,
                X_AXIS_LABEL, Y_AXIS_LABEL, dataset,
                SHOW_LEGEND, false, false);

        addDataMarkers(lineGraph);
        setLineColors(lineGraph.getXYPlot().getRenderer());
        addDollarFormat(lineGraph);

        File file = new File(outputFilePath);
        ChartUtils.saveChartAsPNG(file, lineGraph, width, height);
    }

    private TimeSeries getEndingBalanceSeries(final FinanceData financeData) {
        TimeSeries series = new TimeSeries(ACTUAL_SERIES_KEY);
        for (LocalDate date : financeData.getDates()) {
            if (date.getYear() >= startYear) {
                Day day = new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
                series.add(day, financeData.getEndingBalance(date));
            }
        }

        return series;
    }

    private TimeSeries getAdditionsSeries(final FinanceData financeData) {
        Map<LocalDate, Double> totalAdditions = Utils.getTotalAdditions(financeData);

        TimeSeries series = new TimeSeries(ADDITIONS_SERIES_KEY);
        for (LocalDate date : financeData.getDates()) {
            if (date.getYear() >= startYear) {
                Day day = new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
                series.add(day, totalAdditions.get(date));
            }
        }

        return series;
    }

    private TimeSeries getExpectedBalancesSeries(final FinanceData financeData, final double yearlyReturn) {
        Map<LocalDate, Double> expectedBalances = Utils.getExpectedBalances(financeData, yearlyReturn);

        TimeSeries series = new TimeSeries(EXPECTED_SERIES_KEY + " @ " + yearlyReturn + " per year");
        for (LocalDate date : financeData.getDates()) {
            if (date.getYear() >= startYear) {
                Day day = new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
                series.add(day, expectedBalances.get(date));
            }
        }
        return series;
    }

    private void addDataMarkers(final JFreeChart chart) {
        Shape circle = new Ellipse2D.Double(-3, -3, 6, 6);
        XYItemRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShape(0, circle);
        renderer.setSeriesShape(1, circle);
        renderer.setSeriesShape(2, circle);
        chart.getXYPlot().setRenderer(renderer);
    }

    private void setLineColors(final XYItemRenderer renderer) {
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesPaint(2, Color.GREEN);
    }

    private void addDollarFormat(final JFreeChart chart) {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        NumberAxis rangeAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        rangeAxis.setNumberFormatOverride(currency);
    }

}
