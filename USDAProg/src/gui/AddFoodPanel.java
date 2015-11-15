package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
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
import parser.InvalidParseDataException;
import parser.parsables.FoodGroup;
import parser.parsables.FoodItem;
import parser.parsables.Footnote;
import parser.parsables.Nutrient;
import parser.parsables.NutrientData;
import parser.parsables.NutrientInfo;
import parser.parsables.WeightUnit;
import parser.util.DoublyLinkedList;

public class AddFoodPanel extends JPanel {

	private PanelManager manager;
	private JPanel contentPanel;
	private JScrollPane contentScrollbar;

	private CustomTextEntryBox longDescEntry;
	private CustomTextEntryBox commonNameEntry;
	private CustomTextEntryBox manufacNameEntry;
	private CustomTextEntryBox weightUnitEntry;
	private JSpinner gramsPerEntry;
	private JComboBox<FoodGroup> foodGroupEntry;
	private DoublyLinkedList<NutrientEntryLine> nutrientEntries;

	protected AddFoodPanel(PanelManager pManager) {
		super();
		this.setLayout(new BorderLayout());
		this.manager = pManager;
		this.setBackground(GUI.BACKGROUND_COLOUR);

		JPanel header = new JPanel();
		header.setBackground(GUI.HEADER_COLOUR);
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		header.add(new HomeButton(manager));

		JLabel title = new JLabel("Add Food");
		title.setFont(GUI.TITLE_FONT);
		title.setForeground(GUI.TITLE_COLOUR);
		title.setAlignmentX(LEFT_ALIGNMENT);
		header.add(title);

		this.add(header, BorderLayout.NORTH);

		contentPanel = new JPanel();
		BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(contentLayout);
		contentPanel.setBackground(GUI.BACKGROUND_COLOUR);
		contentPanel.setOpaque(false);
		contentPanel.setMaximumSize(new Dimension(400, Short.MAX_VALUE));

		contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));

		// longDesc
		JPanel longDescLine = new JPanel(new BorderLayout());
		longDescLine.setBackground(GUI.BACKGROUND_COLOUR);
		longDescLine.setMaximumSize(new Dimension(450, Short.MAX_VALUE));

		longDescLine.add(new CustomizedTextArea(
				"What is the name of your new food?"), BorderLayout.CENTER);

		longDescEntry = new CustomTextEntryBox("Name");
		longDescLine.add(longDescEntry, BorderLayout.EAST);
		contentPanel.add(longDescLine);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 7)));

		// commonName
		JPanel commonNameLine = new JPanel(new BorderLayout());
		commonNameLine.setBackground(GUI.BACKGROUND_COLOUR);
		commonNameLine.setMaximumSize(new Dimension(450, Short.MAX_VALUE));

		commonNameLine
				.add(new CustomizedTextArea(
						"What are some of the other names for your new food? Leave blank if none."),
						BorderLayout.CENTER);

		commonNameEntry = new CustomTextEntryBox("Common Name");
		commonNameLine.add(commonNameEntry, BorderLayout.EAST);
		contentPanel.add(commonNameLine);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 7)));

		// foodGrp
		JPanel foodGroupLine = new JPanel(new BorderLayout());
		foodGroupLine.setBackground(GUI.BACKGROUND_COLOUR);
		foodGroupLine.setMaximumSize(new Dimension(450, Short.MAX_VALUE));

		foodGroupLine.add(new CustomizedTextArea(
				"What is the food group of your new food?"),
				BorderLayout.CENTER);

		DataManager.getInstance().registerSyncEvent(new Runnable() {
			public void run() {
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

				// manufacName
				JPanel manufacNameLine = new JPanel(new BorderLayout());
				manufacNameLine.setBackground(GUI.BACKGROUND_COLOUR);
				manufacNameLine.setMaximumSize(new Dimension(450,
						Short.MAX_VALUE));

				manufacNameLine.add(new CustomizedTextArea(
						"What is the manufacturer name of your new food?"),
						BorderLayout.CENTER);

				manufacNameEntry = new CustomTextEntryBox("Manufacturer Name");
				manufacNameLine.add(manufacNameEntry, BorderLayout.EAST);
				contentPanel.add(manufacNameLine);
				contentPanel.add(Box.createRigidArea(new Dimension(0, 7)));

				// weightUnit
				JPanel weightUnitLine = new JPanel(new BorderLayout());
				weightUnitLine.setBackground(GUI.BACKGROUND_COLOUR);
				weightUnitLine.setMaximumSize(new Dimension(450,
						Short.MAX_VALUE));

				weightUnitLine.add(new CustomizedTextArea(
						"What is the unit used to measure your new food?"),
						BorderLayout.CENTER);

				weightUnitEntry = new CustomTextEntryBox("Unit");
				weightUnitLine.add(weightUnitEntry, BorderLayout.EAST);
				contentPanel.add(weightUnitLine);
				contentPanel.add(Box.createRigidArea(new Dimension(0, 7)));

				// grams per weightUnit
				JPanel gramsPerLine = new JPanel(new BorderLayout());
				gramsPerLine.setBackground(GUI.BACKGROUND_COLOUR);
				gramsPerLine
						.setMaximumSize(new Dimension(450, Short.MAX_VALUE));

				gramsPerLine
						.add(new CustomizedTextArea(
								"How many grams are in each unit (as indicated above)?"),
								BorderLayout.CENTER);

				gramsPerEntry = new JSpinner(new SpinnerNumberModel(1, 0, 999,
						1));
				gramsPerEntry.setFont(GUI.CONTENT_FONT);
				gramsPerEntry.setPreferredSize(new Dimension(150, 30));
				gramsPerEntry.setFocusable(false);
				gramsPerEntry.setBackground(GUI.BACKGROUND_COLOUR);
				gramsPerEntry.setForeground(GUI.CONTENT_COLOUR);
				gramsPerLine.add(gramsPerEntry, BorderLayout.EAST);
				contentPanel.add(gramsPerLine);

				// prompts for all nutrients
				CustomizedTextArea nutrEntryPrompt = new CustomizedTextArea(
						"\nBelow, enter information about the nutrients in your new food.\n"
								+ "For each nutrient, enter how of it there is in 1 gram of your new food\n");
				nutrEntryPrompt.setAlignmentX(CENTER_ALIGNMENT);
				nutrEntryPrompt.setMaximumSize(new Dimension(450,
						Short.MAX_VALUE));
				contentPanel.add(nutrEntryPrompt);

				JPanel nutrientAdd = new JPanel();
				nutrientAdd.setOpaque(false);
				nutrientAdd.setLayout(new BoxLayout(nutrientAdd,
						BoxLayout.Y_AXIS));
				nutrientAdd.setMaximumSize(new Dimension(450, Short.MAX_VALUE));

				NutrientInfo[] listOfNutrients = DataManager.getInstance()
						.getNutrientData();
				nutrientEntries = new DoublyLinkedList<NutrientEntryLine>();

				for (int i = listOfNutrients.length - 1; i >= 0; i--) {
					NutrientEntryLine line = new NutrientEntryLine(
							listOfNutrients[i]);
					nutrientEntries.add(line);
					nutrientAdd.add(line);
					nutrientAdd.add(Box.createRigidArea(new Dimension(0, 5)));
				}
				nutrientAdd.setAlignmentX(CENTER_ALIGNMENT);
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
		saveButton.setBorder(GUI.EMPTY_BORDER);
		saveButton.setFont(GUI.SUBTITLE_FONT);
		saveButton.addActionListener(new SaveButtonActionListener());
		saveButton.setPreferredSize(new Dimension(150, 50));
		this.add(saveButton, BorderLayout.SOUTH);
	}

	protected void resetFields() {
		longDescEntry.setText("Name");
		commonNameEntry.setText("Common Name");
		manufacNameEntry.setText("Manufacturer Name");
		weightUnitEntry.setText("Unit");
		gramsPerEntry.getModel().setValue(1);
	}

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

	class NutrientEntryLine extends JPanel {

		private JSpinner amount;
		private NutrientInfo nutrient;

		private NutrientEntryLine(NutrientInfo nut) {
			super(new BorderLayout());
			this.setBackground(GUI.BACKGROUND_COLOUR);
			this.nutrient = nut;

			this.add(new CustomizedTextArea(nutrient.getNutrientName() + " ("
					+ nutrient.getUnit() + ")"), BorderLayout.CENTER);

			amount = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
			amount.setFont(GUI.CONTENT_FONT);
			amount.setOpaque(false);
			amount.setFocusable(true);
			amount.setForeground(GUI.CONTENT_COLOUR);
			amount.setBackground(GUI.BACKGROUND_COLOUR);
			this.add(amount, BorderLayout.EAST);
		}

		private NutrientInfo getNutrient() {
			return nutrient;
		}

		private double getAmountForEntry() {
			return Double.parseDouble(amount.getModel().getValue().toString()) * 100.0;
		}

	}

	class SaveButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int ndbNo = DataManager.getInstance().getUnusedNDBNumber();
			int groupID = ((FoodGroup) foodGroupEntry.getModel()
					.getSelectedItem()).getFoodGroupID();
			String longDesc = longDescEntry.getText();
			String commonName = commonNameEntry.getText();
			String manufacName = manufacNameEntry.getText();

			WeightUnit unit = new WeightUnit();
			try {
				unit = unit.parse(new String[] { ndbNo + "", "0",
						weightUnitEntry.getText(),
						gramsPerEntry.getModel().getValue().toString() + "",
						"", "" });
			} catch (InvalidParseDataException e1) {
				e1.printStackTrace();
			}

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

			FoodItem newFood = new FoodItem();
			try {
				newFood = newFood.parse(new String[] { ndbNo + "",
						groupID + "", longDesc, "", commonName, manufacName,
						"", "", "", "", "", "", "", "" });
			} catch (InvalidParseDataException e1) {
				e1.printStackTrace();
			}

			Footnote footnote = new Footnote();
			try {
				footnote = footnote.parse(new String[] { ndbNo + "", "", "D",
						"", "Created by user." });
			} catch (InvalidParseDataException e1) {
				e1.printStackTrace();
			}

			newFood.setFootnotes(footnote);
			newFood.setNutrientData(nutrients);
			DataManager.getInstance().addFoodItem(newFood);

			// TODO add something to end of footnotes indicating it was added by
			// user

		}
	}

	class CustomTextEntryBox extends JTextField {
		public CustomTextEntryBox(String boxText) {
			super(boxText);
			this.setFont(GUI.CONTENT_FONT);
			this.setBackground(GUI.BACKGROUND_COLOUR);
			this.setForeground(GUI.CONTENT_COLOUR);
			Dimension size = new Dimension(150, 20);
			this.setPreferredSize(size);
			this.setMaximumSize(size);
			this.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (CustomTextEntryBox.this.getText().equals(boxText)) {
						CustomTextEntryBox.this.setText("");
					}
				}
			});
		}
	}
}
