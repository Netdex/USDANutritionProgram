package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import parser.DataManager;
import parser.parsables.FoodGroup;
import parser.util.DoublyLinkedList;

public class AddFoodPanel extends JPanel {

	private PanelManager manager;
	private JPanel contentPanel;
	private JScrollPane contentScrollbar;

	private JTextField nameEntry;
	private JTextField commonNameEntry;
	private JTextField manufacNameEntry;
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
		JPanel nameLine = new JPanel();
		nameLine.setLayout(new BorderLayout());
		nameLine.setBackground(GUI.BACKGROUND_COLOUR);

		JLabel namePrompt = new JLabel("What is the name of your new food?");
		namePrompt.setFont(GUI.CONTENT_FONT);
		namePrompt.setOpaque(false);
		namePrompt.setAlignmentX(LEFT_ALIGNMENT);
		namePrompt.setMaximumSize(new Dimension(325, 200));
		nameLine.add(namePrompt, BorderLayout.CENTER);

		nameEntry = new JTextField("Name");
		// TODO set these to change colour on click (ugh!) and also make text
		// disappear on click
		nameEntry.setFont(GUI.CONTENT_FONT);
		nameEntry.setPreferredSize(new Dimension(175, 50));
		nameLine.add(nameEntry, BorderLayout.EAST);
		contentPanel.add(nameLine);

		// commonName
		JPanel commonNameLine = new JPanel();
		commonNameLine.setLayout(new BorderLayout());
		commonNameLine.setBackground(GUI.BACKGROUND_COLOUR);

		JLabel commonNamePrompt = new JLabel(
				"What are some of the other names for your new food?");
		commonNamePrompt.setFont(GUI.CONTENT_FONT);
		commonNamePrompt.setOpaque(false);
		commonNamePrompt.setAlignmentX(LEFT_ALIGNMENT);
		commonNamePrompt.setMaximumSize(new Dimension(325, 200));
		commonNameLine.add(commonNamePrompt, BorderLayout.CENTER);

		commonNameEntry = new JTextField("Common Name");
		commonNameEntry.setFont(GUI.CONTENT_FONT);
		commonNameEntry.setPreferredSize(new Dimension(175, 50));
		commonNameLine.add(commonNameEntry, BorderLayout.EAST);
		contentPanel.add(commonNameLine);

		// foodGrp
		JPanel foodGroupLine = new JPanel();
		foodGroupLine.setLayout(new BorderLayout());
		foodGroupLine.setBackground(GUI.BACKGROUND_COLOUR);

		JLabel foodGroupPrompt = new JLabel(
				"What is the food group of your new food?");
		foodGroupPrompt.setFont(GUI.CONTENT_FONT);
		foodGroupPrompt.setOpaque(false);
		foodGroupPrompt.setAlignmentX(LEFT_ALIGNMENT);
		foodGroupPrompt.setMaximumSize(new Dimension(325, 200));
		foodGroupLine.add(foodGroupPrompt, BorderLayout.CENTER);

		DataManager.getInstance().registerSyncEvent(new Runnable() {
			public void run() {
				foodGroupEntry = new JComboBox<FoodGroup>(DataManager
						.getInstance().getFoodGroups());
				foodGroupEntry.setFont(GUI.CONTENT_FONT);
				foodGroupEntry.setPreferredSize(new Dimension(175, 50));
				foodGroupLine.add(foodGroupEntry, BorderLayout.EAST);
				contentPanel.add(foodGroupLine);

				// manufacName
				JPanel manufacNameLine = new JPanel();
				manufacNameLine.setLayout(new BorderLayout());
				manufacNameLine.setBackground(GUI.BACKGROUND_COLOUR);

				JLabel manufacNamePrompt = new JLabel(
						"What is the manufacturer name of your new food?");
				manufacNamePrompt.setFont(GUI.CONTENT_FONT);
				manufacNamePrompt.setOpaque(false);
				manufacNamePrompt.setAlignmentX(LEFT_ALIGNMENT);
				manufacNamePrompt.setMaximumSize(new Dimension(325, 200));
				manufacNameLine.add(manufacNamePrompt, BorderLayout.CENTER);

				manufacNameEntry = new JTextField("Manufacturer Name");
				manufacNameEntry.setFont(GUI.CONTENT_FONT);
				manufacNameEntry.setPreferredSize(new Dimension(175, 50));
				manufacNameLine.add(manufacNameEntry, BorderLayout.EAST);
				contentPanel.add(manufacNameLine);
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
		saveButton.setFont(GUI.SUBTITLE_FONT);
		saveButton.addActionListener(new SaveButtonActionListener());
		saveButton.setPreferredSize(new Dimension(150, 50));
		this.add(saveButton, BorderLayout.SOUTH);
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
