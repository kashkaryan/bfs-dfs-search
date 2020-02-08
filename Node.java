import java.util.LinkedList;

public class Node implements Comparable<Node>{
	
	//identifier for node
    private int nodeNum;
    // The node's adjacency list
    private LinkedList<Node> adjacent;
    //distance from Node to start Node
    private int dist; 

    /**
     * Creates a node using its number and its adjacency list, then records its distance when bfs
     * is ran.
     * 
     * @param Node number
     */
    public Node(int nodeNum) {
        this.nodeNum = nodeNum;
        adjacent = new LinkedList<Node>();
    }
    
    //getter and setter for adjacency list
	public LinkedList<Node> getAdjacent() {
		return adjacent;
	}

	public void setAdjacent(LinkedList<Node> adjacent) {
		this.adjacent = adjacent;
	}
	
	//Node number getter
	public int getNodeNum() {
		return nodeNum;
	}
	
	//Help with printing Nodes for debugging and understanding algorithm
    @Override
    public String toString() {
        return "" + nodeNum;
    }
    
    //equality method for checking if two Nodes are equal
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Node)) {
            return false;
        }
        Node other = (Node) obj;
        return (nodeNum == other.getNodeNum());
    }

    //getter for distance
    public int getDist() {
        return dist;
    }

    //setter for distance
    public void setDist(int dist) {
        this.dist = dist;
    }

    //organize Nodes by their node number in increasing order
    @Override
    public int compareTo(Node o) {
        if (this.getNodeNum() == o.getNodeNum()) {
        return 0;
        } else if(this.getNodeNum() < o.getNodeNum()) {
            return -1;
        } else {
            return 1;
        }
    }
}
