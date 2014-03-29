package evolutionaryAlgoritm;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 20.02.14
 * Time: 02:16
 */
public class GraphVisualization extends JFrame {

    public GraphVisualization(String title, ArrayList<Double> fitness, ArrayList<Double> averageFitness, ArrayList<Double> std) {
        super("EA GraphVisualization");
        XYDataset graphData = createGraphData(fitness, averageFitness, std);
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Generation",
                "Value",
                graphData,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesLinesVisible(2, true);
        renderer.setSeriesShapesVisible(2, true);
        plot.setRenderer(renderer);

        NumberAxis xAxis = new NumberAxis("Generation");
        xAxis.setTickUnit(new NumberTickUnit(5));
        plot.setDomainAxis(xAxis);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 500));
        setContentPane(chartPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createGraphData(ArrayList<Double> fitness, ArrayList<Double> averageFitness, ArrayList<Double> std) {
        XYSeries series1 = new XYSeries("Best fitness");
        XYSeries series2 = new XYSeries("Average fitness");
        XYSeries series3 = new XYSeries("Standard deviation");

        for (int i = 0; i < fitness.size(); i++) {
            series1.add(i, fitness.get(i));
            series2.add(i, averageFitness.get(i));
            series3.add(i, std.get(i));
        }

        XYSeriesCollection graphData = new XYSeriesCollection();
        graphData.addSeries(series1);
        graphData.addSeries(series2);
        graphData.addSeries(series3);
        return graphData;
    }
}