package Configuration;

import java.awt.*;
import java.util.HashMap;

public class MovementConfig {
    public HashMap<Integer, HashMap<Integer, MovementItem>> movementMapping = new HashMap<>();

    public HashMap<Integer, Boolean> rightBlacklist = new HashMap<>();
    public HashMap<Integer, Boolean> bottomBlacklist = new HashMap<>();
    public HashMap<Integer, Boolean> leftBlacklist = new HashMap<>();
    public HashMap<Integer, Boolean> topBlacklist = new HashMap<>();
}
