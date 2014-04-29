package neuralNetwork.standard;

import neuralNetwork.Neuron;

import java.util.Random;

/**
 * User: sigurd
 * Date: 30.03.14
 * Time: 18:09
 */
public class StandardNeuron extends Neuron {
    public StandardNeuron(int numberOfInputs) {
        super(numberOfInputs);
    }

    @Override
    protected void initializeWeights() {
        super.weights = new double[super.numberOfInputs + 1];
        numberOfInputs += 1;
        Random random = new Random();

        for (int i = 0; i < weights.length; i++) {
            double sign = (Math.random() > 0.5) ? -1.0 : 1.0;
            weights[i] = sign * (random.nextDouble() * 5.0);
        }
    }
}
