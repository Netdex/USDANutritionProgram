package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import parser.parsables.FoodItem;
import parser.parsables.Footnote;
import parser.parsables.LanguaL;

/**
 * More information on a food (i.e. LanguaL descriptors and footnotes)
 * 
 * @author Vince Ou
 *
 */
class ExtraInfoPanel extends JPanel {

	/**
	 * The card layout switcher
	 */
	private final PanelManager manager;
	/**
	 * The actual food that is being described
	 */
	private FoodItem food;
	/**
	 * Panel to hold all of the interesting content that is worth showing here
	 */
	private final JTabbedPane contentTabs;
	private final ExtraInfoTextArea languaLsList;
	private final ExtraInfoTextArea footnotes;

	ExtraInfoPanel(PanelManager panelManager) {
		// Creates a new JPanel
		super();
		this.manager = panelManager;
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.HEADER_COLOUR);

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
		UIManager.put("TabbedPane.selected", GUI.BACKGROUND_COLOUR);
		UIManager.put("TabbedPane.shadow", GUI.BACKGROUND_COLOUR);
		UIManager.put("TabbedPane.background", GUI.BACKGROUND_COLOUR);
		UIManager.put("TabbedPane.tabAreaBackground", GUI.BACKGROUND_COLOUR);
		UIManager.put("TabbedPane.contentAreaColor", GUI.BACKGROUND_COLOUR);
		UIManager.put("TabbedPane.borderColor", GUI.BACKGROUND_COLOUR);
		UIManager
				.put("TabbedPane.borderHightlightColor", GUI.BACKGROUND_COLOUR);
		contentTabs = new JTabbedPane(JTabbedPane.TOP,
				JTabbedPane.SCROLL_TAB_LAYOUT);
		contentTabs.setBorder(GUI.EMPTY_BORDER);
		contentTabs.setBackground(GUI.BACKGROUND_COLOUR);
		contentTabs.setFocusable(false);

		languaLsList = new ExtraInfoTextArea();
		footnotes = new ExtraInfoTextArea();
		contentTabs.addTab("LanguaL Descriptors", languaLsList);
		contentTabs.addTab("Footnotes", footnotes);

		for (int i = 0; i < contentTabs.getTabCount(); i++) {
			contentTabs.setForegroundAt(i, GUI.CONTENT_COLOUR);
			contentTabs.setBackgroundAt(i, GUI.BACKGROUND_COLOUR);
		}

		// Makes it scrollable
		this.add(contentTabs, BorderLayout.CENTER);
	}

	/**
	 * Food is only set at run time (when the icon is clicked), because the
	 * layout of the panel (header, etc.) stays constant, where the content
	 * panel is the only one that changes each time, so we aren't making a new
	 * ExtraInfoPanel every time, for better performance.
	 * 
	 * @param item
	 *            the food being displayed
	 * @author Vince Ou
	 */
	void setFood(FoodItem item) {
		// Clears the former contents, and sets the food
		this.food = item;

		// adds LanguaLs
		if (food.getLangualGroup() != null) {
			LanguaL[] languals = food.getLangualGroup().getLanguaLs()
					.toArray(LanguaL.SAMPLE);
			String languaLString = "";
			for (LanguaL term : languals) {
				languaLString += "    " + term + "\n";
			}
			languaLsList
					.setText("The LanguaL descriptors for this food are: \n\n"
							+ languaLString);
		} else {
			// If there are no LanguaL descriptors
			languaLsList.setText("There are no LanguaL descriptors for "
					+ food.toString());
		}

		// Adds footnotes
		if (food.getFootnoteGroup() != null) {
			Footnote[] listOfFN = food.getFootnoteGroup().getFootnotes();
			String footNoteText = "";
			for (Footnote footnote : listOfFN) {
				footNoteText += footnote.toString() + "\n";
			}
			footnotes.setText("The footnotes for this food: \n\n"
					+ footNoteText);
		} else
			// There are no footnotes in this case
			footnotes.setText("There are no footnotes for " + food.toString());
	}

	/**
	 * Removing duplicate code, again.
	 * 
	 * @author Vince Ou
	 *
	 */
	class ExtraInfoTextArea extends JTextArea {

		public ExtraInfoTextArea() {
			// Creates it
			super();
			// Formatting.
			this.setMaximumSize(new Dimension(400, Short.MAX_VALUE));
			this.setFont(GUI.CONTENT_FONT);
			this.setForeground(GUI.CONTENT_COLOUR);
			this.setBackground(GUI.BACKGROUND_COLOUR);
			this.setAlignmentX(LEFT_ALIGNMENT);
			this.setWrapStyleWord(true);
			this.setLineWrap(true);
			this.setEditable(false);
			this.setFocusable(false);
			this.setOpaque(true);
		}
	}
}
