package implementations.flatland;

import javax.swing.*;
import java.awt.*;

/**
 * User: sigurd
 * Date: 24.03.14
 * Time: 23:11
 */
public class FlatlandInterface extends JFrame {
    private FlatlandBoardViewPanel flatlandBoardViewPanel;
    private FlatlandBoard flatlandBoard;
    private JTextField foodConsumed;
    private JTextField poisonConsumed;

    public FlatlandInterface(FlatlandBoard flatlandBoard) throws HeadlessException {
        super("Flatland");

        this.flatlandBoard = flatlandBoard;
        this.flatlandBoardViewPanel = new FlatlandBoardViewPanel(flatlandBoard);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        foodConsumed = new JTextField(5);
        foodConsumed.setEditable(false);
        poisonConsumed = new JTextField(5);
        poisonConsumed.setEditable(false);

        infoPanel.add(new JLabel("Food consumed"));
        infoPanel.add(foodConsumed);
        infoPanel.add(new JLabel("Poison consumed"));
        infoPanel.add(poisonConsumed);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        container.add(flatlandBoardViewPanel, BorderLayout.CENTER);
        container.add(infoPanel, BorderLayout.PAGE_END);

        update();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 550);
        setVisible(true);
    }

    public void update() {
        flatlandBoardViewPanel.update();
        foodConsumed.setText(flatlandBoard.getFlatlandAgent().getNumberOfFoodConsumed() + "/" + flatlandBoard.getTotalFood());
        poisonConsumed.setText(flatlandBoard.getFlatlandAgent().getNumberOfPoisonConsumed() + "/" + flatlandBoard.getTotalPoison());
    }
}
