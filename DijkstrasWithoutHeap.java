package prj1;

import java.util.ArrayList;

/**
 * The implementation of Dijkstras shortest path algorithm by using a simple
 * linear search to find the unvisited node with the minimum distance estimate
 * 
 * @author Clara McDaniel
 * @version 1.1
 */
public class DijkstrasWithoutHeap {
	private int n;
	private int[] distances;
	private boolean[] explored;
	private ArrayList<int[]>[] adj;
    /**
     * Constructor of the class
     * 
     * @param n:
     *            number of nodes of the graph
     * @param edges:
     *            the set of edges of the graph. Each row of 'edges' is in the
     *            form of [u, v, w], which means that there is an edge between u
     *            and v with weight w. So edges[i][0] and edges[i][1] are the
     *            end-points of the i-th edge and edges[i][2] is its weight
     */
    public DijkstrasWithoutHeap(int n, int[][] edges) {
        this.n = n;
        this.distances = new int[n];
        this.explored = new boolean[n];
        
      //working on adjacency list
        adj = new ArrayList[n];
        for(int i = 0; i < n; i++) {
        	adj[i] = new ArrayList<>();
        }
        
        for(int i = 0; i < edges.length; i++) {
        	int u = edges[i][0];
        	int v = edges[i][1];
        	int w = edges[i][2];
        	
        	int[] uNeigh = new int[2];
        	uNeigh[0] = v;
        	uNeigh[1] = w;
        	
        	int[] vNeigh = new int[2];
        	vNeigh[0] = u;
        	vNeigh[1] = w;
        	
        	adj[u - 1].add(uNeigh);
        	adj[v - 1].add(vNeigh);
        }
        
        //mark all vertices v as unexplored
        for(int i = 0; i < n; i++) {
        	explored[i] = false;
        }
        
        //filling the distances array with distances higher than any possible
        //distance that can be calculated
        for(int j = 0; j < n; j++) {
        	distances[j] = Integer.MAX_VALUE;
        }
        
    }


    /**
     * This method computes and returns the distances of all nodes of the graph
     * from the source node
     * 
     * @param source
     * 
     * @return an array containing the distances of the nodes of the graph from
     *         source. Element i of the returned array represents the distance
     *         of node i from the source
     */
    public int[] run(int source) {
    	//source vertex distance = 0
    	// 1 is subtracted because indices of distances[] are number 0 to n-1
    	distances[source - 1] = 0;
    	//iteratively
    	for(int i = 0; i < n - 1; i++) {
    		int smallestDist = Integer.MAX_VALUE;
    		int location = -1; // -1 will be returned if the node i is unreachable from source
    		
    		// find unexplored vertex u with the MINIMUM distance estimate
    		for (int j = 0; j < n; j++) {
    			if (!explored[j] && distances[j] < smallestDist) {
    				smallestDist = distances[j];
    				location = j;
    			}
    		}
    		
    		//check if the vertex is unreachable 
    		if (location == -1) {
    			break; // if yes, do not go further
    		}
    		
    		//if not, set the vertex as explored
    		explored[location] = true;
    		
    		//for any unexplored neighbor v of u, set distance[v] = min{distance[v], distance[u] + weight(u, v)}
    		//using a for each loop of adj list for simplicity 
    		for (int[] edge : adj[location]) {
    			//assign values for points 
    			 int v = edge[0];
    			 int w = edge[1];
    			//testing edge if beginning vertex is min location
    			if (!explored[v - 1]) {//ERROR ERROR ERROR
    				distances[v - 1] = Math.min(distances[v - 1], distances[location] + w);
    			}
    		}
    		
    		
    	}
    	//we now organize the distances array, seeing if any spots remained unreachable, and setting distance to -1
		for (int i = 0; i < n; i++) {
			if (distances[i] == Integer.MAX_VALUE) {
				distances[i] = -1;
			}
		}
    		
 
    		
        return distances;
    }

}
