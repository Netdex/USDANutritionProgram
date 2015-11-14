package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
		header.setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.setBackground(GUI.HEADER_COLOUR);
		buttonPanel.add(new HomeButton(manager));
		buttonPanel.add(new BackButton(manager.getInfoPanel(), this.manager));
		header.add(buttonPanel, BorderLayout.WEST);

		JLabel titleNameLabel = new JLabel("Extra Info");
		titleNameLabel.setFont(GUI.TITLE_FONT);
		header.add(titleNameLabel, BorderLayout.CENTER);
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

		// adds LanguaLs
		JTextArea languaLsList;
		if (food.getLangualGroup() != null) {
			languaLsList = new JTextArea(
					"The LanguaL descriptors for this food are: \n\n"
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

		// adds footnotes
		JTextArea footnotes = new JTextArea();
		if (food.getFootnotes() != null)
			footnotes.setText("The footnotes for this food: \n\n"
					+ food.getFootnotes().getFootnoteText());
		else
			footnotes.setText("There are no footnotes for " + food.toString());
		footnotes.setMaximumSize(new Dimension(470, Short.MAX_VALUE));
		footnotes.setFont(GUI.CONTENT_FONT);
		footnotes.setAlignmentX(LEFT_ALIGNMENT);
		footnotes.setWrapStyleWord(true);
		footnotes.setLineWrap(true);
		footnotes.setEditable(false);
		footnotes.setFocusable(false);
		footnotes.setOpaque(false);
		contentPanel.add(footnotes);

	}
}
