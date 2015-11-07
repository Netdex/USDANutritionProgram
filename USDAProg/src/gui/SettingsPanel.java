package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class SettingsPanel extends JPanel {

	PanelManager manager;

	private double dailyCal;

	JComboBox<String> weightUnitSelector;
	JComboBox<String> heightUnitSelector;

	private JComboBox<String> genderSelector;
	private JSpinner weightEntry;
	private JSpinner heightEntry;
	private JSpinner ageEntry;
	private JComboBox<Integer> exerciseSelector;

	public SettingsPanel(PanelManager pManager) {
		super();
		this.manager = pManager;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(GUI.BACKGROUND_COLOR);

		// header
		JPanel header = new JPanel();
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		header.setBackground(GUI.HEADER_GREY);
		header.add(new HomeButton(manager));
		JLabel title = new JLabel("PERSONALIZATION");
		title.setFont(GUI.TITLE_FONT);
		header.add(title);

		this.add(header);

		// gender selection
		JPanel genderLine = new JPanel();
		genderLine.setLayout(new FlowLayout(FlowLayout.LEFT));
		genderLine.setBackground(GUI.BACKGROUND_COLOR);
		JLabel genderPrompt = new JLabel("What is your biological gender?");
		genderPrompt.setFont(GUI.CONTENT_FONT);
		genderLine.add(genderPrompt);

		String[] genderAmounts = { "Male", "Female" };
		genderSelector = new JComboBox<String>(genderAmounts);
		genderSelector.setBackground(GUI.BACKGROUND_COLOR);
		genderSelector.setBorder(GUI.EMPTY_BORDER);
		genderLine.add(genderSelector);
		this.add(genderLine);

		// weight
		JPanel weightLine = new JPanel();
		weightLine.setLayout(new FlowLayout(FlowLayout.LEFT));
		weightLine.setBackground(GUI.BACKGROUND_COLOR);
		JLabel weightPrompt = new JLabel("Weight in: ");
		weightPrompt.setFont(GUI.CONTENT_FONT);
		weightLine.add(weightPrompt);

		weightUnitSelector = new JComboBox<String>(new String[] { "kg", "lbs" });
		weightUnitSelector.setEditable(false);
		weightUnitSelector.setBackground(GUI.BACKGROUND_COLOR);
		weightUnitSelector.setBorder(GUI.EMPTY_BORDER);
		weightLine.add(weightUnitSelector);

		weightEntry = new JSpinner();
		SpinnerNumberModel weightSelectorModel = new SpinnerNumberModel(0, 0,
				1400, 1);
		weightEntry.setModel(weightSelectorModel);
		weightLine.add(weightEntry);

		this.add(weightLine);

		// height
		JPanel heightLine = new JPanel();
		heightLine.setLayout(new FlowLayout(FlowLayout.LEFT));
		heightLine.setBackground(GUI.BACKGROUND_COLOR);
		JLabel heightPrompt = new JLabel("Height in: ");
		heightPrompt.setFont(GUI.CONTENT_FONT);
		heightLine.add(heightPrompt);

		heightUnitSelector = new JComboBox<String>(new String[] { "cm",
				"inches" });
		heightUnitSelector.setEditable(false);
		heightUnitSelector.setBackground(GUI.BACKGROUND_COLOR);
		heightUnitSelector.setBorder(GUI.EMPTY_BORDER);
		heightLine.add(heightUnitSelector);

		heightEntry = new JSpinner();
		SpinnerNumberModel heightSelectorModel = new SpinnerNumberModel(0, 0,
				300, 1);
		heightEntry.setModel(heightSelectorModel);
		heightLine.add(heightEntry);

		this.add(heightLine);

		// age
		JPanel ageLine = new JPanel();
		ageLine.setLayout(new FlowLayout(FlowLayout.LEFT));
		ageLine.setBackground(GUI.BACKGROUND_COLOR);
		JLabel agePrompt = new JLabel("How old are you, in years?");
		agePrompt.setFont(GUI.CONTENT_FONT);
		ageLine.add(agePrompt);

		ageEntry = new JSpinner();
		SpinnerNumberModel ageSelectorModel = new SpinnerNumberModel(0, 0, 150,
				1);
		ageEntry.setModel(ageSelectorModel);
		ageLine.add(ageEntry);

		this.add(ageLine);

		// exercise amount
		JPanel exerciseLine = new JPanel();
		exerciseLine.setLayout(new FlowLayout(FlowLayout.LEFT));
		exerciseLine.setBackground(GUI.BACKGROUND_COLOR);
		JLabel exercisePrompt = new JLabel(
				"How many days do you exercise per week?");
		exercisePrompt.setFont(GUI.CONTENT_FONT);
		exerciseLine.add(exercisePrompt);

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

	class SaveButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			double weight = 0;
			if (weightUnitSelector.getSelectedItem().toString().equals("kg"))
				weight = Double.parseDouble(weightEntry.getModel().getValue()
						.toString());
			else
				weight = Double.parseDouble(weightEntry.getModel().getValue()
						.toString()) / 2.20462;

			double height = 0;
			if (heightUnitSelector.getSelectedItem().toString().equals("cm"))
				height = Double.parseDouble(heightEntry.getModel().getValue()
						.toString());
			else
				height = Double.parseDouble(heightEntry.getModel().getValue()
						.toString()) / 0.393701;

			int age = 0;

			age = (int) ageEntry.getValue();

			double bmr;
			if (genderSelector.getSelectedItem().toString().equals("Male"))
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

			System.out.println(genderSelector.getSelectedItem() + " weighs "
					+ weight + "kg, is " + height + "cm tall, exercises "
					+ exerciseAmount + " times per week");

			GUI.CONFIG.addItem("userNutritionMultiplier", dailyCal / 2000);

			GUI.CONFIG.save();
		}
	}
}
