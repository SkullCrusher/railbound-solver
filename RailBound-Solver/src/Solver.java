import java.io.IOException;

public interface Solver {

    // Total Ticks
    // Simulation count
    // Cycle count
    // Solution count

    // A simulator to validate the solution.
    Simulation Sim = new Simulation();

    boolean loadConfiguration(String path) throws IOException;

    void solve();
}
