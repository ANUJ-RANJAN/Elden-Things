package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.Random;

/**
 * Class representing an attack behaviour in the game.
 *
 * <p>This behaviour makes the actor attack a specified target. The attack has a 5% chance of dealing 100 damage
 * and removing the target from the map if they are no longer conscious after the attack. Otherwise, the actor
 * misses the attack.</p>
 *
 * @author Adrian Kristanto
 */
public class AttackBehaviour extends Action implements Behaviour {
    /**
     * The actor that is the target of the attack.
     */
    private Actor target;
    /**
     * The direction of the incoming attack.
     */
    private String direction;
    /**
     * Random number generator for determining hit success.
     */
    private final Random random = new Random();
    /**
     * Constructor for the AttackBehaviour class.
     *
     * @param subject the actor that is the target of the attack
     */
    private int damage;
    private int hitChance;
    public AttackBehaviour(Actor subject, int damage, int hitChance) {
        this.target = subject;
        this.direction = direction;
        this.damage=damage;
        this.hitChance=hitChance;
    }

    /**
     * Executes the attack behaviour.
     *
     * <p>The attack has a 5% chance of success, dealing 100 damage to the target. If the target's health
     * drops to 0 or below, they are removed from the map. Otherwise, the attack misses.</p>
     *
     * @param actor the actor performing the attack
     * @param map the game map where the attack takes place
     * @return a string describing the result of the attack
     */
    @Override
    public String execute (Actor actor, GameMap map){
        if (random.nextInt(100) < hitChance) {
            target.hurt(damage);
            if (!target.isConscious()) {
                map.removeActor(target);
                return actor + " attacks " + target + " to death!";
            }
            return actor + " attacks " + target + " for "+ damage + "damage!";
        }
        return actor + " missed the attack on " + target +"!";
    }
    /**
     * Provides a description of the attack behaviour for display in the menu.
     *
     * @param actor the actor performing the action
     * @return a string describing the attack behaviour
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " stomps " + target + " at " + direction;
    }
    /**
     * Retrieves the action that represents this behaviour.
     *
     * @param actor the actor performing the action
     * @param map the game map where the action takes place
     * @return this action
     */
    public Action getAction(Actor actor, GameMap map) {
        return this;
    }
}