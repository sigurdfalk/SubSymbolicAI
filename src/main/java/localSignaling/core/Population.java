package localSignaling.core;

import utilities.Mathematics;

import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 10.04.14
 * Time: 16:58
 */
public class Population {
    public static final int M = 34;

    private NodeManager nodeManager;
    private ArrayList<Node> nodes;

    public Population(NodeManager nodeManager) {
        this.nodeManager = nodeManager;
        this.nodes = nodeManager.getNodes();
    }

    public void search() {
        int maxNumberOfEdges = nodeManager.getMaxNumberOfEdges();
        double p = 1.0 / (double) maxNumberOfEdges;

        while (p < 1.0) {
            for (int i = 0; i < (M * Mathematics.log(nodes.size(), 2)); i++) {
                ArrayList<Node> unassignedNodes = nodeManager.getUnassignedNodes();

                // Round 1
                for (Node node : unassignedNodes) {
                    if (Math.random() <= p) {
                        nodeManager.roundOne(node);
                    }
                }

                // Round 2
                for (Node node : unassignedNodes) {
                    nodeManager.roundTwo(node);
                }
            }

            p *= 2.0;
        }
    }
}
