
package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

public class TeleportAction extends Action {

    private Location destination;
    private String destinationName;

    public TeleportAction(Location destination, String destinationName) {
        this.destination = destination;
        this.destinationName = destinationName;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if(! destination.containsAnActor()) {
            map.moveActor(actor, destination);
            return actor + " teleports to " + destinationName;
        } else {
            return "Teleportation failed: Destination is occupied by another actor.";
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " teleports to " + destinationName;
    }
}
