package neuralNetwork;

import java.util.ArrayList;
import java.util.Random;

/**
 * User: sigurd
 * Date: 24.03.14
 * Time: 14:28
 */
public class Neuron {
    private int numberOfInputs;
    private double[] weights;

    public Neuron(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
        this.weights = new double[numberOfInputs + 1];
        initializeWeights();
    }

    private void initializeWeights() {
        Random random = new Random();

        for (int i = 0; i < weights.length; i++) {
            double sign = (Math.random() > 0.5) ? -1.0 : 1.0;
            weights[i] = sign * random.nextDouble();
        }
    }

    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    public void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }
}
