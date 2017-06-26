package mancala;

public class Mancala
{
	public final static int NR_PITS = 6;
	private int[][] board = { {0}, {4,4}, {4,4}, {4,4}, {4,4}, {4,4}, {4,4}, {0} };	
	private int addThisTurn;
	
	public Mancala()
	{
		addThisTurn = 0;
	}
	
	/**
	 * copy constructor
	 * @param other a Mancala object
	 */
	public Mancala(int[][] other)
	{
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				board[i][j] = other[i][j];
			}
		}
		
		addThisTurn = 0;
	}
	
	public Mancala turn(int start, Player player)
	{	
		Mancala newMancala = new Mancala(board);
		int direction = player == Player.MAX ? -1 : 1;
		int side = player == Player.MAX ? 1 : 0;
		int stones = newMancala.board[start][side];
		
		newMancala.board[start][side] = 0;	//set the place that you picked up the stones from to 0
		int place = start + direction;	//start putting pieces after place picked up
			
		while(stones > 0)
		{		
			//if the current box is either Mancala
			if(place == 7 || place == 0)
			{
				if(place == 7 && player == Player.MIN || place == 0 && player == Player.MAX)
				{
					newMancala.board[place][0]++;
					stones--;
					newMancala.addThisTurn++;
				}
				
				direction *= -1;
				side = side == 1 ? 0 : 1;	
			}
			else
			{
				newMancala.board[place][side]++;
				stones--;	
			}	
			
			//landed in a place, get stones from the other side of the board
			//now it's one stone because you already put it down
			if(stones == 0 && place > 0 && place < 7)
			{
				//the player put in a piece so now there is 1
				if(newMancala.board[place][side] == 1)
				{
					int new_side = side == 1 ? 0 : 1;
					//the player landed on his own side
					if(player == Player.MAX && side == 1)
					{
						newMancala.board[0][0] += newMancala.board[place][new_side];		
						newMancala.addThisTurn += newMancala.board[place][new_side];
						newMancala.board[place][new_side] = 0;
					}
					else if(player == Player.MIN && side == 0)
					{
						newMancala.board[7][0] += newMancala.board[place][new_side];		
						newMancala.addThisTurn += newMancala.board[place][new_side];
						newMancala.board[place][new_side] = 0;
					}
				}
			}
			place += direction;	
		}
		return newMancala;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("-----------------------------------------\n");
		sb.append("|    ");
		for(int x = 1; x < board.length-1; x++)
		{
			sb.append(String.format("| %2d ", board[x][1]));			
		}		
		sb.append("|    |\n");
		
		sb.append("-    -------------------------------    -\n");
		
		for(int x = 0; x < board.length; x++)
		{
			sb.append(String.format("| %2d ", board[x][0]));			
		}
		sb.append("|\n");
		sb.append("-----------------------------------------\n       ");
		for(int i = 1; i < board.length-1; i++)
		{
			sb.append(String.format(" %-2d  ", i));
		}
		return sb.toString();	
	}
	
	/**
	 * 0 = tie
	 * 1 = true
	 * -1 = false
	 */
	public int isWin(Player player)
	{
		if(isGameOver())
		{
			//take the extra stones on 1 users side and add it to their mancala
			int stonesMin = board[7][0];
			int stonesMax = board[0][0];
			for(int i = 1; i <= NR_PITS; i++)
			{
				stonesMin += board[i][0];
				stonesMax += board[i][1];
			}
			
			//if both mancalas have the same number of stones, it's a tie
			if(stonesMax == stonesMin) return 0;
			
			if(player == Player.MAX && stonesMax > stonesMin
					|| player == Player.MIN && stonesMin > stonesMax)
			{
				return 1;
			}	
		}
		
		return -1;	
	}
	
	public boolean isGameOver()
	{
		int stonesMin = 0;
		int stonesMax = 0;
		for(int i = 1; i <= NR_PITS; i++)
		{
			stonesMin += board[i][0];
			stonesMax += board[i][1];
		}

		if(stonesMin > 0 && stonesMax > 0) return false;
		
		return true;
	}
	
	/**
	 * make sure that there are stones in the pit they want to move from
	 * check that it is a valid move
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean canMove(int row, Player player)
	{
		int side = player == Player.MAX ? 1 : 0;
		if(row > 0 && row < 7 && board[row][side] > 0)
			return true;		
		return false;
	}
	
	public double heuristicValue(int inMancala)
    {
        double retVal = 0.0;
        if (isWin(Player.MAX) == 1)
        {
            retVal = 1.0;
        }
        else if (isWin(Player.MIN) == 1)
        {
            retVal = -1.0;
        }
        else if(isWin(Player.MIN) == 0)
        {
        	retVal = 0;
        }
        else
        {
            retVal = heuristicCount(inMancala);
        }
        return retVal;
    }

    private double heuristicCount(int inMancala)
    {
        return inMancala * 0.05;           
    }
    
    public int getAddThisTurn()
    {
    	return addThisTurn;
    }
    
    public static double value(Mancala mancala, int depth, double alfa, double beta, Player player, int inMancala)
	{
		double value = 0.0;
		if(depth == 0)
		{
			value = mancala.heuristicValue(inMancala);
		}
		else if(mancala.isGameOver())
		{
			value = mancala.heuristicValue(inMancala);
		}
		else
		{
			Player opponent = player == Player.MAX ? Player.MIN : Player.MAX;
			
			if(player == Player.MAX)
			{
				for(int row = 1; row <= Mancala.NR_PITS; row++)
				{
					if(mancala.canMove(row, player))
					{
						Mancala nextPos = mancala.turn(row, player);
						double thisVal = value(nextPos, depth - 1, alfa, beta, opponent, inMancala + nextPos.getAddThisTurn());
						if(thisVal >= alfa)
						{
							alfa = thisVal;
						}
						if(beta <= alfa)
						{
							break;
						}
					}
				}
				value = alfa;
			}
			else //player = Player.MIN
			{
				for(int row = 1; row <= Mancala.NR_PITS; row++)
				{
					if(mancala.canMove(row, player))
					{
						Mancala nextPos = mancala.turn(row, player);
						double thisVal = value(nextPos, depth - 1, alfa, beta, opponent, inMancala - nextPos.getAddThisTurn());
						if(thisVal <= beta)
						{
							beta = thisVal;
						}
						if(beta <= alfa)
						{
							break;
						}
					}
				}
				value = beta;
			}
		}
		
		return value;
	}
}