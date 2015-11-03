package parser;

public class FoodItem {

	/**
	 * Nutrient Databank number<br>
	 * (5 digits)
	 */
	private int NDB_No;

	/**
	 * Food Group ID<br>
	 * (4 digits)
	 */
	private int FdGrp_Cd;

	/** 200-character description of the item */
	private String Long_Desc;

	/** 60-character abbreviated description of the item */
	private String Shrt_Desc;

	/**
	 * Common name associated with the item<br>
	 * Can be blank
	 */
	private String ComName;

	/**
	 * Manufacturer company name<br>
	 * Can be blank
	 */
	private String ManufacName;

	/**
	 * Indicates if the food item is used in a USDA survey database<br>
	 * Y/N<br>
	 * Can be blank
	 */
	private boolean Survey;

	/**
	 * Description of refuse (inedible parts) of the food<br>
	 * Can be blank
	 */
	private String Ref_desc;

	/**
	 * Percentage of the food which is refuse<br>
	 * Can be blank
	 */
	private double Refuse;

	/**
	 * Scientific name of the food item<br>
	 * Can be blank
	 */
	private String SciName;

	/**
	 * Factor for converting nitrogen to protein<br>
	 * Can be blank
	 */
	private double N_Factor;

	/**
	 * Factor for calculating calories from protein<br>
	 * Can be blank
	 */
	private double Pro_Factor;

	/**
	 * Factor for calculating calories from fat<br>
	 * Can be blank
	 */
	private double Fat_Factor;

	/**
	 * Factor for calculating calories from carbohydrate<br>
	 * Can be blank
	 */
	private double CHO_Factor;

	private FoodItem(int nDB_No, int fdGrp_Cd, String long_Desc, String shrt_Desc, String comName, String manufacName,
			boolean survey, String ref_desc, double refuse, String sciName, double n_Factor, double pro_Factor,
			double fat_Factor, double cHO_Factor) {
		super();
		NDB_No = nDB_No;
		FdGrp_Cd = fdGrp_Cd;
		Long_Desc = long_Desc;
		Shrt_Desc = shrt_Desc;
		ComName = comName;
		ManufacName = manufacName;
		Survey = survey;
		Ref_desc = ref_desc;
		Refuse = refuse;
		SciName = sciName;
		N_Factor = n_Factor;
		Pro_Factor = pro_Factor;
		Fat_Factor = fat_Factor;
		CHO_Factor = cHO_Factor;
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
	public int getNDB_No() {
		return NDB_No;
	}

	/**
	 * @param nDB_No
	 *            the nDB_No to set
	 */
	public void setNDB_No(int nDB_No) {
		NDB_No = nDB_No;
	}

	/**
	 * @return the fdGrp_Cd
	 */
	public int getFdGrp_Cd() {
		return FdGrp_Cd;
	}

	/**
	 * @param fdGrp_Cd
	 *            the fdGrp_Cd to set
	 */
	public void setFdGrp_Cd(int fdGrp_Cd) {
		FdGrp_Cd = fdGrp_Cd;
	}

	/**
	 * @return the long_Desc
	 */
	public String getLong_Desc() {
		return Long_Desc;
	}

	/**
	 * @param long_Desc
	 *            the long_Desc to set
	 */
	public void setLong_Desc(String long_Desc) {
		Long_Desc = long_Desc;
	}

	/**
	 * @return the shrt_Desc
	 */
	public String getShrt_Desc() {
		return Shrt_Desc;
	}

	/**
	 * @param shrt_Desc
	 *            the shrt_Desc to set
	 */
	public void setShrt_Desc(String shrt_Desc) {
		Shrt_Desc = shrt_Desc;
	}

	/**
	 * @return the comName
	 */
	public String getComName() {
		return ComName;
	}

	/**
	 * @param comName
	 *            the comName to set
	 */
	public void setComName(String comName) {
		ComName = comName;
	}

	/**
	 * @return the manufacName
	 */
	public String getManufacName() {
		return ManufacName;
	}

	/**
	 * @param manufacName
	 *            the manufacName to set
	 */
	public void setManufacName(String manufacName) {
		ManufacName = manufacName;
	}

	/**
	 * @return the survey
	 */
	public boolean isSurvey() {
		return Survey;
	}

	/**
	 * @param survey
	 *            the survey to set
	 */
	public void setSurvey(boolean survey) {
		Survey = survey;
	}

	/**
	 * @return the ref_desc
	 */
	public String getRef_desc() {
		return Ref_desc;
	}

	/**
	 * @param ref_desc
	 *            the ref_desc to set
	 */
	public void setRef_desc(String ref_desc) {
		Ref_desc = ref_desc;
	}

	/**
	 * @return the refuse
	 */
	public double getRefuse() {
		return Refuse;
	}

	/**
	 * @param refuse
	 *            the refuse to set
	 */
	public void setRefuse(double refuse) {
		Refuse = refuse;
	}

	/**
	 * @return the sciName
	 */
	public String getSciName() {
		return SciName;
	}

	/**
	 * @param sciName
	 *            the sciName to set
	 */
	public void setSciName(String sciName) {
		SciName = sciName;
	}

	/**
	 * @return the n_Factor
	 */
	public double getN_Factor() {
		return N_Factor;
	}

	/**
	 * @param n_Factor
	 *            the n_Factor to set
	 */
	public void setN_Factor(double n_Factor) {
		N_Factor = n_Factor;
	}

	/**
	 * @return the pro_Factor
	 */
	public double getPro_Factor() {
		return Pro_Factor;
	}

	/**
	 * @param pro_Factor
	 *            the pro_Factor to set
	 */
	public void setPro_Factor(double pro_Factor) {
		Pro_Factor = pro_Factor;
	}

	/**
	 * @return the fat_Factor
	 */
	public double getFat_Factor() {
		return Fat_Factor;
	}

	/**
	 * @param fat_Factor
	 *            the fat_Factor to set
	 */
	public void setFat_Factor(double fat_Factor) {
		Fat_Factor = fat_Factor;
	}

	/**
	 * @return the cHO_Factor
	 */
	public double getCHO_Factor() {
		return CHO_Factor;
	}

	/**
	 * @param cHO_Factor
	 *            the cHO_Factor to set
	 */
	public void setCHO_Factor(double cHO_Factor) {
		CHO_Factor = cHO_Factor;
	}

}
