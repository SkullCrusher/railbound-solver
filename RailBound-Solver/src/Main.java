import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {



    // The levels from world 1.
    static String [] world1 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "11a", "11b", "12", "12a", "13", "13a", "14", "14a", "15", "15a"};

    static void solve(String filename, String path, String outputPath) throws IOException {

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

        // Dump the solution to the file.
        if(dfs.foundSolution) {
            try {
                File myObj = new File(outputPath);

                // Create the new file.
                myObj.createNewFile();

                FileWriter myWriter = new FileWriter(outputPath);
                myWriter.write(dfs.solutionString);
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {

        // World 8.

        // World 7.

        // World 6.

        // World 5.

        // World 4.

        // World 3.

        // World 2.

        //solve("w-1-08.json", "./world-problems/world-1/w-1-08.json");

        // World 1.

        for (String s : world1) {
            // solve("./example/world-1/w-1-11b.json"); // Invalid solution?
            String filename = "w-1-" + s + ".json";
            solve(filename, "./world-problems/world-1/" + filename, "./generated-solutions/world-1/" + "w-1-" + s + ".txt");
        }

    }

    @Test
    public void world1Test() throws IOException {
        String basePath = "./world-solutions/world-1/";

        // Simulation the known solutions for world 1.
        for (String s : world1) {
            Simulation currentSimulation = new Simulation();

            String filename = "w-1-" + s + "-solved.json";

            // Load the configuration that is already solved.
            currentSimulation.loadFile(basePath + filename);

            // Run the simulation.
            boolean result = currentSimulation.run();

            // Run the simulation and verify it was successful.
            System.out.println("Simulation result on '" + filename + "' - " + result);

            if(!result){
                System.out.println(currentSimulation.printMap());
            }

            Assert.assertTrue(result);
        }
    }
}