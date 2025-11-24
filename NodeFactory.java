import java.util.Random;

public class NodeFactory {
    private int numIndepVars;
    private Node[] currentOps;

    NodeFactory(Binop[] b, int numVars) {
        this.numIndepVars = numVars;
        this.currentOps = new Node[b.length];
        for (int i = 0; i < b.length; i++) {
            this.currentOps[i] = new Node(b[i]); // Create a Node to hold the Binop
        }
    }

    public Node getOperator(Random rand) {
        int index = rand.nextInt(currentOps.length);
        return (Node) currentOps[index].clone(); // Return a clone of the Node
    }

    public int getNumOps() {
        return currentOps.length;
    }

    public Node getTerminal(Random rand) {
        int randomNumber = rand.nextInt(numIndepVars + 1);
        if (randomNumber < numIndepVars) {
            return new Node(new Variable(randomNumber));
        } else {
            return new Node(new Const(rand.nextDouble()));
        }
    }

    public int getNumIndepVars() {
        return numIndepVars;
    }
}
