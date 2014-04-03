package flatland;

import evolutionaryAlgoritm.*;
import neuralNetwork.NeuralNet;
import neuralNetwork.standard.StandardNeuralNet;

import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 28.03.14
 * Time: 17:25
 */
public class FlatlandPopulation extends Population {
    public static final int TYPE_STATIC = 0;
    public static final int TYPE_DYNAMIC = 1;

    private ArrayList<FlatlandController> flatlandControllers;
    private StandardNeuralNet neuralNet;
    private int type;

    protected FlatlandPopulation(AdultSelection adultSelection, ParentSelection parentSelection, StandardNeuralNet neuralNet, int type) {
        super(adultSelection, parentSelection);
        this.neuralNet = neuralNet;
        this.flatlandControllers = new ArrayList<>();
        this.type = type;
    }

    public FlatlandPopulation(StandardNeuralNet neuralNet, int type) {
        super();
        this.neuralNet = neuralNet;
        this.type = type;
        this.flatlandControllers = new ArrayList<>();
    }

    @Override
    protected void newGeneration(int generation) {
        if (type == TYPE_DYNAMIC) {
            generateFlatlandControllers();
        }
    }

    @Override
    protected double getFitnessLimit() {
        return 1.0;
    }

    @Override
    protected Individual[] mutate(Individual[] children) {
        for (Individual child : children) {
            for (int i = 0; i < child.getGenotype().getValue().length; i++) {
                if (Parameters.MUTATION_RATE > Math.random()) {
                    double sign = (Math.random() > 0.5) ? -1.0 : 1.0;
                    double mutValue = sign * (Math.random());
                    double value = (double) child.getGenotype().getValue()[i] + mutValue;
                    child.getGenotype().getValue()[i] = value;
                }
            }
        }

        return children;
    }

    @Override
    protected void initializePopulation() {
        generateFlatlandControllers();

        for (int i = 0; i < children.length; i++) {
            Object[] dna = new Object[neuralNet.getNumberOfWeights()];

            for (int j = 0; j < dna.length; j++) {
                double sign = (Math.random() > 0.5) ? -1.0 : 1.0;
                dna[j] = sign * (Math.random() * 5.0);
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

        for (FlatlandController flatlandController : flatlandControllers) {
            FlatlandController controller = flatlandController.clone();
            controller.runGame(neuralNet, false, 0);

            double totalFood = controller.getFlatlandBoard().getTotalFood();
            double foodConsumed = controller.getFlatlandBoard().getFlatlandAgent().getNumberOfFoodConsumed();
            double totalPoison = controller.getFlatlandBoard().getTotalPoison();
            double poisonConsumed = controller.getFlatlandBoard().getFlatlandAgent().getNumberOfPoisonConsumed();

            //score += (totalFood - foodConsumed);
            //score += poisonConsumed;

            score += foodConsumed / totalFood;
            score -= (poisonConsumed / totalPoison) * 1.2;

            //score += foodConsumed * 1.5;
            //score -= poisonConsumed;
        }

        //score = 1.0 / (1.0 + score);
        score /= 5.0;
        individual.setFitness(score);
    }

    @Override
    public void printIndividual(Individual individual) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < individual.getGenotype().getValue().length; i++) {
            stringBuilder.append((double) individual.getGenotype().getValue()[i]).append(" : ");
        }

        System.out.println(stringBuilder);
    }

    private void generateFlatlandControllers() {
        flatlandControllers.clear();

        for (int i = 0; i < 5; i++) {
            flatlandControllers.add(new FlatlandController(50));
        }
    }

    public static ArrayList<Double> generatePhenotype(Individual individual) {
        ArrayList<Double> phenotype = new ArrayList<>();

        for (int i = 0; i < individual.getGenotype().getValue().length; i++) {
            phenotype.add((double) individual.getGenotype().getValue()[i]);
        }

        return phenotype;
    }
}
