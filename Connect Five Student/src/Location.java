/** Location - Stores the x,y,z values of a 3D location */
import java.io.Serializable;
public class Location implements Serializable
{
    int z;
    int y;
    int x;

    /** Sets x, y, z to the recieved values */
    public Location(int x,int y,int z)
    {
        this.x	= x;
        this.y	= y;
        this.z 	= z;
    }

    /** Copys the data of the received location
     * @param l Location - the location to be copied*/
    public Location(Location l)
    {
        this.x	= l.getX();
        this.y	= l.getY();
        this.z 	= l.getZ();
    }

    /** Returns the location's column
     * @return int - the column*/
    public int getX()
    {
        return x;
    }

    /** Returns the location's row
     * @return int - the row*/
    public int getY()
    {
        return y;
    }

    /** Returns the location's sheet
     * @return int - the sheet*/
    public int getZ()
    {
        return z;
    }

    /** Returns a text representation of the location
     * in the form of (x,y,x)
     * @ return Stirng - a text representation of the location*/
    public String toString()
    {
        String s = "(x=" + x+",y="+y+ ",z="+z+ ")";
        return s;
    }



}