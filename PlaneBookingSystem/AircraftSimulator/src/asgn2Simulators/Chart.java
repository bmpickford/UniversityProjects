package asgn2Simulators;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 * Class to create the charts to be used in the GUI
 * @author pickford and poole
 */
public class Chart extends ApplicationFrame {

    //Parameters to be used in the charts
    private static final String TITLE = "Booking Overview";
    XYSeriesCollection coll = new XYSeriesCollection();
    XYSeries econ = new XYSeries("Economy");
    XYSeries prem = new XYSeries("Premium");
    XYSeries business = new XYSeries("Business");
    XYSeries first = new XYSeries("First");
    XYSeries total = new XYSeries("Total");
    XYSeries empty = new XYSeries("Empty");
    XYSeries que = new XYSeries("Queue");
    XYSeries refused = new XYSeries("Refused");

    /**
     * Simple constructor for Chart
     */
    public Chart(final String title){
        super(title);
    }
    /**
     * Finalises the data that's been added for chart 1
     * @return <code> JFreeChart </code> to be added to the JFrame in the GUI
     */
    public JFreeChart finaliseChart(){
        final XYSeriesCollection dataset = finaliseData();
        JFreeChart chart = createChart(dataset);
        return chart;
    }
    /**
     * Finalises the data that's been added for chart 2
     * @return <code> JFreeChart </code> to be added to the JFrame in the GUI
     */
    public JFreeChart finaliseChart2(){
        final XYSeriesCollection dataset = finaliseSecondChartData();
        JFreeChart chart = createChart(dataset);
        return chart;
    }

    /**
     * Adds the values to the chart data every loop in the main simulation loop
     * @param s
     * @param time used for the x-axis
     * @throws SimulationException
     */
    public void addTimeSeriesData(Simulator s, int time) throws SimulationException {
    	if (time <= Constants.FIRST_FLIGHT){
    		econ.add((double)time, s.getTotalEconomy());
            prem.add((double)time, s.getTotalPremium());
            business.add((double)time, s.getTotalBusiness());
            first.add((double)time, s.getTotalFirst());
            total.add((double)time, s.getTotalFlown());
            empty.add((double)time, s.getTotalEmpty());
    	} else {
    		econ.add((double)time, s.getFlightStatus(time).getNumEconomy());
        	prem.add((double)time, s.getFlightStatus(time).getNumPremium());
        	business.add((double)time, s.getFlightStatus(time).getNumBusiness());
        	first.add((double)time, s.getFlightStatus(time).getNumFirst());
        	total.add((double)time, s.getFlightStatus(time).getTotal());
        	empty.add((double)time, s.getFlightStatus(time).getAvailable());
    	}
        que.add(time, s.numInQueue());
        refused.add(time, s.numRefused()); 
    }

    /**
     * Method that adds each individual series to the complete XYSeriesCollection for chart 1
     * @return <Code>XYSeriesCollection</Code> to be added to the dataset
     */
    private XYSeriesCollection finaliseData(){
        coll.removeAllSeries();
        coll.addSeries(first);
        coll.addSeries(business);
        coll.addSeries(prem);
        coll.addSeries(econ);
        coll.addSeries(total);
        coll.addSeries(empty);
        return coll;
    }
    /**
     * Method that adds each individual series to the complete XYSeriesCollection for chart 2
     * @return <Code>XYSeriesCollection</Code> to be added to the dataset
     */
    private XYSeriesCollection finaliseSecondChartData(){
        coll.removeAllSeries();
        coll.addSeries(que);
        coll.addSeries(refused);
        return coll;
    }

    /**
     * Method to set the axis values and set up the chart ready to be added to the GUI
     * @param dataset
     * @return <code>JFreeChart</code> to be added to the GUI
     */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createXYLineChart(TITLE, "Days", "Passengers", dataset);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return result;
    }
}
