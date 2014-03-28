package evolutionaryAlgoritm;

import utilities.Statistics;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * User: sigurd
 * Date: 28.03.14
 * Time: 14:15
 */
public class ParentSelection {
    public static final int FITNESS_PROPORTIONATE = 0;
    public static final int SIGMA_SCALING = 1;
    public static final int RANK_SELECTION = 2;
    public static final int TOURNAMENT_SELECTION = 3;

    private int type;

    public ParentSelection(int type) {
        this.type = type;
    }

    public Individual[] selectParents(Individual[] adults) {
        switch (type) {
            case FITNESS_PROPORTIONATE:
                return fitnessProportionate(adults);
            case SIGMA_SCALING:
                return sigmaScaling(adults);
            case TOURNAMENT_SELECTION:

            case RANK_SELECTION:
                return rankSelection(adults);
            default:
                throw new IllegalArgumentException("Unsupported parent selection.");
        }
    }

    private Individual[] sigmaScaling(Individual[] adults) {
        double[] fitnessValues = Utilities.getFitnessList(adults);
        double[] rouletteWheel = new double[adults.length];
        double fitnessMean = Statistics.getMean(fitnessValues);
        double std = Statistics.getStandardDeviation(fitnessValues);

        double sum = 0.0;

        for (int i = 0; i < rouletteWheel.length; i++) {
            rouletteWheel[i] = (1.0 + ((adults[i].getFitness() - fitnessMean) / (2.0 * std)));
            sum += rouletteWheel[i];
        }

        for (int i = 0; i < rouletteWheel.length; i++) {
            rouletteWheel[i] /= sum;
        }

        return getParents(adults, rouletteWheel);
    }

    private Individual[] fitnessProportionate(Individual[] adults) {
        double[] fitnessValues = Utilities.getFitnessList(adults);
        double[] rouletteWheel = new double[adults.length];
        double netFitness = Statistics.getSum(fitnessValues);

        for (int i = 0; i < rouletteWheel.length; i++) {
            rouletteWheel[i] = adults[i].getFitness() / netFitness;
        }

        return getParents(adults, rouletteWheel);
    }

    private Individual[] rankSelection(Individual[] adults) {
        double[] rouletteWheel = new double[adults.length];
        Arrays.sort(adults);

        int rank = rouletteWheel.length;
        double sum = 0.0;

        for (int i = 0; i < rouletteWheel.length; i++) {
            rouletteWheel[i] = Parameters.RANK_SELECTION_MIN + ((Parameters.RANK_SELECTION_MAX - Parameters.RANK_SELECTION_MIN) * ((double)(rank - 1) / (double)(adults.length - 1)));
        }

        for (int i = 0; i < rouletteWheel.length; i++) {
            rouletteWheel[i] /= sum;
        }

        return getParents(adults, rouletteWheel);
    }

    private Individual[] getParents(Individual[] adults, double[] rouletteWheel) {
        Individual[] parents = new Individual[Parameters.POPULATION_SIZE - Parameters.ELITISM_COUNT];

        for (int i = 0; i < parents.length; i++) {
            parents[i] = adults[getRouletteWheelIndex(rouletteWheel)].clone();
        }

        return parents;
    }

    private int getRouletteWheelIndex(double[] rouletteWheel) {
        double randomNumber = Math.random();
        double total = 0.0;
        int i = 0;

        for (; i < rouletteWheel.length; i++) {
            total += rouletteWheel[i];

            if (randomNumber < total) {
                return i;
            }
        }

        return i - 1;
    }
}
