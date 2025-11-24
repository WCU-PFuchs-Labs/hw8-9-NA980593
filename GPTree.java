import tabular.DataRow;
import tabular.DataSet;
import java.util.ArrayList;
import java.util.Random;

public class GPTree implements Collector, Comparable<GPTree>, Cloneable  {
    private Node root;
    private ArrayList<Node> crossNodes;
    private double fitness;

    public void collect(Node node) {
        // add node to crossNodes if it is not a leaf node
        if (!node.isLeaf()) {
            crossNodes.add(node);
        }
    }

    public void evalFitness(DataSet dataSet) {
        fitness = 0;
        for (DataRow row : dataSet.getRows()) {
            double[] xValues = row.getIndependentVariables();
            double yValue = row.getDependentVariable();
            double treeValue = this.eval(xValues);
            fitness += Math.pow(treeValue - yValue, 2);
        }
    }

    public double getFitness() {
        return fitness;
    }

    @Override
    public int compareTo(GPTree other) {
        return Double.compare(this.fitness, other.fitness);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GPTree)) {
            return false;
        }
        return compareTo((GPTree) o) == 0;
    }

    @Override
    public Object clone() {
        try {
            GPTree clonedTree = (GPTree) super.clone();
            clonedTree.root = (Node) this.root.clone();
            clonedTree.crossNodes = new ArrayList<>();
            clonedTree.fitness = this.fitness;
            return clonedTree;
        } catch (CloneNotSupportedException e) {
            throw new InternalError("Clone error");
        }
    }

    // DO NOT EDIT code below for Homework 8.
    // If you are doing the challenge mentioned in
    // the comments above the crossover method
    // then you should create a second crossover
    // method above this comment with a slightly
    // different name that handles all types
    // of crossover.


    /**
     * This initializes the crossNodes field and
     * calls the root Node's traverse method on this
     * so that this can collect the Binop Nodes.
     */
    public void traverse() {
        crossNodes = new ArrayList<Node>();
        root.traverse(this);
    }

    /**
     * This returns a String with all of the binop Strings
     * separated by semicolons
     */
    public String getCrossNodes() {
        StringBuilder string = new StringBuilder();
        int lastIndex = crossNodes.size() - 1;
        for(int i = 0; i < lastIndex; ++i) {
            Node node = crossNodes.get(i);
            string.append(node.toString());
            string.append(";");
        }
        string.append(crossNodes.get(lastIndex));
        return string.toString();
    }


    /**
     * this implements left child to left child
     * and right child to right child crossover.
     * Challenge: additionally implement left to
     * right child and right to left child crossover.
     */
    public void crossover(GPTree tree, Random rand) {
        // find the points for crossover
        this.traverse();
        tree.traverse();
        int thisPoint = rand.nextInt(this.crossNodes.size());
        int treePoint = rand.nextInt(tree.crossNodes.size());
        boolean left = rand.nextBoolean();
        // get the connection points
        Node thisTrunk = crossNodes.get(thisPoint);
        Node treeTrunk = tree.crossNodes.get(treePoint);


        if(left) {
            thisTrunk.swapLeft(treeTrunk);

        } else {
            thisTrunk.swapRight(treeTrunk);
        }

    }

    GPTree() {
        root = null;
    }

    public GPTree(NodeFactory n, int maxDepth, Random rand) {
        root = n.getOperator(rand);
        root.addRandomKids(n, maxDepth, rand);
    }

    public String toString() {
        return root.toString();
    }

    public double eval(double[] data) {
        return root.eval(data);
    }

}