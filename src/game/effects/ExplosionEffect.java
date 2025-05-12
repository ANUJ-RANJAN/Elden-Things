package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.Fire;
/**
 * A class representing the effects of an explosion, including dealing damage to nearby actors and setting the ground on fire.
 *
 * <p>The {@code ExplosionEffect} class handles the logic for determining the area affected by the explosion,
 * dealing damage to actors in the vicinity, and transforming certain ground tiles into fire.</p>
 *
 * Modified by:
 * @author Anuj Ranjan and Mateysh Naidu
 */
public class ExplosionEffect {
    /**
     * The source actor that triggered the explosion.
     */
    private final Actor source;
    /**
     * Constructor to initialize the explosion effect with the source actor.
     *
     * @param source the actor that triggered the explosion
     */
    public ExplosionEffect (Actor source) {
        this.source = source;
    }
    /**
     * Creates the explosion effect by damaging adjacent actors and setting the ground on fire.
     *
     * <p>This method retrieves all the adjacent locations to the source actor and checks for any actors
     * in those locations. It applies 50 damage to each actor found and changes the ground to fire
     * if it is burnable. Messages are printed to the display to indicate the results of the explosion.</p>
     *
     * @param map the current game map where the explosion occurs
     * @param display the display used to print messages to the console
     */
    public void createExplosion(GameMap map, Display display) {
        Location currentLocation = map.locationOf(source);

        for (Exit exit: currentLocation.getExits()) {
            Location adjacentLocation = exit.getDestination();
            if (adjacentLocation.containsAnActor()) {
                Actor target = adjacentLocation.getActor();
                target.hurt(50);
                display.println(target + " takes 50 collateral damage from the explosion.");
            }

            if (canBurn(adjacentLocation.getGround())) {
                adjacentLocation.setGround(new Fire(adjacentLocation.getGround(), 5, display));
                display.println("The ground at " + adjacentLocation + " is set on fire!");
            }
        }
    }
    /**
     * Checks if the ground can be set on fire.
     *
     * @param ground the ground object to check
     * @return true if the ground is burnable, false if it is a non-flammable surface like a puddle
     */
    private boolean canBurn(Ground ground) {
        return !ground.toString().equalsIgnoreCase("Puddle");
    }
}