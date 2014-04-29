package localSignaling.vertexColoring;

import localSignaling.core.Edge;
import localSignaling.core.Node;
import localSignaling.core.NodeManager;

import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 28.04.14
 * Time: 18:01
 */
public class VertexColoringNodeManager extends NodeManager {
    private int maxColors;
    private ArrayList<boolean[]> availableNodeColors;

    public VertexColoringNodeManager(String filePath, int maxColors) {
        super(filePath);
        this.maxColors = maxColors;
        this.availableNodeColors = new ArrayList<>();
        initializeAvailableNodeColors();
    }

    private void initializeAvailableNodeColors() {
        for (int i = 0; i < super.getNodes().size(); i++) {
            boolean[] availableColors = new boolean[maxColors];

            for (int j = 0; j < availableColors.length; j++) {
                availableColors[j] = true;
            }

            availableNodeColors.add(availableColors);
        }
    }

    private boolean isAssigned(Node node) {
        return node.getState() != -1;
    }

    private boolean hasAvailableColors(Node node) {
        for (boolean availableColor : availableNodeColors.get(node.getIndex())) {
            if (availableColor) {
                return true;
            }
        }

        return false;
    }

    private void updateAvailableColors(Node node) {
        for (int i = 0; i < maxColors; i++) {
            boolean isAvailable = true;

            for (Node neighbor : node.getNeighbors()) {
                if (neighbor.getState() == i) {
                    availableNodeColors.get(node.getIndex())[i] = false;
                    isAvailable = false;
                }
            }

            if (isAvailable) {
                availableNodeColors.get(node.getIndex())[i] = true;
            }
        }
    }

    private int chooseColor(Node node) {
        boolean[] availableColors = availableNodeColors.get(node.getIndex());

        for (int i = 0; i < availableColors.length; i++) {
            if (availableColors[i]) {
                return i;
            }
        }

        return -1;
    }

    private int getNumberOfAdjacentColorsConflicts() {
        int count = 0;

        for (Edge edge : super.getEdges()) {
            Node node1 = super.getNodes().get(edge.getIndex1());
            Node node2 = super.getNodes().get(edge.getIndex2());

            if (isAssigned(node1) && isAssigned(node2)) {
                if (node1.getState() == node2.getState()) {
                    count++;
                }
            }
        }

        return count;
    }

    private int getNumberOfUnassignedConflicts() {
        int count = 0;

        for (Node node : super.getNodes()) {
            if (!isAssigned(node)) {
                count++;
            }
        }

        return count;
    }

    @Override
    public ArrayList<Node> getUnassignedNodes() {
        ArrayList<Node> unassignedNodes = new ArrayList<>();

        for (Node node : super.getNodes()) {
            if (node.getState() == -1) {
                unassignedNodes.add(node);
            }
        }

        //return unassignedNodes;
        return super.getNodes();
    }

    @Override
    public void roundOne(Node node) {
        if (isAssigned(node)) {
            for (Node neighbor : node.getNeighbors()) {
                if (!isAssigned(neighbor) && !hasAvailableColors(neighbor)) {
                    node.setState(-1);
                    updateAvailableColors(neighbor);
                }
            }
        } else {
            if (hasAvailableColors(node)) {
                node.setState(chooseColor(node));

                for (Node neighbor : node.getNeighbors()) {
                    if (neighbor.getState() == node.getState()) {
                        neighbor.setState(-1);
                    }
                }
            }
        }
    }

    @Override
    public void roundTwo(Node node) {
        if (isAssigned(node)) {
            for (Node neighbor : node.getNeighbors()) {
                updateAvailableColors(neighbor);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Adjacent colors conflicts: ").append(getNumberOfAdjacentColorsConflicts()).append("\n");
        stringBuilder.append("Unassigned conflicts: ").append(getNumberOfUnassignedConflicts());

        return stringBuilder.toString();
    }
}
