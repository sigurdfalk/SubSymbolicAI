package evolutionaryAlgoritm;

import org.jfree.ui.RefineryUtilities;
import utilities.Statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * User: sigurd
 * Date: 28.03.14
 * Time: 14:07
 */
public abstract class Population {
    protected Individual[] adults;
    protected Individual[] children;
    protected AdultSelection adultSelection;
    protected ParentSelection parentSelection;

    private ArrayList<Double> fitnessList;
    private ArrayList<Double> averageFitnessList;
    private ArrayList<Double> standardDeviationList;

    private Individual bestIndividual;

    protected Population(AdultSelection adultSelection, ParentSelection parentSelection) {
        this.adults = new Individual[Parameters.POPULATION_SIZE];
        this.children = new Individual[Parameters.POPULATION_SIZE + Parameters.OVER_PRODUCTION_COUNT];
        this.adultSelection = adultSelection;
        this.parentSelection = parentSelection;
        this.fitnessList = new ArrayList<Double>();
        this.averageFitnessList = new ArrayList<Double>();
        this.standardDeviationList = new ArrayList<Double>();
    }

    protected Population() {
        this(Parameters.getAdultSelection(), Parameters.getParentSelection());
        Parameters.readParameters();
    }

    public void runEvolution() {
        initializePopulation();

        for (int i = 0; i < Parameters.GENERATION_COUNT; i++) {
            /*System.out.println();
            for (Individual individual : children) {
                printIndividual(individual);
            } */

            newGeneration(i);

            for (Individual child : children) {
                setFitness(child);
            }

            bestIndividual = findBestIndividual();
            updateFitnessLists(bestIndividual);

            System.out.println("Generation " + (i + 1) + " - Best fitness: " + bestIndividual.getFitness() + " : " + Arrays.toString(bestIndividual.getGenotype().getValue()));

            if (bestIndividual.getFitness() == getFitnessLimit()) {
                break;
            }

            setAdults(adultSelection.selectAdults(adults, children));
            elitism(adults);
            setChildren(mutate(crossover(parentSelection.selectParents(adults))));
        }
    }

    protected abstract void newGeneration(int generation);

    private void updateFitnessLists(Individual bestIndividual) {
        fitnessList.add(bestIndividual.getFitness());
        averageFitnessList.add(Statistics.getMean(Utilities.getFitnessList(children)));
        standardDeviationList.add(Statistics.getStandardDeviation(Utilities.getFitnessList(children)));
    }

    protected abstract double getFitnessLimit();

    private Individual findBestIndividual() {
        double bestFitness = (double) Integer.MIN_VALUE;
        Individual bestIndividual = null;

        for (Individual individual : children) {
            if (individual.getFitness() > bestFitness) {
                bestFitness = individual.getFitness();
                bestIndividual = individual;
            }
        }

        return bestIndividual;
    }

    private void elitism(Individual[] adults) {
        Arrays.sort(adults);
        children = new Individual[Parameters.POPULATION_SIZE + Parameters.OVER_PRODUCTION_COUNT];

        for (int i = 0; i < Parameters.ELITISM_COUNT; i++) {
            children[i] = adults[i].clone();
        }
    }

    private void setAdults(Individual[] adults) {
        this.adults = adults;
    }

    private void setChildren(Individual[] children) {
        System.arraycopy(children, 0, this.children, Parameters.ELITISM_COUNT, children.length);
    }

    private Individual[] crossover(Individual[] parents) {
        int i = 0;

        if (parents.length % 2 != 0) {
            i = 1;
        }

        for (; i < parents.length; i += 2) {
            if (Parameters.CROSSOVER_RATE > Math.random()) {
                Individual parent1 = parents[i];
                Individual parent2 = parents[i + 1];
                int genotypeLength = parent1.getGenotype().getValue().length;
                int[] breakpoints = getCrossOverBreakpoints(genotypeLength);

                boolean cross = false;

                for (int j = 0, k = 0; j < genotypeLength; j++) {
                    if (k < breakpoints.length && breakpoints[k] == j) {
                        cross = !cross;
                        k++;
                    }

                    if (cross) {
                        Object temp = parent1.getGenotype().getValue()[j];
                        parent1.getGenotype().getValue()[j] = parent2.getGenotype().getValue()[j];
                        parent2.getGenotype().getValue()[j] = temp;
                    }
                }
            }
        }

        return parents;
    }

    private int[] getCrossOverBreakpoints(int genotypeLength) {
        int[] breakpoints = new int[Parameters.GENOTYPE_CROSSOVER_COUNT];
        Random random = new Random();

        for (int i = 0; i < breakpoints.length; i++) {
            breakpoints[i] = random.nextInt(genotypeLength);
        }

        Arrays.sort(breakpoints);
        return breakpoints;
    }

    protected abstract Individual[] mutate(Individual[] children);

    protected abstract void initializePopulation();

    protected abstract void setFitness(Individual individual);

    public Individual getBestIndividual() {
        return bestIndividual;
    }

    public void visualizeResult() {
        final GraphVisualization demo = new GraphVisualization("Fitness plots (" + adultSelection + " / " + parentSelection + ")", fitnessList, averageFitnessList, standardDeviationList);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    public abstract void printIndividual(Individual individual);
}
