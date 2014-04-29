package localSignaling.mis;

import localSignaling.core.Edge;
import localSignaling.core.Node;
import localSignaling.core.NodeManager;

import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 28.04.14
 * Time: 16:13
 */
public class MISNodeManager extends NodeManager {
    private ArrayList<Node> leaders;
    private ArrayList<Node> nonLeaders;

    public MISNodeManager(String filePath) {
        super(filePath);
        leaders = new ArrayList<>();
        nonLeaders = new ArrayList<>();
    }

    private boolean isAssigned(Node node) {
        return leaders.contains(node) || nonLeaders.contains(node);
    }

    private int getNumberOfAdjacentLeadersConflicts() {
        int count = 0;

        for (Edge edge : super.getEdges()) {
            Node node1 = super.getNodes().get(edge.getIndex1());
            Node node2 = super.getNodes().get(edge.getIndex2());

            if (leaders.contains(node1) && leaders.contains(node2)) {
                count++;
            }
        }

        return count;
    }

    private int getNumberOfNoLeaderConflicts() {
        int count = 0;

        for (Node nonLeader : nonLeaders) {
            boolean hasLeader = false;

            for (Node neighbor : nonLeader.getNeighbors()) {
                if (leaders.contains(neighbor)) {
                    hasLeader = true;
                }
            }

            if (!hasLeader) {
                count++;
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
            if (!isAssigned(node)) {
                unassignedNodes.add(node);
            }
        }

        return unassignedNodes;
    }

    @Override
    public void roundOne(Node node) {
        if (!isAssigned(node)) {
            node.setState(1);

            for (Node neighbor : node.getNeighbors()) {
                neighbor.setState(0);
            }
        }
    }

    @Override
    public void roundTwo(Node node) {
        if (!isAssigned(node) && node.getState() == 1) {
            leaders.add(node);

            for (Node neighbor : node.getNeighbors()) {
                if (!isAssigned(neighbor)) {
                    nonLeaders.add(neighbor);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        //Collections.sort(leaders);
        //Collections.sort(nonLeaders);
        //stringBuilder.append("Leaders: ").append(Arrays.toString(leaders.toArray())).append("\n");
        //stringBuilder.append("Non-leaders: ").append(Arrays.toString(nonLeaders.toArray())).append("\n");

        stringBuilder.append("Adjacent leaders conflicts: ").append(getNumberOfAdjacentLeadersConflicts()).append("\n");
        stringBuilder.append("No leader conflicts: ").append(getNumberOfNoLeaderConflicts()).append("\n");
        stringBuilder.append("Unassigned conflicts: ").append(getNumberOfUnassignedConflicts());

        return stringBuilder.toString();
    }
}
