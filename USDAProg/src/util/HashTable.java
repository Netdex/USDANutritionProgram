package util;

public class HashTable<K extends Comparable<K>, V> {
	
	public BinaryTree<HashTableNode<K, V>> tree = new BinaryTree<HashTableNode<K, V>>();
	
	public HashTable(){
		
	}
	
	public void put(K key, V value){
		HashTableNode<K, V> node = new HashTableNode<>(key, value);
		tree.add(node);
	}
	
	public V get(K key){
		return get(key, tree.getRootNode());
	}
	
	private V get(K key, BinaryTree.BinaryTreeNode<HashTableNode<K,V>> node){
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
		return key.toString() + "=>" + value.toString();
		}
	}
}
