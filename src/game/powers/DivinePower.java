package game.powers;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Abstract class representing a divine power used by the Divine Beast Dancing Lion.
 * <p>
 * Each divine power has its own special attack and can switch to another power state.
 * </p>
 */
public abstract class DivinePower {
    /**
     * Performs the special attack associated with the divine power.
     *
     * @param lion   The actor performing the special attack (the Divine Beast).
     * @param target The actor being attacked.
     * @param map    The game map containing the actors and locations.
     * @return A string describing the outcome of the special attack.
     */
    public abstract String specialAttack(Actor lion, Actor target, GameMap map);

    /**
     * Switches the current divine power to the next one.
     *
     * @return The next divine power state.
     */
    public abstract DivinePower switchPower(); // Method to switch powers between states
}
