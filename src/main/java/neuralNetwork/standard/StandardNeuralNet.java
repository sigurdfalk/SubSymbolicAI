package neuralNetwork.standard;

import neuralNetwork.NeuralNet;
import neuralNetwork.NeuronLayer;

import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 30.03.14
 * Time: 18:44
 */
public class StandardNeuralNet extends NeuralNet {
    public static final int ACTIVATION_RESPONSE = 1;
    public static final int BIAS = -1;

    public StandardNeuralNet(int numberOfInputs, int numberOfOutputs, int numberOfHiddenLayers, int numberOfHiddenLayerNeurons) {
        super(numberOfInputs, numberOfOutputs, numberOfHiddenLayers, numberOfHiddenLayerNeurons);
    }

    @Override
    protected void createNet() {
        if (numberOfHiddenLayers > 0) {
            neuronLayers = new StandardNeuronLayer[numberOfHiddenLayers + 1];
            neuronLayers[0] = new StandardNeuronLayer(numberOfHiddenLayerNeurons, numberOfInputs);

            for (int i = 1; i < neuronLayers.length - 1; i++) {
                neuronLayers[i] = new StandardNeuronLayer(numberOfHiddenLayerNeurons, numberOfHiddenLayerNeurons);
            }

            neuronLayers[neuronLayers.length - 1] = new StandardNeuronLayer(numberOfOutputs, numberOfHiddenLayerNeurons);
        } else {
            neuronLayers = new NeuronLayer[]{new StandardNeuronLayer(numberOfOutputs, numberOfInputs)};
        }
    }

    @Override
    public ArrayList<Double> update(ArrayList<Double> inputs) {
        ArrayList<Double> outputs = new ArrayList<>();
        int weightCount = 0;

        if (inputs.size() != numberOfInputs) {
            throw new IllegalArgumentException("Size of inputs differs from numberOfInputs.");
        }

        for (int i = 0; i < numberOfHiddenLayers + 1; i++) {
            if (i > 0) {
                inputs = (ArrayList<Double>) outputs.clone();
            }

            outputs.clear();
            weightCount = 0;

            for (int j = 0; j < neuronLayers[i].getNumberOfNeurons(); j++) {
                double netInput = 0;
                int numInputs = neuronLayers[i].getNeurons()[j].getNumberOfInputs();

                for (int k = 0; k < numInputs - 1; k++) {
                    netInput += neuronLayers[i].getNeurons()[j].getWeights()[k] * inputs.get(weightCount++);
                }

                netInput += neuronLayers[i].getNeurons()[j].getWeights()[numInputs - 1] * BIAS;
                outputs.add(sigmoid(netInput, ACTIVATION_RESPONSE));
                weightCount = 0;
            }

        }

        return outputs;
    }
}
