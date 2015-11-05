package gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SettingsPanel extends JPanel {

	GUI gui;

	boolean kilograms;
	boolean centimeters;

	JComboBox<String> genderSelector;
	JTextField weightEntry;
	JTextField heightEntry;
	JTextField ageEntry;
	JComboBox<Integer> exerciseSelector;

	public SettingsPanel(GUI gui) {
		super();
		this.gui = gui;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// header
		JPanel header = new JPanel();
		header.setLayout(new FlowLayout());

		JButton homeButton;
		try {
			// TODO make graphic
			homeButton = new JButton(new ImageIcon(ImageIO.read(new File(
					"images/homeButton.png"))));
		} catch (IOException e) {
			homeButton = null;
		}
		header.add(homeButton);

		JLabel title = new JLabel("SETTINGS");
		title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		header.add(title);

		this.add(header);

		// gender selection
		JPanel genderLine = new JPanel();
		genderLine.setLayout(new FlowLayout());
		genderLine.add(new JLabel("What is your biological gender?"));

		String[] genderAmounts = { "Male", "Female" };
		genderSelector = new JComboBox<String>(genderAmounts);
		genderLine.add(genderSelector);

		// weight
		JPanel weightLine = new JPanel();
		weightLine.setLayout(new FlowLayout());
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
		weightLine.add(weightEntry);

		this.add(weightLine);

		// height
		JPanel heightLine = new JPanel();
		heightLine.setLayout(new FlowLayout());
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
		heightLine.add(heightEntry);

		this.add(heightLine);

		// age
		JPanel ageLine = new JPanel();
		ageLine.setLayout(new FlowLayout());
		ageLine.add(new JLabel("How old are you, in years?"));

		ageEntry = new JTextField("Age");
		ageLine.add(ageEntry);

		this.add(ageLine);

		// exercise amount
		JPanel exerciseLine = new JPanel();
		exerciseLine.setLayout(new FlowLayout());
		exerciseLine
				.add(new JLabel("How many days do\nyou exercise per week?"));

		Integer[] exerciseAmounts = { 0, 1, 2, 3, 4, 5, 6, 7 };
		exerciseSelector = new JComboBox<Integer>(exerciseAmounts);
		exerciseLine.add(exerciseSelector);

		this.add(exerciseLine);

		// Save button at the bottom
		JButton saveButton = new JButton("Save Changes");
		saveButton.addActionListener(new SaveButtonListener());
		saveButton.setHorizontalAlignment(SwingConstants.RIGHT);
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

			double weight;
			if (kilograms)
				weight = Double.parseDouble(weightEntry.getText());
			else
				weight = Double.parseDouble(weightEntry.getText()) / 2.20462;

			double height;
			if (centimeters)
				height = Double.parseDouble(heightEntry.getText());
			else
				height = Double.parseDouble(heightEntry.getText()) / 0.393701;

			int age = Integer.parseInt(ageEntry.getText());

			double bmr;
			if (genderSelector.getSelectedItem().equals("Male"))
				bmr = 88.362 + (13.397 * weight) + (4.799 * height)
						- (5.677 * age);
			else
				bmr = 447.593 + (9.247 * weight) + (3.098 * height)
						- (4.330 * age);

			int exerciseAmount = exerciseSelector.getSelectedIndex();
			if (exerciseAmount == 0) {
				gui.setDailyCal(bmr * 1.2);
			} else if (exerciseAmount <= 1 && exerciseAmount >= 3) {
				gui.setDailyCal(bmr * 1.375);
			} else if (exerciseAmount <= 3 && exerciseAmount >= 5) {
				gui.setDailyCal(bmr * 1.55);
			} else if (exerciseAmount == 6) {
				gui.setDailyCal(bmr * 1.725);
			} else
				gui.setDailyCal(bmr * 1.9);
		}
	}
}
