package parser.util;

public class Stack<E> {
	private SimpleLinkedList<E> linkedList;

	public Stack() {
		linkedList = new SimpleLinkedList<E>();
	}

	public void push(E item) {
		linkedList.add(item);
	}

	public E pop() {
		if (linkedList.size() > 0)
			return linkedList.remove(linkedList.size() - 1);
		return null;
	}

	public boolean isEmpty() {
		return linkedList.size() == 0;
	}

	public String toString() {
		return linkedList.toString();
	}

	static class SimpleLinkedList<E> {

		private SingleLLNode<E> base;

		public SimpleLinkedList() {
			base = new SingleLLNode<E>(null);
		}

		public boolean add(E item) {
			SingleLLNode<E> newNode = new SingleLLNode<E>(item);
			SingleLLNode<E> currentItem = base;
			while (currentItem.getNext() != null) {
				currentItem = currentItem.getNext();
			}
			currentItem.setNext(newNode);
			return true;
		}

		public E get(int index) {
			return getNode(index).getItem();
		}

		private SingleLLNode<E> getNode(int index) {
			SingleLLNode<E> currentItem = base;
			for (int i = 0; i < index + 1; i++) {
				currentItem = currentItem.getNext();
				if (currentItem == null)
					return null;
			}
			return currentItem;
		}

		public int indexOf(E item) {
			SingleLLNode<E> currentItem = base.getNext();
			int count = 0;
			while (currentItem != null) {
				if (item.equals(currentItem.getItem()))
					return count;
				currentItem = currentItem.getNext();
				count++;
			}
			return -1;
		}

		public E remove(int index) {
			SingleLLNode<E> currentItem = base;
			for (int i = 0; i < index; i++) {
				currentItem = currentItem.getNext();
			}
			SingleLLNode<E> nodeAtIndex = currentItem.getNext();
			currentItem.setNext(nodeAtIndex.getNext());
			return nodeAtIndex.getItem();
		}

		public boolean remove(E item) {
			SingleLLNode<E> previousItem = null;
			SingleLLNode<E> currentItem = base;
			while (currentItem.getNext() != null) {
				if (item.equals(currentItem.getItem())) {
					if (previousItem != null)
						previousItem.setNext(currentItem.getNext());
					return true;
				}
				previousItem = currentItem;
				currentItem = currentItem.getNext();
			}
			return false;
		}

		public void clear() {
			base.setNext(null);
		}

		public int size() {
			int count = 0;
			SingleLLNode<E> currentItem = base.getNext();
			while (currentItem != null) {
				count++;
				currentItem = currentItem.getNext();
			}
			return count;
		}

		@Override
		public String toString() {
			String ts = "[";
			SingleLLNode<E> currentItem = base.getNext();
			while (currentItem != null) {
				ts += currentItem.getItem().toString() + ", ";
				currentItem = currentItem.getNext();
			}
			ts += "]";
			return ts;
		}
	}

	static class SingleLLNode<E> {

		private E item;
		private SingleLLNode<E> next;

		public SingleLLNode(E item) {
			this.item = item;
		}

		public void setNext(SingleLLNode<E> next) {
			this.next = next;
		}

		public SingleLLNode<E> getNext() {
			return next;
		}

		public E getItem() {
			return item;
		}

	}
}
