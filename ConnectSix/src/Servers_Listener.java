import java.io.*;
import java.util.ArrayList;

public class Servers_Listener implements Runnable
{
	// Streams
	private ObjectInputStream is;
	private ObjectOutputStream os;
	
	// board
	private Board board= new Board();;
	
	// selected opponent
	private Player opponentAsRed;
	private Player opponentAsBlue;
	
	// X AIs by category
	private ArrayList<Player> testingAIsAsRed = new ArrayList<Player>();
	private ArrayList<Player> firstPeriodAIsAsRed = new ArrayList<Player>();
	private ArrayList<Player> secondPeriodAIsAsRed = new ArrayList<Player>();
	private ArrayList<Player> thirdPeriodAIsAsRed = new ArrayList<Player>();
	private ArrayList<Player> fourthPeriodAIsAsRed = new ArrayList<Player>();
	private ArrayList<Player> fifthPeriodAIsAsRed = new ArrayList<Player>();
	private ArrayList<Player> sixthPeriodAIsAsRed = new ArrayList<Player>();
	private ArrayList<Player> seventhPeriodAIsAsRed = new ArrayList<Player>();
	
	// X AIs by category
	private ArrayList<Player> testingAIsAsBlue = new ArrayList<Player>();
	private ArrayList<Player> firstPeriodAIsAsBlue = new ArrayList<Player>();
	private ArrayList<Player> secondPeriodAIsAsBlue = new ArrayList<Player>();
	private ArrayList<Player> thirdPeriodAIsAsBlue = new ArrayList<Player>();
	private ArrayList<Player> fourthPeriodAIsAsBlue = new ArrayList<Player>();
	private ArrayList<Player> fifthPeriodAIsAsBlue = new ArrayList<Player>();
	private ArrayList<Player> sixthPeriodAIsAsBlue = new ArrayList<Player>();
	private ArrayList<Player> seventhPeriodAIsAsBlue = new ArrayList<Player>();
	
	// Category Names
	private ArrayList<String> categories = new ArrayList<String>();
	private int numberOfGameToPlay = 10;
	private Command_From_Server commandFromSerever = null;
	private String playerName;
	
	
	public Servers_Listener(ObjectOutputStream os, ObjectInputStream is)
	{
		this.is			= is;
		this.os			= os;
		
		// Adds testing AIs
		testingAIsAsRed.add(new RandomComputer('R'));
		testingAIsAsBlue.add(new RandomComputer('B'));



		// Adds fourth Period AIs
		
		// Adds Seventh Period AIs
		
		// Load Categories
		categories.add("Test AIs");
		//categories.add("Fourth Period");
		//categories.add("Seventh Period");
	}

	public void run()
	{
		try
		{
			//System.out.println("AI Connection");
			while(true)
			{
				Command_To_Server bigCommand = (Command_To_Server)is.readObject();
				if(bigCommand.getCommand() == Command_To_Server.NEW_MATCH)
				{
					playerName = (String) bigCommand.getCommandData();
					//System.out.println("AI Name is" + playerName);
					
					commandFromSerever = new Command_From_Server(Command_From_Server.CATEGORY_SELECTION,categories);
					os.writeObject(commandFromSerever);
					os.reset();
					
					
					Command_To_Server categoryCommand = (Command_To_Server)is.readObject();
					int categoryIndex =(Integer) categoryCommand.getCommandData();
					ArrayList<String> names = new ArrayList<String>();
					//System.out.println("Category Number" +categoryIndex);
					
					if(categoryIndex == 0)
					{
						for(Player p: testingAIsAsRed)
						{
							names.add(p.getName());
						}
						
						commandFromSerever = new Command_From_Server(Command_From_Server.AI_SELECTION,names);
						os.writeObject(commandFromSerever);
						os.reset();
						
						Command_To_Server pickedAI = (Command_To_Server)is.readObject();
						int ai_Index =(Integer) pickedAI.getCommandData();
						//System.out.println("ai Number " +ai_Index);
						
						if(ai_Index >= testingAIsAsRed.size())
						{
							System.out.println("Bad AI Index");
							break;
						}
						else
						{
							opponentAsRed = testingAIsAsRed.get(ai_Index).freshCopy();
							opponentAsBlue = testingAIsAsBlue.get(ai_Index).freshCopy();
						}
					}
					else if(categoryIndex == 1)
					{
						//System.out.println("Category not implemented");
						break;
						
					}
					else if(categoryIndex == 2)
					{
						//System.out.println("Category not implemented");
						break;
					
					}
					else
					{
						//System.out.println("Bad Category");
						break;
					}
					
					Command_From_Server a = new Command_From_Server(Command_From_Server.START_PLAYER_FIRST_GAMES,opponentAsBlue.getName());
					os.writeObject(a);
					os.reset();
					//System.out.println("a");
					for(int x = 0; x<numberOfGameToPlay; x++)
						playGame(true);
					//System.out.println("b");
					commandFromSerever = new Command_From_Server(Command_From_Server.START_PLAYER_SECOND_GAMES,opponentAsBlue.getName());
					os.writeObject(commandFromSerever);
					os.reset();
					//System.out.println("c");
					for(int x = 0; x<numberOfGameToPlay; x++)
						playGame(false);
					//System.out.println("d");
					commandFromSerever = new Command_From_Server(Command_From_Server.MATCHES_COMPLETE,opponentAsBlue.getName());
					os.writeObject(commandFromSerever);
					os.reset();
					//System.out.println("e");
					break;
				}
			}
		}
		catch(Exception e)
		{
			//System.out.println("Error in Server's Listener: "+ e.getMessage());
			//e.printStackTrace();
		}
		
	}
	
	public void playGame(boolean playerFirst)
	{
		//System.out.println("In play game");
		try
		{
			board.reset();
			//Location l;
		 	boolean firstPlayersTurn = true;
			
			if(playerFirst)
			{
				while(true)
				{
					if(board.getWinner()!='T')
					{
						if(firstPlayersTurn)
						{
							commandFromSerever = new Command_From_Server(Command_From_Server.MAKE_MOVE);
							os.writeObject(commandFromSerever);
							os.reset();
							
							Command_To_Server categoryCommand = (Command_To_Server)is.readObject();
							Move l =(Move) categoryCommand.getCommandData();
							//System.out.println("Visitor moved to  "+l);
					
							if(l!=null &&!board.isFull(l))
							{
								board.makeMove(l,'R');
								commandFromSerever = new Command_From_Server(Command_From_Server.SUCCESSFUL_MOVE,l);
								os.writeObject(commandFromSerever);
								os.reset();
							}
							else
							{
								//System.out.println(playerName + " failed to move.");
								commandFromSerever = new Command_From_Server(Command_From_Server.FAILED_MOVE);
								os.writeObject(commandFromSerever);
								os.reset();
							}
							
							firstPlayersTurn = false;
						}
						else
						{
							Move l=opponentAsBlue.getMove(new Board(board));
							if(!board.isFull(l))
							{
								board.makeMove(l,'B');
								commandFromSerever = new Command_From_Server(Command_From_Server.OPPONENT_MOVE,l);
								os.writeObject(commandFromSerever);
								os.reset();
								//System.out.println("Home AI moved to "+ l);
							}
							else
							{
								//System.out.println(opponentAsO.getName()+" failed to move.");
								commandFromSerever = new Command_From_Server(Command_From_Server.OPPONENT_FAILED_TO_MOVE);
								os.writeObject(commandFromSerever);
								os.reset();
							}
								
							firstPlayersTurn = true;
						}	
						
					}
					if(board.getWinner()=='R')
					{
						//System.out.println("X Wins");
						commandFromSerever = new Command_From_Server(Command_From_Server.WIN);
						os.writeObject(commandFromSerever);
						os.reset();
						return;
					}
					else if(board.getWinner()=='B')
					{
						//System.out.println("O Wins");
						commandFromSerever = new Command_From_Server(Command_From_Server.LOSE);
						os.writeObject(commandFromSerever);
						os.reset();
						return;
					}
					else if(board.getWinner()=='T')
					{
						//System.out.println("Cat");
						commandFromSerever = new Command_From_Server(Command_From_Server.TIE);
						os.writeObject(commandFromSerever);
						os.reset();
						return;
					}
				}
			}
			else
			{
				while(true)
				{
					if(board.getWinner()=='-')
					{
						if(firstPlayersTurn)
						{
							Move l= opponentAsRed.getMove(new Board(board));
							if(!board.isFull(l))
							{
								board.makeMove(l,'R');
								commandFromSerever = new Command_From_Server(Command_From_Server.OPPONENT_MOVE,l);
								os.writeObject(commandFromSerever);
								os.reset();
							}
							else
							{
								commandFromSerever = new Command_From_Server(Command_From_Server.OPPONENT_FAILED_TO_MOVE);
								os.writeObject(commandFromSerever);
								os.reset();
							}
							
							firstPlayersTurn = false;
						}
						else
						{
							commandFromSerever = new Command_From_Server(Command_From_Server.MAKE_MOVE);
							os.writeObject(commandFromSerever);
							os.reset();
							
							Command_To_Server categoryCommand = (Command_To_Server)is.readObject();
							Move l =(Move) categoryCommand.getCommandData();
					
							if(l!=null &&!board.isFull(l))
							{
								board.makeMove(l,'B');
								commandFromSerever = new Command_From_Server(Command_From_Server.SUCCESSFUL_MOVE,l);
								os.writeObject(commandFromSerever);
								os.reset();
							}
							else
							{
								commandFromSerever = new Command_From_Server(Command_From_Server.FAILED_MOVE);
								os.writeObject(commandFromSerever);
								os.reset();
							}
							/**/
								
							firstPlayersTurn = true;
						}	
						
					}
					
					if(board.getWinner()=='R')
					{
						//System.out.println("X Wins");
						commandFromSerever = new Command_From_Server(Command_From_Server.LOSE);
						os.writeObject(commandFromSerever);
						os.reset();
						return;
					}
					else if(board.getWinner()=='B')
					{
						//System.out.println("O Wins");
						commandFromSerever = new Command_From_Server(Command_From_Server.WIN);
						os.writeObject(commandFromSerever);
						os.reset();
						return;
					}
					else if(board.getWinner()=='T')
					{
						//System.out.println("Cat");
						commandFromSerever = new Command_From_Server(Command_From_Server.TIE);
						os.writeObject(commandFromSerever);
						os.reset();
						return;
					}
				}
			}
		}
		catch(Exception e)
		{ 
			System.out.println("Crashed While Playing a Game");
			e.printStackTrace();
		}
		
	}
}


