package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.weaponarts.WeaponArt;
import game.weapons.WeaponItem;

/**
 * Class representing an action to attack
 * Note that the attacker must have a weapon, e.g.,
 * an intrinsic weapon or a weapon item.
 * Otherwise, the execute method will throw an error.
 * @author Adrian Kristanto
 */
public class AttackAction extends Action {

    /**
     * The actor that is to be attacked
     */
    private Actor target;

    /**
     * The direction of incoming attack.
     */
    private String direction;

    /**
     * weapon used for the attack
     */
    private WeaponItem weapon;

    /**
     * Constructor.
     *
     * @param target the actor to attack
     * @param direction the direction where the attack should be performed (only used for display purposes)
     */
    public AttackAction(Actor target, String direction, WeaponItem weapon) {
        this.target = target;
        this.direction = direction;
        this.weapon = weapon;
    }

    /**
     * Constructor with intrinsic weapon as default
     *
     * @param target the actor to attack
     * @param direction the direction where the attack should be performed (only used for display purposes)
     */
    public AttackAction(Actor target, String direction) {
        this.target = target;
        this.direction = direction;
    }
    /**
     * Executes the attack on the target.
     *
     * <p>If the actor has a weapon art, it will be activated before the attack.
     * The attack result depends on whether the attacker has a weapon or is using the intrinsic weapon (bare fists).</p>
     *
     * @param actor the actor performing the attack
     * @param map the game map where the action takes place
     * @return a string describing the result of the attack
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";
        if (weapon != null) {
            WeaponArt weaponArt = weapon.getWeaponArt();
            if (weaponArt != null) {
                weaponArt.activate(actor, map);
            }
            result = weapon.attack(actor, target, map);
        } else {
            result = actor.getIntrinsicWeapon().attack(actor, target, map);
        }

        if (!target.isConscious()) {
            result += "\n" + target.unconscious(actor, map);
        }

        return result;
    }
    /**
     * Provides a description of the attack for display in the menu.
     *
     * @param actor the actor performing the attack
     * @return a string describing the attack action for the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        if (weapon != null) {
            return actor + " attacks " + target + " with " + weapon;
        } else {
            return actor + " attacks " + target + " with bared fists";
        }

    }
}
