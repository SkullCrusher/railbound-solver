package Configuration;

import Entities.Entity;

public class ConfigFile {

    // The size of the map.
    public int mapX = 0;
    public int mapY = 0;

    // How many pieces of track are given to solve the puzzle.
    public int availableTrack = 0;

    public jsonEntity[] carts;
    public jsonTrack[] track;

    public ConfigFile(jsonEntity[] carts, int mapX, int mapY, jsonTrack[] track) {
        this.carts = carts;
        this.mapX = mapX;
        this.mapY = mapY;
        this.track = track;
    }

    public int getAvailableTrack() { return this.availableTrack; }

    public int getMapX(){
        return this.mapX;
    }

    public int getMapY(){
        return this.mapY;
    }

    public jsonEntity[] getCarts(){
        return this.carts;
    }

    public jsonTrack[] getTrack(){
        return this.track;
    }


}
