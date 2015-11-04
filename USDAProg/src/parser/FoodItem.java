package parser;

/**
 * I have reverted the structure temporarily since this structure is actually
 * required for the parser to work This class will be revised in the future to
 * use a "builder" structure instead of this giant ass constructor
 * 
 * @author Netdex
 *
 */
public class FoodItem implements Parsable<FoodItem> {

	private static final int PARSE_DATA_LENGTH = 14;

	private NutrientData nutrientData = new NutrientData();
	private FoodGroup foodGroup;
	private FoodWeight foodWeight;

	@Override
	public void parse(String[] data) throws InvalidParseDataException {
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
	}

	/**
	 * @return the nutrientData
	 */
	public NutrientData getNutrientData() {
		return nutrientData;
	}

	/**
	 * @param nutrientData
	 *            the nutrientData to set
	 */
	protected void setNutrientData(NutrientData nutrientData) {
		this.nutrientData = nutrientData;
	}

	/**
	 * @return the foodGroup
	 */
	public FoodGroup getFoodGroup() {
		return foodGroup;
	}

	/**
	 * @param foodGroup
	 *            the foodGroup to set
	 */
	protected void setFoodGroup(FoodGroup foodGroup) {
		this.foodGroup = foodGroup;
	}

	public FoodWeight getWeightInfo() {
		return foodWeight;
	}

	protected void setWeightInfo(FoodWeight fw) {
		this.foodWeight = fw;
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
	 * @return the nDB_No
	 */
	public int getNutrientDatabankNumber() {
		return nutrientDatabankNumber;
	}

	/**
	 * @return the fdGrp_Cd
	 */
	protected int getFoodGroupID() {
		return foodGroupID;
	}

	/**
	 * @return the long_Desc
	 */
	public String getLongDescription() {
		return longDescription;
	}

	/**
	 * @return the shrt_Desc
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * @return the comName
	 */
	public String getCommmonName() {
		return commonName;
	}

	/**
	 * @return the manufacName
	 */
	public String getManufacturerName() {
		return manufacturerName;
	}

	/**
	 * @return the survey
	 */
	public boolean isSurvey() {
		return isSurvey;
	}

	/**
	 * @return the ref_desc
	 */
	public String getRefuseDescription() {
		return refuseDescription;
	}

	/**
	 * @return the refuse
	 */
	public double getRefusePercentage() {
		return percentRefuse;
	}

	/**
	 * @return the sciName
	 */
	public String getScientificName() {
		return scientificName;
	}

	/**
	 * @return the n_Factor
	 */
	public double getNitrogenFactor() {
		return nitrogenFactor;
	}

	/**
	 * @return the pro_Factor
	 */
	public double getProteinFactor() {
		return proteinFactor;
	}

	/**
	 * @return the fat_Factor
	 */
	public double getFatFactor() {
		return fatFactor;
	}

	/**
	 * @return the cHO_Factor
	 */
	public double getCholestrolFactor() {
		return cholestrolFactor;
	}

}