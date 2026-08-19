package com.ug.campusops.datastructures;

/**
 * A generic Binary Search Tree (BST). Organizes data in a tree shape so that
 * searching is O(log n) on average instead of O(n) with a linear scan.
 *
 * This is a custom implementation — do NOT use java.util.TreeMap or TreeSet.
 *
 * Feature 3 — Trees & Hashing
 *
 * Required evidence: search path trace and sorted inorder output.
 *
 * @param <K> the type of keys (must be Comparable)
 * @param <V> the type of values
 */
public class BST<K extends Comparable<K>, V> {

    /**
     * Internal node of the BST.
     */
    protected static class BSTNode<K, V> {
        public K key;
        public V value;
        public BSTNode<K, V> left;
        public BSTNode<K, V> right;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    protected BSTNode<K, V> root;
    protected int size;

    /** Creates an empty BST. */
    public BST() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Inserts a key-value pair. If the key already exists, updates the value.
     *
     * @param key   the key to insert
     * @param value the value associated with the key
     */
    public void insert(K key, V value) {
        root = insertRec(root, key, value);
    }

    private BSTNode<K, V> insertRec(BSTNode<K, V> node, K key, V value) {
        if (node == null) {
            size++;
            return new BSTNode<>(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insertRec(node.left, key, value);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    /**
     * Searches for a value by key.
     *
     * @param key the key to search for
     * @return the associated value, or null if not found
     */
    public V search(K key) {
        BSTNode<K, V> current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp == 0) return current.value;
            else if (cmp < 0) current = current.left;
            else current = current.right;
        }
        return null;
    }

    /**
     * Deletes a node with the specified key.
     *
     * @param key the key to delete
     * @return true if the key was found and deleted, false otherwise
     */
    public boolean delete(K key) {
        int initialSize = size;
        root = deleteRec(root, key);
        return size < initialSize;
    }

    private BSTNode<K, V> deleteRec(BSTNode<K, V> node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = deleteRec(node.left, key);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, key);
        } else {
            size--;
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            
            BSTNode<K, V> minNode = findMin(node.right);
            node.key = minNode.key;
            node.value = minNode.value;
            size++; // Compensate for the recursive delete
            node.right = deleteRec(node.right, minNode.key);
        }
        return node;
    }

    private BSTNode<K, V> findMin(BSTNode<K, V> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Returns all key-value pairs in sorted (inorder) order.
     * Left subtree → Root → Right subtree
     * Required for the "sorted inorder output" evidence.
     */
    public void inorderTraversal() {
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(BSTNode<K, V> node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.print(node.key + " ");
            inorderRec(node.right);
        }
    }

    /**
     * Root → Left subtree → Right subtree
     */
    public void preorderTraversal() {
        preorderRec(root);
        System.out.println();
    }

    private void preorderRec(BSTNode<K, V> node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preorderRec(node.left);
            preorderRec(node.right);
        }
    }

    /**
     * Left subtree → Right subtree → Root
     */
    public void postorderTraversal() {
        postorderRec(root);
        System.out.println();
    }

    private void postorderRec(BSTNode<K, V> node) {
        if (node != null) {
            postorderRec(node.left);
            postorderRec(node.right);
            System.out.print(node.key + " ");
        }
    }

    /** Returns the number of nodes in the tree. */
    public int size() { return size; }

    /** Returns true if the tree is empty. */
    public boolean isEmpty() { return root == null; }

    /** Returns the height of the tree (for performance comparison with balanced trees). */
    public int height() {
        return heightRec(root);
    }

    private int heightRec(BSTNode<K, V> node) {
        return node == null ? 0 : 1 + Math.max(heightRec(node.left), heightRec(node.right));
    }

    /** Returns the root node (for testing/diagramming). */
    public BSTNode<K, V> getRoot() { return root; }
}
