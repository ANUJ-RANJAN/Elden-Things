package game.weaponarts;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.behaviours.WanderBehaviour;
/**
 * Class representing the "QuickStep" weapon art.
 * QuickStep allows the actor to move in a random direction during the same turn as an attack.
 * Unlike other weapon arts, it does not consume mana.
 */
public class QuickStep implements WeaponArt {

    private WanderBehaviour wanderBehaviour = new WanderBehaviour();

    /**
     * Activates the QuickStep weapon art.
     * This causes the actor to move to a random adjacent location using the WanderBehaviour.
     *
     * @param actor the actor using the QuickStep weapon art
     * @param map the current game map where the actor is located
     */
    @Override
    public void activate(Actor actor, GameMap map) {
        Location currentLocation = map.locationOf(actor);
        Action quickStepAction = wanderBehaviour.getAction(actor, map);

        if (quickStepAction != null) {
            quickStepAction.execute(actor, map);
        }
    }

}