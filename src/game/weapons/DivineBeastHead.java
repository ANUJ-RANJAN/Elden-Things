package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;
import game.actors.Status;
import game.behaviours.AttackBehaviour;
import game.powers.DivinePower;
import game.powers.WindPower;
import game.weapons.WeaponItem;

import java.util.Random;

/**
 * Represents the Divine Beast Head weapon, granting the player the Divine Beast's abilities and powers.
 */
public class DivineBeastHead extends WeaponItem {
    private DivinePower currentDivinePower;
    private final Random random = new Random();

    private static final int BITE_DAMAGE = 150;
    private static final int BITE_HIT_CHANCE = 30; // 30% chance to hit

    /**
     * Constructor to initialize the Divine Beast Head with its powers and bite attack.
     */
    public DivineBeastHead() {
        super("Divine Beast Head", '$', 50, "strikes", 80, 15); // Strength requirement set to 15
        this.currentDivinePower = new WindPower(); // Start with Wind power
    }

    /**
     * Activates the current divine power, replicating the Divine Beast's abilities.
     *
     * @param wielder The actor wielding the Divine Beast Head.
     * @param target  The target actor upon whom the power will be used.
     * @param map     The game map containing actors and locations.
     * @return A string describing the result of the power's activation.
     */
    public String activateDivinePower(Actor wielder, Actor target, GameMap map) {
        String result = currentDivinePower.specialAttack(wielder, target, map);

        System.out.println(result);
        if (random.nextInt(100) < 25) {
            transitionPower();
        }

        return result;
    }

    /**
     * Provides the special bite attack as an AttackBehaviour.
     *
     * @param target The actor being attacked with the bite.
     * @return An AttackBehaviour for the bite attack.
     */
    public AttackBehaviour getBiteAttackBehaviour(Actor target) {
        return new AttackBehaviour(target, BITE_DAMAGE, BITE_HIT_CHANCE);
    }

    /**
     * Transitions to a different divine power with specified probabilities.
     */
    private void transitionPower() {
        DivinePower newPower = currentDivinePower.switchPower();
        if (newPower != currentDivinePower) { // Only announce change if there’s a new power
            currentDivinePower = newPower;
            System.out.println("The Divine Beast Head shifts to " + currentDivinePower.getClass().getSimpleName() + " power!");
        }
    }

    /**
     * Applies the effects of the Divine Beast Head to the player, increasing health and mana.
     *
     * @param player The player wielding the Divine Beast Head.
     */
    public void applyEffects(Player player) {
        player.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 50);
        player.modifyAttributeMaximum(BaseActorAttributes.MANA, ActorAttributeOperations.INCREASE, 100);
        player.addCapability(Status.DIVINE_BEAST); // Grants player divine power capability
    }

    /**
     * Overrides the attack method from WeaponItem to add special attack capabilities.
     *
     * @param attacker The actor wielding the weapon.
     * @param target   The target of the attack.
     * @param map      The game map where the action takes place.
     * @return A description of the attack.
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        // Uses normal attack if not using special powers
        if (random.nextInt(100) < BITE_HIT_CHANCE) {
            return getBiteAttackBehaviour(target).execute(attacker, map);
        } else {
            return activateDivinePower(attacker, target, map);
        }
    }

    /**
     * Returns a description of the weapon and its current power.
     *
     * @return A string describing the Divine Beast Head.
     */
    @Override
    public String toString() {
        return "Divine Beast Head (Current Power: " + currentDivinePower.getClass().getSimpleName() + ")";
    }
}