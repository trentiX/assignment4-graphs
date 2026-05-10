import java.util.*;

/**
 * Represents a graph using an adjacency list.
 * Supports directed edges and BFS/DFS traversals.
 */
public class Graph {
    // Maps vertex id -> Vertex object
    private Map<Integer, Vertex> vertices;
    // Adjacency list: vertex id -> list of neighbor vertex ids
    private Map<Integer, List<Integer>> adjList;

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
     * Add a directed edge from vertex 'from' to vertex 'to'.
     * Both vertices must already exist in the graph.
     */
    public void addEdge(int from, int to) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            throw new IllegalArgumentException("Vertex not found: " + from + " or " + to);
        }
        adjList.get(from).add(to);
    }

    /** Print the adjacency list representation of the graph. */
    public void printGraph() {
        System.out.println("Graph adjacency list:");
        for (int id : adjList.keySet()) {
            System.out.print("  " + id + " -> ");
            System.out.println(adjList.get(id));
        }
    }

    /**
     * Breadth-First Search starting from vertex 'start'.
     * Visits all reachable vertices level by level using a queue.
     * Time complexity: O(V + E)
     *
     * @param start starting vertex id
     * @return ordered list of visited vertex ids
     */
    public List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        // Enqueue the starting vertex and mark it visited
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);

            // Visit all unvisited neighbors
            for (int neighbor : adjList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return order;
    }

    /**
     * Depth-First Search starting from vertex 'start'.
     * Explores as far as possible along each branch before backtracking.
     * Uses an explicit stack (iterative) to avoid stack overflow on large graphs.
     * Time complexity: O(V + E)
     *
     * @param start starting vertex id
     * @return ordered list of visited vertex ids
     */
    public List<Integer> dfs(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            // Skip if already visited (may be pushed multiple times)
            if (visited.contains(current)) continue;

            visited.add(current);
            order.add(current);

            // Push neighbors in reverse order to maintain natural traversal direction
            List<Integer> neighbors = adjList.get(current);
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                if (!visited.contains(neighbors.get(i))) {
                    stack.push(neighbors.get(i));
                }
            }
        }
        return order;
    }

    public int vertexCount() { return vertices.size(); }
    public int edgeCount()   { return adjList.values().stream().mapToInt(List::size).sum(); }
}