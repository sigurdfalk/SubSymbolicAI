package flatland;

import java.util.Random;

/**
 * User: sigurd
 * Date: 24.03.14
 * Time: 17:22
 */
public class FlatlandAgent {
    public static final int DIRECTION_UP = 0;
    public static final int DIRECTION_DOWN = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_LEFT = 3;

    private int row;
    private int column;
    private int direction;
    private int numberOfFoodConsumed;
    private int numberOfPoisonConsumed;

    public FlatlandAgent(int row, int column) {
        this.row = row;
        this.column = column;
        this.direction = new Random().nextInt(4);
        this.numberOfFoodConsumed = 0;
        this.numberOfPoisonConsumed = 0;
    }

    public FlatlandAgent(int row, int column, int direction, int numberOfFoodConsumed, int numberOfPoisonConsumed) {
        this.row = row;
        this.column = column;
        this.direction = direction;
        this.numberOfFoodConsumed = numberOfFoodConsumed;
        this.numberOfPoisonConsumed = numberOfPoisonConsumed;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getNumberOfFoodConsumed() {
        return numberOfFoodConsumed;
    }

    public int getNumberOfPoisonConsumed() {
        return numberOfPoisonConsumed;
    }

    public void addFood() {
        numberOfFoodConsumed++;
    }

    public void addPoison() {
        numberOfPoisonConsumed++;
    }

    public FlatlandAgent clone() {
        return new FlatlandAgent(row, column, direction, numberOfFoodConsumed, numberOfPoisonConsumed);
    }
}
