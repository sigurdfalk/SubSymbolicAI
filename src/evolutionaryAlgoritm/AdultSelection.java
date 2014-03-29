package evolutionaryAlgoritm;

import java.util.Arrays;

/**
 * User: sigurd
 * Date: 28.03.14
 * Time: 14:53
 */
public class AdultSelection {
    public static final int FULL_GENERATIONAL_REPLACEMENT = 0;
    public static final int OVER_PRODUCTION = 1;
    public static final int GENERATIONAL_MIXING = 2;

    private int type;

    public AdultSelection(int type) {
        this.type = type;
    }

    public Individual[] selectAdults(Individual[] adults, Individual[] children) {
        switch (type) {
            case FULL_GENERATIONAL_REPLACEMENT:
                return fullGenerationalReplacement(children);
            case OVER_PRODUCTION:
                return overProduction(adults, children);
            case GENERATIONAL_MIXING:
                return generationalMixing(adults, children);
            default:
                throw new IllegalArgumentException("Unsupported adult selection.");
        }
    }

    private Individual[] fullGenerationalReplacement(Individual[] children) {
        return Arrays.copyOfRange(children, 0, Parameters.POPULATION_SIZE);
    }

    private Individual[] overProduction(Individual[] adults, Individual[] children) {
        Arrays.sort(children);
        return Arrays.copyOfRange(children, 0, adults.length);
    }

    private Individual[] generationalMixing(Individual[] adults, Individual[] children) {
        if (adults[0] != null) {
            Individual[] temp = new Individual[adults.length + children.length];
            System.arraycopy(adults, 0, temp, 0, adults.length);
            System.arraycopy(children, 0, temp, adults.length, children.length);

            Arrays.sort(temp);

            return Arrays.copyOfRange(temp, 0, Parameters.POPULATION_SIZE);
        }

        Arrays.sort(children);
        return children;
    }
}
