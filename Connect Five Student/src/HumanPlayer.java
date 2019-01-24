import java.util.Scanner;

public class HumanPlayer extends Player
{
    private char letter;
    private String name;

    public HumanPlayer(String name,char letter)
    {
        super(name, letter);
    }


    public Move getMove(Board board)
    {
        Scanner keyboard = new Scanner(System.in);
        while(true)
        {
            System.out.print("Enter a x value: ");
            int x = keyboard.nextInt();
            System.out.print("Enter a z value: ");
            int z = keyboard.nextInt();

            Move l = new Move(x,z);
            if(!board.isFull(l))
            {
                return l;
            }
        }
    }



    public Player freshCopy()
    {
        return new RandomComputer(letter);
    }
}