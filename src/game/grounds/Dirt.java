package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
/**
 * A class that represents bare dirt on the ground.
 * Created by:
 * @author Riordan D. Alfredo
 *
 */
public class Dirt extends Ground {
    /**
     * Constructor for the Dirt class.
     * Initializes the Dirt ground with a character symbol and name.
     */
    public Dirt() {
        super('.', "Dirt");
    }
}