package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Status;
/**
 * A class that represents a fire on the ground which can burn actors and eventually extinguishes, reverting the ground to its original state.
 */
public class Fire extends Ground{
    private Ground OriginalGround;
    private int BurnDuration;
    private Display display;
    /**
     * Constructor for Fire.
     * Initializes the fire on top of the original ground, with a burn duration and display for output messages.
     *
     * @param OriginalGround The ground that the fire is burning on.
     * @param BurnDuration The number of ticks the fire will last.
     * @param display The display object to output messages about the fire.
     */
    public Fire(Ground OriginalGround, int BurnDuration, Display display) {
        super('w', "Fire");
        this.OriginalGround = OriginalGround;
        this.BurnDuration = BurnDuration;
        this.display = display;
    }
    /**
     * Method that defines the behavior of fire over time.
     * Each tick, the fire may hurt any actor standing on it, and the burn duration decreases.
     * When the burn duration reaches zero, the fire burns out, and the ground reverts to its original state.
     *
     * @param location The location of the fire.
     */
    public void tick(Location location) {
        super.tick(location);

        if (location.containsAnActor()) {
            Actor actor = location.getActor();
            if (!(actor.hasCapability(Status.FURNACE_GOLEM))) {
                actor.hurt(5);
                display.println(actor + "takes 5 fire damage from burning ground.");
            }
        }

        BurnDuration --;

        if (BurnDuration <= 0) {
            location.setGround(OriginalGround);
            display.println("The fire at " + location + " has burned out and the ground returns to " + OriginalGround + ".");
        }
    }

}
