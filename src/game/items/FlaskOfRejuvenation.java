package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import game.actions.ConsumeAction;

import java.util.Collections;
/**
 * Class representing a Flask of Rejuvenation, a consumable item that restores mana to the actor who consumes it.
 * <p>
 * This item has a limited number of uses and each use restores a specific amount of mana.
 */
public class FlaskOfRejuvenation extends ConsumableItem {
    /**
     * Constructor for FlaskOfRejuvenation.
     * Initializes the flask with 3 uses, each use restoring 100 mana.
     */
    public FlaskOfRejuvenation(){
        super(
                "Flask of Rejuvenation", 'o',
                Collections.singletonList(BaseActorAttributes.MANA), Collections.singletonList(100), // Base attributes
                Collections.emptyList(), Collections.emptyList(), // No actor-specific attributes
                3, null // Null item type or specify if needed
        );
    }
    /**
     * Defines the actions available to the owner of the Flask of Rejuvenation.
     * If the item is owned by the actor, they can consume it to restore mana.
     *
     * @param owner the actor who owns the item
     * @return an ActionList containing the available actions for this item
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();
        if (this.isOwnedBy(owner)) {
            actions.add(new ConsumeAction(this));
        }
        return actions;
    }
}
