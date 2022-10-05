import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        // Debugging.
        // String path = "E:\\Source\\railbound-solver\\RailBound-Solver\\Example\\world-1\\w-1-01.json";
        String path = "E:\\Source\\railbound-solver\\RailBound-Solver\\Example\\world-1\\w-1-02.json";

        // Create a new solver.
        var dfs = new DFSSolver();

        // Load the configuration from test cases.
        boolean validConfiguration = dfs.loadConfiguration(path);

        // Verify that the configuration was parsable.
        if(!validConfiguration) {
            System.out.println("Invalid configuration.");
            return;
        }

        // Attempt to solve the problem.
        dfs.solve();
    }
}