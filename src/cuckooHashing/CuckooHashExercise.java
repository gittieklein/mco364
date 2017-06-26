package cuckooHashing;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CuckooHashExercise
{
	public static void main(String[] args)
	{
		try
		{
			CuckooHashTable<Integer> table = new CuckooHashTable<>();
	
			menu(table);
		}
		catch (InputMismatchException e)
		{
			System.out.println("You entered the wrong type of data. The program is shutting down. Please restart"
					+ "the program to continue.");
		}
		catch(NullPointerException e)
		{
			System.out.println("There is no reference for given data. The program is shutting down. Please restart"
					+ " the program to continue.");
		}
		catch(StackOverflowError e)
		{
			System.out.println("Something took too long to process. The program is shutting down. "
					+ "Please restart the program to continue.");
		}
		catch (Exception e)
		{
			System.out.println("Oops! Something is not right. Plase restart the program to try again");
		}

	}

	private static void menu(CuckooHashTable<Integer> table)
	{
		Scanner input = new Scanner(System.in);
		
		int select;
		do
		{
			System.out.println("\n1.Insert Value\n2.Remove Value\n3.Display Table\n4.Get Position of Value\n5.Display elements occupied, number of rehashes and capacity\n6.Exit application");
			select = input.nextInt();

			Integer value;
			boolean flag;
			
			switch (select)
			{
				case 1:
					System.out.print("Which value would you like to insert? ");
					value = input.nextInt();
					flag = table.insert(value);
					if (flag)
						System.out.println("\n" + value + " has been added to the tree");
					else
						System.out.println("\n" + value + " was not added to the tree");
					break;
				case 2:
					System.out.print("Which value would you like to remove? ");
					value = input.nextInt();
					flag = table.remove(value);
					if (flag)
						System.out.println("\n" + value + " has been removed from the tree");
					else
						System.out.println("\n" + value + " is not in the tree");
					break;
				case 3:
					System.out.println(table.toString());
					break;
				case 4:
					System.out.print("Of which value would you like to get the table location? ");
					value = input.nextInt();
					System.out.println("\n" + table.getPosition(value));
					break;
				case 5:
					System.out.printf("Number of elements occupied: %d%nNumber of rehashes: %d%nTable capacity: %d each (Total: %d)%n", 
								table.getOccupied(), table.getRehashes(), table.getCapacity(), table.getCapacity()*2);	
					break;
				case 6:
					System.out.println("Exiting application...");
					System.exit(0);
					break;
			}

		} while (select != 6);
	}
}
