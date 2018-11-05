package Pacman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tablero extends JPanel implements ActionListener {
    
    //Dimensiones del jframe
    private Dimension dimDimension;
    //Fuente o tipo de letra/tamaño
    private final Font fntFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;
    private final Color clrDotColor = new Color(192, 192, 0);
    private Color clrMazeColor;

    private boolean blnInGame = false;
    private boolean blnDying = false;

    private final int intCantPixelsBlockSize = 24;   //tamaño en pixeles de los bloques
    private final int intCantBlocksXY = 15;  //cantidad de bloques - EJE X y EJE Y
    private final int intScreenSize = intCantBlocksXY * intCantPixelsBlockSize; //tamaño pantalla = 360
    private final int intPacManAnimDelay = 2; //delay animacion Pacman 
    private final int intPacManCantAnim = 4;  //cantidad animaciones Pacman
    private final int intMaxCantGhosts = 12;
    private final int intPacManSpeed = 6;  //velocidad del Pacman

    private int intPacCantAnim = intPacManAnimDelay;    //cantidad de animaciones = delay animacion Pacman
    private int intPacManAnimDir = 1;
    private int pacmananimpos = 0;
    private int nrofghosts = 6;
    private int pacsleft, score;
    private int[] dx, dy;
    private int[] ghostx, ghosty, ghostdx, ghostdy, ghostspeed; 
    //primeras 2 variables (posicion sprite) - segundas 2 variables (cambios delta direccion horizontal y vertical)
    
    private Image ghost;
    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;
    private Image pacman4up, pacman4down, pacman4left, pacman4right;

    private int pacmanx, pacmany, pacmandx, pacmandy;
    private int reqdx, reqdy, viewdx, viewdy;
    
    //Significado nros Leveldata
    //16 = punto / 1 = borde izquierdo / 2 = borde superior / 4 = borde derecha
    //8 = borde inferior
    //Ejemplo: 1er num = 19 (16 + 2 + 1) / 2do num = 26 (16 + 8 + 2)

    private final short leveldata[] = {
        19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
        21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
        17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
        25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
        1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
        1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
        1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
        1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
        1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
        1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
        1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
        9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28
    };

    private final int validspeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxspeed = 6;

    private int currentspeed = 3;
    private short[] screendata;
    private Timer timer;

    public Tablero() {

        loadImages();
        initVariables();

        addKeyListener(new TAdapter()); //Se encarga de leer el boton del teclado

        setFocusable(true);

        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    private void initVariables() {

        screendata = new short[intCantBlocksXY * intCantBlocksXY];
        clrMazeColor = new Color(5, 100, 5);
        dimDimension = new Dimension(400, 400);
        ghostx = new int[intMaxCantGhosts];
        ghostdx = new int[intMaxCantGhosts];
        ghosty = new int[intMaxCantGhosts];
        ghostdy = new int[intMaxCantGhosts];
        ghostspeed = new int[intMaxCantGhosts];
        dx = new int[4];
        dy = new int[4];

        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }

    private void doAnim() {

        intPacCantAnim--;

        if (intPacCantAnim <= 0) {
            intPacCantAnim = intPacManAnimDelay;
            pacmananimpos = pacmananimpos + intPacManAnimDir;

            if (pacmananimpos == (intPacManCantAnim - 1) || pacmananimpos == 0) {
                intPacManAnimDir = -intPacManAnimDir;
            }
        }
    }

    private void playGame(Graphics2D g2d) {

        if (blnDying) {

            death();

        } else {

            movePacman();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, intScreenSize / 2 - 30, intScreenSize - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, intScreenSize / 2 - 30, intScreenSize - 100, 50);

        String s = "Presiona s para empezar.";
        Font fntIntroScreenFont = new Font("Helvetica", Font.BOLD, 15);
        FontMetrics metr = this.getFontMetrics(fntIntroScreenFont);

        g2d.setColor(Color.white);
        g2d.setFont(fntIntroScreenFont);
        g2d.drawString(s, (intScreenSize - metr.stringWidth(s)) / 2, intScreenSize / 2);
    }

    private void drawScore(Graphics2D g) {

        int i;
        String s;

        g.setFont(fntFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, intScreenSize / 2 + 96, intScreenSize + 16);

        for (i = 0; i < pacsleft; i++) {
            g.drawImage(pacman3left, i * 28 + 8, intScreenSize + 1, this);
        }
    }

    private void checkMaze() {  //chequea si quedan puntos por comer

        short i = 0;
        boolean finished = true;

        while (i < intCantBlocksXY * intCantBlocksXY && finished) {

            if ((screendata[i] & 48) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished) {

            score += 50;

            if (nrofghosts < intMaxCantGhosts) {
                nrofghosts++;
            }

            if (currentspeed < maxspeed) {
                currentspeed++;
            }

            initLevel();
        }
    }

    private void death() {//murio

        pacsleft--; //cantidad de vidas

        if (pacsleft == 0) {
            blnInGame = false;
        }

        continueLevel();
    }

    private void moveGhosts(Graphics2D g2d) {//mover fantasmas

        short i;
        int pos;
        int count;

        for (i = 0; i < nrofghosts; i++) {
            if (ghostx[i] % intCantPixelsBlockSize == 0 && ghosty[i] % intCantPixelsBlockSize == 0) {
                //determinar la posicion del fantasma
                pos = ghostx[i] / intCantPixelsBlockSize + intCantBlocksXY * (int) (ghosty[i] / intCantPixelsBlockSize);

                count = 0;

                if ((screendata[pos] & 1) == 0 && ghostdx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screendata[pos] & 2) == 0 && ghostdy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screendata[pos] & 4) == 0 && ghostdx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screendata[pos] & 8) == 0 && ghostdy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screendata[pos] & 15) == 15) {
                        ghostdx[i] = 0;
                        ghostdy[i] = 0;
                    } else {
                        ghostdx[i] = -ghostdx[i];
                        ghostdy[i] = -ghostdy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghostdx[i] = dx[count];
                    ghostdy[i] = dy[count];
                }

            }

            ghostx[i] = ghostx[i] + (ghostdx[i] * ghostspeed[i]);
            ghosty[i] = ghosty[i] + (ghostdy[i] * ghostspeed[i]);
            drawGhost(g2d, ghostx[i] + 1, ghosty[i] + 1);

            //Si hay colision entre Ghost y Pacman, Pacman muere
            if (pacmanx > (ghostx[i] - 12) && pacmanx < (ghostx[i] + 12)
                    && pacmany > (ghosty[i] - 12) && pacmany < (ghosty[i] + 12)
                    && blnInGame) {

                blnDying = true;
            }
        }
    }

    private void drawGhost(Graphics2D g2d, int x, int y) {

        g2d.drawImage(ghost, x, y, this);
    }

    private void movePacman() {

        int pos;
        short ch;
        
        //reqdx y reqdy son de la clase TAdapter
        if (reqdx == -pacmandx && reqdy == -pacmandy) {
            pacmandx = reqdx;
            pacmandy = reqdy;
            viewdx = pacmandx;
            viewdy = pacmandy;
        }

        if (pacmanx % intCantPixelsBlockSize == 0 && pacmany % intCantPixelsBlockSize == 0) {
            pos = pacmanx / intCantPixelsBlockSize + intCantBlocksXY * (int) (pacmany / intCantPixelsBlockSize);
            ch = screendata[pos];
            
            //Si Pacman come una galletita, se le suma un punto
            if ((ch & 16) != 0) {
                screendata[pos] = (short) (ch & 15);
                score++;
            }
            
            if (reqdx != 0 || reqdy != 0) {
                if (!((reqdx == -1 && reqdy == 0 && (ch & 1) != 0)
                        || (reqdx == 1 && reqdy == 0 && (ch & 4) != 0)
                        || (reqdx == 0 && reqdy == -1 && (ch & 2) != 0)
                        || (reqdx == 0 && reqdy == 1 && (ch & 8) != 0))) {
                    pacmandx = reqdx;
                    pacmandy = reqdy;
                    viewdx = pacmandx;
                    viewdy = pacmandy;
                }
            }

            //Pacman se detiene si no puede seguir en la direccion que venia
            if ((pacmandx == -1 && pacmandy == 0 && (ch & 1) != 0)
                    || (pacmandx == 1 && pacmandy == 0 && (ch & 4) != 0)
                    || (pacmandx == 0 && pacmandy == -1 && (ch & 2) != 0)
                    || (pacmandx == 0 && pacmandy == 1 && (ch & 8) != 0)) {
                pacmandx = 0;
                pacmandy = 0;
            }
        }
        pacmanx = pacmanx + intPacManSpeed * pacmandx;
        pacmany = pacmany + intPacManSpeed * pacmandy;
    }

    private void drawPacman(Graphics2D g2d) {

        if (viewdx == -1) {
            drawPacnanLeft(g2d);
        } else if (viewdx == 1) {
            drawPacmanRight(g2d);
        } else if (viewdy == -1) {
            drawPacmanUp(g2d);
        } else {
            drawPacmanDown(g2d);
        }
    }

    private void drawPacmanUp(Graphics2D g2d) {

        switch (pacmananimpos) {
            case 1:
                g2d.drawImage(pacman2up, pacmanx + 1, pacmany + 1, this);

                break;
            case 2:
                g2d.drawImage(pacman3up, pacmanx + 1, pacmany + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4up, pacmanx + 1, pacmany + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                break;
        }
    }

    private void drawPacmanDown(Graphics2D g2d) {

        switch (pacmananimpos) {
            case 1:
                g2d.drawImage(pacman2down, pacmanx + 1, pacmany + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3down, pacmanx + 1, pacmany + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4down, pacmanx + 1, pacmany + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                break;
        }
    }

    private void drawPacnanLeft(Graphics2D g2d) {

        switch (pacmananimpos) {
            case 1:
                g2d.drawImage(pacman2left, pacmanx + 1, pacmany + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3left, pacmanx + 1, pacmany + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4left, pacmanx + 1, pacmany + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                break;
        }
    }

    private void drawPacmanRight(Graphics2D g2d) {

        switch (pacmananimpos) {
            case 1:
                g2d.drawImage(pacman2right, pacmanx + 1, pacmany + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3right, pacmanx + 1, pacmany + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4right, pacmanx + 1, pacmany + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                break;
        }
    }

    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < intScreenSize; y += intCantPixelsBlockSize) {
            for (x = 0; x < intScreenSize; x += intCantPixelsBlockSize) {

                g2d.setColor(clrMazeColor);
                g2d.setStroke(new BasicStroke(2));

                if ((screendata[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + intCantPixelsBlockSize - 1);
                }

                if ((screendata[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + intCantPixelsBlockSize - 1, y);
                }

                if ((screendata[i] & 4) != 0) {
                    g2d.drawLine(x + intCantPixelsBlockSize - 1, y, x + intCantPixelsBlockSize - 1,
                            y + intCantPixelsBlockSize - 1);
                }

                if ((screendata[i] & 8) != 0) {
                    g2d.drawLine(x, y + intCantPixelsBlockSize - 1, x + intCantPixelsBlockSize - 1,
                            y + intCantPixelsBlockSize - 1);
                }

                if ((screendata[i] & 16) != 0) {
                    g2d.setColor(clrDotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }

                i++;
            }
        }
    }

    private void initGame() {

        pacsleft = 3;
        score = 0;
        initLevel();
        nrofghosts = 6;
        currentspeed = 3;
    }

    private void initLevel() {

        int i;
        for (i = 0; i < intCantBlocksXY * intCantBlocksXY; i++) {
            screendata[i] = leveldata[i];
        }

        continueLevel();
    }

    private void continueLevel() {

        short i;
        int dx = 1;
        int random;

        for (i = 0; i < nrofghosts; i++) {

            ghosty[i] = 4 * intCantPixelsBlockSize;  //posicion X inicio Ghosts
            ghostx[i] = 4 * intCantPixelsBlockSize;  //posicion Y inicio Ghosts
            ghostdy[i] = 0;
            ghostdx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentspeed + 1));

            if (random > currentspeed) {
                random = currentspeed;
            }

            ghostspeed[i] = validspeeds[random];
        }

        pacmanx = 7 * intCantPixelsBlockSize;
        pacmany = 11 * intCantPixelsBlockSize;
        pacmandx = 0;
        pacmandy = 0;
        reqdx = 0;
        reqdy = 0;
        viewdx = -1;
        viewdy = 0;
        blnDying = false;
    }

    private void loadImages() {

        ghost = new ImageIcon(getClass().getResource("../Images/Ghost.gif")).getImage();
        pacman1 = new ImageIcon(getClass().getResource("../Images/pacman.gif")).getImage();
        pacman2up = new ImageIcon(getClass().getResource("../Images/up1.gif")).getImage();
        pacman3up = new ImageIcon(getClass().getResource("../Images/up2.gif")).getImage();
        pacman4up = new ImageIcon(getClass().getResource("../Images/up3.gif")).getImage();
        pacman2down = new ImageIcon(getClass().getResource("../Images/down1.gif")).getImage();
        pacman3down = new ImageIcon(getClass().getResource("../Images/down2.gif")).getImage();
        pacman4down = new ImageIcon(getClass().getResource("../Images/down3.gif")).getImage();
        pacman2left = new ImageIcon(getClass().getResource("../Images/left1.gif")).getImage();
        pacman3left = new ImageIcon(getClass().getResource("../Images/left2.gif")).getImage();
        pacman4left = new ImageIcon(getClass().getResource("../Images/left3.gif")).getImage();
        pacman2right = new ImageIcon(getClass().getResource("../Images/right1.gif")).getImage();
        pacman3right = new ImageIcon(getClass().getResource("../Images/right2.gif")).getImage();
        pacman4right = new ImageIcon(getClass().getResource("../Images/right3.gif")).getImage();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, dimDimension.width, dimDimension.height);

        drawMaze(g2d);
        drawScore(g2d);
        doAnim();

        if (blnInGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (blnInGame) {
                
                if (key == KeyEvent.VK_LEFT) {
                    
                    reqdx = -1;
                    reqdy = 0;
                    
                } else if (key == KeyEvent.VK_RIGHT) {
                    
                    reqdx = 1;
                    reqdy = 0;
                    
                } else if (key == KeyEvent.VK_UP) {
                    
                    reqdx = 0;
                    reqdy = -1;
                    
                } else if (key == KeyEvent.VK_DOWN) {
                    
                    reqdx = 0;
                    reqdy = 1;
                    
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    
                    blnInGame = false;
                    
                } else if (key == KeyEvent.VK_PAUSE) {
                    
                    if (timer.isRunning()) {
                        
                        timer.stop();
                    } else {
                        
                        timer.start();
                    }
                }
            } else {
                
                if (key == 's' || key == 'S') {
                    
                    blnInGame = true;
                    initGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT || key == Event.UP || key == Event.DOWN) {
                reqdx = 0;
                reqdy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}
