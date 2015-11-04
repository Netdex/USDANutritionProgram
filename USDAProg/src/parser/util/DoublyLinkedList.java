package parser.util;

public class DoublyLinkedList<E> {

	private DoubleLLNode<E> front;
	private DoubleLLNode<E> back;

	public DoublyLinkedList() {
		front = new DoubleLLNode<E>(null);
		back = new DoubleLLNode<E>(null);
		front.setNext(back);
		back.setPrevious(front);
	}

	public boolean add(E item) {
		DoubleLLNode<E> newNode = new DoubleLLNode<E>(item);
		DoubleLLNode<E> currentItem = front;
		while (currentItem != null && currentItem.getNext().getNext() != null) {
			currentItem = currentItem.getNext();
		}
		currentItem.getNext().setPrevious(newNode);
		currentItem.setNext(newNode);

		newNode.setPrevious(currentItem);
		newNode.setNext(back);
		return true;
	}

	public E get(int index) {
		return getNode(index).getItem();
	}

	private DoubleLLNode<E> getNode(int index) {
		DoubleLLNode<E> currentItem = front.getNext();
		for (int i = 0; i < index; i++) {
			if (currentItem == null || currentItem.getNext().getNext() == null)
				return null;
			currentItem = currentItem.getNext();
		}
		return currentItem;
	}

	public E getFirst() {
		return front.getNext().getItem();
	}

	public E getLast() {
		return back.getPrevious().getItem();
	}

	public int indexOf(E item) {
		DoubleLLNode<E> currentItem = front.getNext();
		int count = 0;
		while (currentItem != null && currentItem.getNext() != null) {
			if (currentItem.getItem().equals(item))
				return count;
			count++;
			currentItem = currentItem.getNext();
		}
		return -1;
	}

	public E remove(int index) {
		DoubleLLNode<E> currentItem = front.getNext();
		for (int i = 0; i < index - 1; i++) {
			if (currentItem == back)
				return null;
			currentItem = currentItem.getNext();
		}
		DoubleLLNode<E> itemToReturn = currentItem.getNext();
		currentItem.setNext(currentItem.getNext().getNext());
		currentItem.getNext().setPrevious(currentItem);
		return itemToReturn.getItem();
	}

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

	public void clear() {
		front.setNext(back);
		back.setPrevious(front);
	}

	public int size() {
		int count = 0;
		DoubleLLNode<E> currentItem = front.getNext();
		while (currentItem != null && currentItem.getNext() != null) {
			count++;
			currentItem = currentItem.getNext();
		}
		return count;
	}

	public void swap(int a, int b) {
		DoubleLLNode<E> nodeA = this.getNode(a);
		DoubleLLNode<E> nodeB = this.getNode(b);

		if (nodeA == nodeB)
			return;
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

	@Override
	public String toString() {
		String ts = "[";
		DoubleLLNode<E> currentItem = front.getNext();
		while (currentItem != null && currentItem.getNext() != null) {
			ts += currentItem.getItem().toString() + ", ";
			currentItem = currentItem.getNext();
		}
		ts += "]";
		// ts += "\n[";
		// currentItem = back.getPrevious();
		// while (currentItem != null && currentItem.getPrevious() != null)
		// {
		// ts += currentItem.getItem().toString() + ", ";
		// currentItem = currentItem.getPrevious();
		// }
		// ts += "]";
		return ts;
	}

	static class DoubleLLNode<E> {
		private E item;
		private DoubleLLNode<E> next;
		private DoubleLLNode<E> previous;

		public DoubleLLNode(E item) {
			this.item = item;
		}

		public void setNext(DoubleLLNode<E> next) {
			this.next = next;
		}

		public void setPrevious(DoubleLLNode<E> previous) {
			this.previous = previous;
		}

		public DoubleLLNode<E> getNext() {
			return this.next;
		}

		public DoubleLLNode<E> getPrevious() {
			return this.previous;
		}

		public E getItem() {
			return item;
		}

	}
}