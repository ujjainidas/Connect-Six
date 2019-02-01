import java.util.ArrayList;
import java.util.Random;

public class AkiyamaDas extends Player
{
    public static final int FLAT = 4;
    public static final int VERTICAL = 3;
    public static final int VERT_DIAGONAL = 2;

    public static final int ONE = 1;
    public static final int TWO = 10;
    public static final int THREE = 100;
    public static final int FOUR = 1000;
    public static final int FIVE = 10000;
    public static final int SIX = 100000;


    private char letter;
    private String name;

    public AkiyamaDas(char letter)
    {
        super("My AI",letter);
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
//        ArrayList<MoveGrades> movesOne = new ArrayList<MoveGrades>();

//        int level = 0;
        int grade = 0;
        Location l;
        for(int i = 0; i<Board.Z_SIZE; i++)
        {
            for(int j = 0; j<Board.X_SIZE; j++)
            {
                for(int k = 0; k<Board.Y_SIZE; k++)
                {
                    if (board.getBoard()[i][k][j] == Board.EMPTY)
                    {
                        moves.add(new MoveGrades(new Move(i, j), k));
                        break;
                    }
                }
            }
        }

        int count = 1;
        int x, y, z = 0;
        int largestGrade = 0;
        for(int j = 0; j<moves.size(); j++)
        {
            x = moves.get(j).getMove().getX();
            y = moves.get(j).getY();
            z = moves.get(j).getMove().getZ();
            //horizontal x
            for(int i = x+1; i<Board.X_SIZE; i++)
            {
                if(board.getBoard()[z][y][i] == letter)
                    count++;
                else
                    break;
            }
            for(int i = x-1; i>=0; i--)
            {
                if(board.getBoard()[z][y][i] == letter)
                    count++;
                else
                    break;
            }
            grade += (Math.pow(10, count-1)) * FLAT;

            count = 1;
            //horizontal z
            for(int i = z+1; i<Board.Z_SIZE; i++)
            {
                if(board.getBoard()[i][y][x] == letter)
                    count++;
                else
                    break;
            }
            for(int i = z-1; i>=0; i--)
            {
                if(board.getBoard()[i][y][x] == letter)
                    count++;
                else
                    break;
            }
            grade += (Math.pow(10, count-1)) * FLAT;

            count = 1;
            //increasing diagonal x-z
            for(int i = 1; i<= i+5; i++)
            {
                if(x+1 < Board.X_SIZE && z+1 < Board.Z_SIZE && board.getBoard()[z+1][y][x+1] == letter)
                    count++;
                else
                    break;
            }
            for(int i = 1; i<= i+5; i++)
            {
                if(x-1 >= 0 && z-1 >= 0 && board.getBoard()[z-1][y][x-1] == letter)
                    count++;
                else
                    break;
            }

            count=1;
            //decreasing diagonal x-z
            for(int i = 1; i<= i+5; i++)
            {
                if(x+1 < Board.X_SIZE && z-1 >= 0 && board.getBoard()[z-1][y][x+1] == letter)
                    count++;
                else
                    break;
            }
            for(int i = 1; i<= i+5; i++)
            {
                if(x-1 >= 0 && z+1 < Board.Z_SIZE && board.getBoard()[z+1][y][x-1] == letter)
                    count++;
                else
                    break;
            }

            grade += (Math.pow(10, count-1)) * FLAT;

            count=1;
            //vertical
            for(int i = y+1; i<Board.Y_SIZE; i++)
            {
                if(board.getBoard()[z][i][x] == letter)
                    count++;
                else
                    break;
            }
            for(int i = y-1; i >= 0; i++)
            {
                if(board.getBoard()[z][i][x] == letter)
                    count++;
                else
                    break;
            }
            grade += (Math.pow(10, count-1)) * VERTICAL;

            count=1;
            //diagonal x-y
            for(int i = 1; i < i+5; i++)
            {
                if(x+i < Board.X_SIZE && y+i < Board.Y_SIZE && board.getBoard()[z][y+i][x+i] == letter)
                    count++;
                else
                    break;
            }
            for(int i = 1; i < i+5; i++)
            {
                if(x-i >=0 && y-i >=0 && board.getBoard()[z][y-i][x-i] == letter)
                    count++;
                else
                    break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count = 1;
            //other x-y diagonal
            for(int i = 1; i < i+5; i++)
            {
                if(x+i < board.X_SIZE && y-i >= 0 && board.getBoard()[z][y-i][x+i] == letter)
                    count++;
                else
                    break;
            }
            for(int i = 1; i < i+5; i++)
            {
                if(x-i >= 0 && y+i < board.Y_SIZE && board.getBoard()[z][y+i][x-i] == letter)
                    count++;
                else
                    break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count = 1;
            //z-y diagonal
            for(int i = 1; i < 1+5; i++)
            {
                if(z+i < board.Z_SIZE && y+i < board.Y_SIZE && board.getBoard()[z+i][y+i][x] == letter)
                    count++;
                else break;
            }
            for(int i = 1; i < 1+5; i++)
            {
                if(z-i >= 0 && y-i >= 0 && board.getBoard()[z-i][y-i][x] == letter)
                    count++;
                else break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count=1;
            //z-y other diagonal
            for(int i = 1; i < 1+5; i++)
            {
                if(z+i < board.Z_SIZE && y-i >= 0 && board.getBoard()[z+i][y-i][x] == letter)
                    count++;
                else break;
            }
            for(int i = 1; i < 1+5; i++)
            {
                if(z-i >= 0 && y+i < board.Y_SIZE && board.getBoard()[z-i][y+i][x] == letter)
                    count++;
                else break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count = 1;
            //special diagonal all increasing
            for(int i = 1; i < 1+5; i++)
            {
                if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x+i <Board.X_SIZE && board.getBoard()[z+i][y+i][x] == letter)
                    count++;
                else break;
            }
            for(int i = 1; i < 1+5; i++)
            {
                if(z-i >= 0 && y-i >= 0 && x-i >= 0 && board.getBoard()[z-i][y-i][x] == letter)
                    count++;
                else break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count = 1;
            //special diagonal all increasing except x
            for(int i = 1; i < 1+5; i++)
            {
                if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x-i >= 0 && board.getBoard()[z+i][y+i][x] == letter)
                    count++;
                else break;
            }
            for(int i = 1; i < 1+5; i++)
            {
                if(z-i >= 0 && y-i >= 0 && x+i < Board.X_SIZE && board.getBoard()[z-i][y-i][x] == letter)
                    count++;
                else break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count = 1;
            //special diagonal all decreasing except y
            for(int i = 1; i < 1+5; i++)
            {
                if(z-i >= 0 && y+i < Board.Y_SIZE && x-i >= 0 && board.getBoard()[z-i][y+i][x-i] == letter)
                    count++;
                else break;
            }
            for(int i = 1; i < 1+5; i++)
            {
                if(z+i < Board.Z_SIZE && y-i >= 0 && x+i < Board.X_SIZE && board.getBoard()[z+i][y-i][x+i] == letter)
                    count++;
                else break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count = 1;
            //special diagonal all increasing except z
            for(int i = 1; i < 1+5; i++)
            {
                if(z-i >= 0 && y+i < Board.Y_SIZE && x+i < Board.X_SIZE && board.getBoard()[z-i][y+i][x+i] == letter)
                    count++;
                else break;
            }
            for(int i = 1; i < 1+5; i++)
            {
                if(z+i < Board.Z_SIZE && y-i >= 0 && x-i >= 0 && board.getBoard()[z-i][y-i][x] == letter)
                    count++;
                else break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            moves.set(j, new MoveGrades(moves.get(j).getMove(), moves.get(j).getY(), grade));




        }

        MoveGrades bestMove = null;
        for(int i = 0; i<moves.size(); i++)
        {
            if(moves.get(i).getGrade() > moves.get(largestGrade).getGrade())
            {
                largestGrade = i;
                bestMove = moves.get(i);
            }
        }









        return new Move(bestMove.getMove().getX(),bestMove.getMove().getZ());//no look ahead, just best move
    }



    public Player freshCopy()
    {
        return new AkiyamaDas(letter);
    }
}