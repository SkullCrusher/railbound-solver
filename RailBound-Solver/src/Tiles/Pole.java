package Tiles;

import java.awt.*;

public class Pole implements Tile {

    /*
    * # CalcNextPosition
    *
    **/
    @Override
    public Point CalcNextPosition(int direction) {

        return new Point(0, 0);
    }

    @Override
    public int getType(){
        return 0;
    }


}
