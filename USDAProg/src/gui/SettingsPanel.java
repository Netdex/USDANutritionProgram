package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import config.ConfigurationManager;

public class SettingsPanel extends JPanel {

	PanelManager manager;

	private boolean kilograms;
	private boolean centimeters;
	private double dailyCal;

	private JComboBox<String> genderSelector;
	private JTextField weightEntry;
	private JTextField heightEntry;
	private JTextField ageEntry;
	private JComboBox<Integer> exerciseSelector;

	private ConfigurationManager config;

	public SettingsPanel(PanelManager pManager) {
		super();
		this.manager = pManager;
		config = new ConfigurationManager(new File("config.txt"));
		config.load();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(new Color(250, 250, 250));

		// header
		JPanel header = new JPanel();
		header.setLayout(new FlowLayout(FlowLayout.LEFT));

		try {
			header.add(new HomeButton(manager));
		} catch (IOException e) {
		}

		JLabel title = new JLabel("SETTINGS");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
		header.add(title);

		this.add(header);

		// gender selection
		JPanel genderLine = new JPanel();
		genderLine.setLayout(new FlowLayout(FlowLayout.LEFT));
		genderLine.add(new JLabel("What is your biological gender?"));

		String[] genderAmounts = { "Male", "Female" };
		genderSelector = new JComboBox<String>(genderAmounts);
		genderLine.add(genderSelector);
		this.add(genderLine);

		// weight
		JPanel weightLine = new JPanel();
		weightLine.setLayout(new FlowLayout(FlowLayout.LEFT));
		weightLine.add(new JLabel("Weight in: "));

		JComboBox<String> weightUnitSelector = new JComboBox<String>(
				new String[] { "kg", "lbs" });
		weightUnitSelector.setEditable(false);
		weightUnitSelector.addActionListener(new WeightBoxListener());
		weightLine.add(weightUnitSelector);

		weightEntry = new JTextField();
		if (kilograms)
			weightEntry.setText("Weight in kg");
		else
			weightEntry.setText("Weight in lbs");
		weightEntry.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				weightEntry.setText("");
			}
		});
		weightLine.add(weightEntry);

		this.add(weightLine);

		// height
		JPanel heightLine = new JPanel();
		heightLine.setLayout(new FlowLayout(FlowLayout.LEFT));
		heightLine.add(new JLabel("Height in: "));

		JComboBox<String> heightUnitSelector = new JComboBox<String>(
				new String[] { "cm", "inches" });
		heightUnitSelector.setEditable(false);
		heightUnitSelector.addActionListener(new HeightBoxListener());
		heightLine.add(heightUnitSelector);

		heightEntry = new JTextField();
		if (centimeters)
			heightEntry.setText("Height in cm");
		else
			heightEntry.setText("Height in inches");
		heightEntry.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				heightEntry.setText("");
			}
		});
		heightLine.add(heightEntry);

		this.add(heightLine);

		// age
		JPanel ageLine = new JPanel();
		ageLine.setLayout(new FlowLayout(FlowLayout.LEFT));
		ageLine.add(new JLabel("How old are you, in years?"));

		ageEntry = new JTextField("Age");
		ageEntry.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				ageEntry.setText("");
			}
		});
		ageLine.add(ageEntry);

		this.add(ageLine);

		// exercise amount
		JPanel exerciseLine = new JPanel();
		exerciseLine.setLayout(new FlowLayout(FlowLayout.LEFT));
		exerciseLine.add(new JLabel("How many days do you exercise per week?"));

		Integer[] exerciseAmounts = { 0, 1, 2, 3, 4, 5, 6, 7 };
		exerciseSelector = new JComboBox<Integer>(exerciseAmounts);
		exerciseLine.add(exerciseSelector);

		this.add(exerciseLine);

		// Save button at the bottom
		JButton saveButton = new JButton("Save Changes");
		saveButton.addActionListener(new SaveButtonListener());
		saveButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(saveButton);
	}

	class WeightBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> weightUnitSelector = (JComboBox<String>) e
					.getSource();
			String unit = weightUnitSelector.getSelectedItem().toString();
			if (unit.equals("kg"))
				kilograms = true;
			else if (unit.equals("lbs"))
				kilograms = false;
		}
	}

	class HeightBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> heightUnitSelector = (JComboBox<String>) e
					.getSource();
			String unit = heightUnitSelector.getSelectedItem().toString();
			if (unit.equals("cm"))
				centimeters = true;
			else if (unit.equals("lbs"))
				centimeters = false;
		}
	}

	class SaveButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			double weight = 0;
			try {
				if (kilograms)
					weight = Double.parseDouble(weightEntry.getText());
				else
					weight = Double.parseDouble(weightEntry.getText()) / 2.20462;
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(SettingsPanel.this,
						"Please enter a weight", "Invalid weight",
						JOptionPane.ERROR_MESSAGE);
			}

			double height = 0;
			try {
				if (centimeters)
					height = Double.parseDouble(heightEntry.getText());
				else
					height = Double.parseDouble(heightEntry.getText()) / 0.393701;
			} catch (NumberFormatException exc) {
				JOptionPane.showMessageDialog(SettingsPanel.this,
						"Please enter a height", "Invalid height",
						JOptionPane.ERROR_MESSAGE);
			}

			int age = 0;
			try {
				age = Integer.parseInt(ageEntry.getText());
			} catch (NumberFormatException whee) {
				JOptionPane.showMessageDialog(SettingsPanel.this,
						"Please enter an age", "Invalid age",
						JOptionPane.ERROR_MESSAGE);
			}

			double bmr;
			if (genderSelector.getSelectedItem().equals("Male"))
				bmr = 88.362 + (13.397 * weight) + (4.799 * height)
						- (5.677 * age);
			else
				bmr = 447.593 + (9.247 * weight) + (3.098 * height)
						- (4.330 * age);

			int exerciseAmount = exerciseSelector.getSelectedIndex();
			if (exerciseAmount == 0) {
				dailyCal = bmr * 1.2;
			} else if (exerciseAmount <= 1 && exerciseAmount >= 3) {
				dailyCal = bmr * 1.375;
			} else if (exerciseAmount <= 3 && exerciseAmount >= 5) {
				dailyCal = bmr * 1.55;
			} else if (exerciseAmount == 6) {
				dailyCal = bmr * 1.725;
			} else
				dailyCal = bmr * 1.9;
			
			config.addItem("weight", kilograms + "");
			config.addItem("height", centimeters + "");
			config.addItem("dailyCal", dailyCal + "");
		}
	}
}
