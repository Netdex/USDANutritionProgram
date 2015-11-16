package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import parser.parsables.FoodItem;

/**
 * More information on a food (i.e. LanguaL descriptors and footnotes)
 * 
 * @author Vince Ou
 *
 */
public class ExtraInfoPanel extends JPanel {

	/**
	 * The card layout switcher
	 */
	private PanelManager manager;
	/**
	 * The actual food that is being described
	 */
	private FoodItem food;
	/**
	 * Panel to hold all of the interesting content that is worth showing here
	 */
	private JPanel contentPanel;

	protected ExtraInfoPanel(PanelManager panelManager) {
		// Creates a new JPanel
		super();
		this.manager = panelManager;
		this.setLayout(new BorderLayout());

		// Creates a header
		JPanel header = new JPanel();
		header.setBackground(GUI.HEADER_COLOUR);
		header.setLayout(new BorderLayout());
		// Adds two buttons (home and back) to the header
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.setBackground(GUI.HEADER_COLOUR);
		buttonPanel.add(new HomeButton(manager));
		buttonPanel.add(new BackButton(manager.getInfoPanel(), this.manager));
		header.add(buttonPanel, BorderLayout.WEST);
		// Adds the title
		JLabel titleLabel = new JLabel("Extra Info");
		titleLabel.setFont(GUI.TITLE_FONT);
		titleLabel.setForeground(GUI.TITLE_COLOUR);
		header.add(titleLabel, BorderLayout.CENTER);
		this.add(header, BorderLayout.NORTH);

		// Creates the content panel
		contentPanel = new JPanel();
		contentPanel.setBackground(GUI.BACKGROUND_COLOUR);
		BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(contentLayout);
		contentPanel.setMaximumSize(new Dimension(470, Short.MAX_VALUE));
		contentPanel.setOpaque(false);

		// Makes it scrollable
		this.add(new CustomScrollPane(contentPanel), BorderLayout.CENTER);
	}

	/**
	 * Food is only set at run time (when the icon is clicked), because the
	 * layout of the panel (header, etc.) stays constant, where the content
	 * panel is the only one that changes each time, so we aren't making a new
	 * ExtraInfoPanel every time, for better performance.
	 * 
	 * @param item
	 *            the food being displayed
	 * @param titleName
	 *            the "formatted" title name of the food (it is already
	 *            determined in the FoodInfo class, so there is no point in
	 *            doing it again, so it would be more efficient to just pass it
	 *            in as a parameter)
	 * @author Vince Ou
	 */
	protected void setFood(FoodItem item, String titleName) {
		// Clears the former contents, and sets the food
		contentPanel.removeAll();
		this.food = item;

		// adds LanguaLs
		ExtraInfoTextArea languaLsList = new ExtraInfoTextArea();
		if (food.getLangualGroup() != null) {
			languaLsList.setText(
					"The LanguaL descriptors for this food are: \n\n"
							+ food.getLangualGroup().getLanguaLs().toString());
		} else {
			// If there are no LanguaL descriptors
			languaLsList.setText(
					"There are no LanguaL descriptors for " + food.toString());
		}
		contentPanel.add(languaLsList);

		// Adds footnotes
		ExtraInfoTextArea footnotes = new ExtraInfoTextArea();
		if (food.getFootnotes() != null)
			footnotes.setText("The footnotes for this food: \n\n"
					+ food.getFootnotes().getFootnoteText());
		else
			// There are no footnotes in this case
			footnotes.setText("There are no footnotes for " + food.toString());
		contentPanel.add(footnotes);

	}

	/**
	 * Removing duplicate code, again.
	 * @author Vince Ou
	 *
	 */
	class ExtraInfoTextArea extends JTextArea {

		public ExtraInfoTextArea() {
			// Creates it
			super();
			// Formatting.
			this.setMaximumSize(new Dimension(470, Short.MAX_VALUE));
			this.setFont(GUI.CONTENT_FONT);
			this.setForeground(GUI.CONTENT_COLOUR);
			this.setBackground(GUI.BACKGROUND_COLOUR);
			this.setAlignmentX(LEFT_ALIGNMENT);
			this.setWrapStyleWord(true);
			this.setLineWrap(true);
			this.setEditable(false);
			this.setFocusable(false);
			this.setOpaque(false);
		}
	}
}
