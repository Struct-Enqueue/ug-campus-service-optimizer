# Feature 4 — Priority Queue, Graph Core & Disjoint Set

This guide is specifically for the Feature 4 team and is based on the project requirements in the main README and the Feature 1 setup guide.

## 1. What this feature is responsible for

Feature 4 builds the core infrastructure used by every later graph and service feature:

- `MyPriorityQueue.java` — a custom min-heap priority queue for scheduling and dispatch ordering
- `Graph.java` — an adjacency-list + adjacency-matrix campus graph representation
- `DisjointSet.java` — union-find structure used by Kruskal's MST algorithm

This is the foundation for Feature 5 (BFS, DFS, Dijkstra, Prim, Kruskal), Feature 8 (service scheduling), and final system integration.

## 2. Prerequisites from the project docs

Before starting this feature, make sure you have completed the setup described in:

- [README.md](../README.md)
- [FEATURE1_SETUP_GUIDE.md](FEATURE1_SETUP_GUIDE.md)

At minimum:

1. Java 17+ is installed
2. Maven is available from the terminal
3. The project compiles successfully
4. You understand the project conventions: no built-in Java graph libraries, no `PriorityQueue` use, and all method names must stay exactly as defined

## 3. Build steps for Feature 4

### Step 1: Open the stubs and review the required API

Check these files:

- `src/main/java/com/ug/campusops/datastructures/MyPriorityQueue.java`
- `src/main/java/com/ug/campusops/graph/Graph.java`
- `src/main/java/com/ug/campusops/datastructures/DisjointSet.java`

Do not rename any method signatures. The rest of the project expects the exact names already defined in the stubs.

### Step 2: Confirm the project still compiles

From the project root, run:

```bash
mvn compile
```

If you have not set up the database yet, that is fine for this feature, because the project can compile without a live PostgreSQL connection. The Feature 1 setup guide is still required for later features, but Feature 4 itself is focused on the data structures and graph layer.

### Step 3: Review the expected behavior

#### MyPriorityQueue

This should behave like a min-heap:

- smallest item is at the front
- `insert()` must restore heap order
- `extractMin()` must return the minimum element and maintain the heap
- `peek()` returns the minimum without removing it
- `heapify()` must rebuild the heap from the current state
- `extractMax()` should work even in a min-heap by scanning the heap and removing the maximum element

#### Graph

This should represent the campus network with:

- adjacency list for neighbor traversal
- adjacency matrix for edge-weight lookup
- vertex count and edge count tracking
- support for weighted directed edges
- `getNeighbors()` and `getWeight()` being the most important methods for Feature 5

#### DisjointSet

This should implement the standard union-find behavior:

- `makeSet()` creates a singleton set
- `find()` returns the representative root with path compression
- `union()` merges sets by rank
- `connected()` decides whether two elements belong to the same component
- `getCount()` reports how many sets remain

### Step 4: Write tests for the expected behavior

Before implementing the fix, create/complete tests in:

- `src/test/java/com/ug/campusops/datastructures/MyPriorityQueueTest.java`
- `src/test/java/com/ug/campusops/graph/GraphTest.java`
- `src/test/java/com/ug/campusops/datastructures/DisjointSetTest.java`

These tests should cover:

- insertion and removal ordering
- heap validity
- empty queue behavior
- graph vertex/edge creation
- neighbor retrieval and edge weights
- union-find connectivity and rank logic

### Step 5: Run the feature tests and confirm failure

Run:

```bash
mvn test
```

The first run should fail because the Feature 4 methods are still stubs. This is the correct red-to-green workflow for the project.

### Step 6: Implement the feature

Implement only the required behavior in the three feature 4 files:

- correct heap operations in `MyPriorityQueue`
- graph storage and lookup methods in `Graph`
- union-find operations in `DisjointSet`

Keep the method names exactly as they appear in the stubs.

### Step 7: Validate the feature

After implementation, run:

```bash
mvn test
```

Your results should show the Feature 4 tests passing, which confirms the structure is ready for the graph algorithm work in Feature 5.

## 4. Common pitfalls to avoid

- Do not rename the methods required by later features
- Do not use `java.util.PriorityQueue` in `MyPriorityQueue`
- Do not use any built-in graph framework for `Graph`
- Do not skip path compression or union-by-rank in `DisjointSet`
- Do not rely on unsupported assumptions like all location IDs starting from zero; handle reasonable positive IDs robustly

## 5. Expected deliverables

By the end of this feature, the team should have:

- a working heap-based priority queue
- a working campus graph structure
- a working union-find algorithm
- passing JUnit tests for the feature
- a clean foundation for Feature 5 graph algorithms

## 6. Suggested validation checklist

- [ ] `MyPriorityQueue` inserts and removes in sorted priority order
- [ ] `Graph` adds vertices and weighted edges correctly
- [ ] `Graph.getNeighbors()` returns the expected neighbor list
- [ ] `Graph.getWeight()` returns `-1` for missing edges
- [ ] `DisjointSet.find()` compresses paths
- [ ] `DisjointSet.union()` merges by rank
- [ ] `DisjointSet.connected()` correctly reports connected components
- [ ] `mvn test` passes without errors

## 7. Next step after Feature 4

Once this feature passes, the Feature 5 team can begin implementing BFS, DFS, Dijkstra, Prim, and Kruskal using the exact interfaces created here.
