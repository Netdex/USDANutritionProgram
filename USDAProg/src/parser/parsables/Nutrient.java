package parser.parsables;

import parser.InvalidParseDataException;

/**
 * 
 * PLEASE DONT CHANGE THIS SHIT YET IT IS STILL A WORK IN PROGRESS
 *
 * Represents a nutrient, which is contained in a NutrientData. Modify in future
 * to use builder structure, this is temporary to allow my code to work.
 */
public class Nutrient implements Parsable<Nutrient> {

	private static final int PARSE_DATA_LENGTH = 18;

	@Override
	public void parse(String[] data) throws InvalidParseDataException {
		if (data.length != PARSE_DATA_LENGTH)
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
	 * Standard error of the mean Can be NaN
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
	private NutrientDescription desc;

	public Nutrient() {

	}

	/**
	 * @return the ndbNo
	 */
	public int getNdbNo() {
		return ndbNo;
	}

	/**
	 * @return the nutrNo
	 */
	public int getNutrNo() {
		return nutrNo;
	}

	/**
	 * @return the nutrVal
	 */
	public double getNutrVal() {
		return nutrVal;
	}

	/**
	 * @return the numDataPts
	 */
	public int getNumDataPts() {
		return numDataPts;
	}

	/**
	 * @return the stdError
	 */
	public double getStdError() {
		return stdError;
	}

	/**
	 * @return the srcCd
	 */
	public String getSrcCd() {
		return srcCd;
	}

	/**
	 * @return the derivCd
	 */
	public String getDerivCd() {
		return derivCd;
	}

	/**
	 * @return the refNDBNo
	 */
	public int getRefNDBNo() {
		return refNDBNo;
	}

	/**
	 * @return the addNutrMark
	 */
	public String getAddNutrMark() {
		return addNutrMark;
	}

	/**
	 * @return the numStudies
	 */
	public int getNumStudies() {
		return numStudies;
	}

	/**
	 * @return the min
	 */
	public double getMin() {
		return min;
	}

	/**
	 * @return the max
	 */
	public double getMax() {
		return max;
	}

	/**
	 * @return the df
	 */
	public double getDf() {
		return df;
	}

	/**
	 * @return the lowEB
	 */
	public double getLowEB() {
		return lowEB;
	}

	/**
	 * @return the upEB
	 */
	public double getUpEB() {
		return upEB;
	}

	/**
	 * @return the statCmt
	 */
	public String getStatCmt() {
		return statCmt;
	}

	/**
	 * @return the addModDate
	 */
	public String getAddModDate() {
		return addModDate;
	}

	/**
	 * @return the confidenceCode
	 */
	public String getConfidenceCode() {
		return confidenceCode;
	}

	public void setNutrientDescription(NutrientDescription nd) {
		this.desc = nd;
	}

	public NutrientDescription getNutrientDescription() {
		return this.desc;
	}

	public String toString() {
		return this.desc.toString();
	}

}
