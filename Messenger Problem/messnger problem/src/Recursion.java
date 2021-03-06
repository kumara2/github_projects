

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public class Recursion {
	
	public static char[][] getGrid() {
		return Grid;
	}

	public static void setGrid(char[][] grid) {
		Grid = grid;
	}
	//setStart_row(startx);
	public static int getStart_row() {
		return start_row;
	}

	public static void setStart_row(int start_row) {
		Recursion.start_row = start_row;
	}

	public static int getStart_col() {
		return start_col;
	}

	public static void setStart_col(int start_col) {
		Recursion.start_col = start_col;
	}

	static char Grid[][]=new char[20][20];
	static ArrayList<Integer> Goals=new ArrayList<Integer>();
	private static ArrayList<Integer> packages=new ArrayList<Integer>();
	static String path="";
	static int Max_Row,start_row,start_col,length,Max_Col;
	static double map_cost[][]=new double[40][20];
	static int goal_position[][]=new int[40][20]; 
	static Comparator<Node> comp = new Node.DFSComparator();
	static PriorityQueue<Node> openList = new PriorityQueue<Node>(1000,comp);
	static PriorityQueue<Node> closedList = new PriorityQueue<Node>(1000,comp);
	public static ArrayList<Integer> getGoals() 
	{
		return Goals;
	}

	public static void setGoals(ArrayList<Integer> goals) 
	{
		Goals = goals;
	}
	
	public static int getMax_Row() 
	{
		return Max_Row;
	}

	public static void setMax_Row(int max_Row) 
	{
		Max_Row = max_Row;
	}

	

	public static int getMax_Col() 
	{
		return Max_Col;
	}

	public static void setMax_Col(int max_Col) 
	{
		Max_Col = max_Col;
	}

	public static double[][] getMap_cost() 
	{
		return map_cost;
	}

	public static void setMap_cost(double[][] map_cost) 
	{
		Recursion.map_cost = map_cost;
	}

	
	public static void main(String args[])
	{
	Read.readFile();
	int row_position = Max_Row-start_row;
	Node root=new Node(row_position,start_col,0,map_cost[row_position][start_col],null,packages,path);
	openList.add(root);
	closedList.add(root);
	recursion(root);
}


		
	private static void recursion(Node root) {
		// TODO Auto-generated method stub
		//Base Case
		if(goalstate(root))
		{
			//System.out.println("I am here");
			root.cost = root.cost - map_cost[start_row][start_col];
			System.out.println(root);
		}
		//otherwise make recursive call 
		else
		{
			Node temp = openList.remove();
			if(!isInClosedList(closedList, temp))
				{
				add_n_expand(temp);
				closedList.add(temp);
				}
			root = temp;
			
			
			
			recursion(root);//RECURSIVE CALL MADE HERE
		
		
		
		
		}
	}

	public static int[][] getGoal_pos() {
		return goal_position;
	}

	public static void setGoal_pos(int[][] goal_position) {
		Recursion.goal_position = goal_position;
	}

	static boolean isInClosedList(Queue<Node> closedList,Node n)
	{
		Queue<Node> iterator=new LinkedList<Node>();
		iterator.addAll(closedList);
		if(iterator.size()==0)
			return false;
		else{
			Node node1=iterator.remove();
		while(iterator.size()>0)
		{
			if(node1.row==n.row && node1.col==n.col)
			{
				if(node1.package_delivered.equals(n.package_delivered))
					return true;
			}
		if(iterator.size()>0){node1=iterator.remove();}
		}
		return false;
		}
	}
		
	private static boolean goalstate(Node n)
	{
		boolean match=false;
		if(n.row==(Max_Row-start_row) && n.col==start_col)
		{
			Collections.sort(n.package_delivered);
			if(n.package_delivered.equals(Goals))
			{				
				match = true;
			}
		}
		return match;
	}
		
	private static void compareGoals(Node child)
	{
		int goal_state=0,j;
		for(int i=0;i<Goals.size();i++)
		{
			j=0;
			if(child.row==goal_position[i][j] && child.col==goal_position[i][j+1])
			{
				goal_state=Goals.get(i);
				if(child.package_delivered.size()==0)
				{
					child.package_delivered.add(goal_state);
					break;
				}
				else if(!child.package_delivered.contains(goal_state))
				{
					child.package_delivered.add(goal_state);
				}
			}
		}
	}
		
	private static void add_n_expand(Node Parent)
	{
		int node_row=Parent.row;
		int node_col=Parent.col;
		int node_depth=Parent.depth;
		double node_cost=Parent.cost;
		String dir=Parent.path;	
		if(node_row!=0 && node_row<=Max_Row)
		{	//Expanding the north child
			ArrayList<Integer> node_goals_reached = new ArrayList<Integer>();
			node_goals_reached.addAll(Parent.package_delivered);
			String child_dir=dir.concat("N");
			node_depth=Parent.depth+1;
			Node child=new Node(node_row-1,node_col,node_depth,node_cost+map_cost[node_row-1][node_col],Parent,node_goals_reached,child_dir);
			compareGoals(child);
			openList.add(child);		
		}
		//expandng the south child
		
		if(node_row!=Max_Row && node_row>=0)
		{
			ArrayList<Integer> node_goals = new ArrayList<Integer>();
			node_goals.addAll(Parent.package_delivered);
			String child_dir=dir.concat("S");
			node_depth=Parent.depth+1;
			Node child=new Node(node_row+1,node_col,node_depth,node_cost+map_cost[node_row+1][node_col],Parent,node_goals,child_dir);
			compareGoals(child);
			openList.add(child);
		}
		
		//expanding the East child
		if(node_col!=Max_Col && node_col>=0)
		{
			ArrayList<Integer> node_goals = new ArrayList<Integer>();
			node_goals.addAll(Parent.package_delivered);
			String child_dir=dir.concat("E");
			node_depth=Parent.depth+1;
			Node child=new Node(node_row,node_col+1,node_depth,node_cost+map_cost[node_row][node_col+1],Parent,node_goals,child_dir);
			compareGoals(child);
			openList.add(child);
		}
		
		//expanding the west child
		if(node_col!=0 && node_col<=Max_Col)
		{
			ArrayList<Integer> node_goals = new ArrayList<Integer>();
			node_goals.addAll(Parent.package_delivered);
			String child_dir=dir.concat("W");
			node_depth=Parent.depth+1;
			Node child=new Node(node_row,node_col-1,node_depth,node_cost+map_cost[node_row][node_col-1],Parent,node_goals,child_dir);
			compareGoals(child);
			openList.add(child);
		}
	}
	
}
