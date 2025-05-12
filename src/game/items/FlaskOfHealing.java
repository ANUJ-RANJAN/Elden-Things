package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import game.actions.ConsumeAction;

import java.util.Collections;

/**
 * Class representing a Flask of Healing, a consumable item that restores health to the actor who consumes it.
 * <p>
 * This item has a limited number of uses and restores a specific amount of health upon consumption.
 */
public class FlaskOfHealing extends ConsumableItem {
    private static final int HEAL_AMOUNT = 150;

    /**
     * Constructor for FlaskOfHealing.
     * Initializes the flask with 5 uses and each use restores a specific amount of health.
     */
    public FlaskOfHealing() {
        super(
                "Flask of Healing", 'u',
                Collections.singletonList(BaseActorAttributes.HEALTH), Collections.singletonList(HEAL_AMOUNT), // Base attributes
                Collections.emptyList(), Collections.emptyList(), // No actor-specific attributes
                5, null // Null item type or specify if needed
        );
    }

    /**
     * Defines the actions available to the owner of the Flask of Healing.
     * If the item is owned by the actor, they can consume it to restore health.
     *
     * @param owner the actor who owns the item
     * @return an ActionList containing the available actions for this item
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();

        if (this.isOwnedBy(owner)) {
            actions.add(new ConsumeAction(this) {
                @Override
                public String execute(Actor actor, edu.monash.fit2099.engine.positions.GameMap map) {
                    int currentHealth = actor.getAttribute(BaseActorAttributes.HEALTH);
                    int maxHealth = actor.getAttributeMaximum(BaseActorAttributes.HEALTH);

                    // Calculate the actual healing amount
                    int healAmount;
                    if (maxHealth - currentHealth >= HEAL_AMOUNT) {
                        healAmount = HEAL_AMOUNT;
                    } else {
                        healAmount = maxHealth - currentHealth;
                    }

                    // Apply healing without exceeding max health
                    if (healAmount > 0) {
                        actor.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, healAmount);
                    }

                    // Reduce the number of uses for the flask
                    decreaseUse();

                    return actor + " uses " + FlaskOfHealing.this + " and restores " + healAmount + " health.";
                }
            });
        }

        return actions;
    }
}
