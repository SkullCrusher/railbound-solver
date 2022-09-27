import java.io.IOException;

public class DFSSolver implements Solver {

    public boolean loadConfiguration(String path) throws IOException {
        return Sim.loadFile(path);
    }

    public void solve() {

        // Print out the base map for debugging.
        this.Sim.printMap();

        /*



        */

    }
}
