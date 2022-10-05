import java.io.IOException;

public class Main {


    // The levels from world 1.
    static String [] world1 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "11a", "11b", "12", "12a", "13", "13a", "14", "14a", "15", "15a"};

    static void solve(String filename, String path) throws IOException {

        // Create a new solver.
        var dfs = new DFSSolver();

        // Load the configuration from test cases.
        boolean validConfiguration = dfs.loadConfiguration(path);

        // Verify that the configuration was parsable.
        if(!validConfiguration) {
            System.out.println("Invalid configuration '" + path + "'.");
            return;
        }

        // Attempt to solve the problem.
        dfs.solve();

        // Print out the results from the simulation.
        System.out.println("'" + filename + "' Time to compute " + dfs.computeTime + "ms, Solved: '" + dfs.foundSolution + "'");
    }


    public static void main(String[] args) throws IOException {

        // World 8.

        // World 7.

        // World 6.

        // World 5.

        // World 4.

        // World 3.

        // World 2.

        // solve("w-1-03.json", "./world-problems/world-1/w-1-03.json");

        // World 1.

        for (String s : world1) {
            // solve("./example/world-1/w-1-11b.json"); // Invalid solution?
            String filename = "w-1-" + s + ".json";
            solve(filename, "./world-problems/world-1/" + filename);
        }
        

    }
}