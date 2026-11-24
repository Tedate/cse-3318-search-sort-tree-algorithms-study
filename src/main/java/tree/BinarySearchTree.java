package tree;

public class BinarySearchTree {
    private static class Node {
        int key;
        Node left;
        Node right;

        Node(int key) {
            this.key = key;
        }
    }

    private Node root;
    private int size;
    private int height;

    public void insert(int key) {
        if (root == null) {
            root = new Node(key);
            size = 1;
            height = 1;
            return;
        }

        Node current = root;
        int depth = 1;

        while (true) {
            if (key == current.key) {
                return;
            }

            depth++;

            if (key < current.key) {
                if (current.left == null) {
                    current.left = new Node(key);
                    break;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node(key);
                    break;
                }
                current = current.right;
            }
        }

        size++;
        if (depth > height) {
            height = depth;
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
        return height;
    }
}
