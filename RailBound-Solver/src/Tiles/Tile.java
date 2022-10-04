package Tiles;

import Configuration.ConfigFile;
import Configuration.MovementConfig;
import Configuration.MovementItem;
import com.google.gson.Gson;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Tile {

    Point position;

    int origination = -1;

    boolean isExit = false;

    static HashMap<Integer, HashMap<Integer, MovementItem>> movementMapping = null;

    // The black listed combinations, it's impossible to place a piece of track in such a way.
    static HashMap<Integer, Boolean> rightBL = null;
    static HashMap<Integer, Boolean> bottomBL = null;
    static HashMap<Integer, Boolean> leftBL = null;
    static HashMap<Integer, Boolean> upBL = null;

    public Tile(Point pos, int origination, boolean isExit) {
        this.position = pos;
        this.origination = origination;
        this.isExit = isExit;
    }

    public int getType(){
        return origination;
    }

    public boolean getIsExit(){
        return this.isExit;
    }

    public boolean isValidTilePlacement(int mapWidth, int mapHeight){

        // Check if the position is right edge.
        if(this.position.x == (mapWidth - 1) && rightBL.get(this.origination) != null){
            return false;
        }

        // Check if the position is bottom edge.
        if(this.position.y == (mapHeight - 1) && bottomBL.get(this.origination) != null){
            return false;
        }

        // Check if the position is left edge.
        if(this.position.x == 0 && leftBL.get(this.origination) != null){
            return false;
        }

        // Check if the position is top edge.
        return this.position.y != 0 || upBL.get(this.origination) == null;
    }

    public MovementItem CalcNextPosition(int direction) throws IOException {

        if(movementMapping == null){
            this.Load();
        }

        HashMap<Integer, MovementItem> tmp = movementMapping.get(this.origination);

        if(tmp == null){
            return null;
        }

        return tmp.get(direction);
    }

    public void Load() throws IOException {

        // Prevent double loading.
        if(movementMapping != null){
            return;
        }

        // Load the file into a string if it exists.
        String content = new String(Files.readAllBytes(Paths.get("E:\\Source\\railbound-solver\\RailBound-Solver\\configuration\\moveConfig.json")));

        Gson g = new Gson();

        // Parse the JSON into a configuration object.
        MovementConfig config = g.fromJson(content, MovementConfig.class);

        // If it did not parse correctly, reject.
        if(config == null){
            return;
        }

        // Update the internal object.
        movementMapping = config.movementMapping;

        // Put the black list in place.
        rightBL = config.rightBlacklist;
        bottomBL = config.bottomBlacklist;
        leftBL = config.leftBlacklist;
        upBL = config.topBlacklist;
    }
}
