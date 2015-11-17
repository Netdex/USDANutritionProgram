package parser.parsables;

import parser.util.DoublyLinkedList;

/**
 * Represents a group of LanguaLs which a food item can own
 *
 * @author Gordon Guan
 */
public class LanguaLGroup {

	private final DoublyLinkedList<LanguaL> languals = new DoublyLinkedList<>();
	
	public LanguaLGroup(){

	}

	/**
	 * Addsa a langual to this group
	 *
	 * @param l the langual to add
	 */
	public void addLanguaL(LanguaL l){
		languals.add(l);
	}

	/**
	 * Gets all languals in this group
	 * @return all languals in this group
	 */
	public DoublyLinkedList<LanguaL> getLanguaLs(){
		return languals;
	}
	
	public String toString(){
		String ret = "";
		for(int i = 0; i < languals.size() - 1; i++){
			ret += languals.get(i) + ";";
		}
		ret += languals.get(languals.size() - 1);
		return ret;
	}
}
