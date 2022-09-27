package Tiles;

import java.awt.*;

public class Track implements Tile {

    /*
    * The
    **/
    int origination = -1;


    Track(int origination) {
        this.origination = origination;
    }

    /*
     * # CalcNextPosition
     *
     **/
    @Override
    public Point CalcNextPosition(int direction) {

        return new Point(0, 0);
    }

}
