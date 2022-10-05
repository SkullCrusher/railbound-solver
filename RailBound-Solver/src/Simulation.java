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

    // How many is the maximum number of cycles for a simulation.
    public int tickLimit = 50;

    // The size of the map which defines the limitation.
    int mapWidth = 0;
    int mapHeight = 0;

    // How many pieces of track total that are possible.
    int availableTrack = 0;

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
        this.availableTrack = config.availableTrack;
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
    boolean run() throws IOException {

        // Reset the simulation from last time.
        this.reset();

        // Keep track if the solution is valid.
        boolean validSolution = true;

        // Run the simulation up to the limit of ticks, or it's finished.
        while(validSolution && this.cartsThatFinished < this.numberOfCarts && this.tickCount < this.tickLimit){

            // Move each entity based on its tile.
            for(Entity entity : this.entities){
                int result = entity.doNextMove(this.tiles, this.entities);

                // Invalid or error.
                if(result == -1){
                    validSolution = false;
                    break;
                }

                // result > 0 (success, return train number).
                if(result > 0){

                    // Make sure that the cart that finished is lastCart + 1.
                    if(this.cartsThatFinished + 1 != result){
                        validSolution = false;
                        break;
                    }

                    // Mark the cart as done.
                    this.cartsThatFinished += 1;
                }
            }

            // Keep track of where each cart is to see if there are any collisions.
            HashMap<Point, Boolean> overlaps = new HashMap<>();

            // Check for overlaps.
            for(Entity entity : this.entities){

                // Skip the cart if it's completed.
                if(entity.getCompleted()){
                    continue;
                }

                Boolean tmp = overlaps.get(entity.getPos());

                // If there is a cart at the same position, it's invalid.
                if(tmp != null){
                    validSolution = false;
                    break;
                }

                // Mark the location as pre-flagged.
                overlaps.put(entity.getPos(), true);
            }

            // Advance to the next tick.
            this.tickCount += 1;

            // Debugging.
            // System.out.println(this.tickCount);
            // this.printMapWithCarts();
        }

        return validSolution;
    }

    boolean isElementAt(Point pos){
        return this.tiles.containsKey(pos);
    }

    int getAvailableTrack(){
        return this.availableTrack;
    }

    Tile getTileAtPos(Point pos) {
        return this.tiles.get(pos);
    }

    void setTileAtPos(Point pos, Tile newTile) {
        this.tiles.put(pos, newTile);
    }

    void removeTileAtPos(Point pos) {
        this.tiles.remove(pos);
    }

    // Find the cart that is being requested and return its current location.
    Point getCartPosition(int trainNumber){
        for(Entity entity : this.entities){
            if(entity.getTrainNumber() == trainNumber){
                return entity.getPos();
            }
        }

        return null;
    }

    HashMap<Integer, Point> getCartStartingPos(){

        HashMap<Integer, Point> result = new HashMap<>();

        for(Entity entity : this.entities){
            result.put(entity.getTrainNumber(), entity.getStartPos());
        }

        return result;
    }

    HashMap<Integer, Integer> getCartStartingDirection(){

        HashMap<Integer, Integer> result = new HashMap<>();

        for(Entity entity : this.entities){
            result.put(entity.getTrainNumber(), entity.getDirection());
        }

        return result;
    }

    public boolean isPosInMap(Point arg){
        return arg.x >= 0 && arg.y >= 0 && arg.x < this.mapWidth && arg.y < this.mapHeight;
    }

    public int getMapWidth() {
        return this.mapWidth;
    }

    public int getMapHeight(){
        return this.mapHeight;
    }

    int getNumberOfCarts(){
        return this.numberOfCarts;
    }

    void printMapWithCarts(){
        System.out.println("=================================");

        int [][] generatedMap = this.generateMap();

        // Map the carts into the map.
        for (Entity c : this.entities) {

            // Prevent drawing the cart if it goes off the array.
            if(c.getPos().x < 0 || c.getPos().y < 0 || c.getPos().y >=this.mapHeight || c.getPos().y >= this.mapWidth){
                continue;
            }

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
        System.out.println("====");

        int [][] generatedMap = this.generateMap();

        // Build the spacer to help make the map easier to read.
        StringBuilder spacer = new StringBuilder();
        spacer.append(" |  ".repeat(Math.max(0, this.mapWidth)));

        // Print out the array.
        for(int y = 0; y < this.mapHeight; y += 1) {

            // Print out the spacer.
            if(y > 0) {
                System.out.println(spacer);
            }

            for (int x = 0; x < this.mapWidth; x += 1) {
                if(x > 0){
                    System.out.print("-");
                }
                System.out.print(String.format("%03d", generatedMap[y][x]));
            }
            System.out.print("\n");
        }

        System.out.println("====");
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
