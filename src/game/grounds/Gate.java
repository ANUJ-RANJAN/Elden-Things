package game.grounds;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.List;
/**
 * A class representing a Gate in the game world.
 * The Gate can hold multiple actions, such as transitioning to different areas or interacting with the environment.
 */
public class Gate extends Ground {
    /**
     * A list of actions that can be performed on or through the gate.
     */
    private List<Action> gateActions = new ArrayList<>();
    /**
     * Constructor for the Gate.
     * Initializes the Gate with a display character and a name.
     *
     * @param displayChar the character that represents the gate on the map
     * @param name the name of the gate
     */
    public Gate(char displayChar, String name) {
        super(displayChar, name);
    }
    /**
     * Adds a new action to the gate.
     * This action could represent an interaction, such as moving to another map.
     *
     * @param newAction the action to add to the gate
     */
    public void addSampleAction(Action newAction) {
        this.gateActions.add(newAction);
    }
    /**
     * Overrides the allowableActions method to return the list of actions that can be performed on the gate.
     * The actions include those added specifically to the gate via {@link #addSampleAction(Action)}.
     *
     * @param actor the actor performing the action
     * @param location the location of the gate in the game world
     * @param direction the direction of the gate relative to the actor
     * @return the list of allowable actions at the gate
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);
        for (Action action: gateActions) {
            actions.add(action);
        }
        return actions;
    }
}
