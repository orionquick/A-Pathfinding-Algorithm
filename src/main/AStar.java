package main;

import java.util.ArrayList;
import java.util.Random;

public class AStar
{
	
	static ArrayList<Point> path = new ArrayList<Point>();
	
	//map size
	static int mapX = 10;
	static int mapY = 10;
	static int lowestF = Integer.MAX_VALUE;
	static Point checkNext = new Point(0, 0);
	static Square[][] squares = new Square[mapX][mapY];
	static Point[] obstacle = new Point[7];
	static Random rand = new Random();
	
	static void print(String x) throws InterruptedException
	{
		System.out.println(x);
		Thread.sleep(1000);
	}
	
	static void hValue(int x, int y , Point f)
	{
		squares[x][y].hValue = Math.abs((x - f.x)) + Math.abs((y - f.y));
		squares[x][y].pos = new Point(x, y);
	}
	
	static void fCheck() throws InterruptedException
	{
		lowestF = Integer.MAX_VALUE;
		Point lowest = new Point(0,0);
		
		for (int i = 0 ; i < mapX; i++)
		{
			for (int j = 0; j < mapY; j++)
			{
				if(squares[i][j].fValue < lowestF && squares[i][j].isValid == true && squares[i][j].isParent == true)
				{
					lowestF = squares[i][j].fValue;
					lowest = squares[i][j].pos;
				}
			}
		}
		
		squares[lowest.x][lowest.y].isValid= false;
		lowestF = squares[lowest.x][lowest.y].fValue;
		if(checkNext.x == lowest.x && checkNext.y == lowest.y)
		{
			print("No Path Found");
			System.exit(1);
		}
		checkNext = squares[lowest.x][lowest.y].pos;
		System.out.println(lowest.x + ", " + lowest.y);
		Thread.sleep(500);
	}
	
	static void pCheck(int x, int y, Point f) throws InterruptedException
	{
		for (int i = 0 ; i < mapX; i++)
		{
			for (int j = 0; j < mapY; j++)
			{
				if(i > x - 2 && i < x + 2 && j > y - 2 && j < y + 2 && !(x == i && y == j) && squares[i][j].isParent == false)
				{
					squares[x][y].parents.add(squares[i][j]);
					squares[i][j].child = squares[x][y];
					squares[i][j].isParent = true;
					
					if(squares[i][j].pos.x == f.x && squares[i][j].pos.y == f.y)
					{
						
						path.add(squares[i][j].child.pos);
						
						while(true)
						{
							try
							{
								path.add(squares[path.get(path.size()-1).x][path.get(path.size()-1).y].child.pos);
							}
							catch(NullPointerException e)
							{
								break;
							}
						}
						
						for(int k = 0; k < path.size(); k++)
						{
							System.out.println(path.get(k).x + ", " + path.get(k).y);
						}
						System.exit(0);
					}
				}
			}
		}
	}
	
	static void mUpdate()
	{
		for (int i = 0 ; i < mapX; i++)
		{
			for (int j = 0; j < mapY; j++)
			{
				squares[i][j].mValue = 1;
				
				for (int k = 0; k < squares[i][j].parents.size(); k++)
				{
					squares[i][j].mValue += squares[i][j].parents.get(k).mValue;
				}
				
				squares[i][j].fUpdate();
			}
		}
	}
	public static void main(String args[]) throws InterruptedException
	{	
		//print("Fills in Square array with squares");
		for (int i = 0 ; i < mapX; i++)
		{
			for (int j = 0; j < mapY; j++)
			{
				squares[i][j] = new Square();
			}
		}
		
		//makes obstacles
		obstacle[0] = new Point(rand.nextInt(9),rand.nextInt(9));
		obstacle[1] = new Point(rand.nextInt(9),rand.nextInt(9));
		obstacle[2] = new Point(rand.nextInt(9),rand.nextInt(9));
		obstacle[3] = new Point(rand.nextInt(9),rand.nextInt(9));
		obstacle[4] = new Point(rand.nextInt(9),rand.nextInt(9));
		obstacle[5] = new Point(rand.nextInt(9),rand.nextInt(9));
		obstacle[6] = new Point(rand.nextInt(9),rand.nextInt(9));
				
		for (int i = 0 ; i < obstacle.length; i++)
		{
				squares[obstacle[i].x][obstacle[i].y].isValid = false;	
		}
		
		//starting point
		Point s = new Point(0, 0);
		squares[s.x][s.y].isParent = true;

		//finishing point
		Point f = new Point(9, 9);
		
		//print("Generates heuristic values for each square");
		for (int i = 0 ; i < mapX; i++)
		{
			for (int j = 0; j < mapY; j++)
			{
				hValue(i, j, f);
			}
		}
		
		//print("Initial parent check");
		pCheck(s.x, s.y, f);
		
		boolean running = true;
		
		while(running)
		{
			//print("Updates mValues");
			mUpdate();
			
			//print("Checks for the square with the lowest fValue");
			fCheck();
	
			//print("Runs a test to find it's parents");
			pCheck(checkNext.x, checkNext.y, f);
			//System.out.println(checkNext.x + "," + checkNext.y);
		}
	}		
}
