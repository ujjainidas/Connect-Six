import java.util.ArrayList;
import java.util.Random;

public class AkiyamaDas extends Player
{
    private char letter;
    private String name;

    public AkiyamaDas(char letter)
    {
        super("Random Computer",letter);
    }

    public Move getMove(Board board)
    {
//        Move m;
//        Random rand = new Random();
//        do
//        {
//            m = new Move(rand.nextInt(8),rand.nextInt(8));
//        }while(board.isFull(m));
//        return m;

        ArrayList<MoveGrades> moves = new ArrayList<MoveGrades>();

//        int level = 0;
        int grade = 0;
        Location l;
        for(int i = 0; i<board.getBoard().length; i++)
        {
            for(int j = 0; j<board.getBoard()[i][0].length; j++)
            {
                for(int k = Board.Y_SIZE-1; k>=0; k--)
                {
                    if (board.getBoard()[i][j][k] == Board.EMPTY)
                        moves.add(new MoveGrades(new Move(i, j)));
                }
            }
        }




        return new Move(0,0);//just to stop the error, will be removed later
    }



    public Player freshCopy()
    {
        return new AkiyamaDas(letter);
    }
}