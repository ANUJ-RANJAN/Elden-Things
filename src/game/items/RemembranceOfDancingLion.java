package game.items;

import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.actors.Actor;

/**
 * A remembrance item dropped by the Divine Beast Dancing Lion.
 * Can be traded with a trader for the Divine Beast Head.
 */
public class RemembranceOfDancingLion extends RemembranceItem {

    /**
     * Constructor for the Remembrance of the Dancing Lion.
     */
    public RemembranceOfDancingLion() {
        super("Remembrance of the Dancing Lion", '*', true);
    }

    /**
     * Gets the pick-up action for the remembrance item.
     *
     * @param actor the actor attempting to pick up the item.
     * @return a {@link PickUpAction} if the item is portable, null otherwise.
     */
    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        return super.getPickUpAction(actor);
    }
}
