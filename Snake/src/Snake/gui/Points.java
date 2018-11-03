package Snake.gui;

import java.awt.Point;
import Snake.enums.KeyDirections;

public class Points {
    /*
    * "thisPartsPoint" = the coordinates for this part of the snake's body
    * "thisPartsDirection" = the direction this part of the snake's body is facing
    */
    Point thisPartsPoint = new Point();
    KeyDirections thisPartsDirection;

    public Points(final Point the_point, final KeyDirections the_direction) {
        thisPartsPoint = the_point;
        thisPartsDirection = the_direction;
    }

    public Point getPoint() {
        return thisPartsPoint;
    }

}
