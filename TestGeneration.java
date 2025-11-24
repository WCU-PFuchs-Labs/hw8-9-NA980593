import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class TestGeneration {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();

        Generation generation = new Generation(500, 2, fileName);
        generation.evalAll();

        generation.printBestTree();

        System.out.println("Top Ten Fitness Values:");
        ArrayList<GPTree> topTen = generation.getTopTen();
        DecimalFormat df = new DecimalFormat("0.00");
        for (int i = 0; i < topTen.size(); i++) {
            System.out.print(df.format(topTen.get(i).getFitness()));
            if (i < topTen.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        scanner.close();
    }
}