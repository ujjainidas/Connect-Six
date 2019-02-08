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
    private boolean goesFirst;
    private int turnNum;

    public AkiyamaDas(char letter)
    {
        super("AkiyamaDas",letter);
        turnNum = 0;
        goesFirst = false;
    }

    /**
     * calls the recursive method to calculate the move
     * @param board the current board of the game
     * @return what recurive method returns
     */
    public Move getMove(Board board)
    {
//        char[][][] temp = board.getBoard();
//        turnNum++;
//        locBoard = board.getBoard();
//
//        ArrayList<MoveGrades> moves = new ArrayList<MoveGrades>();
//
//        for(int i = 0; i<Board.Z_SIZE; i++)
//        {
//            for(int j = 0; j<Board.Y_SIZE; j++)
//            {
//                for(int k = 0; k<Board.X_SIZE; k++)
//                {
//                    if (locBoard[i][j][k] == Board.EMPTY)
//                    {
//                        continue;
//                    }
//                    else
//                    {
//                        if(turnNum == 1)
//                            goesFirst = false;
//                    }
//                }
//            }
//        }
//
//        System.out.println(turnNum + " " + goesFirst);
//
//        if(turnNum == 1 && goesFirst)
//        {
//            return new Move(3, 3);
//        }
//
//        for(int i = 0; i<Board.Z_SIZE; i++)
//        {
//            for (int j = 0; j < Board.X_SIZE; j++)
//            {
//                for(int k = 0; k < Board.Y_SIZE; k++)
//                {
//                    if(locBoard[i][k][j] == Board.EMPTY)
//                    {
//                        moves.add(new MoveGrades(new Move(j, i), k));
//                        System.out.println("added");
//                        break;
//                    }
//                }
//            }
//        }
//
//        System.out.println(moves.size());
//
//        int largestGrade = moves.get(0).getGrade();
//        Move bestMove = moves.get(0);
//        for(MoveGrades m : moves)
//        {
//            temp[m.getZ()][m.getY()][m.getX()] = letter;
//            if(getGrade(temp, letter) > largestGrade)
//            {
//                bestMove = new Move(m.getX(), m.getZ());
//                largestGrade = getGrade(temp, letter);
//            }
//
//        }


//        for(int i = moves.size()-1; i>=0; i++)
//        {
//            if(moves.get(i).getX() > 0 && locBoard[moves.get(i).getZ()][moves.get(i).getY()][moves.get(i).getX()-1] == Board.EMPTY)
//            {
//                if(moves.get(i).getX() < Board.X_SIZE && locBoard[moves.get(i).getZ()][moves.get(i).getY()][moves.get(i).getX()+1] == Board.EMPTY)
//                {
//                    if(moves.get(i).getZ() > 0 && locBoard[moves.get(i).getZ()-1][moves.get(i).getY()][moves.get(i).getX()] == Board.EMPTY)
//                    {
//
//                    }
//                }
//            }
//        }

        return new Move(0, 0);//no look ahead, just best move
    }

    public Move calculateMove(Board board, int layers)
    {
        //from board and valid moves, calulate grade for each one
        //for best 4 grades, call calculateMove with layers +1
        //once reaches 2, break, store arraylist of all grades in last layer, calulate highest grade and send to getMove()

        return new Move(0, 0);
    }

    public int getGrade(char[][][] array, char player)
    {
        int grade = 0;
        int count = 0;

        for(int z = 0; z<Board.Z_SIZE; z++)
        {
            for(int y = 0; y<Board.Y_SIZE; y++)
            {
                for(int x = 0; x<Board.X_SIZE; x++)
                {
                    //increasing x
                        for(int i = x; i<Board.X_SIZE; i++)
                        {
                            if(array[z][y][i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //decreasing x
                        for(int i = x; i >= 0; i--)
                        {
                            if(array[z][y][i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //increasing z
                        for(int i = z; i<Board.Z_SIZE; i++)
                        {
                            if(array[i][y][x] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //decreasing z
                        for(int i = z; i >= 0; i--)
                        {
                            if(array[i][y][x] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //increasing y
                        for(int i = y; i<Board.Y_SIZE; i++)
                        {
                            if(array[z][i][x] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERTICAL;
                    count = 0;

                    //decreasing y
                        for(int i = y; i >= 0; i--)
                        {
                            if(array[z][i][x] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERTICAL;
                    count = 0;

                    //increasing xz
                        for(int i = 0; i<8; i++)
                        {
                            if(x+i < Board.X_SIZE && z+i < Board.Z_SIZE && array[z+i][y][x+i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //decreasing xz
                        for(int i = 0; i<8; i++)
                        {
                            if(x-i >= 0 && z-i >= 0 && array[z-i][y][x-i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //increasing x decreasing z
                        for(int i = 0; i<8; i++)
                        {
                            if(x+i < Board.X_SIZE && z-i >= 0 && array[z-i][y][x+i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //decreasing x increasing z
                        for(int i = 0; i<8; i++)
                        {
                            if(x-i >= 0 && z+i < Board.Z_SIZE && array[z+i][y][x-i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //increasing xy
                        for(int i = 0; i<8; i++)
                        {
                            if(x+i < Board.X_SIZE && y+i < Board.Y_SIZE && array[z][y+i][x+i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing xy
                        for(int i = 0; i<8; i++)
                        {
                            if(x-i >= 0 && y-i >= 0 && array[z][y-i][x-i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing x decreasing y
                        for(int i = 0; i<8; i++)
                        {
                            if(x+i < Board.X_SIZE && y-i >= 0 && array[z][y-i][x+i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing x increasing y
                        for(int i = 0; i<8; i++)
                        {
                            if(x-i >= 0 && y+i < Board.Y_SIZE && array[z][y+i][x-i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing yz
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && array[z+i][y+i][x] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing yz
                        for(int i = 0; i<8; i++)
                        {
                            if(z-i >= 0 && y-i >= 0 && array[z-i][y-i][x] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing y decreasing z
                        for(int i = 0; i<8; i++)
                        {
                            if(z-i >= 0 && y+i < Board.Y_SIZE && array[z-i][y+i][x] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing y increasing z
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y-i >= 0 && array[z+i][y-i][x] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing xyz
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x+i < Board.X_SIZE && array[z+i][y+i][x+i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing xyz
                        for(int i = 0; i<8; i++)
                        {
                            if(z-i >= 0 && y-i >= 0 && x-i >= 0 && array[z-i][y-i][x-i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing zy decreasing x
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x-i >= 0 && array[z+i][y+i][x-i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing zy increasing x
                        for(int i = 0; i<8; i++)
                        {
                            if (z - i >= 0 && y - i >= 0 && x + i < Board.X_SIZE && array[z - i][y - i][x + i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing y decreasing xz
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y-i >= 0 && x-i >= 0 && array[z+i][y-i][x-i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing y increasing xz
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y-i >= 0 && x+i < Board.X_SIZE && array[z+i][y-i][x+i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing xy decreasing z
                        for(int i = 0; i<8; i++)
                        {
                            if(z-i >= 0 && y+i < Board.Y_SIZE && x+i < Board.X_SIZE && array[z-i][y+i][x+i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing xy increasing z
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y-i >= 0 && x-i >= 0 && array[z+i][y-i][x-i] == player)
                                count++;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                }
            }
        }
        return grade;
    }



    public Player freshCopy()
    {
        return new AkiyamaDas(letter);
    }
}