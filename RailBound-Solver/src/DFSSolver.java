import Configuration.MovementItem;
import Tiles.Tile;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class DFSSolver implements Solver {

    public boolean loadConfiguration(String path) throws IOException {
        return Sim.loadFile(path);
    }

    public void solve() throws IOException {

        // Print out the base map for debugging.
        System.out.println("====");
        this.Sim.printMap();
        System.out.println("====\n");

        // Debugging simulation.
        // System.out.println(this.Sim.run());

        // Get the starting locations of each cart to prevent doing lookups.
        HashMap<Integer, Point> sLoc = this.Sim.getCartStartingPos();
        HashMap<Integer, Integer> sDir = this.Sim.getCartStartingDirection();

        // Generate the possible solutions.
        int totalSolutions = dfs(sLoc.get(1), sDir.get(1), this.Sim.getAvailableTrack(), 15);

        System.out.println("solutions");
        System.out.println(totalSolutions);
    }

    int dfs(Point pos, int direction, int trackPiecesLeft, int typeOfTrackPieces) throws IOException {

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
            return dfs(newPos, newMove.direction, trackPiecesLeft, typeOfTrackPieces);
        }

        int solutions = 0;

        // Try adding each piece to the map at the current location and following it.
        for(int i = 1; i <= typeOfTrackPieces; i += 1) {

            Tile newTile = new Tile(pos, i, false);

            // Prevent placing a tile that is impossible to place in the game.
            if(!newTile.isValidTilePlacement(this.Sim.getMapWidth(), this.Sim.getMapHeight())){
                continue;
            }

            // Place each piece of track and attempt to follow it.
            this.Sim.setTileAtPos(pos, newTile);

            MovementItem newMove = newTile.CalcNextPosition(direction);

            // If there is a possible move.
            if (newMove != null) {
                Point newPos = new Point(pos.x + newMove.x, pos.y + newMove.y);

                // Do the depth first search base on the new location.
                solutions += dfs(newPos, newMove.direction, trackPiecesLeft - 1, typeOfTrackPieces);
            }

            // Remove it.
            this.Sim.removeTileAtPos(pos);
        }

        return solutions;
    }
}
