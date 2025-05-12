package game.powers;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Status;


import java.util.ArrayList;
import java.util.List;
/**
 * Class representing the Frost power of the Divine Beast Dancing Lion.
 * <p>
 * The Frost power allows the Divine Beast to cause a target actor to slip if they are standing on a puddle.
 * The target actor will drop all items in their inventory.
 * </p>
 */
public class FrostPower extends DivinePower {
    /**
     * Performs the special attack associated with the Frost power.
     * <p>
     * If the target is standing on a puddle, they will slip and drop all items in their inventory.
     * </p>
     *
     * @param lion   The actor performing the special attack (the Divine Beast).
     * @param target The actor being attacked.
     * @param map    The game map containing the actors and locations.
     * @return A string describing the outcome of the special attack.
     */
    @Override
    public String specialAttack(Actor lion, Actor target, GameMap map) {
        Location targetLocation = map.locationOf(target);

        // Check if the target is on a puddle and has not already slipped this turn
        if (targetLocation.getGround().toString().equals("Puddle") && !target.hasCapability(Status.SLIPPED)) {
            target.addCapability(Status.SLIPPED);  // Mark as slipped for this turn

            // Drop each item from the target's inventory
            List<Item> itemsToDrop = new ArrayList<>(target.getItemInventory());
            for (Item item : itemsToDrop) {
                target.removeItemFromInventory(item);
                targetLocation.addItem(item);
            }

            return "The air turns bitter, and the land shudders beneath an ancient cold. Frost grips all in its chilling embrace.\n" +
                    "The cold seizes the water beneath, and the ground betrays your footing.\n " +
                    target + " slips on the icy ground and drops their inventory!";
        }
        return "The frost has no effect as " + target + " is not on water.";
    }
    /**
     * Switches the current divine power to the next one.
     * <p>
     * For the Frost power, it always switches back to the Wind power.
     * </p>
     *
     * @return The next divine power, which is an instance of {@link WindPower}.
     */
    @Override
    public DivinePower switchPower() {
        // Always switch back to WindPower
        return new WindPower();
    }
}
