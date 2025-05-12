package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.actions.TradeAction;
import game.items.RemembranceOfDancingLion;
import game.items.RemembranceOfFurnaceGolem;
import game.weapons.*;

/**
 * Class representing the Player, the main character controlled by the user.
 * Players have attributes like strength, mana, and hit points, and can wield weapons or engage in combat with bare fists.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:Anuj Ranjan, Mateysh Naidu
 *
 */
public class Player extends Actor{
    private int Strength;
    private int Mana;
    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.Strength = 10;
        this.Mana = 100;
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.addCapability(Status.IS_PLAYER);
        this.setIntrinsicWeapon(new BareFist());
        this.addAttribute(BaseActorAttributes.MANA, new BaseActorAttribute(Mana));
        this.addAttribute(ActorAttributes.STRENGTH, new BaseActorAttribute(Strength));
    }
    /**
     * Gets the current hit points of the player.
     *
     * @return Player's current hit points.
     */
    public int getHitPoints() {
        return this.getAttribute(BaseActorAttributes.HEALTH);
    }
    /**
     * Gets the current mana points of the player.
     *
     * @return Player's current mana points.
     */
    public int getManaPoints() {
        return this.getAttribute(BaseActorAttributes.MANA);
    }
    /**
     * Gets the current strength of the player.
     *
     * @return Player's current strength.
     */
    public int getStrength() {
        return this.getAttribute(ActorAttributes.STRENGTH);
    }
    /**
     * Increases the player's strength by a specified amount.
     *
     * @param amount The amount to increase the player's strength by.
     */
    /**public void increaseStrength(int amount) {
     this.strength += amount;
     }*/
    /**
     * Gets the player's maximum hit points.
     *
     * @return Player's maximum hit points.
     */
    public int getMaxHitPoints() {
        return this.getAttributeMaximum(BaseActorAttributes.HEALTH);
    }
    /**
     * Gets the player's maximum mana points.
     *
     * @return Player's maximum mana points.
     */
    public int getMaxManaPoints() {
        return this.getAttributeMaximum(BaseActorAttributes.MANA);
    }
    /**
     * Finds the nearest target for the player to attack.
     *
     * @param map The game map the player is currently on.
     * @return The target actor, or null if no hostile target is found.
     */
    private Actor findTarget(GameMap map) {
        Location playerLocation = map.locationOf(this);

        for (Exit exit: playerLocation.getExits()) {
            Location adjacentLocation = exit.getDestination();
            if (adjacentLocation.containsAnActor()) {
                Actor potentialTarget = adjacentLocation.getActor();
                if (potentialTarget.hasCapability(Status.HOSTILE_TO_PLAYER)) {
                    return potentialTarget;
                }
            }
        }
        return null;
    }

    private Actor findTrader(GameMap map) {
        Location playerLocation = map.locationOf(this);

        for (Exit exit : playerLocation.getExits()) {
            Location adjacentLocation = exit.getDestination();
            if (adjacentLocation.containsAnActor()) {
                Actor potentialTrader = adjacentLocation.getActor();
                if (potentialTrader.hasCapability(Status.TRADER)) {
                    return potentialTrader;
                }
            }
        }
        return null;
    }
    /**
     * Defines the player's actions for their turn, including attacking with weapons or using items from the inventory.
     *
     * @param actions The list of possible actions for the player.
     * @param lastAction The player's last action taken.
     * @param map The game map the player is currently on.
     * @param display The display object used to show the game's output.
     * @return The action the player takes on their turn.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        if (!this.isConscious()) {
            display.println("Tarnished has died.");
            this.unconscious(map);
            return new DoNothingAction();
        }

        display.println("Tarnished " + "(" + String.valueOf(this.getHitPoints()) + "/" + String.valueOf(this.getMaxHitPoints()) + ")");
        display.println("Mana: " + "(" + String.valueOf(this.getManaPoints()) + "/" + String.valueOf(this.getMaxManaPoints()) + ")");
        display.println("Strength: " + String.valueOf(this.getStrength()));

        Actor target = findTarget(map);

        if (findTarget(map) != null) {
            actions.add(new AttackAction(target, "with Bare Fist"));
        }

        for (Item item : this.getItemInventory()) {
            if (item.getClass() == ShortSword.class) {
                WeaponItem shortSword = (WeaponItem) item;
                shortSword.setMeetMinimumRequirement(this);
                if (shortSword.getPickUpAction(this) != null) {
                    if (findTarget(map) != null) {
                        actions.add(new AttackAction(target, "with Short Sword", shortSword));
                    }
                }
            }
        }


        for (Item item : this.getItemInventory()) {
            if (item.getClass() == GreatKnife.class) {
                WeaponItem greatKnife = (WeaponItem) item;
                greatKnife.setMeetMinimumRequirement(this);
                if (greatKnife.getPickUpAction(this) != null) {
                    if (findTarget(map) != null) {
                        actions.add(new AttackAction(target, "with Great Knife", greatKnife));
                    }
                }
            }
        }
        Actor trader = findTrader(map);
        if (trader != null) {
            for (Item item : this.getItemInventory()) {
                if (item.getClass() == RemembranceOfDancingLion.class) {
                    actions.add(new TradeAction(this, trader, item));
                    break;  // Stop after finding the first relevant item
                }
            }

            // Check for Remembrance of Furnace Golem
            for (Item item : this.getItemInventory()) {
                if (item.getClass() == RemembranceOfFurnaceGolem.class) {
                    actions.add(new TradeAction(this, trader, item));
                    break;  // Stop after finding the first relevant item
                }
            }
        }


        // return/print the console menu
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }
}