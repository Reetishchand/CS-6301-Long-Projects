/**
 * Team members:
 * Reetish Chand Guntakal Patil(RXG190006)
 * Rohan Vannala(RXV190003)
 * */
package rxg190006;
// YET TO REFACTOR

import rxg190006.Graph.Vertex;
import rxg190006.Graph.Edge;
import rxg190006.Graph.GraphAlgorithm;
import rxg190006.Graph.Factory;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
    private int upperbound;
     static boolean hasCycle;
    private LinkedList<Vertex> treeList;
     int ccCount;

    public static class DFSVertex implements Factory {
        boolean isVisited;
        int start;
        int cno;
        Vertex parent;
        public DFSVertex(Vertex u) {
            this.start = 0;
            this.parent = null;
            this.cno = 0;
            this.isVisited = false;
        }
        public DFSVertex make(Vertex u) {
            return new DFSVertex(u);
        }
    }

    public DFS(Graph graph) {
        super(graph, new DFSVertex(null));
        ccCount =0;
        hasCycle = false;
    }

    public static DFS depthFirstSearch(Graph graph) {
        DFS d = new DFS(graph);
        d.dfsHelper();
        return d;
    }

    public void dfsHelper() {
        upperbound = graph.size();
        treeList = new LinkedList<Vertex>();
        for (Vertex vertex : graph) {
            get(vertex).isVisited = false;
            get(vertex).start = 0;
            get(vertex).parent = null;
        }
        for (Vertex vertex : graph) {
            if (!get(vertex).isVisited) {
                ccCount+=1;
                get(vertex).cno = ccCount;
                checkVisited(vertex);
            }
        }
    }

    private void checkVisited(Vertex vertex) {
        get(vertex).isVisited = true;
        for (Edge edge : graph.incident(vertex)) {
            Vertex edgeVertex = edge.otherEnd(vertex);
            if (!get(edgeVertex).isVisited) {
                get(edgeVertex).parent = vertex;
                get(edgeVertex).cno = get(vertex).cno;
                checkVisited(edgeVertex);
            } else {
                if (get(edgeVertex).start == 0) {
                    hasCycle = true;
                }
            }
        }
        get(vertex).start = upperbound;
        upperbound-=1;
        treeList.addFirst(vertex);
    }

    public List<Vertex> topologicalOrder1() {
        dfsHelper();
        if(hasCycle) {
            return null;
        }
        return treeList;
    }

    public int connectedComponents() {
        dfsHelper();
        return ccCount;
    }

    public int cno(Vertex vertex) {
        return get(vertex).cno;
    }

    public static void main(String[] args) throws Exception {
        String string = "9 13 1 2 1 2 3 1 3 1 1 3 4 1 4 5 1 5 6 1 6 3 1 4 7 1 7 8 1 8 4 1 5 7 1 7 9 1 9 5 1";
        Scanner in;
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
        Graph graph = Graph.readGraph(in);
        graph.printGraph(false);

        DFS helper = new DFS(graph);
        int connectedComponents = helper.connectedComponents();
        System.out.println("No. of connected components in the graph: " + connectedComponents + "\nu\tcno");
        for (Vertex vertex : graph) {
            System.out.println(vertex + "\t" + helper.cno(vertex));
        }
        System.out.println("");

        System.out.println("Output of Dfs:\nNode\ttop\tParent\n----------------------");
        for (Vertex vertex : graph) {
            System.out.println(vertex + "\t" + helper.get(vertex).start + "\t" + helper.get(vertex).parent);
        }
        System.out.println("");
        System.out.println("Output of topological order\n-----------------------------");
        List<Vertex> sortList = helper.topologicalOrder1();
        if(sortList==null) {
            System.out.println("Graph is cyclic. No topological order exists");
        }else {
            for(Vertex vertex: sortList) {
                System.out.print(vertex+"\t");
            }
            System.out.println("");
        }
    }
}
