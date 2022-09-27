import java.awt.*;

/**
 * # Entity
 * Handles objects that can move (train carts and the pusher cart).
 **/
public class Entity {

    // The location that the entity starts.
    Point startPos = new Point(-1, -1);

    // Where the entity currently is going.
    Point pos = new Point(-1, -1);

    // The number of the cart (to check the order).
    int trainNumber = -1;

    // Direction: the direction the cart is attempting to move
    // 0: right, 1: down, 2: left, 3: up
    int direction = -1;

    // If we shouldn't process it anymore.
    boolean completed = false;

    // Do the next move for the entity.
    int doNextMove(){
        return 0;
    }

    Entity(Point start, int trainNumber, int direction){
        this.startPos = start;
        this.trainNumber = trainNumber;
        this.direction = direction;
    }

}
