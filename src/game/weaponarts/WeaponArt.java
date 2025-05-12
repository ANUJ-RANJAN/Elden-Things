package game.weaponarts;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
/**
 * Interface representing a WeaponArt.
 * Weapon arts are special skills that can be activated by a weapon during an attack,
 * provided the actor meets the necessary mana requirements.
 */
public interface WeaponArt {
    /**
     * Activates the weapon art effect.
     *
     * @param actor the actor using the weapon art
     * @param map the current game map where the actor is located
     */

    void activate(Actor actor, GameMap map); // Activates the weapon art effect
}