package parser.util;

public abstract class Selector<V> {

	public Selector(){
		
	}
	
	public abstract boolean select(V value);
}
