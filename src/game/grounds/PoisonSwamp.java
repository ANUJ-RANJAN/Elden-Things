package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.effects.PoisonStatusEffect;
import game.actors.Status;

/**
 * A class representing a Poison Swamp ground type.
 * Actors stepping into a Poison Swamp will be poisoned if they are not already, and the poison effect is applied over time.
 *
 * Poison swamps apply continuous damage to any actors that enter and are not immune to the poison effect.
 *
 * Modified by:
 * Mateysh Naidu
 */
public class PoisonSwamp extends Ground {
    /**
     * Constructor for PoisonSwamp.
     * Initializes the PoisonSwamp ground type with a display character '+' and a name "Poison Swamp".
     */
    public PoisonSwamp() {
        super('+', "Poison Swamp");
    }
    /**
     * Overrides the tick method to apply poison to any actor standing in the Poison Swamp.
     * If the actor does not already have the POISONED status, a PoisonStatusEffect is added.
     *
     * @param location the location of the Poison Swamp in the game map.
     */
    @Override
    public void tick(Location location) {
        Actor actor = location.getActor();
        if (actor != null) {
            if (!actor.hasCapability(Status.POISONED)) {
                PoisonStatusEffect poisonEffect = new PoisonStatusEffect();
                actor.addStatusEffect(poisonEffect);
                actor.addCapability(Status.POISONED);
            }
            PoisonStatusEffect poisonEffect = findPoisonStatusEffect(actor);
            poisonEffect.applyPoison();
        }
    }
    /**
     * Helper method to find the PoisonStatusEffect on an actor.
     *
     * @param actor the actor whose status effects are checked
     * @return the PoisonStatusEffect if present, otherwise null
     */
    private PoisonStatusEffect findPoisonStatusEffect(Actor actor) {
        for (StatusEffect effect : actor.getStatusEffects()) {
            if (effect.getClass() == PoisonStatusEffect.class) {
                return (PoisonStatusEffect) effect;
            }
        }
        return null;
    }
}

