package parser.util;

import parser.util.BinaryTree.BinaryTreeNode;

public class BinaryTreeMap<K extends Comparable<K>, V> {
	
	public BalancedBinaryTree<HashTableNode<K, V>> tree = new BalancedBinaryTree<HashTableNode<K, V>>();
	
	public BinaryTreeMap(){
		
	}
	
	/**
	 * Probably the most useful method you will ever have.
	 * Selects all items from this BinaryTreeMap which match a Selector of type V.
	 * The selector is given the value, and then the selector can determine whether or not to add
	 * this item to the list.
	 * 
	 * @param sel The selector to base adding decisions on
	 * @return The list of selected items
	 */
	public DoublyLinkedList<V> selectAllItems(Selector<V> sel){
		DoublyLinkedList<V> selectedValues = new DoublyLinkedList<V>();
		Stack <BinaryTreeNode<HashTableNode<K, V>>> stack = new Stack<>();
		stack.push(tree.getRootNode());
		
		while(!stack.isEmpty()){
			BinaryTreeNode<HashTableNode<K, V>> currentNode = stack.pop();
			if(sel.select(currentNode.getItem().getValue())){
				selectedValues.add(currentNode.getItem().getValue());
			}
			if(currentNode.getLeft() != null)
				stack.push(currentNode.getLeft());
			if(currentNode.getRight() != null)
				stack.push(currentNode.getRight());
		}
		return selectedValues;
	}
	
	public DoublyLinkedList<V> getAllValues(){
		return selectAllItems(Selector.TRUE_SELECTOR);
	}
	
	public void put(K key, V value){
		HashTableNode<K, V> node = new HashTableNode<>(key, value);
		tree.add(node);
	}
	
	public V get(K key){
		return get(key, tree.getRootNode());
	}
	
	private V get(K key, BinaryTreeNode<HashTableNode<K,V>> node){
		if(node == null)
			return null;
		if(node.getItem().getKey().equals(key))
			return node.getItem().getValue();
		if(node.getItem().getKey().compareTo(key) > 0){
			return get(key, node.getLeft());
		}
		else{
			return get(key, node.getRight());
		}
	}
	
	public String toString(){
		return tree.toString();
	}
	
	public static class HashTableNode<K extends Comparable<K>, V> implements Comparable<HashTableNode<K, V>> {
		private K key;
		private V value;
		
		public HashTableNode(K key, V value){
			this.key = key;
			this.value = value;
		}
		
		public K getKey(){
			return key;
		}
		
		public V getValue(){
			return value;
		}
		
		@Override
		public int compareTo(HashTableNode<K, V> o) {
			return this.getKey().compareTo(o.getKey());
		}
		
		public String toString(){
		return key.toString() + " => " + value.toString();
		}
	}
}
