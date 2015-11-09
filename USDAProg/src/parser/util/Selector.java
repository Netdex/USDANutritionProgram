package parser.util;

public abstract class Selector<V> {
	
	public static Selector TRUE_SELECTOR = new Selector(){
		@Override
		public boolean select(Object value){
			return true;
		}
	};
	
	public Selector(){
		
	}
	
	public abstract boolean select(V value);
}
