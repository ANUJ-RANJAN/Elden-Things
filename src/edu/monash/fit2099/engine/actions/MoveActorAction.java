package edu.monash.fit2099.engine.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * An action that moves the actor.
 */
public class MoveActorAction extends Action {

	/**
	 * Target location
	 */
	private Location moveToLocation;
	/**
	 * One of the 8-d navigation
	 */
	private String direction;
	/**
	 * Or the command key
	 */
	private String hotKey;

	/**
	 * Constructor to create an action that will move the actor to a Location in a given Direction, using
	 * a given menu hotkey.
	 *
	 * Note that this constructor does not check whether the supplied Location is actually in the given direction
	 * from the actor's current location.  This allows for (e.g.) teleporters, etc.
	 *
	 * @param moveToLocation the destination of the move
	 * @param direction the direction of the move (to be displayed in menu)
	 * @param hotKey the menu hotkey for this move
	 */
	public MoveActorAction(Location moveToLocation, String direction, String hotKey) {
		this.moveToLocation = moveToLocation;
		this.direction = direction;
		this.hotKey = hotKey;
	}

	/**
	 * Constructor to create an action that will move the actor to a Location in a given Direction.  A currently-unused
	 * menu hotkey will be assigned.
	 *
	 * Note that this constructor does not check whether the supplied Location is actually in the given direction
	 * from the actor's current location.  This allows for (e.g.) teleporters, etc.
	 *
	 * @param moveToLocation Location to move to
	 * @param direction String describing the direction to move in, e.g. "north"
	 */
	public MoveActorAction(Location moveToLocation, String direction) {
		this.moveToLocation = moveToLocation;
		this.direction = direction;
		this.hotKey = null;
	}

	/**
	 * Allow the actor to be moved.
	 *
	 * Overrides action.execute()
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of the action suitable for the menu
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		map.moveActor(actor, moveToLocation);
		return menuDescription(actor);
	}

	/**
	 * Returns a description of this movement suitable to display in the menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, e.g. "Player moves east"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " moves " + direction;
	}

	/**
	 * Returns this action's hotkey.
	 *
	 * @return the hotkey
	 */
	@Override
	public String hotkey() {
		return hotKey;
	}
}