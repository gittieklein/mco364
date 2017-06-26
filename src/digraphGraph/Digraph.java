package digraphGraph;

import java.util.HashMap;
import java.util.LinkedList;

public class Digraph
{
	//create a map for the grid - it should be a string that point to a linked list
	private HashMap<String, LinkedList<Neighbor>> graph;
	
	/**
	 * to initialize a digraph the user passes in an array of the vertices of the graph
	 * @param vertices
	 */
	public Digraph()
	{
		graph = new HashMap<String, LinkedList<Neighbor>>();
	}
	
	/**
	 * set the distance of a path from one vertex to another
	 * @param a location 1
	 * @param b location 2
	 * @param distance from vertex1 to vertex2
	 */
	public void setEdge(String a, String b, int distance)
	{		
		//create a new neighbor and add it to the list for the vertx it's neighbors with
		LinkedList<Neighbor> list = graph.get(a);
		
		//if the vertex is not in the list, then add the vertex
		if(list == null)
		{
			graph.put(a, new LinkedList<Neighbor>());
			list = graph.get(a);
		}
		
		//check if the b, the vertex you are going to, is in the graph, if not add it
		if(graph.get(b) == null)
			graph.put(b,  new LinkedList<Neighbor>());
		
		//add the new neighbor to the list
		list.add(new Neighbor(b, distance));
	}
	
	/**
	 * finds the shortest path from one vertex to every other vertex in the graph
	 * @param start the vertex the paths should start from
	 * @return an array of all the shortest path distances
	 */
	public String findShortestPath(String start)
	{
		//return null if the vertex the user wants to start with is not in the graph
		if(graph.get(start) == null) return "The starting vertex is not in the graph";
		
		//create an new map for shortest path grid 
		HashMap<String, ShortestPath> path = new HashMap<String, ShortestPath>();
		
		//loop through the hash map and add all the vertices to the path 
		for(String x: graph.keySet())
		{
			path.put(x, new ShortestPath());
		}
		
		//set the distance from the starting vertex to itself to 0
		path.get(start).setDistance(0);
		//keep the previous null because there is none

		int minValue;
		String minVertex = null;
		//search for the smallest distance, set it to true and look at its children
		while(hasFalse(path))
		{
			minValue = Integer.MAX_VALUE;
			
			//find the smallest value that is not set to true
			for(String v: path.keySet())
			{
				if(!(path.get(v).isFinished()) && path.get(v).getDistance() < minValue)
				{
					minValue = path.get(v).getDistance();					
					minVertex = v;
				}
			}
			
			//the vertex with the smallest vertex should be set to true
			path.get(minVertex).setFinished(true);
			
			//loop through the neighbors
			//check if the path is shorter than the existing path
			//and if it is shorter set the new shortest path for that vertex
			//the new path is the path of the minValue plus the distance of their connection edge
			for(Neighbor n: graph.get(minVertex))
			{
				if(!path.get(n.getVertex()).isFinished())
					if(path.get(minVertex).getDistance() + n.getDistance() < path.get(n.getVertex()).getDistance())
					{
						path.get(n.getVertex()).setDistance(path.get(minVertex).getDistance() + n.getDistance());
						path.get(n.getVertex()).setPrev(minVertex);
					}
				
			}
		}				
		
		StringBuilder sp = new StringBuilder();
		sp.append(String.format("%-10s%-14s%-10s", "Vertex", "Distance", "Prev"));
		for(String element: path.keySet())
		{			
			sp.append(String.format("%n%-10s%-14s%-10s", element, 
					path.get(element).getDistance() == Integer.MAX_VALUE ? "unreachable" : path.get(element).getDistance(), 
					path.get(element).getPrev() == null ? "-" : path.get(element).getPrev()));
		}
		return sp.toString();
	}
	
	/**
	 * checks if the array passed in contains any elements equal to false (0) in the second column
	 * used when checking for the shortest path
	 * @param path
	 * @return true or false if the array contains any false elements
	 */
	private boolean hasFalse(HashMap<String, ShortestPath> path)
	{
		for(String v: path.keySet())
			if (!path.get(v).isFinished() && path.get(v).getDistance() != Integer.MAX_VALUE) return true;
		return false;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(String v: graph.keySet())
		{
			sb.append(String.format("%-8s", v));
			for(Neighbor n: graph.get(v))
			{
				sb.append(String.format("%8s %s - %d\t", "->", n.getVertex(), n.getDistance()));
			}
			sb.append("\n");
		}
		
		return sb.toString();

	}
	

	/**
	 * This class is used as a table when trying to find the shortest algorithm
	 * With each vertex, we need to know what the vertex is, the distance from the starting node, 
	 * if we know it's the shortest and the previous vertex
	 * @author Gittie Klein
	 *
	 */
	private class ShortestPath
	{
		private int distance;
		private boolean finished;
		private String prev;
		
		public ShortestPath()
		{
			distance = Integer.MAX_VALUE;
			finished = false;
		}
	
		public int getDistance()
		{
			return distance;
		}
	
		public void setDistance(int distance)
		{
			this.distance = distance;
		}
	
		public boolean isFinished()
		{
			return finished;
		}
	
		public void setFinished(boolean finished)
		{
			this.finished = finished;
		}
	
		public String getPrev()
		{
			return prev;
		}
	
		public void setPrev(String prev)
		{
			this.prev = prev;
		}
	}

}
