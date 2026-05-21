/**
 * Entry point — demonstrates BFS, DFS, and Dijkstra's algorithm.
 */
public class Main {

    public static void main(String[] args) {

        // -------------------------------------------------------
        // Part 1: Small graph — BFS and DFS demo
        // -------------------------------------------------------
        System.out.println("=== Small Graph — BFS / DFS Demo ===");
        Graph g1 = new Graph();
        for (int i = 0; i < 10; i++) g1.addVertex(new Vertex(i));

        int[][] edges = {
                {0,1},{0,2},{1,3},{1,4},{2,5},{3,6},{4,6},{5,7},{6,8},{7,9},{8,9},{2,9}
        };
        for (int[] e : edges) g1.addEdge(e[0], e[1]);
        g1.printGraph();

        System.out.println("\nBFS from 0: " + g1.bfs(0));
        System.out.println("DFS from 0: " + g1.dfs(0));

        // -------------------------------------------------------
        // Part 2: Dijkstra demo on a weighted graph
        // -------------------------------------------------------
        System.out.println("\n=== Dijkstra — Weighted Graph Demo ===");
        Graph g2 = new Graph();
        for (int i = 0; i < 6; i++) g2.addVertex(new Vertex(i));

        // Weighted directed edges
        g2.addEdge(0, 1, 4);
        g2.addEdge(0, 2, 1);
        g2.addEdge(2, 1, 2);
        g2.addEdge(1, 3, 1);
        g2.addEdge(2, 3, 5);
        g2.addEdge(3, 4, 3);
        g2.addEdge(4, 5, 2);
        g2.addEdge(0, 5, 10);

        g2.printGraph();
        System.out.println();
        g2.dijkstra(0);

        // -------------------------------------------------------
        // Part 3: Performance experiments
        // -------------------------------------------------------
        System.out.println();
        Experiment experiment = new Experiment();
        experiment.runMultipleTests();
        experiment.printResults();
    }
}