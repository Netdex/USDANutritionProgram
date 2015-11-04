package parser;

/**
 * 
 * PLEASE DONT CHANGE THIS SHIT YET IT IS STILL A WORK IN PROGRESS
 *
 * Represents a nutrient, which is contained in a NutrientData. 
 * Modify in future to use builder structure, this is temporary to allow my code to work.
 */
public class Nutrient implements Parsable<Nutrient> {
	
	private static final int PARSE_DATA_LENGTH = 18;
	
	@Override
	public void parse(String[] data) throws InvalidParseDataException {
		if(data.length != PARSE_DATA_LENGTH)
			throw new InvalidParseDataException();
		ndbNo = Integer.parseInt(data[0]); 
		nutrNo = Integer.parseInt(data[1]);
		nutrVal = Double.parseDouble(data[2]); 
		numDataPts = Integer.parseInt(data[3]);
		stdError = data[4].equals("") ? 0 : Double.parseDouble(data[4]); 
		srcCd = data[5]; 
		derivCd = data[6];
		refNDBNo = data[7].equals("") ? 0 : Integer.parseInt(data[7]); 
		addNutrMark = data[8]; 
		numStudies = data[9].equals("") ? 0 : Integer.parseInt(data[9]);
		min = data[10].equals("") ? 0 : Double.parseDouble(data[10]); 
		max = data[11].equals("") ? 0 : Double.parseDouble(data[11]);
		df = data[12].equals("") ? 0 : Double.parseDouble(data[12]); 
		lowEB = data[13].equals("") ? 0 : Double.parseDouble(data[13]);
		upEB = data[14].equals("") ? 0 : Double.parseDouble(data[14]); 
		statCmt = data[15]; 
		addModDate = data[16]; 
		confidenceCode = data[17];
		
	}
	
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
	
	private double min;
	private double max;
	private double df;
	private double lowEB;
	private double upEB;
	private String statCmt;
	private String addModDate;
	private String confidenceCode;
	
	public Nutrient(){
		
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
	public double getMin() {
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
	public double getMax() {
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
