package Pacman;

import javax.swing.JFrame;

public class Pacman extends JFrame {

    public Pacman() {
        initUI();
    }

    private void initUI() {

        add(new Tablero());
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
