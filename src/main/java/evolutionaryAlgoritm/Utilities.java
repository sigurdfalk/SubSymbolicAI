package evolutionaryAlgoritm;

/**
 * User: sigurd
 * Date: 28.03.14
 * Time: 14:36
 */
public class Utilities {

    public static double[] getFitnessList(Individual[] individuals) {
        double[] fitnessList = new double[individuals.length];

        for (int i = 0; i < fitnessList.length; i++) {
            fitnessList[i] = individuals[i].getFitness();
        }

        return fitnessList;
    }
}
