package implementations.tracker;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * User: sigurd
 * Date: 30.03.14
 * Time: 15:30
 */
public class TrackerEnvironmentViewPanel extends JPanel {
    private final int BOX_HEIGHT = 20;
    private final int BOX_WIDTH = 20;

    private TrackerEnvironment trackerEnvironment;

    public TrackerEnvironmentViewPanel(TrackerEnvironment trackerEnvironment) {
        this.trackerEnvironment = trackerEnvironment;
    }

    public void update() {
        super.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.scale(2, 2);

        int objectRowNumber = TrackerEnvironment.ENVIRONMENT_HEIGHT - trackerEnvironment.getObjectRowNumber();
        boolean[] objectRow = trackerEnvironment.getObjectRow();
        boolean[] agentRow = trackerEnvironment.getAgentRow();

        ArrayList<Double> agentSensors = trackerEnvironment.getAgentSensors();

        for (int i = trackerEnvironment.getAgentStartIndex(), j = 0; j < agentSensors.size(); i++) {
            if (i == agentRow.length) {
                i = 0;
            }

            if (agentRow[i]) {
                int xStart = i * BOX_WIDTH + (BOX_WIDTH / 2);
                int xEnd = xStart;
                int yStart = 0;
                int yEnd = (TrackerEnvironment.ENVIRONMENT_HEIGHT) * BOX_HEIGHT;

                if (agentSensors.get(j) == 1.0) {
                    yStart = objectRowNumber * BOX_HEIGHT;
                    graphics2D.setColor(Color.RED);
                } else {
                    yStart = 0;
                    graphics2D.setColor(Color.YELLOW);
                }

                graphics2D.draw(new Line2D.Double(xStart, yStart, xEnd, yEnd));
                j++;
            }
        }

        graphics2D.setColor(Color.GREEN);

        for (int i = 0; i < objectRow.length; i++) {
            if (objectRow[i]) {
                int x = i * BOX_WIDTH;
                int y = (objectRowNumber - 1) * BOX_HEIGHT;
                graphics2D.fill(new Rectangle2D.Double(x, y, BOX_WIDTH, BOX_HEIGHT));
            }
        }

        for (int i = trackerEnvironment.getAgentStartIndex(), j = 0; j < agentSensors.size(); i++) {
            if (i == agentRow.length) {
                i = 0;
            }

            if (agentRow[i]) {
                if (objectRowNumber == (TrackerEnvironment.ENVIRONMENT_HEIGHT) && agentSensors.get(j) == 1.0) {
                    graphics2D.setColor(Color.RED);
                } else {
                    graphics2D.setColor(Color.BLUE);
                }

                int x = i * BOX_WIDTH;
                int y = (TrackerEnvironment.ENVIRONMENT_HEIGHT - 1) * BOX_HEIGHT;
                graphics2D.fill(new Rectangle2D.Double(x, y, BOX_WIDTH, BOX_HEIGHT));
                j++;
            }
        }

        graphics2D.setColor(Color.BLACK);

        for (int i = 0; i <= TrackerEnvironment.ENVIRONMENT_HEIGHT; i++) {
            int xStart = 0;
            int yStart = i * BOX_HEIGHT;
            int xEnd = TrackerEnvironment.ENVIRONMENT_WIDTH * BOX_WIDTH;
            int yEnd = yStart;
            graphics2D.draw(new Line2D.Double(xStart, yStart, xEnd, yEnd));
        }

        for (int i = 0; i <= TrackerEnvironment.ENVIRONMENT_WIDTH; i++) {
            int xStart = i * BOX_WIDTH;
            int yStart = 0;
            int xEnd = xStart;
            int yEnd = TrackerEnvironment.ENVIRONMENT_HEIGHT * BOX_HEIGHT;
            graphics2D.draw(new Line2D.Double(xStart, yStart, xEnd, yEnd));
        }
    }
}
