import Configuration.ConfigFile;
import Configuration.jsonEntity;
import Entities.Entity;
import Tiles.Tile;
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

    int numberOfCarts = 0;
    int cartsThatFinished = 0;

    // The end location for the trains.
    public Point endPosition = new Point(-1, -1);

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
        this.entities = new Entity[config.carts.length];

        // Map the carts into the simulation.
        for(int i = 0; i < config.carts.length; i += 1 ){
            jsonEntity tmp = config.carts[i];
            this.entities[i] = new Entity(tmp.x, tmp.y, tmp.trainNumber, tmp.direction);
        }

        // Add up the total number of carts that are not pushers.
        for(int i = 0; i < config.carts.length; i += 1){
            if(config.carts[i].trainNumber >= 0){
                this.numberOfCarts += 1;
            }
        }

        // Put the track onto the map.
        for(int i = 0; i < config.track.length; i += 1){

            // The location the tile is on the map.
            Point location = new Point(config.track[i].x, config.track[i].y);

            // Generate a new track.
            Tile newTrack = new Tile(location, config.track[i].variation, config.track[i].isExit);

            // If it's the exit, remember it.
            if(config.track[i].isExit){
                this.endPosition = location;
            }

            // Load the config if possible.
            if(i == 0){
                newTrack.Load();
            }

            // Add the piece of track into the system.
            tiles.put(location, newTrack);
        }

        return true;
    }

    // Resets the internals for the next simulation.
    void reset() {
        this.tickCount = 0;
        this.cartsThatFinished = 0;

        // Reset the entities back to the start.
        for (Entity entity : this.entities) {
            entity.reset();
        }
    }

    // Runs the simulation with the current configuration.
    boolean run() {

        // Reset the simulation from last time.
        this.reset();

        // Run the simulation up to the limit of ticks, or it's finished.
        while((this.cartsThatFinished + 1) < this.numberOfCarts && this.tickCount < 1){

            // If the cart needs to still keep processing.
            boolean stillProcessing = false;

            // Move each entity based on its tile.
            for(Entity entity : this.entities){
                int result = entity.doNextMove(this.tiles, this.entities);

                System.out.print(result);
                System.out.print(" \n");
            }

            // Check for overlaps.
            System.out.println("\n");




            // Advance to the next tick.
            this.tickCount += 1;

            break;
        }

        // Debugging.
        this.printMapWithCarts();

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

    void printMapWithCarts(){
        System.out.println("=================================");

        int [][] generatedMap = this.generateMap();

        // Map the carts into the map.
        for (Entity c : this.entities) {
            generatedMap[c.getPos().y][c.getPos().x] = -10;
        }

        for(int y = 0; y < this.mapHeight; y += 1) {
            for (int x = 0; x < this.mapWidth; x += 1) {
                if(generatedMap[y][x] == -10){
                    System.out.print("x");
                }else{
                    System.out.print(generatedMap[y][x]);
                }

            }
            System.out.print("\n");
        }

        System.out.println("=================================");
    }

    // Print out the map for visual feedback.
    void printMap(){
        System.out.println("=================================");

        int [][] generatedMap = this.generateMap();

        // Print out the array.
        for(int y = 0; y < this.mapHeight; y += 1) {
            for (int x = 0; x < this.mapWidth; x += 1) {
                System.out.print(generatedMap[y][x]);
            }
            System.out.print("\n");
        }

        System.out.println("=================================");
    }

    int [][] generateMap() {
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

        return generatedMap;
    }
}
