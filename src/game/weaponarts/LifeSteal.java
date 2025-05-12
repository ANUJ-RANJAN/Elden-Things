package game.weaponarts;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
/**
 * Class representing the "LifeSteal" weapon art.
 * LifeSteal allows an actor to drain health from enemies, healing itself by 20 hit points
 * if the actor has enough mana. The LifeSteal weapon art consumes 10 mana per activation.
 */
public class LifeSteal implements WeaponArt {

    private static final int manaCost = 10;

    /**
     * Activates the LifeSteal weapon art.
     * The actor spends 10 mana and heals itself by 20 hit points, as long as the actor's
     * current health is at least 20 points below its maximum health.
     *
     * @param actor the actor using the LifeSteal weapon art
     * @param map the current game map where the actor is located
     */
    @Override
    public void activate(Actor actor, GameMap map) {

        if (actor.getAttribute(BaseActorAttributes.MANA) >= manaCost) {
            actor.modifyAttribute(BaseActorAttributes.MANA, ActorAttributeOperations.DECREASE, 10);
            if (actor.getAttributeMaximum(BaseActorAttributes.HEALTH) - actor.getAttribute(BaseActorAttributes.HEALTH)  >= 20) {
                actor.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 20);
            } else if (actor.getAttributeMaximum(BaseActorAttributes.HEALTH) - actor.getAttribute(BaseActorAttributes.HEALTH) < 20) {
                actor.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, actor.getAttributeMaximum(BaseActorAttributes.HEALTH));
            }
        }
    }
    /**
     * Returns the mana cost of the LifeSteal weapon art.
     *
     * @return the mana cost, which is 10
     */
    public int manaCost() {
        return manaCost;
    }
}