package evolutionaryAlgoritm;

import javax.swing.*;

/**
 * User: sigurd
 * Date: 28.03.14
 * Time: 15:04
 */
public class Parameters {
    public static int POPULATION_SIZE = 300;
    public static int GENERATION_COUNT = 100;
    public static int ELITISM_COUNT = 2;
    public static double CROSSOVER_RATE = 0.8;
    public static int GENOTYPE_CROSSOVER_COUNT = 2;
    public static double MUTATION_RATE = 0.05;
    public static double RANK_SELECTION_MIN = 0.5;
    public static double RANK_SELECTION_MAX = 1.5;

    public static void readParameters() {
        POPULATION_SIZE = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter population size"));
        GENERATION_COUNT = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter generation count"));
        ELITISM_COUNT = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter elitism count"));
        MUTATION_RATE = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter mutation rate"));
        CROSSOVER_RATE = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter crossover rate"));
        GENOTYPE_CROSSOVER_COUNT = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter genotype crossover count"));
    }

    public static AdultSelection getAdultSelection() {
        int type = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter adult selection\n0 = Full Generational Replacement\n2 = Generational Mixing"));
        return new AdultSelection(type);
    }

    public static ParentSelection getParentSelection() {
        int type = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter parent selection\n0 = Fitness Proportionate\n1 = Sigma Scaling\n2 = Rank Selection"));
        return new ParentSelection(type);
    }
}
