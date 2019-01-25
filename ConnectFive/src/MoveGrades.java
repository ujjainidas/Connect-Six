public class MoveGrades
{
    Move m;
    int grade;


    public MoveGrades(Move m, int grade)
    {
        this.m=m;
        this.grade=grade;
    }

    public MoveGrades(Move m)
    {
        this.m = m;
    }

    public Move getM() {
        return m;
    }

    public int getGrade() {
        return grade;
    }

    public void setM(Move m) {
        this.m = m;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
