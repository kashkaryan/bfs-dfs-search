First, I created a Node class, where each node has a number (given in the textfile), a list of 
nodes they are adjacent to, and a variable called distance that keeps track of how far a node is
from the start node when we run BFS. The class also implements comparable in order for me to order
the nodes based on their number. When reading in the textfile in the graph class, I assumed that
1. All the edges that go from the left number to the right mean that both nodes are adjacent to 
each other, 2. That the largest number in the textfile is the largest number Node, 3. Every number
less than the largest number is a node that exists, 4. The first node is numbered zero, and 5. If a 
node is not in the textfile, it exists but is not adjacent to any other nodes. Reading in the file
returns a graph object that has a linked list of all the nodes in increasing order starting from 0
to the max. The graph also keeps track of whether it is connected (which gets updated in BFS) and
the list of nodes it has visited (which is used in DFS). In BFS, I start at a node, and add it to a
queue then the final bfs linked list which I return with the nodes in order, where I check the
nodes adjacent to the first and add to the queue to add to the final linked list. I also update the
distance of each node from the start value. Then, if all nodes are visited it is connected, else,
it repeats the process on a node not visited in a different connected component. For depth first
search, I recursively check deeper into each branch by recursively adding to the final dfs
linked list, saying we visited the node, then recursing into its adjacent nodes until the nodes 
don't have any other adjacent nodes. In order to run the bfs, create a graph object that takes
in a file, and then call graph.breadthFirst(start) where start is the node you want to start at.
In order to get the start node, use graph.allNodes.get(NUM); where NUM is the node number of where 
you want to start. After running bfs, you can then use maxFront(graph.allNodes)) to find the maximum
frontier of the graph. Also, you can use nodesAt(graph.allNodes, 4) where it finds the number
of nodes within a distance of 4 from the start node you used in bfs. Finally, we can access if 
the graph is connected by asking graph.connected after running bfs, and the distance of a node from
the start we used in bfs by using graph.getNode(NUM).getDist(). This can all be done in the main 
method to get the characteristics of the graph. Also, you can print the linked list that is returned
in bfs and dfs as I overrode the string method to print out the node's numbers. I assumed the
distance of a node that is not connected to the starting node in bfs is Integer.MAX_VALUE, instead 
of infinity, as I don't count these as having a distance from the starting node and also wanted to
keep the distance an integer and infinity in java is a double. Also, if a node is not connected,
I set its frontier to max value again as I assumed to not count these nodes as having a frontier.
Finally, each time you want to run bfs or dfs on your graph, create the graph using the file, then 
run dfs/bfs on the graph. If you want to run it again using the same or different algorithm, 
it would be preferable for the to user create another graph object with the same file then run the 
other algorithm they wanted to run.