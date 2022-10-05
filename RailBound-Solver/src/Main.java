import java.io.IOException;

public class Main {


    // The levels from world 1.
    static String [] world1 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "11a", "11b", "12", "12a", "13", "13a", "14", "14a", "15", "15a"};

    static void solve(String path) throws IOException {

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
    }

    public static void main(String[] args) throws IOException {

        // World 8.

        // World 7.

        // World 6.

        // World 5.

        // World 4.

        // World 3.

        // World 2.

        // World 1.
        for (int i = 0; i < world1.length; i += 1) {
            // for (String s : world1) {
            // solve("./example/world-1/w-1-11b.json"); // Invalid solution?

            System.out.println("zzzzzz");
            System.out.println(i + 1);
            solve("./world-problems/world-1/w-1-" + world1[i] + ".json");
        }

    }
}