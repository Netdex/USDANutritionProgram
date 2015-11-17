package parser.util;

import parser.util.BinaryTree.BinaryTreeNode;

/**
 * Represents a map structure, using a balanced binary tree
 *
 * @param <K> The key type of the map
 * @param <V> The value type of the map
 * @author Gordon Guan
 */
public class BinaryTreeMap<K extends Comparable<K>, V> {

    private final BalancedBinaryTree<BinaryTreeMapNode<K, V>> tree = new BalancedBinaryTree<>();

    public BinaryTreeMap() {

    }

    public BalancedBinaryTree<BinaryTreeMapNode<K, V>> getInternalTree() {
        return tree;
    }

    /**
     * Selects all items from this BinaryTreeMap which match a Selector of type V. The selector is
     * given the value, and then the selector can determine whether or not to
     * add this item to the list.
     *
     * @param sel The selector to base adding decisions on
     * @return The list of selected items
     */
    private DoublyLinkedList<V> selectAllItems(Selector<V> sel) {
        if (tree.size() == 0)
            return new DoublyLinkedList<>();
        DoublyLinkedList<V> selectedValues = new DoublyLinkedList<>();
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

    /**
     * Gets all values
     *
     * @return all values
     */
    public DoublyLinkedList<V> getAllValues() {
        return selectAllItems(Selector.TRUE_SELECTOR);
    }

    /**
     * Gets all keys
     *
     * @return all keys
     */
    public DoublyLinkedList<K> getAllKeys() {
        if (tree.size() == 0)
            return new DoublyLinkedList<>();
        DoublyLinkedList<K> selectedValues = new DoublyLinkedList<>();
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

    /**
     * Add an item to the tree
     *
     * @param key   The key of the item
     * @param value the item
     */
    public void put(K key, V value) {
        BinaryTreeMapNode<K, V> node = new BinaryTreeMapNode<>(key, value);
        tree.add(node);
    }

    /**
     * Get an item by its key
     *
     * @param key The key of the item
     * @return the item
     */
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

    /**
     * Represents a node in the binary tree map
     * @param <K> The key type
     * @param <V> The value type
     */
    public static class BinaryTreeMapNode<K extends Comparable<K>, V> implements
            Comparable<BinaryTreeMapNode<K, V>> {
        private final K key;
        private final V value;

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
