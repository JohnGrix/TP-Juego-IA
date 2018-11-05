package Pacman;

public class Juego {

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
    
    protected void initVariables(){    
    }

}
