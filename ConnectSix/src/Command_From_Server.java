import java.io.Serializable;

public class Command_From_Server implements Serializable
{
	private int command = 0;
	private Object commandData = null;

	public static final int UPDATE_GAME 				= 1;
	public static final int CATEGORY_SELECTION 			= 2;
	public static final int AI_SELECTION 				= 3;
	public static final int START_PLAYER_FIRST_GAMES 	= 4;
	public static final int START_PLAYER_SECOND_GAMES 	= 5;
	public static final int MAKE_MOVE 					= 6;
	public static final int WIN 						= 7;
	public static final int LOSE 						= 8;
	public static final int TIE 						= 9;
	public static final int MATCHES_COMPLETE			= 10;
	public static final int SUCCESSFUL_MOVE				= 11;
	public static final int FAILED_MOVE					= 12;
	public static final int OPPONENT_MOVE				= 13;
	public static final int OPPONENT_FAILED_TO_MOVE		= 14;

	
	public Command_From_Server(int command)
	{
		this.command	= command;
	}
	
	public Command_From_Server(int command, Object commandData)
	{
		this.command	 = command;
		this.commandData = commandData;
	}
	
	public int getCommand()
	{	return command;	}
	
	public Object getCommandData()
	{	return commandData;	}
}