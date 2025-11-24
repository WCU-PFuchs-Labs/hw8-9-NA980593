public class Variable extends Unop {
    private int index;

    public Variable(int subscript) {
        index = subscript;
    }

    public double eval(double[] data) {
        return data[index];
    }

    public String toString() {
        return "X" + index;
    }
}
