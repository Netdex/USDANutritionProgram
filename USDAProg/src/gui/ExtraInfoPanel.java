package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import parser.ImageExtract;
import parser.parsables.FoodItem;

public class ExtraInfoPanel extends JPanel {

	private PanelManager manager;
	private FoodItem food;

	private JPanel contentPanel;

	protected ExtraInfoPanel(PanelManager panelManager) {
		super();
		this.manager = panelManager;
		this.setLayout(new BorderLayout());
		JPanel header = new JPanel();
		header.setBackground(GUI.HEADER_COLOUR);
		header.add(new BackButton(manager.getInfoPanel(), this.manager));

		JLabel titleNameLabel = new JLabel("EXTRA INFO");
		titleNameLabel.setFont(GUI.TITLE_FONT);
		header.add(titleNameLabel);
		this.add(header, BorderLayout.NORTH);

		contentPanel = new JPanel();
		contentPanel.setBackground(GUI.BACKGROUND_COLOUR);
		BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(contentLayout);
		contentPanel.setMaximumSize(new Dimension(470, Short.MAX_VALUE));
		contentPanel.setOpaque(false);

		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.createVerticalScrollBar();
		scrollPane.getViewport().setBackground(GUI.BACKGROUND_COLOUR);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(GUI.SCROLL_SPEED);
		scrollPane.getVerticalScrollBar().setBackground(GUI.BACKGROUND_COLOUR);
		scrollPane.setHorizontalScrollBar(null);

		scrollPane.setWheelScrollingEnabled(true);
		this.add(scrollPane, BorderLayout.CENTER);
	}

	protected void setFood(FoodItem item, String titleName) {
		contentPanel.removeAll();
		this.food = item;

		// adds an image
		JLabel imageLabel = new JLabel();
		ImageExtract.injectImage(imageLabel, titleName);
		if (imageLabel.getIcon() != null)
			contentPanel.add(imageLabel);
		else {
			JTextArea imageNotFound = new JTextArea("Image not found");
			imageNotFound.setFont(GUI.CONTENT_FONT);
			imageNotFound.setAlignmentX(LEFT_ALIGNMENT);
			imageNotFound.setWrapStyleWord(true);
			imageNotFound.setLineWrap(true);
			imageNotFound.setEditable(false);
			imageNotFound.setFocusable(false);
			imageNotFound.setOpaque(false);
			contentPanel.add(imageNotFound);
		}

		// adds LanguaLs
		JTextArea languaLsList;
		if (food.getLangualGroup() != null) {
			System.out.println("adding languals for " + food.toString());
			languaLsList = new JTextArea(
					"The LanguaL descriptors for this food are: "
							+ food.getLangualGroup().getLanguaLs().toString());
		} else {
			languaLsList = new JTextArea(
					"There are no LanguaL descriptors for " + food.toString());

		}
		languaLsList.setMaximumSize(new Dimension(470, Short.MAX_VALUE));
		languaLsList.setFont(GUI.CONTENT_FONT);
		languaLsList.setAlignmentX(LEFT_ALIGNMENT);
		languaLsList.setWrapStyleWord(true);
		languaLsList.setLineWrap(true);
		languaLsList.setEditable(false);
		languaLsList.setFocusable(false);
		languaLsList.setOpaque(false);
		contentPanel.add(languaLsList);

	}
}
