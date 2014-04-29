package neuralNetwork.standard;

import neuralNetwork.Neuron;
import neuralNetwork.NeuronLayer;

/**
 * User: sigurd
 * Date: 30.03.14
 * Time: 18:42
 */
public class StandardNeuronLayer extends NeuronLayer {
    public StandardNeuronLayer(int numberOfNeurons, int numberOfInputs) {
        super(numberOfNeurons, numberOfInputs);
    }

    @Override
    protected void createNeurons(int numberOfInputs) {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new StandardNeuron(numberOfInputs);
        }
    }
}
