package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;

/**
 * Abstract class representing a Remembrance item dropped by bosses.
 * Remembrance items are unique collectible items that can be traded for special rewards.
 */
public abstract class RemembranceItem extends Item {

    /**
     * Constructor for a remembrance item.
     *
     * @param name        the name of the remembrance item
     * @param displayChar the character to represent the item on the map
     */
    public RemembranceItem(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable); // Remembrance items are portable
    }

    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        if (portable) {
            return new PickUpAction(this);
        }
        return null;
    }

    /**
     * No additional allowable actions for remembrance items.
     *
     * @param owner the actor that owns this item.
     * @return an empty {@link ActionList}.
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        return new ActionList(); // No actions other than pick-up
    }
}

