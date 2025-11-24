import java.util.Random;

public class Node implements Cloneable{
    private Node left;
    private Node right;
    private Op operation;

    protected int depth;

    public Node(Unop operation) {
        this.operation = operation;
        this.depth = 0;
    }

    public Node(Binop operation, Node left, Node right) {
        this.left = left;
        this.right = right;
        this.operation = operation;
        this.depth = 0;
    }

    public Node(Binop operation) {
        this.operation = operation;
        this.depth = 0;
    }

    public double eval(double[] values) {
        if (operation instanceof Unop) {
            return ((Unop) operation).eval(values);
        } else if (operation instanceof Binop) {
            return ((Binop) operation).eval(left.eval(values), right.eval(values));
        } else {
            System.err.println("Error operation is not a Unop or a Binop!");
            return 0.0;
        }
    }

    public void addRandomKids(NodeFactory nf, int maxDepth, Random rand) {
        // use the [above algorithm](#node)
        if (operation instanceof Unop) {
            return; // Unops have no children
        }

        if (operation instanceof Binop) {
            if (depth == maxDepth) {
                left = nf.getTerminal(rand);
                left.depth = depth + 1;
                right = nf.getTerminal(rand);
                right.depth = depth + 1;
            } else {
                // Add left child
                int numOperators = nf.getNumOps();
                int numIndepVars = nf.getNumIndepVars();
                int randValLeft = rand.nextInt(numOperators + numIndepVars + 1); // +1 because getTerminal can also return a Const

                if (randValLeft < numOperators) {
                    left = nf.getOperator(rand);
                    left.depth = depth + 1;
                    left.addRandomKids(nf, maxDepth, rand);
                } else {
                    left = nf.getTerminal(rand);
                    left.depth = depth + 1;
                }

                // Add right child
                int randValRight = rand.nextInt(numOperators + numIndepVars + 1); // +1 because getTerminal can also return a Const

                if (randValRight < numOperators) {
                    right = nf.getOperator(rand);
                    right.depth = depth + 1;
                    right.addRandomKids(nf, maxDepth, rand);
                } else {
                    right = nf.getTerminal(rand);
                    right.depth = depth + 1;
                }
            }
        }
    }

    public String toString() {
        if (operation instanceof Unop) {
            return operation.toString();
        } else if (operation instanceof Binop) {
            return "(" + left.toString() + " " + operation.toString() + " " + right.toString() + ")";
        }
        return "";
    }

    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Op can't clone.");
        }
        Node b = (Node) o;
        if (left != null) {
            b.left = (Node) left.clone();
        }
        if (right != null) {
            b.right = (Node) right.clone();
        }
        if (operation != null) {
            b.operation = (Op) operation.clone();
        }
        return o;
    }
    /**
     * collect using preorder traversal.
     */
    public void traverse(Collector c) {
        // collect this
        c.collect(this);
        // traverse lChild
        if (left != null) {
            left.traverse(c);
        }
        // traverse rChild
        if (right != null) {
            right.traverse(c);
        }
    }
    /**
     * swap this left child node with trunk left child node
     */
    public void swapLeft(Node trunk) {
        Node temp = this.left;
        this.left = trunk.left;
        trunk.left = temp;
    }
    /**
     * swap this right child node with trunk right child node
     */
    public void swapRight(Node trunk) {
        Node temp = this.right;
        this.right = trunk.right;
        trunk.right = temp;
    }
    /**
     * return true if operation is s Unop
     */
    public boolean isLeaf() {
        // return true if operation is a Unop
        return operation instanceof Unop;
    }
}
