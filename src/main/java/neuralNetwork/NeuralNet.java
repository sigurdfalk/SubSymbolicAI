package neuralNetwork;

import java.lang.Double;import java.lang.IllegalArgumentException;import java.lang.Math;import java.util.ArrayList;
import java.util.Arrays;

/**
 * User: sigurd
 * Date: 24.03.14
 * Time: 14:28
 */
public abstract class NeuralNet {
    protected int numberOfInputs;
    protected int numberOfOutputs;
    protected int numberOfHiddenLayers;
    protected int numberOfHiddenLayerNeurons;

    protected NeuronLayer[] neuronLayers;

    public NeuralNet(int numberOfInputs, int numberOfOutputs, int numberOfHiddenLayers, int numberOfHiddenLayerNeurons) {
        this.numberOfInputs = numberOfInputs;
        this.numberOfOutputs = numberOfOutputs;
        this.numberOfHiddenLayers = numberOfHiddenLayers;
        this.numberOfHiddenLayerNeurons = numberOfHiddenLayerNeurons;
        createNet();
    }

    protected abstract void createNet();


    protected double sigmoid(double netInput, double response) {
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

    public abstract ArrayList<Double> update(ArrayList<Double> inputs);
}
