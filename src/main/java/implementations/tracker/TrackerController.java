package implementations.tracker;

import neuralNetwork.NeuralNet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * User: sigurd
 * Date: 30.03.14
 * Time: 15:30
 */
public class TrackerController {
    public static final int MOVE_LEFT = 0;
    public static final int MOVE_RIGHT = 1;
    public static final int STAND_STILL = 2;

    private TrackerEnvironment trackerEnvironment;
    private int numberOfObjects;

    private ArrayList<TrackerObject> trackerObjects;

    public TrackerController(int numberOfObjects) {
        this.trackerEnvironment = new TrackerEnvironment();
        this.numberOfObjects = numberOfObjects;
        this.trackerObjects = new ArrayList<>();
        initializeTrackerObjects();
    }

    public TrackerController(TrackerEnvironment trackerEnvironment, int numberOfObjects, ArrayList<TrackerObject> trackerObjects) {
        this.trackerEnvironment = trackerEnvironment;
        this.numberOfObjects = numberOfObjects;
        this.trackerObjects = trackerObjects;
    }

    private void initializeTrackerObjects() {
        Random random = new Random();

        for (int i = 0; i < numberOfObjects; i++) {
            //trackerObjects.add(new TrackerObject(random.nextInt(3), 1 + random.nextInt(6), random.nextInt(TrackerEnvironment.ENVIRONMENT_WIDTH)));
            trackerObjects.add(new TrackerObject(TrackerObject.OBJECT_FALLING, 1 + random.nextInt(6), random.nextInt(TrackerEnvironment.ENVIRONMENT_WIDTH)));
        }
    }

    public void runGame() {
        TrackerInterface trackerInterface = new TrackerInterface(trackerEnvironment);

        for (TrackerObject trackerObject : trackerObjects) {
            trackerEnvironment.newObject(trackerObject);
            trackerInterface.update();

            while (true) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Arrays.toString(trackerEnvironment.getAgentSensors().toArray()));

                if (!trackerEnvironment.moveObject()) {
                    trackerInterface.update();
                    break;
                } else {
                    int steps = new Random().nextInt(5);

                    if (Math.random() < 0.5) {
                        trackerEnvironment.moveAgentRight(steps);
                    } else {
                        trackerEnvironment.moveAgentLeft(steps);
                    }

                    trackerInterface.update();
                }

            }
        }
    }

    public void runGame(NeuralNet neuralNet, boolean visualize, long delay) {
        TrackerInterface trackerInterface = null;

        if (visualize) {
            trackerInterface = new TrackerInterface(trackerEnvironment);
        }

        for (TrackerObject trackerObject : trackerObjects) {
            try {
                Thread.sleep(delay * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            trackerEnvironment.newObject(trackerObject);
            if (visualize) {
                trackerInterface.update();
            }

            while (true) {
                if (visualize) {
                    trackerInterface.update();
                }

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!trackerEnvironment.moveObject()) {
                    if (visualize) {
                        trackerInterface.update();
                    }
                    break;
                } else {
                    ArrayList<Double> output = neuralNet.update(trackerEnvironment.getAgentSensors());
                    react(output);
                    if (visualize) {
                        trackerInterface.update();
                    }
                }
            }
        }
    }

    private void makeMove(int direction, int steps) {
        switch (direction) {
            case MOVE_LEFT:
                trackerEnvironment.moveAgentLeft(steps);
                break;
            case MOVE_RIGHT:
                trackerEnvironment.moveAgentRight(steps);
                break;
            case STAND_STILL:
                break;
            default:
                throw new IllegalArgumentException("Illegal direction.");
        }
    }

    private void react(ArrayList<Double> outputs) {
        int leftOutput = (int) (outputs.get(0) * 4.0);
        int rightOutput = (int) (outputs.get(1) * 4.0);

        int diff = leftOutput - rightOutput;

        if (diff == 0) {
            makeMove(STAND_STILL, 0);
        } else if (diff > 0) {
            makeMove(MOVE_RIGHT, (leftOutput > 4) ? 4 : leftOutput);
        } else if (diff < 0) {
            makeMove(MOVE_LEFT, (rightOutput > 4) ? 4 : rightOutput);
        }
    }

    public TrackerEnvironment getTrackerEnvironment() {
        return trackerEnvironment;
    }

    public TrackerController clone() {
        return new TrackerController(trackerEnvironment.clone(), numberOfObjects, (ArrayList<TrackerObject>) trackerObjects.clone());
    }
}
