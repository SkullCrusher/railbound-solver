import Configuration.ConfigFile;
import Entities.Entity;
import Tiles.Tile;
import Tiles.Track;
import com.google.gson.Gson;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class Simulation {

    // Metrics.
    public int tickCount = 0;

    // The size of the map which defines the limitation.
    int mapWidth = 0;
    int mapHeight = 0;

    // Contains the map tiles that can be used for the simulation.
    HashMap<Point, Tile> tiles = new HashMap<>();

    // Entities
    Entity[] entities = new Entity[0];

    // Hooks are a number that ticks on each even, based on that tiles will know their state.
    HashMap<Integer, Integer> hooks = new HashMap<>();

    boolean loadFile(String filename) throws IOException {

        // Load the file into a string if it exists.
        String content = new String(Files.readAllBytes(Paths.get(filename)));

        Gson g = new Gson();

        // Parse the JSON into a configuration object.
        ConfigFile config = g.fromJson(content, ConfigFile.class);

        // If it did not parse correctly, reject.
        if(config == null){
            return false;
        }

        // Put the items into the system.
        this.mapWidth = config.mapX;
        this.mapHeight = config.mapY;
        this.entities = config.carts;

        // Put the track onto the map.
        for(int i = 0; i < config.track.length; i += 1){

            // The location the tile is on the map.
            Point location = new Point(config.track[i].x, config.track[i].y);

            // Generate a new track.
            Track newTrack = new Track(location, config.track[i].variation, config.track[i].isExit);

            // Add the piece of track into the system.
            tiles.put(location, newTrack);
        }

        return true;
    }

    // Resets the internals for the next simulation.
    void reset() {
        // TODO.
    }

    // Runs the simulation with the current configuration.
    boolean run()  {
        return false;
    }

    boolean isElementAt(Point pos){
        return this.tiles.containsKey(pos);
    }

    void setTileAtPos(Point pos, Tile newTile) {
        this.tiles.put(pos, newTile);
    }

    void removeTileAtPos(Point pos) {
        this.tiles.remove(pos);
    }

    // Print out the map for visual feedback.
    void printMap(){

        // Generate place holder to merge the elements in place.
        int[][] generatedMap = new int[this.mapHeight][this.mapWidth];

        // Map the values into gen.
        for (Map.Entry<Point, Tile> set : tiles.entrySet()) {

            // Get the value out of the list.
            Point key = set.getKey();
            Tile currentTile = set.getValue();

            // Set the value in place.
            generatedMap[key.y][key.x] = currentTile.getType();
        }

        // Print out the array.
        for(int y = 0; y < this.mapHeight; y += 1) {
            for (int x = 0; x < this.mapWidth; x += 1) {
                System.out.print(generatedMap[y][x]);
            }
            System.out.print("\n");
        }
    }
}
