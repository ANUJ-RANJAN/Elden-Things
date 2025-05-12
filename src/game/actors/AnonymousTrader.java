package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Represents an Anonymous Trader who remains stationary in the game world.
 * The trader is friendly, cannot be attacked, and does not perform any actions.
 */
public class AnonymousTrader extends Actor {

    /**
     * Constructor for the Anonymous Trader.
     * Sets the trader's name, display character, and capabilities.
     */
    public AnonymousTrader() {
        super("Anonymous Trader", 'ඞ', Integer.MAX_VALUE); // Infinite health to prevent it from being attacked
        this.addCapability(Status.TRADER); // Marks this actor as a trader
    }

    /**
     * The Anonymous Trader does not perform any actions on its own turn.
     *
     * @param actions     the list of possible actions for this actor
     * @param lastAction  the action this actor took last turn
     * @param map         the map the actor is currently on
     * @param display     the display for outputting messages (not used in this case)
     * @return an empty action list, as the trader takes no actions
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }
}


