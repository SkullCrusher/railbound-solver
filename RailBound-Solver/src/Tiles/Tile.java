package Tiles;

import java.awt.*;
import java.util.HashMap;

public class Tile {

    Point position;

    int origination = -1;

    boolean isExit = false;

    /*
         // Track type.
         1:{
            // Direction of the cart coming in.
            0: Point(1, 0),
            2: Point(-1, 0)
         }
    */
    HashMap<Integer, HashMap<Integer, Point>> movementMapping = new HashMap<>();


    public Tile(Point pos, int origination, boolean isExit) {
        this.position = pos;
        this.origination = origination;
        this.isExit = isExit;
    }

    public int getType(){
        return origination;
    }

    /*
     * # CalcNextPosition
     *
     **/
    public Point CalcNextPosition(int direction) {

        return new Point(0, 0);
    }
}
