package localSignaling.gui;

import localSignaling.core.Edge;
import localSignaling.core.Node;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 28.04.14
 * Time: 17:01
 */
public class GraphViewer extends JFrame implements ChangeListener {
    private GraphViewerPanel graphViewerPanel;
    private JSlider scaleSlider;

    private double scale = 20;
    private double moveX = 0;
    private double moveY = 0;

    public GraphViewer(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.graphViewerPanel = new GraphViewerPanel(nodes, edges);
        scaleSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 10);
        scaleSlider.addChangeListener(this);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(new JLabel("Scale"));
        jPanel.add(scaleSlider);

        container.add(jPanel, BorderLayout.PAGE_END);
        container.add(graphViewerPanel, BorderLayout.CENTER);

        addMouseMotionListener(new MouseListener());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Viewer");
        setSize(900, 900);
    }

    public void update() {
        update(scale, moveX, moveY);
    }

    public void update(double scale, double moveX, double moveY) {
        graphViewerPanel.update(scale, moveX, moveY);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        scale = (double) scaleSlider.getValue();

        graphViewerPanel.update(scale, moveX, moveY);

    }

    private class MouseListener extends MouseInputAdapter {
        private double x;
        private double y;

        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {

            moveX += (e.getX() - x);
            moveY += (e.getY() - y);

            x = e.getX();
            y = e.getY();

            graphViewerPanel.update(scale, moveX, moveY);
        }
    }
}
