package Tiles;

import java.awt.*;

public class Track implements Tile {

    Point position;

    int origination = -1;

    boolean isExit = false;

    public Track(Point pos, int origination, boolean isExit) {
        this.position = pos;
        this.origination = origination;
        this.isExit = isExit;
    }

    @Override
    public int getType(){
        return origination;
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
