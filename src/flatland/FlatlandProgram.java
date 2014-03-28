package flatland;

import evolutionaryAlgoritm.AdultSelection;
import evolutionaryAlgoritm.ParentSelection;
import neuralNetwork.NeuralNet;

import javax.swing.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * User: sigurd
 * Date: 24.03.14
 * Time: 17:47
 */
public class FlatlandProgram {

    public static void main(String[] args) {
        int type = Integer.parseInt(JOptionPane.showInputDialog("Enter EA type (0 = Static, 1 = Dynamic)"));

        NeuralNet neuralNet = new NeuralNet(6, 3, 0, 4);

        FlatlandPopulation population = new FlatlandPopulation(new AdultSelection(AdultSelection.GENERATIONAL_MIXING), new ParentSelection(ParentSelection.SIGMA_SCALING), neuralNet, type);
        //FlatlandPopulation population = new FlatlandPopulation(neuralNet, type);

        population.runEvolution();
        population.visualizeResult();
        ArrayList<Double> weights = FlatlandPopulation.generatePhenotype(population.getBestIndividual());

        neuralNet.setWeights(weights);
        for (int i = 0; i < 5; i++) {
            FlatlandController flatlandController = new FlatlandController(100);
            flatlandController.runGame(neuralNet, true);
        }
    }
}
