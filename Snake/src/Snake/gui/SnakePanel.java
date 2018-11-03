package Snake.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SnakePanel extends JPanel {

    Color board[][] = new Color[30][30];
    private Rectangle2D[][] my_board = new Rectangle2D[30][30];
    private boolean collided;
    private boolean growMode;
    int growingThreeSpaces;
    int spacesToGrowTotal;
    int beginning;

    public SnakePanel(Color[][] gameBoard) {
        collided = false;
        growMode = false;
        beginning = 0;
        board = gameBoard;

        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 30; y++) {
                my_board[x][y]
                        = new Rectangle2D.Double(y * 15, x * 15, 14, 14);      //Quick fix
            }
        }   //board is correct here
    }

    public void paintComponent(final Graphics the_graphics) {
        super.paintComponent(the_graphics);
        //Rectangle2D my_square;
        final Graphics2D g2d = (Graphics2D) the_graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 30; y++) {
                g2d.setColor(board[x][y]);
                g2d.fill(my_board[x][y]);
                g2d.draw(my_board[x][y]);
            }
        }
    }

    public void clearOldSnake(List points) {
        //System.out.println("clearOldSnake(List points (points.size() = " + points.size() + ")");
        for (int x = 0; x < points.size(); x++) {
            board[points.get(x).x][points.get(x).y] = Color.GREEN;
        }
    }

    public boolean growMode() {
        return growMode;
    }

    /*
   * This updates the position of the snake by points (positions) on the map level
     */
    public void updateSnake(List points) {
        if (beginning < 2) {
            beginning++;
        }
        if (growMode) {
            growingThreeSpaces++;
            if (growingThreeSpaces == spacesToGrowTotal) {
                resetGrowMode();
            }
        }

        for (int x = 0; x < points.size(); x++) {
            //System.out.println("Coords: " + board[points.get(x).x][points.get(x).y]);
            if (board[points.get(x).x][points.get(x).y] == Color.BLUE) {
                //System.out.println("COLLIDED WITH SOMETHING THAT MAKES SNAKE GROW");
                spacesToGrowTotal += 3;
                growMode = true;

            } else if (board[points.get(x).x][points.get(x).y] != Color.GREEN
                    && board[points.get(x).x][points.get(x).y] != Color.BLUE) {
                if (beginning > 1) {
                    collided = true;
                }
            }

            board[points.get(x).x][points.get(x).y] = Color.YELLOW;
            //System.out.println("Coloring the snake yellow");
            //System.out.println(points.get(x).x + ", " + points.get(x).y);
        }
        repaint();
    }

    public boolean isCollided() {
        return collided;
    }

    public void resetCollided() {
        collided = false;
    }

    public void resetGrowMode() {
        spacesToGrowTotal = 0;
        growingThreeSpaces = 0;
        growMode = false;
    }

    public boolean checkForLevelCompletion() {
        boolean complete = true;
        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 30; y++) {
                if (board[x][y] == Color.BLUE) {
                    complete = false;
                }
            }
        }
        return complete;
    }
}
