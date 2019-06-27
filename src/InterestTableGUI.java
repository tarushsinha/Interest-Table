import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class InterestTableGUI extends JPanel{
	private JTextArea display;
	private JTextArea principalTextArea;
	private JTextArea rateTextArea;
	private JLabel principal;
	private JLabel rate;
	private JLabel numYears;
	private JSlider slider;
	private int numberOfYears = 17;

	public InterestTableGUI(){
		//Create TextArea to display interest rate calculations
		display  = new JTextArea();
		display.setFont(new Font("serif", Font.PLAIN, 18));
		JScrollPane scrollPane = new JScrollPane(display);
		scrollPane.setPreferredSize(new Dimension(800, 280));
		display.setEditable(false);
		add(scrollPane);

		//creates principal modifiers
		principal = new JLabel("Principal:");
		principal.setFont(new Font("serif", Font.PLAIN, 18));
		add(principal);
		principalTextArea = new JTextArea("5000");
		principalTextArea.setFont(new Font("serif", Font.PLAIN, 21));
		principalTextArea.setPreferredSize(new Dimension (170, 24));
		add(principalTextArea);

		//creates rate modifiers
		rate = new JLabel("Rate(Percentage):");
		rate.setFont(new Font("serif", Font.PLAIN, 18));
		add(rate);
		rateTextArea = new JTextArea("5");
		rateTextArea.setFont(new Font("serif", Font.PLAIN, 21));
		rateTextArea.setPreferredSize(new Dimension (170, 24));
		add(rateTextArea);

		//creates slider
		slider = new JSlider();
		slider.setPreferredSize(new Dimension (170,75));
		slider.setMinimum(1);
		slider.setMaximum(25);
		slider.setValue(numberOfYears);
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(4);  /* draws numbers */
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		//creates panel for slider
		JPanel sliderPanel = new JPanel();
		numYears = new JLabel("Number of Years:");
		numYears.setFont(new Font("serif", Font.PLAIN, 18));
		sliderPanel.add(numYears);
		sliderPanel.add(slider);
		add(sliderPanel);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				numberOfYears = slider.getValue();
			}
		});	

		//Create simple interest button
		JButton simpleInterest = new JButton("SimpleInterest");
		simpleInterest.setPreferredSize(new Dimension(170,50));
		simpleInterest.addActionListener(new simpleListener());

		//Create compound interest button
		JButton compoundInterest = new JButton("CompoundInterest");
		compoundInterest.setPreferredSize(new Dimension(170,50));
		compoundInterest.addActionListener(new compoundListener());

		//Create both interest button
		JButton bothInterests = new JButton("BothInterests");
		bothInterests.setPreferredSize(new Dimension(170,50));
		bothInterests.addActionListener(new bothListener());

		//Create panel for buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(simpleInterest);
		buttonsPanel.add(new JSeparator());
		buttonsPanel.add(compoundInterest);
		buttonsPanel.add(new JSeparator());
		buttonsPanel.add(bothInterests);
		add(buttonsPanel);
	}

	//Calculate simple interest
	public void simpleInterestCalculator(){
		double principal = Double.parseDouble(principalTextArea.getText());
		double rate = Double.parseDouble(rateTextArea.getText());

		String displayMsg = "Principal: " + 
				NumberFormat.getCurrencyInstance().format(principal) + ", "
				+ "Rate: " + NumberFormat.getCurrencyInstance().format(rate)
				+ "\n" + "Year, " + 
				"Simple Interest Amount" + "\n";
		display.append(displayMsg);

		for (int i = 1; i <=numberOfYears; i++){
			String calculations = i + "-->";
			calculations +=  NumberFormat.getCurrencyInstance().format(
					doSimpleInterestMath(principal, rate, i));
			calculations += "\n";
			display.append(calculations);
		}
	}

	public double doSimpleInterestMath(double principal, double rate, int time){
		double returner;
		returner = principal + (principal* (rate/100) * time);
		return returner;
	}

	public void compoundInterestCalculator(){
		double principal = Double.parseDouble(principalTextArea.getText());
		double rate = Double.parseDouble(rateTextArea.getText());

		String displayMsg = "Principal: " + 
				NumberFormat.getCurrencyInstance().format(principal) + ", "
				+ "Rate: " + NumberFormat.getCurrencyInstance().format(rate)
				+ "\n" + "Year, " + 
				"Compound Interest Amount" + "\n";
		display.append(displayMsg);

		for (int i = 1; i <=numberOfYears; i++){
			String calculations = i + "-->";
			calculations +=  NumberFormat.getCurrencyInstance().format(
					doCompoundInterestMath(principal, rate, i));
			calculations += "\n";
			display.append(calculations);
		}
	}

	public double doCompoundInterestMath(double principal, double rate, int time){
		double returner;
		returner = principal * Math.pow(1+rate/100, time);
		return returner;
	}

	public void bothInterests(){
		double principal = Double.parseDouble(principalTextArea.getText());
		double rate = Double.parseDouble(rateTextArea.getText());

		String displayMsg = "Principal: " + 
				NumberFormat.getCurrencyInstance().format(principal) + ", "
				+ "Rate: " +  NumberFormat.getCurrencyInstance().format(rate)
				+ "\n" + "Year, " + 
				"Simple Interest Amount, " + "Compound Interest Amount" + "\n";
		display.append(displayMsg);

		for (int i = 1; i <=numberOfYears; i++){
			String calculations = i + "-->";
			calculations +=  NumberFormat.getCurrencyInstance().format(
					doSimpleInterestMath(principal, rate, i));
			calculations += "-->";
			calculations += NumberFormat.getCurrencyInstance().format(
					doCompoundInterestMath(principal, rate, i));
			calculations += "\n";
			display.append(calculations);
		}

	}

	public static void createAndShowGUI(){
		int width = 800, height = 500;
		JFrame frame = new JFrame("Interest Table Calculator");
		frame.setSize(new Dimension(width, height));
		frame.setResizable(false);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setContentPane(new InterestTableGUI());

		/* Centralizing the frame */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int upperLeftCornerX = (screenSize.width - frame.getWidth()) / 2;
		int upperLeftCornerY = (screenSize.height - frame.getHeight()) / 2;

		frame.setLocation(upperLeftCornerX, upperLeftCornerY);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		/* Shows the GUI */
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private class bothListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			display.setEditable(true);
			display.setText(null);
			bothInterests();
			display.setEditable(false);
		}
	}
	
	private class compoundListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			display.setEditable(true);
			display.setText(null);
			compoundInterestCalculator();
			display.setEditable(false);
		}
	}
	
	private class simpleListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			display.setEditable(true);
			display.setText(null);
			simpleInterestCalculator();
			display.setEditable(false);
		}
	}

}