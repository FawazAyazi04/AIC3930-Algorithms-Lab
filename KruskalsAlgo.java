import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KruskalsAlgo {
    class Edge implements Comparable<Edge> {
        int src, dest, weight;

        Edge(int _src, int _dest, int _weight) {
            this.src = _src;
            this.dest = _dest;
            this.weight = _weight;
        }

        
        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    }

    class DisjointSet {
        List<Integer> rank;
        List<Integer> parent;

        public DisjointSet(int n) {
            rank = new ArrayList<>(n);
            parent = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                rank.add(0);
                parent.add(i); 
            }
        }

        public int findParent(int node) {
            if (node == parent.get(node)) {
                return node;
            }
            int ulp = findParent(parent.get(node));
            parent.set(node, ulp);
            return ulp; 
        }

        public void unionByRank(int u, int v) {
            int ulp_u = findParent(u);
            int ulp_v = findParent(v);
            if (ulp_u == ulp_v) {
                return;
            }
            if (rank.get(ulp_u) < rank.get(ulp_v)) {
                parent.set(ulp_u, ulp_v);
            } else if (rank.get(ulp_u) > rank.get(ulp_v)) {
                parent.set(ulp_v, ulp_u);
            } else {
                parent.set(ulp_v, ulp_u);
                int rankU = rank.get(ulp_u);
                rank.set(ulp_u, rankU + 1);
            }
        }
    }

    public int spanningTree(int v, ArrayList<ArrayList<ArrayList<Integer>>> adj) {
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < v; i++) {
            for (int j = 0; j < adj.get(i).size(); j++) {
                int adjNode = adj.get(i).get(j).get(0);
                int wt = adj.get(i).get(j).get(1);
                edges.add(new Edge(i, adjNode, wt));
            }
        }

        edges.sort(Edge::compareTo);
        DisjointSet ds = new DisjointSet(v);

        int mstWt = 0;
        List<Edge> mstEdges = new ArrayList<>();
        for (Edge edge : edges) {
            int u = edge.src;
            int v1 = edge.dest;
            int wt = edge.weight;

            if (ds.findParent(u) != ds.findParent(v1)) {
                mstWt += wt;
                ds.unionByRank(u, v1);
                mstEdges.add(edge);
                ds.unionByRank(u, v1);
            }
        }
        System.out.println("Edges in the Minimum Spanning Tree:");
        for (Edge edge : mstEdges) {
            System.out.println("Edge: " + edge.src + " - " + edge.dest + " with weight: " + edge.weight);
        }

        return mstWt;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of vertices:");
        int v = sc.nextInt();

        System.out.println("Enter the number of edges:");
        int e = sc.nextInt();

        ArrayList<ArrayList<ArrayList<Integer>>> adj = new ArrayList<>();

        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < e; i++) {
            System.out.println("Enter source, destination, and weight of edge " + (i+1) + ":");
            int src = sc.nextInt();
            int dest = sc.nextInt();
            int weight = sc.nextInt();

            ArrayList<Integer> edge1 = new ArrayList<>();
            edge1.add(dest);
            edge1.add(weight);
            adj.get(src).add(edge1);

            ArrayList<Integer> edge2 = new ArrayList<>();
            edge2.add(src);
            edge2.add(weight);
            adj.get(dest).add(edge2);
        }

        KruskalsAlgo kruskal = new KruskalsAlgo();
        int mstWeight = kruskal.spanningTree(v, adj);
        

        System.out.println("The weight of the Minimum Spanning Tree is: " + mstWeight);

        sc.close();
    }
}
