import tabular.DataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Generation {
    private int size;
    private int maxDepth;
    private DataSet dataSet;
    private GPTree[] trees;
    private Random random;

    public Generation(int size, int maxDepth, String fileName) {
        this.size = size;
        this.maxDepth = maxDepth;
        this.dataSet = new DataSet(fileName);
        int numIndepVars = dataSet.getNumIndependentVariables();
        Binop[] ops = {new Plus(), new Minus(), new Mult(), new Divide()};
        NodeFactory nodeFactory = new NodeFactory(ops, numIndepVars);
        this.random = new Random();
        this.trees = new GPTree[size];
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(nodeFactory, maxDepth, random);
        }
    }

    public void evalAll() {
        for (GPTree tree : trees) {
            tree.evalFitness(this.dataSet);
        }
        Arrays.sort(trees);
    }

    public ArrayList<GPTree> getTopTen() {
        int count = Math.min(10, trees.length);
        return new ArrayList<>(Arrays.asList(trees).subList(0, count));
    }

    public void printBestFitness() {
        if (trees.length > 0) {
            DecimalFormat df = new DecimalFormat("0.00");
            System.out.println(df.format(trees[0].getFitness()));
        }
    }

    public void printBestTree() {
        if (trees.length > 0) {
            System.out.println(trees[0].toString());
        }
    }

    public void evolve() {
        GPTree[] nextGeneration = new GPTree[size];
        for (int i = 0; i < size / 2; i++) {
            int index1 = random.nextInt(size / 2);
            int index2 = random.nextInt(size / 2);
            GPTree parent1 = trees[index1];
            GPTree parent2 = trees[index2];

            GPTree child1 = (GPTree) parent1.clone();
            GPTree child2 = (GPTree) parent2.clone();

            child1.crossover(child2, random);
            child2.crossover(child1, random);

            nextGeneration[2 * i] = child1;
            nextGeneration[2 * i + 1] = child2;
        }
        for (int i = size / 2; i < size; i++) {
            nextGeneration[i] = (GPTree) trees[i % (size / 2)].clone();
        }
        trees = nextGeneration;
    }
}