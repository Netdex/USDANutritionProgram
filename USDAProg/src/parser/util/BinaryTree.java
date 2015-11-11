package parser.util;

public class BinaryTree<E extends Comparable<E>> {

	private BinaryTreeNode<E> root;

	public BinaryTree() {

	}

	public boolean contains(E item) {
		return findNode(item) != null;
	}

	protected BinaryTreeNode<E> findNode(E item) {
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

	public void add(E item) {
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

	public boolean remove(E item) {
		BinaryTreeNode<E> node = findNode(item);
		if (node == null)
			return false;
		remove(node);
		return true;
	}

	private void remove(BinaryTreeNode<E> node) {
		int branches = node.getBranches();
		switch (branches) {
		case 0:
			if (node.getParent().getLeft() == node) {
				node.getParent().setLeft(null);
			} else {
				node.getParent().setRight(null);
			}
			break;
		case 1:
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
			BinaryTreeNode<E> minNode = this.getMinNode(node.getRight());
			node.setItem(minNode.getItem());
			remove(minNode);
			break;
		}
	}

	public BinaryTreeNode<E> getMinNode(BinaryTreeNode<E> start) {
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

	public boolean isEmpty() {
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

	protected BinaryTreeNode<E> getRootNode() {
		return root;
	}

	protected void setRootNode(BinaryTreeNode<E> node) {
		this.root = node;
	}

	protected int getHeight(BinaryTreeNode<E> node) {
		if (node == null)
			return 0;

		return Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1;
	}

	protected int getLeftWidth(BinaryTreeNode<E> root) {
		return Math.abs(getLeftWidth(root, 0));
	}

	private int getLeftWidth(BinaryTreeNode<E> root, int dist) {
		if (root == null)
			return dist;
		return Math.min(getLeftWidth(root.getLeft(), dist - 1),
				getLeftWidth(root.getRight(), dist + 1));
	}

	protected int getRightWidth(BinaryTreeNode<E> root) {
		return Math.abs(getRightWidth(root, 0));
	}

	private int getRightWidth(BinaryTreeNode<E> node, int dist) {
		if (node == null)
			return dist;
		return Math.max(getRightWidth(node.getLeft(), dist - 1),
				getRightWidth(node.getRight(), dist + 1));
	}

	static class BinaryTreeNode<E extends Comparable<E>> {

		private E item;

		private BinaryTreeNode<E> parent;
		private BinaryTreeNode<E> left;
		private BinaryTreeNode<E> right;

		public BinaryTreeNode(E item) {
			this.item = item;
		}

		public BinaryTreeNode<E> getLeft() {
			return left;
		}

		public void setLeft(BinaryTreeNode<E> left) {
			this.left = left;
		}

		public BinaryTreeNode<E> getRight() {
			return right;
		}

		public void setRight(BinaryTreeNode<E> right) {
			this.right = right;
		}

		public E getItem() {
			return item;
		}

		public void setItem(E item) {
			this.item = item;
		}

		public BinaryTreeNode<E> getParent() {
			return parent;
		}

		public void setParent(BinaryTreeNode<E> parent) {
			this.parent = parent;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}

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
