import Configuration.MovementItem;
import Tiles.Tile;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class DFSSolver implements Solver {

    HashMap<Integer, Point> sLoc = new HashMap<>();
    HashMap<Integer, Integer> sDir = new HashMap<>();


    public boolean loadConfiguration(String path) throws IOException {
        return Sim.loadFile(path);
    }

    public void solve() throws IOException {

        // Print out the base map for debugging.
        System.out.println("==== Starting Map ====");
        this.Sim.printMap();
        System.out.println("====\n");

        // Get the starting locations of each cart to prevent doing lookups.
        this.sLoc = this.Sim.getCartStartingPos();
        this.sDir = this.Sim.getCartStartingDirection();

        // How many tiles are there.
        int tileCount = new Tile(new Point(0, 0), 0, false).getMovementMapCount();

        // Generate the possible solutions.
        int totalSolutions = dfs(1, this.sLoc.get(1), this.sDir.get(1), this.Sim.getAvailableTrack(), tileCount, 0);

        System.out.println("solutions");
        System.out.println(totalSolutions);
    }

    int dfs(int cart, Point pos, int direction, int trackPiecesLeft, int typeOfTrackPieces, int depth) throws IOException {

        // this.Sim.printMap();
        if(this.sLoc == null || this.sDir == null){
            return 0;
        }

        // Stop going down once we get to the last cart.
        if(cart > this.Sim.getNumberOfCarts()){
            return 0;
        }

        // If the depth is over 1000, it's invalid.
        if(depth > 1000){
            return 0;
        }

        // If the position is off the map, reject it.
        if(!this.Sim.isPosInMap(pos)){
            return 0;
        }

        // If we can't do any more track pieces, reject.
        if(trackPiecesLeft < 0){
            return 0;
        }

        // If the current location has a piece of track, follow it.
        Tile currentTrack = this.Sim.getTileAtPos(pos);

        // If there is one, follow it and do the child.
        if(currentTrack != null){

            // If it's the exit, run the simulation.
            if(currentTrack.getIsExit()){
                boolean result = this.Sim.run();

                if(result){
                    this.Sim.printMap();
                }

                return (result) ? 1 : 0;
            }

            MovementItem newMove = currentTrack.CalcNextPosition(direction);

            // If there is NO next move, do nothing.
            if(newMove == null){
                return 0;
            }

            Point newPos = new Point(pos.x + newMove.x, pos.y + newMove.y);

            // Do the next item on the search from the move.
            return dfs(cart, newPos, newMove.direction, trackPiecesLeft, typeOfTrackPieces, depth + 1);
        }

        int solutions = 0;

        // Do the base simulation before the tile is added.
        if(sLoc.get(cart + 1) != null) {
            solutions += dfs(cart + 1, sLoc.get(cart + 1), sDir.get(cart + 1), trackPiecesLeft, typeOfTrackPieces, depth);
        }

        // Try adding each piece to the map at the current location and following it.
        for(int i = 1; i <= typeOfTrackPieces; i += 1) {

            // Simulate the child

            Tile newTile = new Tile(pos, i, false);

            // Prevent placing a tile that is impossible to place in the game.
            if(!newTile.isValidTilePlacement(this.Sim.getMapWidth(), this.Sim.getMapHeight())){
                continue;
            }

            // Place each piece of track and attempt to follow it.
            this.Sim.setTileAtPos(pos, newTile);

            // Do the simulations for the child carts (if possible).
            if(sLoc.get(cart + 1) != null) {
                solutions += dfs(cart + 1, this.sLoc.get(cart + 1), this.sDir.get(cart + 1), trackPiecesLeft - 1, typeOfTrackPieces, depth);
            }

            // Move the current cart.
            MovementItem newMove = newTile.CalcNextPosition(direction);

            // If there is a possible move.
            if (newMove != null) {
                Point newPos = new Point(pos.x + newMove.x, pos.y + newMove.y);

                // Do the depth first search base on the new location.
                solutions += dfs(cart, newPos, newMove.direction, trackPiecesLeft - 1, typeOfTrackPieces, depth);
            }

            // Remove it.
            this.Sim.removeTileAtPos(pos);
        }

        return solutions;
    }
}
