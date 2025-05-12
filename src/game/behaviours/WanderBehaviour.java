package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class representing a wandering behaviour for actors.
 *
 * <p>The {@code WanderBehaviour} class makes an actor move to a random adjacent location,
 * provided it can enter that location. If no valid movement is possible,
 * the behaviour will return {@code null}, indicating that the actor should remain still.</p>
 *
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by: Anuj Ranjan & Mateysh Naidu
 *
 */
public class WanderBehaviour implements Behaviour {
    /**
     * Random generator for selecting a random direction to move.
     */
    private final Random random = new Random();

    /**
     * Returns a MoveAction to wander to a random location, if possible.
     * If no movement is possible, returns null.
     *
     * @param actor the actor enacting the behaviour
     * @param map the map that actor is currently on
     * @return an action, or null if no MoveAction is possible
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        ArrayList<Action> actions = new ArrayList<>();

        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
            }
        }

        if (!actions.isEmpty()) {
            return actions.get(random.nextInt(actions.size()));
        }
        else {
            return null;
        }
    }
}

