package edu.monash.fit2099.engine.positions;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.displays.Printable;

/**
 * Class representing terrain type
 */
public abstract class Ground extends GameEntity implements Printable {

    private char displayChar;
    private String name;

    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     */
    public Ground(char displayChar, String name) {
        this.displayChar = displayChar;
        this.name = name;
    }

    @Override
    public char getDisplayChar() {
        return displayChar;
    }

    protected final void setDisplayChar(char displayChar) {
        this.displayChar = displayChar;
    }

    /**
     * Returns an empty action list.
     *
     * @param actor     the actor acting
     * @param location  the current Location
     * @param direction the direction of the ground from the actor
     * @return a new, empty collection of Actions
     */
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        return new ActionList();
    }

    /**
     * Override this to implement impassable terrain, or terrain that is only passable if conditions are met.
     *
     * @param actor the actor to check
     * @return true
     */
    public boolean canActorEnter(Actor actor) {
        return true;
    }

    /**
     * ground can also experience the joy of time.
     *
     * @param location The location of the ground
     */
    public void tick(Location location) {
    }

    /**
     * Override this to implement terrain that blocks thrown objects but not movement, or vice versa
     *
     * @return true
     */
    public boolean blocksThrownObjects() {
        return false;
    }

    /**
     * The toString method of the ground class prints out its name, e.g. puddle
     *
     * @return the name of the current ground
     */
    @Override
    public String toString() {
        return name;
    }
}
