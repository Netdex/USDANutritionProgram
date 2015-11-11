package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;
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
	// private double nutritionMultiplier;

	private SearchPanel searchPanel;
	private PanelManager manager;

	private JPanel header;
	private JPanel contentPanel;
	private JPanel nutritionPanel;
	private JLabel titleNameLabel;
	private JSpinner amountEntry;

	protected static final javax.swing.border.Border BLACK_BORDER = BorderFactory
			.createLineBorder(Color.DARK_GRAY, 2);

	private NutritionInfoLabel[] nutritionLabels;

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
		header.add(new BackButton(this.searchPanel, this.manager));
		titleNameLabel = new JLabel();
		titleNameLabel.setFont(GUI.TITLE_FONT);
		header.add(titleNameLabel);
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

		JScrollPane contentScrollable = new JScrollPane(contentPanel);
		contentScrollable.createVerticalScrollBar();
		contentScrollable.getViewport().setBackground(GUI.BACKGROUND_COLOUR);
		contentScrollable
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentScrollable.getVerticalScrollBar().setUnitIncrement(
				GUI.SCROLL_SPEED);
		contentScrollable.getVerticalScrollBar().setBackground(
				GUI.BACKGROUND_COLOUR);
		contentScrollable.setWheelScrollingEnabled(true);

		this.add(contentScrollable, BorderLayout.CENTER);
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
		String titleName;
		// ridiculously long string before comma/bracket
		if (firstSeparatorIndex > 17) {
			int firstSpaceIndex = longDesc.indexOf(' ');
			if (firstSpaceIndex > 17)
				// if the first space is still too long, force cut
				titleName = longDesc.substring(0, 17);
			else
				// use space instead of the other separators then...
				titleName = longDesc.substring(0, firstSpaceIndex);
		} else if (firstSeparatorIndex > 0) {
			// normal case
			titleName = longDesc.substring(0, firstSeparatorIndex);
		} else
			// no commas or brackets at all
			titleName = longDesc;
		titleNameLabel.setText(titleName);

		// adds an image
		JLabel image = new JLabel();
		ImageExtract.injectImage(image, titleName);
		contentPanel.add(image);

		// adds long name in actual page
		JLabel longName = new JLabel("<html>" + longDesc + "<br></html>");
		longName.setFont(GUI.SUBTITLE_FONT);
		longName.setForeground(GUI.ACCENT_COLOUR);
		longName.setAlignmentX(LEFT_ALIGNMENT);
		longName.setPreferredSize(new Dimension(470, 50));
		contentPanel.add(longName);

		// adds common name info
		if (!food.getCommonName().equals("")) {
			JLabel commonName = new JLabel("<html> Other name(s) include: "
					+ food.getCommonName().toString() + "<br></html>");
			commonName.setFont(GUI.CONTENT_FONT);
			commonName.setAlignmentX(LEFT_ALIGNMENT);
			commonName.setPreferredSize(new Dimension(470, 50));
			contentPanel.add(commonName);
		}

		// adds food group info
		JLabel foodGroup = new JLabel("Food Group: "
				+ food.getFoodGroup().toString() + "\n");
		foodGroup.setFont(GUI.CONTENT_FONT);
		foodGroup.setAlignmentX(LEFT_ALIGNMENT);
		foodGroup.setPreferredSize(new Dimension(470, 50));
		contentPanel.add(foodGroup);

		// add scientific name
		if (!food.getScientificName().equals("")) {
			JLabel scientificName = new JLabel("<html> Scientific name: "
					+ food.getScientificName().toString() + "<br></html>");
			scientificName.setFont(GUI.SCIENTIFIC_FONT);
			scientificName.setAlignmentX(LEFT_ALIGNMENT);
			scientificName.setPreferredSize(new Dimension(470, 50));
			contentPanel.add(scientificName);
		}

		// add manufacturer name
		if (!food.getManufacturerName().equals("")) {
			JLabel manufacName = new JLabel("<html> Manufactured by: "
					+ food.getManufacturerName().toString() + "<br></html>");
			manufacName.setFont(GUI.CONTENT_FONT);
			manufacName.setAlignmentX(LEFT_ALIGNMENT);
			manufacName.setPreferredSize(new Dimension(470, 50));
			contentPanel.add(manufacName);
		}

		// asks the user for how much food they are consuming
		JPanel amountEntryLine = new JPanel();
		amountEntryLine.setPreferredSize(new Dimension(470, 150));
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

		SpinnerNumberModel amountEntryModel = new SpinnerNumberModel(1, 0, 999,
				1);
		amountEntry = new JSpinner(amountEntryModel);
		amountEntry.setBackground(GUI.BACKGROUND_COLOUR);
		amountEntry.setFont(GUI.CONTENT_FONT);
		amountEntry.setAlignmentX(LEFT_ALIGNMENT);
		amountEntry.addChangeListener(new AmountEntryListener());
		amountEntryLine.add(amountEntry);

		amountEntryLine.setAlignmentX(LEFT_ALIGNMENT);
		contentPanel.add(amountEntryLine);

		// create the base "framework" for displaying nutrients
		nutritionPanel = new JPanel();
		// TODO add a header row
		BoxLayout nutritionLayout = new BoxLayout(nutritionPanel,
				BoxLayout.Y_AXIS);
		nutritionPanel.setLayout(nutritionLayout);
		nutritionPanel.setBorder(BLACK_BORDER);
		Nutrient[] nutrients = food.getNutrientData().getNutrientArray();
		nutritionLabels = new NutritionInfoLabel[nutrients.length];

		for (int i = 0; i < nutrients.length; i++) {
			NutritionInfoLabel label = new NutritionInfoLabel(nutrients[i]);
			label.setAlignmentX(LEFT_ALIGNMENT);
			nutritionLabels[i] = label;
			nutritionPanel.add(label);
		}

		// nutritionPanel.revalidate();
		// nutritionPanel.repaint();
		contentPanel.add(nutritionPanel);

		contentPanel.revalidate();
		contentPanel.repaint();
	}

	// protected void setNutritionMultiplier(double personalizedMultiplier) {
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
			for (NutritionInfoLabel label : nutritionLabels) {
				label.updateAmounts();
				label.revalidate();
				label.repaint();
			}

			nutritionPanel.revalidate();
			nutritionPanel.repaint();
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
		private double gramsOfNutrientPerGramOfFood;
		private double gramsOfNutrientInSample;

		private JLabel gramsOfNutrientLabel;

		// private double percentDV;

		// private JLabel percentDVLabel;

		private NutritionInfoLabel(Nutrient nutrient) {
			super();
			this.nutrient = nutrient;
			name = this.nutrient.getNutrientDescription()
					.getNutrientDescription();
			gramsOfNutrientPerGramOfFood = this.nutrient.getNutrVal() / 100.0
					* gramsOfFood;

			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			this.setAlignmentX(LEFT_ALIGNMENT);
			this.setBackground(GUI.ACCENT_COLOUR);

			JLabel nameLabel = new JLabel("<html>" + name + "</html>");
			nameLabel.setPreferredSize(new Dimension(380, 50));
			nameLabel.setFont(GUI.CONTENT_FONT);
			nameLabel.setOpaque(false);
			this.add(nameLabel);

			gramsOfNutrientLabel = new JLabel();
			gramsOfNutrientLabel.setText(gramsOfNutrientInSample + "");
			gramsOfNutrientLabel.setPreferredSize(new Dimension(50, 50));
			gramsOfNutrientLabel.setFont(GUI.CONTENT_FONT);
			gramsOfNutrientLabel.setOpaque(false);
			this.add(gramsOfNutrientLabel);

		}

		private void updateAmounts() {
			gramsOfNutrientLabel.setText(gramsOfNutrientPerGramOfFood
					* gramsOfFood + "");
		}
	}

}