package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import parser.DataManager;
import parser.parsables.FoodGroup;
import parser.parsables.Nutrient;
import parser.util.DoublyLinkedList;

public class AddFoodPanel extends JPanel {

	private PanelManager manager;
	private JPanel contentPanel;
	private JScrollPane contentScrollbar;

	private JTextField nameEntry;
	private JTextField commonNameEntry;
	private JTextField manufacNameEntry;
	private JTextField weightUnitEntry;
	private JSpinner gramsPerEntry;
	private JComboBox<FoodGroup> foodGroupEntry;

	protected AddFoodPanel(PanelManager pManager) {
		super();
		this.setLayout(new BorderLayout());
		this.manager = pManager;
		this.setBackground(GUI.BACKGROUND_COLOUR);

		JPanel header = new JPanel();
		header.setBackground(GUI.HEADER_COLOUR);
		header.setLayout(new BorderLayout());
		header.add(new HomeButton(manager), BorderLayout.WEST);

		JLabel title = new JLabel("Add Food");
		title.setFont(GUI.TITLE_FONT);
		title.setForeground(GUI.TITLE_COLOUR);
		title.setAlignmentX(CENTER_ALIGNMENT);
		header.add(title, BorderLayout.CENTER);
		header.add(title);

		this.add(header, BorderLayout.NORTH);

		contentPanel = new JPanel();
		BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(contentLayout);
		contentPanel.setBackground(GUI.BACKGROUND_COLOUR);
		contentPanel.setOpaque(false);
		contentPanel.setMaximumSize(new Dimension(465, Short.MAX_VALUE));

		// longDesc
		JPanel nameLine = new JPanel(new BorderLayout());
		nameLine.setBackground(GUI.BACKGROUND_COLOUR);

		nameLine.add(new CustomizedTextArea(
				"What is the name of your new food?"), BorderLayout.CENTER);

		nameEntry = new JTextField("Name");
		// TODO set these to change colour on click (ugh!) and also make text
		// disappear on click
		nameEntry.setFont(GUI.CONTENT_FONT);
		nameEntry.setBackground(GUI.BACKGROUND_COLOUR);
		nameEntry.setForeground(GUI.CONTENT_COLOUR);
		nameEntry.setPreferredSize(new Dimension(175, 50));
		nameEntry.setFocusable(false);
		nameLine.add(nameEntry, BorderLayout.EAST);
		contentPanel.add(nameLine);

		// commonName
		JPanel commonNameLine = new JPanel(new BorderLayout());
		commonNameLine.setBackground(GUI.BACKGROUND_COLOUR);

		commonNameLine
				.add(new CustomizedTextArea(
						"What are some of the other names for your new food? Leave blank if none."),
						BorderLayout.CENTER);

		commonNameEntry = new JTextField("Common Name");
		commonNameEntry.setFont(GUI.CONTENT_FONT);
		commonNameEntry.setBackground(GUI.BACKGROUND_COLOUR);
		commonNameEntry.setForeground(GUI.CONTENT_COLOUR);
		commonNameEntry.setPreferredSize(new Dimension(175, 50));
		commonNameEntry.setFocusable(false);
		commonNameLine.add(commonNameEntry, BorderLayout.EAST);
		contentPanel.add(commonNameLine);

		// foodGrp
		JPanel foodGroupLine = new JPanel(new BorderLayout());
		foodGroupLine.setBackground(GUI.BACKGROUND_COLOUR);

		foodGroupLine.add(new CustomizedTextArea(
				"What is the food group of your new food?"),
				BorderLayout.CENTER);

		DataManager.getInstance().registerSyncEvent(new Runnable() {
			public void run() {
				foodGroupEntry = new JComboBox<FoodGroup>(DataManager
						.getInstance().getFoodGroups());
				foodGroupEntry.setFont(GUI.CONTENT_FONT);
				foodGroupEntry.setPreferredSize(new Dimension(175, 50));
				foodGroupEntry.setFocusable(false);
				foodGroupEntry.setBackground(GUI.BACKGROUND_COLOUR);
				foodGroupEntry.setForeground(GUI.CONTENT_COLOUR);
				foodGroupEntry.setBackground(GUI.BACKGROUND_COLOUR);
				foodGroupLine.add(foodGroupEntry, BorderLayout.EAST);
				contentPanel.add(foodGroupLine);

				// manufacName
				JPanel manufacNameLine = new JPanel(new BorderLayout());
				manufacNameLine.setBackground(GUI.BACKGROUND_COLOUR);

				manufacNameLine.add(new CustomizedTextArea(
						"What is the manufacturer name of your new food?"),
						BorderLayout.CENTER);

				manufacNameEntry = new JTextField("Manufacturer Name");
				manufacNameEntry.setFont(GUI.CONTENT_FONT);
				manufacNameEntry.setPreferredSize(new Dimension(175, 50));
				manufacNameEntry.setFocusable(false);
				manufacNameEntry.setBackground(GUI.BACKGROUND_COLOUR);
				manufacNameEntry.setForeground(GUI.CONTENT_COLOUR);
				manufacNameLine.add(manufacNameEntry, BorderLayout.EAST);
				contentPanel.add(manufacNameLine);

				// weightUnit
				JPanel weightUnitLine = new JPanel(new BorderLayout());
				weightUnitLine.setBackground(GUI.BACKGROUND_COLOUR);

				weightUnitLine.add(new CustomizedTextArea(
						"What is the unit used to measure your new food?"),
						BorderLayout.CENTER);

				weightUnitEntry = new JTextField("Unit");
				weightUnitEntry.setFont(GUI.CONTENT_FONT);
				weightUnitEntry.setPreferredSize(new Dimension(175, 50));
				weightUnitEntry.setFocusable(false);
				weightUnitEntry.setBackground(GUI.BACKGROUND_COLOUR);
				weightUnitEntry.setForeground(GUI.CONTENT_COLOUR);
				weightUnitLine.add(weightUnitEntry, BorderLayout.EAST);
				contentPanel.add(weightUnitLine);

				// grams per weightUnit
				JPanel gramsPerLine = new JPanel(new BorderLayout());
				gramsPerLine.setBackground(GUI.BACKGROUND_COLOUR);

				gramsPerLine
						.add(new CustomizedTextArea(
								"How many grams are in each unit (as indicated above)?"),
								BorderLayout.CENTER);

				gramsPerEntry = new JSpinner(new SpinnerNumberModel(1, 0, 999,
						1));
				gramsPerEntry.setFont(GUI.CONTENT_FONT);
				gramsPerEntry.setPreferredSize(new Dimension(175, 50));
				gramsPerEntry.setFocusable(false);
				gramsPerEntry.setBackground(GUI.BACKGROUND_COLOUR);
				gramsPerEntry.setForeground(GUI.CONTENT_COLOUR);
				gramsPerLine.add(gramsPerEntry, BorderLayout.EAST);
				contentPanel.add(gramsPerLine);

				// prompts for all nutrients
				CustomizedTextArea nutrEntryPrompt = new CustomizedTextArea(
						"\nBelow, enter information about the nutrients in your new food.\n"
								+ "For each nutrient, enter the units it's measuered in,\n"
								+ "and the amount of each unit is in one gram of food.");
				nutrEntryPrompt.setAlignmentX(CENTER_ALIGNMENT);
				Dimension nutrSize = new Dimension(400, 150);
				nutrEntryPrompt.setPreferredSize(nutrSize);
				nutrEntryPrompt.setMaximumSize(nutrSize);
				contentPanel.add(nutrEntryPrompt);

				JPanel nutrientAdd = new JPanel();
				nutrientAdd.setOpaque(false);
				nutrientAdd.setLayout(new BoxLayout(nutrientAdd,
						BoxLayout.Y_AXIS));

				Nutrient[] listOfNutrients = { new Nutrient() };
				DoublyLinkedList<NutrientEntryLine> nutrientEntries = new DoublyLinkedList<NutrientEntryLine>();
				// TODO get the list of all nutrients here
				for (Nutrient nutrient : listOfNutrients) {
					NutrientEntryLine line = new NutrientEntryLine(nutrient);
					nutrientEntries.add(line);
					nutrientAdd.add(line);
				}
				nutrientAdd.setAlignmentX(LEFT_ALIGNMENT);
				contentPanel.add(nutrientAdd);
			}
		});

		contentScrollbar = new JScrollPane(contentPanel);
		contentScrollbar.createVerticalScrollBar();
		contentScrollbar.getViewport().setBackground(GUI.BACKGROUND_COLOUR);
		contentScrollbar
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentScrollbar.getVerticalScrollBar().setUnitIncrement(
				GUI.SCROLL_SPEED);
		contentScrollbar.getVerticalScrollBar().setBackground(
				GUI.BACKGROUND_COLOUR);
		contentScrollbar.setWheelScrollingEnabled(true);
		contentScrollbar.setHorizontalScrollBar(null);

		this.add(contentScrollbar, BorderLayout.CENTER);

		JButton saveButton = new JButton("Create");
		saveButton.setBackground(GUI.ACCENT_COLOUR);
		saveButton.setFocusable(false);
		saveButton.setFont(GUI.SUBTITLE_FONT);
		saveButton.addActionListener(new SaveButtonActionListener());
		saveButton.setPreferredSize(new Dimension(150, 50));
		this.add(saveButton, BorderLayout.SOUTH);
	}

	class CustomizedTextArea extends JTextArea {

		private CustomizedTextArea(String displayText) {
			super(displayText);
			this.setFont(GUI.CONTENT_FONT);
			this.setWrapStyleWord(true);
			this.setEditable(false);
			this.setLineWrap(true);
			this.setForeground(GUI.CONTENT_COLOUR);
			this.setOpaque(false);
			this.setFocusable(false);
			this.setAlignmentX(LEFT_ALIGNMENT);
			this.setMaximumSize(new Dimension(325, 150));
		}
	}

	class NutrientEntryLine extends JPanel {

		private JTextField nutrientUnit;
		private JSpinner amount; // TODO remember to multiply amount by 100
									// before giving it to Gordon

		private NutrientEntryLine(Nutrient nutrient) {
			super(new BorderLayout());
			this.setBackground(GUI.BACKGROUND_COLOUR);

			add(new CustomizedTextArea(nutrient.getNutrientInfo()
					.getNutrientName()), BorderLayout.WEST);

			nutrientUnit = new JTextField("Unit");
			nutrientUnit.setFont(GUI.CONTENT_FONT);
			nutrientUnit.setOpaque(false);
			nutrientUnit.setForeground(GUI.CONTENT_COLOUR);
			nutrientUnit.setBackground(GUI.BACKGROUND_COLOUR);
			nutrientUnit.setPreferredSize(new Dimension(75, 50));
			add(nutrientUnit, BorderLayout.CENTER);

			amount = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
			amount.setFont(GUI.CONTENT_FONT);
			amount.setOpaque(false);
			amount.setFocusable(false);
			amount.setForeground(GUI.CONTENT_COLOUR);
			amount.setBackground(GUI.BACKGROUND_COLOUR);
			add(amount, BorderLayout.WEST);

		}

		private JTextField getNutrientUnit() {
			return nutrientUnit;
		}

		private JSpinner getAmount() {
			return amount;
		}

	}

	class SaveButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("method not implemented. trying to add food.");

			// DataManager.getInstance().addFoodItem(new FoodItem()); // TODO
			// this
			// "datamanager getunusedndbnumber"
			// TODO add something to end of footnotes indicating it was added by
			// user

		}
	}
}
