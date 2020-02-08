import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph {
    // Set of all nodes in a graph
    private LinkedList<Node> allNodes;
    //boolean for ifConnected, value changes when bfs is run
    private boolean connected = true;
    //array to keep track of which Nodes were visited for depth first.
    boolean[] visited;

    /**
     * Creates a graph keeping track of all its Nodes.
     * 
     * @param Textfile with all Nodes up to the max value in the textfile.
     */
    public Graph(File in) {
        try {
			allNodes = outputToNodes(in);
			allNodes.sort(null); //sorts the textfile in increasing order in case not sorted
			visited = new boolean[allNodes.size()]; //make visited boolean array
		} catch (IOException e) { //if there is an error in the file, catch it
			System.out.print("Error in File");
		}
    }
    
    /**
     * Method called in constructor of graph that reads in a file and converts the lines into a 
     * LinkedList of all the Nodes in (and not in) the file.
     * 
     * @param Takes in file
     * @returns a linked list of all the nodes
     * @throws IOException
     * 
     */
    public static LinkedList<Node> outputToNodes(File in) throws IOException {
        int max = 0; //largest number in text file
        Scanner read = new Scanner (in); //scanner to read in file
        LinkedList<Node> fullSet = new LinkedList<Node>(); //for storing nodes
        while (read.hasNext()) { //while there is another line to read in txtfile
        	String a = read.nextLine(); //a is the entire line in a text file
        	String[] cutoff = a.split(" "); //split the string into two strings at space
        	String numNode = cutoff[0]; //number for current node
        	String adjNum = cutoff[1]; //adjacent node to current node
        	if (max < Integer.parseInt(numNode)) { //set max to bigger 
        	    max = Integer.parseInt(numNode);
        	}
            if (max < Integer.parseInt(adjNum)) {  //set max to bigger 
                max = Integer.parseInt(adjNum);
            }
	        Node current = new Node (Integer.parseInt(numNode)); //left number is current node
	        Node adj = new Node (Integer.parseInt(adjNum)); //Node on the right side
        	if (!fullSet.contains(current)) { //if node does not exist, then add it
	        	fullSet.add(current);
        	} if (!fullSet.contains(adj)) {
	        	fullSet.add(adj);
        	}
        	int currInt = fullSet.indexOf(current); //get indices to then get the node from fullSet
        	int adjInt = fullSet.indexOf(adj);
        	Node curPr = fullSet.get(currInt);
        	Node adjPr = fullSet.get(adjInt);
        	if (!curPr.getAdjacent().contains(adjPr))  { //add to adjacency list if not already
        		curPr.getAdjacent().add(adjPr);
        	} if (!adjPr.getAdjacent().contains(curPr)) { //add to adjacent's adjacency list
            	adjPr.getAdjacent().add(curPr);
        	}
        }
        read.close(); //after read all the way through, close the file
        if (fullSet.size() != max) { //add missing nodes
            for (int i = 0; i < max; i++) {
                if (!fullSet.contains(new Node(i))) {
                    fullSet.add(new Node(i));
                }
            }
        }
        return fullSet;
    }
    
    /**
     * Implements breadthFirst algorithm on a graph object, returning an ordered list of the Nodes
     * starting at the starting node inputed into the algorithm and ending on the nodes at the
     * farthest frontiers or the disconnected nodes from the start.
     * 
     * @param start Node
     * @return list of nodes in order of breadth first search while updating if connected and
     * distance for each node
     */
    
    public LinkedList<Node> breadthFirst (Node start) {
    	boolean[] visited = new boolean[allNodes.size()]; //new visited to not interfere with dfs
    	start.setDist(0); //set distance of start to 0 as it is 0 nodes away from the start
    	LinkedList<Node> bfs = new LinkedList<Node>(); //linked list storing bfs order of nodes
    	LinkedList<Node> queue = new LinkedList<Node>(); //queue of adjacent nodes not seen
    	visited[start.getNodeNum()] = true; //already visited that node
    	queue.add(start); //start queue with start node
    	
    	while (queue.size() != 0) { //finish when queue is done
    		start = queue.poll(); //return first in queue and add it to bfs
    		bfs.add(start);
    		
    		Iterator<Node> iter = start.getAdjacent().listIterator(); //get all adj to start
    		while (iter.hasNext()) {
    			Node current = iter.next();
    			if (!visited[current.getNodeNum()]) { //add not visited nodes to queue to add later
    				visited[current.getNodeNum()] = true;
    				current.setDist(start.getDist() + 1); //set visited to 1 + dist of previous node
    				queue.add(current);
    			}
    		}
    	}
    	if (!allTrue(visited)) { //if bool array is not all true then its not connected
    	    connected = false;
    	}
    	bfs.addAll(breadthDisconnected(visited)); //get nodes in disconnected components to add them
    	return bfs;
    }
    
    /**
     * Recursively call to order nodes in bfs of each connected component separate from the start 
     * node, while adding them together to eventually add to the original connected component's 
     * linked list.
     * 
     * @param takes in bool array of visited array
     * @return linked list of nodes in bfs of connected components not in original connected comp
     */
    public LinkedList<Node> breadthDisconnected (boolean[] visited) {
        LinkedList<Node> bfs = new LinkedList<Node>();
        LinkedList<Node> queue = new LinkedList<Node>();
        if (allTrue(visited)) { //base case when all nodes have been visited
            return (new LinkedList<Node>()); //empty list to add for base case
        } else {
            //find first unused Node, mark it as visited, set its distance to 0, and add to queue
            int first = 0;
            Node first1;
            for(int i = 0; i < visited.length; i++) {
                if (visited[i] == false) {
                    first = i;
                    break;
                }
            }
            visited[first] = true;
            //set distance to max value as not connected (replacement for infinity)
            allNodes.get(first).setDist(Integer.MAX_VALUE);
            queue.add(allNodes.get(first));
            
            //add to bfs and queue, continue using normal bfs
            while (queue.size() != 0) {
                first1 = queue.poll();
                bfs.add(first1);
                
                Iterator<Node> iter = first1.getAdjacent().listIterator();
                while (iter.hasNext()) {
                    Node current = iter.next();
                    if (!visited[current.getNodeNum()]) {
                        visited[current.getNodeNum()] = true;
                        current.setDist(Integer.MAX_VALUE);
                        queue.add(current);
                    }
                }
            }
        }
        //recurse to other connected components until all true
        bfs.addAll(breadthDisconnected(visited));
        return bfs;
    }
    
    
    /**
     * Starts on a Node, and travels as deep into the branch, then uses recursion to unravel and 
     * go down different branches, until the visited is all true.
     * 
     * @param start Node
     * @return Linked List of nodes in order for dfs
     */
    public LinkedList<Node> depthFirst(Node start) {
    	LinkedList<Node> dfs = new LinkedList<Node>(); //dfs of nodes in start's connected comp
    	LinkedList<Node> fin = depthFirstRec(start, dfs); //final list of dfs of all nodes
    	//if disconnected, then dfs on other connected components or when not all visited
          if (!allTrue(visited)) {
              for(int i = 0; i < visited.length; i++) {
                  if (!visited[i]) { //recurse to other conn comp and add to fin
                      depthFirstRec(allNodes.get(i), fin);
                  }
              }
          }
    	return fin;
    }    
    
    /**
     * Recurse into a branch deeper until the last node has no more adjacent nodes and unwind from
     * the last node.
     * 
     * @param start Node
     * @param dfs linked list
     * @return
     */
    public LinkedList<Node> depthFirstRec (Node start, LinkedList<Node> dfs) {
    	visited[start.getNodeNum()] = true; //mark as visited
    	dfs.add(start); //add to dfs
    		
    	//get all adjacent and recurse deeper into each adjacent node until no more adjacent
    	Iterator<Node> iter = start.getAdjacent().listIterator();
    	while (iter.hasNext()) {
    		Node current = iter.next();
    		if (!visited[current.getNodeNum()]) {
    			depthFirstRec(current, dfs);
   			}
   		}
    	return dfs;
    }
    
    /**
     * Method is for determining if any false exist in a bool array for checking if all nodes were 
     * visited by bfs or dfs.
     * 
     * @param boolean array 
     * @return boolean value
     */
    private static boolean allTrue (boolean[] values) {
        for (boolean value : values) {
            if (!value)
                return false;
        }
        return true;
    }
    
    /**
     * Finds the maximum frontier by taking the max distance from start.
     * 
     * @param Linked list of all the nodes
     * @return int of maximum frontier
     */
    private static int maxFront(LinkedList<Node> allNode) {
        int front = 0;
        for (Node node : allNode) {
            if (node.getDist() > front && node.getDist() < Integer.MAX_VALUE) {
                front = node.getDist();
            }
        }
        return front;
    }
    
    /**
     * Finds the number of nodes within a certain distance away from the start node.
     * 
     * @param Linked list of all nodes
     * @param The distance we want all nodes within
     * @return number of nodes within distance
     */
    private static int nodesAt(LinkedList<Node> allNode, int dist) {
        int total = 0;
        for (Node node : allNode) {
            if (node.getDist() <= dist) {
                total++;
            }
        }
        return total;
    }
    
    public static void main (String[] args) throws IOException {
    	Graph graph = new Graph(new File ("facebook_combined.txt"));
    	Node start = graph.allNodes.get(1344);
//      Node start = graph.allNodes.get(40);
//  	Node end = graph.allNodes.get(1050);
        System.out.println("start " + start);
//    	System.out.println("end " + end);
    	System.out.println(graph.breadthFirst(start));
//    	System.out.print("distance from " + start + " = " + end.getDist()); 	
//    	System.out.print("Node is " + graph.allNodes.get(4000));
    	System.out.println("MAX " + maxFront(graph.allNodes));
        System.out.println("Nodes At " + nodesAt(graph.allNodes, 4));
//    	System.out.println("Distance " + allNodes.get(1050).getDist());
  //      System.out.println("Is connected  " + graph.connected);
//    	System.out.println("The node number is " + graph.allNodes.get(6));
//    	System.out.print("Adjacent to " + graph.allNodes.get(6).getAdjacent());	
    }
	
}
