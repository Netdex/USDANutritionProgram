package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import parser.ImageExtract;
import parser.parsables.FoodItem;
import parser.parsables.Nutrient;

public class InfoPanel extends JPanel {

	private FoodItem food;
	private double gramsOfFood;
	private double nutritionMultiplier;

	private SearchPanel searchPanel;
	private PanelManager manager;

	private JPanel header;
	private JPanel contentPanel;
	private JPanel nutritionPanel;
	private JLabel titleName;
	private JSpinner amountEntry;

	protected static final javax.swing.border.Border IMAGE_BORDER = BorderFactory
			.createLineBorder(GUI.ACCENT_COLOUR, 3);

	private NutritionInfoLabel[] nutritionLabels;

	public InfoPanel(SearchPanel searchPanel, PanelManager manager) {
		// TODO make a config to load the multiplier each time
		super();
		this.searchPanel = searchPanel;
		this.manager = manager;
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.BACKGROUND_COLOUR);

		header = new JPanel();
		header.add(new BackButton(this.searchPanel, this.manager));
		titleName = new JLabel();
		titleName.setFont(GUI.TITLE_FONT);
		header.add(titleName);
		JButton moreInfo = new JButton();
		try {
			moreInfo.setIcon(new ImageIcon(ImageIO.read(
					new File("images/moreInfoButton.png")).getScaledInstance(
					48, 48, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
		}
		moreInfo.addActionListener(new MoreInfoButtonListener());
		moreInfo.setBackground(GUI.BACKGROUND_COLOUR);
		header.add(moreInfo);
		header.setBackground(GUI.HEADER_COLOUR);

		this.add(header, BorderLayout.NORTH);

		contentPanel = new JPanel();
		BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(contentLayout);
		contentPanel.setBackground(GUI.BACKGROUND_COLOUR);
		contentPanel.setOpaque(false);

		// TODO make contentPanel scrollable

		this.add(contentPanel, BorderLayout.CENTER);
	}

	protected void setFoodItem(FoodItem item) {
		contentPanel.removeAll();
		this.food = item;

		// Changes title in header
		String longDesc = food.getLongDescription();
		int firstSeparatorIndex = longDesc.indexOf(',');

		int alternateFirstSeparator = longDesc.indexOf('(');
		if ((alternateFirstSeparator > 0 && alternateFirstSeparator < firstSeparatorIndex)
				|| firstSeparatorIndex == -1)
			firstSeparatorIndex = alternateFirstSeparator;

		// ridiculously long string before comma/bracket
		if (firstSeparatorIndex > 15) {
			int firstSpaceIndex = longDesc.indexOf(' ');
			if (firstSpaceIndex > 15)
				// if the first space is still too long, force cut
				titleName.setText(longDesc.substring(0, 15));
			else
				// use space instead of the other separators then...
				titleName.setText(longDesc.substring(0, firstSpaceIndex));
		} else if (firstSeparatorIndex > 0) {
			// normal case
			titleName.setText(longDesc.substring(0, firstSeparatorIndex));
		} else
			// no commas or brackets at all
			titleName.setText(longDesc);

		// adds image
		Image img = ImageExtract.getSearchImage(food.getLongDescription()
				.substring(0, 
						food.getLongDescription().indexOf(",") == - 1?  food.getLongDescription().length() : food.getLongDescription().indexOf(",")
						));
		if (img != null) {
			JLabel image = new JLabel();
			image.setIcon(new ImageIcon(img.getScaledInstance(256, 256,
					Image.SCALE_SMOOTH)));
			image.setAlignmentX(LEFT_ALIGNMENT);
			image.setBorder(IMAGE_BORDER);
			contentPanel.add(image);
		}

		// adds long name in actual page
		JLabel longName = new JLabel("<html>" + longDesc + "<br></html>");
		longName.setFont(GUI.SUBTITLE_FONT);
		longName.setForeground(GUI.ACCENT_COLOUR);
		longName.setAlignmentX(LEFT_ALIGNMENT);
		contentPanel.add(longName);

		// adds common name info
		if (!food.getCommonName().equals("")) {
			JLabel commonName = new JLabel("<html> Other name(s) include: "
					+ food.getCommonName().toString() + "<br></html>");
			commonName.setFont(GUI.CONTENT_FONT);
			commonName.setAlignmentX(LEFT_ALIGNMENT);
			contentPanel.add(commonName);
		}

		// adds food group info
		JLabel foodGroup = new JLabel("Food Group: "
				+ food.getFoodGroup().toString() + "\n");
		foodGroup.setFont(GUI.CONTENT_FONT);
		foodGroup.setAlignmentX(LEFT_ALIGNMENT);
		contentPanel.add(foodGroup);

		// add scientific name
		if (!food.getScientificName().equals("")) {
			JLabel scientificName = new JLabel("<html> Scientific name: "
					+ food.getScientificName().toString() + "<br></html>");
			scientificName.setFont(GUI.SCIENTIFIC_FONT);
			scientificName.setAlignmentX(LEFT_ALIGNMENT);
			contentPanel.add(scientificName);
		}

		// add manufacturer name
		if (!food.getManufacturerName().equals("")) {
			JLabel manufacName = new JLabel("<html> Manufactured by: "
					+ food.getManufacturerName().toString() + "<br></html>");
			manufacName.setFont(GUI.CONTENT_FONT);
			manufacName.setAlignmentX(LEFT_ALIGNMENT);
			contentPanel.add(manufacName);
		}

		// asks the user for how much food they are consuming
		JPanel amountEntryLine = new JPanel();
		amountEntryLine.setOpaque(false);
		FlowLayout amountEntryLayout = new FlowLayout(FlowLayout.LEFT);
		amountEntryLine.setLayout(amountEntryLayout);

		String promptText;
		if (food.getWeightInfo() != null)
			promptText = "The unit used to measure this item is:<br>\""
					+ food.getWeightInfo().getDesc().toString()
					+ "\" ("
					+ food.getWeightInfo().getGramWeight()
					+ " grams).<br>Please enter the amount (in the provided units above)<br>"
					+ "you are intending to consume";
		else
			promptText = "This item is measured in grams.<br>Please enter the number of grams you are consuming.<br>";
		JLabel amountEntryPrompt = new JLabel("<html>" + promptText + "</html>");
		amountEntryPrompt.setFont(GUI.CONTENT_FONT);
		amountEntryPrompt.setAlignmentX(LEFT_ALIGNMENT);
		amountEntryLine.add(amountEntryPrompt);

		SpinnerNumberModel amountEntryModel = new SpinnerNumberModel(0, 0, 999,
				1);
		amountEntry = new JSpinner(amountEntryModel);
		amountEntry.setBackground(GUI.BACKGROUND_COLOUR);
		amountEntry.setFont(GUI.CONTENT_FONT);
		amountEntry.setAlignmentX(LEFT_ALIGNMENT);
		amountEntry.addChangeListener(new AmountEntryListener());
		amountEntryLine.add(amountEntry);

		amountEntryLine.setAlignmentX(LEFT_ALIGNMENT);
		contentPanel.add(amountEntryLine);

		// create the base "framework" for nutrients
		nutritionPanel = new JPanel();
		// TODO add a header row
		BoxLayout nutritionLayout = new BoxLayout(nutritionPanel,
				BoxLayout.Y_AXIS);
		nutritionPanel.setLayout(nutritionLayout);
		Nutrient[] nutrients = food.getNutrientData().getNutrientArray();
		nutritionLabels = new NutritionInfoLabel[nutrients.length];

		for (int i = 0; i < nutrients.length; i++) {
			NutritionInfoLabel label = new NutritionInfoLabel(nutrients[i]);
			label.setAlignmentX(LEFT_ALIGNMENT);
			nutritionLabels[i] = label;
			nutritionPanel.add(label);
		}

		nutritionPanel.revalidate();
		nutritionPanel.repaint();
		contentPanel.add(nutritionPanel);

		contentPanel.revalidate();
		contentPanel.repaint();
	}

	private void updateFields(double newAmountInArbitraryUnits) {
		if (food.getWeightInfo() != null)
			gramsOfFood = newAmountInArbitraryUnits
					* food.getWeightInfo().getGramWeight();
		else
			// actually in grams
			gramsOfFood = newAmountInArbitraryUnits;

		// update all of the labels
		for (NutritionInfoLabel label : nutritionLabels) {
			label.updateAmounts(gramsOfFood);
			label.revalidate();
			label.repaint();
		}

		nutritionPanel.revalidate();
		nutritionPanel.repaint();
	}

	protected void setNutritionMultiplier(double personalizedMultiplier) {
		this.nutritionMultiplier = personalizedMultiplier;
		for (NutritionInfoLabel label : nutritionLabels) {
			label.updateAmounts(gramsOfFood);
		}
	}

	class AmountEntryListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			updateFields(Double.parseDouble(amountEntry.getValue().toString()));
		}

	}

	class MoreInfoButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO draw the more info dialog
		}

	}

	class NutritionInfoLabel extends JLabel {

		private Nutrient nutrient;
		private String name;
		private double amountPerGram;
		private double actualAmount;
		private double percentDV;

		private JLabel percentDVLabel;

		private NutritionInfoLabel(Nutrient nutrient) {
			super();
			this.nutrient = nutrient;
			name = this.nutrient.getNutrientDescription()
					.getNutrientDescription();
			// starts by assuming 100g sample (default)
			actualAmount = this.nutrient.getNutrVal();
			amountPerGram = actualAmount / 100;

			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			this.setAlignmentX(LEFT_ALIGNMENT);

			JLabel nameLabel = new JLabel(name);
			nameLabel.setSize(380, 50);
			nameLabel.setFont(GUI.CONTENT_FONT);
			nameLabel.setOpaque(false);
			this.add(nameLabel);

			JLabel amountInSampleLabel = new JLabel(actualAmount + "");
			amountInSampleLabel.setSize(50, 50);
			amountInSampleLabel.setFont(GUI.CONTENT_FONT);
			amountInSampleLabel.setOpaque(false);
			this.add(amountInSampleLabel);

			percentDVLabel = new JLabel();
			percentDVLabel.setSize(50, 50);
			percentDVLabel.setFont(GUI.CONTENT_FONT);
			percentDVLabel.setOpaque(false);
			this.add(percentDVLabel);

		}

		private void updateAmounts(double grams) {
			actualAmount = amountPerGram * grams;
			// percentDV = actualAmount / nutrient.

			// is there a percent recommended field?
		}
	}

}