package implementations.tracker;

import neuralNetwork.ctrnn.CTRNNNeuralNet;

import javax.swing.*;
import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 29.03.14
 * Time: 18:19
 */
public class TrackerProgram {

    public static void main(String[] args) {


        CTRNNNeuralNet neuralNet = new CTRNNNeuralNet(5);

        TrackerPopulation population = new TrackerPopulation();
        population.runEvolution();

        String visualizeEA = JOptionPane.showInputDialog(null, "Visualize EA run? (y/n)");

        if (visualizeEA.equals("y")) {
            population.visualizeResult();
        }

        String visualizeFlatland = JOptionPane.showInputDialog(null, "Visualize tracker environment? (y/n)");

        ArrayList<Double> weights = TrackerPopulation.generatePhenotype(population.getBestIndividual());
        neuralNet.setWeights(weights);

        if (visualizeFlatland.equals("y")) {
            TrackerController trackerEnvironment = population.getTrackerController().clone();
            trackerEnvironment.runGame(neuralNet, true, 50);
        }



        /*System.out.println();
        int leftOutput = 0;
        int rightOutput = 0;

        System.out.println("Best weights: " + Arrays.toString(weights.toArray()));

        ArrayList<Double> allOnes = new ArrayList<>();
        allOnes.add(1.0);
        allOnes.add(1.0);
        allOnes.add(1.0);
        allOnes.add(1.0);
        allOnes.add(1.0);

        System.out.println();
        ArrayList<Double> outputs = neuralNet.update(allOnes);
        leftOutput = (int) (outputs.get(0) * 4.0);
        rightOutput = (int) (outputs.get(1) * 4.0);
        System.out.println("allOnes: " + Arrays.toString(outputs.toArray()));
        System.out.println("leftOutput: " + leftOutput + " - rightOutput: " + rightOutput);


        ArrayList<Double> right1 = new ArrayList<>();
        right1.add(0.0);
        right1.add(0.0);
        right1.add(0.0);
        right1.add(0.0);
        right1.add(1.0);


        System.out.println();
        outputs = neuralNet.update(right1);
        leftOutput = (int) (outputs.get(0) * 4.0);
        rightOutput = (int) (outputs.get(1) * 4.0);
        System.out.println("right1: " + Arrays.toString(outputs.toArray()));
        System.out.println("leftOutput: " + leftOutput + " - rightOutput: " + rightOutput);

        ArrayList<Double> right2 = new ArrayList<>();
        right2.add(0.0);
        right2.add(0.0);
        right2.add(0.0);
        right2.add(1.0);
        right2.add(1.0);

        System.out.println();
        outputs = neuralNet.update(right2);
        leftOutput = (int) (outputs.get(0) * 4.0);
        rightOutput = (int) (outputs.get(1) * 4.0);
        System.out.println("right2: " + Arrays.toString(outputs.toArray()));
        System.out.println("leftOutput: " + leftOutput + " - rightOutput: " + rightOutput);

        ArrayList<Double> right3 = new ArrayList<>();
        right3.add(0.0);
        right3.add(0.0);
        right3.add(1.0);
        right3.add(1.0);
        right3.add(1.0);

        System.out.println();
        outputs = neuralNet.update(right3);
        leftOutput = (int) (outputs.get(0) * 4.0);
        rightOutput = (int) (outputs.get(1) * 4.0);
        System.out.println("right3: " + Arrays.toString(outputs.toArray()));
        System.out.println("leftOutput: " + leftOutput + " - rightOutput: " + rightOutput);

        ArrayList<Double> left1 = new ArrayList<>();
        left1.add(1.0);
        left1.add(0.0);
        left1.add(0.0);
        left1.add(0.0);
        left1.add(0.0);

        System.out.println();
        outputs = neuralNet.update(left1);
        leftOutput = (int) (outputs.get(0) * 4.0);
        rightOutput = (int) (outputs.get(1) * 4.0);
        System.out.println("left1: " + Arrays.toString(outputs.toArray()));
        System.out.println("leftOutput: " + leftOutput + " - rightOutput: " + rightOutput);

        ArrayList<Double> left2 = new ArrayList<>();
        left2.add(1.0);
        left2.add(1.0);
        left2.add(0.0);
        left2.add(0.0);
        left2.add(0.0);

        System.out.println();
        outputs = neuralNet.update(left2);
        leftOutput = (int) (outputs.get(0) * 4.0);
        rightOutput = (int) (outputs.get(1) * 4.0);
        System.out.println("left2: " + Arrays.toString(outputs.toArray()));
        System.out.println("leftOutput: " + leftOutput + " - rightOutput: " + rightOutput);

        ArrayList<Double> left3 = new ArrayList<>();
        left3.add(1.0);
        left3.add(1.0);
        left3.add(1.0);
        left3.add(0.0);
        left3.add(0.0);

        System.out.println();
        outputs = neuralNet.update(left3);
        leftOutput = (int) (outputs.get(0) * 4.0);
        rightOutput = (int) (outputs.get(1) * 4.0);
        System.out.println("left3: " + Arrays.toString(outputs.toArray()));
        System.out.println("leftOutput: " + leftOutput + " - rightOutput: " + rightOutput);

        ArrayList<Double> allZeroes = new ArrayList<>();
        allZeroes.add(0.0);
        allZeroes.add(0.0);
        allZeroes.add(0.0);
        allZeroes.add(0.0);
        allZeroes.add(0.0);

        System.out.println();
        outputs = neuralNet.update(allZeroes);
        leftOutput = (int) (outputs.get(0) * 4.0);
        rightOutput = (int) (outputs.get(1) * 4.0);
        System.out.println("allZeroes: " + Arrays.toString(outputs.toArray()));
        System.out.println("leftOutput: " + leftOutput + " - rightOutput: " + rightOutput);

        ArrayList<Double> middle3 = new ArrayList<>();
        middle3.add(0.0);
        middle3.add(1.0);
        middle3.add(1.0);
        middle3.add(1.0);
        middle3.add(0.0);

        System.out.println();
        outputs = neuralNet.update(middle3);
        leftOutput = (int) (outputs.get(0) * 4.0);
        rightOutput = (int) (outputs.get(1) * 4.0);
        System.out.println("middle3: " + Arrays.toString(outputs.toArray()));
        System.out.println("leftOutput: " + leftOutput + " - rightOutput: " + rightOutput); */


    }
}
