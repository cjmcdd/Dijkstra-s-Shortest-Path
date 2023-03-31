package prj1;

import java.util.ArrayList;

/**
 * 
 * The implementation of Dijkstras shortest path algorithm by using
 * min-heaps
 * 
 * @author Enter your names here
 */
public class DijkstrasWithHeap {
	private int n;
	private MinHeap heap;
	private boolean[] explored;
	private int[] distances;
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
    public DijkstrasWithHeap(int n, int[][] edges) {
        this.n = n;
        heap = new MinHeap(n, 2);
        
        explored = new boolean[n];
        this.distances = new int[n];
        
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
    	distances[source - 1] = 0;
    	heap.insert(source, 0);
    	
    	//while heap is not empty
    	while(!heap.isEmpty()) {
    		int[] smallestDistNode = heap.extractMin();
    		
    		int idSmallest = smallestDistNode[0];
    		explored[idSmallest - 1] = true;
    		
    		//iteratively -- using for each loop for simplicity 
    		for(int[] edge : adj[idSmallest - 1]) {
    			//assign values for points 
    			int v = edge[0];
    			int w = edge[1];
    			
    			//testing edge if beginning vertex is min location
    			if (!explored[v -1]) {
    				distances[v - 1] = Math.min(distances[v - 1], distances[idSmallest - 1] + w);
    				if(heap.contains(v)) {
    					heap.decreaseKey(v, distances[v - 1]);
    				}else {
    					heap.insert(v, distances[v - 1]);
    				}
    				
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
