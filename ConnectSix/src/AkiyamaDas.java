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
        goesFirst = false;
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
//            System.out.println(m.getZ() + " " + m.getY() + " " + m.getX() + " -" + m.getGrade());
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
        int count1 = 0, count2 = 0;
        int numCheck = 0;
        int subtract = 0;
        int twoCount = 0, threeCount = 0, fourCount = 0, fiveCount = 0;
        int flat = 0, vert = 0, vert_diag = 0;
        char opponent = (player == 'R')? 'B' : 'R';
        if(opponent == getLetter())
        {
            flat = vert = vert_diag = FLAT;
        }
        else
        {
            flat = 4;
            vert = 3;
            vert_diag = 2;
        }

        for(int z = 0; z<Board.Z_SIZE; z++)
        {
            for(int y = 6; y>=0; y--)
            {
                for(int x = 0; x<Board.X_SIZE; x++)
                {
                    if(array[z][y][x] != player) {
                        continue;
                    }
                    //increasing x
                        for(int i = x; i<Board.X_SIZE; i++)
                        {
                            numCheck = i;
                            if(array[z][y][i] == player) {
                                count1++;
                            }
                            else if(array[z][y][i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }

                        subtract += numCheck-x;
//                    grade +=(int)(Math.pow(10, count-1))*flat;
//                    count = 0;

                    //decreasing x
                        for(int i = x; i >= 0; i--)
                        {
                            numCheck = i;
                            if(array[z][y][i] == player)
                            {
                                count2++;
                            }
                            else if(array[z][y][i] == opponent)
                            {
                                numCheck = i + 1;
                                break;
                            }
                        }
                        subtract += x-numCheck;
                    if(subtract >= 5) grade +=(int)(Math.pow(10, (count1 + count2)-2))*flat;
                    if((count1+count2)-1 == 3) threeCount++;
                    else if((count1+count2)-1 == 4) fourCount++;
                    else if((count1+count2)-1 == 5) fourCount++;
                    count1 = 0;
                    count2 = 0;
                    subtract = 0;

                    //increasing z
                        for(int i = z; i<Board.Z_SIZE; i++)
                        {
                            numCheck = i;
                            if(array[i][y][x] == player) {
                                count1++;
                            }
                            else if(array[i][y][x] == opponent)
                            {
                                numCheck = i-1;
                                break;
                            }
                        }
                        subtract += numCheck-z;
//                    grade +=(int)(Math.pow(10, count-1))*flat;
//                    count = 0;

                    //decreasing z
                        for(int i = z; i >= 0; i--)
                        {
                            numCheck = i;
                            if(array[i][y][x] == player) {
                                count2++;
                            }
                            else if(array[i][y][x] == opponent)
                            {
                                numCheck = i + 1;
                                break;
                            }
                        }
                        subtract += z-numCheck;
                    if(subtract >= 5) grade +=(int)(Math.pow(10, (count1 + count2)-2))*flat;
                    if((count1+count2)-1 == 3) threeCount++;
                    else if((count1+count2)-1 == 4) fourCount++;
                    else if((count1+count2)-1 == 5) fourCount++;
                    count1 = 0;
                    count2 = 0;
                    subtract = 0;

                    //increasing y
                        for(int i = y; i>=0; i--)
                        {
                            numCheck=i;
                            if(array[z][i][x] == player) {
                                count1++;
                            }
                            else if(array[z][i][x] == opponent)
                            {
                                numCheck = i + 1;
                                break;
                            }
                        }
                        subtract += y-numCheck;
//                    grade +=(int)(Math.pow(10, count-1))*vert;
//                    count = 0;

                    //decreasing y
                        for(int i = y; i < Board.Y_SIZE; i++)
                        {
                            numCheck = i;
                            if(array[z][i][x] == player)
                            {
                                count2++;
                            }
                            else if(array[z][i][x] == opponent)
                            {
                                numCheck = i-1;
                                break;
                            }
                        }
                        subtract += numCheck-y;
                    if(subtract >= 5) grade +=(int)(Math.pow(10, (count1 + count2)-2))*vert;
                    if((count1+count2)-1 == 3) threeCount++;
                    else if((count1+count2)-1 == 4) fourCount++;
                    else if((count1+count2)-1 == 5) fourCount++;
                    count1 = 0;
                    count2 = 0;
                    subtract = 0;

                    //increasing xz
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(x+i >= Board.X_SIZE || z+i >= Board.Z_SIZE)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(x+i < Board.X_SIZE && z+i < Board.Z_SIZE && array[z+i][y][x+i] == player) {
                                count1++;
                            }
                            else if(x+i < Board.X_SIZE && z+i < Board.Z_SIZE && array[z+i][y][x+i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
//                    grade +=(int)(Math.pow(10, count-1))*flat;
//                    count = 0;

                    //decreasing xz
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(x-i <0 || z-i <0)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(x-i >= 0 && z-i >= 0 && array[z-i][y][x-i] == player) {
                                count2++;
                            }
                            else if(x-i >= 0 && z-i >= 0 && array[z-i][y][x-i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
                    if(subtract >= 5) grade +=(int)(Math.pow(10, (count1 + count2)-2))*flat;
                    if((count1+count2)-1 == 3) threeCount++;
                    else if((count1+count2)-1 == 4) fourCount++;
                    else if((count1+count2)-1 == 5) fourCount++;
                    count1 = 0;
                    count2 = 0;
                    subtract = 0;

                    //increasing x decreasing z
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(x+i >= Board.X_SIZE || z-i < 0)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(x+i < Board.X_SIZE && z-i >= 0 && array[z-i][y][x+i] == player) {
                                count1++;
                            }
                            else if(x+i < Board.X_SIZE && z-i >= 0 && array[z-i][y][x+i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
//                    grade +=(int)(Math.pow(10, count-1))*flat;
//                    count = 0;

                    //decreasing x increasing z
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(x-i <0 || z+i >= Board.Z_SIZE)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(x-i >= 0 && z+i < Board.Z_SIZE && array[z+i][y][x-i] == player) {
                                count2++;
                            }
                            else if(x-i >= 0 && z+i < Board.Z_SIZE && array[z+i][y][x-i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
                    if(subtract >= 5) grade +=(int)(Math.pow(10, (count1 + count2)-2))*flat;
                    if((count1+count2)-1 == 3) threeCount++;
                    else if((count1+count2)-1 == 4) fourCount++;
                    else if((count1+count2)-1 == 5) fourCount++;
                    count1 = 0;
                    count2 = 0;
                    subtract = 0;

                    //increasing xy
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(x+i >= Board.X_SIZE || y-i <0)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(x+i < Board.X_SIZE && y-i >= 0 && array[z][y-i][x+i] == player) {
                                count1++;
                            }
                            else if(x+i < Board.X_SIZE && y-i >= 0 && array[z][y-i][x+i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
//                    grade +=(int)(Math.pow(10, count-1))*vert_diag;
//                    count = 0;

                    //decreasing xy
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(x-i <0 || y+i >= Board.Y_SIZE)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(x-i >= 0 && y+i < Board.Y_SIZE && array[z][y+i][x-i] == player) {
                                count2++;
                            }
                            else if(x-i >= 0 && y+i < Board.Y_SIZE && array[z][y+i][x-i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
                    if(subtract >= 5) grade +=(int)(Math.pow(10, (count1 + count2)-2))*vert_diag;
                    if((count1+count2)-1 == 3) threeCount++;
                    else if((count1+count2)-1 == 4) fourCount++;
                    else if((count1+count2)-1 == 5) fourCount++;
                    count1 = 0;
                    count2 = 0;
                    subtract = 0;

                    //increasing x decreasing y
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(x+i >= Board.X_SIZE || y+i >= Board.Y_SIZE)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(x+i < Board.X_SIZE && y+i < Board.Y_SIZE && array[z][y+i][x+i] == player) {
                                count1++;
                            }
                            else if(x+i < Board.X_SIZE && y+i < Board.Y_SIZE && array[z][y+i][x+i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
//                    grade +=(int)(Math.pow(10, count-1))*vert_diag;
//                    count = 0;

                    //decreasing x increasing y
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(x-i <0|| y-i <0)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(x-i >= 0 && y-i >= 0 && array[z][y-i][x-i] == player) {
                                count2++;
                            }
                            else if(x-i >= 0 && y-i >= 0 && array[z][y-i][x-i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
                    if(subtract >= 5) grade +=(int)(Math.pow(10, (count1 + count2)-2))*vert_diag;
                    if((count1+count2)-1 == 3) threeCount++;
                    else if((count1+count2)-1 == 4) fourCount++;
                    else if((count1+count2)-1 == 5) fourCount++;
                    count1 = 0;
                    count2 = 0;
                    subtract = 0;

                    //increasing yz
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(z+i >= Board.Z_SIZE || y-i <0)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(z+i < Board.Z_SIZE && y-i >= 0 && array[z+i][y-i][x] == player) {
                                count1++;
                            }
                            else if(z+i < Board.Z_SIZE && y-i >= 0 && array[z+i][y-i][x] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
//                    grade +=(int)(Math.pow(10, count-1))*vert_diag;
//                    count = 0;

                    //decreasing yz
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(z-i <0 || y+i >= Board.Y_SIZE)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(z-i >= 0 && y+i < Board.Y_SIZE && array[z-i][y+i][x] == player) {
                                count2++;
                            }
                            else if(z-i >= 0 && y+i < Board.Y_SIZE && array[z-i][y+i][x] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
                    if(subtract >= 5) grade +=(int)(Math.pow(10, (count1 + count2)-2))*vert_diag;
                    if((count1+count2)-1 == 3) threeCount++;
                    else if((count1+count2)-1 == 4) fourCount++;
                    else if((count1+count2)-1 == 5) fourCount++;
                    count1 = 0;
                    count2 = 0;
                    subtract = 0;

                    //increasing y decreasing z
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(z-i <0|| y-i <0)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(z-i >= 0 && y-i >= 0 && array[z-i][y-i][x] == player) {
                                count1++;
                            }
                            else if(z-i >= 0 && y-i >= 0 && array[z-i][y-i][x] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
//                    grade +=(int)(Math.pow(10, count-1))*vert_diag;
//                    count = 0;

                    //decreasing y increasing z
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(z+i >= Board.Z_SIZE || y+i >= Board.Y_SIZE)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && array[z+i][y+i][x] == player) {
                                count2++;
                            }
                            else if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && array[z+i][y+i][x] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
                    if(subtract >= 5) grade +=(int)(Math.pow(10, (count1 + count2)-2))*vert_diag;
                    if((count1+count2)-1 == 3) threeCount++;
                    else if((count1+count2)-1 == 4) fourCount++;
                    else if((count1+count2)-1 == 5) fourCount++;
                    count1 = 0;
                    count2 = 0;
                    subtract = 0;

                    //increasing xyz
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(z+i >= Board.Z_SIZE || y-i <0 || x+i >= Board.X_SIZE)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(z+i < Board.Z_SIZE && y-i >= 0 && x+i < Board.X_SIZE && array[z+i][y-i][x+i] == player) {
                                count1++;
                            }
                            else if(z+i < Board.Z_SIZE && y-i >= 0 && x+i < Board.X_SIZE && array[z+i][y-i][x+i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
//                    grade +=(int)(Math.pow(10, count-1))*vert_diag;
//                    count = 0;

                    //decreasing xyz
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(z-i <0|| y+i >= Board.Y_SIZE || x-i <0)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(z-i >= 0 && y+i < Board.Y_SIZE && x-i >= 0 && array[z-i][y+i][x-i] == player) {
                                count2++;
                            }
                            else if(z-i >= 0 && y+i < Board.Y_SIZE && x-i >= 0 && array[z-i][y+i][x-i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
                    if(subtract >= 5) grade +=(int)(Math.pow(10, (count1 + count2)-2))*vert_diag;
                    if((count1+count2)-1 == 3) threeCount++;
                    else if((count1+count2)-1 == 4) fourCount++;
                    else if((count1+count2)-1 == 5) fourCount++;
                    count1 = 0;
                    count2 = 0;
                    subtract = 0;

                    //increasing zy decreasing x
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(z+i >= Board.Z_SIZE || y-i <0 || x-i <0)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(z+i < Board.Z_SIZE && y-i >= 0 && x-i >= 0 && array[z+i][y-i][x-i] == player) {
                                count1++;
                            }
                            else if(z+i < Board.Z_SIZE && y-i >= 0 && x-i >= 0 && array[z+i][y-i][x-i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
//                    grade +=(int)(Math.pow(10, count-1))*vert_diag;
//                    count = 0;

                    //decreasing zy increasing x
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(z-i <0 || y+i >= Board.Y_SIZE || x+i >= Board.X_SIZE)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if (z - i >= 0 && y+i < Board.Y_SIZE && x + i < Board.X_SIZE && array[z - i][y + i][x + i] == player) {
                                count2++;
                            }
                            else if (z - i >= 0 && y+i < Board.Y_SIZE && x + i < Board.X_SIZE && array[z - i][y + i][x + i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
                    if(subtract >= 5) grade +=(int)(Math.pow(10, (count1 + count2)-2))*vert_diag;
                    if((count1+count2)-1 == 3) threeCount++;
                    else if((count1+count2)-1 == 4) fourCount++;
                    else if((count1+count2)-1 == 5) fourCount++;
                    count1 = 0;
                    count2 = 0;
                    subtract = 0;

                    //increasing y decreasing xz
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(z+i >= Board.Z_SIZE || y-i <0 || x-i <0)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(z+i < Board.Z_SIZE && y-i >= 0 && x-i >= 0 && array[z+i][y-i][x-i] == player) {
                                count1++;
                            }
                            else if(z+i < Board.Z_SIZE && y-i >= 0 && x-i >= 0 && array[z+i][y-i][x-i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;


                    //decreasing y increasing xz
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(z+i >= Board.Z_SIZE || y+i >= Board.Y_SIZE || x+i >= Board.X_SIZE)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x+i < Board.X_SIZE && array[z+i][y+i][x+i] == player) {
                                count2++;
                            }
                            else if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x+i < Board.X_SIZE && array[z+i][y+i][x+i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
                    if(subtract >= 5) grade +=(int)(Math.pow(10, (count1 + count2)-2))*vert_diag;
                    if((count1+count2)-1 == 3) threeCount++;
                    else if((count1+count2)-1 == 4) fourCount++;
                    else if((count1+count2)-1 == 5) fourCount++;
                    count1 = 0;
                    count2 = 0;
                    subtract = 0;

                    //increasing xy decreasing z
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(z-i < 0 || y-i <0 || x+i >= Board.X_SIZE)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(z-i >= 0 && y-i >=0 && x+i < Board.X_SIZE && array[z-i][y-i][x+i] == player) {
                                count1++;
                            }
                            else if(z-i >= 0 && y-i >=0 && x+i < Board.X_SIZE && array[z-i][y-i][x+i] == opponent)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
//                    grade +=(int)(Math.pow(10, count-1))*vert_diag;
//                    count = 0;

                    //decreasing xy increasing z
                        for(int i = 0; i<8; i++)
                        {
                            numCheck = i;
                            if(z+i >= Board.Z_SIZE || y+i >= Board.Y_SIZE || x-i <0)
                            {
                                numCheck = i-1;
                                break;
                            }
                            if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x-i >= 0 && array[z+i][y+i][x-i] == player)
                            {
                                count2++;
                            }
                            else if(z+i < Board.Z_SIZE && y+i < Board.Y_SIZE && x-i >= 0 && array[z+i][y+i][x-i] == player)
                            {
                                numCheck = i - 1;
                                break;
                            }
                        }
                        subtract += numCheck;
                    if(subtract >= 5) grade +=(int)(Math.pow(10, (count1 + count2)-2))*vert_diag;
                    if((count1+count2)-1 == 3) threeCount++;
                    else if((count1+count2)-1 == 4) fourCount++;
                    else if((count1+count2)-1 == 5) fourCount++;
                    count1 = 0;
                    count2 = 0;
                    subtract = 0;
                }
            }
        }
//        grade += 20000*twoCount;
//        grade += 20500*threeCount;
//        grade += 30000*fourCount;
//        grade += 30500*fiveCount;
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


//    public ArrayList<MoveGrades> bestGrades(ArrayList<MoveGrades> moves)
//    {
//        for(int i = 0; i<moves.size(); i++)
//        {
//
//        }
//    }

    public Player freshCopy()
    {
        return new AkiyamaDas(getLetter());
    }
}

class MoveGrades extends Move
{
    Move m;
    int y;
    int grade;


    public MoveGrades(Move m, int y, int grade)
    {
        super(m.getX(), m.getZ());
        this.m = m;
        this.y = y;
        this.grade=grade;
    }

    public MoveGrades(Move m, int y)
    {
        super(m.getX(), m.getZ());
        this.m = m;
        this.y = y;
        grade = 0;
    }

    public Move getMove() {
        return m;
    }

    public int getGrade() {
        return grade;
    }

    public void setMove(Move m) {
        this.m = m;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getY() {
        return y;
    }
}