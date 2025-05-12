package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;
import game.actors.Status;
import game.effects.Explosion;
import game.effects.ExplosionEffect;
import game.effects.HealingEffect;
import edu.monash.fit2099.engine.actions.ActionList;

import java.util.Random;

/**
 * Represents the Furnace Engine weapon, granting the player the explosive abilities of the Furnace Golem.
 * This weapon allows for explosive attacks, a stomp attack, and applies a healing effect over time.
 */
public class FurnaceEngine extends WeaponItem {
    private static final int EXPLOSION_DAMAGE = 200;
    private static final int EXPLOSION_HIT_CHANCE = 50; // 50% chance to trigger explosion in normal attacks
    private static final int STOMP_DAMAGE = 100;
    private static final int STOMP_HIT_CHANCE = 5; // 5% chance to hit
    private static final int STOMP_EXPLOSION_CHANCE = 10; // 10% chance to create an explosion on stomp
    private static final int FIRE_DURATION = 5;
    private final Display display;
    private final Explosion explosion; // Instance of Explosion to handle explosion logic

    /**
     * Constructor to initialize the Furnace Engine with its explosive attack capabilities.
     *
     * @param display The display used for outputting messages.
     */
    public FurnaceEngine(Actor actor, Display display) {
        super("Furnace Engine", 'E', 60, "smashes", 70, 20); // Strength requirement set to 20
        this.display = display;
        this.explosion = new Explosion(50, new ExplosionEffect(actor)); // 50% chance explosion
    }

    /**
     * Executes the Furnace Engine's stomp or regular attack.
     *
     * @param wielder The actor wielding the Furnace Engine.
     * @param target  The target actor.
     * @param map     The game map where the action takes place.
     * @param isStomp Whether this is a stomp attack or a regular explosion attack.
     * @return A string describing the result of the attack.
     */
    public String performAttack(Actor wielder, Actor target, GameMap map, boolean isStomp) {
        int hitChance = isStomp ? STOMP_HIT_CHANCE : EXPLOSION_HIT_CHANCE;
        int damage = isStomp ? STOMP_DAMAGE : EXPLOSION_DAMAGE;

        // Check if the direct attack (stomp or explosion) hits
        if (new Random().nextInt(100) < hitChance) {
            target.hurt(damage);
            display.println(wielder + (isStomp ? " stomps on " : " triggers an explosion on ") + target + ", causing " + damage + " damage!");
        } else if (isStomp) {
            display.println(wielder + " attempts to stomp " + target + " but misses.");
        }

        // If it's a stomp, there's an additional chance to trigger an explosion
        if (isStomp && new Random().nextInt(100) < STOMP_EXPLOSION_CHANCE) {
            explosion.checkForExplosion(map, display); // Triggers explosion effect in the vicinity
        }

        return isStomp ? "Stomp attack executed!" : "Explosion attack executed!";
    }

    /**
     * Overrides the attack method from WeaponItem to add explosive attack capabilities.
     *
     * @param attacker The actor wielding the weapon.
     * @param target   The target of the attack.
     * @param map      The game map where the action takes place.
     * @return A description of the attack.
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        return performAttack(attacker, target, map, false); // Regular explosion attack
    }

    /**
     * Applies the Furnace Engine's effects, including healing over time, stomp attack ability, and furnace golem capabilities.
     * Adds custom actions to the player's action list, making them available in the menu.
     *
     * @param player The player wielding the Furnace Engine.
     */
    public void applyEffects(Player player) {
        player.addCapability(Status.FURNACE_GOLEM); // Grants player furnace golem capabilities
        player.addStatusEffect(new HealingEffect()); // Adds a healing effect over time
    }

    /**
     * Allows the player to execute a stomp attack.
     *
     * @param player The player wielding the Furnace Engine.
     * @param target The target actor to stomp.
     * @param map    The game map where the action takes place.
     * @return A description of the stomp attack.
     */
    public String stompAttack(Player player, Actor target, GameMap map) {
        return performAttack(player, target, map, true); // Stomp attack with explosion effect
    }
}

