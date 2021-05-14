package rxg190006;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Hashtable;

import rxg190006.Graph.Vertex;
import rxg190006.Graph.Edge;
import rxg190006.Graph.Factory;
import rxg190006.Graph.GraphAlgorithm;
import rxg190006.Graph.Timer;

import rxg190006.DFS.*;

public class EulerPath {
    Vertex start;
    List<Vertex> tour;

    public void printCircuit(Graph g) {
        for (Vertex u : g) {
            for (Edge e: g.adj(u).outEdges) {

            }
        }
    }
}
