package parser;

import java.util.StringTokenizer;

import parser.util.DoublyLinkedList;

public enum FattyAcid {

	// UNSATURATED FATTY ACIDS
	MYRISTOLEIC_ACID(14, 1, "Myristoleic Acid"), PALMITOLEIC_ACID(16, 1, "Palmitoleic Acid"), SAPIENIC_ACID(
			16, 1, "Sapienic Acid"), OLEIC_ACID(18, 1, "Oleic Acid"), ELAIDIC_ACID(18, 1,
			"Elaidic Acid"), VACCENIC_ACID(18, 1, "Vaccenic Acid"), LINOLEIC_ACID(18, 2,
			"Linoleic Acid"), LINOELAIDIC_ACID(18, 2, "Linoelaidic Acid"), ALPHA_LINOLENIC_ACID(18,
			3, "a-Linolenic Acid"), ARACHIDONIC_ACID(20, 4, "Arachidonic Acid"), EICOSAPENTAENOIC_ACID(
			20, 5, "Eicosapentaenoic Acid"), ERUCIC_ACID(22, 1, "Erucic Acid"), DOCOSAHEXAENOIC_ACID(
			22, 6, "Docosahexaenoic Acid"),

	// SATURATED FATTY ACIDS
	CAPRYLIC_ACID(8, 0, "Caprylic Acid"), CAPRIC_ACID(10, 0, "Capric Acid"), LAURIC_ACID(12, 0,
			"Lauric Acid"), MYRISTIC_ACID(14, 0, "Myristic Acid"), PALMITIC_ACID(16, 0,
			"Palmitic Acid"), STEARIC_ACID(18, 0, "Stearic Acid"), ARACHIDIC_ACID(20, 0,
			"Arachidic Acid"), BEHENIC_ACID(22, 0, "Behenic Acid"), LIGNOCERIC_ACID(24, 0,
			"Lignoceric Acid"), CEROTIC_ACID(26, 0, "Cerotic Acid");

	private int cn, dn;
	private String name;

	FattyAcid(int cn, int dn, String name) {
		this.cn = cn;
		this.dn = dn;
		this.name = name;
	}

	public static FattyAcid[] lookupByCDRatio(String cd) {
		StringTokenizer st = new StringTokenizer(cd, ":");
		return lookupByCDRatio(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
	}

	public static FattyAcid[] lookupByCDRatio(int cn, int dn) {

		DoublyLinkedList<FattyAcid> matches = new DoublyLinkedList<>();

		for (FattyAcid fa : FattyAcid.values()) {
			if (fa.getCarbonAtomCount() == cn && fa.getDoubleCarbonBondCount() == dn) {
				matches.add(fa);
			}
		}
		return matches.toArray(FattyAcid.ALPHA_LINOLENIC_ACID);
	}

	public String getName() {
		return name;
	}

	public int getCarbonAtomCount() {
		return cn;
	}

	public int getDoubleCarbonBondCount() {
		return dn;
	}
}
