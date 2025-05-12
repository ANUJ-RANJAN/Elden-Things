package edu.monash.fit2099.engine.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 *
 */
public interface Behaviour {
	
	/**
	 * A factory for creating actions. Chaining these together can result in an actor performing more complex tasks.
	 *
	 * A behaviour represents a kind of objective that an actor can have.  For example
	 * it might want to seek out a particular kind of object, or follow another actor,
	 * or run away and hide.  Each implementation of behaviour returns an action that the
	 * actor could take to achieve its objective, or null if no useful options are available.
	 * method that determines which behaviour to perform.  This allows the behaviour's logic
	 * to be reused in other Actors via delegation instead of inheritance.
	 *
	 * An actor's {@code playTurn()} method can use Behaviours to help decide which action to
     * perform next.  It can also simply create Actions itself, and for simpler Actors this is
	 * likely to be sufficient.  However, using Behaviours allows
	 * us to modularize the code that decides what to do, and that means that it can be 
	 * reused if (e.g.) more than one kind of actor needs to be able to seek, follow, or hide.
	 *
	 * @author Riordan D. Alfredo
	 * @param actor the actor acting
	 * @param map the GameMap containing the actor
	 * @return an action that actor can perform, or null if actor can't do this.
	 */
	Action getAction(Actor actor, GameMap map);
}
