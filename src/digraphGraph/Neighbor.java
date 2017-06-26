package digraphGraph;

/**
 * the neighbor class will be for the values in the graph
 * @author Gittie Klein
 *
 */
public class Neighbor
{
	private String vertex;
	private int distance;
	
	public Neighbor(String v, int d)
	{
		vertex = v;
		distance = d;
	}
	
	public String getVertex()
	{
		return vertex;
	}
	
	public int getDistance()
	{
		return distance;
	}
}
