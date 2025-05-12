package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
/**
 * A class representing the poison status effect applied to an actor.
 *
 * <p>The {@code PoisonStatusEffect} class handles the application and management of the poison effect,
 * which damages an actor over time. It stacks multiple poison effects, each with its own duration,
 * and deals damage based on the number of poison stacks.</p>
 *
 * Created by:Mateysh Naidu
 */
public class PoisonStatusEffect extends StatusEffect {
    /**
     * A list storing the durations for each poison stack.
     */

    private final List<Integer> poisonDurations;
    /**
     * The fixed amount of damage dealt by each poison stack.
     */
    private static final int poisonDamage = 5;
    private final Display display = new Display();
    /**
     * Constructor to initialize the poison status effect.
     */
    public PoisonStatusEffect() {
        super("Poisoned");  // Setting the name of the status effect
        this.poisonDurations = new ArrayList<>();
    }
    /**
     * Applies a poison effect with a default duration of 3 ticks.
     *
     * <p>This method is called when the actor steps into a poison swamp,
     * applying a new stack of poison.</p>
     */
    public void applyPoison() {
        addPoisonDuration(3);
    }

    /**
     * Adds a poison stack with a specified duration.
     *
     * @param duration the number of turns the poison will last
     */
    public void addPoisonDuration(int duration) {
        poisonDurations.add(duration);
        display.println("Poison duration increased! New stack size: " + poisonDurations.size());
    }
    /**
     * Ticks the poison effect, applying damage to the actor and reducing poison durations.
     *
     * <p>This method is called at the start of each turn. It calculates the total poison damage
     * based on the number of poison stacks, reduces the remaining duration of each poison stack,
     * and removes the poison effect when all stacks expire.</p>
     *
     * @param location the location of the actor
     * @param actor the actor affected by the poison
     */
    @Override
    public void tick(Location location, Actor actor) {
        if (!poisonDurations.isEmpty()) {
            int totalDamage = poisonDurations.size() * 5;
            actor.hurt(totalDamage);
            display.println(actor + " takes " + totalDamage + " poison damage.");

            ListIterator<Integer> listIterator = poisonDurations.listIterator();
            while(listIterator.hasNext()) {
                int duration = listIterator.next() - 1;
                if (duration <= 0) {
                    listIterator.remove();
                } else {
                    listIterator.set(duration);
                }
            }

            if (poisonDurations.isEmpty()) {
                actor.removeStatusEffect(this);
                actor.removeCapability(Status.POISONED);
                display.println(actor + " is no longer poisoned.");
            }
        }
    }

    /**
     * This could be used to track whether the poison is still active.
     * @return true if poison is still active
     */
    public boolean isActive() {
        return poisonDurations.isEmpty();
    }
}