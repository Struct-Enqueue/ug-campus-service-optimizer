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
        if (root == null) {
            root = new BTreeNode<>(t, true);
            root.keys[0] = key;
            root.numKeys = 1;
        } else {
            if (root.numKeys == 2 * t - 1) {
                BTreeNode<K> s = new BTreeNode<>(t, false);
                s.children[0] = root;
                splitChild(s, 0);
                int i = 0;
                if (((K) s.keys[0]).compareTo(key) < 0) {
                    i++;
                }
                insertNonFull(s.children[i], key);
                root = s;
            } else {
                insertNonFull(root, key);
            }
        }
    }

    private void insertNonFull(BTreeNode<K> x, K key) {
        int i = x.numKeys - 1;
        if (x.isLeaf) {
            while (i >= 0 && ((K) x.keys[i]).compareTo(key) > 0) {
                x.keys[i + 1] = x.keys[i];
                i--;
            }
            x.keys[i + 1] = key;
            x.numKeys = x.numKeys + 1;
        } else {
            while (i >= 0 && ((K) x.keys[i]).compareTo(key) > 0) {
                i--;
            }
            i++;
            if (x.children[i].numKeys == 2 * t - 1) {
                splitChild(x, i);
                if (((K) x.keys[i]).compareTo(key) < 0) {
                    i++;
                }
            }
            insertNonFull(x.children[i], key);
        }
    }

    /**
     * Searches for a key in the B-Tree.
     *
     * @param key the key to search for
     * @return true if the key is found, false otherwise
     */
    public boolean search(K key) {
        return search(root, key);
    }

    private boolean search(BTreeNode<K> x, K key) {
        if (x == null) return false;
        int i = 0;
        while (i < x.numKeys && key.compareTo((K) x.keys[i]) > 0) {
            i++;
        }
        if (i < x.numKeys && key.compareTo((K) x.keys[i]) == 0) {
            return true;
        }
        if (x.isLeaf) {
            return false;
        }
        return search(x.children[i], key);
    }

    /**
     * Deletes a key from the B-Tree, merging or redistributing nodes as needed.
     *
     * @param key the key to delete
     * @return true if found and deleted, false otherwise
     */
    public boolean delete(K key) {
        if (root == null) return false;
        boolean found = search(key);
        if (found) {
            delete(root, key);
            if (root.numKeys == 0) {
                if (root.isLeaf) {
                    root = null;
                } else {
                    root = root.children[0];
                }
            }
        }
        return found;
    }

    private void delete(BTreeNode<K> x, K key) {
        int idx = findKey(x, key);
        if (idx < x.numKeys && ((K) x.keys[idx]).compareTo(key) == 0) {
            if (x.isLeaf) {
                removeFromLeaf(x, idx);
            } else {
                removeFromNonLeaf(x, idx);
            }
        } else {
            if (x.isLeaf) return;
            boolean flag = (idx == x.numKeys);
            if (x.children[idx].numKeys < t) {
                fill(x, idx);
            }
            if (flag && idx > x.numKeys) {
                delete(x.children[idx - 1], key);
            } else {
                delete(x.children[idx], key);
            }
        }
    }

    private int findKey(BTreeNode<K> x, K key) {
        int idx = 0;
        while (idx < x.numKeys && ((K) x.keys[idx]).compareTo(key) < 0) {
            idx++;
        }
        return idx;
    }

    private void removeFromLeaf(BTreeNode<K> x, int idx) {
        for (int i = idx + 1; i < x.numKeys; i++) {
            x.keys[i - 1] = x.keys[i];
        }
        x.numKeys--;
    }

    private void removeFromNonLeaf(BTreeNode<K> x, int idx) {
        K k = (K) x.keys[idx];
        if (x.children[idx].numKeys >= t) {
            K pred = getPred(x, idx);
            x.keys[idx] = pred;
            delete(x.children[idx], pred);
        } else if (x.children[idx + 1].numKeys >= t) {
            K succ = getSucc(x, idx);
            x.keys[idx] = succ;
            delete(x.children[idx + 1], succ);
        } else {
            merge(x, idx);
            delete(x.children[idx], k);
        }
    }

    private K getPred(BTreeNode<K> x, int idx) {
        BTreeNode<K> cur = x.children[idx];
        while (!cur.isLeaf) {
            cur = cur.children[cur.numKeys];
        }
        return (K) cur.keys[cur.numKeys - 1];
    }

    private K getSucc(BTreeNode<K> x, int idx) {
        BTreeNode<K> cur = x.children[idx + 1];
        while (!cur.isLeaf) {
            cur = cur.children[0];
        }
        return (K) cur.keys[0];
    }

    private void fill(BTreeNode<K> x, int idx) {
        if (idx != 0 && x.children[idx - 1].numKeys >= t) {
            borrowFromPrev(x, idx);
        } else if (idx != x.numKeys && x.children[idx + 1].numKeys >= t) {
            borrowFromNext(x, idx);
        } else {
            if (idx != x.numKeys) {
                merge(x, idx);
            } else {
                merge(x, idx - 1);
            }
        }
    }

    private void borrowFromPrev(BTreeNode<K> x, int idx) {
        BTreeNode<K> child = x.children[idx];
        BTreeNode<K> sibling = x.children[idx - 1];
        
        for (int i = child.numKeys - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }
        
        if (!child.isLeaf) {
            for (int i = child.numKeys; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
        }
        
        child.keys[0] = x.keys[idx - 1];
        if (!child.isLeaf) {
            child.children[0] = sibling.children[sibling.numKeys];
        }
        
        x.keys[idx - 1] = sibling.keys[sibling.numKeys - 1];
        child.numKeys += 1;
        sibling.numKeys -= 1;
    }

    private void borrowFromNext(BTreeNode<K> x, int idx) {
        BTreeNode<K> child = x.children[idx];
        BTreeNode<K> sibling = x.children[idx + 1];
        
        child.keys[child.numKeys] = x.keys[idx];
        
        if (!child.isLeaf) {
            child.children[child.numKeys + 1] = sibling.children[0];
        }
        
        x.keys[idx] = sibling.keys[0];
        
        for (int i = 1; i < sibling.numKeys; i++) {
            sibling.keys[i - 1] = sibling.keys[i];
        }
        
        if (!sibling.isLeaf) {
            for (int i = 1; i <= sibling.numKeys; i++) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }
        
        child.numKeys += 1;
        sibling.numKeys -= 1;
    }

    private void merge(BTreeNode<K> x, int idx) {
        BTreeNode<K> child = x.children[idx];
        BTreeNode<K> sibling = x.children[idx + 1];
        
        child.keys[t - 1] = x.keys[idx];
        
        for (int i = 0; i < sibling.numKeys; i++) {
            child.keys[i + t] = sibling.keys[i];
        }
        
        if (!child.isLeaf) {
            for (int i = 0; i <= sibling.numKeys; i++) {
                child.children[i + t] = sibling.children[i];
            }
        }
        
        for (int i = idx + 1; i < x.numKeys; i++) {
            x.keys[i - 1] = x.keys[i];
        }
        
        for (int i = idx + 2; i <= x.numKeys; i++) {
            x.children[i - 1] = x.children[i];
        }
        
        child.numKeys += sibling.numKeys + 1;
        x.numKeys--;
    }

    /**
     * Splits a full child node into two nodes.
     * Required for the "node split explanation" evidence.
     *
     * @param parent the parent node
     * @param index  the index of the child to split
     */
    protected void splitChild(BTreeNode<K> parent, int index) {
        BTreeNode<K> y = parent.children[index];
        BTreeNode<K> z = new BTreeNode<>(t, y.isLeaf);
        z.numKeys = t - 1;
        
        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }
        
        if (!y.isLeaf) {
            for (int j = 0; j < t; j++) {
                z.children[j] = y.children[j + t];
            }
        }
        
        y.numKeys = t - 1;
        
        for (int j = parent.numKeys; j >= index + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        
        parent.children[index + 1] = z;
        
        for (int j = parent.numKeys - 1; j >= index; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }
        
        parent.keys[index] = y.keys[t - 1];
        parent.numKeys = parent.numKeys + 1;
    }

    /**
     * Prints the tree level by level (for visualization and tracing).
     */
    public void traverse() {
        if (root != null) {
            traverse(root, 0);
        }
    }

    private void traverse(BTreeNode<K> x, int level) {
        System.out.print("Level " + level + " [ ");
        for (int i = 0; i < x.numKeys; i++) {
            System.out.print(x.keys[i] + " ");
        }
        System.out.println("]");
        if (!x.isLeaf) {
            for (int i = 0; i <= x.numKeys; i++) {
                traverse(x.children[i], level + 1);
            }
        }
    }

    /** Returns the root node (for testing). */
    public BTreeNode<K> getRoot() { return root; }

    /** Returns the minimum degree. */
    public int getMinDegree() { return t; }
}
