package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.PickUpAction;
import game.actions.ConsumeAction;
import game.actors.ActorAttributes;

import java.util.Arrays;
import java.util.Collections;

/**
 * Represents the Shadow Tree consumable item in the game.
 * <p>
 * This item grants both health and mana to the player when consumed.
 * It has a limited number of uses and can be picked up and used by the player.
 */
public class ShadowTree extends ConsumableItem {

    /**
     * Constructor that initializes the Shadow Tree item with
     * predefined health and mana recovery attributes.
     */
    public ShadowTree() {
        super(
                "Shadow Tree", 'e',
                Arrays.asList(BaseActorAttributes.HEALTH, BaseActorAttributes.MANA), Arrays.asList(50, 25),
                Collections.singletonList(ActorAttributes.STRENGTH), Collections.singletonList(5),
                1, ItemType.SHADOW_TREE
        );
    }
    /**
     * Gets the action for picking up the Shadow Tree item.
     *
     * @param actor the actor that will pick up the item.
     * @return a {@link PickUpAction} if the item is portable, null otherwise.
     */
    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        if (portable) {
            return new PickUpAction(this);
        }
        return null;
    }
    /**
     * Gets the list of actions available for the owner of this item.
     *
     * @param owner the actor that owns this item.
     * @return an {@link ActionList} of available actions.
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();

        if (this.isOwnedBy(owner)) {
            actions.add(new ConsumeAction(this));
        }

        return actions;
    }
    /**
     * Returns the item type of this object, which is {@link ItemType#SHADOW_TREE}.
     *
     * @return the {@link ItemType} of this item.
     */
    public ItemType getItemType() {
        return super.getItemType();
    }
}
