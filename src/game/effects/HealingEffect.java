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
 * A class representing a healing effect that restores health over a set number of ticks.
 * This effect heals the actor gradually, simulating a healing-over-time effect.
 */
public class HealingEffect extends StatusEffect {
    private final List<Integer> healingDurations;
    private static final int HEAL_AMOUNT = 150; // Heal amount per tick
    private static final Display display = new Display();

    /**
     * Constructor to initialize the healing effect with a default duration of 5 ticks.
     */
    public HealingEffect() {
        super("Healing"); // Setting the name of the status effect
        this.healingDurations = new ArrayList<>();
        addHealingDuration(5); // Default duration of 5 ticks
    }

    /**
     * Adds a healing stack with a specified duration.
     *
     * @param duration the number of turns the healing will last
     */
    public void addHealingDuration(int duration) {
        healingDurations.add(duration);
        display.println("Healing duration increased! New stack size: " + healingDurations.size());
    }

    /**
     * Ticks the healing effect, restoring health to the actor and reducing healing durations.
     *
     * <p>This method is called at the start of each turn. It calculates the total healing amount
     * based on the number of healing stacks, reduces the remaining duration of each healing stack,
     * and removes the healing effect when all stacks expire.</p>
     *
     * @param location the location of the actor
     * @param actor the actor affected by the healing effect
     */
    @Override
    public void tick(Location location, Actor actor) {
        if (!healingDurations.isEmpty()) {
            int totalHealing = healingDurations.size() * HEAL_AMOUNT;
            actor.heal(totalHealing);
            display.println(actor + " heals for " + totalHealing + " health points.");

            // Decrease duration for each healing stack
            ListIterator<Integer> listIterator = healingDurations.listIterator();
            while (listIterator.hasNext()) {
                int duration = listIterator.next() - 1;
                if (duration <= 0) {
                    listIterator.remove(); // Remove expired healing stack
                } else {
                    listIterator.set(duration); // Update duration
                }
            }

            // If all healing durations are exhausted, remove the healing effect
            if (healingDurations.isEmpty()) {
                actor.removeStatusEffect(this);
                display.println(actor + " has fully recovered from the healing effect.");
            }
        }
    }

    /**
     * Checks if the healing effect is still active.
     *
     * @return true if healing is still active, false otherwise.
     */
    public boolean isActive() {
        return !healingDurations.isEmpty();
    }
}