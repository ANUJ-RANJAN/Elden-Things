package game.effects;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
/**
 * A class representing an explosion event that may occur during certain conditions, such as a Furnace Golem's attack.
 *
 * <p>The {@code Explosion} class determines whether an explosion will occur based on a given probability,
 * and triggers an explosion effect if the conditions are met.</p>
 *
 * <p>Explosions can result in significant damage or changes to the game world, making this class useful
 * for creating impactful events in the game.</p>
 *
 * Created and modified by:
 * @author Mateysh Naidu and Anuj Ranjan
 */
public class Explosion {
    /**
     * The probability of the explosion occurring, as a percentage.
     */
    private final int chance;
    /**
     * The effect that will be triggered if the explosion occurs.
     */
    private final ExplosionEffect explosionEffect;
    /**
     * Constructor to initialize the explosion with a chance and explosion effect.
     *
     * @param chance the probability of the explosion occurring, represented as a percentage (0-100)
     * @param explosionEffect the specific explosion effect to be triggered if the explosion occurs
     */
    public Explosion (int chance, ExplosionEffect explosionEffect){
        this.chance = chance;
        this.explosionEffect = explosionEffect;
    }
    /**
     * Checks whether an explosion occurs based on the assigned probability.
     *
     * <p>If the explosion occurs (based on a random chance), the explosion effect is executed,
     * and a message is printed to the display to indicate the shockwave caused by the explosion.</p>
     *
     * @param map the current game map where the explosion could take place
     * @param display the display used to print messages to the console
     */
    public void checkForExplosion(GameMap map, Display display) {
        if (Math.random()*100 < chance) {
            display.println("Furnace Golem's stomp attack results in a shockwave in the surrounding areas.");
            explosionEffect.createExplosion(map, display);
        }
    }
}
