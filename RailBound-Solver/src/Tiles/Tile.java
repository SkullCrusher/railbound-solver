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

    int tunnelConnectNum = 0;
    int tunnelConnectExit = 0;

    static HashMap<Integer, HashMap<Integer, MovementItem>> movementMapping = null;

    // The black listed combinations, it's impossible to place a piece of track in such a way.
    static HashMap<Integer, Boolean> rightBL = null;
    static HashMap<Integer, Boolean> bottomBL = null;
    static HashMap<Integer, Boolean> leftBL = null;
    static HashMap<Integer, Boolean> upBL = null;

    public Tile(Point pos, int origination, boolean isExit, int tunnelConnectNum, int tunnelConnectExit) {
        this.position = pos;
        this.origination = origination;
        this.isExit = isExit;
        this.tunnelConnectNum = tunnelConnectNum;
        this.tunnelConnectExit = tunnelConnectExit;
    }

    public int getMovementMapCount(){
        return movementMapping.size();
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

    public int inverseDirection(int arg) {

        // Right
        if(arg == 0){
            return 2; // left
        }
        // Left
        if(arg == 2){
            return 0; // Right
        }
        // Down
        if(arg == 1){
            return 3; // Up
        }

        // Up.
        return 1; // Down.
    }

    public MovementItem CalcNextPosition(int direction, HashMap<Point, Tile> tiles, HashMap<Integer, Point> tunnels) throws IOException {

        // If there is no mapping, load the mapping.
        if(movementMapping == null){
            this.Load();
        }

        // Get the mapping for this tile.
        HashMap<Integer, MovementItem> tmp = movementMapping.get(this.origination);

        // If there is no mapping, it's impossible to move.
        if(tmp == null){
            return null;
        }

        // If there is no mapping for that direction, it's impossible to move.
        MovementItem newMove = tmp.get(direction);

        if(newMove == null){
            return null;
        }

        // Special case for tunnels that automatically moves it and delta is both 0.
        if(this.origination >= 17 && this.origination <= 20 && (newMove.x == 0 && newMove.y == 0)){

            // Find the other tunnel exit and place the entity there and inverse the direction.
            Point exit = tunnels.get(this.tunnelConnectExit);

            // If there is no found exit, it's a invalid configuration.
            if(exit == null){
                return null;
            }

            // Get the tile at that location.
            Tile newTile = tiles.get(exit);

            // If it isn't findable, it's invalid config.
            if(newTile == null){
                return null;
            }

            // Update the output to the correct location (it's the delta to the location).
            newMove.x = (exit.x - this.position.x);
            newMove.y = (exit.y - this.position.y);

            // Set the direction.
            if(newTile.origination == 17){
                newMove.direction = 2;
            }
            if(newTile.origination == 18){
                newMove.direction = 3;
            }
            if(newTile.origination == 19){
                newMove.direction = 0;
            }
            if(newTile.origination == 20){
                newMove.direction = 1;
            }
        }

        return newMove;
    }

    public void Load() throws IOException {

        // Prevent double loading.
        if(movementMapping != null){
            return;
        }

        // Load the file into a string if it exists.
        // String content = new String(Files.readAllBytes(Paths.get("E:\\Source\\railbound-solver\\RailBound-Solver\\configuration\\moveConfig.json")));
        String content = new String(Files.readAllBytes(Paths.get("./configuration/moveConfig.json")));

        //

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
