package Tiles;

import java.awt.*;

public interface Tile {

    // The location of the tile.
    Point position = new Point(-1, -1);

    // What kind of tile it happens to be.
    int type = -1;

    Point CalcNextPosition(int direction);

}
