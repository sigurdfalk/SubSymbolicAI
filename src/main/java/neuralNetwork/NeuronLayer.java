package neuralNetwork;

/**
 * User: sigurd
 * Date: 24.03.14
 * Time: 14:28
 */
public abstract class NeuronLayer {
    protected int numberOfNeurons;
    protected Neuron[] neurons;

    public NeuronLayer(int numberOfNeurons, int numberOfInputs) {
        this.numberOfNeurons = numberOfNeurons;
        this.neurons = new Neuron[numberOfNeurons];
        createNeurons(numberOfInputs);
    }

    protected abstract void createNeurons(int numberOfInputs);

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
