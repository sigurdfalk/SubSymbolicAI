package flatland;

import neuralNetwork.NeuralNet;
import sun.net.www.content.text.plain;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * User: sigurd
 * Date: 25.03.14
 * Time: 17:21
 */
public class FlatlandController {
    public static final int MOVE_FORWARD = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int STAND_STILL = 3;

    private FlatlandBoard flatlandBoard;
    private FlatlandInterface flatlandInterface;

    private int timesteps;

    public FlatlandController(int timesteps) {
        this.flatlandBoard = new FlatlandBoard();
        this.timesteps = timesteps;
    }

    public FlatlandController(FlatlandBoard flatlandBoard, int timesteps) {
        this.flatlandBoard = flatlandBoard;
        this.timesteps = timesteps;
    }

    public void react(ArrayList<Double> motorOutputs, boolean visualize) {

        if (motorOutputs.get(0) > 0.5 && motorOutputs.get(1) > 0.5 && motorOutputs.get(2) > 0.5) {
            makeMove(STAND_STILL, visualize);
        } else if (motorOutputs.get(0) < 0.5 && motorOutputs.get(1) > 0.5 && motorOutputs.get(2) > 0.5) {
            makeMove(STAND_STILL, visualize);
        } else if (motorOutputs.get(0) > 0.5 && motorOutputs.get(1) < 0.5 && motorOutputs.get(2) < 0.5) {
            makeMove(MOVE_FORWARD, visualize);
        } else if (motorOutputs.get(0) < 0.5 && motorOutputs.get(1) > 0.5 && motorOutputs.get(2) < 0.5) {
            makeMove(MOVE_LEFT, visualize);
        } else if (motorOutputs.get(0) < 0.5 && motorOutputs.get(1) < 0.5 && motorOutputs.get(2) > 0.5) {
            makeMove(MOVE_RIGHT, visualize);
        } else if (motorOutputs.get(0) > 0.5 && motorOutputs.get(1) > 0.5 && motorOutputs.get(2) < 0.5) {
            makeMove(MOVE_LEFT, visualize);
            makeMove(MOVE_FORWARD, visualize);
        } else if (motorOutputs.get(0) > 0.5 && motorOutputs.get(1) < 0.5 && motorOutputs.get(2) > 0.5) {
            makeMove(MOVE_RIGHT, visualize);
            makeMove(MOVE_FORWARD, visualize);
        } else if (motorOutputs.get(0) < 0.5 && motorOutputs.get(1) < 0.5 && motorOutputs.get(2) < 0.5) {
            makeMove(STAND_STILL, visualize);
        }
    }

    public void makeMove(int direction, boolean visualize) {
        switch (direction) {
            case MOVE_FORWARD:
                flatlandBoard.moveForward();
                break;
            case MOVE_LEFT:
                flatlandBoard.moveLeft();
                break;
            case MOVE_RIGHT:
                flatlandBoard.moveRight();
                break;
            case STAND_STILL:
                break;
            default:
                throw new IllegalArgumentException("Illegal direction.");
        }

        timesteps--;

        if (visualize) {
            flatlandInterface.update();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void runGame(NeuralNet neuralNet, boolean visualize) {
        if (visualize) {
            flatlandInterface = new FlatlandInterface(flatlandBoard);
        }

        while (timesteps > 0) {
            react(neuralNet.update(flatlandBoard.getAgentSensorValues()), visualize);
        }
    }

    public FlatlandBoard getFlatlandBoard() {
        return flatlandBoard;
    }

    public FlatlandController clone() {
        return new FlatlandController(flatlandBoard.clone(), timesteps);
    }
}
