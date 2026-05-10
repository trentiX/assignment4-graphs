import java.util.List;

/**
 * Handles graph traversal experiments and performance analysis.
 */
public class Experiment {

    // Store results for later reporting
    private long[] bfsTimes;
    private long[] dfsTimes;
    private int[] sizes;

    public Experiment() {
        sizes    = new int[]{10, 30, 100};
        bfsTimes = new long[sizes.length];
        dfsTimes = new long[sizes.length];
    }

    /**
     * Run both BFS and DFS on the given graph, measure execution time.
     *
     * @param g     the graph to traverse
     * @param start starting vertex id
     * @param label label for output (e.g. "Small", "Medium", "Large")
     * @param printOrder whether to print the traversal order (for small graphs)
     */
    public void runTraversals(Graph g, int start, String label, boolean printOrder) {
        System.out.println("=== " + label + " Graph (" + g.vertexCount()
                + " vertices, " + g.edgeCount() + " edges) ===");

        // --- BFS ---
        long bfsStart = System.nanoTime();
        List<Integer> bfsOrder = g.bfs(start);
        long bfsEnd   = System.nanoTime();
        long bfsTime  = bfsEnd - bfsStart;

        // --- DFS ---
        long dfsStart = System.nanoTime();
        List<Integer> dfsOrder = g.dfs(start);
        long dfsEnd   = System.nanoTime();
        long dfsTime  = dfsEnd - dfsStart;

        if (printOrder) {
            System.out.println("  BFS order: " + bfsOrder);
            System.out.println("  DFS order: " + dfsOrder);
        }

        System.out.printf("  BFS time: %,d ns%n", bfsTime);
        System.out.printf("  DFS time: %,d ns%n", dfsTime);
        System.out.println();
    }

    /**
     * Run experiments on three graph sizes and store timing results.
     */
    public void runMultipleTests() {
        System.out.println("============================================");
        System.out.println("   GRAPH TRAVERSAL PERFORMANCE EXPERIMENTS ");
        System.out.println("============================================\n");

        for (int i = 0; i < sizes.length; i++) {
            int n = sizes[i];
            Graph g = buildGraph(n);
            String label = (n == 10) ? "Small" : (n == 30) ? "Medium" : "Large";

            // Warm-up run (JIT)
            g.bfs(0);
            g.dfs(0);

            // Timed run
            long bfsStart = System.nanoTime();
            g.bfs(0);
            bfsTimes[i] = System.nanoTime() - bfsStart;

            long dfsStart = System.nanoTime();
            g.dfs(0);
            dfsTimes[i] = System.nanoTime() - dfsStart;

            runTraversals(g, 0, label, n == 10);
        }
    }

    /**
     * Print a formatted summary table of all experiment results.
     */
    public void printResults() {
        System.out.println("============================================");
        System.out.println("         PERFORMANCE SUMMARY TABLE          ");
        System.out.println("============================================");
        System.out.printf("%-12s %15s %15s%n", "Graph Size", "BFS (ns)", "DFS (ns)");
        System.out.println("--------------------------------------------");
        String[] labels = {"10 vertices", "30 vertices", "100 vertices"};
        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("%-12s %,15d %,15d%n", labels[i], bfsTimes[i], dfsTimes[i]);
        }
        System.out.println("============================================");
    }

    /**
     * Build a graph with n vertices and a fixed edge pattern
     * (each vertex connects to next 3 vertices, wrapping around).
     */
    private Graph buildGraph(int n) {
        Graph g = new Graph();
        for (int i = 0; i < n; i++) {
            g.addVertex(new Vertex(i));
        }
        // Add edges: each vertex → next 3 (to ensure connectivity)
        for (int i = 0; i < n; i++) {
            for (int k = 1; k <= 3; k++) {
                int dest = (i + k) % n;
                if (dest != i) {
                    g.addEdge(i, dest);
                }
            }
        }
        return g;
    }
}