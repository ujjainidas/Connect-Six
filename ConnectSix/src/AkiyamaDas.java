import java.util.ArrayList;
import java.util.Random;

public class AkiyamaDas extends Player
{
    //127.0.0.1
    public static final int FLAT = 4;
    public static final int VERTICAL = 3;
    public static final int VERT_DIAGONAL = 2;

//    public static final int ONE = 1;
//    public static final int TWO = 10;
//    public static final int THREE = 100;
//    public static final int FOUR = 1000;
//    public static final int FIVE = 10000;
//    public static final int SIX = 100000;


    private char letter;
    private String name;
    private char[][][] locBoard;

    public AkiyamaDas(char letter)
    {
        super("My AI",letter);
    }

    public Move getMove(Board board)
    {
        locBoard = board.getBoard();

        ArrayList<MoveGrades> moves = new ArrayList<MoveGrades>();

        int grade = 0;
        Location l;
        for(int i = 0; i<Board.Z_SIZE; i++)
        {
            for(int j = 0; j<Board.X_SIZE; j++)
            {
                for(int k = 0; k<Board.Y_SIZE; k++)
                {
                    if (locBoard[i][k][j] == Board.EMPTY)
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
            grade = 0;
            x = moves.get(j).getX();
            y = moves.get(j).getY();
            z = moves.get(j).getZ();
            //horizontal x
            for(int i = x+1; i<Board.X_SIZE; i++)
            {
                if(locBoard[z][y][i] == letter)
                    count++;
                else
                    break;
            }
            for(int i = x-1; i>=0; i--)
            {
                if(locBoard[z][y][i] == letter)
                    count++;
                else
                    break;
            }
            grade += (Math.pow(10, count-1)) * FLAT;

            count = 1;
            //horizontal z
            for(int i = z+1; i<Board.Z_SIZE; i++)
            {
                if(locBoard[i][y][x] == letter)
                    count++;
                else
                    break;
            }
            for(int i = z-1; i>=0; i--)
            {
                if(locBoard[i][y][x] == letter)
                    count++;
                else
                    break;
            }
            grade += (Math.pow(10, count-1)) * FLAT;

            count = 1;
            //increasing diagonal x-z
            for(int i = 1; i<= i+5; i++)
            {
                if(x+1 < Board.X_SIZE && z+1 < Board.Z_SIZE && locBoard[z+1][y][x+1] == letter)
                    count++;
                else
                    break;
            }
            for(int i = 1; i<= i+5; i++)
            {
                if(x-1 >= 0 && z-1 >= 0 && locBoard[z-1][y][x-1] == letter)
                    count++;
                else
                    break;
            }

            count=1;
            //decreasing diagonal x-z
            for(int i = 1; i<= i+5; i++)
            {
                if(x+1 < Board.X_SIZE && z-1 >= 0 && locBoard[z-1][y][x+1] == letter)
                    count++;
                else
                    break;
            }
            for(int i = 1; i<= i+5; i++)
            {
                if(x-1 >= 0 && z+1 < Board.Z_SIZE && locBoard[z+1][y][x-1] == letter)
                    count++;
                else
                    break;
            }
            grade += (Math.pow(10, count-1)) * FLAT;

            count=1;
            //vertical
            for(int i = y+1; i<Board.Y_SIZE; i++)
            {
                if(locBoard[z][i][x] == letter)
                    count++;
                else
                    break;
            }
            for(int i = y-1; i >= 0; i++)
            {
                if(locBoard[z][i][x] == letter)
                    count++;
                else
                    break;
            }
            grade += (Math.pow(10, count-1)) * VERTICAL;

            count=1;
            //diagonal x-y
            for(int i = 1; i < i+5; i++)
            {
                if(x+i < Board.X_SIZE && y+i < Board.Y_SIZE && locBoard[z][y+i][x+i] == letter)
                    count++;
                else
                    break;
            }
            for(int i = 1; i < i+5; i++)
            {
                if(x-i >=0 && y-i >=0 && locBoard[z][y-i][x-i] == letter)
                    count++;
                else
                    break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count = 1;
            //other x-y diagonal
            for(int i = 1; i < i+5; i++)
            {
                if(x+i < board.X_SIZE && y-i >= 0 && locBoard[z][y-i][x+i] == letter)
                    count++;
                else
                    break;
            }
            for(int i = 1; i < i+5; i++)
            {
                if(x-i >= 0 && y+i < board.Y_SIZE && locBoard[z][y+i][x-i] == letter)
                    count++;
                else
                    break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count = 1;
            //z-y diagonal
            for(int i = 1; i < 1+5; i++)
            {
                if(z+i < board.Z_SIZE && y+i < board.Y_SIZE && locBoard[z+i][y+i][x] == letter)
                    count++;
                else break;
            }
            for(int i = 1; i < 1+5; i++)
            {
                if(z-i >= 0 && y-i >= 0 && locBoard[z-i][y-i][x] == letter)
                    count++;
                else break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count=1;
            //z-y other diagonal
            for(int i = 1; i < 1+5; i++)
            {
                if(z+i < board.Z_SIZE && y-i >= 0 && locBoard[z+i][y-i][x] == letter)
                    count++;
                else break;
            }
            for(int i = 1; i < 1+5; i++)
            {
                if(z-i >= 0 && y+i < board.Y_SIZE && locBoard[z-i][y+i][x] == letter)
                    count++;
                else break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count = 1;
            //special diagonal all increasing
            for(int i = 1; i < 1+5; i++)
            {
                if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x+i <Board.X_SIZE && locBoard[z+i][y+i][x] == letter)
                    count++;
                else break;
            }
            for(int i = 1; i < 1+5; i++)
            {
                if(z-i >= 0 && y-i >= 0 && x-i >= 0 && locBoard[z-i][y-i][x] == letter)
                    count++;
                else break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count = 1;
            //special diagonal all increasing except x
            for(int i = 1; i < 1+5; i++)
            {
                if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x-i >= 0 && locBoard[z+i][y+i][x] == letter)
                    count++;
                else break;
            }
            for(int i = 1; i < 1+5; i++)
            {
                if(z-i >= 0 && y-i >= 0 && x+i < Board.X_SIZE && locBoard[z-i][y-i][x] == letter)
                    count++;
                else break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count = 1;
            //special diagonal all decreasing except y
            for(int i = 1; i < 1+5; i++)
            {
                if(z-i >= 0 && y+i < Board.Y_SIZE && x-i >= 0 && locBoard[z-i][y+i][x-i] == letter)
                    count++;
                else break;
            }
            for(int i = 1; i < 1+5; i++)
            {
                if(z+i < Board.Z_SIZE && y-i >= 0 && x+i < Board.X_SIZE && locBoard[z+i][y-i][x+i] == letter)
                    count++;
                else break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            count = 1;
            //special diagonal all increasing except z
            for(int i = 1; i < 1+5; i++)
            {
                if(z-i >= 0 && y+i < Board.Y_SIZE && x+i < Board.X_SIZE && locBoard[z-i][y+i][x+i] == letter)
                    count++;
                else break;
            }
            for(int i = 1; i < 1+5; i++)
            {
                if(z+i < Board.Z_SIZE && y-i >= 0 && x-i >= 0 && locBoard[z-i][y-i][x] == letter)
                    count++;
                else break;
            }
            grade += (Math.pow(10, count-1)) * VERT_DIAGONAL;

            moves.set(j, new MoveGrades(moves.get(j), moves.get(j).getY(), grade));
        }

        //to beat straight-line, remove after adding look-ahead
        Move bestMove = null;
        char opponent = (letter == Board.BLUE)? Board.RED : Board.BLUE;
        int oppCount = 0;
        int notFilled = 0;
        for(int i = 0; i<Board.Z_SIZE; i++)
        {
            for(int j = 0; j<Board.Y_SIZE; j++)
            {
                for(int k = 0; k<Board.X_SIZE; k++)
                {
                    //increase x
                    if(locBoard[i][j][k] == opponent && k <= 2)
                    {
                        for(int a = k; a < Board.X_SIZE; a++)
                        {
                            if(locBoard[i][j][a] == opponent) oppCount++;
                            else notFilled = a;
                        }
                        if(oppCount == 5 && (j == 0 || locBoard[i][j-1][notFilled] == '-'))
                             return new Move(notFilled, i);
                    }

                    //decrease x
                    if(locBoard[i][j][k] == opponent && k >=6)
                    {
                        for(int a = k; a >= 0; a--)
                        {
                            if(locBoard[i][j][a] == opponent) oppCount++;
                            else notFilled = a;
                        }
                        if(oppCount == 5 && (j == 0 || locBoard[i][j-1][notFilled] == '-'))
                            return new Move(notFilled, i);
                    }

                    //increase y
                    if(locBoard[i][j][k] == opponent && j <= 1)
                    {
                        for(int a = j; a < Board.Y_SIZE; a++)
                        {
                            if(locBoard[i][a][k] == opponent) oppCount++;
                            else notFilled = a;
                        }
                        if(oppCount == 5 && (notFilled == 0 || locBoard[i][notFilled-1][k] == '-'))
                            return new Move(k, i);
                    }

                    //decrease y
                    if(locBoard[i][j][k] == opponent && j >=6)
                    {
                        for(int a = j; a >= 0; a--)
                        {
                            if(locBoard[i][a][k] == opponent) oppCount++;
                            else notFilled = a;
                        }
                        if(oppCount == 5 && (notFilled == 0 || locBoard[i][notFilled-1][k] == '-'))
                            return new Move(k, i);
                    }

                    //increase z
                    if(locBoard[i][j][k] == opponent && i <= 2)
                    {
                        for(int a = i; a < Board.Z_SIZE; a++)
                        {
                            if(locBoard[a][j][k] == opponent) oppCount++;
                            else notFilled = a;
                        }
                        if(oppCount == 5 && (j == 0 || locBoard[notFilled][j-1][k] == '-'))
                            return new Move(k, notFilled);
                    }

                    //decrease z
                    if(locBoard[i][j][k] == opponent && i >=6)
                    {
                        for(int a = i; a >= 0; a--)
                        {
                            if(locBoard[a][j][k] == opponent) oppCount++;
                            else notFilled = a;
                        }
                        if(oppCount == 5 && (j == 0 || locBoard[notFilled][j-1][k] == '-'))
                            return new Move(k, notFilled);
                    }
                }
            }
        }

        for(int i = 0; i<moves.size(); i++)
        {
            if(moves.get(i).getGrade() > moves.get(largestGrade).getGrade())
            {
                largestGrade = i;
                bestMove = moves.get(i);
            }
        }
        return new Move(bestMove.getX(),bestMove.getZ());//no look ahead, just best move
    }



    public Player freshCopy()
    {
        return new AkiyamaDas(letter);
    }
}