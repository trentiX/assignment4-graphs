import java.util.*;

/**
 * Represents a graph using a weighted adjacency list.
 * Supports BFS, DFS, and Dijkstra's shortest path algorithm.
 */
public class Graph {
    // Maps vertex id -> Vertex object
    private Map<Integer, Vertex> vertices;
    // Weighted adjacency list: vertex id -> list of [neighborId, weight]
    private Map<Integer, List<int[]>> adjList;

    public Graph() {
        vertices = new HashMap<>();
        adjList  = new HashMap<>();
    }

    /** Add a vertex to the graph. Ignored if already present. */
    public void addVertex(Vertex v) {
        if (!vertices.containsKey(v.getId())) {
            vertices.put(v.getId(), v);
            adjList.put(v.getId(), new ArrayList<>());
        }
    }

    /**
     * Add a directed weighted edge from 'from' to 'to'.
     * Both vertices must already exist.
     */
    public void addEdge(int from, int to, int weight) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            throw new IllegalArgumentException("Vertex not found: " + from + " or " + to);
        }
        adjList.get(from).add(new int[]{to, weight});
    }

    /** Convenience method: unweighted edge (weight = 1) */
    public void addEdge(int from, int to) {
        addEdge(from, to, 1);
    }

    /** Print the adjacency list with weights. */
    public void printGraph() {
        System.out.println("Graph adjacency list (weighted):");
        for (int id : adjList.keySet()) {
            System.out.print("  " + id + " -> ");
            List<int[]> neighbors = adjList.get(id);
            if (neighbors.isEmpty()) {
                System.out.println("[]");
            } else {
                StringJoiner sj = new StringJoiner(", ", "[", "]");
                for (int[] nb : neighbors) sj.add(nb[0] + "(w=" + nb[1] + ")");
                System.out.println(sj);
            }
        }
    }

    // ----------------------------------------------------------------
    // BFS
    // ----------------------------------------------------------------
    /**
     * Breadth-First Search from 'start'.
     * Time complexity: O(V + E)
     */
    public List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);
            for (int[] nb : adjList.get(current)) {
                if (!visited.contains(nb[0])) {
                    visited.add(nb[0]);
                    queue.add(nb[0]);
                }
            }
        }
        return order;
    }

    // ----------------------------------------------------------------
    // DFS
    // ----------------------------------------------------------------
    /**
     * Depth-First Search from 'start' (iterative).
     * Time complexity: O(V + E)
     */
    public List<Integer> dfs(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (visited.contains(current)) continue;

            visited.add(current);
            order.add(current);

            List<int[]> neighbors = adjList.get(current);
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                if (!visited.contains(neighbors.get(i)[0])) {
                    stack.push(neighbors.get(i)[0]);
                }
            }
        }
        return order;
    }

    // ----------------------------------------------------------------
    // Dijkstra
    // ----------------------------------------------------------------
    /**
     * Dijkstra's Algorithm — finds shortest distances from 'start' to all vertices.
     *
     * How it works:
     *  1. Set distance to start = 0, all others = infinity.
     *  2. Repeat V times:
     *     a. Pick the unvisited vertex with the smallest known distance.
     *     b. Mark it visited.
     *     c. For each neighbor: if dist[current] + weight < dist[neighbor], update it.
     *  3. Print the result.
     *
     * Time complexity: O(V²) — simple array-based implementation (no priority queue).
     *
     * @param start starting vertex id
     */
    public void dijkstra(int start) {
        int n = vertices.size();
        int[] dist    = new int[n];       // shortest distance from start to each vertex
        boolean[] vis = new boolean[n];   // whether vertex has been finalized

        // Step 1: initialize all distances to infinity
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        for (int iter = 0; iter < n; iter++) {
            // Step 2a: find unvisited vertex with minimum distance
            int u = -1;
            for (int v = 0; v < n; v++) {
                if (!vis[v] && (u == -1 || dist[v] < dist[u])) {
                    u = v;
                }
            }

            if (u == -1 || dist[u] == Integer.MAX_VALUE) break; // remaining unreachable

            // Step 2b: mark as visited (finalized)
            vis[u] = true;

            // Step 2c: relax neighbors
            if (!adjList.containsKey(u)) continue;
            for (int[] nb : adjList.get(u)) {
                int v = nb[0], w = nb[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }

        // Step 3: print results
        System.out.println("Dijkstra shortest distances from vertex " + start + ":");
        for (int v = 0; v < n; v++) {
            if (!vertices.containsKey(v)) continue;
            String distStr = (dist[v] == Integer.MAX_VALUE) ? "unreachable" : String.valueOf(dist[v]);
            System.out.printf("  Vertex %d: %s%n", v, distStr);
        }
    }

    public int vertexCount() { return vertices.size(); }
    public int edgeCount()   { return adjList.values().stream().mapToInt(List::size).sum(); }
}