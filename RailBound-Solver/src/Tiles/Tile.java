package Tiles;

import java.awt.*;

public interface Tile {
    Point CalcNextPosition(int direction);

    public int getType();
}
