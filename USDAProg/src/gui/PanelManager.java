package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * The card-switching, panel manager
 *
 * @author Vince Ou
 */
public class PanelManager extends JPanel {

    /**
     * Home screen
     */
    private final HomePanel homePanel;
    /**
     * The list of food groups
     */
    private final GroupPanel groupPanel;
    /**
     * List of foods under each food group
     */
    private final FoodListPanel foodListPanel;
    /**
     * Search function view
     */
    private final SearchPanel searchPanel;
    /**
     * The information about a certain food
     */
    private final InfoPanel infoPanel;
    /**
     * Any other pertinent information about a food
     */
    private final ExtraInfoPanel extraInfoPanel;
    /**
     * Allows user to create their own foods and add them to the database
     */
    private final AddFoodPanel addFoodPanel;
    /**
     * The manager being used
     */
    private final CardLayout cardLayoutManager;
    /**
     * Used to animate the loading wheel
     */
    public int LOADING_PERCENTAGE = -1;

    public PanelManager() {
        // Set up
        cardLayoutManager = new CardLayout();
        this.setLayout(cardLayoutManager);

        // Creates all of the panels
        homePanel = new HomePanel(this);
        groupPanel = new GroupPanel(this);
        foodListPanel = new FoodListPanel(this);
        searchPanel = new SearchPanel(this);
        /*
      Information about developers
     */
        AboutPanel aboutPanel = new AboutPanel(this);
        infoPanel = new InfoPanel(searchPanel, this);
        extraInfoPanel = new ExtraInfoPanel(this);
        addFoodPanel = new AddFoodPanel(this);

        // Adds the panels into the stock "list" of panels
        this.add(homePanel, "home");
        this.add(groupPanel, "group");
        this.add(foodListPanel, "foodList");
        this.add(searchPanel, "search");
        this.add(aboutPanel, "about");
        this.add(extraInfoPanel, "extraInfo");
        this.add(infoPanel, "foodInfo");
        this.add(addFoodPanel, "addFood");
    }

    /**
     * Switches to the ExtraInfo panel
     */
    void switchToExtraInfo() {
        cardLayoutManager.show(this, "extraInfo");
    }

    /**
     * Switches to the home panel
     */
    void switchToHome() {
        cardLayoutManager.show(this, "home");
    }

    /**
     * Switches to the search panel
     *
     * @param reset reset the search results?
     */
    void switchToSearchPanel(boolean reset) {
        // Disables buttons during loading wheel sequence
        if (LOADING_PERCENTAGE == -1) {
            cardLayoutManager.show(this, "search");
            // resets only if going from home, else keeps the results
            if (reset) {
                searchPanel.resetSearchBox();
                searchPanel.resetResults();
            }
        }
    }

    /**
     * Switches to the info panel, along with loading wheel blocking support
     */
    void switchToInfoPanel() {
        if (LOADING_PERCENTAGE == -1)
            cardLayoutManager.show(this, "foodInfo");
    }

    /**
     * Switches to the food groups list panel, along with loading wheel blocking
     * support
     */
    void switchToGroup() {
        if (LOADING_PERCENTAGE == -1) {
            groupPanel.resetScroll();
            cardLayoutManager.show(this, "group");
        }
    }

    /**
     * Switches to the about panel, along with loading wheel blocking support
     */
    void switchToAbout() {
        if (LOADING_PERCENTAGE == -1)
            cardLayoutManager.show(this, "about");
    }

    /**
     * Switches to the list of foods panel
     */
    void switchToFoodListPanel() {
        cardLayoutManager.show(this, "foodList");
    }

    /**
     * Switches to the add food to database panel, along with loading wheel
     * blocking support
     */
    void switchToAddFood() {
        if (LOADING_PERCENTAGE == -1) {
            cardLayoutManager.show(this, "addFood");
            addFoodPanel.resetFields();
        }
    }

    /**
     * Gets the food group list panel
     *
     * @return the groupPanel
     */
    JPanel getGroupPanel() {
        return groupPanel;
    }

    /**
     * Gets the info panel
     *
     * @return the InfoPanel
     */
    InfoPanel getInfoPanel() {
        return infoPanel;
    }

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//    /**
//     * Gets the home panel
//     *
//     * @return the home panel
//     */
//    protected HomePanel getHomePanel() {
//        return homePanel;
//    }
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

    /**
     * Gets the panel of list of foods in a food group
     *
     * @return the foodListPanel
     */
    FoodListPanel getFoodListPanel() {
        return foodListPanel;
    }

    /**
     * Gets the extra info panel
     *
     * @return the extraInfoPanel
     */
    ExtraInfoPanel getExtraInfoPanel() {
        return extraInfoPanel;
    }

    /**
     * Overrides the graphics in order to draw the loading wheel
     *
     * @author Gordon Guan
     */
    @Override
    public void paint(Graphics gr) {
        // set up
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

            g.setColor(GUI.ACCENT_COLOUR);
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
