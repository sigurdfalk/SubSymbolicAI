package localSignaling.core;

import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 10.04.14
 * Time: 16:57
 */
public class Node implements Comparable<Node> {
    private int index;
    private double x;
    private double y;

    private int state;
    private ArrayList<Node> neighbors;

    public Node(int index, double x, double y) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.state = -1;
        this.neighbors = new ArrayList<>();
    }

    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIndex() {
        return index;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getState() {
        return state;
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    @Override
    public boolean equals(Object obj) {
        return this.index == ((Node) obj).index;
    }

    @Override
    public String toString() {
        return Integer.toString(index);
    }

    @Override
    public int compareTo(Node o) {
        if (this.index > o.getIndex()) {
            return 1;
        } else if (this.index < o.getIndex()) {
            return -1;
        } else {
            return 0;
        }
    }
}
