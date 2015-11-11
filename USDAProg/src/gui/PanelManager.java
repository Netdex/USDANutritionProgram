package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import parser.ImageExtract;
import parser.parsables.FoodGroup;

public class PanelManager extends JPanel {

	private HomePanel homePanel;
	private GroupPanel groupPanel;
	private FoodListPanel foodListPanel;
	private SearchPanel searchPanel;
	private InfoPanel infoPanel;
	private ExtraInfoPanel extraInfoPanel;
	// private SettingsPanel settingsPanel;
	private AboutPanel aboutPanel;
	// private HelpPanel helpPanel;

	CardLayout cardLayoutManager;

	public int LOADING_PERCENTAGE = -1;

	// private Image img;

	public PanelManager() {
		cardLayoutManager = new CardLayout();
		this.setLayout(cardLayoutManager);
		homePanel = new HomePanel(this);
		groupPanel = new GroupPanel(this);
		foodListPanel = new FoodListPanel(this);
		searchPanel = new SearchPanel(this);
		// settingsPanel = new SettingsPanel(this);
		aboutPanel = new AboutPanel(this);
		infoPanel = new InfoPanel(searchPanel, this);
		extraInfoPanel = new ExtraInfoPanel(this);
		// helpPanel = new HelpPanel(this);

		this.add(homePanel, "home");
		this.add(groupPanel, "group");
		this.add(foodListPanel, "foodList");
		this.add(searchPanel, "search");
		// this.add(settingsPanel, "settings");
		this.add(aboutPanel, "about");
		// this.add(helpPanel, "help");
		this.add(extraInfoPanel, "extraInfo");
		this.add(infoPanel, "foodInfo");
		// img = ImageExtract.getSearchImage("apple");
	}

	protected void switchToHome() {
		cardLayoutManager.show(this, "home");
	}

	protected void switchToSearchPanel(boolean reset) {
		if (LOADING_PERCENTAGE == -1) {
			cardLayoutManager.show(this, "search");
			searchPanel.resetSearchBox();
			if (reset)
				searchPanel.resetResults();
		}
	}

	protected void switchToInfoPanel() {
		if (LOADING_PERCENTAGE == -1)
			cardLayoutManager.show(this, "foodInfo");
	}

	protected void switchToGroup() {
		if (LOADING_PERCENTAGE == -1)
			cardLayoutManager.show(this, "group");
	}

	// protected void switchToSettings() {
	// if (LOADING_PERCENTAGE == -1)
	// cardLayoutManager.show(this, "settings");
	// }

	// protected void switchToHelp() {
	// if (LOADING_PERCENTAGE == -1)
	// cardLayoutManager.show(this, "help");
	// }

	protected void switchToAbout() {
		if (LOADING_PERCENTAGE == -1)
			cardLayoutManager.show(this, "about");

	}

	protected void switchToFoodList(FoodGroup group) {
		if (LOADING_PERCENTAGE == -1)
			cardLayoutManager.show(this, "foodList");
	}

	protected JPanel getGroupPanel() {
		return groupPanel;
	}

	protected InfoPanel getInfoPanel() {
		return infoPanel;
	}

	public HomePanel getHomePanel() {
		return homePanel;
	}

	@Override
	public void paint(Graphics gr) {
		super.paint(gr);
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// For drawing progress bars and stuff
		if (LOADING_PERCENTAGE != -1) {
			final int size = 70;
			final int loadOffset = 5;
			int x = this.getWidth() / 6;
			int y = this.getHeight() / 3;
			int width = this.getWidth() * 2 / 3;
			int height = this.getHeight() / 3;
			g.setColor(new Color(0, 0, 0, 128));
			g.fillRect(x + 10, y + 10, width, height);
			g.setColor(Color.WHITE);
			g.fillRect(x, y, width, height);
			g.setColor(Color.DARK_GRAY);
			g.drawRect(x, y, width, height);

			try {
				g.setColor(new Color(
						255 - (int) (LOADING_PERCENTAGE / 100.0 * 1024) % 255,
						255 - (int) (LOADING_PERCENTAGE / 100.0 * 2000) % 255,
						(int) (LOADING_PERCENTAGE / 100.0 * 4000) % 255));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			g.fillArc(x + width / 2 - size, y + height / 2 - size + loadOffset,
					size * 2, size * 2, 0,
					(int) (LOADING_PERCENTAGE / 100.0 * 360));
			g.setColor(Color.WHITE);
			g.fillOval(x + width / 2 - size / 2, y + height / 2 - size / 2
					+ loadOffset, size, size);
			g.setColor(Color.DARK_GRAY);
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 36));
			String perc = LOADING_PERCENTAGE + "%";
			g.drawString(perc,
					x + width / 2 - g.getFontMetrics().stringWidth(perc) / 2, y
							+ height / 2 + g.getFontMetrics().getAscent() / 2);
			g.setColor(Color.BLUE);
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
			g.drawString("Loading database...", x + 10, y + 20);
			g.drawString("Loaded "
					+ GUI.dataManager.getParser().getFoodItemMap()
							.getInternalTree().size() + " food(s)", x + 10, y
					+ height - 10);
		}
	}
}
