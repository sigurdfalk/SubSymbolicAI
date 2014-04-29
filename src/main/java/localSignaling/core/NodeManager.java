package localSignaling.core;

import localSignaling.gui.GraphViewer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 11.04.14
 * Time: 00:21
 */
public abstract class NodeManager {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    private GraphViewer graphViewer;

    public NodeManager(final String filePath) {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        readGraphData(filePath);
        this.graphViewer = new GraphViewer(nodes, edges);
    }

    private void readGraphData(final String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line = null;
            int numberOfVertices = 0;
            int numberOfEdges = 0;

            for (int i = 0; (line = bufferedReader.readLine()) != null; i++) {
                String[] val = line.split(" ");

                if (i == 0) {
                    numberOfVertices = Integer.parseInt(val[0]);
                    numberOfEdges = Integer.parseInt(val[1]);
                } else if (i > 0 && i < (numberOfVertices + 1)) {
                    int index = Integer.parseInt(val[0]);
                    double x = Double.parseDouble(val[1]);
                    double y = Double.parseDouble(val[2]);

                    nodes.add(new Node(index, x , y));
                } else if (i >= (numberOfVertices + 1)) {
                    int i1 = Integer.parseInt(val[0]);
                    int i2 = Integer.parseInt(val[1]);

                    nodes.get(i1).addNeighbor(nodes.get(i2));
                    nodes.get(i2).addNeighbor(nodes.get(i1));
                    edges.add(new Edge(i1, i2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMaxNumberOfEdges() {
        int maxNumberOfEdges = Integer.MIN_VALUE;

        for (Node node : nodes) {
            int nodeEdges = node.getNeighbors().size();

            if (nodeEdges > maxNumberOfEdges) {
                maxNumberOfEdges = nodeEdges;
            }
        }

        return maxNumberOfEdges;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void visualize() {
        graphViewer.setVisible(true);
        graphViewer.update();
    }

    public abstract ArrayList<Node> getUnassignedNodes();

    public abstract void roundOne(Node node);

    public abstract void roundTwo(Node node);
}
