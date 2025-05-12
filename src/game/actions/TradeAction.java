package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;
import game.weapons.FurnaceEngine;
import game.weapons.DivineBeastHead;

/**
 * Represents an action to trade a remembrance token with the Suspicious Trader.
 * Depending on the remembrance token, the player will receive either the Divine Beast Head or the Furnace Engine.
 */
public class TradeAction extends Action {
    private final Player player;
    private final Actor trader;
    private final Item remembrance;

    public TradeAction(Player player, Actor trader, Item remembrance) {
        this.player = player;
        this.trader = trader;
        this.remembrance = remembrance;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        String remembranceName = remembrance.toString();

        // Determine the remembrance token by its name and apply the corresponding effect
        if (remembranceName.equals("Remembrance of the Dancing Lion")) {
            player.removeItemFromInventory(remembrance);
            DivineBeastHead divineBeastHead = new DivineBeastHead();
            player.addItemToInventory(divineBeastHead);
            divineBeastHead.applyEffects(player);
            return "Traded Remembrance of the Dancing Lion for Divine Beast Head, gaining its powers and bonuses.";
        }
        else if (remembranceName.equals("Remembrance of the Furnace Golem")) {
            player.removeItemFromInventory(remembrance);
            FurnaceEngine furnaceEngine = new FurnaceEngine(actor,new Display());
            player.addItemToInventory(furnaceEngine);
            furnaceEngine.applyEffects(player);
            return "Traded Remembrance of the Furnace Golem for Furnace Engine, gaining its powers and bonuses.";
        }

        return "Trade failed. Unknown remembrance token.";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Trade " + remembrance + " with " + trader;
    }
}