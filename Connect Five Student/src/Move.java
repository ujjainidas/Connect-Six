import java.io.Serializable;

public class Move implements Serializable
{
    private int x,z;

    public Move(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public String toString()
    {
        String s = "(x=" + x+",z="+z+ ")";
        return s;
    }
}
