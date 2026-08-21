package com.ug.campusops.datastructures;

/**
 * Disjoint Set (Union-Find) data structure with union by rank and path compression.
 * Used to track which campus locations are connected to each other,
 * particularly needed for Kruskal's MST algorithm.
 *
 * This is a custom implementation.
 *
 * Feature 4 — Priority Queue, Graph Core & Disjoint Set
 *
 * Required evidence: Kruskal connectivity trace showing find/union operations.
 */
public class DisjointSet {

    private int[] parent;
    private int[] rank;
    private int count; // number of disjoint sets

    /**
     * Creates a disjoint set structure for n elements (0 to n-1).
     * Initially each element is its own set.
     *
     * @param n the number of elements
     */
    public DisjointSet(int n) {
        this.parent = new int[n];
        this.rank = new int[n];
        this.count = n;
        // Each element starts as its own parent (its own set)
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    /**
     * Initializes a single element as its own set.
     * (Already done in constructor, but can be called for dynamically added elements.)
     *
     * @param x the element to make a set for
     */
    public void makeSet(int x) {
        if (x < 0 || x >= parent.length) {
            throw new IllegalArgumentException("Element out of range: " + x);
        }
        if (parent[x] != x || rank[x] != 0) {
            parent[x] = x;
            rank[x] = 0;
            count++;
        }
    }

    /**
     * Finds and returns the representative (root) of the set containing element x.
     * Must implement PATH COMPRESSION: make every node on the path point directly to root.
     *
     * @param x the element to find the root of
     * @return the root representative of x's set
     */
    public int find(int x) {
        if (x < 0 || x >= parent.length) {
            throw new IllegalArgumentException("Element out of range: " + x);
        }
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    /**
     * Merges the sets containing elements a and b.
     * Must implement UNION BY RANK: attach the shorter tree under the taller one.
     *
     * @param a element from the first set
     * @param b element from the second set
     * @return true if the sets were different and got merged, false if already in same set
     */
    public boolean union(int a, int b) {
        if (a < 0 || a >= parent.length || b < 0 || b >= parent.length) {
            throw new IllegalArgumentException("Element out of range: " + a + ", " + b);
        }

        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) {
            return false;
        }

        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }

        count--;
        return true;
    }

    /**
     * Returns true if elements a and b are in the same set.
     *
     * @param a first element
     * @param b second element
     * @return true if connected (same set)
     */
    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    /**
     * Returns the number of disjoint sets remaining.
     */
    public int getCount() {
        return count;
    }
}
