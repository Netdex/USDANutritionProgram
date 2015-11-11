package parser.util;

import parser.util.BinaryTree.BinaryTreeNode;

public class BinaryTreeMap<K extends Comparable<K>, V> {

	private BalancedBinaryTree<BinaryTreeMapNode<K, V>> tree = new BalancedBinaryTree<BinaryTreeMapNode<K, V>>();

	public BinaryTreeMap() {

	}

	public BalancedBinaryTree<BinaryTreeMapNode<K, V>> getInternalTree(){
		return tree;
	}
	/**
	 * Probably the most useful method you will ever have. Selects all items
	 * from this BinaryTreeMap which match a Selector of type V. The selector is
	 * given the value, and then the selector can determine whether or not to
	 * add this item to the list.
	 * 
	 * @param sel The selector to base adding decisions on
	 * @return The list of selected items
	 */
	public DoublyLinkedList<V> selectAllItems(Selector<V> sel) {
		if(tree.size() == 0)
			return new DoublyLinkedList<V>();
		DoublyLinkedList<V> selectedValues = new DoublyLinkedList<V>();
		Stack<BinaryTreeNode<BinaryTreeMapNode<K, V>>> stack = new Stack<>();
		stack.push(tree.getRootNode());

		while (!stack.isEmpty()) {
			BinaryTreeNode<BinaryTreeMapNode<K, V>> currentNode = stack.pop();
			if (sel.select(currentNode.getItem().getValue())) {
				selectedValues.add(currentNode.getItem().getValue());
			}
			if (currentNode.getLeft() != null)
				stack.push(currentNode.getLeft());
			if (currentNode.getRight() != null)
				stack.push(currentNode.getRight());
		}
		return selectedValues;
	}

	public DoublyLinkedList<V> getAllValues() {
		return selectAllItems(Selector.TRUE_SELECTOR);
	}

	public DoublyLinkedList<K> getAllKeys() {
		if(tree.size() == 0)
			return new DoublyLinkedList<K>();
		DoublyLinkedList<K> selectedValues = new DoublyLinkedList<K>();
		Stack<BinaryTreeNode<BinaryTreeMapNode<K, V>>> stack = new Stack<>();
		stack.push(tree.getRootNode());

		while (!stack.isEmpty()) {
			BinaryTreeNode<BinaryTreeMapNode<K, V>> currentNode = stack.pop();

			selectedValues.add(currentNode.getItem().getKey());

			if (currentNode.getLeft() != null)
				stack.push(currentNode.getLeft());
			if (currentNode.getRight() != null)
				stack.push(currentNode.getRight());
		}
		return selectedValues;
	}

	public void put(K key, V value) {
		BinaryTreeMapNode<K, V> node = new BinaryTreeMapNode<>(key, value);
		tree.add(node);
	}

	public V get(K key) {
		return get(key, tree.getRootNode());
	}

	private V get(K key, BinaryTreeNode<BinaryTreeMapNode<K, V>> node) {
		if (node == null)
			return null;
		if (node.getItem().getKey().equals(key))
			return node.getItem().getValue();
		if (node.getItem().getKey().compareTo(key) > 0) {
			return get(key, node.getLeft());
		} else {
			return get(key, node.getRight());
		}
	}

	public String toString() {
		return tree.toString();
	}

	public static class BinaryTreeMapNode<K extends Comparable<K>, V> implements
			Comparable<BinaryTreeMapNode<K, V>> {
		private K key;
		private V value;

		public BinaryTreeMapNode(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		@Override
		public int compareTo(BinaryTreeMapNode<K, V> o) {
			return this.getKey().compareTo(o.getKey());
		}

		public String toString() {
			return key.toString() + " => " + value.toString();
		}
	}
}
