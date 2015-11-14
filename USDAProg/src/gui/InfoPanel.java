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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import parser.FattyAcid;
import parser.ImageExtract;
import parser.parsables.FoodItem;
import parser.parsables.Nutrient;

public class InfoPanel extends JPanel {

	private FoodItem food;
	private double gramsOfFood = 1;
	// private double nutritionMultiplier;

	private SearchPanel searchPanel;
	private PanelManager manager;

	private String titleName;
	private JPanel header;
	private JPanel contentPanel;
	private JPanel nutritionPanel;
	private JLabel titleNameLabel;
	private JSpinner amountEntry;
	private JScrollPane contentScrollbar;
	private BackButton back;

	protected static final javax.swing.border.Border BLACK_BORDER = BorderFactory
			.createLineBorder(GUI.ACCENT_COLOUR, 2);

	private Nutrient[] nutrients;
	private NutrientInfoPanel[] nutritionLabels;

	public InfoPanel(SearchPanel searchPanel, PanelManager manager) {
		super();
		this.searchPanel = searchPanel;
		this.manager = manager;
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.BACKGROUND_COLOUR);

		// GUI.CONFIG.load();
		// nutritionMultiplier =
		// GUI.CONFIG.getDouble("userNutritionMultiplier");

		header = new JPanel();
		header.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.setBackground(GUI.HEADER_COLOUR);
		buttonPanel.add(new HomeButton(manager));
		back = new BackButton(this.searchPanel, this.manager);
		buttonPanel.add(back);
		header.add(buttonPanel, BorderLayout.WEST);

		titleNameLabel = new JLabel();
		titleNameLabel.setFont(GUI.TITLE_FONT);
		titleNameLabel.setAlignmentX(CENTER_ALIGNMENT);
		header.add(titleNameLabel, BorderLayout.CENTER);

		JButton moreInfo = new JButton();
		try {
			moreInfo.setIcon(new ImageIcon(ImageIO.read(
					new File("images/moreInfoButton.png")).getScaledInstance(
					48, 48, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
		}
		moreInfo.setFocusable(false);
		moreInfo.addActionListener(new MoreInfoButtonListener());
		moreInfo.setBackground(GUI.BACKGROUND_COLOUR);
		header.add(moreInfo, BorderLayout.EAST);
		header.setBackground(GUI.HEADER_COLOUR);

		this.add(header, BorderLayout.NORTH);

		contentPanel = new JPanel();
		BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(contentLayout);
		contentPanel.setBackground(GUI.BACKGROUND_COLOUR);
		contentPanel.setOpaque(false);
		contentPanel.setMaximumSize(new Dimension(465, Short.MAX_VALUE));

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
	}

	protected void setFoodItem(FoodItem item) {
		contentPanel.removeAll();
		this.food = item;
		if (food.getWeightInfo() != null)
			gramsOfFood = food.getWeightInfo().getGramWeight();
		else
			gramsOfFood = 1;

		// Changes title in header
		String longDesc = food.getLongDescription();
		int firstSeparatorIndex = longDesc.indexOf(',');
		int alternateFirstSeparator = longDesc.indexOf('(');
		if ((alternateFirstSeparator > 0 && alternateFirstSeparator < firstSeparatorIndex)
				|| firstSeparatorIndex == -1)
			firstSeparatorIndex = alternateFirstSeparator;

		// ridiculously long string before comma/bracket or none
		if (firstSeparatorIndex > 17 || firstSeparatorIndex == -1) {
			int firstSpaceIndex = longDesc.indexOf(' ');
			if (longDesc.length() <= 17)
				titleName = longDesc;
			else {
				if (firstSpaceIndex > 17)
					// if the first space is still too long, force cut
					titleName = longDesc.substring(0, 17);
				else
					// use space instead of the other separators then...
					titleName = longDesc.substring(0, firstSpaceIndex);
			}
		} else {
			// normal case
			titleName = longDesc.substring(0, firstSeparatorIndex);
		}
		titleName = toTitleCase(titleName);
		titleNameLabel.setText(titleName);

		// adds an image
		JLabel imageLabel = new JLabel();
		ImageExtract.injectImage(imageLabel, titleName);
		contentPanel.add(imageLabel);
		contentPanel.revalidate();
		contentPanel.repaint();

		// adds long name in actual page
		JTextArea longName = new JTextArea(longDesc);
		longName.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
		longName.setFont(GUI.SUBTITLE_FONT);
		longName.setWrapStyleWord(true);
		longName.setEditable(false);
		longName.setLineWrap(true);
		longName.setForeground(GUI.ACCENT_COLOUR);
		longName.setOpaque(false);
		longName.setFocusable(false);
		longName.setAlignmentX(LEFT_ALIGNMENT);
		contentPanel.add(longName);

		// adds common name info
		if (!food.getCommonName().equals("")) {
			JTextArea commonName = new JTextArea("Other name(s) include: "
					+ food.getCommonName().toString());
			commonName.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
			commonName.setFont(GUI.CONTENT_FONT);
			commonName.setWrapStyleWord(true);
			commonName.setEditable(false);
			commonName.setOpaque(false);
			commonName.setFocusable(false);
			commonName.setLineWrap(true);
			commonName.setAlignmentX(LEFT_ALIGNMENT);
			contentPanel.add(commonName);
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
		gramsOfNutrientLabel.setEditable(false);
		gramsOfNutrientLabel.setFocusable(false);
		gramsOfNutrientLabel.setOpaque(false);
		contentPanel.add(gramsOfNutrientLabel);

		// add scientific name
		if (!food.getScientificName().equals("")) {
			JTextArea scientificName = new JTextArea("Scientific name: "
					+ food.getScientificName().toString());
			scientificName.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
			scientificName.setFont(GUI.SCIENTIFIC_FONT);
			scientificName.setAlignmentX(LEFT_ALIGNMENT);
			scientificName.setWrapStyleWord(true);
			scientificName.setLineWrap(true);
			scientificName.setEditable(false);
			scientificName.setFocusable(false);
			scientificName.setOpaque(false);
			contentPanel.add(scientificName);
		}

		// add manufacturer name
		if (!food.getManufacturerName().equals("")) {
			JTextArea manufacName = new JTextArea("Manufactured by: "
					+ food.getManufacturerName().toString());
			manufacName.setMaximumSize(new Dimension(450, Short.MAX_VALUE));
			manufacName.setFont(GUI.CONTENT_FONT);
			manufacName.setAlignmentX(LEFT_ALIGNMENT);
			manufacName.setWrapStyleWord(true);
			manufacName.setLineWrap(true);
			manufacName.setEditable(false);
			manufacName.setFocusable(false);
			manufacName.setOpaque(false);
			contentPanel.add(manufacName);
		}

		// asks the user for how much food they are consuming
		JPanel amountEntryLine = new JPanel();
		amountEntryLine.setOpaque(false);
		FlowLayout amountEntryLayout = new FlowLayout(FlowLayout.LEFT);
		amountEntryLine.setLayout(amountEntryLayout);
		amountEntryLine.setMaximumSize(new Dimension(450, Short.MAX_VALUE));

		String promptText;
		if (food.getWeightInfo() != null)
			promptText = "The unit used to measure this item is: \""
					+ food.getWeightInfo().getDesc().toString() + "\" ("
					+ food.getWeightInfo().getGramWeight()
					+ " grams).\nPlease enter the amount in "
					+ food.getWeightInfo().getDesc().toString()
					+ "(s) you are intending to consume";
		else
			promptText = "This item is measured in grams.\nPlease enter the number of grams you are consuming.";

		JTextArea amountEntryPrompt = new JTextArea(promptText);
		amountEntryPrompt.setPreferredSize(new Dimension(350, 80));
		amountEntryPrompt.setFont(GUI.CONTENT_FONT);
		amountEntryPrompt.setAlignmentX(LEFT_ALIGNMENT);
		amountEntryPrompt.setWrapStyleWord(true);
		amountEntryPrompt.setLineWrap(true);
		amountEntryPrompt.setEditable(false);
		amountEntryPrompt.setFocusable(false);
		amountEntryPrompt.setOpaque(false);
		amountEntryLine.add(amountEntryPrompt);

		SpinnerNumberModel amountEntryModel = new SpinnerNumberModel(1, 1, 999,
				1);
		amountEntry = new JSpinner(amountEntryModel);
		amountEntry.setBackground(GUI.BACKGROUND_COLOUR);
		amountEntry.setFont(GUI.CONTENT_FONT);
		amountEntry.setAlignmentX(LEFT_ALIGNMENT);
		amountEntry.setFocusable(false);
		amountEntry.addChangeListener(new AmountEntryListener());
		amountEntryLine.add(amountEntry);

		amountEntryLine.setAlignmentX(LEFT_ALIGNMENT);
		contentPanel.add(amountEntryLine);

		// create the base "framework" for displaying nutrients
		nutritionPanel = new JPanel();
		nutritionPanel
				.setLayout(new BoxLayout(nutritionPanel, BoxLayout.Y_AXIS));
		// nutritionPanel.setMinimumSize(new Dimension(480, 120));
		nutritionPanel.setBorder(BLACK_BORDER);
		nutritionPanel.setAlignmentX(LEFT_ALIGNMENT);
		nutritionPanel.setBackground(GUI.BACKGROUND_COLOUR);

		nutrients = food.getNutrientData().getNutrients()
				.toArray(Nutrient.SAMPLE);
		nutritionLabels = new NutrientInfoPanel[nutrients.length];
		for (int i = 0; i < nutrients.length; i++) {
			NutrientInfoPanel nutrientPanel = new NutrientInfoPanel(
					nutrients[i]);
			nutritionLabels[i] = nutrientPanel;
			nutritionPanel.add(nutrientPanel);
		}
		nutritionPanel.setMaximumSize(new Dimension(460, Short.MAX_VALUE));
		nutritionPanel.revalidate();
		nutritionPanel.repaint();
		contentPanel.add(nutritionPanel);

		contentPanel.revalidate();
		contentPanel.repaint();

		contentScrollbar.getVerticalScrollBar().setValue(0);
	}

	private String toTitleCase(String str) {
		char[] array = str.toCharArray();
		String out = "";
		for (int i = 0; i < str.length(); i++) {
			if (i == 0 || array[i - 1] == ' ')
				out += Character.toUpperCase(array[i]);
			else
				out += Character.toLowerCase(array[i]);
		}
		return out;
	}

	protected BackButton getBackButton() {
		return back;
	}

	// protected void setNutritionMultiplier(double personalizedMultiplier)
	// {
	// this.nutritionMultiplier = personalizedMultiplier;
	// for (NutritionInfoLabel label : nutritionLabels) {
	// label.updateAmounts(gramsOfFood);
	// }
	// }

	class AmountEntryListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			double newAmountInArbitraryUnits = Double.parseDouble(amountEntry
					.getModel().getValue().toString());
			if (food.getWeightInfo() != null)
				gramsOfFood = newAmountInArbitraryUnits
						* food.getWeightInfo().getGramWeight();
			else
				// actually in grams, not arbitrary units
				gramsOfFood = newAmountInArbitraryUnits;

			// update all of the labels
			for (NutrientInfoPanel nutPanel : nutritionLabels) {
				nutPanel.updateFields();
			}

			nutritionPanel.revalidate();
			nutritionPanel.repaint();
		}

	}

	class MoreInfoButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			manager.getExtraInfoPanel().setFood(food, titleName);
			manager.switchToExtraInfo();
		}

	}

	class NutrientInfoPanel extends JPanel {

		private Nutrient nutrient;
		private double amountPerGram;

		private JLabel amount;
		private JLabel nameLabel;

		private NutrientInfoPanel(Nutrient nut) {
			super();
			this.nutrient = nut;
			this.amountPerGram = nutrient.getNutrVal() / 100;
			this.setBackground(GUI.ACCENT_COLOUR);
			this.setBorder(GUI.EMPTY_BORDER);
			this.setLayout(new BorderLayout());
			this.setOpaque(false);
			this.setMaximumSize(new Dimension(400, Short.MAX_VALUE));

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
			nameLabel = new JLabel("<html>" + nutrientName + " ("
					+ nutrient.getNutrientInfo().getUnit() + ")</html>");
			nameLabel.setFont(GUI.CONTENT_FONT);
			nameLabel.setOpaque(false);
			nameLabel.setFocusable(false);
			nameLabel.setPreferredSize(new Dimension(300, 40));
			this.add(nameLabel, BorderLayout.WEST);

			amount = new JLabel();
			updateFields();
			amount.setFont(GUI.CONTENT_FONT);
			amount.setOpaque(false);
			this.add(amount, BorderLayout.EAST);
		}

		private void updateFields() {
			amount.setText(Double.toString(Math.round(1000 * amountPerGram
					* gramsOfFood) / 1000.0));
			amount.revalidate();
			amount.repaint();
		}
	}
}
