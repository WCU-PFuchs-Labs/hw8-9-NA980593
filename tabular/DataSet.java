package tabular;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSet {

    // add fields here
    private ArrayList<DataRow> data;
    private int numIndepVariables;

    /**
     * @param filename the name of the file to read the data set from
     */
    public DataSet(String filename) {
        // initialize fields here
        data = new ArrayList<>();
        File file = new File(filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (scanner.hasNextLine()) {
            String headerLine = scanner.nextLine();
            String[] headers = headerLine.split(",");
            numIndepVariables = headers.length - 1;
        }
        while (scanner.hasNextLine()) {
            String dataLine = scanner.nextLine();
            String[] values = dataLine.split(",");
            double y = Double.parseDouble(values[0]);
            double[] x = new double[numIndepVariables];
            for (int i = 0; i < numIndepVariables; i++) {
                x[i] = Double.parseDouble(values[i + 1]);
            }
            data.add(new DataRow(y, x));
        }
    }

    /**
     * @return the list of rows
     */
    public ArrayList<DataRow> getRows() {
        // FIXME: return the right thing here
        return data;
    }

    /**
     * @return the number of independent variables in each row of the data set
     */
    public int getNumIndependentVariables() {
        // FIXME: return the right thing here
        return numIndepVariables;
    }
}

