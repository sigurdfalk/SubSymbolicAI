package localSignaling.gui;

import localSignaling.core.Edge;
import localSignaling.core.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 28.04.14
 * Time: 17:02
 */
public class GraphViewerPanel extends JPanel {
    public static final int COLOR_RED = 0;
    public static final int COLOR_BLUE = 1;
    public static final int COLOR_GREEN = 2;
    public static final int COLOR_YELLOW = 3;
    public static final int COLOR_CYAN = 4;
    public static final int COLOR_MAGENTA = 5;
    public static final int COLOR_ORANGE = 6;
    public static final int COLOR_PINK = 7;

    public static final double COORDINATE_MULTIPLIER = 7.0;

    private double scale;
    private double moveX;
    private double moveY;

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    public GraphViewerPanel(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.scale = 0;
        this.moveX = 0;
        this.moveY = 0;

        this.nodes = nodes;
        this.edges = edges;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.scale(scale / 10, scale / 10);
        graphics2D.translate(moveX, moveY);

        for (Edge edge : edges) {
            Node node1 = nodes.get(edge.getIndex1());
            Node node2 = nodes.get(edge.getIndex2());
            graphics2D.draw(new Line2D.Double(node1.getX() * COORDINATE_MULTIPLIER, node1.getY() * COORDINATE_MULTIPLIER, node2.getX() * COORDINATE_MULTIPLIER, node2.getY() * COORDINATE_MULTIPLIER));
        }

        for (Node node : nodes) {
            graphics2D.setColor(getColor(node.getState()));
            graphics2D.fill(new Ellipse2D.Double((node.getX() * COORDINATE_MULTIPLIER) - 2, (node.getY() * COORDINATE_MULTIPLIER) - 2, 4, 4));
        }
    }

    private Color getColor(int value) {
        switch (value) {
            case COLOR_RED:
                return Color.RED;
            case COLOR_BLUE:
                return Color.BLUE;
            case COLOR_GREEN:
                return Color.GREEN;
            case COLOR_YELLOW:
                return Color.YELLOW;
            case COLOR_MAGENTA:
                return Color.MAGENTA;
            case COLOR_CYAN:
                return Color.CYAN;
            case COLOR_PINK:
                return Color.PINK;
            case COLOR_ORANGE:
                return Color.ORANGE;
            default:
                return Color.GRAY;
        }
    }

    public void update(double scale, double moveX, double moveY) {
        this.scale = scale;
        this.moveX = moveX;
        this.moveY = moveY;
        super.repaint();
    }
}
