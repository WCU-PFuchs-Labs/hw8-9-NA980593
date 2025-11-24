import java.util.Scanner;

public class TestGP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the data file name: ");
        String fileName = scanner.nextLine();

        Generation generation = new Generation(500, 5, fileName);

        System.out.println("Initial Generation:");
        generation.evalAll();
        System.out.print("Best Fitness: ");
        generation.printBestFitness();
        System.out.print("Best Tree: ");
        generation.printBestTree();
        System.out.println();

        for (int i = 1; i <= 50; i++) {
            System.out.println("Generation " + i + ":");
            generation.evolve();
            generation.evalAll();
            System.out.print("Best Fitness: ");
            generation.printBestFitness();
            System.out.print("Best Tree: ");
            generation.printBestTree();
            System.out.println();
        }
        scanner.close();
    }
}