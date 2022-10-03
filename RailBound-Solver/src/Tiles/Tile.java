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

    /*
     * # CalcNextPosition
     *
     **/
    public MovementItem CalcNextPosition(int direction) {
        return movementMapping.get(this.origination).get(direction);
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
    }
}
