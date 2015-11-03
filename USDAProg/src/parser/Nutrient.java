package parser;

/**
 * 
 * PLEASE DONT CHANGE THIS SHIT YET IT IS STILL A WORK IN PROGRESS
 *
 */
public class Nutrient {
	
	private int ndbNo;
	/**
	 * 3 digit unique ID for a nutrient
	 */
	private int nutrNo;
	
	/**
	 * Amount in 100 grams, edible portion
	 */
	private double nutrVal;
	
	/**
	 * Number of data points used to calculate the value
	 */
	private int numDataPts;
	
	/**
	 * Standard error of the mean
	 * Can be NaN
	 */
	private double stdError;
	
	/**
	 * Type of data
	 */
	private String srcCd;
	
	/**
	 * Some data derivation code thing
	 */
	private String derivCd;
	
	private int refNDBNo;
	
	private String addNutrMark;
	
	private int numStudies;
	
	private int min;
	private int max;
	private double df;
	private double lowEB;
	private double upEB;
	private String statCmt;
	private String addModDate;
	private String confidenceCode;
	public Nutrient(int ndbNo, int nutrNo, double nutrVal, int numDataPts, double stdError, String srcCd,
			String derivCd, int refNDBNo, String addNutrMark, int numStudies, int min, int max, double df, double lowEB,
			double upEB, String statCmt, String addModDate, String confidenceCode) {
		super();
		this.ndbNo = ndbNo;
		this.nutrNo = nutrNo;
		this.nutrVal = nutrVal;
		this.numDataPts = numDataPts;
		this.stdError = stdError;
		this.srcCd = srcCd;
		this.derivCd = derivCd;
		this.refNDBNo = refNDBNo;
		this.addNutrMark = addNutrMark;
		this.numStudies = numStudies;
		this.min = min;
		this.max = max;
		this.df = df;
		this.lowEB = lowEB;
		this.upEB = upEB;
		this.statCmt = statCmt;
		this.addModDate = addModDate;
		this.confidenceCode = confidenceCode;
	}
	/**
	 * @return the ndbNo
	 */
	public int getNdbNo() {
		return ndbNo;
	}
	/**
	 * @param ndbNo the ndbNo to set
	 */
	public void setNdbNo(int ndbNo) {
		this.ndbNo = ndbNo;
	}
	/**
	 * @return the nutrNo
	 */
	public int getNutrNo() {
		return nutrNo;
	}
	/**
	 * @param nutrNo the nutrNo to set
	 */
	public void setNutrNo(int nutrNo) {
		this.nutrNo = nutrNo;
	}
	/**
	 * @return the nutrVal
	 */
	public double getNutrVal() {
		return nutrVal;
	}
	/**
	 * @param nutrVal the nutrVal to set
	 */
	public void setNutrVal(double nutrVal) {
		this.nutrVal = nutrVal;
	}
	/**
	 * @return the numDataPts
	 */
	public int getNumDataPts() {
		return numDataPts;
	}
	/**
	 * @param numDataPts the numDataPts to set
	 */
	public void setNumDataPts(int numDataPts) {
		this.numDataPts = numDataPts;
	}
	/**
	 * @return the stdError
	 */
	public double getStdError() {
		return stdError;
	}
	/**
	 * @param stdError the stdError to set
	 */
	public void setStdError(double stdError) {
		this.stdError = stdError;
	}
	/**
	 * @return the srcCd
	 */
	public String getSrcCd() {
		return srcCd;
	}
	/**
	 * @param srcCd the srcCd to set
	 */
	public void setSrcCd(String srcCd) {
		this.srcCd = srcCd;
	}
	/**
	 * @return the derivCd
	 */
	public String getDerivCd() {
		return derivCd;
	}
	/**
	 * @param derivCd the derivCd to set
	 */
	public void setDerivCd(String derivCd) {
		this.derivCd = derivCd;
	}
	/**
	 * @return the refNDBNo
	 */
	public int getRefNDBNo() {
		return refNDBNo;
	}
	/**
	 * @param refNDBNo the refNDBNo to set
	 */
	public void setRefNDBNo(int refNDBNo) {
		this.refNDBNo = refNDBNo;
	}
	/**
	 * @return the addNutrMark
	 */
	public String getAddNutrMark() {
		return addNutrMark;
	}
	/**
	 * @param addNutrMark the addNutrMark to set
	 */
	public void setAddNutrMark(String addNutrMark) {
		this.addNutrMark = addNutrMark;
	}
	/**
	 * @return the numStudies
	 */
	public int getNumStudies() {
		return numStudies;
	}
	/**
	 * @param numStudies the numStudies to set
	 */
	public void setNumStudies(int numStudies) {
		this.numStudies = numStudies;
	}
	/**
	 * @return the min
	 */
	public int getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(int min) {
		this.min = min;
	}
	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}
	/**
	 * @return the df
	 */
	public double getDf() {
		return df;
	}
	/**
	 * @param df the df to set
	 */
	public void setDf(double df) {
		this.df = df;
	}
	/**
	 * @return the lowEB
	 */
	public double getLowEB() {
		return lowEB;
	}
	/**
	 * @param lowEB the lowEB to set
	 */
	public void setLowEB(double lowEB) {
		this.lowEB = lowEB;
	}
	/**
	 * @return the upEB
	 */
	public double getUpEB() {
		return upEB;
	}
	/**
	 * @param upEB the upEB to set
	 */
	public void setUpEB(double upEB) {
		this.upEB = upEB;
	}
	/**
	 * @return the statCmt
	 */
	public String getStatCmt() {
		return statCmt;
	}
	/**
	 * @param statCmt the statCmt to set
	 */
	public void setStatCmt(String statCmt) {
		this.statCmt = statCmt;
	}
	/**
	 * @return the addModDate
	 */
	public String getAddModDate() {
		return addModDate;
	}
	/**
	 * @param addModDate the addModDate to set
	 */
	public void setAddModDate(String addModDate) {
		this.addModDate = addModDate;
	}
	/**
	 * @return the confidenceCode
	 */
	public String getConfidenceCode() {
		return confidenceCode;
	}
	/**
	 * @param confidenceCode the confidenceCode to set
	 */
	public void setConfidenceCode(String confidenceCode) {
		this.confidenceCode = confidenceCode;
	}
	
}
