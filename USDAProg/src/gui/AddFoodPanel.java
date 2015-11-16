package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import parser.DataManager;
import parser.FattyAcid;
import parser.InvalidParseDataException;
import parser.parsables.FoodGroup;
import parser.parsables.FoodItem;
import parser.parsables.Footnote;
import parser.parsables.Nutrient;
import parser.parsables.NutrientData;
import parser.parsables.NutrientInfo;
import parser.parsables.WeightUnit;
import parser.util.DoublyLinkedList;

/**
 * Allows the user to add custom foods to the database
 * 
 * @author Vince Ou
 *
 */
public class AddFoodPanel extends JPanel {

	/**
	 * The manager for all the cards that are switched between views
	 */
	private PanelManager manager;
	/**
	 * The panel to actually hold the content in the panel
	 */
	private JPanel contentPanel;
	/**
	 * Allows the content to be scrolled
	 */
	private CustomScrollPane contentScrollbar;

	/**
	 * For the user to provide a long-description for their custom food
	 */
	private CustomTextEntryBox longDescEntry;
	/**
	 * For the user to provide common names/ other names for their custom food
	 */
	private CustomTextEntryBox commonNameEntry;
	/**
	 * For the user to provide a manufacturer name for their custom food
	 */
	private CustomTextEntryBox manufacNameEntry;
	/**
	 * For the user to provide a unit that their custom food is measured in
	 */
	private CustomTextEntryBox weightUnitEntry;
	/**
	 * For the user to provide the number of grams per unit of weight for their
	 * custom food
	 */
	private JSpinner gramsPerEntry;
	/**
	 * For the user to specify the food group their custom food is in
	 */
	private JComboBox<FoodGroup> foodGroupEntry;
	/**
	 * A list of JPanels that hold the entry text and boxes for each nutrient
	 */
	private DoublyLinkedList<NutrientEntryLine> nutrientEntries;

	protected AddFoodPanel(PanelManager pManager) {
		// Sets up a JPanel
		super();
		this.setLayout(new BorderLayout());
		this.manager = pManager;
		this.setBackground(GUI.BACKGROUND_COLOUR);

		// Creates a header panel
		JPanel header = new JPanel();
		header.setBackground(GUI.HEADER_COLOUR);
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		// Adds a home button
		header.add(new HomeButton(manager));

		// Adds text for the title
		JLabel title = new JLabel("Add Food");
		title.setFont(GUI.TITLE_FONT);
		title.setForeground(GUI.TITLE_COLOUR);
		title.setAlignmentX(LEFT_ALIGNMENT);
		header.add(title);

		// Adds the entire header panel assembly to the main panel
		this.add(header, BorderLayout.NORTH);

		// Creates the content panel for all other items (non-header)
		contentPanel = new JPanel();
		BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(contentLayout);
		contentPanel.setBackground(GUI.BACKGROUND_COLOUR);
		contentPanel.setOpaque(false);
		contentPanel.setMaximumSize(new Dimension(400, Short.MAX_VALUE));

		// Adds padding (all other occurrences of
		// "add(Box.createRigidArea(new Dimension(...)))" are for padding and
		// formatting
		contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));

		// Area for the user to add info for the long description
		JPanel longDescLine = new JPanel(new BorderLayout());
		longDescLine.setBackground(GUI.BACKGROUND_COLOUR);
		longDescLine.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
		// prompt for the user
		longDescLine.add(new CustomizedTextArea(
				"What is the name of your new food?"), BorderLayout.CENTER);
		// JTextField for the user to enter the name
		longDescEntry = new CustomTextEntryBox("Name");
		longDescLine.add(longDescEntry, BorderLayout.EAST);
		contentPanel.add(longDescLine);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 7)));

		// Area for user to add info about the common/other names
		JPanel commonNameLine = new JPanel(new BorderLayout());
		commonNameLine.setBackground(GUI.BACKGROUND_COLOUR);
		commonNameLine.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
		// prompt for the user
		commonNameLine
				.add(new CustomizedTextArea(
						"What are some of the other names for your new food? Leave blank if none."),
						BorderLayout.CENTER);
		// Actually takes the name from the user
		commonNameEntry = new CustomTextEntryBox("Common Name");
		commonNameLine.add(commonNameEntry, BorderLayout.EAST);
		contentPanel.add(commonNameLine);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 7)));

		// Area for user to specify food group
		JPanel foodGroupLine = new JPanel(new BorderLayout());
		foodGroupLine.setBackground(GUI.BACKGROUND_COLOUR);
		foodGroupLine.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
		// Prompt
		foodGroupLine.add(new CustomizedTextArea(
				"What is the food group of your new food?"),
				BorderLayout.CENTER);
		// Gets list of food groups AFTER the food groups have been loaded from
		// the database (resolving errors)
		DataManager.getInstance().registerSyncEvent(new Runnable() {
			public void run() {
				// gets the food group choice through a list
				foodGroupEntry = new JComboBox<FoodGroup>(DataManager
						.getInstance().getFoodGroups());
				foodGroupEntry.setFont(GUI.CONTENT_FONT);
				foodGroupEntry.setPreferredSize(new Dimension(150, 30));
				foodGroupEntry.setFocusable(false);
				foodGroupEntry.setBackground(GUI.BACKGROUND_COLOUR);
				foodGroupEntry.setForeground(GUI.CONTENT_COLOUR);
				foodGroupEntry.setBackground(GUI.BACKGROUND_COLOUR);
				foodGroupLine.add(foodGroupEntry, BorderLayout.EAST);
				contentPanel.add(foodGroupLine);
				contentPanel.add(Box.createRigidArea(new Dimension(0, 7)));

				// Area for user to specify manufacturer name
				JPanel manufacNameLine = new JPanel(new BorderLayout());
				manufacNameLine.setBackground(GUI.BACKGROUND_COLOUR);
				manufacNameLine.setMaximumSize(new Dimension(450,
						Short.MAX_VALUE));
				// Prompt
				manufacNameLine.add(new CustomizedTextArea(
						"What is the manufacturer name of your new food?"),
						BorderLayout.CENTER);
				// Actual entry
				manufacNameEntry = new CustomTextEntryBox("Manufacturer Name");
				manufacNameLine.add(manufacNameEntry, BorderLayout.EAST);
				contentPanel.add(manufacNameLine);
				contentPanel.add(Box.createRigidArea(new Dimension(0, 7)));

				// Area for user to specify what unit the food is measured in
				JPanel weightUnitLine = new JPanel(new BorderLayout());
				weightUnitLine.setBackground(GUI.BACKGROUND_COLOUR);
				weightUnitLine.setMaximumSize(new Dimension(450,
						Short.MAX_VALUE));
				// Prompt
				weightUnitLine.add(new CustomizedTextArea(
						"What is the unit used to measure your new food?"),
						BorderLayout.CENTER);
				// Actually entering the unit
				weightUnitEntry = new CustomTextEntryBox("Unit");
				weightUnitLine.add(weightUnitEntry, BorderLayout.EAST);
				contentPanel.add(weightUnitLine);
				contentPanel.add(Box.createRigidArea(new Dimension(0, 7)));

				// Area for user to specify how many grams are in each unit they
				// are specifying
				JPanel gramsPerLine = new JPanel(new BorderLayout());
				gramsPerLine.setBackground(GUI.BACKGROUND_COLOUR);
				gramsPerLine
						.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
				// Prompt
				gramsPerLine
						.add(new CustomizedTextArea(
								"How many grams are in each unit (as indicated above)?"),
								BorderLayout.CENTER);
				// Actually taking the input through a Spinner (limited to only
				// numbers)
				gramsPerEntry = new JSpinner(new SpinnerNumberModel(1.000,
						0.001, 999.999, 0.025));
				gramsPerEntry.setFont(GUI.CONTENT_FONT);
				gramsPerEntry.setPreferredSize(new Dimension(150, 30));
				gramsPerEntry.setFocusable(false);
				gramsPerEntry.setBackground(GUI.BACKGROUND_COLOUR);
				gramsPerEntry.setForeground(GUI.CONTENT_COLOUR);
				gramsPerLine.add(gramsPerEntry, BorderLayout.EAST);
				contentPanel.add(gramsPerLine);

				// Prompt for user to enter all nutrients
				CustomizedTextArea nutrEntryPrompt = new CustomizedTextArea(
						"\nBelow, enter information about the nutrients in your new food.\n"
								+ "For each nutrient, enter how of it there is in 1 gram of your new food\n");
				nutrEntryPrompt.setAlignmentX(CENTER_ALIGNMENT);
				nutrEntryPrompt.setMaximumSize(new Dimension(450,
						Short.MAX_VALUE));
				contentPanel.add(nutrEntryPrompt);

				// Creates a panel that holds all of the nutrients (for
				// alignment purposes)
				JPanel nutrientAdd = new JPanel();
				nutrientAdd.setOpaque(false);
				nutrientAdd.setLayout(new BoxLayout(nutrientAdd,
						BoxLayout.Y_AXIS));
				nutrientAdd.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
				// Gets a list of possible nutrients and converts it to a doubly
				// linked list
				NutrientInfo[] listOfNutrients = DataManager.getInstance()
						.getNutrientData();
				nutrientEntries = new DoublyLinkedList<NutrientEntryLine>();
				// Adds prompts+entry boxes (lines) for each possible nutrient
				for (int i = listOfNutrients.length - 1; i >= 0; i--) {
					NutrientEntryLine line = new NutrientEntryLine(
							listOfNutrients[i]);
					nutrientEntries.add(line);
					nutrientAdd.add(line);
					nutrientAdd.add(Box.createRigidArea(new Dimension(0, 5)));
				}
				// Adds the nutrient entry pane
				nutrientAdd.setAlignmentX(CENTER_ALIGNMENT);
				contentPanel.add(nutrientAdd);
			}
		});

		// Makes the content scrollable
		contentScrollbar = new CustomScrollPane(contentPanel);
		this.add(contentScrollbar, BorderLayout.CENTER);

		// Creates a button for the user to create the food and add it to the
		// database
		JButton saveButton = new JButton("Create");
		saveButton.setBackground(GUI.ACCENT_COLOUR);
		saveButton.setFocusable(false);
		saveButton.setBorder(GUI.EMPTY_BORDER);
		saveButton.setFont(GUI.SUBTITLE_FONT);
		saveButton.addActionListener(new SaveButtonActionListener());
		saveButton.setPreferredSize(new Dimension(150, 50));
		this.add(saveButton, BorderLayout.SOUTH);
	}

	/**
	 * Resets all of the entry fields Used when loading the screen, fresh
	 * 
	 * @author Vince Ou
	 */
	protected void resetFields() {
		// self explanatory
		contentScrollbar.scrollToTop();

		// Resets text
		longDescEntry.setText("Name");
		commonNameEntry.setText("Common Name");
		manufacNameEntry.setText("Manufacturer Name");
		weightUnitEntry.setText("Unit");
		gramsPerEntry.getModel().setValue(1);

		// Resets colour to the default grey when nothing is selected
		longDescEntry.setForeground(GUI.SEARCH_BOX_GRAY);
		commonNameEntry.setForeground(GUI.SEARCH_BOX_GRAY);
		manufacNameEntry.setForeground(GUI.SEARCH_BOX_GRAY);
		weightUnitEntry.setForeground(GUI.SEARCH_BOX_GRAY);

		contentPanel.revalidate();
		contentPanel.repaint();
	}

	/**
	 * A customized JTextArea (JLabel but with text wrapping!) so the settings
	 * don't have to be set every time
	 * 
	 * @author Vince
	 */
	class CustomizedTextArea extends JTextArea {

		private CustomizedTextArea(String displayText) {
			super(displayText);
			this.setFont(GUI.CONTENT_FONT);
			this.setWrapStyleWord(true);
			this.setEditable(false);
			this.setLineWrap(true);
			this.setFocusable(false);
			this.setForeground(GUI.CONTENT_COLOUR);
			this.setOpaque(false);
			this.setAlignmentX(LEFT_ALIGNMENT);
			this.setMaximumSize(new Dimension(325, 150));
		}
	}

	/**
	 * A set of JTextArea and JSpinner for the prompt for a nutrient, and the
	 * amount of that nutrient that is in each gram of food. Makes code cleaner
	 * to read.
	 * 
	 * @author Vince Ou
	 */
	class NutrientEntryLine extends JPanel {

		private JSpinner amount;
		private NutrientInfo nutrient;

		private NutrientEntryLine(NutrientInfo nut) {
			super(new BorderLayout());
			this.setBackground(GUI.BACKGROUND_COLOUR);
			this.nutrient = nut;

			String nutrientName = nutrient.getNutrientName();
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
			this.add(
					new CustomizedTextArea(nutrientName + " ("
							+ nutrient.getUnit() + ")"), BorderLayout.CENTER);

			amount = new JSpinner(new SpinnerNumberModel(0.000, 0.000,
					9999.999, 0.500));
			amount.setFont(GUI.CONTENT_FONT);
			amount.setOpaque(false);
			amount.setFocusable(true);
			amount.setForeground(GUI.CONTENT_COLOUR);
			amount.setBackground(GUI.BACKGROUND_COLOUR);
			this.add(amount, BorderLayout.EAST);
		}

		/**
		 * Gets the nutrient
		 * 
		 * @return the nutrient
		 */
		private NutrientInfo getNutrient() {
			return nutrient;
		}

		/**
		 * Gets the amount in the JSpinner
		 * 
		 * @return the currently displayed number in the JSpinner
		 */
		private double getAmountForEntry() {
			return Double.parseDouble(amount.getModel().getValue().toString()) * 100.0;
		}

	}

	/**
	 * Action listener for the "save" button when the user has finished
	 * specifying details for their food.
	 * 
	 * @author Vince
	 *
	 */
	class SaveButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Gets the values from the objects
			int ndbNo = DataManager.getInstance().getUnusedNDBNumber();
			int groupID = ((FoodGroup) foodGroupEntry.getModel()
					.getSelectedItem()).getFoodGroupID();
			String longDesc = longDescEntry.getText();
			String commonName = commonNameEntry.getText();
			String manufacName = manufacNameEntry.getText();

			// Specifies the units the food is measured in.
			WeightUnit unit = new WeightUnit();
			try {
				unit = unit.parse(new String[] { ndbNo + "", "0",
						weightUnitEntry.getText(),
						gramsPerEntry.getModel().getValue().toString() + "",
						"", "" });
			} catch (InvalidParseDataException e1) {
				e1.printStackTrace();
			}

			// Creates the nutrient database for the new food
			NutrientData nutrients = new NutrientData();
			NutrientEntryLine[] nutEntryLineArray = nutrientEntries
					.toArray(new NutrientEntryLine(new NutrientInfo()));
			for (int i = 0; i < nutEntryLineArray.length; i++) {
				NutrientEntryLine line = nutEntryLineArray[i];
				Nutrient nut = new Nutrient();
				try {
					nut.parse(new String[] { ndbNo + "",
							line.getNutrient().getNutrientNumber() + "",
							line.getAmountForEntry() + "" });
				} catch (InvalidParseDataException e1) {
					e1.printStackTrace();
				}
				nutrients.addNutrient(nut);
			}

			// Actually creates the new food object
			FoodItem newFood = new FoodItem();
			try {
				newFood = newFood.parse(new String[] { ndbNo + "",
						groupID + "", longDesc, "", commonName, manufacName,
						"", "", "", "", "", "", "", "" });
			} catch (InvalidParseDataException e1) {
				e1.printStackTrace();
			}

			// Adds a footnote, that specifies that it was created by the user
			// and is NOT part of the USDA database.
			Footnote footnote = new Footnote();
			try {
				footnote = footnote.parse(new String[] { ndbNo + "", "", "D",
						"", "Created by user." });
			} catch (InvalidParseDataException e1) {
				e1.printStackTrace();
			}
			newFood.setFootnotes(footnote);
			newFood.setNutrientData(nutrients);

			// Finishes up.
			DataManager.getInstance().addFoodItem(newFood);
		}
	}

	/**
	 * A custom JTextField with options set as desired, so code is cleaner.
	 * 
	 * @author Vince Ou
	 */
	class CustomTextEntryBox extends JTextField {
		/**
		 * Constructor.
		 * 
		 * @param boxText
		 *            the text to be displayed in the custom text field.
		 */
		public CustomTextEntryBox(String boxText) {
			super(boxText);
			this.setFont(GUI.CONTENT_FONT);
			this.setBackground(GUI.BACKGROUND_COLOUR);
			this.setForeground(GUI.SEARCH_BOX_GRAY);
			Dimension size = new Dimension(150, 20);
			this.setPreferredSize(size);
			this.setMaximumSize(size);
			// Makes the "prompt" text disappear when clicked.
			this.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (CustomTextEntryBox.this.getText().equals(boxText)) {
						CustomTextEntryBox.this.setText("");
					}
					CustomTextEntryBox.this.setForeground(GUI.CONTENT_COLOUR);
				}
			});
		}
	}
}
