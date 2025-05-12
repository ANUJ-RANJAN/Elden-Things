package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;

/**
 * A class representing a wall that cannot be entered by any actor
 *
 */
public class Wall extends Ground {
    /**
     * Constructor for Wall. Initializes the Wall with a display character '#' and a name "Wall".
     */
    public Wall() {
        super('#', "Wall");
    }
    /**
     * Determines if the actor can enter this ground (Wall).
     *
     * @param actor the Actor trying to enter
     * @return false, as no actor can enter a Wall.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}
