package parser.parsables;

import parser.Formattable;
import parser.InvalidParseDataException;

/**
 * Represents a single food item
 *
 * @author Gordon Guan
 */
public class FoodItem implements Parsable<FoodItem>, Comparable<FoodItem>,
        Formattable {

    /**
     * Required length of parse data
     */
    public static final int PARSE_DATA_LENGTH = 14;
    /* A sample for creating generic arrays */
    public static final FoodItem SAMPLE = new FoodItem();
    /* The nutrient data of this food item */
    private NutrientData nutrientData = new NutrientData();
    /* The food group of this food item */
    private FoodGroup foodGroup;
    /* The weight statistics of this food item */
    private FoodWeight weightInfo = new FoodWeight();
    /* A set of languals for this food item */
    private LanguaLGroup langualGroup = new LanguaLGroup();
    /* Footnotes for this food item */
    private FootnoteGroup footnotes = new FootnoteGroup();
    /**
     * Nutrient Databank number<br>
     * (5 digits)
     */
    private int nutrientDatabankNumber;
    /**
     * Food Group ID<br>
     * (4 digits)
     */
    private int foodGroupID;
    /**
     * 200-character description of the item
     */
    private String longDescription;
    /**
     * Common name associated with the item<br>
     * Can be blank
     */
    private String commonName;
    /**
     * Manufacturer company name<br>
     * Can be blank
     */
    private String manufacturerName;
    /**
     * Description of refuse (inedible parts) of the food<br>
     * Can be blank
     */
    private String refuseDescription;
    /**
     * Percentage of the food which is refuse<br>
     * Can be blank
     */
    private double percentRefuse;
    /**
     * Scientific name of the food item<br>
     * Can be blank
     */
    private String scientificName;

    // All the fields start here

    public FoodItem() {

    }

    @Override
    public String getFormat() {
        return Formattable.getFileFormatted(
                String.format("~%05d~", nutrientDatabankNumber),
                String.format("~%04d~", foodGroupID), "~" + longDescription
                        + "~", "~~", "~" + commonName + "~", "~"
                        + manufacturerName + "~", "~~", "~" + refuseDescription
                        + "~", percentRefuse + "", "~" + scientificName + "~",
                "", "", "", "");
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof FoodItem && ((FoodItem) o).getNDBNo() == this.getNDBNo();
    }

    @Override
    public FoodItem parse(String[] data) throws InvalidParseDataException {
        if (data.length != PARSE_DATA_LENGTH)
            throw new InvalidParseDataException();
        nutrientDatabankNumber = Integer.parseInt(data[0]);
        foodGroupID = Integer.parseInt(data[1]);
        longDescription = data[2];
        // shortDescription = data[3];
        commonName = data[4];
        manufacturerName = data[5];
        // isSurvey = data[6].equals("Y");
        refuseDescription = data[7];
        percentRefuse = data[8].equals("") ? 0 : Double.parseDouble(data[8]);
        scientificName = data[9];
        // nitrogenFactor = data[10].equals("") ? 0 :
        // Double.parseDouble(data[10]);
        // proteinFactor = data[11].equals("") ? 0 :
        // Double.parseDouble(data[11]);
        // fatFactor = data[12].equals("") ? 0 : Double.parseDouble(data[12]);
        // cholestrolFactor = data[13].equals("") ? 0 :
        // Double.parseDouble(data[13]);
        return this;
    }

    @Override
    public int compareTo(FoodItem o) {
        return this.getNDBNo() - o.getNDBNo();
    }

    /**
     * @return Gets the nutritional data of this FoodItem
     */
    public NutrientData getNutrientData() {
        return nutrientData;
    }

    /**
     * @param nutrientData the nutrientData to set
     */
    public void setNutrientData(NutrientData nutrientData) {
        this.nutrientData = nutrientData;
    }

    /**
     * @return Gets the FoodGroup of this FoodItem
     */
    public FoodGroup getFoodGroup() {
        return foodGroup;
    }

    /**
     * @param foodGroup the foodGroup to set
     */
    public void setFoodGroup(FoodGroup foodGroup) {
        this.foodGroup = foodGroup;
    }

    /**
     * @return The information about weights of this FoodItem
     */
    public FoodWeight getWeightInfo() {
        return weightInfo;
    }

    public void setWeightInfo(FoodWeight fw) {
        this.weightInfo = fw;
    }

    /**
     * @return The LanguaLGroup of this FoodItem, containing all LanguaL
     * descriptions
     */
    public LanguaLGroup getLangualGroup() {
        return langualGroup;
    }

    public void setLangualGroup(LanguaLGroup langualGroup) {
        this.langualGroup = langualGroup;
    }

    /**
     * @return the footnotes
     */
    public FootnoteGroup getFootnoteGroup() {
        return footnotes;
    }

    /**
     * @param footnotes the footnotes to set
     */
    public void setFootnotes(FootnoteGroup footnotes) {
        this.footnotes = footnotes;
    }

    /**
     * @return The Nutrient DataBank number of this food item
     */
    public int getNDBNo() {
        return nutrientDatabankNumber;
    }

    /**
     * @return The food group ID of the food item
     */
    public int getFoodGroupID() {
        return foodGroupID;
    }

    /**
     * @return The long description of this food item
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * @return A common name for this item
     */
    public String getCommonName() {
        return commonName;
    }

    /**
     * @return The manufacturer's name of the item
     */
    public String getManufacturerName() {
        return manufacturerName;
    }

    /**
     * @return The scientific name for this item
     */
    public String getScientificName() {
        return scientificName;
    }

    /**
     * @return The factor
     */
    public String toString() {
        return this.getLongDescription();
    }

}