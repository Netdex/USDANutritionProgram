package parser.util;

public class BalancedBinaryTree<E extends Comparable<E>> extends BinaryTree<E> {

    public BalancedBinaryTree() {

    }

    @Override
    public void add(E item) {
        if (this.getRootNode() == null) {
            super.add(item);
            return;
        }
        BinaryTreeNode<E> newNode = new BinaryTreeNode<>(item);
        Stack<BinaryTreeNode<E>> stack = new Stack<>();
        stack.push(this.getRootNode());
        while (!stack.isEmpty()) {
            BinaryTreeNode<E> node = stack.pop();
            int c = item.compareTo(node.getItem());
            if (c < 0) {
                if (node.getLeft() == null) {
                    newNode.setParent(node);
                    node.setLeft(newNode);
                    break;
                } else {
                    stack.push(node.getLeft());
                }
            } else if (c > 0) {
                if (node.getRight() == null) {
                    newNode.setParent(node);
                    node.setRight(newNode);
                    break;
                } else {
                    stack.push(node.getRight());
                }
            } else {
                if (node.getParent() != null) {
                    if (node == node.getParent().getRight()) {
                        node.getParent().setRight(newNode);
                        newNode.setParent(node.getParent());
                    } else {
                        node.getParent().setLeft(newNode);
                        newNode.setParent(node.getParent());
                    }
                }
                else{
                    this.setRootNode(newNode);
                }
                newNode.setLeft(node.getLeft());
                newNode.setRight(node.getRight());
                if (node.getLeft() != null)
                    node.getLeft().setParent(newNode);
                if (node.getRight() != null)
                    node.getRight().setParent(newNode);
            }
        }
        balanceUp(newNode);
    }

    public boolean remove(E item) {
        BinaryTreeNode<E> node = findNode(item);
        if (node == null)
            return false;
        remove(node);
        balanceUp(node);
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

    private void balanceUp(BinaryTreeNode<E> node) {
        BinaryTreeNode<E> currentNode = node;
        while (currentNode != null) {
            int balance = this.getHeight(currentNode.getLeft())
                    - this.getHeight(currentNode.getRight());
            if (Math.abs(balance) >= 2) {
                if (balance < 0) {
                    if (currentNode.getRight() != null && currentNode.getRight().getRight() != null) {
                        this.leftRotation(currentNode);
                    } else if (currentNode.getRight() != null
                            && currentNode.getRight().getLeft() != null) {
                        this.doubleLeftRotation(currentNode);
                    }
                } else {
                    if (currentNode.getLeft() != null && currentNode.getLeft().getRight() != null) {
                        this.doubleRightRotation(currentNode);
                    } else if (currentNode.getLeft() != null
                            && currentNode.getLeft().getLeft() != null) {
                        this.rightRotation(currentNode);
                    }
                }
            }
            currentNode = currentNode.getParent();
        }
    }

    public void leftRotation(BinaryTreeNode<E> r) {
        BinaryTreeNode<E> p = r.getRight();
        if (p == null)
            return;
        r.setRight(p.getLeft());
        if (p.getLeft() != null) {
            p.getLeft().setParent(r);
        }
        p.setLeft(r);
        if (r.getParent() != null) {
            if (r.getParent().getLeft() == r)
                r.getParent().setLeft(p);
            else
                r.getParent().setRight(p);
        } else {
            this.setRootNode(p);
        }
        p.setParent(r.getParent());
        r.setParent(p);
    }

    public void rightRotation(BinaryTreeNode<E> r) {
        BinaryTreeNode<E> p = r.getLeft();
        if (p == null)
            return;
        r.setLeft(p.getRight());
        if (p.getRight() != null) {
            p.getRight().setParent(r);
        }
        p.setRight(r);
        if (r.getParent() != null) {
            if (r.getParent().getLeft() == r)
                r.getParent().setLeft(p);
            else
                r.getParent().setRight(p);
        } else {
            this.setRootNode(p);
        }
        p.setParent(r.getParent());
        r.setParent(p);
    }

    public void doubleLeftRotation(BinaryTreeNode<E> r) {
        rightRotation(r.getRight());
        leftRotation(r);
    }

    public void doubleRightRotation(BinaryTreeNode<E> r) {
        leftRotation(r.getLeft());
        rightRotation(r);
    }
}
