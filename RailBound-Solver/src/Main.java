import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        // Debugging.
        String path = "E:\\Source\\railbound-solver\\RailBound-Solver\\Example\\w-1-15-solved.json";

        // Create a new solver.
        var tmp = new DFSSolver();

        // Load the configuration from test cases.
        boolean validConfiguration = tmp.loadConfiguration(path);

        // Verify that the configuration was parsable.
        if(!validConfiguration) {
            System.out.println("Invalid configuration.");
            return;
        }

        // Attempt to solve the problem.
        tmp.solve();

        // System.out.println("Hello world!");
    }
}