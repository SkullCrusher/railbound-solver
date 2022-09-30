package Entities;

import Tiles.Tile;

import java.awt.*;
import java.util.HashMap;

/**
 * # Entities.Entity
 * Handles objects that can move (train carts and the pusher cart).
 **/
public class Entity {

    // The location that the entity starts.
    Point startPos;

    // Where the entity currently is going.
    Point pos;

    // The number of the cart (to check the order).
    int trainNumber = -1;

    // Direction: the direction the cart is attempting to move
    // 0: right, 1: down, 2: left, 3: up
    int direction = -1;
    int startDirection = -1;

    // If we shouldn't process it anymore.
    boolean completed = false;

    public Entity(int x, int y, int trainNumber, int direction){
        this.startPos = new Point(x, y);
        this.pos = new Point(x, y);
        this.trainNumber = trainNumber;
        this.direction = direction;
        this.startDirection = direction;
    }

    // Do the next move for the entity.
    public int doNextMove(HashMap<Point, Tile> tiles, Entity[] entities){

        // Stop processing if it got to the exit.
        if(this.completed) {
            return 0;
        }

        // System.out.println("-zzz");

        // System.out.println(tiles);
        Tile currentTile = tiles.get(this.pos);

        // System.out.println(currentTile);
        // System.out.println(this.pos);

        // System.out.println("-zzz");

        return -1;
    }

    public void reset(){
        this.completed = false;
        this.pos = this.startPos;
        this.direction = this.startDirection;
    }

    public int getTrainNumber(){
        return this.trainNumber;
    }

    public Point getPos() { return this.pos; }
}
