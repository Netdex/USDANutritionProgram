package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import parser.DataManager;
import parser.FattyAcid;
import parser.ImageExtract;
import parser.parsables.FoodItem;
import parser.parsables.Nutrient;
import parser.parsables.WeightUnit;
import parser.util.DoublyLinkedList;

/**
 * The information panel representing a single food item.
 * 
 * @author Vince Ou
 *
 */
class InfoPanel extends JPanel {

	/**
	 * The food which information it is displaying
	 */
	private FoodItem food;

	/**
	 * The panel that directed here from search (if applicable)
	 */
	private final SearchPanel searchPanel;
	/**
	 * The card layout switcher manager
	 */
	private final PanelManager manager;

	/**
	 * The header panel
	 */
	private final JPanel header;
	// --Commented out by Inspection START (11/20/2015 12:10 PM):
	// /**
	// * The name of the food
	// */
	// private String titleName;
	// --Commented out by Inspection STOP (11/20/2015 12:10 PM)
	/**
	 * The label containing the title name
	 */
	private final JLabel titleLabel;
	/**
	 * The back button, going to either the search panel or the food group list
	 */
	private final BackButton back;

	/**
	 * The panel holding the contents
	 */
	private final JPanel contentPanel;
	/**
	 * The panel inside content panel, holding the nutrients
	 */
	private JPanel nutritionPanel;
	/**
	 * Makes it scrollable
	 */
	private final CustomScrollPane contentScrollbar;

	/**
	 * The text to prompt the user to enter an amount of food
	 */
	private String amountEntryPromptText;
	/**
	 * The area that has the prompt text
	 */
	private JTextArea amountEntryPrompt;
	/**
	 * The JSpinner that lets the user enter an amount of food they are
	 * consuming
	 */
	private JSpinner amountEntry;

	/**
	 * Allows the user select a unit to measure their food in
	 */
	private JComboBox<WeightUnit> unitSelection;
	/**
	 * The unit the user has selected
	 */
	private WeightUnit selectedUnit = WeightUnit.GRAM;
	/**
	 * The number of grams in the sample of food that nutritional information is
	 * being displayed for
	 */
	private double gramsOfFood = 1;
	/**
	 * The amount of units (used to calculate the number of grams of food)
	 */
	private double amountOfUnits = 1;

	/**
	 * An array of nutrients that are in the food
	 */
	private Nutrient[] nutrients;
	/**
	 * An array of the panels, each holding information about one nutrient.
	 */
	private NutrientInfoLine[] nutritionLines;

	/**
	 * The border around the nutrition panel
	 */
	private static final javax.swing.border.Border ACCENT_BORDER = BorderFactory
			.createLineBorder(GUI.HEADER_COLOUR, 2);

	public InfoPanel(SearchPanel searchPanel, PanelManager manager) {
		// sets things up
		super();
		this.searchPanel = searchPanel;
		this.manager = manager;
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.BACKGROUND_COLOUR);

		// Framework for the header
		header = new JPanel();
		header.setLayout(new BorderLayout());
		// Adds a home and back button
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.setBackground(GUI.HEADER_COLOUR);
		buttonPanel.add(new HomeButton(manager));
		back = new BackButton(this.searchPanel, this.manager);
		buttonPanel.add(back);
		header.add(buttonPanel, BorderLayout.WEST);

		// Adds the title label
		titleLabel = new JLabel();
		titleLabel.setFont(GUI.TITLE_FONT);
		titleLabel.setForeground(GUI.TITLE_COLOUR);
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		header.add(titleLabel, BorderLayout.CENTER);

		// Adds a button that links to the "more info" panel
		JButton moreInfo = new JButton();
		try {
			moreInfo.setIcon(new ImageIcon(ImageIO.read(
					new File("images/moreInfoButton.png")).getScaledInstance(
					48, 48, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(this,
					"Cannot find image for \"Extra Info\" button",
					"Image Not Found", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}
		// more set up
		moreInfo.setFocusable(false);
		moreInfo.addActionListener(new MoreInfoButtonListener());
		moreInfo.setBackground(GUI.HEADER_COLOUR);
		moreInfo.setBorder(GUI.EMPTY_BORDER);
		header.add(moreInfo, BorderLayout.EAST);
		header.setBackground(GUI.HEADER_COLOUR);

		// "Shows" the header
		this.add(header, BorderLayout.NORTH);

		// Creates the content panel for the actual information
		contentPanel = new JPanel();
		BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(contentLayout);
		contentPanel.setBackground(GUI.BACKGROUND_COLOUR);
		contentPanel.setOpaque(false);
		contentPanel.setMaximumSize(new Dimension(465, Short.MAX_VALUE));

		// Enables scrolling
		contentScrollbar = new CustomScrollPane(contentPanel);
		this.add(contentScrollbar, BorderLayout.CENTER);
	}

	/**
	 * Sets the food item, and is called upon each "refresh" of the page
	 * 
	 * @param item
	 *            the FoodItem being displayed
	 */
	void setFoodItem(FoodItem item) {
		// Clears the panel
		contentPanel.removeAll();
		this.food = item;
		// Changes some set up variables
		if (food.getWeightInfo().getWeightUnits() != null)
			gramsOfFood = food.getWeightInfo().getWeightUnits()[0]
					.getGramWeight();
		else
			gramsOfFood = 1;

		// Changes the title
		String titleName = DataManager.getInstance().getRelevantKeywords(food);
		titleName = toTitleCase(titleName);
		titleLabel.setText(titleName);

		// adds an image
		JLabel imageLabel = new JLabel();
		ImageExtract.injectImage(imageLabel, titleName);
		contentPanel.add(imageLabel);
		contentPanel.revalidate();
		contentPanel.repaint();

		// adds long name in actual page
		JTextArea longName = new JTextArea(food.getLongDescription());
		longName.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
		longName.setFont(GUI.SUBTITLE_FONT);
		longName.setWrapStyleWord(true);
		longName.setEditable(false);
		longName.setLineWrap(true);
		longName.setForeground(GUI.HEADER_COLOUR);
		longName.setOpaque(false);
		longName.setFocusable(false);
		longName.setAlignmentX(LEFT_ALIGNMENT);
		contentPanel.add(longName);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 9)));

		// adds common name info
		if (!food.getCommonName().equals("")) {
			JTextArea commonName = new JTextArea("Other name(s) include: "
					+ food.getCommonName());
			commonName.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
			commonName.setFont(GUI.CONTENT_FONT);
			commonName.setWrapStyleWord(true);
			commonName.setEditable(false);
			commonName.setForeground(GUI.CONTENT_COLOUR);
			commonName.setOpaque(false);
			commonName.setFocusable(false);
			commonName.setLineWrap(true);
			commonName.setAlignmentX(LEFT_ALIGNMENT);
			contentPanel.add(commonName);
			contentPanel.add(Box.createRigidArea(new Dimension(0, 9)));
		}

		// adds food group info
		JTextArea gramsOfNutrientLabel = new JTextArea("Food Group: "
				+ food.getFoodGroup().toString());
		gramsOfNutrientLabel
				.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
		gramsOfNutrientLabel.setFont(GUI.CONTENT_FONT);
		gramsOfNutrientLabel.setAlignmentX(LEFT_ALIGNMENT);
		gramsOfNutrientLabel.setWrapStyleWord(true);
		gramsOfNutrientLabel.setLineWrap(true);
		gramsOfNutrientLabel.setForeground(GUI.CONTENT_COLOUR);
		gramsOfNutrientLabel.setEditable(false);
		gramsOfNutrientLabel.setFocusable(false);
		gramsOfNutrientLabel.setOpaque(false);
		contentPanel.add(gramsOfNutrientLabel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 9)));

		// add scientific name
		if (!food.getScientificName().equals("")) {
			JTextArea scientificName = new JTextArea("Scientific name: "
					+ food.getScientificName());
			scientificName.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
			scientificName.setFont(GUI.SCIENTIFIC_FONT);
			scientificName.setAlignmentX(LEFT_ALIGNMENT);
			scientificName.setWrapStyleWord(true);
			scientificName.setLineWrap(true);
			scientificName.setEditable(false);
			scientificName.setFocusable(false);
			scientificName.setForeground(GUI.CONTENT_COLOUR);
			scientificName.setOpaque(false);
			contentPanel.add(scientificName);
			contentPanel.add(Box.createRigidArea(new Dimension(0, 9)));
		}

		// add manufacturer name
		if (!food.getManufacturerName().equals("")) {
			JTextArea manufacName = new JTextArea("Manufactured by: "
					+ food.getManufacturerName());
			manufacName.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
			manufacName.setFont(GUI.CONTENT_FONT);
			manufacName.setAlignmentX(LEFT_ALIGNMENT);
			manufacName.setWrapStyleWord(true);
			manufacName.setLineWrap(true);
			manufacName.setEditable(false);
			manufacName.setFocusable(false);
			manufacName.setOpaque(false);
			manufacName.setForeground(GUI.CONTENT_COLOUR);
			contentPanel.add(manufacName);
			contentPanel.add(Box.createRigidArea(new Dimension(0, 9)));
		}

		// Prompts the user to select a unit to measure with
		amountEntryPromptText = "This item is measured in grams.\n"
				+ "Please enter the number of grams you are intending on consuming.";
		if (food.getWeightInfo().getWeightUnits() != null) {
			// Gets the possible units
			DoublyLinkedList<WeightUnit> possibleUnits = new DoublyLinkedList<WeightUnit>(
					food.getWeightInfo().getWeightUnits());
			possibleUnits.add(WeightUnit.GRAM);
			if (possibleUnits.size() > 1) {
				// Creates a framework for the text
				JPanel unitSelectionLine = new JPanel(new FlowLayout(
						FlowLayout.LEFT));
				unitSelectionLine.setOpaque(false);
				unitSelectionLine.setMinimumSize(new Dimension(400, 0));

				// The actual prompt asking the user
				JTextArea unitPrompt = new JTextArea(
						"What unit will you be measuring your food with?");
				unitPrompt.setMinimumSize(new Dimension(300, 0));
				unitPrompt.setFont(GUI.CONTENT_FONT);
				unitPrompt.setWrapStyleWord(true);
				unitPrompt.setLineWrap(true);
				unitPrompt.setEditable(false);
				unitPrompt.setFocusable(false);
				unitPrompt.setForeground(GUI.CONTENT_COLOUR);
				unitPrompt.setOpaque(false);
				unitSelectionLine.add(unitPrompt);

				// Takes input from the user to select a unit of measure
				unitSelection = new JComboBox<WeightUnit>(
						possibleUnits.toArray(WeightUnit.SAMPLE));
				unitSelection.setForeground(GUI.CONTENT_COLOUR);
				unitSelection.setFont(GUI.CONTENT_FONT);
				unitSelection.setBackground(GUI.BACKGROUND_COLOUR);
				unitSelection.setEditable(false);
				unitSelection.setFocusable(false);
				unitSelection.addActionListener(new UnitSelectorListener());
				unitSelectionLine.add(unitSelection);

				// Adds the unit selection components to the panel
				unitSelectionLine.setAlignmentX(LEFT_ALIGNMENT);
				contentPanel.add(unitSelectionLine);
				contentPanel.add(Box.createRigidArea(new Dimension(0, 9)));

				// Changes the "default" text prompting the user for an amount
				// of food in a certain unit, depending on whether or not there
				// are specific units of weight being used.
				selectedUnit = unitSelection.getModel().getElementAt(0);
				String selectedUnitName = selectedUnit.getDesc();
				int separatorIndex = selectedUnitName.indexOf('(');
				if (separatorIndex < 1)
					separatorIndex = selectedUnitName.length();
				amountEntryPromptText = "You have selected to measure this food in \""
						+ selectedUnitName
						+ "\".\nPlease enter the amount of "
						+ selectedUnitName.substring(0, separatorIndex).trim()
						+ "(s) you are intending to consume.";
			}
		}

		// asks the user for how much food they are consuming
		JPanel amountEntryLine = new JPanel(new BorderLayout());
		amountEntryLine.setOpaque(false);
		amountEntryLine.setMaximumSize(new Dimension(450, Short.MAX_VALUE));

		// Prompts user to enter an amount of food
		amountEntryPrompt = new JTextArea(amountEntryPromptText);
		amountEntryPrompt.setPreferredSize(new Dimension(350, 80));
		amountEntryPrompt.setFont(GUI.CONTENT_FONT);
		amountEntryPrompt.setAlignmentX(LEFT_ALIGNMENT);
		amountEntryPrompt.setWrapStyleWord(true);
		amountEntryPrompt.setLineWrap(true);
		amountEntryPrompt.setForeground(GUI.CONTENT_COLOUR);
		amountEntryPrompt.setEditable(false);
		amountEntryPrompt.setFocusable(false);
		amountEntryPrompt.setOpaque(false);
		amountEntryLine.add(amountEntryPrompt, BorderLayout.CENTER);

		// Takes input from the user on the amount of food desired
		SpinnerNumberModel amountEntryModel = new SpinnerNumberModel(1.000,
				1.000, GUI.SPINNER_MAX, 0.500);
		amountEntry = new JSpinner(amountEntryModel);
		amountEntry.setBackground(GUI.BACKGROUND_COLOUR);
		amountEntry.setFont(GUI.CONTENT_FONT);
		amountEntry.setAlignmentX(LEFT_ALIGNMENT);
		amountEntry.setForeground(GUI.CONTENT_COLOUR);
		amountEntry.setBackground(GUI.BACKGROUND_COLOUR);
		amountEntry.addChangeListener(new AmountEntryListener());
		amountEntryLine.add(amountEntry, BorderLayout.EAST);

		// Adds the food amount entry components
		amountEntryLine.setAlignmentX(LEFT_ALIGNMENT);
		contentPanel.add(amountEntryLine);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 9)));

		// Sets up a framework for displaying nutrients
		nutritionPanel = new JPanel();
		nutritionPanel
				.setLayout(new BoxLayout(nutritionPanel, BoxLayout.Y_AXIS));
		nutritionPanel.setBorder(ACCENT_BORDER);
		nutritionPanel.setAlignmentX(LEFT_ALIGNMENT);
		nutritionPanel.setBackground(GUI.BACKGROUND_COLOUR);

		// Gets the nutrients
		nutrients = food.getNutrientData().getNutrients()
				.toArray(Nutrient.SAMPLE);
		nutritionLines = new NutrientInfoLine[nutrients.length];
		// Creates a line for each nutrient with its name and amount
		for (int i = 0; i < nutrients.length; i++) {
			NutrientInfoLine nutrientPanel = new NutrientInfoLine(nutrients[i]);
			nutritionLines[i] = nutrientPanel;
			nutritionPanel.add(nutrientPanel);
		}
		// Sets up and adds the amount for the nutrient to the panel
		nutritionPanel.setMaximumSize(new Dimension(460, Short.MAX_VALUE));
		nutritionPanel.revalidate();
		nutritionPanel.repaint();
		contentPanel.add(nutritionPanel);

		// Makes things show up properly
		contentPanel.revalidate();
		contentPanel.repaint();

		// Scrolls to top
		contentScrollbar.getVerticalScrollBar().setValue(0);
	}

	/**
	 * Converts a String to title case (i.e. first letter after a space is
	 * capitalized, all others are lower case
	 * 
	 * @param str
	 *            String to convert
	 * @return the original string in title case
	 */
	private String toTitleCase(String str) {
		// sets all to lower case
		char[] array = str.toCharArray();
		String out = "";
		// Checks if the previous character was a space, if it was, then set the
		// current to capital, and just adds the one if else.
		for (int i = 0; i < str.length(); i++) {
			if (i == 0 || array[i - 1] == ' ')
				out += Character.toUpperCase(array[i]);
			else
				out += Character.toLowerCase(array[i]);
		}
		return out;
	}

	/**
	 * Gets the back button (necessary for setting up the "target" for the back
	 * button)
	 * 
	 * @return the back button
	 */
	BackButton getBackButton() {
		return back;
	}

	/**
	 * Forces the nutrient fields to update, displaying their true data
	 */
	private void updateNutrientFields() {
		// Calls all the nutrient fields to update
		for (NutrientInfoLine nutrientInfoLine : nutritionLines) {
			nutrientInfoLine.updateFields();
		}

		// Repaints to make sure content is being displayed correctly
		nutritionPanel.revalidate();
		nutritionPanel.repaint();
		contentPanel.revalidate();
		contentPanel.repaint();
	}

	/**
	 * Updates the prompt for amount entry, based on the new selected unit
	 */
	private void updateAmountEntryPrompt() {
		// Gets the name of the unit
		String selectedUnitName = selectedUnit.getDesc();
		int separatorIndex = selectedUnitName.indexOf('(');
		if (separatorIndex < 1)
			separatorIndex = selectedUnitName.length();
		// Changes the text
		amountEntryPromptText = "You have selected to measure this food in \""
				+ selectedUnitName + "\".\nPlease enter the amount of "
				+ selectedUnitName.substring(0, separatorIndex).trim()
				+ "(s) you are intending to consume.";
		// Refreshes the text field
		amountEntryPrompt.setText(amountEntryPromptText);
		amountEntryPrompt.revalidate();
		amountEntryPrompt.repaint();
	}

	/**
	 * What happens when you change the unit with the selector
	 * 
	 * @author Vince Ou
	 *
	 */
	private class UnitSelectorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Changes the selected unit
			selectedUnit = (WeightUnit) unitSelection.getModel()
					.getSelectedItem();

			// Updates prompts
			updateAmountEntryPrompt();

			// Updates the amount of grams of food to be examined
			gramsOfFood = selectedUnit.getGramWeight() * amountOfUnits;

			// Updates the nutrients too
			updateNutrientFields();
		}
	}

	/**
	 * What happens when you change the amount of food being examined
	 * 
	 * @author Vince Ou
	 *
	 */
	private class AmountEntryListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			// Changes the amount of units, and the amount of food (in grams)
			amountOfUnits = Double.parseDouble(amountEntry.getModel()
					.getValue().toString());
			if (food.getWeightInfo() != null)
				gramsOfFood = amountOfUnits * selectedUnit.getGramWeight();
			else
				// if there are no units, then it is automatically in grams
				gramsOfFood = amountOfUnits;

			// update all of the labels
			updateNutrientFields();
		}

	}

	/**
	 * What happens when you click on the "more info" button
	 * 
	 * @author Vince
	 *
	 */
	private class MoreInfoButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Sets up the more info panel, then switches to it.
			manager.getExtraInfoPanel().setFood(food);
			manager.switchToExtraInfo();
		}

	}

	/**
	 * Each line is used to represent one nutrient, in order to reduce the
	 * amount of messy code.
	 * 
	 * @author Vince Ou
	 *
	 */
	class NutrientInfoLine extends JPanel {

		/**
		 * The nutrient itself
		 */
		private final Nutrient nutrient;
		/**
		 * The amount of this nutrient per gram of food
		 */
		private final double amountPerGram;

		/**
		 * The amount of this nutrient in the current sample
		 */
		private final JLabel amount;
		/**
		 * The label containing the name of the nutrient
		 */
		private final JLabel nameLabel;

		private NutrientInfoLine(Nutrient nut) {
			// Sets up things
			super();
			this.nutrient = nut;
			this.amountPerGram = nutrient.getNutrVal() / 100;
			this.setBorder(GUI.EMPTY_BORDER);
			this.setLayout(new BorderLayout());
			this.setOpaque(false);
			this.setMaximumSize(new Dimension(400, Short.MAX_VALUE));

			// Gets fatty acid names, if applicable
			String nutrientName = nutrient.getNutrientInfo().getNutrientName();
			if (nutrientName.matches(".*[0-9]*:[0-9]*.*")) {
				Matcher m = Pattern.compile("[0-9]*:[0-9]*").matcher(
						nutrientName);
				m.find();
				int start = m.start();
				int end = m.end();
				FattyAcid[] proteinNames = FattyAcid
						.lookupByCDRatio(m.group(0));
				if (proteinNames != null && proteinNames.length > 0) {
					String proteinName = "";
					for (FattyAcid fa : proteinNames) {
						proteinName += fa.getName() + "; ";
					}
					nutrientName = nutrientName.substring(0, start)
							+ proteinName
									.substring(0, proteinName.length() - 2)
							+ nutrientName.substring(end);
				}
			}
			// Sets up the nutrient name label with the correct text
			nameLabel = new JLabel("<html>" + nutrientName + " ("
					+ nutrient.getNutrientInfo().getUnit() + ")</html>");
			nameLabel.setFont(GUI.CONTENT_FONT);
			nameLabel.setOpaque(false);
			nameLabel.setForeground(GUI.CONTENT_COLOUR);
			nameLabel.setFocusable(false);
			nameLabel.setPreferredSize(new Dimension(300, 40));
			this.add(nameLabel, BorderLayout.WEST);

			// Sets up the label showing the amount of the nutrient
			amount = new JLabel();
			updateFields();
			amount.setFont(GUI.CONTENT_FONT);
			amount.setForeground(GUI.CONTENT_COLOUR);
			amount.setOpaque(false);
			this.add(amount, BorderLayout.EAST);
		}

		/**
		 * Updates all nutrient amount fields to their correct values
		 */
		private void updateFields() {
			// Does math and does rounding to get a mostly-correct number.
			amount.setText(Double.toString(Math.round(1000 * amountPerGram
					* gramsOfFood) / 1000.0));
			// Refreshes to make sure the graphics are being displayed correctly
			amount.revalidate();
			amount.repaint();
		}
	}
}
