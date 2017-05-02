/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;


import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

/**
 * Class used to create and run the GUI
 * Runs {@Link asgn2Simulatos.SimulationRunner} through actionListener
 * @author hogan, poole, pickford
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements ActionListener, Runnable {

	public static final int WIDTH = 700;
	public static final int HEIGHT = 500;
	
	private JPanel inputVarsPnl;
	private JPanel textPnlDisplay;
	private JPanel simulationVars;
	private JPanel fareClassesVars;
	private JPanel operationPanel;
	
	private ChartPanel chartDisplay;
	private JTextArea textDisplay;
	private JScrollPane scroll;

	private JLabel simulationLbl;
	private JLabel rngSeedLbl;
	private JLabel dailyMeanLbl;
	private JLabel queSizeLbl;
	private JLabel cancellationLbl;

	private JTextField rngSeedTxt;
	private JTextField dailyMeanTxt;
	private JTextField queSizeTxt;
	private JTextField cancellationTxt;

	private JLabel fareclassesLbl;
	private JLabel firstLbl;
	private JLabel businessLbl;
	private JLabel premiumLbl;
	private JLabel economyLbl;
	
	private JTextField economyTxt;
	private JTextField businessTxt;
	private JTextField premiumTxt;
	private JTextField firstTxt;
	
	private JLabel executionLbl;
	
	private JButton runSimulationBtn;
	private JButton showChart2Btn;
	private JButton showChart1Btn;
	private JButton showTextBtn;
	
	//Variables used in actionPerformed
	private Simulator s = null;
	private Log l = null;
	private Chart c;
	private double sdDailyBookings;
	private String textOutput;
	private boolean flying;
	private final double RESET_SD = 0.0;

	/**
	 * Simple constructor
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
	}

	/**
	 * Method that originally creates the overall GUI
	 */
	public void createGUI() {
		setSize(WIDTH, HEIGHT);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new BorderLayout());
	    
	    textPnlDisplay = createPanel(Color.WHITE);
	    inputVarsPnl = createPanel(Color.DARK_GRAY);
	    simulationVars = createPanel(Color.LIGHT_GRAY);
	    fareClassesVars = createPanel(Color.LIGHT_GRAY);
	    operationPanel = createPanel(Color.LIGHT_GRAY);
	    
	    simulationLbl = new JLabel("Simulation");
	    simulationLbl.setFont(new Font("Arial",Font.BOLD,24));
		rngSeedLbl = new JLabel("RNG Seed:                         ");
		dailyMeanLbl = new JLabel("Daily Mean:");
		queSizeLbl = new JLabel("Que Size:");
		cancellationLbl = new JLabel("Cancellation:");
		
		fareclassesLbl = new JLabel("Fare Classes");
		fareclassesLbl.setFont(new Font("Arial",Font.BOLD,24));
		firstLbl = new JLabel("First:                         ");
		businessLbl = new JLabel("Business:");
		premiumLbl = new JLabel("Premium:");
		economyLbl = new JLabel("Economy:");
		
		executionLbl = new JLabel("Execution");
		executionLbl.setFont(new Font("Arial",Font.BOLD,24));

		Plot p = new XYPlot();
	    JFreeChart jf = new JFreeChart(p);
		chartDisplay = new ChartPanel(jf);
		textDisplay = createTextArea();
		scroll = new JScrollPane(textDisplay);
	    
	    rngSeedTxt = createTextFieldForPanels();
	    rngSeedTxt.setText(Integer.toString(Constants.DEFAULT_SEED));
		dailyMeanTxt = createTextFieldForPanels();
		dailyMeanTxt.setText(Double.toString(Constants.DEFAULT_DAILY_BOOKING_MEAN));
		queSizeTxt = createTextFieldForPanels();
		queSizeTxt.setText(Integer.toString(Constants.DEFAULT_MAX_QUEUE_SIZE));
		cancellationTxt = createTextFieldForPanels();
		cancellationTxt.setText(Double.toString(Constants.DEFAULT_CANCELLATION_PROB));
		
		economyTxt = createTextFieldForPanels();
		economyTxt.setText(Double.toString(Constants.DEFAULT_ECONOMY_PROB));
		businessTxt = createTextFieldForPanels();
		businessTxt.setText(Double.toString(Constants.DEFAULT_BUSINESS_PROB));
		premiumTxt = createTextFieldForPanels();
		premiumTxt.setText(Double.toString(Constants.DEFAULT_PREMIUM_PROB));
		firstTxt = createTextFieldForPanels();
		firstTxt.setText(Double.toString(Constants.DEFAULT_FIRST_PROB));
		
		runSimulationBtn = createButtonRunSimulation("Run Simulation");
		runSimulationBtn.setPreferredSize(new Dimension(120, 40));
		showChart2Btn = createButtonShowResult("Show Chart 2");
		showChart2Btn.setPreferredSize(new Dimension(120, 40));
		showChart1Btn = createButtonShowResult("Show Chart 1");
		showChart1Btn.setPreferredSize(new Dimension(120, 40));
		showTextBtn = createButtonShowResult("Show Text");
		showTextBtn.setPreferredSize(new Dimension(120, 40));

		textPnlDisplay.setLayout(new BorderLayout());

	    inputVariablesPanel(); 
	    inputVariablesIntoPanel(simulationVars, simulationLbl);
	    inputVariablesIntoPanel(fareClassesVars, fareclassesLbl);
	    inputButtonsAndTitleIntoExecutionPanel();
	 
	    this.getContentPane().add(textPnlDisplay,BorderLayout.CENTER);
	    this.getContentPane().add(inputVarsPnl,BorderLayout.SOUTH);

	    repaint(); 
	    this.setVisible(true);
	}

	/**
	 * If the user enters command line arguments, this sets them in the GUI
	 * @param args
     */
	public void setArgs(String[] args){
		rngSeedTxt.setText(args[1]);
		queSizeTxt.setText(args[2]);
		dailyMeanTxt.setText(args[3]);
		sdDailyBookings = Double.parseDouble(args[4]);
		firstTxt.setText(args[5]);
		businessTxt.setText(args[6]);
		premiumTxt.setText(args[7]);
		economyTxt.setText(args[8]);
		cancellationTxt.setText(args[9]);
	}

	/**
	 * Method that takes the button inputs and displays the correct components
	 * accordingly
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//Variables to hold the inputs
		int range;
		double mean;
		int que;
		double cancelProb;
		double econ;
		double business;
		double premium;
		double first;

		//Error handling
		try {
			range = Integer.parseInt(rngSeedTxt.getText());
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this,"Range must be an integer","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			mean = Double.parseDouble(dailyMeanTxt.getText());
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this,"Mean must be an double","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			que = Integer.parseInt(queSizeTxt.getText());
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this,"Que must be an integer","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			cancelProb = Double.parseDouble(cancellationTxt.getText());
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this,"Cancellation must be an double","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			first = Double.parseDouble(firstTxt.getText());
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this,"First must be an double","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			business = Double.parseDouble(businessTxt.getText());
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this,"Business must be an double","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			premium = Double.parseDouble(premiumTxt.getText());
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this,"Premium must be an double","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			econ = Double.parseDouble(economyTxt.getText());
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this,"Econ must be an double","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (range < 0) {
			JOptionPane.showMessageDialog(this,"RNG Seed must be greater than or equal to 0","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (mean < 0) {
			JOptionPane.showMessageDialog(this,"Daily mean must be greater than or equal to 0","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (que < 0) {
			JOptionPane.showMessageDialog(this,"Que size must be greater than or equal to 0","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if ((cancelProb < 0) || (cancelProb > 1)) {
			JOptionPane.showMessageDialog(this,"Cancellation must be greater than or equal to 0 but less than or equal to 1","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if ((first < 0) || (first > 1)) {
			JOptionPane.showMessageDialog(this,"First must be greater than or equal to 0 but less than or equal to 1","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if ((business < 0) || (business > 1)) {
			JOptionPane.showMessageDialog(this,"Business must be greater than or equal to 0 but less than or equal to 1","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if ((premium < 0) || (premium > 1)) {
			JOptionPane.showMessageDialog(this,"Premium must be greater than or equal to 0 but less than or equal to 1","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if ((econ < 0) || (econ > 1)) {
			JOptionPane.showMessageDialog(this,"Econ must be greater than or equal to 0 but less than or equal to 1","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}

		if ((first + business + econ + premium) != 1){
			JOptionPane.showMessageDialog(this,"Fare classes must add up to 1","Input error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//below needs to be done for when standard deviation is not set by args
		if (sdDailyBookings == RESET_SD) {
			sdDailyBookings = 0.33 * mean;
		}

		Component source = (Component) e.getSource();
		if(source == runSimulationBtn){
			c = new Chart("Chart");
			showChart2Btn.setEnabled(true);
			showChart1Btn.setEnabled(true);
			showTextBtn.setEnabled(false);
			removeComponents();

			//Creation of simulation using inputs
			try {
				s = new Simulator(range, que, mean, sdDailyBookings, first, business, premium, econ, cancelProb);
				l = new Log();
			} catch (SimulationException | IOException e1) {
				e1.printStackTrace();
			}

			SimulationRunner sr = new SimulationRunner(s,l);
			try {
				s.createSchedule();
				l.initialEntry(s);

				//Main Simulation loop
				for (int time=0; time<=Constants.DURATION; time++) {
					sr.runSimulation(s, l, time, c);
					flying = (time >= Constants.FIRST_FLIGHT);
					if (time == 0){
						textOutput = "Start of Simulation\n";
						textOutput += s.toString() + "\n";
						String capacities = s.getFlights(Constants.FIRST_FLIGHT).initialState();
						textOutput += capacities;
						textOutput += s.getSummary(time, flying);
					} else {
						textOutput += s.getSummary(time, flying);
					}
				}
				
				//Finalise log
				s.finaliseQueuedAndCancelledPassengers(Constants.DURATION);
				l.logQREntries(Constants.DURATION, s);
				l.finalise(s);

				textOutput += "\n" + s.finalState();
				
				//Text output and scrollbar
				setUpText();
				setUpScroll();

			} catch (Exception e1) {
				e1.printStackTrace();
				System.exit(-1);
			}
			
			//need to reset sd to allow for user to run simulation again with different inputs 
			sdDailyBookings = RESET_SD; 
		}
		//Show chart 2
		if (source == showChart2Btn){
			addChart(c.finaliseChart2());
			setVisible(true);
			showChart2Btn.setEnabled(false);
			showChart1Btn.setEnabled(true);
			showTextBtn.setEnabled(true);
		}
		//Show chart 1
		if (source == showChart1Btn){
			addChart(c.finaliseChart());
			setVisible(true);
			showChart2Btn.setEnabled(true);
			showChart1Btn.setEnabled(false);
			showTextBtn.setEnabled(true);
		}
		//Re-Show text output
		if (source == showTextBtn){
			removeComponents();
			setUpText();
			setUpScroll();
			showChart2Btn.setEnabled(true);
			showChart1Btn.setEnabled(true);
			showTextBtn.setEnabled(false);
		}
	}

	/**
	 * Simple helper method to create a panel
	 * @param c
	 * @return <code>JPanel</code> to be added to the GUI
     */
	private JPanel createPanel(Color c) {
		JPanel jp = new JPanel();
		jp.setBackground(c);
		return jp;
	}

	/**
	 * Simple helper method to create the text area in the GUI
	 * @return <code>JTextArea</code> to be added to the GUI
     */
	private JTextArea createTextArea() {
		JTextArea jta = new JTextArea(); 
		jta.setEditable(false);
		jta.setLineWrap(true);
		jta.setFont(new Font("Arial",Font.BOLD,24));
		jta.setBorder(BorderFactory.createEtchedBorder());
		return jta;
	}

	/**
	 * 	/**
	 * Simple helper method to create the text field in the GUI
	 * @return <code>JTextField</code> to be added to the GUI
     */
	private JTextField createTextFieldForPanels() {
		JTextField jta = new JTextField();
		jta.setEditable(true);
		jta.setColumns(4);
		jta.setFont(new Font("Arial",Font.BOLD,12));
		jta.setBorder(BorderFactory.createEtchedBorder());
		return jta;
	}

	/**
	 * Method to add the input panels to the GUI
	 */
	private void inputVariablesPanel() {
		GridBagLayout layout = new GridBagLayout();
		inputVarsPnl.setLayout(layout);
	    
	    //add components to grid
	    GridBagConstraints constraints = new GridBagConstraints(); 
	    
	    //Defaults
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.CENTER;
	    constraints.weightx = 100;
	    constraints.weighty = 100;
	    
	    addToPanel(inputVarsPnl, simulationVars,constraints,0,0,1,2); 
	    addToPanel(inputVarsPnl, fareClassesVars,constraints,1,0,1,2); 
	    addToPanel(inputVarsPnl, operationPanel,constraints,2,0,1,2); 
	}

	/**
	 * Sets up the input fields for the GUI
	 * @param panel
	 * @param label
     */
	private void inputVariablesIntoPanel(JPanel panel, JLabel label) {
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);

		//add components to grid
		GridBagConstraints constraints = new GridBagConstraints();
		GridBagConstraints titleConstraints = new GridBagConstraints();
		GridBagConstraints textFieldsConstraints = new GridBagConstraints();

		//Defaults
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx = 100;
		constraints.weighty = 100;

		titleConstraints.fill = GridBagConstraints.NONE;
		titleConstraints.anchor = GridBagConstraints.NORTH;
		titleConstraints.weightx = 100;
		titleConstraints.weighty = 100;

		textFieldsConstraints.fill = GridBagConstraints.NONE;
		textFieldsConstraints.anchor = GridBagConstraints.EAST;
		textFieldsConstraints.weightx = 100;
		textFieldsConstraints.weighty = 100;

		addToPanel(panel, label,titleConstraints,0,0,4,1);
		if (panel.equals(simulationVars)) {
			addToPanel(panel, rngSeedLbl, constraints, 0, 1, 2, 1);
			addToPanel(panel, dailyMeanLbl, constraints, 0, 2, 2, 1);
			addToPanel(panel, queSizeLbl, constraints, 0, 3, 2, 1);
			addToPanel(panel, cancellationLbl, constraints, 0, 4, 2, 1);

			addToPanel(panel, rngSeedTxt, textFieldsConstraints, 0, 1, 1, 1);
			addToPanel(panel, dailyMeanTxt, textFieldsConstraints, 0, 2, 1, 1);
			addToPanel(panel, queSizeTxt, textFieldsConstraints, 0, 3, 1, 1);
			addToPanel(panel, cancellationTxt, textFieldsConstraints, 0, 4, 1, 1);
		} else {
			addToPanel(panel, firstLbl,constraints,0,1,2,1);
			addToPanel(panel, businessLbl,constraints,0,2,2,1);
			addToPanel(panel, premiumLbl,constraints,0,3,2,1);
			addToPanel(panel, economyLbl,constraints,0,4,2,1);

			addToPanel(panel, firstTxt,textFieldsConstraints,0,1,1,1);
			addToPanel(panel, businessTxt,textFieldsConstraints,0,2,1,1);
			addToPanel(panel, premiumTxt,textFieldsConstraints,0,3,1,1);
			addToPanel(panel, economyTxt,textFieldsConstraints,0,4,1,1);
		}
	}

	/**
	 * Displays, and sets up the button layout for the GUI
	 */
	private void inputButtonsAndTitleIntoExecutionPanel() {
		GridBagLayout layout = new GridBagLayout();
		operationPanel.setLayout(layout);
	    
	    //add components to grid
	    GridBagConstraints titleConstraints = new GridBagConstraints();
	    GridBagConstraints buttonConstraints = new GridBagConstraints(); 
	    
	    //Defaults
	    titleConstraints.fill = GridBagConstraints.NONE;
	    titleConstraints.anchor = GridBagConstraints.NORTH;
	    titleConstraints.weightx = 100;
	    titleConstraints.weighty = 100;
	    
	    buttonConstraints.fill = GridBagConstraints.NONE;
	    buttonConstraints.anchor = GridBagConstraints.SOUTH;
	    buttonConstraints.weightx = 100;
	    buttonConstraints.weighty = 100;
	    
	    addToPanel(operationPanel, executionLbl,titleConstraints,1,0,4,1);
	    addToPanel(operationPanel, runSimulationBtn,buttonConstraints,0,1,2,1);
	    addToPanel(operationPanel, showChart2Btn,buttonConstraints,0,2,2,1);
	    addToPanel(operationPanel, showChart1Btn,buttonConstraints,3,1,2,1);
	    addToPanel(operationPanel, showTextBtn,buttonConstraints,3,2,2,1);
	}

	/**
	 * Simple method to create the simulation button
	 * @param str
	 * @return <code>JButton</code> to be added to the GUI
     */
	private JButton createButtonRunSimulation(String str) {
		JButton jb = new JButton(str); 
		jb.addActionListener(this);
		return jb;
	}

	/**
	 * Simple method to create the graph, and show text buttons
	 * @param str
	 * @return <code>JButton</code> to be added to the GUI
	 */
	private JButton createButtonShowResult(String str) {
		JButton jb = new JButton(str); 
		jb.addActionListener(this);
		jb.setEnabled(false);
		return jb;
	}

	/**
     * 
     * A convenience method to add a component to given grid bag
     * layout locations. Code due to Cay Horstmann 
     *
     * @param c the component to add
     * @param constraints the grid bag constraints to use
     * @param x the x grid position
     * @param y the y grid position
     * @param w the grid width
     * @param h the grid height
     */
   private void addToPanel(JPanel jp,Component c, GridBagConstraints constraints, int x, int y, int w, int h) {  
      constraints.gridx = x;
      constraints.gridy = y;
      constraints.gridwidth = w;
      constraints.gridheight = h;
      jp.add(c, constraints);
   }
   
	/** (non-Javadoc)
	 * Runs GUI if args[] has NOT been set by the command line
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		createGUI();
	}
	
	/**(non-Javadoc)
	 * Runs GUI if args[] has been set by the command line
	 * @see java.lang.Runnable#run()
	 */
	public void run(String[] args) {
		createGUI();
		setArgs(args);
	}

	/**
	 * Method to add the chart to the GUI
	 * @param chart
     */
	private void addChart(JFreeChart chart){
		removeComponents();
		textPnlDisplay.setLayout(new BorderLayout());
		textPnlDisplay.add(chartDisplay, BorderLayout.CENTER);
		chartDisplay.setChart(chart);
	}

	/**
	 * Removes existing components of the textPnlDisplay so
	 * there is no conflict with other components being added
	 */
	private void removeComponents(){
		chartDisplay.setChart(null);
		textDisplay.setText(null);
		textPnlDisplay.remove(textDisplay);
		textPnlDisplay.remove(scroll);
		textPnlDisplay.remove(chartDisplay);
	}

	/**
	 * Simple method to set up the JScrollPane
	 */
	private void setUpScroll(){
		scroll = new JScrollPane(textDisplay);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setEnabled(true);
		scroll.setVisible(true);
		textPnlDisplay.add(scroll);
		setVisible(true);
	}

	/**
	 * Simple method to re-set up the JTextArea
	 */
	private void setUpText(){
		textDisplay = createTextArea();
		textDisplay.append(textOutput);
		textDisplay.setFont(new Font("Arial",Font.BOLD,12));
	}

}