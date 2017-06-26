package mancala;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MancalaGame
{
	final static int MAX_DEPTH = 9;
	
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		
		Mancala mancala = new Mancala();
		boolean gameOver = false;
		
		while(!gameOver)
		{
			System.out.println("\n\nI am thinking...");
			double highVal = -1.0;
			int bestMove = 0;
			double alfa = -1.0;
			double beta = 1.0;
			
			for(int row = 1; row <= Mancala.NR_PITS; row++)
			{	
				if(mancala.canMove(row, Player.MAX))
				{
					Mancala nextPos = mancala.turn(row, Player.MAX);
					double thisVal = Mancala.value(nextPos, MAX_DEPTH, alfa, beta, Player.MAX, nextPos.getAddThisTurn());
					if(thisVal >= highVal) 
					{				
						bestMove = row;
						highVal = thisVal;
					}
				}
			}
			System.out.println("My move is " + bestMove);
			mancala = mancala.turn(bestMove, Player.MAX);
			System.out.println(mancala.toString());
			
			if(mancala.isWin(Player.MAX) == 1)
			{
				System.out.println("I Win!");
				gameOver = true;
			}
			else if (mancala.isWin(Player.MAX) == 0)
			{
				System.out.println("It's a Tie!");
				gameOver = true;
			}
			else if (mancala.isWin(Player.MIN) == 1)
			{
				System.out.println("You Win!");
				gameOver = true;
			}
			else
			{
				System.out.print("\n\nSelect a pit to move from: ");
				int move = 0;
				boolean again = false;
				do
				{
					String smove = input.nextLine(); 
					try 
					{ 
						move = Integer.parseInt(smove);
						if(move < 1 || move > 6 || !mancala.canMove(move, Player.MIN))
						{
							System.out.print("Please enter a valid pit (1-6): ");
							again = true;
						}
						else
						{
							again = false;
						}
					} 
					catch(NumberFormatException e)
					{
						System.out.print("Please enter a number from 1-6: ");
						again = true;
					}	
					catch(InputMismatchException e)
					{
						System.out.print("Please enter a number from 1-6: ");
						again = true;
					}
				} while(again);
				mancala = mancala.turn(move, Player.MIN);
				System.out.println(mancala.toString());
				
				if(mancala.isWin(Player.MIN) == 1)
				{
					System.out.println("You Win!");
					gameOver = true;
				}
				else if (mancala.isWin(Player.MAX) == 0)
				{
					System.out.println("It's a Tie!");
					gameOver = true;
				}
				else if(mancala.isWin(Player.MAX) == 1)
				{
					System.out.println("I Win!");
					gameOver = true;
				}
			}
			
			
		}		
	}
}
