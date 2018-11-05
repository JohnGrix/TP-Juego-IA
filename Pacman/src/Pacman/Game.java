/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pacman;

import javax.swing.JFrame;

/**
 *
 * @author Alex
 */
public class Game extends JFrame{

    public Game() {
        //Llama al metodo
        initUI();
    }
    
    //Metodo para inicializar la interfaz grafica del usuario
    private void initUI() {
        
        //Añade y llama al constructor de la clase tablero
        add(new Tablero());
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
}
