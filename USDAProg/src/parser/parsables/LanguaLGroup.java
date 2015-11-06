package parser.parsables;

import parser.util.DoublyLinkedList;

public class LanguaLGroup {

	private DoublyLinkedList<LanguaL> languals = new DoublyLinkedList<>();
	
	public LanguaLGroup(){
		
	}
	
	public void addLanguaL(LanguaL l){
		languals.add(l);
	}
	
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
