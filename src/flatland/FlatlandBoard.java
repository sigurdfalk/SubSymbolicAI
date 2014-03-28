package flatland;

import java.util.ArrayList;
import java.util.Random;

/**
 * User: sigurd
 * Date: 24.03.14
 * Time: 16:43
 */
public class FlatlandBoard {
    public static final int CONTENT_EMPTY = 0;
    public static final int CONTENT_FOOD = 1;
    public static final int CONTENT_POISON = 2;
    public static final int CONTENT_AGENT = 3;

    public static final int COUNT_ROWS = 8;
    public static final int COUNT_COLUMNS = 8;

    private int[][] board;
    private FlatlandAgent flatlandAgent;

    private int totalFood;
    private int totalPoison;

    public FlatlandBoard() {
        this.board = new int[COUNT_ROWS][COUNT_COLUMNS];
        initializeBoard();
    }

    public FlatlandBoard(int[][] board, FlatlandAgent flatlandAgent, int totalFood, int totalPoison) {
        this.board = board;
        this.flatlandAgent = flatlandAgent;
        this.totalFood = totalFood;
        this.totalPoison = totalPoison;
    }

    private void initializeBoard() {
        initializeAgentPosition();
        int numberOfCells = COUNT_ROWS * COUNT_COLUMNS;
        int maxFood = (int) (numberOfCells * 0.5);
        totalFood = generateCellContents(maxFood, CONTENT_FOOD);
        int maxPoison = (int) ((numberOfCells - totalFood) * 0.5);
        totalPoison = generateCellContents(maxPoison, CONTENT_POISON);
    }

    private void initializeAgentPosition() {
        Random random = new Random();
        int row = random.nextInt(COUNT_ROWS);
        int column = random.nextInt(COUNT_COLUMNS);
        board[row][column] = CONTENT_AGENT;
        flatlandAgent = new FlatlandAgent(row, column);
    }

    private int generateCellContents(int maxContent, int content) {
        int contentCreated = 0;
        Random random = new Random();

        for (int i = 0; i < COUNT_ROWS; i++) {
            for (int j = 0; j < COUNT_COLUMNS; j++) {
                if (board[i][j] == CONTENT_EMPTY && random.nextDouble() < 0.5) {
                    board[i][j] = content;
                    contentCreated++;
                }

                if (contentCreated == maxContent) {
                    break;
                }
            }
        }

        return contentCreated;
    }

    private int getContentFront() {
        int agentRow = flatlandAgent.getRow();
        int agentColumn = flatlandAgent.getColumn();

        switch (flatlandAgent.getDirection()) {
            case FlatlandAgent.DIRECTION_UP:
                if (agentRow == 0) {
                    return board[COUNT_ROWS - 1][agentColumn];
                }

                return board[agentRow - 1][agentColumn];
            case FlatlandAgent.DIRECTION_DOWN:
                if (agentRow == (COUNT_ROWS - 1)) {
                    return board[0][agentColumn];
                }

                return board[agentRow + 1][agentColumn];
            case FlatlandAgent.DIRECTION_RIGHT:
                if (agentColumn == (COUNT_COLUMNS - 1)) {
                    return board[agentRow][0];
                }

                return board[agentRow][agentColumn + 1];
            case FlatlandAgent.DIRECTION_LEFT:
                if (agentColumn == 0) {
                    return board[agentRow][COUNT_COLUMNS - 1];
                }

                return board[agentRow][agentColumn - 1];
            default:
                throw new RuntimeException("Illegal agent direction.");
        }
    }

    private int getContentRight() {
        int agentRow = flatlandAgent.getRow();
        int agentColumn = flatlandAgent.getColumn();

        switch (flatlandAgent.getDirection()) {
            case FlatlandAgent.DIRECTION_UP:
                if (agentColumn == (COUNT_COLUMNS - 1)) {
                    return board[agentRow][0];
                }

                return board[agentRow][agentColumn + 1];
            case FlatlandAgent.DIRECTION_DOWN:
                if (agentColumn == 0) {
                    return board[agentRow][COUNT_COLUMNS - 1];
                }

                return board[agentRow][agentColumn - 1];
            case FlatlandAgent.DIRECTION_RIGHT:
                if (agentRow == (COUNT_ROWS - 1)) {
                    return board[0][agentColumn];
                }

                return board[agentRow + 1][agentColumn];
            case FlatlandAgent.DIRECTION_LEFT:
                if (agentRow == 0) {
                    return board[COUNT_ROWS - 1][agentColumn];
                }

                return board[agentRow - 1][agentColumn];
            default:
                throw new RuntimeException("Illegal agent direction.");
        }
    }

    private int getContentLeft() {
        int agentRow = flatlandAgent.getRow();
        int agentColumn = flatlandAgent.getColumn();

        switch (flatlandAgent.getDirection()) {
            case FlatlandAgent.DIRECTION_UP:
                if (agentColumn == 0) {
                    return board[agentRow][COUNT_COLUMNS - 1];
                }

                return board[agentRow][agentColumn - 1];
            case FlatlandAgent.DIRECTION_DOWN:
                if (agentColumn == (COUNT_COLUMNS - 1)) {
                    return board[agentRow][0];
                }

                return board[agentRow][agentColumn + 1];
            case FlatlandAgent.DIRECTION_RIGHT:
                if (agentRow == 0) {
                    return board[COUNT_ROWS - 1][agentColumn];
                }

                return board[agentRow - 1][agentColumn];
            case FlatlandAgent.DIRECTION_LEFT:
                if (agentRow == (COUNT_ROWS - 1)) {
                    return board[0][agentColumn];
                }

                return board[agentRow + 1][agentColumn];
            default:
                throw new RuntimeException("Illegal agent direction.");
        }
    }

    public int moveForward() {
        int agentRow = flatlandAgent.getRow();
        int agentColumn = flatlandAgent.getColumn();
        int newAgentRow = 0;
        int newAgentColumn = 0;

        switch (flatlandAgent.getDirection()) {
            case FlatlandAgent.DIRECTION_UP:
                if (agentRow == 0) {
                    newAgentRow = COUNT_ROWS - 1;
                } else {
                    newAgentRow = agentRow - 1;
                }

                newAgentColumn = agentColumn;
                break;
            case FlatlandAgent.DIRECTION_DOWN:
                if (agentRow == (COUNT_ROWS - 1)) {
                    newAgentRow = 0;
                } else {
                    newAgentRow = agentRow + 1;
                }

                newAgentColumn = agentColumn;
                break;
            case FlatlandAgent.DIRECTION_RIGHT:
                if (agentColumn == (COUNT_COLUMNS - 1)) {
                    newAgentColumn = 0;
                } else {
                    newAgentColumn = agentColumn + 1;
                }

                newAgentRow = agentRow;
                break;
            case FlatlandAgent.DIRECTION_LEFT:
                if (agentColumn == 0) {
                    newAgentColumn = COUNT_COLUMNS - 1;
                } else {
                    newAgentColumn = agentColumn - 1;
                }

                newAgentRow = agentRow;
                break;
            default:
                throw new RuntimeException("Illegal agent direction.");
        }

        int contentEncountered = board[newAgentRow][newAgentColumn];
        board[agentRow][agentColumn] = CONTENT_EMPTY;
        board[newAgentRow][newAgentColumn] = CONTENT_AGENT;
        flatlandAgent.setRow(newAgentRow);
        flatlandAgent.setColumn(newAgentColumn);

        if (contentEncountered == CONTENT_FOOD) {
            flatlandAgent.addFood();
        } else if (contentEncountered == CONTENT_POISON) {
            flatlandAgent.addPoison();
        }

        return contentEncountered;
    }

    public int moveRight() {
        switch (flatlandAgent.getDirection()) {
            case FlatlandAgent.DIRECTION_UP:
                flatlandAgent.setDirection(FlatlandAgent.DIRECTION_RIGHT);
                break;
            case FlatlandAgent.DIRECTION_DOWN:
                flatlandAgent.setDirection(FlatlandAgent.DIRECTION_LEFT);
                break;
            case FlatlandAgent.DIRECTION_RIGHT:
                flatlandAgent.setDirection(FlatlandAgent.DIRECTION_DOWN);
                break;
            case FlatlandAgent.DIRECTION_LEFT:
                flatlandAgent.setDirection(FlatlandAgent.DIRECTION_UP);
                break;
            default:
                throw new RuntimeException("Illegal agent direction.");
        }

        return moveForward();
    }

    public int moveLeft() {
        switch (flatlandAgent.getDirection()) {
            case FlatlandAgent.DIRECTION_UP:
                flatlandAgent.setDirection(FlatlandAgent.DIRECTION_LEFT);
                break;
            case FlatlandAgent.DIRECTION_DOWN:
                flatlandAgent.setDirection(FlatlandAgent.DIRECTION_RIGHT);
                break;
            case FlatlandAgent.DIRECTION_RIGHT:
                flatlandAgent.setDirection(FlatlandAgent.DIRECTION_UP);
                break;
            case FlatlandAgent.DIRECTION_LEFT:
                flatlandAgent.setDirection(FlatlandAgent.DIRECTION_DOWN);
                break;
            default:
                throw new RuntimeException("Illegal agent direction.");
        }

        return CONTENT_EMPTY;
    }

    public ArrayList<Double> getAgentSensorValues() {
        ArrayList<Double> sensorValues = new ArrayList<>();

        sensorValues.add(getContentFront() == CONTENT_FOOD ? 1.0 : 0.0);
        sensorValues.add(getContentLeft() == CONTENT_FOOD ? 1.0 : 0.0);
        sensorValues.add(getContentRight() == CONTENT_FOOD ? 1.0 : 0.0);
        sensorValues.add(getContentFront() == CONTENT_POISON ? 1.0 : 0.0);
        sensorValues.add(getContentLeft() == CONTENT_POISON ? 1.0 : 0.0);
        sensorValues.add(getContentRight() == CONTENT_POISON ? 1.0 : 0.0);

        return sensorValues;
    }

    public int[][] getBoard() {
        return board;
    }

    public FlatlandAgent getFlatlandAgent() {
        return flatlandAgent;
    }

    public int getTotalFood() {
        return totalFood;
    }

    public int getTotalPoison() {
        return totalPoison;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < COUNT_ROWS; i++) {
            for (int j = 0; j < COUNT_COLUMNS; j++) {
                stringBuilder.append(board[i][j]).append("\t");
            }

            stringBuilder.append("\n");
        }

        stringBuilder.append("\n").append("food encountered: ").append(flatlandAgent.getNumberOfFoodConsumed()).append("/").append(totalFood);
        stringBuilder.append("\n").append("poison encountered: ").append(flatlandAgent.getNumberOfPoisonConsumed()).append("/").append(totalPoison);

        return stringBuilder.toString();
    }

    public FlatlandBoard clone() {
        int[][] boardClone = new int[COUNT_ROWS][COUNT_COLUMNS];

        for (int i = 0; i < COUNT_ROWS; i++) {
            for (int j = 0; j < COUNT_COLUMNS; j++) {
                boardClone[i][j] = board[i][j];
            }
        }

        return new FlatlandBoard(boardClone, flatlandAgent.clone(), totalFood, totalPoison);
    }
}
