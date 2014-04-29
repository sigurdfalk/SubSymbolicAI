package neuralNetwork.ctrnn;

import neuralNetwork.NeuronLayer;

/**
 * User: sigurd
 * Date: 31.03.14
 * Time: 13:56
 */
public class CTRNNNeuronLayer extends NeuronLayer {
    public CTRNNNeuronLayer(int numberOfNeurons, int numberOfInputs) {
        super(numberOfNeurons, numberOfInputs);
    }

    @Override
    protected void createNeurons(int numberOfInputs) {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new CTRNNNeuron(numberOfInputs);
        }
    }
}
