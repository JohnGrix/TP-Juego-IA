package Pacman;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JComponent;

public class MenuFondo extends JPanel {

    private JPanel jp;
    private Image title;
    //private BufferedImage image;

    public MenuFondo() {
        jp = new JPanel();
        title = new ImageIcon(getClass().getResource("../Images/Pac-Man-Logo.bmp")).getImage();
        /*try{
            image = ImageIO.read(getClass().getResourceAsStream("../Images/Pac-Man-Logo.bmp"));
        }catch(IOException ex){
            ex.printStackTrace();
        }*/
    }
    
    @Override
    public void paintComponent(Graphics g) {
        jp.paintComponents(g);
        
        showTitle(g);
    }

    private void showTitle(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.drawImage(title, 240, 200, null);
    }
}
