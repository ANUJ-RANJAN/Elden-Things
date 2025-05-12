package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
/**
 * Class representing a behaviour that makes an actor follow a target actor.
 *
 * <p>This behaviour calculates the shortest path to move an actor closer to its target
 * by checking all available exits and selecting the one that brings the actor closest
 * to the target.</p>
 *
 * @author Adrian Kristanto
 */
public class FollowBehaviour implements Behaviour {

    /**
     * The actor that is being followed.
     */
    private final Actor target;
    /**
     * Constructor for the FollowBehaviour class.
     *
     * @param subject the actor that is the target to follow
     */
    public FollowBehaviour(Actor subject) {
        this.target = subject;
    }
    /**
     * Retrieves the action for the actor to move closer to the target actor.
     *
     * <p>If the target or the actor is not present in the game map, the behaviour does nothing.
     * Otherwise, it selects the exit that leads the actor closer to the target and returns a
     * {@code MoveActorAction} to move the actor in that direction.</p>
     *
     * @param actor the actor performing the follow behaviour
     * @param map the game map where the behaviour takes place
     * @return an action to move the actor towards the target, or null if no movement is possible
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if(!map.contains(target) || !map.contains(actor))
            return null;

        Location here = map.locationOf(actor);
        Location there = map.locationOf(target);

        int currentDistance = distance(here, there);
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                int newDistance = distance(destination, there);
                if (newDistance < currentDistance) {
                    return new MoveActorAction(destination, exit.getName());
                }
            }
        }

        return null;
    }
    /**
     * Calculates the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the second location
     * @return the Manhattan distance between the two locations
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
    }
