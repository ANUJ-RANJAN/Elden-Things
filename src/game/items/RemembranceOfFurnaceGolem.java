package game.items;

import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.actors.Actor;

/**
 * A remembrance item dropped by the Furnace Golem.
 * Can be traded with a trader for the Furnace Engine.
 */
public class RemembranceOfFurnaceGolem extends RemembranceItem {

    /**
     * Constructor for the Remembrance of the Furnace Golem.
     */
    public RemembranceOfFurnaceGolem() {
        super("Remembrance of the Furnace Golem", '*', true);
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
