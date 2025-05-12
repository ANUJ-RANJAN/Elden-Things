package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.ActorAttributes;
import game.items.ConsumableItem;

import java.util.List;
/**
 * Class representing an action for consuming a consumable item.
 *
 * <p>The action allows an actor to use an item that has a consumable effect, modifying the actor's attributes
 * based on the effects of the consumable item. Each consumable item has a limited number of uses,
 * and this class handles the logic for applying those effects and reducing the number of uses remaining.</p>
 *
 * @author Adrian Kristanto
 */
public class ConsumeAction extends Action {
    /**
     * The consumable item being used.
     */
    private ConsumableItem item;

    public ConsumeAction(ConsumableItem item) {
        this.item = item;

    }
    /**
     * Executes the consume action on the target actor.
     *
     * <p>This method applies the effects of the consumable item to the actor, modifying its attributes.
     * It also checks if the item is empty and reduces the number of uses left for the item.</p>
     *
     * @param actor the actor performing the consume action
     * @param map the game map where the action is being performed
     * @return a string describing the result of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (item.isEmpty()) {
            return item + " is empty.";
        }

        // Apply effects on BaseActorAttributes if present
        List<BaseActorAttributes> baseAttributes = item.getBaseEffects();
        List<Integer> baseValues = item.getBaseEffectValues();
        for (int i = 0; i < baseAttributes.size(); i++) {
            BaseActorAttributes effect = baseAttributes.get(i);
            int effectValue = baseValues.get(i);

            // Apply maximum increase or regular increase based on effect value
            actor.modifyAttributeMaximum(effect, ActorAttributeOperations.INCREASE, effectValue);
            actor.modifyAttribute(effect, ActorAttributeOperations.UPDATE, actor.getAttributeMaximum(effect));
        }

        // Apply effects on ActorAttributes if present
        List<ActorAttributes> actorAttributes = item.getActorEffects();
        List<Integer> actorValues = item.getActorEffectValues();
        if (!actorAttributes.isEmpty()) {
            for (int i = 0; i < actorAttributes.size(); i++) {
                ActorAttributes effect = actorAttributes.get(i);
                int effectValue = actorValues.get(i);
                actor.modifyAttributeMaximum(effect, ActorAttributeOperations.INCREASE, effectValue);
            }
            actor.removeItemFromInventory(item); // Remove item from inventory after use if ActorAttributes effects are applied
        } else {
            item.decreaseUse(); // Decrease use count if there are no ActorAttributes effects
        }

        return actor + " uses " + item + " and gains effects on attributes.";
    }
    /**
     * Provides a description of the consume action for display in the menu.
     *
     * @param actor the actor performing the action
     * @return a string describing the action and the number of charges remaining for the item
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes " + item + ". " + item.getNoOfUse() + " charges remain.";
    }

}
