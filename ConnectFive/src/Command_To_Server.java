import java.io.Serializable;

public class Command_To_Server implements Serializable
{
	private int command 					= 0;
	private Object commandData				= null;
	
	public static final int MOVE 			= 1;
	public static final int NEW_MATCH 		= 2;
	public static final int SELECT_CATEGORY = 3;
	public static final int SELECT_AI 		= 4;
	public static final int DISCONNECT 		= 5;

	public Command_To_Server(int command)
	{
		this.command	= command;
	}
	
	public Command_To_Server(int command, Object commandData)
	{
		this.command	 = command;
		this.commandData = commandData;
	}
	
	public int getCommand()
	{	return command;	}
	
	public Object getCommandData()
	{	return commandData;	}
}