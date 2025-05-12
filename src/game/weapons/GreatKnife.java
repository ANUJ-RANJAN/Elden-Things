package game.weapons;

import game.weaponarts.WeaponArt;
/**
 * Class representing a Great Knife weapon.
 * The Great Knife has a moderate damage output and can be equipped with WeaponArt for special abilities.
 */
public class GreatKnife extends WeaponItem {
    /**
     * Constructor to create a GreatKnife with a specific WeaponArt.
     *
     * @param weaponArt the WeaponArt to be associated with this Great Knife
     */

    public GreatKnife(WeaponArt weaponArt) {
        super("Great Knife", '†', 75, "stabs", 60, 5);
        setWeaponArt(weaponArt);
    }
}