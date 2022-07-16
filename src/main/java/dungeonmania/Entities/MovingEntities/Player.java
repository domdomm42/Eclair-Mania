package dungeonmania.Entities.MovingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.MovementStrategies.PlayerMovementStrategy;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.Inventory;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.PotionBag;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Key;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Usable;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Potions.Potion;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends MovingEntity {
    Inventory inventory;
    PotionBag potionBag;

    public Player(String id, Position position) {
        super(id, "player", position, Dungeon.getConfigValue("player_health"), false, new PlayerMovementStrategy(), Dungeon.getConfigValue("player_attack"));
        inventory = new Inventory();
        potionBag = new PotionBag();
        getMovementStrategy().setEntity(this);
    };

    @Override
    public void tick(Direction direction) {
        super.tick(direction);
        getMovementStrategy().move(direction);
    }

    @Override
    public void tick() {
        super.tick();
        potionBag.tick();
    }

    @Override
    public void tick(String itemId) throws InvalidActionException, IllegalArgumentException {
        super.tick(itemId);
        useItem(itemId);
    }

    public void useItem(String itemId) throws InvalidActionException, IllegalArgumentException {
        Entity entityToUse = Dungeon.getEntityFromId(itemId);
        if (!inventory.containsCollectableById(itemId)) throw new InvalidActionException("Item does not exist in player's inventory");
        if (entityToUse instanceof Usable) {
            ((Usable) entityToUse).use();
        } else throw new IllegalArgumentException(itemId.concat(" is not Usable"));
    }
    
    public Inventory getInventory() {
        return inventory;
    }

    public List<CollectableEntity> getInventory(String type) {
        return inventory.getItemsOfType(type);
    }

    public void useKey(Key key) {
        inventory.removeItem(key);
    }

    public void useSwordToBreakZombieToastSpawner() {
        if (hasCollectable("sword")) inventory.removeItem(inventory.getFirstItemsOfType("sword"));
    }

    public void usePotion(Potion potion) {
        inventory.removeItem(potion);
        potionBag.usePotion(potion);
    }

    public PotionBag getPotionBag() {
        return potionBag;
    }

    public ArrayList<String> getBuildables() {
        return inventory.getCraftableItems();
    }

    @Override
    public double getAttack() {
        return (super.getAttack() + inventory.getItemsOfType("sword").size() > 0 ? Dungeon.getConfigValue("sword_attack") : 0) 
                * (inventory.getItemsOfType("bow").size() > 0 ? 2 : 1);
    }

    public double getDefence() {
        return (inventory.getItemsOfType("shield").size() > 0 ? Dungeon.getConfigValue("shield_defence") : 0);
    }

    public List<CollectableEntity> getWeaponryUsed() {
        List<CollectableEntity> weaponryUsed = inventory.getItemsOfType("sword");
        weaponryUsed.addAll(inventory.getItemsOfType("bow"));
        weaponryUsed.addAll(inventory.getItemsOfType("shield"));
        if (activePotionEffect().equals("invincibility_potion")) weaponryUsed.add(potionBag.getActivePotion());
        return weaponryUsed;
    }

    public void pickup(CollectableEntity item) {
        inventory.addItem(item);
    }

    @Override
    public void build(String type) throws InvalidActionException, IllegalArgumentException {
        inventory.buildEntity(type);
    }

    public boolean hasCollectable(String type) {
        return inventory.containsCollectable(type);
    }

    public String activePotionEffect() {
        return potionBag.getActivePotionType();
    }

    public void onDurabilityRunsOut(CollectableEntity entity) {
        inventory.removeItem(entity);
    }

    public int getNumberOfTreasures() {
        return inventory.getItemsOfType("treasure").size();
    }
}
