package digraph;

public class Digraph
{
	private Integer[][] graph;
	private String[] vertices;	//have a list of vertices which can be reference when the user passes in a vertex
	
	/**
	 * to initialize a digraph the user passes in an array of the vertices of the graph
	 * @param vertices
	 */
	public Digraph(String[] vertices)
	{
		graph = new Integer[vertices.length][vertices.length];
		this.vertices = vertices;	
	}
	
	/**
	 * set the distance of a path from one vertex to another
	 * @param a location 1
	 * @param b location 2
	 * @param distance from vertex1 to vertex2
	 */
	public boolean setEdge(String a, String b, int distance)
	{		
		//find the index for the locations the user passed in
		int v1 = findVertex(a);
		int v2 = findVertex(b);
		
		if(v1 == -1 || v2 == -1) return false;	//if either location is not in the locations array return false
		
		graph[v1][v2] = distance;
		return true;
	}
	
	/**
	 * find the index of the vertex in the array
	 * @param a
	 * @return the array index of the vertex or -1 if it is not in the graph
	 */
	private int findVertex(String a)
	{
		for(int i = 0; i < vertices.length; i++)
		{
			if(vertices[i].equals(a))
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * finds the shortest path from one vertex to every other vertex in the graph
	 * @param start the vertex the paths should start from
	 * @return an array of all the shortest path distances
	 */
	public String findShortestPath(String start)
	{
		//return null if the vertex the user wants to start with is not in the graph
		if(findVertex(start) == -1) return null;
		
		//the first column will hold the shortest path 
		//the second column will hold 1 is we know the path is the shortest possible path and 0 is not sure
		//0 is false and 1 is true
		//the third column will hold the previous vertex in the path
		int[][] shortestPath = new int[vertices.length][3];	
		
		//set the first column of the whole array to max_value 
		//and the second column to false (0)
		//set the previous element (col 3) to -1 because there is none
		for(int i = 0; i < shortestPath.length; i++)
		{
			shortestPath[i][0] = Integer.MAX_VALUE;
			shortestPath[i][1] = 0;
			shortestPath[i][2] = -1; 
		}
		
		int minIndex = findVertex(start);	//when starting the smallest distance is the starting vertex
		
		//set the distance from the starting vertex to itself to 0
		shortestPath[minIndex][0] = 0;
		shortestPath[minIndex][2] = minIndex;	
		
		//search for the smallest distance, set it to true and look at its children
		while(hasFalse(shortestPath))
		{
			//set the minIndex variable to = the first element in the graph that has not been finalized
			//this way you could find the min of not finalized vertices
			for(int i = 0; i <= shortestPath.length; i++)
			{
				if(shortestPath[i][1] == 0)
				{
					minIndex = i;
					break;
				}
			}
			
			//find the shortest path that wasn't finalized yet
			for(int i = shortestPath.length - 1; i >= 0; i--)
			{
				if(shortestPath[i][1] == 0)
				{
					if(shortestPath[i][0] < shortestPath[minIndex][0])
					{
						minIndex = i;
					}
				}
			}
			shortestPath[minIndex][1] = 1;
			
			//visit the neighbors of the node with the shortest path
			//go to the index in the array of the vertices with the shortest path and see all the vertices
			//it has edges to
			for(int i = 0; i < graph[minIndex].length; i++)
			{
				if(graph[minIndex][i] == null){}
				else
				{
					//if there is an edge, check if the path is shorter than the existing path
					//and if it is shorter set the new shortest path for that vertex
					//the new path is the path of the minIndex plus the distance of their connection edge
					if(shortestPath[minIndex][0] + graph[minIndex][i] < shortestPath[i][0])
					{
						shortestPath[i][0] = shortestPath[minIndex][0] + graph[minIndex][i];
						shortestPath[i][2] = minIndex;
					}
				}
			}
		}	
		
		StringBuilder sp = new StringBuilder();
		sp.append(String.format("%-10s%-10s%-10s", "Vertex", "Distance", "Prev"));
		for(int i = 0; i < shortestPath.length; i++)
		{			
			sp.append(String.format("%n%-10s%-10d%-10s", vertices[i], shortestPath[i][0] == Integer.MAX_VALUE ? "unreachable" : shortestPath[i][0], 
																vertices[shortestPath[i][2]]));
		}
		return sp.toString();
	}
	
	/**
	 * checks if the array passed in contains any elements equal to false (0) in the second column
	 * used when checking for the shortest path
	 * @param shortestPath
	 * @return true or false if the array contains any false elements
	 */
	private boolean hasFalse(int[][] shortestPath)
	{
		for(int i = 0; i < shortestPath.length; i++)
			if (shortestPath[i][1] == 0 && shortestPath[i][0] != Integer.MAX_VALUE) return true;
		return false;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%8s", " "));
		for(String v: vertices)
			sb.append(String.format("%-8s", v));
		sb.append("\n");
		for(int row = 0; row < graph.length; row++)
		{
			sb.append(String.format("%-8s", vertices[row]));
			for(int col = 0; col < graph[0].length; col++)
			{
				sb.append(String.format("%-8s", graph[row][col] == null ? "-" : graph[row][col]));
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
