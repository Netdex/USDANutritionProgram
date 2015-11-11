package parser.util;

public class Stack<E> {
	private DoublyLinkedList<E> linkedList;

	public Stack() {
		linkedList = new DoublyLinkedList<E>();
	}

	public void push(E item) {
		linkedList.add(item);
	}

	public E pop() {
		int size = linkedList.size();
		if (size > 0)
			return linkedList.remove(size - 1);
		return null;
	}

	public boolean isEmpty() {
		return linkedList.size() == 0;
	}

	public String toString() {
		return linkedList.toString();
	}
}
