/**
 * Team members:
 * Reetish Chand Guntakal Patil(RXG190006)
 * Rohan Vannala(RXV190003)
 * */


//FIX errors
package rxg190006;
import rxg190006.Graph;
import rxg190006.Graph.Vertex;
import rxg190006.Graph.Edge;
import rxg190006.Graph.GraphAlgorithm;
import rxg190006.Graph.Factory;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class PERT extends GraphAlgorithm<PERT.PERTVertex> {
	Vertex source;
	int timeInSeconds;
	Vertex target;
	int numOfCriticalNodes;
	Graph g;
    public static class PERTVertex implements Factory {
    	
    	boolean isCritical;
    	int upperBound;
    	int lowerBound;
    	int time;
    	int slow;
    	
	public PERTVertex(Vertex vertex) {
		this.slow = 0;
		this.isCritical = false;
		this.upperBound = 0;
	}
	
	public PERTVertex make(Vertex vertex) { 
		return new PERTVertex(vertex); 
		}
    }

    public PERT(Graph graph) {
		super(graph, new PERTVertex(null));
		this.source = graph.getVertex(1);
		this.timeInSeconds =0;
		this.target = graph.getVertex(graph.size());
		this.numOfCriticalNodes = 0;
		
    }

    public void setDuration(Vertex vertex, int time) {
		get(vertex).time = time;
    }
    
    public boolean pert() {
    	
		DFS d = new DFS(g);
		List<Vertex> orderedGraph = d.topologicalOrder1();
		if(null==orderedGraph){
			return false;
		}
		for( Vertex vertex : orderedGraph){
		    for(Edge edge : g.incident(vertex)){
                Vertex temp = edge.otherEnd(vertex);
                if( get(temp).upperBound < get(temp).time  + get(vertex).upperBound  ){
                    get(temp).upperBound += get(temp).time;
                }
            }
        }
        timeInSeconds = get(target).upperBound;
		for(Vertex vertex : g){
		    get(vertex).lowerBound = timeInSeconds;
        }

        ListIterator<Vertex> orderedGraphReversed = orderedGraph.listIterator(orderedGraph.size());
        
        while(orderedGraphReversed.hasPrevious()){
            Vertex vertex = orderedGraphReversed.previous();
            for( Edge edge : g.incident(vertex)){
                Vertex v = edge.otherEnd(vertex);
                if(get(vertex).lowerBound> get(v).lowerBound - get(v).time ){
                    get(vertex).lowerBound -= get(v).time;
                }
            }
            PERTVertex current = get(vertex);
            current.slow = current.lowerBound - current.upperBound;
            if(0==current.slow){
                numOfCriticalNodes+=1;
                get(vertex).isCritical = true;
            }
        }
		return true;
    }
    
    public int ec(Vertex vertex) {
	    return get(vertex).upperBound;
    }

    public int lc(Vertex vertex) {
        return get(vertex).lowerBound;
    }

    public int slack(Vertex vertex) {
	    return get(vertex).slow;
    }

    public int criticalPath() {
	    return timeInSeconds;
    }

    public boolean critical(Vertex vertex) {
	    return get(vertex).isCritical;
    }

    public int numCritical() {
	    return numOfCriticalNodes;
    }

    public static PERT pert(int[] time, Graph graph) {
		PERT pert = new PERT(graph);
        for(Vertex vertex: graph) {
            pert.setDuration(vertex, time[vertex.getIndex()]);
            if(1!=vertex.getName()){
                graph.addEdge(pert.source.getIndex(),vertex.getIndex(),0);
            }
            if(vertex.getName() != graph.size()){
                graph.addEdge(vertex.getIndex(),pert.target.getIndex(),0);
            }

        }
        if(pert.pert()){
            return pert;
        }else{
            return null;
        }
    }
    
    public static void main(String[] args) throws Exception {
	String graph = "11 12   2 4 1   2 5 1   3 5 1   3 6 1   4 7 1   5 7 1   5 8 1   6 8 1   6 9 1   7 10 1   8 10 1   9 10 1      0 3 2 3 2 1 3 2 4 1 0";
	Scanner in;
	// If there is a command line argument, use it as file from which
	// input is read, otherwise use input from string.
	in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(graph);
	Graph g = Graph.readDirectedGraph(in);
	g.printGraph(false);
	
	PERT p = new PERT(g);
	for(Vertex u: g) {
	    p.setDuration(u, in.nextInt());
	}
	// Run PERT algorithm.  Returns null if g is not a DAG
	if(p.pert()) {
	    System.out.println("Invalid graph: not a DAG");
	} else {
	    System.out.println("Number of critical vertices: " + p.numCritical());
	    System.out.println("u\tEC\tLC\tSlack\tCritical");
	    for(Vertex vertex: g) {
		System.out.println(vertex + "\t" + p.ec(vertex) + "\t" + p.lc(vertex) + "\t" + p.slack(vertex) + "\t" + p.critical(vertex));
	    }
	}
    }
}