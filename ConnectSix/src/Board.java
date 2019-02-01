/** This class stores the board's data and allows you to manipulate it */

import java.awt.*;
import java.util.*;

public class Board
{
    public static final int Z_SIZE=8, X_SIZE=8, Y_SIZE=7;
    public static final char TIE='T',EMPTY='-',PLAYING='-',RED='R',BLUE='B';
    private char[][][] board = new char[Z_SIZE][Y_SIZE][X_SIZE];
    private ArrayList<GamePolygon> polys = new ArrayList<>();
    int timer = 0;
    char winner = '-'; // 'R', '-', 'B', 'T'
    String winType ="";
    int needed=6;
    Color redWin = Color.red, blueWin = Color.blue;

    public Board()
    {
        for(int z=0; z < Z_SIZE; z++)
            for(int y=0; y < Y_SIZE; y++)
                for(int x=0; x < X_SIZE; x++)
                {
                    board[z][y][x] = '-';
                }

        for(int y = 0; y<Y_SIZE; y++)
        {
            int rowH = 125*(y+1) +10;
            for(int z = 0; z < Z_SIZE; z++)
            {
                int sheetHMod = 0 -14*z;
                for(int x = 0; x<X_SIZE; x++)
                {
                    int colW = 20 + 14*x;
                    int rowWMod = z*7;
                    int drawX = colW+rowWMod;
                    int drawY = rowH+sheetHMod;

                    Polygon p = new Polygon();
                    p.addPoint(drawX+8,drawY);
                    p.addPoint(drawX+22,drawY);

                    p.addPoint(drawX+14,drawY+15);
                    p.addPoint(drawX,drawY+15);

                    polys.add(new GamePolygon(drawX,drawY,new Location(x,y,z),p));
                }
            }
        }
    }

    public Board(Board b)
    {
        for(int z=0; z < Z_SIZE; z++)
            for(int y=0; y < Y_SIZE; y++)
                for(int x=0; x < X_SIZE; x++)
                {
                    board[z][y][x] = b.board[z][y][x];
                }
        this.winner = b.winner;
    }

    public ArrayList<GamePolygon> getPolys()
    {	return polys;	}


    public char[][][] getBoard()
    {
        return board;
    }

    public Location makeMove(Move m, char c)
    {
        Location placedAt = null;
        int y=0;
        int z=m.getZ();
        int x=m.getX();
        for(int level=Y_SIZE-1; level>=0; level--)
            if(board[z][level][x]=='-')
            {
                y=level;
                board[z][y][x] = c;
                placedAt =  new Location(x,y,z);
                break;
            }
        if(placedAt==null)
            return placedAt;

        // win checks

// straights
    // horizontal
        int hCount=1;

        for(int xx=x+1; xx<X_SIZE;xx++)
            if(board[z][y][xx]==c)
                hCount++;
            else
                break;
        for(int xx=x-1; xx>=0;xx--)
            if(board[z][y][xx]==c)
                hCount++;
            else
                break;

        if(hCount>=needed){
            winType="Horizontal";
            winner=c;
            char lowerCase = Character.toLowerCase(c);
            // flash code
            for(int xx=x; xx<X_SIZE;xx++)
                if(board[z][y][xx]==c)
                    board[z][y][xx]=lowerCase;
                else
                    break;
            for(int xx=x-1; xx>=0;xx--)
                if(board[z][y][xx]==c)
                    board[z][y][xx]=lowerCase;
                else
                    break;
            return placedAt;

        }

    // back
        int bCount=1;
        for(int zz=z+1; zz<Z_SIZE;zz++)
            if(board[zz][y][x]==c)
                bCount++;
            else
                break;
        for(int zz=z-1; zz>=0;zz--)
            if(board[zz][y][x]==c)
                bCount++;
            else
                break;
        if(bCount>=needed){
            winType="depth";
            winner=c;
            char lowerCase = Character.toLowerCase(c);
            for(int zz=z; zz<Z_SIZE;zz++)
                if(board[zz][y][x]==c)
                    board[zz][y][x]=lowerCase;
                else
                    break;
            for(int zz=z-1; zz>=0;zz--)
                if(board[zz][y][x]==c)
                    board[zz][y][x]=lowerCase;
                else
                    break;
            return placedAt;
        }

    // up
        int uCount=1;
        for(int yy=y+1; yy<Y_SIZE;yy++)
            if(board[z][yy][x]==c)
                uCount++;
            else
                break;
        for(int yy=y-1; yy>=0;yy--)
            if(board[z][yy][x]==c)
                uCount++;
            else
                break;
        if(uCount>=needed){
            winType="Up";
            winner=c;
            char lowerCase = Character.toLowerCase(c);
            for(int yy=y; yy<Y_SIZE;yy++)
                if(board[z][yy][x]==c)
                    board[z][yy][x]=lowerCase;
                else
                    break;
            for(int yy=y-1; yy>=0;yy--)
                if(board[z][yy][x]==c)
                    board[z][yy][x]=lowerCase;
                else
                    break;
            return placedAt;
        }


// diagonals on the x-plane
    // both increasing
        int idc=1;
        for(int change=1; change<=change+4;change++)
        if(z+change<Z_SIZE && y+change<Y_SIZE && board[z+change][y+change][x]==c)
            idc++;
        else
            break;
        for(int change=1; change<=change+4;change++)
            if(z-change>=0 && y-change>=0 && board[z-change][y-change][x]==c)
                idc++;
            else
                break;

        if(idc>=needed){
            winType="x-plane diagonal #1";
            winner=c;
            char lowerCase = Character.toLowerCase(c);
            for(int change=0; change<=change+4;change++)
                if(z+change<Z_SIZE && y+change<Y_SIZE && board[z+change][y+change][x]==c)
                    board[z+change][y+change][x]=lowerCase;
                else
                    break;
            for(int change=1; change<=change+4;change++)
                if(z-change>=0 && y-change>=0 && board[z-change][y-change][x]==c)
                    board[z-change][y-change][x]=lowerCase;
                else
                    break;
            return placedAt;
        }
    // changing differently
        idc=1;
        for(int change=1; change<=change+4;change++)
            if(z-change>=0 && y+change<Y_SIZE && board[z-change][y+change][x]==c)
                idc++;
            else
                break;
        for(int change=1; change<=change+4;change++)
            if(z+change<Z_SIZE && y-change>=0 && board[z+change][y-change][x]==c)
                idc++;
            else
                break;

        if(idc>=needed){
            winType="x-plane diagonal #2";
            winner=c;
            char lowerCase = Character.toLowerCase(c);
            for(int change=0; change<=change+4;change++)
                if(z-change>=0 && y+change<Y_SIZE && board[z-change][y+change][x]==c)
                    board[z-change][y+change][x]=lowerCase;
                else
                    break;
            for(int change=1; change<=change+4;change++)
                if(z+change<Z_SIZE && y-change>=0 && board[z+change][y-change][x]==c)
                    board[z+change][y-change][x]=lowerCase;
                else
                    break;

            return placedAt;
        }

// diagonals on the y-plane
    // both increasing
        idc=1;
        for(int change=1; change<=change+4;change++)
            if(z+change<Z_SIZE && x+change<X_SIZE && board[z+change][y][x+change]==c)
                idc++;
            else
                break;
        for(int change=1; change<=change+4;change++)
            if(z-change>=0 && x-change>=0 && board[z-change][y][x-change]==c)
                idc++;
            else
                break;

        if(idc>=needed){
            winner=c;
            winType="y-plane diagonal #1";
            char lowerCase = Character.toLowerCase(c);
            for(int change=0; change<=change+4;change++)
                if(z+change<Z_SIZE && x+change<X_SIZE && board[z+change][y][x+change]==c)
                    board[z+change][y][x+change]=lowerCase;
                else
                    break;
            for(int change=1; change<=change+4;change++)
                if(z-change>=0 && x-change>=0 && board[z-change][y][x-change]==c)
                    board[z-change][y][x-change]=lowerCase;
                else
                    break;
            return placedAt;
        }
    // changing differently
        idc=1;
        for(int change=1; change<=change+4;change++)
            if(z-change>=0 && x+change<X_SIZE && board[z-change][y][x+change]==c)
                idc++;
            else
                break;
        for(int change=1; change<=change+4;change++)
            if(z+change<Z_SIZE && x-change>=0 && board[z+change][y][x-change]==c)
                idc++;
            else
                break;

        if(idc>=needed){
            winType="y-plane diagonal #2";
            winner=c;
            char lowerCase = Character.toLowerCase(c);
            for(int change=0; change<=change+4;change++)
                if(z-change>=0 && x+change<X_SIZE && board[z-change][y][x+change]==c)
                    board[z-change][y][x+change]=lowerCase;
                else
                    break;
            for(int change=1; change<=change+4;change++)
                if(z+change<Z_SIZE && x-change>=0 && board[z+change][y][x-change]==c)
                    board[z+change][y][x-change]=lowerCase;
                else
                    break;

            return placedAt;
        }

// diagonals on the z-plane
    // both increasing
        idc=1;
        for(int change=1; change<=change+4;change++)
            if(y+change<Y_SIZE && x+change<X_SIZE && board[z][y+change][x+change]==c)
                idc++;
            else
                break;
        for(int change=1; change<=change+4;change++)
            if(y-change>=0 && x-change>=0 && board[z][y-change][x-change]==c)
                idc++;
            else
                break;

        if(idc>=needed){
            winType="z-plane diagonal #1";
            winner=c;
            char lowerCase = Character.toLowerCase(c);
            for(int change=0; change<=change+4;change++)
                if(y+change<Y_SIZE && x+change<X_SIZE && board[z][y+change][x+change]==c)
                    board[z][y+change][x+change]=lowerCase;
                else
                    break;
            for(int change=1; change<=change+4;change++)
                if(y-change>=0 && x-change>=0 && board[z][y-change][x-change]==c)
                    board[z][y-change][x-change]=lowerCase;
                else
                    break;
            return placedAt;
        }
    // changing differently
        idc=1;
        for(int change=1; change<=change+4;change++)
            if(y-change>=0 && x+change<X_SIZE && board[z][y-change][x+change]==c)
                idc++;
            else
                break;
        for(int change=1; change<=change+4;change++)
            if(y+change<Y_SIZE && x-change>=0 && board[z][y+change][x-change]==c)
                idc++;
            else
                break;

        if(idc>=needed){
            winType="z-plane diagonal #2";
            winner=c;
            char lowerCase = Character.toLowerCase(c);
            for(int change=0; change<=change+4;change++)
                if(y-change>=0 && x+change<X_SIZE && board[z][y-change][x+change]==c)
                    board[z][y-change][x+change]=lowerCase;
                else
                    break;
            for(int change=1; change<=change+4;change++)
                if(y+change<Y_SIZE && x-change>=0 && board[z][y+change][x-change]==c)
                    board[z][y+change][x-change]=lowerCase;
                else
                    break;

            return placedAt;
        }

// special digonal 1 + + +
        idc=1;
        for(int change=1; change<=change+4;change++)
            if(y+change<Y_SIZE && x+change<X_SIZE && z+change<Z_SIZE && board[z+change][y+change][x+change]==c)
                idc++;
            else
                break;
        for(int change=1; change<=change+4;change++)
            if(y-change>=0 && x-change>=0 && z-change>=0 &&board[z-change][y-change][x-change]==c)
                idc++;
            else
                break;

        if(idc>=needed){
            winType="special diagonal #1 + + +";
            winner=c;
            char lowerCase = Character.toLowerCase(c);
            for(int change=0; change<=change+4;change++)
                if(y+change<Y_SIZE && x+change<X_SIZE && z+change<Z_SIZE && board[z+change][y+change][x+change]==c)
                    board[z+change][y+change][x+change]=lowerCase;
                else
                    break;
            for(int change=1; change<=change+4;change++)
                if(y-change>=0 && x-change>=0 && z-change>=0 &&board[z-change][y-change][x-change]==c)
                    board[z-change][y-change][x-change]=lowerCase;
                else
                    break;
            return placedAt;
        }

    // special digonal 2 + + -
        idc=1;
        for(int change=1; change<=change+4;change++)
            if(y+change<Y_SIZE && x+change<X_SIZE && z-change>=0 && board[z-change][y+change][x+change]==c)
                idc++;
            else
                break;
        for(int change=1; change<=change+4;change++)
            if(y-change>=0 && x-change>=0 && z+change<Z_SIZE &&board[z+change][y-change][x-change]==c)
                idc++;
            else
                break;

        if(idc>=needed){
            winner=c;
            winType="special diagonal #2 + + -";
            char lowerCase = Character.toLowerCase(c);
            for(int change=0; change<=change+4;change++)
                if(y+change<Y_SIZE && x+change<X_SIZE && z-change>=0 && board[z-change][y+change][x+change]==c)
                    board[z-change][y+change][x+change]=lowerCase;
                else
                    break;
            for(int change=1; change<=change+4;change++)
                if(y-change>=0 && x-change>=0 && z+change<Z_SIZE &&board[z+change][y-change][x-change]==c)
                    board[z+change][y-change][x-change]=lowerCase;
                else
                    break;
            return placedAt;
        }

// special diagonal 3 - + -
        idc=1;
        for(int change=1; change<=change+4;change++)
            if(y+change<Y_SIZE && x-change>=0 && z-change>=0 && board[z-change][y+change][x-change]==c)
                idc++;
            else
                break;
        for(int change=1; change<=change+4;change++)
            if(y-change>=0 && x+change<X_SIZE && z+change<Z_SIZE &&board[z+change][y-change][x+change]==c)
                idc++;
            else
                break;

        if(idc>=needed){
            winner=c;
            winType="special diagonal #3 - + -";
            char lowerCase = Character.toLowerCase(c);
            for(int change=0; change<=change+4;change++)
                if(y+change<Y_SIZE && x-change>=0 && z-change>=0 && board[z-change][y+change][x-change]==c)
                    board[z-change][y+change][x-change]=lowerCase;
                else
                    break;
            for(int change=1; change<=change+4;change++)
                if(y-change>=0 && x+change<X_SIZE && z+change<Z_SIZE &&board[z+change][y-change][x+change]==c)
                    board[z+change][y-change][x+change]=lowerCase;
                else
                    break;
            return placedAt;
        }

// special diagonal 4 - + +
        idc=1;
        for(int change=1; change<=change+4;change++)
            if(y+change<Y_SIZE && x-change>=0 && z+change<Z_SIZE && board[z+change][y+change][x-change]==c)
                idc++;
            else
                break;
        for(int change=1; change<=change+4;change++)
            if(y-change>=0 && x+change<X_SIZE && z-change>=0 &&board[z-change][y-change][x+change]==c)
                idc++;
            else
                break;

        if(idc>=needed){
            winType="special diagonal #4 - + +";
            winner=c;
            char lowerCase = Character.toLowerCase(c);
            for(int change=0; change<=change+4;change++)
                if(y+change<Y_SIZE && x-change>=0 && z+change<Z_SIZE && board[z+change][y+change][x-change]==c)
                    board[z+change][y+change][x-change]=lowerCase;
                else
                    break;
            for(int change=1; change<=change+4;change++)
                if(y-change>=0 && x+change<X_SIZE && z-change>=0 &&board[z-change][y-change][x+change]==c)
                    board[z-change][y-change][x+change]=lowerCase;
                else
                    break;
            return placedAt;
        }

        // tie
        int tieCount=0;
        for(int xx=0; xx<X_SIZE;xx++)
            for(int zz=0; zz<Z_SIZE;zz++)
                if(board[zz][0][xx]!=EMPTY)
                    tieCount++;
        if(tieCount==64) {
            winner = 'T';
        }
        return placedAt;

    }

    public Location makeMoveNoCheck(Move m, char c)
    {
        Location placedAt = null;
        for(int y=Y_SIZE-1; y>=0; y--)
            if(board[m.getZ()][y][m.getX()]=='-')
            {
                board[m.getZ()][y][m.getX()] = c;
                placedAt =  new Location(m.getX(),y,m.getZ());
                break;
            }
        return placedAt;
    }

    public void setLocation(Location l, char c)
    {
        board[l.getZ()][l.getY()][l.getX()] = c;
    }

    public char getLocation(Location l)
    {
        return board[l.getZ()][l.getY()][l.getX()];
    }

    public char getLocation(int x, int y, int z)
    {
        return board[z][y][x];
    }


    public char getWinner()
    {
        return winner;
    }


    public boolean isFull(Move m)
    {
        return board[m.getZ()][0][m.getX()] != '-';

    }

    public String getWinType()
    {
        return winType;
    }

    /** sets all the locations on the board to empty ('-')*/
    public void reset()
    {
        for(int z=0; z < Z_SIZE; z++)
            for(int y=0; y < Y_SIZE; y++)
                for(int x=0; x < X_SIZE; x++)
                {
                    board[z][y][x] = '-';
                }
        winner = '-';
    }

    /** draws a graphical representation of the board to the received
     * graphics object */
    public void draw(Graphics g)
    {
        timer = (timer+1)%8;

        if(timer == 0)
        {
            if(redWin.equals(Color.red))
            {
                redWin = blueWin = Color.gray;
            }
            else
            {
                redWin = Color.red;
                blueWin = Color.blue;
            }
        }
        g.setColor(Color.black);
        g.fillRect(0,0,300,950);


        for(int a=0; a<polys.size(); a++)
        {
            g.setColor(Color.white);
            g.drawPolygon(polys.get(a).getPolygon());
            int x = polys.get(a).getX();
            int y = polys.get(a).getY();
            char letter  = getLocation(polys.get(a).getLocation());
            if(letter == 'R')
            {
                g.setColor(Color.red);
                g.fillOval(x+5,y+1,14,14);
            }
            else if(letter == 'B')
            {
                g.setColor(Color.blue);
                g.fillOval(x+5,y+1,14,14);
            }
            else if(letter == 'r')
            {
                g.setColor(redWin);
                g.fillOval(x+5,y+1,14,14);
            }
            else if(letter == 'b')
            {
                g.setColor(blueWin);
                g.fillOval(x+5,y+1,14,14);
            }
        }
    }
}
