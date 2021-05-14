/** Starter code for LP2
 *  @author rbk ver 1.0
 *  @author SA ver 1.1
 */

// change to your netid
package rxg190006;

import rxg190006.Graph.Vertex;
import rxg190006.Graph.Edge;
import rxg190006.Graph.Factory;
import rxg190006.Graph.GraphAlgorithm;
import rxg190006.Graph.Timer;
import rxg190006.Graph.AdjList;

import java.util.*;


public class Euler extends GraphAlgorithm<Euler.EulerVertex> {
    static int VERBOSE = 1;
    Vertex start;
    List<Vertex> tour;

    // You need this if you want to store something at each node
    static class EulerVertex implements Factory {
        boolean visited;

        EulerVertex(Vertex u) {
            visited = false;
        }
        public EulerVertex make(Vertex u) { return new EulerVertex(u); }

    }

    // To do: function to find an Euler tour
    public Euler(Graph g, Vertex start) {
        super(g, new EulerVertex(null));
        this.start = start;


        tour = new LinkedList<>();
    }

    /* To do: test if the graph is Eulerian.
     * If the graph is not Eulerian, it prints the message:
     * "Graph is not Eulerian" and one reason why, such as
     * "inDegree = 5, outDegree = 3 at Vertex 37" or
     * "Graph is not strongly connected"
     */
    public boolean isEulerian() {
        //in - out == 1 ; For one
        //out - in == 1; For one
        //in == out; For all others
        int oneEdgeCount = 0;
        boolean equalEdges = false;

        Graph g = this.graph;
        System.out.println("g.size() >> " + g.size());
        for (int i = 1; i<=g.size();i++) {
//            err = "Graph is not Eulerian. inDegree = " + graph.getVertex(i).inDegree() + " outDegree = " + graph.getVertex(i).outDegree() + " at Vertex " + graph.getVertex(i);

            if ((g.getVertex(i).outDegree() - g.getVertex(i).inDegree()) == 1) {
                oneEdgeCount += 1;
            }
            else if ((g.getVertex(i).inDegree() - g.getVertex(i).outDegree()) == 1) {
                oneEdgeCount += 1;
            }
            else if (g.getVertex(i).inDegree() == g.getVertex(i).outDegree()) {
                equalEdges = true;
            }
            else {
                equalEdges = false;
                System.out.printf("Graph is not Eulerian. inDegree = %d, outDegree = %d at Vertex %d", graph.getVertex(i).inDegree(), graph.getVertex(i).inDegree(), graph.getVertex(i));
                break;
            }
        }
        if (equalEdges) {
            System.out.println("Graph is Eulerian.");
            return true;
        }
        return false;
    }

    /*public Vertex findStartNode() {
        Graph g = graph;
        int startNode = 0;
        for (int i = 1; i<=g.size();i++) {
            if ((g.getVertex(i).outDegree() - g.getVertex(i).inDegree()) == 1 || (g.getVertex(i).outDegree() > 0))
                return g.getVertex(i);
        }
        return g.getVertex(0);
    }*/

    /*// creating a My HashTable Dictionary
    Hashtable<Integer, Graph.AdjList> edge_count = new Hashtable<Integer, Graph.AdjList>();

        for (edge : this.graph.adjList) {
        edge_count.put(1, edge);
    }
        if (this.graph.adjList.length == 0) {
        return;
    }

    //Maintain a stack to keep vertices
    ArrayList<Integer> curr_path = new ArrayList<Integer>();

    //vector to store final circuit
    ArrayList<Integer> circuit = new ArrayList<Integer>();

    //start from any vertex
        curr_path.add(0);
    Integer curr_v = 0; // Current vertex
    Integer next_v = 0; // Next vertex

        while (curr_path.size() != 0) {
        if (edge_count.get(curr_v) != null) {
            //Push the vertex
            curr_path.add(curr_v);

            //Find the next vertex using an edge
            next_v = this.graph.adjList[curr_v][-1];

            //and remove that edge
            edge_count[curr_v] -= 1
            adj[curr_v].pop()

            //Move to next vertex
            curr_v = next_v;
        }
    }*/

    public List<Vertex> findEulerTour() {
        if(!isEulerian()) { return new LinkedList<Vertex>(); }

        //Graph is Eulerian...find the tour and return tour
        for (Vertex u : graph) {
            start = u;
            System.out.println("In for");
            if (u.outDegree() != 0 || u.inDegree() != 0) {
                findEulerianTour(u);
            }
        }
        System.out.println(tour);
        return tour;
    }

    public List<Vertex> findEulerianTour(Vertex u) {
            for (Edge e : graph.adj(u).outEdges) {
                System.out.println("In for edge");
                graph.adj(u).outEdges.remove(e);

                Vertex v = e.otherEnd(u);
                if (v.outDegree() != 0) {
                    findEulerianTour(v);
                }

            }
            tour.add(0, u);
            get(u).visited = true;
        return tour;
    }

    public static void main(String[] args) throws Exception {
        Scanner in;
        if (args.length > 1) {
            in = new Scanner(System.in);
        } else {
            String input = "9 13 1 2 1 2 3 1 3 1 1 3 4 1 4 5 1 5 6 1 6 3 1 4 7 1 7 8 1 8 4 1 5 7 1 7 9 1 9 5 1";
            input = "9 10  1 2 1  1 6 1  2 3 2  3 1 1  3 4 1  4 5 1  5 6 1  6 1 1  6 1 2  7 5 1 1";

//            input = "9 10  1 2 1  2 3 1  2 6 2  4 2 1  4 5 1  5 7 1  5 8 1  6 4 1 6 5 2  7 6 1  8 4 1 1";
            in = new Scanner(input);
        }
        int start = 1;
        if(args.length > 1) {
            start = Integer.parseInt(args[1]);
        }
        // output can be suppressed by passing 0 as third argument
        if(args.length > 2) {
            VERBOSE = Integer.parseInt(args[2]);
        }
        Graph g = Graph.readDirectedGraph(in);
        Vertex startVertex = g.getVertex(start);
        Timer timer = new Timer();

        Euler euler = new Euler(g, startVertex);
        List<Vertex> tour = euler.findEulerTour();

        timer.end();
        if(VERBOSE > 0) {
            System.out.println("Output:");
            System.out.println(tour);
            // print the tour as sequence of vertices (e.g., 3,4,6,5,2,5,1,3)
            //1, 7, 5, 6, 1, 0, 3, 4, 5, 3, 1
            System.out.println();
        }
        System.out.println(timer);
    }

    public void setVerbose(int ver) {
        VERBOSE = ver;
    }
}