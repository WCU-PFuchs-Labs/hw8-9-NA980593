package tabular;

import java.util.ArrayList;
import java.util.Random;

public class LinearModel extends Model {
    private double[] coeff;
    private double intercept;
    private double changeRate;

    /**
     * @param rate     - the update/learning rate
     * @param training - the training data
     */
    public LinearModel(double rate, DataSet training) {
        super(training);
        // initialize changeRate based on parameters
        changeRate = rate;
        // call initCoefficients to initialize coeff and intercept
        initCoefficients();
        // call updateCoeff() 1000 times
        // when testing this, you may want to print some
        // debugging output so that you can track the progress
        // of the regression.


    }


    /**
     * helper function to initialize coeff and intercept
     */
    private void initCoefficients() {
        // instantiate an array of size n for coeff, where n is
        // the number of independent variables in the training data
        coeff = new double[trainingData.getNumIndependentVariables()];
        // initialize each value in coeff to be a random double 
        // between -2 and 2 
        Random random = new Random();
        for (int i = 0; i < coeff.length; i++) {
            coeff[i] = random.nextDouble() * -2.0 + (2.0 + 2.0);
        }
        // initialize intercept to be a random double 
        // between -2 and 2 
        this.intercept = random.nextDouble() * -2.0 + (2.0 + 2.0);
    }

    /**
     * This makes a prediction based on the current
     * coefficients and intercept
     *
     * @param x
     * @return
     */
    public double predict(double[] x) {
        // compute the sum of each coefficient times 
        // the corresponding x, then add the intercept
        // and return the result.

        //FIXME:
        double prediction = intercept;
        for (int i = 0; i < coeff.length; i++) {
            prediction += coeff[i] * x[i];
        }
        return prediction;
    }

    /**
     * helper function that does one iteration of computing
     * the error and updating the coefficients and the intercept
     */
    private void updateCoeff() {
        // get the predictions for each row by calling predict()
        double[] pred = predict();

        // get the data rows
        ArrayList<DataRow> rows = trainingData.getRows();

        // for each coefficient 
        // compute the sum of the error times the variable
        // then multiply by 2 and divide by the number of predictions
        for (int i = 0; i < coeff.length; ++i) {
            double sum = 0;
            for (int j = 0; j < pred.length; ++j) {
                DataRow row = rows.get(j);
                double x_i = row.getIndependentVariables()[i];
                sum += (pred[j] - row.getDependentVariable()) * x_i;
            }
            coeff[i] = coeff[i] - changeRate * sum * 2.0 / pred.length;
        }

        // for the intercept 
        double sum = 0;
        for (int j = 0; j < pred.length; ++j) {
            DataRow row = rows.get(j);
            sum += pred[j] - row.getDependentVariable();
        }
        intercept = intercept - changeRate * sum * 2.0 / pred.length;


    }

    public String toString() {
        StringBuilder result = new StringBuilder(coeff[0] + "*X0");
        for (int i = 1; i < coeff.length; i++) {
            result.append(" + ").append(coeff[i]).append("*").append(i);
        }
        result.append(" + ").append(intercept);
        return result.toString();
    }
}
