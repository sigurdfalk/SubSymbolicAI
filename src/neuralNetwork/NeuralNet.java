package neuralNetwork;

import java.lang.Double;import java.lang.IllegalArgumentException;import java.lang.Math;import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 24.03.14
 * Time: 14:28
 */
public class NeuralNet {
    private int numberOfInputs;
    private int numberOfOutputs;
    private int numberOfHiddenLayers;
    private int numberOfHiddenLayerNeurons;

    private NeuronLayer[] neuronLayers;

    public NeuralNet(int numberOfInputs, int numberOfOutputs, int numberOfHiddenLayers, int numberOfHiddenLayerNeurons) {
        this.numberOfInputs = numberOfInputs;
        this.numberOfOutputs = numberOfOutputs;
        this.numberOfHiddenLayers = numberOfHiddenLayers;
        this.numberOfHiddenLayerNeurons = numberOfHiddenLayerNeurons;
        createNet();
    }

    private void createNet() {
        if (numberOfHiddenLayers > 0) {
            neuronLayers = new NeuronLayer[numberOfHiddenLayers + 1];
            neuronLayers[0] = new NeuronLayer(numberOfHiddenLayerNeurons, numberOfInputs);

            for (int i = 1; i < neuronLayers.length - 1; i++) {
                neuronLayers[i] = new NeuronLayer(numberOfHiddenLayerNeurons, numberOfHiddenLayerNeurons);
            }

            neuronLayers[neuronLayers.length - 1] = new NeuronLayer(numberOfOutputs, numberOfHiddenLayerNeurons);
        } else {
            neuronLayers = new NeuronLayer[]{new NeuronLayer(numberOfOutputs, numberOfInputs)};
        }
    }

    private double sigmoid(double netInput, double response) {
        return 1 / (1 + Math.exp(-netInput / response));
    }

    public ArrayList<Double> getWeights() {
        ArrayList<Double> weights = new ArrayList<>();

        for (int i = 0; i < numberOfHiddenLayers + 1; i++) {
            for (int j = 0; j < neuronLayers[i].getNumberOfNeurons(); j++) {
                for (int k = 0; k < neuronLayers[i].getNeurons()[j].getNumberOfInputs(); k++) {
                    weights.add(neuronLayers[i].getNeurons()[j].getWeights()[k]);
                }
            }
        }

        return weights;
    }

    public void setWeights(ArrayList<Double> weights) {
        int weightCount = 0;

        for (int i = 0; i < numberOfHiddenLayers + 1; i++) {
            for (int j = 0; j < neuronLayers[i].getNumberOfNeurons(); j++) {
                for (int k = 0; k < neuronLayers[i].getNeurons()[j].getNumberOfInputs(); k++) {
                    neuronLayers[i].getNeurons()[j].getWeights()[k] = weights.get(weightCount++);
                }
            }
        }
    }

    public int getNumberOfWeights() {
        int numberOfWeights = 0;

        for (int i = 0; i < numberOfHiddenLayers + 1; i++) {
            for (int j = 0; j < neuronLayers[i].getNumberOfNeurons(); j++) {
                for (int k = 0; k < neuronLayers[i].getNeurons()[j].getNumberOfInputs(); k++) {
                    numberOfWeights++;
                }
            }
        }

        return numberOfWeights;
    }

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

                netInput += neuronLayers[i].getNeurons()[j].getWeights()[numInputs - 1] * Parameters.BIAS;
                outputs.add(sigmoid(netInput, Parameters.ACTIVATION_RESPONSE));
                weightCount = 0;
            }
        }

        return outputs;
    }
}
