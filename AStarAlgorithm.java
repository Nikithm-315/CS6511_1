package Program;


import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class AStarAlgorithm {
	 static int [][] direction = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
	 
	// Node to represent world configurations 
    private static class Node implements Comparable<Node> {
        int x, y; // to represent the current location
        int g,h,f;  // heuristic 
        Node parent; // to trace the path 

         // constructor 
        Node(int x, int y, Node parent, int g, int h) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.f, other.f);
        }
    }

    // admissible heuristic 
    private static int heuristicFunction(int x1, int y1, int x2, int y2) {
        // using Manhattan distance as the heuristic 
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static boolean aStarSearch(int[][] maze, int[] start, int[] end) {
    	// priority queue for A* Search 
    	PriorityQueue<Node> fringe = new PriorityQueue<>();

    	Node startNode = new Node(start[0], start[1], null, 0, heuristicFunction(start[0], start[1], end[0], end[1]));

    	// ClosedList: Create a set to keep track of visited nodes.
    	Set<String> visitedNodes = new HashSet<>();
    	
    	//Fringe : nodes generated but not yet expanded 
    	// Add the starting node to the fringe. 
    	fringe.add(startNode);

    	// Loop until the fringe is empty.
    	while (!fringe.isEmpty()) {
    	    // Get the node with the highest priority .
    	    Node currentNode = fringe.poll();

    	    // included the node into the closed list.
    	    visitedNodes.add(currentNode.x + "," + currentNode.y);

    	    // Checking for goal state
    	    if (currentNode.x == end[0] && currentNode.y == end[1]) {
    	        return true;
    	    }

    	    // Successor Function : getting all actions possible 
    	    for (int i=0;i<4;i++) {
    	        int x = currentNode.x + direction[i][0];
    	        int y = currentNode.y + direction[i][1];

    	        // boundary check and movement check .
    	        if (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
    	            // Create a successor node.
    	            Node successorNode = new Node(x, y, currentNode, currentNode.g + 1, heuristicFunction(x, y, end[0], end[1]));
    	            String nodeKey = x + "," + y;

    	            // Check if the successor node has not been visited and is not in the fringe.
    	            if (!visitedNodes.contains(nodeKey) && !fringe.contains(successorNode)) {
    	                fringe.add(successorNode);
    	            }
    	        }
    	    }
    	}

    	// goal not found 
    	return false;
    }

    public static int[][] fileReader(String filePath) throws IOException {
    	List<String> lines = Files.readAllLines(Paths.get(filePath));
        int numRows = lines.size();
        int numCols = lines.get(0).split(" ").length;
        int[][] maze = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            String[] values = lines.get(i).split(" ");
            for (int j = 0; j < numCols; j++) {
                maze[i][j] = Integer.parseInt(values[j]);
            }
        }

        return maze;
    }

    public static void main(String[] args) {
        try {
        	Scanner scanner = new Scanner(System.in);
           
            String fileName= "maze.txt";
            int[][] maze =fileReader(fileName);
            
            /*
            for(int i=0;i<81;i++)
            {
            	for(int j=0;j<81;j++)
            	{
            		System.out.print(maze[i][j]); 
            	}
            	System.out.println();
            }*/
            
            System.out.println(maze.length);
            System.out.println(maze[0].length);
            
            
            

            int[] start = new int[2];
            int[] end  = new int[2];
            
            for(int i=0;i<5;i++)
            {
            	start[0]= scanner.nextInt();
            	start[1] = scanner.nextInt();
            	
            	end[0] = scanner.nextInt();
            	end[1] = scanner.nextInt();

            boolean path = aStarSearch(maze, start, end);
              if(path)
              System.out.println("YES");
              else
            	  System.out.println("NO");  
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
