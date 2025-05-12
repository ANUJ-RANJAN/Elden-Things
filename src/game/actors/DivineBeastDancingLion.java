package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.grounds.Gate;
import game.items.RemembranceOfDancingLion;
import game.items.RemembranceOfFurnaceGolem;
import game.powers.DivinePower;
import game.powers.WindPower;

import java.util.Random;

/**
 * Class representing the Divine Beast Dancing Lion, a powerful boss enemy with various divine powers.
 */
public class DivineBeastDancingLion extends Enemy {
    private DivinePower currentDivinePower;
    private final Random random = new Random();
    private final Display display;
    private Actor targetActor = null;
    private final GameMap map;
    private final GameMap targetMap;
    public DivineBeastDancingLion(Display display, GameMap map, GameMap targetMap) {
        super("Divine Beast Dancing Lion", 'S', 10000);
        this.addCapability(Status.DIVINE_BEAST);
        this.map = map;
        this.display = display;
        this.targetMap = targetMap;
        // Divine Beast starts with the "Wind" power
        this.currentDivinePower = new WindPower();
    }
    @Override
    public void hurt(int points) {
        super.hurt(points);

        // Check if Divine Beast is defeated
        if (!this.isConscious()) {
            addRemembranceItem();
            addGateToLocation();
        }
    }

    /**
     * Adds a gate at the current location of the Divine Beast when it dies.
     */
    private void addGateToLocation() {
        Location location = map.locationOf(this);

        // Create a new gate and add a TeleportAction to it
        Gate gate = new Gate('H', "Gate to Belurat");
        Location targetLocation = targetMap.at(7, 2);  // Specify destination in the target map
        gate.addSampleAction(new TeleportAction(targetLocation, "to Belurat, Tower Settlement"));

        location.setGround(gate);  // Place the gate at the Divine Beast's location
        display.println("A gate opens where the Divine Beast fell, leading back to Belurat!");
    }

    private void addRemembranceItem() {
        Location targetLocation = map.at(6,2);
        RemembranceOfDancingLion remembranceItem = new RemembranceOfDancingLion();
        targetLocation.addItem(remembranceItem);  // Place the remembrance item at the current location
        display.println("The Furnace Golem dropped a Remembrance of the Dancing Lion!");
    }
    /**
     * Defines the behavior of the Divine Beast for each turn.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        Location currentLocation = map.locationOf(this);

        // Check if targetActor is valid; if not, search for new target
        if (targetActor == null || !map.contains(targetActor) || !targetActor.isConscious()) {
            targetActor = findNewTarget(currentLocation);
        }

        // Check if targetActor is within adjacent locations
        if (targetActor != null && isAdjacent(currentLocation, map.locationOf(targetActor))) {
            applyDivinePowerEffect(targetActor, map);
            behaviours.put(1, new FollowBehaviour(targetActor));
            return new AttackBehaviour(targetActor, 150, 30).getAction(this, map);
        }

        // If a target is set and still exists in the game map, continue following it
        if (targetActor != null && map.contains(targetActor)) {
            return new FollowBehaviour(targetActor).getAction(this, map);
        } else {
            // If the target is null or no longer on the map, revert to wandering
            targetActor = null;
            behaviours.put(999, new WanderBehaviour());
        }
        // Default behavior when no target in range
        return super.playTurn(actions, lastAction, map, display);
    }

    /**
     * Searches for a nearby target to attack.
     */
    private Actor findNewTarget(Location currentLocation) {
        for (Exit exit : currentLocation.getExits()) {
            Location adjacentLocation = exit.getDestination();
            if (adjacentLocation.containsAnActor()) {
                Actor potentialTarget = adjacentLocation.getActor();
                if (potentialTarget.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                    return potentialTarget;
                }
            }
        }
        return null;
    }

    /**
     * Checks if the target is adjacent.
     */
    private boolean isAdjacent(Location currentLocation, Location targetLocation) {
        for (Exit exit : currentLocation.getExits()) {
            if (exit.getDestination().equals(targetLocation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Applies the current divine power effect and handles potential power transitions.
     *
     * @param target The actor targeted by the special attack.
     * @param map    The game map.
     */
    private void applyDivinePowerEffect(Actor target, GameMap map) {
        // Execute special effect of the current divine power
        String specialEffectResult = currentDivinePower.specialAttack(this, target, map);
        display.println(specialEffectResult);

        // 25% chance to switch divine power after the special attack
        if (random.nextInt(100) < 25) {
            DivinePower newPower = currentDivinePower.switchPower();
            if (newPower != currentDivinePower) { // Only announce change if there’s a new power
                currentDivinePower = newPower;
                display.println("The Divine Beast changes its divine power to " + currentDivinePower.getClass().getSimpleName() + "!");
            }
        }
    }
}