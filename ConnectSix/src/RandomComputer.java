import java.util.Random;

public class RandomComputer extends Player
{
	public RandomComputer(char letter)
	{
		super("Random Computer",letter);
	}

	public Move getMove(Board board)
	{
		Move m;
		Random rand = new Random();
		do
		{
			m = new Move(rand.nextInt(8),rand.nextInt(8));
		}while(board.isFull(m));
		return m;
	}
	

	
	public Player freshCopy()
	{
		return new RandomComputer(getLetter());
	}
}