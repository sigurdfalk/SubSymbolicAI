package evolutionaryAlgoritm;

import javax.swing.*;

/**
 * User: sigurd
 * Date: 28.03.14
 * Time: 15:04
 */
public class Parameters {
    public static int POPULATION_SIZE = 200;
    public static int GENERATION_COUNT = 100;
    public static int ELITISM_COUNT = 2;
    public static int OVER_PRODUCTION_COUNT = 0;
    public static double CROSSOVER_RATE = 0.8;
    public static int GENOTYPE_CROSSOVER_COUNT = 2;
    public static double MUTATION_RATE = 0.2;
    public static double RANK_SELECTION_MIN = 0.5;
    public static double RANK_SELECTION_MAX = 1.5;
    public static int TOURNAMENT_SELECTION_K = 5;
    public static double TOURNAMENT_SELECTION_e = 0.5;

    public static void readParameters() {
        POPULATION_SIZE = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter population size"));
        GENERATION_COUNT = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter generation count"));
        ELITISM_COUNT = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter elitism count"));
        MUTATION_RATE = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter mutation rate"));
        CROSSOVER_RATE = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter crossover rate"));
        GENOTYPE_CROSSOVER_COUNT = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter genotype crossover count"));
    }

    public static AdultSelection getAdultSelection() {
        int type = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter adult selection:\n0 = Full Generational Replacement\n1 = Over Production\n2 = Generational Mixing"));

        switch (type) {
            case AdultSelection.OVER_PRODUCTION:
                OVER_PRODUCTION_COUNT = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter over production count"));
                break;
            default:
                OVER_PRODUCTION_COUNT = 0;
        }

        return new AdultSelection(type);
    }

    public static ParentSelection getParentSelection() {
        int type = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter parent selection:\n0 = Fitness Proportionate\n1 = Sigma Scaling\n2 = Rank Selection\n3 = Tournament Selection"));

        switch (type) {
            case ParentSelection.RANK_SELECTION:
                RANK_SELECTION_MIN = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter rank selection min value"));
                RANK_SELECTION_MAX = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter rank selection max value"));
                break;
            case ParentSelection.TOURNAMENT_SELECTION:
                TOURNAMENT_SELECTION_K = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter tournament selection group size"));
                TOURNAMENT_SELECTION_e = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter tournament selection probability"));
                break;
        }

        return new ParentSelection(type);
    }
}
