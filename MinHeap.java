package prj1;

/**
 * In this class, we implement the d-ary min-heap data structure
 * 
 * @author Clara McDaniel
 *
 */
public class MinHeap {
    // The parameter d in the d-ary min-heap
    private int d;
    
    //The amount of values inserted into the heap
    private int size;

    // The array representation of the min-heap values
    private HeapNode[] minHeap;
    
    //This maps the id of a heap node to its index in the heap
    private int[] idToIndexMap;
    

    /**
     * Constructor
     * 
     * @param n:
     *            maximum number of elements in the min-heap at each time
     * @param d:
     *            parameter d in the d-ary min-heap
     */
    public MinHeap(int n, int d) {
    	//initializing heap and id index map
        
        this.minHeap = new HeapNode[n + 1];
        
        //initializing d-ary parameter and size
        this.d = d;
        size = 0;
        
        //initializing id map
        idToIndexMap = new int[n + 1];
        
    }


    /**
     * This method inserts a new element with "id" and "value" into the min-heap
     * 
     * @param id
     * @param value
     */
    public void insert(int id, int value) {
        minHeap[size + 1] = new HeapNode(id, value);
        idToIndexMap[id] = size + 1; 
        
        if(size != 0) {
        	floatUp(size + 1);
        }
        size++;
    }


    /**
     * This method extracts the min value of the heap
     * 
     * @return an array consisting of two integer elements: id of the minimum
     *         element and the value of the minimum element
     * 
     *         So for example, if the minimum element has id = 5 and value = 1,
     *         you should return the array [5, 1]
     */
    public int[] extractMin() {
    	int[] result = new int[2];
    	result[0] = minHeap[1].getId();
    	result[1] = minHeap[1].getValue();
    	
    	idToIndexMap[result[0]] = 0;
    	idToIndexMap[minHeap[size].getId()] = 1;
    	
    	minHeap[1] = minHeap[size];
    	minHeap[size] = null;
    	
        
    	size--;
    	sinkDown(1);
    	
        return result;
    }


    /**
     * This method takes an id and a new value newValue for the corresponding
     * node, and updates the data structure accordingly
     * 
     * @param id
     * @param newValue
     */
    public void decreaseKey(int id, int newValue) {
    	int index = idToIndexMap[id];
    	minHeap[index].setValue(newValue);
    	
    	floatUp(index);
    }
    
    /**
     * This method takes an index that is most likely violating the heap order by containing
     * a value that is lower than its parent, and sends it up wards to return to a heap state.
     * 
     * @param currIndex
     */
    private void floatUp(int currIndex) {
    	int parentLoc = (currIndex - 2) / d + 1;
    	while(minHeap[currIndex].getValue() < minHeap[parentLoc].getValue()) {
    		swapNodes(currIndex, parentLoc);
    		currIndex = parentLoc;
    		parentLoc = (currIndex - 2) / d + 1;
    		
    	}
    }
    
    /**
     * This method takes an index that is most likely violating the heap order by containing
     * a value that is higher than its children, and sends it downwards to return to heap state.
     * 
     * @param currIndex
     */
    private void sinkDown(int currIndex) {
    	int minChildIndex = getMinChild(currIndex);
    	
    	while((minChildIndex != -1) && minHeap[currIndex].getValue() > minHeap[minChildIndex].getValue()) {
    		swapNodes(currIndex, minChildIndex);
    		currIndex = minChildIndex;
    		minChildIndex = getMinChild(currIndex);
    	}
    }
    
   /**
    * This method swaps two heapNodes in the MinHeap array.
    * 
    * @param source
    * @param destination
    */
    private void swapNodes(int source, int destination) {
    	HeapNode tempVal = minHeap[source]; // t hold s
    	
    	idToIndexMap[minHeap[source].getId()] = destination;
    	idToIndexMap[minHeap[destination].getId()] = source;
    	
    	minHeap[source] = minHeap[destination]; // s = d (s gone)
    	minHeap[destination] = tempVal; // d = s
    	
    	
    }
    
    
    /**
     * This method finds the lowest possible index of a child of a HeapNode, as well as the highest possible
     * index of a child of a HeapNode, and iterates through them and their values to find the 
     * child with the lowest value. If the given HeapNode is calculated to have children out of bounds,
     * the value given is -1. 
     * 
     * @param currIndex
     * @return index of child with lowest value
     */
    private int getMinChild(int currIndex) {
    	int minValue = Integer.MAX_VALUE;
    	int minIndex = -1;
    	
    	int lowestChild = currIndex * d - d + 2;
    	int highestChild = currIndex * d + 1;
    	
    	if(lowestChild <= size ) {
    		for(int i = lowestChild; i <= highestChild && i <= size; i++) {
        		if((minHeap[i].getValue() < minValue)) {
           			minValue = minHeap[i].getValue();
        			minIndex = i;
        		}
    		}
    	}
    	
    	return minIndex;

    }


    /**
     * This method returns the array representation of heap
     * 
     * @return the array representation of heap
     */
    public int[] getHeap() {
    	int[] result = new int[size];
    	for(int i = 0; i < size; i++) {
    		result[i] = minHeap[i + 1].getValue();
    	}
    	return result;
    }


    /**
     * the toString method that returns a string with the values of the heap in
     * the array representation.
     * This method can help you find the issues of your code when you want to
     * debug.
     * 
     * @return string form of the array representation of heap
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("This iteration's tree: \n");
        for (int i = 0; i < this.minHeap.length; i++) {
        	if(minHeap[i] != null) {
        		sb.append(minHeap[i].getValue());
        		sb.append(' ');
        	}
          
        }
        return sb.toString();
    }

    /**
     * Checks if the monHeap is empty or not
     * @return true if empty, false if not
     */
	public boolean isEmpty() {
		if(this.size == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the minHeap contains an id
	 * @param id
	 * @return true if yes, false if not
	 */
	public boolean contains(int id) {
		if(idToIndexMap[id] != 0) {
			return true;
		}
		
		return false;
	}
    
    

}
