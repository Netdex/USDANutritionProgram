package parser.parsables;

import parser.InvalidParseDataException;

/**
 * Represents a single food item
 * 
 * @author Netdex
 *
 */
public class FoodItem implements Parsable<FoodItem>, Comparable<FoodItem> {

	private static final int PARSE_DATA_LENGTH = 14;

	private NutrientData nutrientData = new NutrientData();
	private FoodGroup foodGroup;
	private FoodWeight foodWeight;
	private LanguaLGroup langualGroup;
	private Footnote footnotes;
	
	@Override
	public boolean equals(Object o){
		if(o instanceof FoodItem){
			return ((FoodItem)o).getNDBNo() == this.getNDBNo();
		}
		return false;
		
	}
	@Override
	public FoodItem parse(String[] data) throws InvalidParseDataException {
		if (data.length != PARSE_DATA_LENGTH)
			throw new InvalidParseDataException();
		nutrientDatabankNumber = Integer.parseInt(data[0]);
		foodGroupID = Integer.parseInt(data[1]);
		longDescription = data[2];
		shortDescription = data[3];
		commonName = data[4];
		manufacturerName = data[5];
		isSurvey = data[6].equals("Y");
		refuseDescription = data[7];
		percentRefuse = data[8].equals("") ? 0 : Double.parseDouble(data[8]);
		scientificName = data[9];
		nitrogenFactor = data[10].equals("") ? 0 : Double.parseDouble(data[10]);
		proteinFactor = data[11].equals("") ? 0 : Double.parseDouble(data[11]);
		fatFactor = data[12].equals("") ? 0 : Double.parseDouble(data[12]);
		cholestrolFactor = data[13].equals("") ? 0 : Double.parseDouble(data[13]);
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
	 * @param nutrientData
	 *            the nutrientData to set
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
	 * @param foodGroup
	 *            the foodGroup to set
	 */
	public void setFoodGroup(FoodGroup foodGroup) {
		this.foodGroup = foodGroup;
	}

	/**
	 * @return The information about weights of this FoodItem
	 */
	public FoodWeight getWeightInfo() {
		return foodWeight;
	}

	public void setWeightInfo(FoodWeight fw) {
		this.foodWeight = fw;
	}

	// All the fields start here
	
	/**
	 * @return The LanguaLGroup of this FoodItem, containing all LanguaL descriptions
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
	public Footnote getFootnotes() {
		return footnotes;
	}

	/**
	 * @param footnotes the footnotes to set
	 */
	public void setFootnotes(Footnote footnotes) {
		this.footnotes = footnotes;
	}

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

	/** 200-character description of the item */
	private String longDescription;

	/** 60-character abbreviated description of the item */
	private String shortDescription;

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
	 * Indicates if the food item is used in a USDA survey database<br>
	 * Y/N<br>
	 * Can be blank
	 */
	private boolean isSurvey;

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

	/**
	 * Factor for converting nitrogen to protein<br>
	 * Can be blank
	 */
	private double nitrogenFactor;

	/**
	 * Factor for calculating calories from protein<br>
	 * Can be blank
	 */
	private double proteinFactor;

	/**
	 * Factor for calculating calories from fat<br>
	 * Can be blank
	 */
	private double fatFactor;

	/**
	 * Factor for calculating calories from carbohydrate<br>
	 * Can be blank
	 */
	private double cholestrolFactor;

	public FoodItem() {

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
	 * @return The short description of this food item
	 */
	public String getShortDescription() {
		return shortDescription;
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
	 * @return Whether this document is in a USDA survey
	 */
	public boolean isSurvey() {
		return isSurvey;
	}

	/**
	 * @return A description of the refuse in this food item
	 */
	public String getRefuseDescription() {
		return refuseDescription;
	}

	/**
	 * @return The percentage of the food item which is refuse
	 */
	public double getRefusePercentage() {
		return percentRefuse;
	}

	/**
	 * @return The scientific name for this item
	 */
	public String getScientificName() {
		return scientificName;
	}

	/**
	 * @return The factor required for converting nitrogen in this food item
	 */
	public double getNitrogenFactor() {
		return nitrogenFactor;
	}

	/**
	 * @return The factor required for calculating protein in this food item
	 */
	public double getProteinFactor() {
		return proteinFactor;
	}

	/**
	 * @return The factor required for calculating fat in this food item
	 */
	public double getFatFactor() {
		return fatFactor;
	}

	/**
	 * @return The factor required for calculating cholestrol in this food item
	 */
	public double getCholestrolFactor() {
		return cholestrolFactor;
	}
	
	/**
	 * @return The factor
	 */
	public String toString(){
		return this.getLongDescription();
	}

	

}