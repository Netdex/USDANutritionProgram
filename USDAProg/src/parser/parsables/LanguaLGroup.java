package parser.parsables;

import util.DoublyLinkedList;

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
}
