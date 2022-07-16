package dungeonmania.Entities.MovingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.MovementStrategies.PlayerMovementStrategy;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.Inventory;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.PotionBag;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Key;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends MovingEntity {
    Inventory inventory;
    PotionBag potionBag;

    public Player(String id, Position position, boolean isInteractable) {
        super(id, "player", position, Dungeon.getConfigValue("player_health"), isInteractable, new PlayerMovementStrategy(), Dungeon.getConfigValue("player_attack"));
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
    
    public Inventory getInventory() {
        return inventory;
    }

    public List<CollectableEntity> getInventory(String type) {
        return inventory.getItemsOfType(type);
    }

    public void useKey(Key key) {
        inventory.removeItem(key);
    }

    public PotionBag getPotionBag() {
        return potionBag;
    }

    public ArrayList<String> getBuildables() {
        return inventory.getCraftableItems();
    }

    @Override
    public int getAttack() {
        return (super.getAttack() + inventory.getItemsOfType("sword").size() > 0 ? Dungeon.getConfigValue("sword_attack") : 0) 
                * (inventory.getItemsOfType("bow").size() > 0 ? 2 : 1);
    }

    public List<CollectableEntity> getWeaponryUsed() {
        List<CollectableEntity> weaponryUsed = inventory.getItemsOfType("sword");
        weaponryUsed.addAll(inventory.getItemsOfType("bow"));
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
}
