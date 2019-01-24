/* PlayerInt - Is an interface all human and computer AIs
 * must implement to function properly in the provided
 * environment. */
public abstract class Player
{
	private String name;
	private char letter;

	public Player(String name, char letter) {
		this.name = name;
		this.letter = letter;
	}

	/* Pre: None
	 * Post: Returns the player's letter */
	public char getLetter()
	{
		return letter;
	}

	/* Pre: Receives the current game board
	 * Post: Returns  the player would like to move to */
	public abstract Move getMove(Board board);

	/* Pre: None
	 * Post: Returns the player's name */
	public String getName()
	{	return name;	}
	
	/* Pre: None
	 * Post: Returns a clone of the current object*/
	public abstract Player freshCopy();

	public String toString()
	{
		return "(" + name+" as "+letter+")";
	}
}
