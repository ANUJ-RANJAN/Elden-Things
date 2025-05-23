package edu.monash.fit2099.engine.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Base class for Actions. These represent things that the actor can do.
 */
public abstract class Action {

	/**
	 * Perform the action.
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened (the result of the action being performed) that can be displayed to the user.
	 */
	public abstract String execute(Actor actor, GameMap map);
	
	/**
	 * Describe what action will be performed if this action is chosen in the menu.
	 * @param actor The actor performing the action.
	 * @return the action description to be displayed on the menu
	 */
	public abstract String menuDescription(Actor actor);
	
	/**
	 * Returns the key used in the menu to trigger this action.
	 * There's no central management system for this, so you need to be careful not to use the same one twice.
	 * @see <a href="https://en.wikipedia.org/wiki/Connascence">Connascence</a>
	 * @return The key we use for this action in the menu, or null to have it assigned for you.
	 */
	public String hotkey() {
		return null;
	}
	
	/**
	 * This provides a mechanism for Actions to take more than one turn.
	 * For example, an action can change its state and return itself, or return the next action in a series.
	 * By default, this returns null, indicating that the action will complete in one turn.
	 * @return null
	 */
	public Action getNextAction() {
		return null;
	}
}
