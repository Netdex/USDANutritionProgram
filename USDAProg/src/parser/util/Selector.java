package parser.util;

/**
 * Selects an item in a list based on the return value of select
 *
 * @param <V>
 * @author Gordon Guan
 */
public abstract class Selector<V> {

	public static final Selector TRUE_SELECTOR = new Selector() {
		@Override
		public boolean select(Object value){
			return true;
		}
	};
	
	public Selector(){

	}

	/**
	 * Whether or not to select this value from the list
	 *
	 * @param value the value to check against
	 * @return whether or not to select this value
	 */
	public abstract boolean select(V value);
}
