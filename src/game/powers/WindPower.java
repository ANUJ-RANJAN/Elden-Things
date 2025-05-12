package game.powers;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing the Wind power of the Divine Beast Dancing Lion.
 * <p>
 * The Wind power allows the Divine Beast to blow the target away to a random adjacent location, if one is available.
 * </p>
 */
public class WindPower extends DivinePower {
    private final Random random = new Random();

    /**
     * Performs the special attack associated with the Wind power.
     * <p>
     * The target is blown away to a random empty adjacent location if available.
     * </p>
     *
     * @param attacker The actor performing the special attack (the Divine Beast).
     * @param target   The actor being attacked.
     * @param map      The game map containing the actors and locations.
     * @return A string describing the outcome of the special attack.
     */
    @Override
    public String specialAttack(Actor attacker, Actor target, GameMap map) {
        Location currentLocation = map.locationOf(attacker);
        List<Location> adjacentLocations = new ArrayList<>();

        // Collect empty adjacent locations only
        for (Exit exit : currentLocation.getExits()) {
            Location destination = exit.getDestination();
            if (!destination.containsAnActor() && destination.canActorEnter(target)) { // Ensure no actor is in this location
                adjacentLocations.add(destination);
            }
        }

        if (!adjacentLocations.isEmpty()) {
            Location newLocation = adjacentLocations.get(random.nextInt(adjacentLocations.size()));
            map.moveActor(target, newLocation);
            return "Remember thou art mortal! The winds howl and rage, lifting you like a leaf in the storm.\n" +
                    target + " is blown away by the wind to a new location!";
        }

        return target + " resists the wind!";
    }
    /**
     * Switches the current divine power to the next one.
     * <p>
     * There is a 30% chance to switch to FrostPower, and a 70% chance to switch to LightningPower.
     * </p>
     *
     * @return The next divine power, which could be an instance of {@link FrostPower} or {@link LightningPower}.
     */
    @Override
    public DivinePower switchPower() {
        // 30% chance to switch to FrostPower, 70% chance to switch to LightningPower
        if (random.nextInt(100) < 30) {
            return new FrostPower();
        } else {
            return new LightningPower();
        }
    }
}
