package parser;

import gui.GUI;
import parser.parsables.*;
import parser.util.BinaryTreeMap;
import parser.util.DoublyLinkedList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Creates data structures out of the given files
 *
 * @author Gordon Guan
 */
public class Parser {
    
    // Various maps for storing indexes to temporary data
    private final BinaryTreeMap<Integer, FoodItem> map_foodItems = new BinaryTreeMap<>();

    private final BinaryTreeMap<Integer, NutrientData> map_nutrData = new BinaryTreeMap<>();
    private final BinaryTreeMap<Integer, NutrientInfo> map_nutrDesc = new BinaryTreeMap<>();
    private final BinaryTreeMap<Integer, FoodGroup> map_foodGroup = new BinaryTreeMap<>();
    private final BinaryTreeMap<Integer, FoodWeight> map_foodWeight = new BinaryTreeMap<>();
    private final BinaryTreeMap<Integer, LanguaLGroup> map_langualGroup = new BinaryTreeMap<>();
    private final BinaryTreeMap<String, LanguaLDescription> map_langualDesc = new BinaryTreeMap<>();
    private final BinaryTreeMap<Integer, Footnote> map_footnote = new BinaryTreeMap<>();

    /**
     * In this order: 0 File foodDesc, 1 File nutrientData, 2 File
     * nutrientDescription, 3 File foodGroup, 4 File foodWeight, 5 File langual,
     * 6 File langualDesc, 7 File footnotes
     */
    private final File[] dataFiles;
    private final GUI gui;
    private long totalFileSize = 0;
    private long processedFileSize = 0;

    public Parser(File[] files, GUI gui) {
        this.dataFiles = files;
        this.gui = gui;
        for (File f : files) {
            totalFileSize += f.length();
        }
    }

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//    /**
//     * Get a list of food items parsed
//     *
//     * @return a list of food items parsed
//     */
//    public DoublyLinkedList<FoodItem> getParsedData() {
//        return map_foodItems.getAllValues();
//    }
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

    public BinaryTreeMap<Integer, FoodItem> getFoodItemMap() {
        return map_foodItems;
    }

    /**
     * Parses all the data in the given files in a separate thread
     */
    public void parseData() {
        try {
            long start = System.currentTimeMillis();

            System.err.println("PARSING FOOD GROUPS");
            parseFoodGroups();
            System.err.println("PARSING FOOD WEIGHTS");
            parseFoodWeights();
            System.err.println("PARSING FOOTNOTES");
            parseFootnotes();
            System.err.println("PARSING NUTRIENT DEFINITIONS");
            parseNutrientDefinitions();
            System.err.println("PARSING NUTRIENT DATA");
            parseNutrientData();
            System.err.println("PARSING LANGUAL DESCRIPTIONS");
            parseLanguaLDescriptions();
            System.err.println("PARSING LANGUAL DATA");
            parseLanguaL();
            System.err.println("PARSING FOOD DESCRIPTIONS");
            parseFoodDescriptions();
            System.err.println("DONE");
            if (gui != null) {
                gui.getPanelManager().LOADING_PERCENTAGE = 100;
            }

            long end = System.currentTimeMillis() - start;
            System.err.println("Took " + end + "ms");
            Thread.sleep(1000);
            if (gui != null) {
                gui.getPanelManager().LOADING_PERCENTAGE = -1;
                gui.getPanelManager().repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Get all nutrient info
     *
     * @return all nutrient info
     */
    public BinaryTreeMap<Integer, NutrientInfo> getNutrientInfo() {
        return map_nutrDesc;
    }

    /**
     * Parse footnotes file
     *
     * @throws IOException               when file error occurs
     * @throws InvalidParseDataException when parsed data is in wrong format
     */
    private void parseFootnotes() throws IOException, InvalidParseDataException {
        BufferedReader br = new BufferedReader(new FileReader(dataFiles[7]));

        String line;
        while ((line = br.readLine()) != null) {
            processedFileSize += line.getBytes().length + 1;
            line = line.replace("~", "");
            // using .split now since stringtokenizer ignores empty values
            String[] items = splitTokens(line, Footnote.PARSE_DATA_LENGTH);
            Footnote footnote = new Footnote().parse(items);
            map_footnote.put(footnote.getNdbNo(), footnote);
            updatePercentage();

        }
        br.close();
    }

    /**
     * Parses descriptions for LanguaL definitions
     *
     * @throws IOException
     * @throws InvalidParseDataException
     */
    private void parseLanguaLDescriptions() throws IOException, InvalidParseDataException {
        BufferedReader br = new BufferedReader(new FileReader(dataFiles[6]));

        String line;
        while ((line = br.readLine()) != null) {
            processedFileSize += line.getBytes().length + 1;
            line = line.replace("~", "");
            // using .split now since stringtokenizer ignores empty values
            String[] items = splitTokens(line, LanguaLDescription.PARSE_DATA_LENGTH);
            LanguaLDescription lld = new LanguaLDescription().parse(items);
            map_langualDesc.put(lld.getFactorCode(), lld);
            updatePercentage();

        }
        br.close();
    }

    /**
     * Parses LanguaL names for food items
     *
     * @throws IOException
     * @throws InvalidParseDataException
     */
    private void parseLanguaL() throws IOException, InvalidParseDataException {
        BufferedReader br = new BufferedReader(new FileReader(dataFiles[5]));

        String line;
        while ((line = br.readLine()) != null) {
            processedFileSize += line.getBytes().length + 1;
            line = line.replace("~", "");
            // using .split now since stringtokenizer ignores empty values
            String[] items = splitTokens(line, LanguaL.PARSE_DATA_LENGTH);
            LanguaL ll = new LanguaL().parse(items);
            ll.setLangualDescription(map_langualDesc.get(ll.getFactorCode()));
            if (map_langualGroup.get(ll.getNDBNo()) == null)
                map_langualGroup.put(ll.getNDBNo(), new LanguaLGroup());
            map_langualGroup.get(ll.getNDBNo()).addLanguaL(ll);
            updatePercentage();

        }
        br.close();
    }

    /**
     * Parses all the food weights
     *
     * @throws IOException
     * @throws InvalidParseDataException
     */
    private void parseFoodWeights() throws IOException, InvalidParseDataException {
        BufferedReader br = new BufferedReader(new FileReader(dataFiles[4]));

        String line;
        while ((line = br.readLine()) != null) {
            processedFileSize += line.getBytes().length + 1;
            line = line.replace("~", "");
            // using .split now since stringtokenizer ignores empty values
            String[] items = splitTokens(line, WeightUnit.PARSE_DATA_LENGTH);
            WeightUnit weightUnit = new WeightUnit().parse(items);
            if (map_foodWeight.get(weightUnit.getNDBNo()) == null)
                map_foodWeight.put(weightUnit.getNDBNo(), new FoodWeight());
            map_foodWeight.get(weightUnit.getNDBNo()).addWeightUnit(weightUnit);
            updatePercentage();

        }
        br.close();
    }

    /**
     * Parses all the food groups
     *
     * @throws IOException
     * @throws InvalidParseDataException
     */
    private void parseFoodGroups() throws IOException, InvalidParseDataException {
        BufferedReader br = new BufferedReader(new FileReader(dataFiles[3]));

        String line;
        while ((line = br.readLine()) != null) {
            processedFileSize += line.getBytes().length + 1;
            line = line.replace("~", "");
            // using .split now since stringtokenizer ignores empty values
            String[] items = splitTokens(line, 2);
            FoodGroup foodGroup = new FoodGroup().parse(items);
            map_foodGroup.put(foodGroup.getFoodGroupID(), foodGroup);
            updatePercentage();

        }
        br.close();
    }

    /**
     * Reads all the food descriptions and places it into a lookup table Sets
     * the proper food group of the item Sets the proper nutrients of the item
     *
     * @throws IOException
     * @throws InvalidParseDataException
     */
    private void parseFoodDescriptions() throws IOException, InvalidParseDataException {
        BufferedReader br = new BufferedReader(new FileReader(dataFiles[0]));

        String line;
        while ((line = br.readLine()) != null) {
            processedFileSize += line.getBytes().length + 1;
            line = line.replace("~", "");
            // using .split now since stringtokenizer ignores empty values
            String[] items = splitTokens(line, FoodItem.PARSE_DATA_LENGTH);
            FoodItem foodItem = new FoodItem().parse(items);
            int ndbNo = foodItem.getNDBNo();
            // Set various references to other data based on NDB number
            foodItem.setFoodGroup(map_foodGroup.get(foodItem.getFoodGroupID()));
            foodItem.setWeightInfo(map_foodWeight.get(ndbNo));
            foodItem.setNutrientData(map_nutrData.get(ndbNo));
            foodItem.setLangualGroup(map_langualGroup.get(ndbNo));
            foodItem.setFootnotes(map_footnote.get(ndbNo));
            addFoodItem(foodItem);
            updatePercentage();
        }
        br.close();
    }

    public void addFoodItem(FoodItem foodItem) {
        int ndbNo = foodItem.getNDBNo();
        foodItem.getFoodGroup().addFood(foodItem);
        map_foodItems.put(ndbNo, foodItem);
    }

    /**
     * Parses all the nutrient definitions
     *
     * @throws IOException
     * @throws InvalidParseDataException
     */
    private void parseNutrientDefinitions() throws IOException, InvalidParseDataException {
        BufferedReader br = new BufferedReader(new FileReader(dataFiles[2]));

        String line;
        while ((line = br.readLine()) != null) {
            processedFileSize += line.getBytes().length + 1;
            line = line.replace("~", "");
            String[] items = splitTokens(line, NutrientInfo.PARSE_DATA_LENGTH);
            NutrientInfo nd = new NutrientInfo().parse(items);
            map_nutrDesc.put(nd.getNutrientNumber(), nd);
            updatePercentage();

        }
        br.close();
    }

    /**
     * Reads the nutrient data, and adds it to the appropriate food item Sets
     * the nutrient definition of the nutrient according to the table
     *
     * @throws IOException
     * @throws InvalidParseDataException
     */
    private void parseNutrientData() throws IOException, InvalidParseDataException {
        BufferedReader br = new BufferedReader(new FileReader(dataFiles[1]));

        String line;
        while ((line = br.readLine()) != null) {
            processedFileSize += line.getBytes().length + 1;
            line = line.replace("~", "");
            String[] items = splitTokens(line, Nutrient.PARSE_DATA_LENGTH);
            Nutrient nutr = new Nutrient().parse(items);
            nutr.setNutrientDescription(map_nutrDesc.get((int) nutr.getNutrNo()));
            NutrientData nd = map_nutrData.get(nutr.getNDBNo());
            if (nd == null)
                map_nutrData.put(nutr.getNDBNo(), new NutrientData());
            map_nutrData.get(nutr.getNDBNo()).addNutrient(nutr);
            updatePercentage();
        }
        br.close();
    }

    /**
     * Split a string into tokens from a delimiter
     * @param item the string to split
     * @param length the number of tokens
     * @return the tokens in the string
     */
    private String[] splitTokens(String item, int length) {
        String[] items = new String[length];
        int lastIdx = 0;
        for (int i = 0; i < length; i++) {
            int idx = item.indexOf("^", lastIdx);
            items[i] = item.substring(lastIdx, idx == -1 ? item.length() : idx);
            lastIdx = idx + "^".length();
        }
        return items;
    }

    /**
     * Tells the GUI to update the loading bar
     */
    private void updatePercentage() {
        if (gui != null) {
            gui.getPanelManager().LOADING_PERCENTAGE = (int) (100.0 * processedFileSize / totalFileSize);
            gui.getPanelManager().repaint();
        }
    }

    /**
     * Gets all food groups
     * @return all food groups
     */
    public DoublyLinkedList<FoodGroup> getFoodGroups() {
        return map_foodGroup.getAllValues();
    }
}
