package parser.util;

/**
 * Represents the stack data structure
 *
 * @param <E>
 * @author Gordon Guan
 */
public class Stack<E> {
	private final DoublyLinkedList<E> linkedList;

	public Stack() {
		linkedList = new DoublyLinkedList<>();
	}

	/**
	 * Push an item onto the stack
	 *
	 * @param item the item to push
	 */
	public void push(E item) {
		linkedList.add(item);
	}

	/**
	 * Pops an item off the stack
	 * @return the item popped off
	 */
	public E pop() {
		int size = linkedList.size();
		if (size > 0)
			return linkedList.remove(size - 1);
		return null;
	}

	/**
	 * Checks if the stack is empty
	 * @return if the stack is empty
	 */
	public boolean isEmpty() {
		return linkedList.size() == 0;
	}

	public String toString() {
		return linkedList.toString();
	}
}
