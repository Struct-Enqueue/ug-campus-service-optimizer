package com.ug.campusops.datastructures;

/**
 * A Red-Black Tree: a self-balancing BST that guarantees O(log n) search, insert, and delete.
 * Each node is coloured RED or BLACK, and the tree maintains balance through rotations
 * and recolouring after every insertion or deletion.
 *
 * This is a custom implementation — do NOT use java.util.TreeMap.
 *
 * Feature 3 — Trees & Hashing
 *
 * Required evidence: before/after rotation diagrams and height discussion.
 *
 * @param <K> the type of keys (must be Comparable)
 * @param <V> the type of values
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    protected static final boolean RED   = true;
    protected static final boolean BLACK = false;

    /**
     * Internal node with a colour field.
     */
    protected static class RBNode<K, V> {
        public K key;
        public V value;
        public RBNode<K, V> left;
        public RBNode<K, V> right;
        public RBNode<K, V> parent;
        public boolean colour; // RED = true, BLACK = false

        public RBNode(K key, V value, boolean colour) {
            this.key = key;
            this.value = value;
            this.colour = colour;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    protected RBNode<K, V> root;
    protected int size;

    /** Creates an empty Red-Black Tree. */
    public RedBlackTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Inserts a key-value pair and restores Red-Black properties via rotations/recolouring.
     *
     * @param key   the key to insert
     * @param value the associated value
     */
    public void insert(K key, V value) {
        RBNode<K, V> node = new RBNode<>(key, value, RED);
        RBNode<K, V> y = null;
        RBNode<K, V> x = root;
        
        while (x != null) {
            y = x;
            int cmp = node.key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                x.value = value;
                return; // Key exists, just update
            }
        }
        
        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.key.compareTo(y.key) < 0) {
            y.left = node;
        } else {
            y.right = node;
        }
        
        if (node.parent == null) {
            node.colour = BLACK;
            size++;
            return;
        }
        if (node.parent.parent == null) {
            size++;
            return;
        }
        
        fixup(node);
        size++;
    }

    /**
     * Searches for a value by key.
     *
     * @param key the key to search for
     * @return the associated value, or null if not found
     */
    public V search(K key) {
        RBNode<K, V> current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp == 0) return current.value;
            else if (cmp < 0) current = current.left;
            else current = current.right;
        }
        return null;
    }

    /**
     * Deletes a node with the specified key and rebalances.
     *
     * @param key the key to delete
     * @return true if found and deleted, false otherwise
     */
    public boolean delete(K key) {
        RBNode<K, V> z = root;
        while (z != null) {
            int cmp = key.compareTo(z.key);
            if (cmp == 0) break;
            else if (cmp < 0) z = z.left;
            else z = z.right;
        }
        if (z == null) return false;
        
        RBNode<K, V> x;
        RBNode<K, V> y = z;
        boolean yOriginalColour = y.colour;
        RBNode<K, V> dummy = null;
        
        if (z.left == null) {
            x = z.right;
            if (x == null) {
                dummy = new RBNode<>(null, null, BLACK);
                dummy.parent = z.parent;
                x = dummy;
                if (z.parent != null) {
                    if (z == z.parent.left) z.parent.left = dummy;
                    else z.parent.right = dummy;
                } else {
                    root = dummy;
                }
            } else {
                transplant(z, z.right);
            }
        } else if (z.right == null) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColour = y.colour;
            x = y.right;
            if (x == null) {
                dummy = new RBNode<>(null, null, BLACK);
                x = dummy;
                dummy.parent = y;
                y.right = dummy;
            }
            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, x); 
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.colour = z.colour;
        }
        
        if (yOriginalColour == BLACK) {
            deleteFixup(x);
        }
        
        if (dummy != null) {
            if (dummy.parent == null) {
                root = null;
            } else if (dummy == dummy.parent.left) {
                dummy.parent.left = null;
            } else {
                dummy.parent.right = null;
            }
        }
        
        size--;
        return true;
    }

    private void transplant(RBNode<K, V> u, RBNode<K, V> v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if (v != null) {
            v.parent = u.parent;
        }
    }

    private RBNode<K, V> minimum(RBNode<K, V> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private void deleteFixup(RBNode<K, V> x) {
        while (x != null && x != root && x.colour == BLACK) {
            if (x == x.parent.left) {
                RBNode<K, V> w = x.parent.right;
                if (w != null && w.colour == RED) {
                    w.colour = BLACK;
                    x.parent.colour = RED;
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }
                if (w == null || ((w.left == null || w.left.colour == BLACK) && (w.right == null || w.right.colour == BLACK))) {
                    if (w != null) w.colour = RED;
                    x = x.parent;
                } else {
                    if (w.right == null || w.right.colour == BLACK) {
                        if (w.left != null) w.left.colour = BLACK;
                        w.colour = RED;
                        rotateRight(w);
                        w = x.parent.right;
                    }
                    w.colour = x.parent.colour;
                    x.parent.colour = BLACK;
                    if (w.right != null) w.right.colour = BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                RBNode<K, V> w = x.parent.left;
                if (w != null && w.colour == RED) {
                    w.colour = BLACK;
                    x.parent.colour = RED;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }
                if (w == null || ((w.right == null || w.right.colour == BLACK) && (w.left == null || w.left.colour == BLACK))) {
                    if (w != null) w.colour = RED;
                    x = x.parent;
                } else {
                    if (w.left == null || w.left.colour == BLACK) {
                        if (w.right != null) w.right.colour = BLACK;
                        w.colour = RED;
                        rotateLeft(w);
                        w = x.parent.left;
                    }
                    w.colour = x.parent.colour;
                    x.parent.colour = BLACK;
                    if (w.left != null) w.left.colour = BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        if (x != null) {
            x.colour = BLACK;
        }
    }

    /**
     * Left-rotates the subtree rooted at the given node.
     * Required for the "rotation diagrams" evidence.
     *
     * @param node the node to rotate around
     */
    protected void rotateLeft(RBNode<K, V> node) {
        if (node == null || node.right == null) return;
        RBNode<K, V> y = node.right;
        node.right = y.left;
        if (y.left != null) {
            y.left.parent = node;
        }
        y.parent = node.parent;
        if (node.parent == null) {
            root = y;
        } else if (node == node.parent.left) {
            node.parent.left = y;
        } else {
            node.parent.right = y;
        }
        y.left = node;
        node.parent = y;
    }

    /**
     * Right-rotates the subtree rooted at the given node.
     *
     * @param node the node to rotate around
     */
    protected void rotateRight(RBNode<K, V> node) {
        if (node == null || node.left == null) return;
        RBNode<K, V> y = node.left;
        node.left = y.right;
        if (y.right != null) {
            y.right.parent = node;
        }
        y.parent = node.parent;
        if (node.parent == null) {
            root = y;
        } else if (node == node.parent.right) {
            node.parent.right = y;
        } else {
            node.parent.left = y;
        }
        y.right = node;
        node.parent = y;
    }

    /**
     * Fixes Red-Black property violations after insertion.
     *
     * @param node the newly inserted node
     */
    protected void fixup(RBNode<K, V> node) {
        while (node.parent != null && node.parent.colour == RED) {
            if (node.parent == node.parent.parent.left) {
                RBNode<K, V> u = node.parent.parent.right;
                if (u != null && u.colour == RED) {
                    node.parent.colour = BLACK;
                    u.colour = BLACK;
                    node.parent.parent.colour = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.colour = BLACK;
                    node.parent.parent.colour = RED;
                    rotateRight(node.parent.parent);
                }
            } else {
                RBNode<K, V> u = node.parent.parent.left;
                if (u != null && u.colour == RED) {
                    node.parent.colour = BLACK;
                    u.colour = BLACK;
                    node.parent.parent.colour = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.colour = BLACK;
                    node.parent.parent.colour = RED;
                    rotateLeft(node.parent.parent);
                }
            }
        }
        root.colour = BLACK;
    }

    /** Returns the number of nodes. */
    public int size() { return size; }

    /** Returns true if the tree is empty. */
    public boolean isEmpty() { return root == null; }

    /** Returns the black-height of the tree (for balance verification). */
    public int height() {
        int blackHeight = 0;
        RBNode<K, V> current = root;
        while (current != null) {
            if (current.colour == BLACK) {
                blackHeight++;
            }
            current = current.left;
        }
        return blackHeight;
    }
}
