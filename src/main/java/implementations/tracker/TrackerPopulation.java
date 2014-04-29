package implementations.tracker;

import evolutionaryAlgoritm.*;
import neuralNetwork.ctrnn.CTRNNNeuralNet;
import neuralNetwork.ctrnn.CTRNNNeuron;
import utilities.Numbers;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * User: sigurd
 * Date: 31.03.14
 * Time: 17:35
 */
public class TrackerPopulation extends Population {
    private CTRNNNeuralNet neuralNet;
    private TrackerController trackerController;
    private ArrayList<Integer> weightTypes;

    public TrackerPopulation() {
        super(new AdultSelection(AdultSelection.GENERATIONAL_MIXING), new ParentSelection(ParentSelection.SIGMA_SCALING));
        //super();
    }

    @Override
    protected void newGeneration(int generation) {
        // Not implemented
    }

    @Override
    protected double getFitnessLimit() {
        return (double) Integer.MIN_VALUE;
    }

    @Override
    protected Individual[] mutate(Individual[] children) {
        for (Individual child : children) {
            int genotypeLength = child.getGenotype().getValue().length;
            for (int i = 0; i < genotypeLength; i++) {
                if (Parameters.MUTATION_RATE > Math.random()) {
                    double sign = (Math.random() > 0.5) ? -1.0 : 1.0;
                    double mutValue = sign * (Math.random());
                    double value = (double) child.getGenotype().getValue()[i] + mutValue;
                    double clampedValue;


                    if (weightTypes.get(i) == CTRNNNeuron.W_BIAS) {
                        clampedValue = Numbers.clampDouble(value, -10.0, 0);
                    } else if (weightTypes.get(i) == CTRNNNeuron.W_GAIN) {
                        clampedValue = Numbers.clampDouble(value, 1.0, 5.0);
                    } else if (weightTypes.get(i) == CTRNNNeuron.W_TIME_CONSTANT) {
                        clampedValue = Numbers.clampDouble(value, 1.0, 2.0);
                    } else  {
                        clampedValue = Numbers.clampDouble(value, -5.0, 5.0);
                    }

                    child.getGenotype().getValue()[i] = clampedValue;
                }
            }
        }

        return children;
    }

    @Override
    protected void initializePopulation() {
        neuralNet = new CTRNNNeuralNet(5);
        weightTypes = neuralNet.getWeightTypes();
        trackerController = new TrackerController(40);
        int dnaLength = neuralNet.getNumberOfWeights();

        for (int i = 0; i < children.length; i++) {
            Object[] dna = new Object[dnaLength];

            for (int j = 0; j < dna.length; j++) {

                if (weightTypes.get(j) == CTRNNNeuron.W_BIAS) {
                    dna[j] = -1.0 * (Math.random() * 10.0);
                } else if (weightTypes.get(j) == CTRNNNeuron.W_GAIN) {
                    dna[j] = 1.0 + (Math.random() * 4.0);
                } else if (weightTypes.get(j) == CTRNNNeuron.W_TIME_CONSTANT) {
                    dna[j] = 1.0 + Math.random();
                } else  {
                    double sign = (Math.random() > 0.5) ? -1.0 : 1.0;
                    dna[j] = sign * (Math.random() * 5.0);
                }
            }

            Genotype genotype = new Genotype(dna);
            Individual individual = new Individual(genotype);
            children[i] = individual;
        }
    }

    @Override
    protected void setFitness(Individual individual) {
        double score = 0.0;

        neuralNet.setWeights(generatePhenotype(individual));
        TrackerController trackerControllerClone = trackerController.clone();
        trackerControllerClone.runGame(neuralNet, false, 0);

        double caught = trackerControllerClone.getTrackerEnvironment().getObjectsCaught();
        double correctlyAvoided = trackerControllerClone.getTrackerEnvironment().getObjectsCorrectlyAvoided();
        double wronglyAvoided = trackerControllerClone.getTrackerEnvironment().getObjectsWronglyAvoided();
        double collided = trackerControllerClone.getTrackerEnvironment().getObjectsCollided();

        score += caught;
        score += correctlyAvoided;

        individual.setFitness(score);
    }

    @Override
    public void printIndividual(Individual individual) {
        System.out.println(Arrays.toString(generatePhenotype(individual).toArray()));
    }

    public static ArrayList<Double> generatePhenotype(Individual individual) {
        ArrayList<Double> phenotype = new ArrayList<>();

        for (int i = 0; i < individual.getGenotype().getValue().length; i++) {
            phenotype.add((double) individual.getGenotype().getValue()[i]);
        }

        return phenotype;
    }

    public TrackerController getTrackerController() {
        return trackerController;
    }
}
