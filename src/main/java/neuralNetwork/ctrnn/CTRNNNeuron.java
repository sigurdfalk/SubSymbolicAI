package neuralNetwork.ctrnn;

import neuralNetwork.Neuron;

import java.util.Arrays;
import java.util.Random;

/**
 * User: sigurd
 * Date: 30.03.14
 * Time: 18:08
 */
public class CTRNNNeuron extends Neuron {
    public static final int W_BIAS = 3;
    public static final int W_GAIN = 2;
    public static final int W_TIME_CONSTANT = 1;

    private double previousState;
    private double previousOutput;

    public CTRNNNeuron(int numberOfInputs) {
        super(numberOfInputs);
        this.previousOutput = Math.random();
        this.previousState = 0;
    }

    @Override
    protected void initializeWeights() {
        super.weights = new double[super.numberOfInputs + 5];
        numberOfInputs += 5;

        int i = 0;

        for (; i < weights.length - 3; i++) {
            double sign = (Math.random() > 0.5) ? -1.0 : 1.0;
            weights[i] = sign * (Math.random() * 5.0);
        }

        weights[weights.length - W_BIAS] = -1.0 * (Math.random() * 10.0); // Bias
        weights[weights.length - W_GAIN] = 1.0 + (Math.random() * 4.0); // Gain
        weights[weights.length - W_TIME_CONSTANT] = 1.0 + Math.random(); // Time constant
    }

    public double getPreviousOutput() {
        return previousOutput;
    }

    public void setPreviousOutput(double previousOutput) {
        this.previousOutput = previousOutput;
    }

    public double getGain() {
        return weights[weights.length - W_GAIN];
    }

    public double getTimeConstant() {
        return weights[weights.length - W_TIME_CONSTANT];
    }

    public double getPreviousState() {
        return previousState;
    }

    public void setPreviousState(double previousState) {
        this.previousState = previousState;
    }
}
