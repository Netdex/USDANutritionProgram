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
	
	@Override
	public void parse(String[] data) throws InvalidParseDataException {
		if(data.length != PARSE_DATA_LENGTH)
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

	private NutrientData nutrientData = new NutrientData();

	public FoodItem() {

	}

	private FoodItem(int nDB_No, int fdGrp_Cd, String long_Desc, String shrt_Desc, String comName, String manufacName,
			boolean survey, String ref_desc, double refuse, String sciName, double n_Factor, double pro_Factor,
			double fat_Factor, double cHO_Factor) {
		super();
		nutrientDatabankNumber = nDB_No;
		foodGroupID = fdGrp_Cd;
		longDescription = long_Desc;
		shortDescription = shrt_Desc;
		commonName = comName;
		manufacturerName = manufacName;
		isSurvey = survey;
		refuseDescription = ref_desc;
		percentRefuse = refuse;
		scientificName = sciName;
		nitrogenFactor = n_Factor;
		proteinFactor = pro_Factor;
		fatFactor = fat_Factor;
		cholestrolFactor = cHO_Factor;
	}

	/**
	 * Creates an instance of a food item.
	 * 
	 * @param nDB_No
	 *            The unique identifier for this food item
	 * @param fdGrp_Cd
	 *            The food group ID of this food item
	 * @param long_Desc
	 *            A long description of this food
	 * @param shrt_Desc
	 *            A short description of this food
	 * @param comName
	 *            A common name for this food
	 * @param manufacName
	 *            A manufacturer name for this food
	 * @param survey
	 *            Whether this food is used in a USDA survey
	 * @param ref_desc
	 *            A description of the refuse in this food
	 * @param refuse
	 *            A percentage of refuse in this food
	 * @param sciName
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
	public static FoodItem getInstance(int nDB_No, int fdGrp_Cd, String long_Desc, String shrt_Desc, String comName,
			String manufacName, boolean survey, String ref_desc, double refuse, String sciName, double n_Factor,
			double pro_Factor, double fat_Factor, double cHO_Factor) {
		return new FoodItem(nDB_No, fdGrp_Cd, long_Desc, shrt_Desc, comName, manufacName, survey, ref_desc, refuse,
				sciName, n_Factor, pro_Factor, fat_Factor, cHO_Factor);
	}

	/**
	 * @return the nDB_No
	 */
	public int getNutrientDatabankNumber() {
		return nutrientDatabankNumber;
	}

	/**
	 * @param nDB_No
	 *            the nDB_No to set
	 */
	public void setNutrientDatabankNumber(int nDB_No) {
		nutrientDatabankNumber = nDB_No;
	}

	/**
	 * @return the fdGrp_Cd
	 */
	public int getFoodGroupID() {
		return foodGroupID;
	}

	/**
	 * @param fdGrp_Cd
	 *            the fdGrp_Cd to set
	 */
	public void setFoodGroupID(int fdGrp_Cd) {
		foodGroupID = fdGrp_Cd;
	}

	/**
	 * @return the long_Desc
	 */
	public String getLongDescription() {
		return longDescription;
	}

	/**
	 * @param long_Desc
	 *            the long_Desc to set
	 */
	public void setLongDescription(String long_Desc) {
		longDescription = long_Desc;
	}

	/**
	 * @return the shrt_Desc
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * @param shrt_Desc
	 *            the shrt_Desc to set
	 */
	public void setShortDescription(String shrt_Desc) {
		shortDescription = shrt_Desc;
	}

	/**
	 * @return the comName
	 */
	public String getCommmonName() {
		return commonName;
	}

	/**
	 * @param comName
	 *            the comName to set
	 */
	public void setCommmonName(String comName) {
		commonName = comName;
	}

	/**
	 * @return the manufacName
	 */
	public String getManufacturerName() {
		return manufacturerName;
	}

	/**
	 * @param manufacName
	 *            the manufacName to set
	 */
	public void setManufacturerName(String manufacName) {
		manufacturerName = manufacName;
	}

	/**
	 * @return the survey
	 */
	public boolean isSurvey() {
		return isSurvey;
	}

	/**
	 * @param survey
	 *            the survey to set
	 */
	public void setSurvey(boolean survey) {
		isSurvey = survey;
	}

	/**
	 * @return the ref_desc
	 */
	public String getRefuseDescription() {
		return refuseDescription;
	}

	/**
	 * @param ref_desc
	 *            the ref_desc to set
	 */
	public void setRefuseDescription(String ref_desc) {
		refuseDescription = ref_desc;
	}

	/**
	 * @return the refuse
	 */
	public double getRefusePercentage() {
		return percentRefuse;
	}

	/**
	 * @param refuse
	 *            the refuse to set
	 */
	public void setRefusePercentage(double refuse) {
		percentRefuse = refuse;
	}

	/**
	 * @return the sciName
	 */
	public String getScientificName() {
		return scientificName;
	}

	/**
	 * @param sciName
	 *            the sciName to set
	 */
	public void setScientificName(String sciName) {
		scientificName = sciName;
	}

	/**
	 * @return the n_Factor
	 */
	public double getNitrogenFactor() {
		return nitrogenFactor;
	}

	/**
	 * @param n_Factor
	 *            the n_Factor to set
	 */
	public void setNitrogenFactor(double n_Factor) {
		nitrogenFactor = n_Factor;
	}

	/**
	 * @return the pro_Factor
	 */
	public double getProteinFactor() {
		return proteinFactor;
	}

	/**
	 * @param pro_Factor
	 *            the pro_Factor to set
	 */
	public void setProteinFactor(double pro_Factor) {
		proteinFactor = pro_Factor;
	}

	/**
	 * @return the fat_Factor
	 */
	public double getFatFactor() {
		return fatFactor;
	}

	/**
	 * @param fat_Factor
	 *            the fat_Factor to set
	 */
	public void setFatFactor(double fat_Factor) {
		fatFactor = fat_Factor;
	}

	/**
	 * @return the cHO_Factor
	 */
	public double getCholestrolFactor() {
		return cholestrolFactor;
	}

	/**
	 * @param cHO_Factor
	 *            the cHO_Factor to set
	 */
	public void setCholestrolFactor(double cHO_Factor) {
		cholestrolFactor = cHO_Factor;
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
	public void setNutrientData(NutrientData nutrientData) {
		this.nutrientData = nutrientData;
	}

}