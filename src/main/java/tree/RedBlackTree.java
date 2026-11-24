package tree;

public class RedBlackTree {
    private static class Node {
        int key;
        boolean red;
        Node left;
        Node right;
        Node parent;

        Node(int key) {
            this.key = key;
            this.red = true;
        }
    }

    private Node root;
    private int size;

    public void insert(int key) {
        Node parent = null;
        Node current = root;

        while (current != null) {
            if (key == current.key) {
                return;
            }
            parent = current;
            if (key < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        Node node = new Node(key);
        node.parent = parent;

        if (parent == null) {
            root = node;
        } else if (key < parent.key) {
            parent.left = node;
        } else {
            parent.right = node;
        }

        size++;
        fixAfterInsert(node);
    }

    private void fixAfterInsert(Node node) {
        while (node.parent != null && node.parent.red) {
            Node parent = node.parent;
            Node grandparent = parent.parent;

            if (parent == grandparent.left) {
                Node uncle = grandparent.right;

                if (uncle != null && uncle.red) {
                    parent.red = false;
                    uncle.red = false;
                    grandparent.red = true;
                    node = grandparent;
                } else {
                    if (node == parent.right) {
                        node = parent;
                        rotateLeft(node);
                        parent = node.parent;
                    }
                    parent.red = false;
                    grandparent.red = true;
                    rotateRight(grandparent);
                }
            } else {
                Node uncle = grandparent.left;

                if (uncle != null && uncle.red) {
                    parent.red = false;
                    uncle.red = false;
                    grandparent.red = true;
                    node = grandparent;
                } else {
                    if (node == parent.left) {
                        node = parent;
                        rotateRight(node);
                        parent = node.parent;
                    }
                    parent.red = false;
                    grandparent.red = true;
                    rotateLeft(grandparent);
                }
            }
        }

        root.red = false;
    }

    private void rotateLeft(Node x) {
        Node y = x.right;

        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }

        replaceChild(x, y);

        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;

        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }

        replaceChild(x, y);

        y.right = x;
        x.parent = y;
    }

    private void replaceChild(Node oldChild, Node newChild) {
        newChild.parent = oldChild.parent;

        if (oldChild.parent == null) {
            root = newChild;
        } else if (oldChild == oldChild.parent.left) {
            oldChild.parent.left = newChild;
        } else {
            oldChild.parent.right = newChild;
        }
    }

    public boolean contains(int key) {
        Node current = root;

        while (current != null) {
            if (key == current.key) {
                return true;
            } else if (key < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
}
