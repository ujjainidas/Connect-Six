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
    private char oppLetter;
    private String name;
    private boolean goesFirst;
    private int turnNum;

    public AkiyamaDas(char letter)
    {
        super("AkiyamaDas",letter);
        turnNum = 0;
        goesFirst = true;
        oppLetter = (letter == 'R')? 'B' : 'R';
    }

    /**
     * calls the recursive method to calculate the move
     * @param board the current board of the game
     * @return what recurive method returns
     */
    public Move getMove(Board board)
    {
        char[][][] temp = board.getBoard();
        turnNum++;
        boolean winnerPresent = true;
        int pieceCount = 0;

//        System.out.println(board.getLocation(3, 6, 3));

        ArrayList<MoveGrades> moves = new ArrayList<MoveGrades>();

        for(int i = 0; i<Board.Z_SIZE; i++)
        {
            for(int j = 6; j>=0; j--)
            {
                for(int k = 0; k < Board.X_SIZE; k++)
                {
                    if (board.getBoard()[i][j][k] == Board.EMPTY)
                    {
                        continue;
                    }
                    else
                    {
                        winnerPresent = false;
                        pieceCount++;
//                        System.out.println("I am not empty: " + i + " " + j + " " + k + " -" +board.getBoard()[i][j][k]);
                    }
                }
            }
        }

        if(pieceCount == 1)
        {
            winnerPresent = true;
            turnNum = 1;
        }

        if(winnerPresent)
        {
            turnNum = 1;
            if(pieceCount == 0)
                goesFirst = true;
        }

        if(turnNum == 1 && goesFirst)
        {
            return new Move(3, 3);
        }

        for(int i = 0; i<Board.Z_SIZE; i++)
        {
            for (int j = 0; j < Board.X_SIZE; j++)
            {
                for(int k = 6; k >= 0; k--)
                {
                    if(board.getBoard()[i][k][j] == Board.EMPTY)
                    {
                        if(filterMoves(i, k, j, board))
                            break;
                        moves.add(new MoveGrades(new Move(j, i), k));
                        break;
                    }
                }
//                for(int k = Board.Y_SIZE-1; k>0; k--)
//                {
//                    if(board.getLocation(j, k, i) == Board.EMPTY && (k == 0 || board.getLocation(j, k-1, i) != Board.EMPTY))
//                    {
//                        moves.add(new MoveGrades(new Move(j, i), k));
//                        System.out.println(i + " " + k + " " +j);
//                    }
//                }
            }
        }

        int myGrade = 0;
        int oppGrade = 0;
        for(MoveGrades m : moves)
        {
            temp[m.getMove().getZ()][m.getY()][m.getMove().getX()] = getLetter();
            myGrade = getGrade(temp, getLetter());
            oppGrade = getGrade(temp, oppLetter);
            m.setGrade(myGrade - oppGrade);
            temp[m.getMove().getZ()][m.getY()][m.getMove().getX()] = Board.EMPTY;
            System.out.println(m.getZ() + " " + m.getY() + " " + m.getX() + " -" + m.getGrade());
        }

//        try
//        {
//            Thread.sleep(2000000000);
//        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.exit(0);

        int largestGrade = moves.get(0).getGrade();
        MoveGrades bestMove = moves.get(0);
        for(MoveGrades m : moves)
        {
            if(m.getGrade() > largestGrade)
            {
                largestGrade = m.getGrade();
                bestMove = new MoveGrades(new Move(m.getX(), m.getZ()), largestGrade);
            }
//            System.out.println(m.getMove().getX() + " " + m.getMove().getZ() + " \t********" + m.getGrade());
        }

//        System.out.println("\n\n************************************\t\t "+ bestMove.getMove().getX()+ " " + bestMove.getMove().getZ());
        return new Move(bestMove.getMove().getX(), bestMove.getMove().getZ());//no look ahead, just best move
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
        char opponent = (player == 'R')? 'B' : 'R';

        for(int z = 0; z<Board.Z_SIZE; z++)
        {
            for(int y = 6; y>=0; y--)
            {
                for(int x = 0; x<Board.X_SIZE; x++)
                {
                    if(array[z][y][x] != player) continue;
                    //increasing x
                        for(int i = x; i<Board.X_SIZE; i++)
                        {
                            if(array[z][y][i] == player)
                                count++;
                            else if(array[z][y][i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //decreasing x
                        for(int i = x; i >= 0; i--)
                        {
                            if(array[z][y][i] == player)
                                count++;
                            else if(array[z][y][i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //increasing z
                        for(int i = z; i<Board.Z_SIZE; i++)
                        {
                            if(array[i][y][x] == player)
                                count++;
                            else if(array[i][y][x] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //decreasing z
                        for(int i = z; i >= 0; i--)
                        {
                            if(array[i][y][x] == player)
                                count++;
                            else if(array[i][y][x] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //increasing y
                        for(int i = y; i>=0; i--)
                        {
                            if(array[z][i][x] == player)
                                count++;
                            else if(array[z][i][x] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERTICAL;
                    count = 0;

                    //decreasing y
                        for(int i = y; i < Board.Y_SIZE; i++)
                        {
                            if(array[z][i][x] == player)
                                count++;
                            else if(array[z][i][x] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERTICAL;
                    count = 0;

                    //increasing xz
                        for(int i = 0; i<8; i++)
                        {
                            if(x+i < Board.X_SIZE && z+i < Board.Z_SIZE && array[z+i][y][x+i] == player)
                            {
                                count++;
//                                System.out.println("I added: " + array[x+i][y][z+i]);
//                                System.out.println(x+i);
//                                System.out.println(y);
//                                System.out.println(z+i);
//                                System.out.println(i);
//                                System.out.println(player + "\n\n");
                            }
                            else if(x+i < Board.X_SIZE && z+i < Board.Z_SIZE && array[z+i][y][x+i] == opponent) break;
                        }
//                        System.out.println(array[x+1][y][z+1]);
//                        System.out.println(x+1);
//                        System.out.println(y);
//                        System.out.println(z+1);
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //decreasing xz
                        for(int i = 0; i<8; i++)
                        {
                            if(x-i >= 0 && z-i >= 0 && array[z-i][y][x-i] == player)
                                count++;
                            else if(x-i >= 0 && z-i >= 0 && array[z-i][y][x-i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //increasing x decreasing z
                        for(int i = 0; i<8; i++)
                        {
                            if(x+i < Board.X_SIZE && z-i >= 0 && array[z-i][y][x+i] == player)
                                count++;
                            else if(x+i < Board.X_SIZE && z-i >= 0 && array[z-i][y][x+i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //decreasing x increasing z
                        for(int i = 0; i<8; i++)
                        {
                            if(x-i >= 0 && z+i < Board.Z_SIZE && array[z+i][y][x-i] == player)
                                count++;
                            else if(x-i >= 0 && z+i < Board.Z_SIZE && array[z+i][y][x-i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*FLAT;
                    count = 0;

                    //increasing xy
                        for(int i = 0; i<8; i++)
                        {
                            if(x+i < Board.X_SIZE && y-i >= 0 && array[z][y-i][x+i] == player)
                                count++;
                            else if(x+i < Board.X_SIZE && y-i >= 0 && array[z][y-i][x+i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing xy
                        for(int i = 0; i<8; i++)
                        {
                            if(x-i >= 0 && y+i < Board.Y_SIZE && array[z][y+i][x-i] == player)
                                count++;
                            else if(x-i >= 0 && y+i < Board.Y_SIZE && array[z][y+i][x-i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing x decreasing y
                        for(int i = 0; i<8; i++)
                        {
                            if(x+i < Board.X_SIZE && y+i < Board.Y_SIZE && array[z][y+i][x+i] == player)
                                count++;
                            else if(x+i < Board.X_SIZE && y+i < Board.Y_SIZE && array[z][y+i][x+i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing x increasing y
                        for(int i = 0; i<8; i++)
                        {
                            if(x-i >= 0 && y-i >= 0 && array[z][y-i][x-i] == player)
                                count++;
                            else if(x-i >= 0 && y-i >= 0 && array[z][y-i][x-i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing yz
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y-i >= 0 && array[z+i][y-i][x] == player)
                                count++;
                            else if(z+i < Board.Z_SIZE && y-i >= 0 && array[z+i][y-i][x] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing yz
                        for(int i = 0; i<8; i++)
                        {
                            if(z-i >= 0 && y+i < Board.Y_SIZE && array[z-i][y+i][x] == player)
                                count++;
                            else if(z-i >= 0 && y+i < Board.Y_SIZE && array[z-i][y+i][x] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing y decreasing z
                        for(int i = 0; i<8; i++)
                        {
                            if(z-i >= 0 && y-i >= 0 && array[z-i][y-i][x] == player)
                                count++;
                            else if(z-i >= 0 && y-i >= 0 && array[z-i][y-i][x] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing y increasing z
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && array[z+i][y+i][x] == player)
                                count++;
                            else if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && array[z+i][y+i][x] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing xyz
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y-i >= 0 && x+i < Board.X_SIZE && array[z+i][y-i][x+i] == player)
                                count++;
                            else if(z+i < Board.Z_SIZE && y-i >= 0 && x+i < Board.X_SIZE && array[z+i][y-i][x+i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing xyz
                        for(int i = 0; i<8; i++)
                        {
                            if(z-i >= 0 && y+i < Board.Y_SIZE && x-i >= 0 && array[z-i][y+i][x-i] == player)
                                count++;
                            else if(z-i >= 0 && y+i < Board.Y_SIZE && x-i >= 0 && array[z-i][y+i][x-i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing zy decreasing x
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y-i >= 0 && x-i >= 0 && array[z+i][y-i][x-i] == player)
                                count++;
                            else if(z+i < Board.Z_SIZE && y-i >= 0 && x-i >= 0 && array[z+i][y-i][x-i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing zy increasing x
                        for(int i = 0; i<8; i++)
                        {
                            if (z - i >= 0 && y+i < Board.Y_SIZE && x + i < Board.X_SIZE && array[z - i][y + i][x + i] == player)
                                count++;
                            else if (z - i >= 0 && y+i < Board.Y_SIZE && x + i < Board.X_SIZE && array[z - i][y + i][x + i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing y decreasing xz
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y-i >= 0 && x-i >= 0 && array[z+i][y-i][x-i] == player)
                                count++;
                            else if(z+i < Board.Z_SIZE && y-i >= 0 && x-i >= 0 && array[z+i][y-i][x-i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing y increasing xz
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x+i < Board.X_SIZE && array[z+i][y+i][x+i] == player)
                                count++;
                            else if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x+i < Board.X_SIZE && array[z+i][y+i][x+i] == opponent) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //increasing xy decreasing z
                        for(int i = 0; i<8; i++)
                        {
                            if(z-i >= 0 && y-i >=0 && x+i < Board.X_SIZE && array[z-i][y-i][x+i] == player)
                                count++;
                            else if(z-i >= 0 && y-i >=0 && x+i < Board.X_SIZE && array[z-i][y-i][x+i] == opponent)break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;

                    //decreasing xy increasing z
                        for(int i = 0; i<8; i++)
                        {
                            if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x-i >= 0 && array[z+i][y+i][x-i] == player)
                                count++;
                            else if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x-i >= 0 && array[z+i][y+i][x-i] == player) break;
                        }
                    grade +=(int)(Math.pow(10, count-1))*VERT_DIAGONAL;
                    count = 0;
                }
            }
        }
        return grade;
    }

    public boolean filterMoves(int z, int y, int x, Board board)
    {
        if(y != Board.Y_SIZE-1) return false;

        //filters out bad moves
        if(z > 0 && board.getBoard()[z-1][y][x] == Board.EMPTY)
        {
            if(z < Board.Z_SIZE-1 && board.getBoard()[z+1][y][x] == Board.EMPTY)
            {
                if(x > 0 && board.getBoard()[z][y][x-1] == Board.EMPTY)
                {
                    if(x < Board.X_SIZE-1 && board.getBoard()[z][y][x+1] == Board.EMPTY)
                    {
                        if(board.getBoard()[z+1][y][x+1] == Board.EMPTY)
                        {
                            if(board.getBoard()[z-1][y][x-1] == Board.EMPTY)
                            {
                                if(board.getBoard()[z+1][y][x-1] == Board.EMPTY)
                                {
                                    if(board.getBoard()[z-1][y][x+1] == Board.EMPTY)
                                    {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if(z == 0 && x == 0)
        {
            if(board.getBoard()[z+1][y][x] == Board.EMPTY)
            {
                if(board.getBoard()[z][y][x+1] == Board.EMPTY)
                {
                    if(board.getBoard()[z+1][y][x+1] == Board.EMPTY)
                    {
                        return true;
                    }
                }
            }
        }
        else if(z == Board.Z_SIZE-1 && x == 0)
        {
            if(board.getBoard()[z-1][y][x] == Board.EMPTY)
            {
                if(board.getBoard()[z][y][x+1] == Board.EMPTY)
                {
                    if(board.getBoard()[z-1][y][x+1] == Board.EMPTY)
                    {
                        return true;
                    }
                }
            }
        }
        else if(z == 0 && x == Board.X_SIZE-1)
        {
            if(board.getBoard()[z+1][y][x] == Board.EMPTY)
            {
                if(board.getBoard()[z][y][x-1] == Board.EMPTY)
                {
                    if(board.getBoard()[z+1][y][x-1] == Board.EMPTY)
                    {
                        return true;
                    }
                }
            }
        }
        else if(z == Board.Z_SIZE-1 && x == Board.X_SIZE-1)
        {
            if(board.getBoard()[z-1][y][x] == Board.EMPTY)
            {
                if(board.getBoard()[z][y][x-1] == Board.EMPTY)
                {
                    if(board.getBoard()[z-1][y][x-1] == Board.EMPTY)
                    {
                        return true;
                    }
                }
            }
        }
        else if(z == 0)
        {
            if(board.getBoard()[z+1][y][x] == Board.EMPTY)
            {
                if(board.getBoard()[z][y][x+1] == Board.EMPTY)
                {
                    if(board.getBoard()[z+1][y][x+1] == Board.EMPTY)
                    {
                        if(board.getBoard()[z+1][y][x-1] == Board.EMPTY)
                        {
                            if(board.getBoard()[z][y][x-1] == Board.EMPTY)
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        else if(z == Board.Z_SIZE-1)
        {
            if(board.getBoard()[z-1][y][x] == Board.EMPTY)
            {
                if(board.getBoard()[z][y][x+1] == Board.EMPTY)
                {
                    if(board.getBoard()[z-1][y][x+1] == Board.EMPTY)
                    {
                        if(board.getBoard()[z-1][y][x-1] == Board.EMPTY)
                        {
                            if(board.getBoard()[z][y][x-1] == Board.EMPTY)
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        else if(x == 0)
        {
            if(board.getBoard()[z+1][y][x] == Board.EMPTY)
            {
                if(board.getBoard()[z][y][x+1] == Board.EMPTY)
                {
                    if(board.getBoard()[z+1][y][x+1] == Board.EMPTY)
                    {
                        if(board.getBoard()[z-1][y][x+1] == Board.EMPTY)
                        {
                            if(board.getBoard()[z-1][y][x] == Board.EMPTY)
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        else if(x == Board.X_SIZE-1)
        {
            if(board.getBoard()[z+1][y][x] == Board.EMPTY)
            {
                if(board.getBoard()[z][y][x-1] == Board.EMPTY)
                {
                    if(board.getBoard()[z+1][y][x-1] == Board.EMPTY)
                    {
                        if(board.getBoard()[z-1][y][x-1] == Board.EMPTY)
                        {
                            if(board.getBoard()[z-1][y][x] == Board.EMPTY)
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }



    public Player freshCopy()
    {
        return new AkiyamaDas(letter);
    }
}