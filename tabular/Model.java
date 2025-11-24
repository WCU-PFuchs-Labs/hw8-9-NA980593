package tabular;

import java.util.ArrayList;

public abstract class Model{
    protected DataSet trainingData;

    /**
     * 
     * @param training - the training data
     */
    public Model(DataSet training) {
        trainingData = training;
    }

    

    /**
     * This makes a prediction using the concrete model
     * @param x
     * @return
     */
    public abstract double predict(double[] x);

   

    /**
     * helper function that computes the prediction values 
     * for all of the training data using the object's predict method
     * @return an array of predictions for each row in the
     * training data
     */
    protected double[] predict() {
        ArrayList<DataRow> rows = trainingData.getRows();
        double[] pred;
        if(rows == null) {
            pred = new double[0];
        }else {
            pred = new double[rows.size()];
        }
        for(int i = 0; i < pred.length; ++i) {
            DataRow currRow = rows.get(i);
            double[] x = currRow.getIndependentVariables();
            pred[i] = predict(x);
        }
        return pred;
    }

    /**
     * Computes the sum of squared errors on the training data
     * for the current coefficients and intercept
     * @return the sum of squared data of the model on the training data
     */
    public double  sumSquaredError() {
        // get the predictions for each row by calling predict()
        double[] pred = predict();

        ArrayList<DataRow> rows = trainingData.getRows();
        double sum = 0;
        for(int j = 0; j < pred.length; ++j) {
            DataRow row = rows.get(j);
            double err = pred[j] - row.getDependentVariable();
            sum += err * err; // add the squared error
        }
        return sum;
    }
}
