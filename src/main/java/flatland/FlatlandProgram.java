package flatland;

import evolutionaryAlgoritm.AdultSelection;
import evolutionaryAlgoritm.ParentSelection;
import neuralNetwork.NeuralNet;
import neuralNetwork.standard.StandardNeuralNet;

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
        int type = Integer.parseInt(JOptionPane.showInputDialog("Enter flatland EA type:\n0 = Static\n1 = Dynamic"));

        StandardNeuralNet neuralNet = new StandardNeuralNet(6, 3, 0, 4);
        //FlatlandPopulation population = new FlatlandPopulation(new AdultSelection(AdultSelection.OVER_PRODUCTION), new ParentSelection(ParentSelection.SIGMA_SCALING), neuralNet, type);
        FlatlandPopulation population = new FlatlandPopulation(neuralNet, type);
        population.runEvolution();

        String visualizeEA = JOptionPane.showInputDialog(null, "Visualize EA run? (y/n)");

        if (visualizeEA.equals("y")) {
            population.visualizeResult();
        }

        String visualizeFlatland = JOptionPane.showInputDialog(null, "Visualize flatland agent? (y/n)");

        if (visualizeFlatland.equals("y")) {
            int count = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter number of flatland runs"));
            long delay = Long.parseLong(JOptionPane.showInputDialog(null, "Enter move delay"));

            ArrayList<Double> weights = FlatlandPopulation.generatePhenotype(population.getBestIndividual());
            neuralNet.setWeights(weights);
            System.out.println();

            for (int i = 0; i < count; i++) {
                FlatlandController flatlandController = new FlatlandController(50);
                flatlandController.runGame(neuralNet, true, delay);
                FlatlandBoard flatlandBoard = flatlandController.getFlatlandBoard();
                FlatlandAgent flatlandAgent = flatlandBoard.getFlatlandAgent();
                System.out.println("Run " + (i + 1) + " - Food consumed: " + flatlandAgent.getNumberOfFoodConsumed() + "/" + flatlandBoard.getTotalFood() + " - Poison consumed: " + flatlandAgent.getNumberOfPoisonConsumed() + "/" + flatlandBoard.getTotalPoison());
            }
        }
    }
}
