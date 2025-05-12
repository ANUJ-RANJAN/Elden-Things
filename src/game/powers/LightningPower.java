package game.powers;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import java.util.Random;
/**
 * Class representing the Lightning power of the Divine Beast Dancing Lion.
 * <p>
 * The Lightning power allows the Divine Beast to unleash a lightning storm, dealing damage to all actors within its surroundings.
 * If the actor is standing on water, additional damage is inflicted.
 * </p>
 */
public class LightningPower extends DivinePower {


    /**
     * Performs the special attack associated with the Lightning power.
     * <p>
     * The attack deals 50 damage to all actors in the adjacent locations. If any actor is on water, an additional 50 damage is inflicted.
     * </p>
     *
     * @param lion   The actor performing the special attack (the Divine Beast).
     * @param target The actor being attacked.
     * @param map    The game map containing the actors and locations.
     * @return A string describing the outcome of the special attack.
     */
    @Override
    public String specialAttack(Actor lion, Actor target, GameMap map) {
        Location lionLocation = map.locationOf(lion);
        for (Exit exit : lionLocation.getExits()) {
            Location nearbyLocation = exit.getDestination();
            if (map.isAnActorAt(nearbyLocation)) {
                Actor nearbyActor = map.getActorAt(nearbyLocation);
                nearbyActor.hurt(50); // Deal 50 damage to all actors nearby
                if (nearbyLocation.getGround().toString().equals("Puddle")) {
                    nearbyActor.hurt(50); // Double damage on water
                }
            }
        }
        return "The storm answers with a vengeful strike. Lightning surges forth, electrifying the ground itself.\n" +
                lion + " unleashes a lightning storm!";
    }
    /**
     * Switches the current divine power to the next one.
     * <p>
     * For the Lightning power, there is a 40% chance to switch to Frost, 40% chance to switch to Wind, and 20% chance to stay in Lightning.
     * </p>
     *
     * @return The next divine power, which could be an instance of {@link FrostPower}, {@link WindPower}, or {@link LightningPower}.
     */
    @Override
    public DivinePower switchPower() {
        // 40% to Frost, 40% to Wind, 20% to stay Lightning
        int roll = new Random().nextInt(100);
        if (roll < 40) {
            return new FrostPower();

        } else if (roll < 80) {
            return new WindPower();
        }
        return new LightningPower(); // Stays in Lightning power
    }
}
