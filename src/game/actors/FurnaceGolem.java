package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.behaviours.AttackBehaviour;
import game.behaviours.WanderBehaviour;
import game.behaviours.FollowBehaviour;
import game.effects.Explosion;
import game.effects.ExplosionEffect;
import game.items.RemembranceOfDancingLion;
import game.items.RemembranceOfFurnaceGolem;

/**

 Class representing the Furnace Golem
 For now, it can only wander around the map.
 @author Adrian Kristanto*/
public class FurnaceGolem extends Enemy {
    /**
     * The explosion instance associated with the Furnace Golem, responsible for triggering explosions.
     */
    private Explosion explosion;
    private Actor target = null;
    private final Display display;
    private final GameMap map;
    /**
     * Constructor for the Furnace Golem.
     * Initializes the Golem with high health and assigns it a wandering behaviour.
     * Also sets up its explosion capability.
     */
    public FurnaceGolem(Display display, GameMap map) {
        super("Furnace Golem", 'A', 1000);
        this.behaviours.put(999, new WanderBehaviour());
        this.addCapability(Status.FURNACE_GOLEM);

        ExplosionEffect explosionEffect = new ExplosionEffect(this);
        this.explosion = new Explosion(10, explosionEffect);
        this.display = display;
        this.map = map;
    }
    /**
     * Defines the behaviour of the Furnace Golem on each turn.
     * The Golem checks if any actors are nearby to attack. If so, it triggers an explosion
     * and attacks the adjacent hostile actors. If no targets are nearby, it will wander.
     *
     * @param actions    The list of possible actions for this actor.
     * @param lastAction The action this actor took last turn.
     * @param map        The map the actor is currently on.
     * @param display    The display used for outputting messages to the console.
     * @return The action that the Golem will take on this turn.
     */

    @Override
    public void hurt(int points) {
        super.hurt(points);

        // Check if Furnace Golem is defeated
        if (!this.isConscious()) {
            addRemembranceItem();
        }
    }

    private void addRemembranceItem() {
        Location location = map.locationOf(this);

        RemembranceOfFurnaceGolem remembranceItem = new RemembranceOfFurnaceGolem();
        location.addItem(remembranceItem);  // Place the remembrance item at the current location
        display.println("The Furnace Golem dropped a Remembrance of the Furnace Golem!");
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        Location currentLocation = map.locationOf(this);

        for (Exit exit: currentLocation.getExits()) {
            Location adjacentLocation = exit.getDestination();
            if (adjacentLocation.containsAnActor()) {
                Actor adjacentActor = adjacentLocation.getActor();

                if (adjacentActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                    target = adjacentActor;
                    AttackBehaviour attackBehaviour = new AttackBehaviour(adjacentActor,100, 5);
                    explosion.checkForExplosion(map, display);
                    behaviours.put(1, new FollowBehaviour(target));
                    return attackBehaviour.getAction(this, map);
                }
            }
        }

        // If a target is set and still exists in the game map, continue following it
        if (target != null && map.contains(target)) {
            return new FollowBehaviour(target).getAction(this, map);
        } else {
            // If the target is null or no longer on the map, revert to wandering
            target = null;
            behaviours.put(999, new WanderBehaviour());
        }

        return super.playTurn(actions, lastAction, map, display);
    }
}