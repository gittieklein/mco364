package digraphGraph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The DigraphMain demonstrates the shortest path of a graph
 * It expects a csv file through the command line
 * Each line should have the edges in the following format:
 * 		The first field of the record should be the starting vertex
 * 		The second field of the record should be the vertex the edge goes to
 * 		The third field of the record should have the distance from vertex A to vertex B
 * @author Gittie Klein
 *
 */
public class DigraphMain
{
	public static void main(String[] args) 
	{
		try
		{
			Scanner read = new Scanner(System.in);
			
			try
			{
				//read the file location
				read = new Scanner(new FileReader(args[0]));
			}
			catch (FileNotFoundException e)
			{
				System.out.println("File not found");
				System.out.println("Exiting programming...");
				System.exit(0);
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				System.out.println("File path not entered");
				System.out.println("Exiting programming...");
				System.exit(0);
			}
		
			//create a digraph 
			Digraph graph = new Digraph();
			
			//go through the rest of the file and set the edges
			String[] edge = null;	//this will be a temporary array to get the edges of the graph
			while (read.hasNextLine())
			{
				edge = (read.nextLine()).split(",");
				graph.setEdge(edge[0], edge[1], Integer.parseInt(edge[2]));
			}
			
			System.out.println(graph);
			
			Scanner input = new Scanner(System.in);
			System.out.print("Which vertex would you like to start with? ");
			String start = input.nextLine();
			System.out.println(graph.findShortestPath(start));
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("There's an error with memory locations");
			System.out.println("Please make sure all inserted data is correct");
			System.out.println("Exiting programming...");
			System.exit(0);
		}
		catch(NullPointerException e)
		{
			System.out.println("There is an error with the file you passed in or with the starting value.");
			System.out.println("Please restart the program.");
			System.out.println("Exiting programming...");
			System.exit(0);
		}
		catch (InputMismatchException e)
		{
			System.out.println("You entered the wrong type of data. The program is shutting down. Please restart"
					+ "the program to continue.");
			System.exit(0);
		}
		catch(StackOverflowError e)
		{
			System.out.println("Something took too long to process. The program is shutting down. "
					+ "Please restart the program to continue.");
			System.exit(0);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

	}
}
