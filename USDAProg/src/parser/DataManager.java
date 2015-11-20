package parser;

import gui.GUI;
import parser.parsables.*;
import parser.util.BinaryTreeMap;
import parser.util.DoublyLinkedList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import javax.swing.JOptionPane;

/**
 * An interface for the GUI to use with easy access to parser methods
 *
 * @author Gordon Guan
 */
public class DataManager {

    private static DataManager instance;

    /**
     * All code to be run after the data is done loading
     */
    private final DoublyLinkedList<Runnable> hooks = new DoublyLinkedList<>();

    private Parser parser;
    private GUI gui;
    private File[] files;

    private DataManager() {

    }

    /**
     * Creates a new instance of DataManager if it is null, or return the
     * existing instance. There should never be more than one DataManager in
     * existence.
     *
     * @return the instance of the DataManager
     */
    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    /**
     * Tells the DataManager to use the following GUI for displaying loading
     * progress
     *
     * @param gui the gui to bind to display
     */
    public void bindProgressDisplay(GUI gui) {
        this.gui = gui;
    }

    /**
     * Initialize the parser with the given data files.
     *
     * @param files In this order: 0 File foodDesc, 1 File nutrientData, 2 File
     *              nutrientDescription, 3 File foodGroup, 4 File foodWeight, 5
     *              File langual, 6 File langualDesc, 7 File footnotes
     */
    public void init(File... files) {
        this.files = files;
        parser = new Parser(files, gui);
        new Thread() {
            public void run() {
                parser.parseData();
                for (int i = 0; i < hooks.size(); i++) {
                    Runnable r = hooks.get(i);
                    r.run();
                }
            }
        }.start();
    }

    /**
     * Run any given code after all parsing is complete
     *
     * @param r The runnable containing the event to run
     */
    public void registerSyncEvent(Runnable r) {
        hooks.add(r);
    }

    /**
     * Gets the first NDB number which is not in use
     *
     * @return an unused NDB number
     */
    public int getUnusedNDBNumber() {
        for (int i = 0; i < 99999; i++) {
            if (this.parser.getFoodItemMap().get(i) == null)
                return i;
        }
        return -1;
    }

    /**
     * Generates relevant keywords from a food item to search by
     *
     * @param food The food item
     * @return relevant keywords
     */
    public String getRelevantKeywords(FoodItem food) {
        String titleName;
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
        return titleName;
    }

    /**
     * Adds a food item into the appropriate files, and loads it into the data
     * structure
     *
     * @param fi The food item to add
     */
    public boolean addFoodItem(FoodItem fi) {
        NutrientData nd = fi.getNutrientData();
        FootnoteGroup fn = fi.getFootnoteGroup();
        FoodWeight wi = fi.getWeightInfo();

        addFormattable(files[0], fi);
        if (fn != null) {
            for(Footnote f : fn.getFootnotes()){
                addFormattable(files[7], f);
            }

        }
        // todo broken
        if (wi != null) {
            for (WeightUnit wu : wi.getWeightUnits()) {
                addFormattable(files[4], wu);
            }
        }
        if (nd != null) {
            for (int i = 0; i < nd.getNutrients().size(); i++) {
                addFormattable(files[1], nd.getNutrients().get(i));
            }
        }
        parser.addFoodItem(fi);

        // TODO make this actually work and not always return true
        return true;
    }

    /**
     * Adds a Formattable into a file, using its .getFormat() to get the file
     * formatted string
     *
     * @param file The file to add the data in
     * @param form The formattable which contains the data
     */
    private void addFormattable(File file, Formattable form) {
        try {
            PrintStream ps = new PrintStream(new FileOutputStream(file, true));
            ps.println(form.getFormat());
            ps.close();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Error in writing to file",
                    "File I/O", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Gets an array of all the foodgroups
     *
     * @return an array of all the food groups
     */
    public FoodGroup[] getFoodGroups() {
        return parser.getFoodGroups().toArray(FoodGroup.SAMPLE);
    }

    public NutrientInfo[] getNutrientData() {
        return parser.getNutrientInfo().getAllValues()
                .toArray(NutrientInfo.SAMPLE);
    }

    public Parser getParser() {
        return parser;
    }

    /**
     * Searches for an item in the food database
     *
     * @param keys The keywords to search by
     * @return A list of all the matched food items
     */
    public FoodItem[] searchForItem(String[] keys) {
        if (keys.length == 0)
            return new FoodItem[0];
        // Check if the user is searching for an NDB number
        if (keys.length == 1) {
            if (keys[0].matches("[0-9]{5}")) {
                if (parser.getFoodItemMap().get(Integer.parseInt(keys[0])) != null)
                    return new FoodItem[]{parser.getFoodItemMap().get(
                            Integer.parseInt(keys[0]))};
                else
                    return new FoodItem[0];
            }
        }
        // Get the map of food items
        BinaryTreeMap<Integer, FoodItem> map_foodItems = parser
                .getFoodItemMap();
        // Extract the list of food items from the map
        FoodItem[] foodItems = map_foodItems.getAllValues().toArray(
                FoodItem.SAMPLE);
        // Create a map to store the score of each food item
        BinaryTreeMap<FoodItem, Double> map_results = new BinaryTreeMap<>();
        for (FoodItem foodItem1 : foodItems) {
            // Rank each food item by appearance of keys in the text
            double score = 0;
            FoodItem foodItem = foodItem1;
            String[] tokens = foodItem.getLongDescription()
                    .replaceAll("[^A-Za-z ]", "").split(" ");
            boolean relevant = false;
            for (int keyIdx = 0; keyIdx < Math.min(keys.length, 5); keyIdx++) {
                // Check if the phrase contains it
                if (foodItem.getLongDescription().toLowerCase()
                        .contains(keys[keyIdx].toLowerCase())) {
                    score += 0.5;
                    if (foodItem.getLongDescription().toLowerCase()
                            .startsWith(keys[keyIdx].toLowerCase())) {
                        score += 0.2;
                    }
                    // Check for whole tokens of the key
                    boolean found = false;
                    for (String token : tokens) {
                        String currentToken = token;
                        if (token.toLowerCase().endsWith("s")
                                && !keys[keyIdx].toLowerCase().endsWith("s")) {
                            currentToken = currentToken.substring(0,
                                    currentToken.length() - 1);
                        }
                        if (currentToken.equalsIgnoreCase(keys[keyIdx])) {
                            found = true;
                            break;
                        }
                    }
                    if (found)
                        score += 1.1;
                    relevant = true;
                }

                // Check if the common name contains the key
                if (foodItem.getCommonName().toLowerCase()
                        .contains(keys[keyIdx].toLowerCase())) {
                    score += 1;
                    relevant = true;
                }

            }
            // Prioritize shorter results
            if (relevant) {
                score += 1.0 / foodItem.getLongDescription().length();
            }
            if (score > 0)
                map_results.put(foodItem, score);
        }
        // System.out.println(map_results);
        // Get all the food items which had a score of at least 1
        FoodItem[] matched = map_results.getAllKeys().toArray(FoodItem.SAMPLE);
        if (matched == null)
            return new FoodItem[0];
        // Sort the list of food items by their score
        Arrays.sort(matched, (a, b) -> {
            if (map_results.get(b) > map_results.get(a))
                return 1;
            else if (map_results.get(b) < map_results.get(a))
                return -1;
            return 0;
        });

        // Return a list of matches capped at 25
        return Arrays.copyOf(matched, Math.min(matched.length, 500));
    }

}
