package flatland;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * User: sigurd
 * Date: 24.03.14
 * Time: 23:16
 */
public class FlatlandBoardViewPanel extends JPanel {
    private final int BOX_HEIGHT = 20;
    private final int BOX_WIDTH = 20;

    private FlatlandBoard flatlandBoard;

    public FlatlandBoardViewPanel(FlatlandBoard flatlandBoard) {
        this.flatlandBoard = flatlandBoard;
    }

    public void update() {
        super.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.scale(3, 3);

        for (int i = 0; i <= FlatlandBoard.COUNT_ROWS; i++) {
            int xStart = 0;
            int yStart = i * BOX_HEIGHT;
            int xEnd = FlatlandBoard.COUNT_COLUMNS * BOX_WIDTH;
            int yEnd = yStart;
            graphics2D.draw(new Line2D.Double(xStart, yStart, xEnd, yEnd));
        }

        for (int i = 0; i <= FlatlandBoard.COUNT_COLUMNS; i++) {
            int xStart = i * BOX_WIDTH;
            int yStart = 0;
            int xEnd = xStart;
            int yEnd = FlatlandBoard.COUNT_ROWS * BOX_HEIGHT;
            graphics2D.draw(new Line2D.Double(xStart, yStart, xEnd, yEnd));
        }

        int[][] board = flatlandBoard.getBoard();

        for (int i = 0; i < FlatlandBoard.COUNT_ROWS; i++) {
            for (int j = 0; j < FlatlandBoard.COUNT_COLUMNS; j++) {
                int x = (j * BOX_WIDTH) + (BOX_WIDTH / 2);
                int y = (i * BOX_HEIGHT) + (BOX_HEIGHT / 2);

                if (board[i][j] == FlatlandBoard.CONTENT_FOOD) {
                    graphics2D.setColor(Color.GREEN);
                    graphics2D.fill(new Ellipse2D.Double(x - 3, y - 3, 6, 6));
                } else if (board[i][j] == FlatlandBoard.CONTENT_POISON) {
                    graphics2D.setColor(Color.RED);
                    graphics2D.fill(new Rectangle2D.Double(x - 3, y - 3, 6, 6));
                } else if (board[i][j] == FlatlandBoard.CONTENT_AGENT) {
                    drawAgent(graphics2D, x, y);
                }
            }
        }
    }

    private void drawAgent(Graphics2D graphics2D, int x, int y) {
        graphics2D.setColor(Color.BLUE);
        graphics2D.draw(new Rectangle2D.Double(x - 5, y - 5, 10, 10));

        FlatlandAgent flatlandAgent = flatlandBoard.getFlatlandAgent();
        ArrayList<Double> sensors = flatlandBoard.getAgentSensorValues();

        int direction = flatlandAgent.getDirection();

        if (direction == FlatlandAgent.DIRECTION_UP) {
            graphics2D.setColor(Color.YELLOW);

            if (sensors.get(0) == 1.0) {
                graphics2D.setColor(Color.GREEN);
            } else if (sensors.get(3) == 1.0) {
                graphics2D.setColor(Color.RED);
            }

            graphics2D.fill(new Ellipse2D.Double(x - 2, y - 7, 4, 4));

            graphics2D.setColor(Color.YELLOW);

            if (sensors.get(1) == 1.0) {
                graphics2D.setColor(Color.GREEN);
            } else if (sensors.get(4) == 1.0) {
                graphics2D.setColor(Color.RED);
            }

            graphics2D.fill(new Ellipse2D.Double(x - 7, y - 2, 4, 4));
            graphics2D.setColor(Color.YELLOW);

            if (sensors.get(2) == 1.0) {
                graphics2D.setColor(Color.GREEN);
            } else if (sensors.get(5) == 1.0) {
                graphics2D.setColor(Color.RED);
            }

            graphics2D.fill(new Ellipse2D.Double(x + 3, y - 2, 4, 4));


        } else if (direction == FlatlandAgent.DIRECTION_DOWN) {
            graphics2D.setColor(Color.YELLOW);

            if (sensors.get(0) == 1.0) {
                graphics2D.setColor(Color.GREEN);
            } else if (sensors.get(3) == 1.0) {
                graphics2D.setColor(Color.RED);
            }

            graphics2D.fill(new Ellipse2D.Double(x - 2, y + 3, 4, 4));

            graphics2D.setColor(Color.YELLOW);

            if (sensors.get(1) == 1.0) {
                graphics2D.setColor(Color.GREEN);
            } else if (sensors.get(4) == 1.0) {
                graphics2D.setColor(Color.RED);
            }

            graphics2D.fill(new Ellipse2D.Double(x + 3, y - 2, 4, 4));
            graphics2D.setColor(Color.YELLOW);

            if (sensors.get(2) == 1.0) {
                graphics2D.setColor(Color.GREEN);
            } else if (sensors.get(5) == 1.0) {
                graphics2D.setColor(Color.RED);
            }

            graphics2D.fill(new Ellipse2D.Double(x - 7, y - 2, 4, 4));

        } else if (direction == FlatlandAgent.DIRECTION_RIGHT) {
            graphics2D.setColor(Color.YELLOW);

            if (sensors.get(0) == 1.0) {
                graphics2D.setColor(Color.GREEN);
            } else if (sensors.get(3) == 1.0) {
                graphics2D.setColor(Color.RED);
            }

            graphics2D.fill(new Ellipse2D.Double(x + 3, y - 2, 4, 4));

            graphics2D.setColor(Color.YELLOW);

            if (sensors.get(1) == 1.0) {
                graphics2D.setColor(Color.GREEN);
            } else if (sensors.get(4) == 1.0) {
                graphics2D.setColor(Color.RED);
            }

            graphics2D.fill(new Ellipse2D.Double(x - 2, y - 7, 4, 4));
            graphics2D.setColor(Color.YELLOW);

            if (sensors.get(2) == 1.0) {
                graphics2D.setColor(Color.GREEN);
            } else if (sensors.get(5) == 1.0) {
                graphics2D.setColor(Color.RED);
            }

            graphics2D.fill(new Ellipse2D.Double(x - 2, y + 3, 4, 4));


        } else if (direction == FlatlandAgent.DIRECTION_LEFT) {
            graphics2D.setColor(Color.YELLOW);

            if (sensors.get(0) == 1.0) {
                graphics2D.setColor(Color.GREEN);
            } else if (sensors.get(3) == 1.0) {
                graphics2D.setColor(Color.RED);
            }

            graphics2D.fill(new Ellipse2D.Double(x - 7, y - 2, 4, 4));

            graphics2D.setColor(Color.YELLOW);

            if (sensors.get(1) == 1.0) {
                graphics2D.setColor(Color.GREEN);
            } else if (sensors.get(4) == 1.0) {
                graphics2D.setColor(Color.RED);
            }

            graphics2D.fill(new Ellipse2D.Double(x - 2, y + 3, 4, 4));
            graphics2D.setColor(Color.YELLOW);

            if (sensors.get(2) == 1.0) {
                graphics2D.setColor(Color.GREEN);
            } else if (sensors.get(5) == 1.0) {
                graphics2D.setColor(Color.RED);
            }

            graphics2D.fill(new Ellipse2D.Double(x - 2, y - 7, 4, 4));
        }
    }
}
