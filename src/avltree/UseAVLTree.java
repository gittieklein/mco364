package avltree;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class UseAVLTree
{
	public static void main(String[] args)
	{
		try
		{
			AvlTree<String> tree = new AvlTree<>();

			Scanner read = null;

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

			while (read.hasNext())
			{
				tree.insert(read.nextLine());
			}

			Scanner input = new Scanner(System.in);

			System.out.println("STARTING TREE");
			tree.printTree();

			int key = 0;
			String value = null;

			do
			{
				System.out.println("\n1.Insert Node\n2.Remove Node\n3.Exit application");
				key = input.nextInt();
				input.nextLine();

				switch (key)
				{
					case 1:
						System.out.print("Which value would you like to insert? ");
						value = input.nextLine();
						tree.insert(value);
						System.out.println("\n\nYou added " + value + " to the tree");
						break;
					case 2:
						System.out.print("Which value would you like to remove? ");
						value = input.nextLine();
						try
						{
							tree.remove(value);
							System.out.println("\n\n" + value + " has been removed from the tree");
						}
						catch (RuntimeException e)
						{
							System.out.println("\n\n" + value + " is not in the tree");
						}
						break;
					case 3:
						System.out.println("Exiting application...");
						System.exit(0);
						break;
				}

				tree.printTree();

			} while (key != 3);
		}
		catch (Exception e)
		{
			System.out.println("error...program ending");
		}
	}
}