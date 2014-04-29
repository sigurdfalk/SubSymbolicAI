package localSignaling;

import localSignaling.core.Population;
import localSignaling.mis.MISNodeManager;

/**
 * User: sigurd
 * Date: 28.04.14
 * Time: 16:36
 */
public class LocalSignalingProgram {
    public static final String GRAPH_FILE_1 = "dataFiles/graph-color-1.txt";
    public static final String GRAPH_FILE_2 = "dataFiles/graph-color-2.txt";
    public static final String GRAPH_FILE_3 = "dataFiles/rand-50-4-color1.txt";
    public static final String GRAPH_FILE_4 = "dataFiles/rand-100-4-color1.txt";
    public static final String GRAPH_FILE_5 = "dataFiles/rand-100-6-color1.txt";
    public static final String GRAPH_FILE_6 = "dataFiles/spiral-500-4-color1.txt";

    public static void main(String[] args) {
        MISNodeManager nodeManager = new MISNodeManager(GRAPH_FILE_5);
        //VertexColoringNodeManager nodeManager = new VertexColoringNodeManager(GRAPH_FILE_6, 4);
        Population population = new Population(nodeManager);
        population.search();
        System.out.println(nodeManager);
        nodeManager.visualize();
    }
}
