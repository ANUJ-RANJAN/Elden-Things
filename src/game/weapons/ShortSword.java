package game.weapons;

import game.weaponarts.WeaponArt;
/**
 * Class representing a Short Sword weapon.
 * A Short Sword has a high damage output and utilizes WeaponArt for special abilities.
 */
public class ShortSword extends WeaponItem {
    /**
     * Constructor to create a ShortSword with a specific WeaponArt.
     *
     * @param weaponArt the WeaponArt to be associated with this Short Sword
     */
    public ShortSword(WeaponArt weaponArt) {
        super ("Short Sword", '!', 100, "slashes",75, 10);
        setWeaponArt(weaponArt);
    }
}
