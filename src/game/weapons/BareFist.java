package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/**
 * Class representing a bare fist
 * @author Adrian Kristanto
 */
public class BareFist extends IntrinsicWeapon {
    /**
     * Constructor that creates a BareFist weapon.
     *
     * <p>Initializes the weapon with 25 damage, the attack verb "punches", and a hit rate of 50%.</p>
     */
    public BareFist() {
        super(25, "punches", 50);
    }
}
