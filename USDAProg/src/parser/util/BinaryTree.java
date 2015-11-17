package parser.util;

/**
 * Represents the binary tree data structure
 *
 * @param <E>
 * @author Gordon Guan
 */
public class BinaryTree<E extends Comparable<E>> {

    private BinaryTreeNode<E> root;

    public BinaryTree() {

    }

    /**
     * Checks if this binary tree contains an item
     *
     * @param item The item to check if contains
     * @return whether or not this tree contains that item
     */
    public boolean contains(E item) {
        return findNode(item) != null;
    }

    /**
     * Looks for a node in the binary tree containing said item
     *
     * @param item The item to look for
     * @return The node of this item, or null if it doesn't exist
     */
    BinaryTreeNode<E> findNode(E item) {
        if (isEmpty())
            return null;
        Stack<BinaryTreeNode<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            BinaryTreeNode<E> node = stack.pop();
            if (node.getItem().equals(item))
                return node;
            if (node.getLeft() != null)
                stack.push(node.getLeft());
            if (node.getRight() != null)
                stack.push(node.getRight());
        }
        return null;
    }

    /**
     * Gets the size of the tree
     *
     * @return the size of the tree
     */
    public int size() {
        if (isEmpty())
            return 0;
        int size = 0;
        Stack<BinaryTreeNode<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            BinaryTreeNode<E> node = stack.pop();
            size++;
            if (node.getLeft() != null)
                stack.push(node.getLeft());
            if (node.getRight() != null)
                stack.push(node.getRight());
        }
        return size;
    }

    /**
     * Add an element to the tree
     *
     * @param item the item to add
     */
    void add(E item) {
        BinaryTreeNode<E> newNode = new BinaryTreeNode<>(item);
        if (this.getRootNode() == null) {
            root = newNode;
            return;
        }
        Stack<BinaryTreeNode<E>> stack = new Stack<>();
        stack.push(this.getRootNode());
        while (!stack.isEmpty()) {
            BinaryTreeNode<E> node = stack.pop();
            if (item.compareTo(node.getItem()) <= 0) {
                if (node.getLeft() == null) {
                    newNode.setParent(node);
                    node.setLeft(newNode);
                    break;
                } else {
                    stack.push(node.getLeft());
                }
            } else {
                if (node.getRight() == null) {
                    newNode.setParent(node);
                    node.setRight(newNode);
                    break;
                } else {
                    stack.push(node.getRight());
                }
            }
        }
    }

    /**
     * Removes an element from the tree
     *
     * @param item the item to remove
     * @return whether or not it was removed successfully
     */
    public boolean remove(E item) {
        BinaryTreeNode<E> node = findNode(item);
        if (node == null)
            return false;
        remove(node);
        return true;
    }

    /**
     * Removes a node from the tree
     *
     * @param node the node to remove
     */
    void remove(BinaryTreeNode<E> node) {
        int branches = node.getBranches();
        switch (branches) {
            case 0:
                // Case 1, no children, so we just remove the node
                if (node.getParent().getLeft() == node) {
                    node.getParent().setLeft(null);
                } else {
                    node.getParent().setRight(null);
                }
                break;
            case 1:
                // Case 2, 1 child, set this node as the child
                if (node.getParent().getLeft() == node) {
                    if (node.getLeft() != null) {
                        node.getParent().setLeft(node.getLeft());
                        node.getLeft().setParent(node.getParent());
                    } else {
                        node.getParent().setLeft(node.getRight());
                        node.getRight().setParent(node.getParent());
                    }
                } else {
                    if (node.getLeft() != null) {
                        node.getParent().setRight(node.getLeft());
                        node.getLeft().setParent(node.getParent());
                    } else {
                        node.getParent().setRight(node.getRight());
                        node.getRight().setParent(node.getParent());
                    }
                }
                break;
            case 2:
                // Case 3, 2 children, find the minimum value on the right child of this node, then remove that one recursively
                BinaryTreeNode<E> minNode = this.getMinNode(node.getRight());
                node.setItem(minNode.getItem());
                remove(minNode);
                break;
        }
    }

    /**
     * Finds the minimum node under a node
     *
     * @param start The node to start from
     * @return the node with the minimum value under this node
     */
    BinaryTreeNode<E> getMinNode(BinaryTreeNode<E> start) {
        BinaryTreeNode<E> minNode = start;
        Stack<BinaryTreeNode<E>> stack = new Stack<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            BinaryTreeNode<E> node = stack.pop();
            if (node.getItem().compareTo(minNode.getItem()) < 0)
                minNode = node;
            if (node.getLeft() != null)
                stack.push(node.getLeft());
            if (node.getRight() != null)
                stack.push(node.getRight());
        }
        return minNode;
    }

    /**
     * Checks if this tree is empty
     *
     * @return if this tree is empty
     */
    private boolean isEmpty() {
        return root == null;
    }

    @Override
    public String toString() {
        return toString(root, 0);
    }

    private String toString(BinaryTreeNode<E> node, int depth) {
        String tabs = "";
        for (int i = 0; i < depth; i++)
            tabs += "\t";
        if (node.getLeft() == null) {
            if (node.getRight() == null) {
                return tabs + node.toString() + "\n";
            } else {
                return tabs + node.toString() + "\n" + toString(node.getRight(), depth + 1);
            }
        } else {
            if (node.getRight() == null) {
                return tabs + node.toString() + "\n" + toString(node.getLeft(), depth + 1);
            } else {
                return tabs + node.toString() + "\n" + toString(node.getLeft(), depth + 1)
                        + toString(node.getRight(), depth + 1);
            }
        }
    }

    /**
     * Gets the root node
     *
     * @return the root node
     */
    BinaryTreeNode<E> getRootNode() {
        return root;
    }

    /**
     * Sets the root node
     *
     * @param node the new root node
     */
    void setRootNode(BinaryTreeNode<E> node) {
        this.root = node;
    }

    /**
     * Gets the height of the tree from a node (deepest distance)
     *
     * @param node The node to check from
     * @return the height of the tree from a node
     */
    int getHeight(BinaryTreeNode<E> node) {
        if (node == null)
            return 0;
        return Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1;
    }

    /**
     * Represents a node in a binary tree
     *
     * @param <E>
     * @author Gordon Guan
     */
    static class BinaryTreeNode<E extends Comparable<E>> {

        private E item;

        private BinaryTreeNode<E> parent;
        private BinaryTreeNode<E> left;
        private BinaryTreeNode<E> right;

        public BinaryTreeNode(E item) {
            this.item = item;
        }

        /**
         * Gets the left node
         *
         * @return the left node
         */
        public BinaryTreeNode<E> getLeft() {
            return left;
        }

        /**
         * Sets the left node
         *
         * @param left the left node
         */
        public void setLeft(BinaryTreeNode<E> left) {
            this.left = left;
        }

        /**
         * Gets the right node
         *
         * @return the right node
         */
        public BinaryTreeNode<E> getRight() {
            return right;
        }

        /**
         * Sets the right node
         *
         * @param right the right node
         */
        public void setRight(BinaryTreeNode<E> right) {
            this.right = right;
        }

        /**
         * Gets this node's item
         *
         * @return this node's item
         */
        public E getItem() {
            return item;
        }

        /**
         * Sets this node's item
         *
         * @param item this node's new item
         */
        public void setItem(E item) {
            this.item = item;
        }

        /**
         * Gets this node's parent
         *
         * @return this node's parent
         */
        public BinaryTreeNode<E> getParent() {
            return parent;
        }

        /**
         * Sets this node's parent
         *
         * @param parent this node's new parent
         */
        public void setParent(BinaryTreeNode<E> parent) {
            this.parent = parent;
        }

        /**
         * Checks if this node has no children
         *
         * @return if this node has no children
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * Gets the number of children
         *
         * @return the number of children
         */
        public int getBranches() {
            int branches = 0;
            if (left != null)
                branches++;
            if (right != null)
                branches++;
            return branches;
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }

}
