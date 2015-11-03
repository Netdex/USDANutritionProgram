package parser;

public class FoodItem {

	/**
	 * Nutrition Data Bank number<br>
	 * (5 digits)
	 */
	private int NDB_No;

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
	 * Scientific name of the food item<br>
	 * Can be blank
	 */
	private String scientificName;

	/**
	 * The food group of the item <br>
	 */
	private FoodGroup foodGroup;

	private NutrientData nutrientData;
	
	/**
	 * Creates an instance of a food item.
	 * 
	 * @param nDB_No
	 *            The unique identifier for this food item
	 * @param foodGroupID
	 *            The food group ID of this food item
	 * @param longDescription
	 *            A long description of this food
	 * @param shortDescription
	 *            A short description of this food
	 * @param commonName
	 *            A common name for this food
	 * @param manufacturerName
	 *            A manufacturer name for this food
	 * @param survey
	 *            Whether this food is used in a USDA survey
	 * @param refuseDescription
	 *            A description of the refuse in this food
	 * @param refuse
	 *            A percentage of refuse in this food
	 * @param scientificName
	 *            The scientific name for this food
	 * @param n_Factor
	 *            The factor for converting nitrogen to protein
	 * @param pro_Factor
	 *            The factor for calculating calories from protein
	 * @param fat_Factor
	 *            The factor for calculating calories from fat
	 * @param cHO_Factor
	 *            The factor for calculating calories from carbohydrate
	 * @return An instance of the food item with the specified parameters
	 */
	public FoodItem(int nDB_No, int foodGroupID, String longDescription,
			String shortDescription, String commonName, String manufacturerName,
			String scientificName, FoodGroup foodGroup) {
		this.NDB_No = nDB_No;
		this.foodGroupID = foodGroupID;
		this.longDescription = longDescription;
		this.shortDescription = shortDescription;
		this.commonName = commonName;
		this.manufacturerName = manufacturerName;
		this.scientificName = scientificName;
		this.foodGroup = foodGroup;
	}

	/**
	 * @return the nDB_No
	 */
	public int getNDB_No() {
		return NDB_No;
	}

	/**
	 * @return the fdGrp_Cd
	 */
	public int getFdGrp_Cd() {
		return foodGroupID;
	}

	/**
	 * @return the long_Desc
	 */
	public String getLong_Desc() {
		return longDescription;
	}

	/**
	 * @return the shrt_Desc
	 */
	public String getShrt_Desc() {
		return shortDescription;
	}

	/**
	 * @return the commonName
	 */
	public String getCommonName() {
		return commonName;
	}

	/**
	 * @return the manufacName
	 */
	public String getManufacName() {
		return manufacturerName;
	}

	/**
	 * @return the sciName
	 */
	public String getSciName() {
		return scientificName;
	}

	/**
	 * @return the FoodGroup
	 */
	public FoodGroup getFoodGroup() {
		return foodGroup;
	}
}
