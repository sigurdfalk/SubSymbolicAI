package localSignaling.core;

/**
 * User: sigurd
 * Date: 27.04.14
 * Time: 13:34
 */
public class Edge {
    private int index1;
    private int index2;

    public Edge(int index1, int index2) {
        this.index1 = index1;
        this.index2 = index2;
    }

    public int getIndex1() {
        return index1;
    }

    public int getIndex2() {
        return index2;
    }
}
