package implementations.tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * User: sigurd
 * Date: 29.03.14
 * Time: 16:51
 */
public class TrackerEnvironment {
    public static final int ENVIRONMENT_WIDTH = 30;
    public static final int ENVIRONMENT_HEIGHT = 15;

    public static final int AGENT_SIZE = 5;
    public static final int AGENT_MAX_CATCH_SIZE = 4;

    private boolean[] agentRow;
    private boolean[] objectRow;

    private TrackerObject trackerObject;
    private int objectRowNumber;

    private int agentStartIndex;

    private final Random random;

    private int objectsCorrectlyAvoided;
    private int objectsWronglyAvoided;
    private int objectsCaught;
    private int objectsCollided;

    public TrackerEnvironment() {
        this.random = new Random();
        this.agentRow = new boolean[ENVIRONMENT_WIDTH];
        this.objectRow = new boolean[ENVIRONMENT_WIDTH];
        initializeAgent(random.nextInt(agentRow.length));
    }

    public TrackerEnvironment(boolean[] agentRow, boolean[] objectRow, TrackerObject trackerObject, int objectRowNumber, int agentStartIndex) {
        this.random = new Random();
        this.agentRow = agentRow;
        this.objectRow = objectRow;
        this.objectRowNumber = objectRowNumber;
        this.agentStartIndex = agentStartIndex;
        this.trackerObject = trackerObject;
    }

    private void initializeAgent(int agentStartIndex) {
        this.agentStartIndex = agentStartIndex;

        for (int i = 0, j = agentStartIndex; i < AGENT_SIZE; i++) {
            agentRow[j] = true;

            if (j == (agentRow.length - 1)) {
                j = 0;
            } else {
                j++;
            }
        }
    }

    public void newObject(TrackerObject trackerObject) {
        this.trackerObject = trackerObject;
        objectRow = new boolean[ENVIRONMENT_WIDTH];
        objectRowNumber = ENVIRONMENT_HEIGHT;

        for (int i = 0, j = trackerObject.getStartIndex(); i < trackerObject.getSize(); i++) {
            objectRow[j] = true;

            if (j == (objectRow.length - 1)) {
                j = 0;
            } else {
                j++;
            }
        }
    }

    private void moveObjectRight(boolean[] row, int steps) {
        ArrayList<Integer> newObjectIndexes = new ArrayList<>();

        for (int i = 0; i < row.length; i++) {
            if (row[i]) {
                row[i] = false;

                if ((i + steps) >= row.length) {
                    newObjectIndexes.add((i + steps) - row.length);
                } else {
                    newObjectIndexes.add(i + steps);
                }
            }
        }

        for (int i : newObjectIndexes) {
            row[i] = true;
        }
    }

    private void moveObjectLeft(boolean[] row, int steps) {
        ArrayList<Integer> newObjectIndexes = new ArrayList<>();

        for (int i = (row.length - 1); i >= 0; i--) {
            if (row[i]) {
                row[i] = false;

                if ((i - steps) < 0) {
                    newObjectIndexes.add(row.length + (i - steps));
                } else {
                    newObjectIndexes.add(i - steps);
                }
            }
        }

        for (int i : newObjectIndexes) {
            row[i] = true;
        }
    }

    public boolean moveObject() {



        if (objectRowNumber == 0) {
            calculateOutcome();
            return false;
        }

        if (trackerObject.getType() == TrackerObject.OBJECT_FALLING_LEFT) {
            moveObjectLeft(objectRow, 1);
        } else if (trackerObject.getType() == TrackerObject.OBJECT_FALLING_RIGHT) {
            moveObjectRight(objectRow, 1);
        }

        objectRowNumber--;

        return true;
    }

    private void calculateOutcome() {
        if (trackerObject.getSize() <= AGENT_MAX_CATCH_SIZE) {
            if (caughtObject()) {
                objectsCaught++;
            } else if (avoidedObject()) {
                objectsWronglyAvoided++;
            } else {
                objectsCollided++;
            }
        } else {
            if (avoidedObject()) {
                objectsCorrectlyAvoided++;
            } else {
                objectsCollided++;
            }
        }
    }

    private boolean avoidedObject() {
        for (int i = 0; i < ENVIRONMENT_WIDTH; i++) {
            if (agentRow[i] && objectRow[i]) {
                return false;
            }
        }

        return true;
    }

    private boolean caughtObject() {
        int squaresAligned = 0;

        for (int i = 0; i < ENVIRONMENT_WIDTH; i++) {
            if (objectRow[i] && agentRow[i]) {
                squaresAligned++;
            }
        }

        if (AGENT_MAX_CATCH_SIZE > AGENT_SIZE && trackerObject.getSize() > AGENT_SIZE) {
            return squaresAligned == AGENT_SIZE;
        } else {
            return squaresAligned == trackerObject.getSize();
        }
    }

    public void moveAgentLeft(int steps) {
        agentStartIndex = ((agentStartIndex - steps) < 0) ? (agentRow.length + (agentStartIndex - steps)) : (agentStartIndex - steps);
        moveObjectLeft(agentRow, steps);
    }

    public void moveAgentRight(int steps) {
        agentStartIndex = ((agentStartIndex + steps) >= agentRow.length) ? ((agentStartIndex + steps) - agentRow.length) : agentStartIndex + steps;
        moveObjectRight(agentRow, steps);
    }

    public ArrayList<Double> getAgentSensors() {
        ArrayList<Double> agentSensors = new ArrayList<>();

        for (int i = agentStartIndex, j = 0; j < AGENT_SIZE; i++, j++) {
            if (i == agentRow.length) {
                i = 0;
            }

            if (agentRow[i] && objectRow[i]) {
                agentSensors.add(1.0);
            } else {
                agentSensors.add(0.0);
            }
        }

        return agentSensors;
    }

    public boolean[] getAgentRow() {
        return agentRow;
    }

    public boolean[] getObjectRow() {
        return objectRow;
    }

    public int getObjectRowNumber() {
        return objectRowNumber;
    }

    public int getObjectsCorrectlyAvoided() {
        return objectsCorrectlyAvoided;
    }

    public int getObjectsWronglyAvoided() {
        return objectsWronglyAvoided;
    }

    public int getObjectsCaught() {
        return objectsCaught;
    }

    public int getObjectsCollided() {
        return objectsCollided;
    }

    public int getAgentStartIndex() {
        return agentStartIndex;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Arrays.toString(objectRow)).append("\n").append(Arrays.toString(agentRow));
        return stringBuilder.toString();
    }

    public TrackerEnvironment clone() {
        return new TrackerEnvironment(Arrays.copyOf(agentRow, agentRow.length), Arrays.copyOf(objectRow, objectRow.length), trackerObject, objectRowNumber, agentStartIndex);
    }
}
