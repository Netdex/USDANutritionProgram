package parser.util;

import java.lang.reflect.Array;

/**
 * A doubly linked list structure
 *
 * @param <E>
 * @author Gordon Guan
 */
public class DoublyLinkedList<E> {

	private final DoubleLLNode<E> front;
	private final DoubleLLNode<E> back;

	public DoublyLinkedList() {
		front = new DoubleLLNode<>(null);
		back = new DoubleLLNode<>(null);
		front.setNext(back);
		back.setPrevious(front);
	}

	public DoublyLinkedList(E[] array) {
		front = new DoubleLLNode<>(null);
		back = new DoubleLLNode<>(null);
		front.setNext(back);
		back.setPrevious(front);
		for (int i = 0; i < array.length - 1; i++) {
			add(array[i]);
		}

	}

	/**
	 * Add an item to the list
	 *
	 * @param item the item to add
	 */
	public void add(E item) {
		DoubleLLNode<E> newNode = new DoubleLLNode<>(item);

		DoubleLLNode<E> currentItem = back.getPrevious();
		currentItem.getNext().setPrevious(newNode);
		currentItem.setNext(newNode);

		newNode.setPrevious(currentItem);
		newNode.setNext(back);
	}

	/**
	 * Get an item by its index
	 * @param index the index of the item
	 * @return the item
	 */
	public E get(int index) {
		return getNode(index).getItem();
	}

	private DoubleLLNode<E> getNode(int index) {
		if (index < 0)
			throw new ArrayIndexOutOfBoundsException();
		DoubleLLNode<E> currentItem = front.getNext();
		for (int i = 0; i < index; i++) {
			if (currentItem.getNext().getNext() == null)
				throw new ArrayIndexOutOfBoundsException();
			currentItem = currentItem.getNext();
		}
		return currentItem;
	}

	/**
	 * Get the first item
	 * @return the first item
	 */
	public E getFirst() {
		return front.getNext().getItem();
	}

	/**
	 * Get the last item
	 * @return the last item
	 */
	public E getLast() {
		return back.getPrevious().getItem();
	}

	/**
	 * Get the index of an item
	 * @param item the item to find its index
	 * @return the index of an item
	 */
	public int indexOf(E item) {
		DoubleLLNode<E> currentItem = front.getNext();
		int count = 0;
		while (currentItem.getNext() != null) {
			if (currentItem.getItem().equals(item))
				return count;
			count++;
			currentItem = currentItem.getNext();
		}
		return -1;
	}

	/**
	 * Remove an item from the list by index
	 * @param index the index of the item
	 * @return the item removed
	 */
	public E remove(int index) {
		DoubleLLNode<E> currentItem = front.getNext();
		for (int i = 0; i < index; i++) {
			if (currentItem.getNext().getNext() == null)
				return null;
			currentItem = currentItem.getNext();
		}
		currentItem.getPrevious().setNext(currentItem.getNext());
		currentItem.getNext().setPrevious(currentItem.getPrevious());
		return currentItem.getItem();
	}

	/**
	 * Removes an item from the list by item reference
	 * @param item the item to remove
	 * @return whether or not the item was removed successfully
	 */
	public boolean remove(E item) {
		DoubleLLNode<E> currentItem = front.getNext();
		while (currentItem != null) {
			if (currentItem.getItem().equals(item)) {
				currentItem.getPrevious().setNext(currentItem.getNext());
				currentItem.getNext().setPrevious(currentItem.getPrevious());
				return true;
			}
			currentItem = currentItem.getNext();
		}
		return false;
	}

	/**
	 * Clear the list
	 */
	public void clear() {
		front.setNext(back);
		back.setPrevious(front);
	}

	/**
	 * Gets the size of the list
	 * @return the size of the list
	 */
	public int size() {
		int count = 0;
		DoubleLLNode<E> currentItem = front.getNext();
		while (currentItem.getNext() != null) {
			count++;
			currentItem = currentItem.getNext();
		}
		return count;
	}

	/**
	 * Swaps two elements in the list
	 * @param a index to swap
	 * @param b other index to swap
	 */
	public void swap(int a, int b) {
		DoubleLLNode<E> nodeA = this.getNode(a);
		DoubleLLNode<E> nodeB = this.getNode(b);

		if (nodeA == nodeB) {
		}
		else if (Math.abs(a - b) == 1) {
			nodeA.getPrevious().setNext(nodeB);
			nodeA.setNext(nodeB.getNext());
			nodeB.getNext().setPrevious(nodeA);
			nodeB.setPrevious(nodeA.getPrevious());
			nodeA.setPrevious(nodeB);
			nodeB.setNext(nodeA);
		} else {
			nodeA.getPrevious().setNext(nodeB);
			nodeA.getNext().setPrevious(nodeB);
			DoubleLLNode<E> aNext = nodeA.getNext();
			DoubleLLNode<E> aPrev = nodeA.getPrevious();
			nodeA.setNext(nodeB.getNext());
			nodeA.setPrevious(nodeB.getPrevious());

			nodeB.getPrevious().setNext(nodeA);
			nodeB.getNext().setPrevious(nodeA);
			nodeB.setNext(aNext);
			nodeB.setPrevious(aPrev);
		}
	}

	/**
	 * @return a primitive array of this DoublyLinkedList
	 */
	public E[] toArray(E sample) {
		int size = this.size();
		if (size == 0)
			return null;
		@SuppressWarnings("unchecked")
		E[] arr = (E[]) Array.newInstance(sample.getClass(), size);
		DoubleLLNode<E> currentNode = front.getNext();
		int idx = 0;
		while (currentNode.getNext() != null) {
			arr[idx++] = currentNode.getItem();
			currentNode = currentNode.getNext();
		}
		return arr;
	}

	/**
	 * Get all items in the list that match a selector
	 * @param sel the selector
	 * @return all items in the list that match
	 */
	public DoublyLinkedList<E> selectAllItems(Selector<E> sel) {
		DoublyLinkedList<E> selected = new DoublyLinkedList<>();
		DoubleLLNode<E> currentNode = front.getNext();
		while (currentNode.getNext() != null) {
			if (sel.select(currentNode.getItem()))
				selected.add(currentNode.getItem());
			currentNode = currentNode.getNext();
		}
		return selected;
	}

	@Override
	public String toString() {
		String ts = "[";
		DoubleLLNode<E> currentItem = front.getNext();
		while (currentItem != null && currentItem.getNext() != null) {
			ts += currentItem.getItem().toString() + ", ";
			currentItem = currentItem.getNext();
		}
		ts += "]";
		return ts;
	}

	/**
	 * Represents a node in the list
	 * @author Gordon Guan
	 * @param <E>
	 */
	static class DoubleLLNode<E> {
		private final E item;
		private DoubleLLNode<E> next;
		private DoubleLLNode<E> previous;

		public DoubleLLNode(E item) {
			this.item = item;
		}

		public DoubleLLNode<E> getNext() {
			return this.next;
		}

		public void setNext(DoubleLLNode<E> next) {
			this.next = next;
		}

		public DoubleLLNode<E> getPrevious() {
			return this.previous;
		}

		public void setPrevious(DoubleLLNode<E> previous) {
			this.previous = previous;
		}

		public E getItem() {
			return item;
		}

	}
}
