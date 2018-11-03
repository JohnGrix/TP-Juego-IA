package Snake.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import Snake.enums.KeyDirections;

@SuppressWarnings("serial")
public class SnakeGUI extends JFrame {

    public static final int FRAME_HEIGHT = 500;
    public static final int FRAME_WIDTH = 455;
    //private int[] my_keys;

    private int my_snake_direction;
    private int my_current_level;

    private Listener my_listener = new Listener();

    private MyTimer my_timer_listener = new MyTimer();
    private Timer my_timer;
    private KeyboardListener my_keyboardlistener = new KeyboardListener();

    private final JPanel my_main_panel = new JPanel(new BorderLayout());

    private final JMenuBar my_menubar = new JMenuBar();

    private final JMenu my_menubar_game = new JMenu("Game");
    private final JMenuItem my_menubar_game_newgame = new JMenuItem("New Game");
    private final JMenuItem my_menubar_game_pause = new JMenuItem("Pause");
    private final JMenuItem my_menubar_game_quit = new JMenuItem("Quit");
    /**
     * ****
     */
    private final JMenu my_menubar_help = new JMenu("Help");
    private final JMenuItem my_menubar_help_rules = new JMenuItem("Rules");
    private final JMenuItem my_menubar_help_about = new JMenuItem("About");

    private SnakePanel my_panel;

    private Color[][] board;
    private Level level = new Level();

    private List my_snake;
    private List new_snake;

    public SnakeGUI() {
        my_current_level = 1;
        my_timer = new Timer(1000, my_timer_listener);

        my_snake = new ArrayList();
        new_snake = new ArrayList();

        my_timer.setDelay(1000);

        newGame();
        setup();
    }

    public void newGame() {
        my_timer.start();
        my_snake.clear();
        board = level.getLevel(my_current_level);   //set level at the same time of assigning my_current_level to 1
        my_snake.add(new Point(1, 14));
        my_snake.add(new Point(2, 14));
        my_snake_direction = 40;

    }

    public void setup() {
        my_panel = new SnakePanel(board);
        //my_panel.updateSnake(my_snake);    //temp
        my_main_panel.add(my_panel, BorderLayout.CENTER);
        addKeyListener(my_keyboardlistener);
        add(my_main_panel);
        setupMenuBar();
        setJMenuBar(my_menubar);
        setTitle("Snake");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        //my_panel.resetCollided();
        my_panel.updateSnake(my_snake);
        my_menubar_game_newgame.addActionListener(my_listener);
        my_menubar_game_pause.addActionListener(my_listener);
        my_menubar_game_quit.addActionListener(my_listener);
        my_menubar_help_rules.addActionListener(my_listener);
        my_menubar_help_about.addActionListener(my_listener);

    }

    private void setupMenuBar() {
        my_menubar.add(my_menubar_game);
        my_menubar_game.add(my_menubar_game_newgame);
        my_menubar_game.add(my_menubar_game_pause);
        my_menubar_game.addSeparator();
        my_menubar_game.add(my_menubar_game_quit);
        my_menubar.add(my_menubar_help);
        my_menubar_help.add(my_menubar_help_rules);
        my_menubar_help.addSeparator();
        my_menubar_help.add(my_menubar_help_about);
    }

    private class KeyboardListener implements KeyListener {

        /**
         * The key that was just pressed.
         *
         * @param the_event The event that just happened (used to acquire what
         * key was pressed).
         */
        public void keyPressed(final KeyEvent the_event) {
            final int key = the_event.getKeyCode();
            if (my_timer.isRunning()) {
                if (key == 38 && my_snake_direction != 40) {
                    my_snake_direction = key;
                    moveSnake(KeyDirections.UP.getDirection());
                } else if (key == 40 && my_snake_direction != 38) {
                    my_snake_direction = key;
                    moveSnake(KeyDirections.DOWN.getDirection());
                    //System.out.println("DOWN pressed");
                } else if (key == 37 && my_snake_direction != 39) {
                    my_snake_direction = key;
                    moveSnake(KeyDirections.LEFT.getDirection());
                    //System.out.println("LEFT pressed");
                } else if (key == 39 && my_snake_direction != 37) {
                    my_snake_direction = key;
                    moveSnake(KeyDirections.RIGHT.getDirection());
                    //System.out.println("RIGHT pressed");
                }
            }
        }

        /**
         * The key that was just released.
         *
         * @param the_event The event that just happened (used to acquire what
         * key was released).
         */
        public void keyReleased(final KeyEvent the_event) {
        }

        /**
         * The key that was just typed.
         *
         * @param the_event The event that just happened (used to acquire what
         * key was typed).
         */
        public void keyTyped(final KeyEvent the_event) {
        }
    }

    private void moveSnake(int direction) {
        //System.out.println(my_timer.getDelay());
        boolean skipit = true;
        new_snake.clear();
        my_panel.clearOldSnake(my_snake);

        //System.out.println(direction + "");
        //If the desired direction is UP and the snake's not going down (can't go down to up, must go left or right first)
        if (direction == 38) {
            //System.out.println("Going UP");
            Point newPoint = new Point(my_snake.get(my_snake.size() - 1).x - 1,
                    my_snake.get(my_snake.size() - 1).y);
            my_snake.add(newPoint);
            //my_snake.remove(0);
        } //Want to go DOWN, but can't if going UP
        else if (direction == 40) {
            //System.out.println("Going DOWN");
            Point newPoint = new Point(my_snake.get(my_snake.size() - 1).x + 1,
                    my_snake.get(my_snake.size() - 1).y);
            my_snake.add(newPoint);
            //my_snake.remove(0);
        } //Want to go LEFT, but can't if going RIGHT
        else if (direction == 37) {
            //System.out.println("Going LEFT");
            Point newPoint = new Point(my_snake.get(my_snake.size() - 1).x,
                    my_snake.get(my_snake.size() - 1).y - 1);
            my_snake.add(newPoint);
            //my_snake.remove(0);
        } //Want to go RIGHT, but can't if going LEFT
        else if (direction == 39) {
            //System.out.println("Going RIGHT");
            Point newPoint = new Point(my_snake.get(my_snake.size() - 1).x,
                    my_snake.get(my_snake.size() - 1).y + 1);
            my_snake.add(newPoint);
            //my_snake.remove(0);
        } else {
            skipit = false;
        }
        if (skipit) {
            if (my_panel.growMode()) {
                if (my_panel.checkForLevelCompletion()) {
                    my_current_level++;
                    if (my_current_level == 11) {
                        JOptionPane.showMessageDialog(null, "Good job.  You beat the game.");
                        my_current_level = 1;
                    }

                    my_timer.setDelay(my_timer.getDelay() - 80);

                    newGame();
                }
            } else {
                my_snake.remove(0);

            }
            my_panel.updateSnake(my_snake);

            if (my_panel.isCollided()) {
                //System.out.println("COLLIDED");
                gameOver();
            }
        }
    }

    private class MyTimer implements ActionListener {

        public void actionPerformed(final ActionEvent the_event) {
            moveSnake(my_snake_direction);
        }
    }

    private void gameOver() {
        my_timer.stop();
        //System.out.println("Game over");

        newGame();
        my_panel.resetCollided();
    }

    private class Listener implements ActionListener {

        public void actionPerformed(final ActionEvent the_event) {
            final String the_action = the_event.getActionCommand();

            if (the_action.equals(my_menubar_game_newgame.getActionCommand())) {
                my_timer.setDelay(1000);

                my_current_level = 1;
                newGame();
            } else if (the_action.equals(my_menubar_game_quit.getActionCommand())) {
                System.exit(0);
            } else if (the_action.equals(my_menubar_help_rules.getActionCommand())) {
                JOptionPane.showMessageDialog(null,
                        "Eric Sweeten\nSnake v1.0\n\n"
                        + "Keep the snake on the green.  Gather up all the food\n"
                        + "(blue squares), and once all the food is gathered, you\n"
                        + "pass the level.  There are 10 levels total.  Once you\n"
                        + "pass the 10th level, you beat the game.");
            } else if (the_action.equals(my_menubar_help_about.getActionCommand())) {
                JOptionPane.showMessageDialog(null,
                        "Eric Sweeten\nSnake v1.0\n\neric.sweeten@gmail.com");
            } else if (the_action.equals(my_menubar_game_pause.getActionCommand())) {
                togglePause();
            }
        }
    }

    private void togglePause() {
        if (my_timer.isRunning()) {
            my_timer.stop();
        } else {
            my_timer.start();
        }
    }
}
