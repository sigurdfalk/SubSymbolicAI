package implementations.tracker;

import javax.swing.*;
import java.awt.*;

/**
 * User: sigurd
 * Date: 30.03.14
 * Time: 15:30
 */
public class TrackerInterface extends JFrame {
    private TrackerEnvironment trackerEnvironment;
    private TrackerEnvironmentViewPanel trackerEnvironmentViewPanel;

    private JTextField objectsCorrectlyAvoided;
    private JTextField objectsWronglyAvoided;
    private JTextField objectsCaught;
    private JTextField objectsCollided;

    public TrackerInterface(TrackerEnvironment trackerEnvironment) throws HeadlessException {
        super("Flatland");

        this.trackerEnvironment = trackerEnvironment;
        this.trackerEnvironmentViewPanel = new TrackerEnvironmentViewPanel(trackerEnvironment);

        objectsCorrectlyAvoided = new JTextField(3);
        objectsCorrectlyAvoided.setEditable(false);
        objectsWronglyAvoided = new JTextField(3);
        objectsWronglyAvoided.setEditable(false);
        objectsCaught = new JTextField(3);
        objectsCaught.setEditable(false);
        objectsCollided = new JTextField(3);
        objectsCollided.setEditable(false);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.add(new JLabel("Avoid"));
        infoPanel.add(objectsCorrectlyAvoided);
        infoPanel.add(new JLabel("Miss"));
        infoPanel.add(objectsWronglyAvoided);
        infoPanel.add(new JLabel("Caught"));
        infoPanel.add(objectsCaught);
        infoPanel.add(new JLabel("Collision"));
        infoPanel.add(objectsCollided);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        container.add(trackerEnvironmentViewPanel, BorderLayout.CENTER);
        container.add(infoPanel, BorderLayout.PAGE_END);

        update();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 670);
        setVisible(true);
    }

    public void update() {
        trackerEnvironmentViewPanel.update();
        objectsCorrectlyAvoided.setText(Integer.toString(trackerEnvironment.getObjectsCorrectlyAvoided()));
        objectsWronglyAvoided.setText(Integer.toString(trackerEnvironment.getObjectsWronglyAvoided()));
        objectsCaught.setText(Integer.toString(trackerEnvironment.getObjectsCaught()));
        objectsCollided.setText(Integer.toString(trackerEnvironment.getObjectsCollided()));
    }
}
