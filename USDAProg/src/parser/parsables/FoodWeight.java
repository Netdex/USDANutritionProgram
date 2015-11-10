package parser.parsables;

import parser.InvalidParseDataException;

/**
 * A structure storing weight information of this FoodItem
 * @author Gordon Guan
 *
 */
public class FoodWeight implements Parsable<FoodWeight> {

	private static final int PARSE_DATA_LENGTH = 7;

	@Override
	public FoodWeight parse(String[] data) throws InvalidParseDataException {
		if (data.length != PARSE_DATA_LENGTH)
			throw new InvalidParseDataException();
		ndbNo = Integer.parseInt(data[0]);
		seq = Integer.parseInt(data[1]);
		amount = Double.parseDouble(data[2]);
		desc = data[3];
		gramWeight = Double.parseDouble(data[4]);
		numDataPts = data[5].equals("") ? 0 : Integer.parseInt(data[5]);
		stdDev = data[6].equals("") ? 0 : Double.parseDouble(data[6]);
		return this;
	}

	private int ndbNo;
	private int seq;
	private double amount;
	private String desc;
	private double gramWeight;
	private int numDataPts;
	private double stdDev;

	public FoodWeight() {

	}

	public int getNDBNo() {
		return ndbNo;
	}

	/**
	 * @return the parseDataLength
	 */
	public static int getParseDataLength() {
		return PARSE_DATA_LENGTH;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @return the gramWeight
	 */
	public double getGramWeight() {
		return gramWeight;
	}

	/**
	 * @return the numDataPts
	 */
	public int getNumDataPts() {
		return numDataPts;
	}

	/**
	 * @return the stdDev
	 */
	public double getStdDev() {
		return stdDev;
	}
	
	public String toString(){
		return this.getAmount() + " " + this.getDesc();
	}
}
