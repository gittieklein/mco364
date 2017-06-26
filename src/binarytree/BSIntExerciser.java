package binarytree;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class BSIntExerciser
{
	public static void main(String[] args)
	{
		try
		{
			BSIntTree tree = new BSIntTree();

			Scanner read = null;
			Scanner input = new Scanner(System.in);

			try
			{
				read = new Scanner(new FileReader(args[0]));
			}
			catch (FileNotFoundException e)
			{
				System.out.println("File not found");
				System.out.println("Exiting programming...");
				System.exit(0);
			}

			while (read.hasNextInt())
			{
				tree.insert(read.nextInt());
			}

			System.out.println("\nSTART TREE");
			ArrayList<Integer> traverse = tree.traverseInOrder();
			for (int val : traverse)
			{
				System.out.print(val + " ");
			}
			System.out.println();
			
			menu(tree, input);
		}
		catch (Exception e)
		{
			System.out.println("Oops! There's an error. The program is shutting down...");
		}

	}

	private static void menu(BSIntTree tree, Scanner input)
	{
		ArrayList<Integer> traverse;
		int key;
		Integer value = null;
		boolean flag;
		do
		{
			System.out.println("\n1.Insert Node\n2.Remove Node\n3.Exit application");
			key = input.nextInt();

			switch (key)
			{
				case 1:
					System.out.print("Which value would you like to insert? ");
					value = input.nextInt();
					flag = tree.insert(value);
					if (flag)
						System.out.println("\n\n" + value + " has been added to the tree");
					else
						System.out.println("\n\n" + value + " is already in the tree and was not added again");
					break;
				case 2:
					System.out.print("Which value would you like to remove? ");
					value = input.nextInt();
					flag = tree.remove(value);
					if (flag)
						System.out.println("\n\n" + value + " has been removed from the tree");
					else
						System.out.println("\n\n" + value + " is not in the tree");
					break;
				case 3:
					System.out.println("Exiting application...");
					System.exit(0);
					break;
			}

			traverse = tree.traverseInOrder();
			for (int val : traverse)
			{
				System.out.print(val + " ");
			}
			System.out.println();

		} while (key != 3);
	}
}
