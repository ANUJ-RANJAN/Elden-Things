package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;

/**
 * A class representing a random puddle of water.
 * This class defines a ground type that visually appears as a puddle and can have additional behavior associated with water if needed.
 *
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 * Mateysh Naidu, Anuj Ranjan
 */
public class Puddle extends Ground {
    /**
     * Constructor for Puddle. Initializes the Puddle with a display character '~' and a name "Puddle".
     */
    public Puddle() {
        super('~', "Puddle");
    }
}
