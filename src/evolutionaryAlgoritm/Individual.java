package evolutionaryAlgoritm;

/**
 * User: sigurd
 * Date: 28.03.14
 * Time: 14:08
 */
public class Individual implements Comparable<Individual> {
    private Genotype genotype;
    private double fitness;

    public Individual(Genotype genotype) {
        this.genotype = genotype;
    }

    public Individual(Genotype genotype, double fitness) {
        this.genotype = genotype;
        this.fitness = fitness;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public void setGenotype(Genotype genotype) {
        this.genotype = genotype;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public Individual clone() {
        return new Individual(genotype.clone(), fitness);
    }

    @Override
    public int compareTo(Individual o) {
        if (this.fitness > o.getFitness()) {
            return -1;
        } else if (this.fitness < o.getFitness()) {
            return 1;
        } else {
            return 0;
        }
    }
}
