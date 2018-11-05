package Pacman;

import java.awt.Graphics2D;
import java.awt.Image;

public class Fantasma extends Tablero implements Runnable{
   
    private final int intMaxCantGhosts = 12;
    private final int intCantBlocksXY = 15;  //cantidad de bloques - EJE X y EJE Y
    private final int intCantPixelsBlockSize = 24;   //tamaño en pixeles de los bloques
    private int nrofghosts = 6;
    private int[] dx, dy;
    private int[] ghostx, ghosty, ghostdx, ghostdy, ghostspeed; 
    private short[] screendata;
    
    private Image ghost;
    
    private int pacmanx, pacmany, pacmandx, pacmandy;

    public Fantasma() {
        initVariables();
    }

    
    @Override
    public void run() {
        
    }
    
    public void initVariables(){
        ghostx = new int[intMaxCantGhosts];
        ghostdx = new int[intMaxCantGhosts];
        ghosty = new int[intMaxCantGhosts];
        ghostdy = new int[intMaxCantGhosts];
        ghostspeed = new int[intMaxCantGhosts];
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
    
    
}
