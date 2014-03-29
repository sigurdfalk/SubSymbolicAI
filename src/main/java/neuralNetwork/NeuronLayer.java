package neuralNetwork;

/**
 * User: sigurd
 * Date: 24.03.14
 * Time: 14:28
 */
public class NeuronLayer {
    private int numberOfNeurons;
    private Neuron[] neurons;

    public NeuronLayer(int numberOfNeurons, int numberOfInputs) {
        this.numberOfNeurons = numberOfNeurons;
        this.neurons = new Neuron[numberOfNeurons];
        createNeurons(numberOfInputs);
    }

    private void createNeurons(int numberOfInputs) {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron(numberOfInputs);
        }
    }

    public int getNumberOfNeurons() {
        return numberOfNeurons;
    }

    public void setNumberOfNeurons(int numberOfNeurons) {
        this.numberOfNeurons = numberOfNeurons;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public void setNeurons(Neuron[] neurons) {
        this.neurons = neurons;
    }
}
