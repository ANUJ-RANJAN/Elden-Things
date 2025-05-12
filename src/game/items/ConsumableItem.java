package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import game.actors.ActorAttributes;

import java.util.List;
/**
 * Abstract class representing an item that can be consumed by an actor.
 *
 * <p>Consumable items have limited uses and provide certain effects upon consumption.
 * They are defined by the attributes they modify and the values of those modifications.</p>
 */
public abstract class ConsumableItem extends Item {
    private List<BaseActorAttributes> baseEffects;
    private List<Integer> baseEffectValues;
    private List<ActorAttributes> actorEffects;
    private List<Integer> actorEffectValues;
    /**
     * The type of consumable item (for categorization).
     */
    private int NoOfUse;


    private ItemType itemType;
    /**
     * Constructor to initialize a consumable item with specific effects, use count, and attributes.
     *
     * @param name        the name of the item
     * @param displayChar the character to represent the item on the map
     * @param Effect      list of attributes affected by the item
     * @param EffectValue list of values for the effects on the attributes
     * @param NoOfUse     the number of times the item can be used
     */
    public ConsumableItem(String name, char displayChar,
                          List<BaseActorAttributes> baseEffects, List<Integer> baseEffectValues,
                          List<ActorAttributes> actorEffects, List<Integer> actorEffectValues,
                          int NoOfUse, ItemType itemType) {
        super(name, displayChar, true);
        this.baseEffects = baseEffects;
        this.baseEffectValues = baseEffectValues;
        this.actorEffects = actorEffects;
        this.actorEffectValues = actorEffectValues;
        this.NoOfUse = NoOfUse;
        this.itemType = itemType;
    }
    /**
     * Gets the type of the consumable item.
     *
     * @return the item type
     */
    public ItemType getItemType() {
        return this.itemType;
    }
    /**
     * Returns the list of attributes affected by this item.
     *
     * @return the list of attributes
     */
    public List<BaseActorAttributes> getBaseEffects() {
        return this.baseEffects;
    }
    /**
     * Returns the list of effect values applied to the attributes.
     *
     * @return the list of effect values
     */
    public List<Integer> getBaseEffectValues() {
        return this.baseEffectValues;
    }
    /**
     * Returns the list of actor-specific attributes affected by this item.
     *
     * @return the list of actor attributes
     */
    public List<ActorAttributes> getActorEffects() {
        return this.actorEffects;
    }

    /**
     * Returns the list of values applied to actor attributes.
     *
     * @return the list of actor attribute effect values
     */
    public List<Integer> getActorEffectValues() {
        return this.actorEffectValues;
    }

    /**
     * Gets the number of remaining uses for this item.
     *
     * @return the number of uses left
     */
    public int getNoOfUse() {
        return this.NoOfUse;
    }

    /**
     * Checks if the item is empty (i.e., no uses left).
     *
     * @return true if the item has no uses left, false otherwise
     */
    public boolean isEmpty() {
        return this.NoOfUse <= 0;
    }
    /**
     * Decreases the number of uses for this item by one.
     */
    public void decreaseUse() {
        if (this.NoOfUse > 0) {
            this.NoOfUse--;
        }
    }
    /**
     * Checks if the item is owned by a specific actor.
     *
     * @param owner the actor to check ownership against
     * @return true if the actor owns the item, false otherwise
     */
    public boolean isOwnedBy(Actor owner) {
        return owner.getItemInventory().contains(this);
    }

}
