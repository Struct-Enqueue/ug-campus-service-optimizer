package com.ug.campusops.datastructures;

/**
 * A B-Tree of configurable order (minimum degree t). B-Trees are designed for
 * systems that read and write large blocks of data (like databases and file systems).
 * Each node can have multiple keys and children, reducing the number of disk accesses.
 *
 * This is a custom implementation for the database index simulation.
 *
 * Feature 3 — Trees & Hashing
 *
 * Required evidence: search trace and node split explanation.
 *
 * @param <K> the type of keys (must be Comparable)
 */
public class BTree<K extends Comparable<K>> {

    /**
     * A B-Tree node. Each node contains up to (2t - 1) keys and up to 2t children.
     */
    protected static class BTreeNode<K> {
        public int numKeys;          // current number of keys
        public Object[] keys;        // array of keys
        public BTreeNode<K>[] children; // array of child pointers
        public boolean isLeaf;       // true if this is a leaf node

        @SuppressWarnings("unchecked")
        public BTreeNode(int t, boolean isLeaf) {
            this.isLeaf = isLeaf;
            this.keys = new Object[2 * t - 1];
            this.children = new BTreeNode[2 * t];
            this.numKeys = 0;
        }
    }

    protected BTreeNode<K> root;
    protected int t; // minimum degree (defines range for number of keys per node)

    /**
     * Creates an empty B-Tree with the specified minimum degree.
     *
     * @param t minimum degree (each non-root node has at least t-1 keys, at most 2t-1 keys)
     */
    public BTree(int t) {
        this.t = t;
        this.root = null;
    }

    /**
     * Inserts a key into the B-Tree, splitting nodes as needed.
     *
     * @param key the key to insert
     */
    public void insert(K key) {
        // TODO: Feature 3 team — implement this (handle root split if full)
        throw new UnsupportedOperationException("BTree.insert() not yet implemented");
    }

    /**
     * Searches for a key in the B-Tree.
     *
     * @param key the key to search for
     * @return true if the key is found, false otherwise
     */
    public boolean search(K key) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("BTree.search() not yet implemented");
    }

    /**
     * Deletes a key from the B-Tree, merging or redistributing nodes as needed.
     *
     * @param key the key to delete
     * @return true if found and deleted, false otherwise
     */
    public boolean delete(K key) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("BTree.delete() not yet implemented");
    }

    /**
     * Splits a full child node into two nodes.
     * Required for the "node split explanation" evidence.
     *
     * @param parent the parent node
     * @param index  the index of the child to split
     */
    protected void splitChild(BTreeNode<K> parent, int index) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("BTree.splitChild() not yet implemented");
    }

    /**
     * Prints the tree level by level (for visualization and tracing).
     */
    public void traverse() {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("BTree.traverse() not yet implemented");
    }

    /** Returns the root node (for testing). */
    public BTreeNode<K> getRoot() { return root; }

    /** Returns the minimum degree. */
    public int getMinDegree() { return t; }
}
