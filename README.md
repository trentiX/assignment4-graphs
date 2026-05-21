# Assignment 4: Graph Traversal and Representation System

## A. Project Overview

This project implements a **Graph** data structure and two classic traversal algorithms — **Breadth-First Search (BFS)** and **Depth-First Search (DFS)** — in Java.

A **graph** is a collection of **vertices** (nodes) connected by **edges** (links). Graphs model many real-world systems: social networks, road maps, web pages, and dependency trees.

- A **vertex** is an entity (a person, a city, a page).
- An **edge** is a relationship between two vertices (a friendship, a road, a hyperlink).
- This implementation uses **directed edges** — an edge from A to B does not automatically create an edge from B to A.

**BFS** explores the graph level by level, visiting all immediate neighbors before going deeper. **DFS** dives as deep as possible along each path before backtracking.

---

## B. Class Descriptions

### `Vertex`
Represents a single node in the graph. Stores a unique integer `id` and provides a `toString()` method for readable output.

### `Edge`
Represents a directed connection between two vertices (`source → destination`). Stores references to both `Vertex` objects.

### `Graph`
The core data structure. Uses an **adjacency list** — a `HashMap<Integer, List<Integer>>` — to map each vertex id to its list of neighbors.

**Why adjacency list?**
- Space-efficient: stores only existing edges — O(V + E) instead of O(V²).
- Fast neighbor lookup: iterating neighbors is proportional to the degree of the vertex.
- Ideal for sparse graphs (most real-world graphs are sparse).

**Methods:**
- `addVertex(Vertex v)` — registers a new vertex.
- `addEdge(int from, int to)` — adds a directed edge.
- `printGraph()` — prints the full adjacency list.
- `bfs(int start)` — BFS traversal, returns visit order.
- `dfs(int start)` — DFS traversal, returns visit order.

### `Experiment`
Builds graphs of sizes 10, 30, and 100 vertices, runs traversals, measures execution time with `System.nanoTime()`, and prints a formatted comparison table.

---

## C. Algorithm Descriptions

### Breadth-First Search (BFS)

**Step-by-step:**
1. Enqueue the starting vertex; mark it visited.
2. Dequeue the front vertex; record it.
3. For each unvisited neighbor: mark visited, enqueue.
4. Repeat until the queue is empty.

**Data structure:** Queue (FIFO)

**Use cases:**
- Finding the shortest path in an unweighted graph.
- Level-order traversal.
- Web crawlers, social network friend recommendations.

**Time complexity:** O(V + E) — every vertex and every edge is processed at most once.

---

### Depth-First Search (DFS)

**Step-by-step:**
1. Push the starting vertex onto a stack.
2. Pop the top vertex; if not visited, record it and mark visited.
3. Push all unvisited neighbors (in reverse order to preserve direction).
4. Repeat until the stack is empty.

**Data structure:** Stack (LIFO) — iterative implementation to avoid stack overflow.

**Use cases:**
- Cycle detection.
- Topological sorting.
- Maze solving, puzzle solving (explore all paths).

**Time complexity:** O(V + E) — same as BFS theoretically.

**Limitations of DFS:**
- Does **not** guarantee the shortest path.
- Recursive DFS can cause `StackOverflowError` on very deep graphs (this implementation uses an iterative stack to avoid this).
- May explore long, unproductive branches before finding a target.

---

## D. Experimental Results

Graphs were built with the following edge pattern: each vertex `i` connects to vertices `(i+1) % n`, `(i+2) % n`, and `(i+3) % n`. Every graph is strongly connected.

### Execution Time Comparison

| Graph Size   | Vertices | Edges | BFS (ns) | DFS (ns) |
|--------------|----------|-------|----------|----------|
| Small        | 10       | 30    | 26,700   | 28,300   |
| Medium       | 30       | 90    | 51,100   | 168,600  |
| Large        | 100      | 300   | 349,400  | 211,900  |

*(Times vary slightly per run due to JVM JIT warm-up.)*

### Observations

- **Both algorithms scale with graph size**, consistent with O(V + E) — as vertex and edge count grows, execution time increases proportionally.
- **BFS is faster on small and medium graphs** (26,700 ns vs 28,300 ns and 51,100 ns vs 168,600 ns), because its queue-based access pattern is more predictable.
- **DFS becomes faster on the large graph** (211,900 ns vs 349,400 ns for BFS), likely because DFS reaches all vertices quickly via deep paths without the overhead of managing a wide queue frontier.
- The traversal **order** on the small graph was identical (`[0, 1, 2, ..., 9]`) due to the regular edge pattern, but in general BFS visits level by level while DFS dives deep first.

---

## E. Screenshots

### Graph Structure & BFS/DFS Output (Small Graph)
![Graph structure and traversal output](docs/screenshots/output1.png)

### Performance Results (Full Run)
![Performance summary table](docs/screenshots/output2.png)

---

## F. Reflection

Implementing this assignment deepened my understanding of how graph traversal algorithms differ not just in theory but in practice. The key insight is that BFS and DFS both achieve O(V + E) time complexity, yet they explore the graph in fundamentally different orders — BFS fans out broadly (useful for shortest paths), while DFS dives deep (useful for connectivity and cycle detection). Seeing the traversal orders printed side by side made this difference concrete.

The biggest challenge was implementing DFS iteratively rather than recursively. Recursive DFS is simpler to write, but it risks a `StackOverflowError` on large or deeply connected graphs. Rewriting it with an explicit `ArrayDeque` required carefully managing the visited check — since a vertex can be pushed onto the stack multiple times before being visited, the check must happen at pop time, not push time. Getting this detail right was the most interesting debugging exercise of the assignment.

---

## Repository Structure

```
assignment4-graphs/
├── src/
│   ├── Vertex.java
│   ├── Edge.java
│   ├── Graph.java
│   ├── Experiment.java
│   └── Main.java
├── README.md
└── .gitignore
```

## Git Commit Storyline

```
init: project structure
feat(vertex): implemented Vertex class
feat(edge): added Edge class
feat(graph): implemented adjacency list
feat(traversal): added BFS and DFS
feat(experiment): added performance testing
docs(readme): added analysis and results
perf(cleanup): improved code readability
release: v1.0
```
