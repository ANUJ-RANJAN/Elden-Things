package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {

    /**
     * Constructor for the Floor.
     * Initializes the Floor with a display character and a name.
     */
    public Floor() {
        super('_', "Floor");
    }
}
