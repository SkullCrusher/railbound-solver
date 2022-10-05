import java.io.IOException;

public class Main {

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

        // World 1
        // solve("./example/world-1/w-1-01.json");
        // solve("./example/world-1/w-1-02.json");
        // solve("./example/world-1/w-1-03.json");
        // solve("./example/world-1/w-1-04.json");
        // solve("./example/world-1/w-1-05.json");
        // solve("./example/world-1/w-1-06.json");
        // solve("./example/world-1/w-1-07.json");
        // solve("./example/world-1/w-1-08.json");
        // solve("./example/world-1/w-1-09.json");
        // solve("./example/world-1/w-1-10.json");
        // solve("./example/world-1/w-1-11.json");
        // solve("./example/world-1/w-1-11a.json");
        // solve("./example/world-1/w-1-11b.json"); // Invalid solution?
        // solve("./example/world-1/w-1-12.json");
        // solve("./example/world-1/w-1-12a.json");
        // solve("./example/world-1/w-1-13.json");
        // solve("./example/world-1/w-1-13a.json");
        // solve("./example/world-1/w-1-14.json");
        // solve("./example/world-1/w-1-14a.json");
        // solve("./example/world-1/w-1-15.json");
        // solve("./example/world-1/w-1-15a.json");
    }
}