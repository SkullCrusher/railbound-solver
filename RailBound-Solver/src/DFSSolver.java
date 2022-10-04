import java.io.IOException;

public class DFSSolver implements Solver {

    public boolean loadConfiguration(String path) throws IOException {
        return Sim.loadFile(path);
    }

    public void solve() {

        // Print out the base map for debugging.
        this.Sim.printMap();

        //

        // Debugging - Run the simulation.
        boolean isValid = this.Sim.run();

        System.out.println(isValid);

        dfs(1, 8, 14);
    }

    void dfs(int cartNumber, int trackPiecesLeft, int typeOfTrackPieces){

        // If the cart number goes past the maximum then it's too deep.
        if(cartNumber > this.Sim.getNumberOfCarts()){
            return;
        }

        // Do the depth first search (go to the highest number cart first).
        // dfs(int cartNumber, int trackPiecesLeft)
        for(int i = 0; i < typeOfTrackPieces; i += 1){

        }
    }
}
