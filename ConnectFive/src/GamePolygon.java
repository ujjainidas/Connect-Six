import java.awt.*;

public class GamePolygon
{
    private Location loc;
    private Polygon poly;
    private int x;
    private int y;

    public GamePolygon(int x, int y,Location loc, Polygon poly)
    {
        this.loc = loc;
        this.poly = poly;
        this.x = x;
        this.y = y;
    }

    public Polygon getPolygon()
    {
        return poly;
    }

    public Location getLocation()
    {	return loc;	}

    public int getX()
    {	return x;	}

    public int getY()
    {	return y;	}
}