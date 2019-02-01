public class MoveGrades
{
    Move m;
    int y;
    int grade;


    public MoveGrades(Move m, int y, int grade)
    {
        this.m = m;
        this.y = y;
        this.grade=grade;
    }

    public MoveGrades(Move m, int y)
    {
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
