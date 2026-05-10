/**
 * Entry point for the Graph Traversal assignment.
 * Creates graphs of different sizes, runs BFS and DFS,
 * measures performance, and prints results.
 */
public class Main {

    public static void main(String[] args) {
        // -------------------------------------------------------
        // Part 1: Demonstrate graph structure on a small graph
        // -------------------------------------------------------
        System.out.println("=== Small Graph — Structure Demo ===");
        Graph smallGraph = new Graph();
        for (int i = 0; i < 10; i++) {
            smallGraph.addVertex(new Vertex(i));
        }
        // Add some edges (directed)
        int[][] edges = {
                {0,1},{0,2},{1,3},{1,4},{2,5},{3,6},{4,6},{5,7},{6,8},{7,9},{8,9},{2,9}
        };
        for (int[] e : edges) {
            smallGraph.addEdge(e[0], e[1]);
        }
        smallGraph.printGraph();
        System.out.println();

        // -------------------------------------------------------
        // Part 2: Run experiments on small / medium / large graphs
        // -------------------------------------------------------
        Experiment experiment = new Experiment();
        experiment.runMultipleTests();
        experiment.printResults();
    }
}