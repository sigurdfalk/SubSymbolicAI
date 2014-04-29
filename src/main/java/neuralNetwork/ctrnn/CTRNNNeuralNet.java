package neuralNetwork.ctrnn;

import evolutionaryAlgoritm.Parameters;
import neuralNetwork.NeuralNet;

import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 31.03.14
 * Time: 13:57
 */
public class CTRNNNeuralNet extends NeuralNet {
    public static final int ACTIVATION_RESPONSE = 1;
    public static final int BIAS = 1;

    public CTRNNNeuralNet(int numberOfInputs) {
        super(numberOfInputs, 2, 1, 2);
    }

    @Override
    protected void createNet() {
        neuronLayers = new CTRNNNeuronLayer[2];
        neuronLayers[0] = new CTRNNNeuronLayer(numberOfHiddenLayerNeurons, numberOfInputs);
        neuronLayers[1] = new CTRNNNeuronLayer(numberOfOutputs, numberOfHiddenLayerNeurons);
    }

    @Override
    public ArrayList<Double> update(ArrayList<Double> inputs) {
        ArrayList<Double> outputs = new ArrayList<>();

        if (inputs.size() != numberOfInputs) {
            throw new IllegalArgumentException("Size of inputs differs from numberOfInputs.");
        }

        for (int i = 0; i < numberOfHiddenLayers + 1; i++) {
            if (i > 0) {
                inputs = (ArrayList<Double>) outputs.clone();
            }

            outputs.clear();

            CTRNNNeuron neuron1 = (CTRNNNeuron) neuronLayers[i].getNeurons()[0];
            CTRNNNeuron neuron2 = (CTRNNNeuron) neuronLayers[i].getNeurons()[1];

            double neuron1State = neuron1.getPreviousState() + calculateInternalState(neuron1, neuron2, inputs);
            double neuron2State = neuron2.getPreviousState() + calculateInternalState(neuron2, neuron1, inputs);
            double output1 = sigmoid(neuron1State * neuron1.getGain(), ACTIVATION_RESPONSE);
            double output2 = sigmoid(neuron2State * neuron2.getGain(), ACTIVATION_RESPONSE);
            neuron1.setPreviousOutput(output1);
            neuron2.setPreviousOutput(output2);


            neuron1.setPreviousState(neuron1State);
            neuron2.setPreviousState(neuron2State);

            outputs.add(output1);
            outputs.add(output2);
        }

        return outputs;
    }

    public ArrayList<Integer> getWeightTypes() {
        ArrayList<Integer> weightTypes = new ArrayList<>();

        for (int i = 0; i < numberOfHiddenLayers + 1; i++) {
            for (int j = 0; j < neuronLayers[i].getNumberOfNeurons(); j++) {
                CTRNNNeuron neuron = (CTRNNNeuron) neuronLayers[i].getNeurons()[j];
                int inputsCount = neuron.getNumberOfInputs();

                for (int k = 0; k < inputsCount; k++) {
                    if (k == inputsCount - CTRNNNeuron.W_BIAS) {
                        weightTypes.add(CTRNNNeuron.W_BIAS);
                    } else if (k == inputsCount - CTRNNNeuron.W_TIME_CONSTANT) {
                        weightTypes.add(CTRNNNeuron.W_TIME_CONSTANT);
                    } else if (k == inputsCount - CTRNNNeuron.W_GAIN) {
                        weightTypes.add(CTRNNNeuron.W_GAIN);
                    } else {
                        weightTypes.add(0);
                    }
                }
            }
        }

        System.out.println(weightTypes.size());
        return weightTypes;
    }

    private double calculateInternalState(CTRNNNeuron neuron, CTRNNNeuron neighbor, ArrayList<Double> inputs) {
        double netInput = 0;
        int numInputs = neuron.getNumberOfInputs();
        int k = 0;
        int weightCount = 0;

        for (; k < numInputs - 5; k++) {
            netInput += neuron.getWeights()[k] * inputs.get(weightCount++);
        }

        netInput += neuron.getWeights()[k++] * neighbor.getPreviousOutput();
        netInput += neuron.getWeights()[k++] * neuron.getPreviousOutput();
        netInput += neuron.getWeights()[neuron.getWeights().length - CTRNNNeuron.W_BIAS] * BIAS;
        double previousState = neuron.getPreviousState();

        double timeConstant = neuron.getTimeConstant();

        double internalState = (1 / timeConstant) * (-previousState + netInput);
        return internalState;
    }
}
