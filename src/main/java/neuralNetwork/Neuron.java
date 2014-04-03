package neuralNetwork;

import java.util.ArrayList;
import java.util.Random;

/**
 * User: sigurd
 * Date: 24.03.14
 * Time: 14:28
 */
public abstract class Neuron {
    protected int numberOfInputs;
    protected double[] weights;

    public Neuron(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
        initializeWeights();
    }

    protected abstract void initializeWeights();

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
