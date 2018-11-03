package Snake.enums;

public enum KeyDirections {

    UP(38),
    DOWN(40),
    LEFT(37),
    RIGHT(39);
    private int my_direction;

    private KeyDirections(final int the_direction) {
        setDirection(the_direction);
    }

    public void setDirection(final int the_direction) {
        my_direction = the_direction;
    }

    public int getDirection() {
        return my_direction;
    }
    
}
