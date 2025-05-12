package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.*;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actors.Player;
import game.weaponarts.WeaponArt;

import java.util.Random;

/**
 * Abstract class representing items that can be used as a weapon.
 * A WeaponItem has attributes like damage, hit rate, strength requirement,
 * and optionally a WeaponArt which grants special abilities when the weapon is used.
 */
public abstract class WeaponItem extends Item implements Weapon {
    private static final float DEFAULT_DAMAGE_MULTIPLIER = 1.0f;
    private int damage;
    private int hitRate;
    private String verb;
    private int StrengthRequirement;
    private float damageMultiplier;
    private boolean meetMinimumRequirement;
    public WeaponArt weaponArt;

    /**
     * Constructor to create a WeaponItem.
     *
     * @param name            name of the weapon item
     * @param displayChar     character used to display the weapon item on the ground
     * @param damage          amount of damage this weapon can deal
     * @param verb            verb describing the weapon's action, e.g. "slashes", "stabs"
     * @param hitRate         probability/chance to hit the target
     * @param StrengthRequirement minimum strength required to use the weapon
     */
    public WeaponItem(String name, char displayChar, int damage, String verb, int hitRate, int StrengthRequirement) {
        super(name, displayChar, true);
        this.damage = damage;
        this.verb = verb;
        this.hitRate = hitRate;
        this.damageMultiplier = DEFAULT_DAMAGE_MULTIPLIER;
        this.StrengthRequirement = StrengthRequirement;
        this.meetMinimumRequirement = true;
    }
    /**
     * Returns the weapon's associated WeaponArt, if any.
     *
     * @return the WeaponArt object associated with this weapon
     */
    public WeaponArt getWeaponArt() {
        return this.weaponArt;
    }
    /**
     * Sets a WeaponArt for this weapon, granting it special abilities.
     *
     * @param weaponArt the WeaponArt to be assigned to this weapon
     */
    public void setWeaponArt(WeaponArt weaponArt) {
        this.weaponArt = weaponArt;
    }
    /**
     * Executes the attack action of the weapon.
     * Calculates whether the attack hits or misses based on hit rate, and deals damage if successful.
     *
     * @param attacker the actor wielding the weapon
     * @param target the actor being attacked
     * @param map the current GameMap
     * @return a string describing the result of the attack
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();
        if (!(rand.nextInt(100) < this.hitRate)) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(Math.round(damage * damageMultiplier));

        return String.format("%s %s %s for %d damage", attacker, verb, target, damage);
    }
    /**
     * Sets whether the player meets the minimum strength requirement to wield the weapon.
     *
     * @param player the player attempting to use the weapon
     */
    public void setMeetMinimumRequirement(Player player) {
        if (player.getStrength() >= StrengthRequirement) {
            this.meetMinimumRequirement = true;
        } else {
            this.meetMinimumRequirement = false;
        }
    }
    /**
     * Provides the action to pick up the weapon, if the actor meets the strength requirement.
     *
     * @param actor the actor attempting to pick up the weapon
     * @return a PickUpAction if the actor meets the strength requirement, otherwise null
     */
    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        if(portable && meetMinimumRequirement)
            return new PickUpAction(this);
        return null;
    }

}