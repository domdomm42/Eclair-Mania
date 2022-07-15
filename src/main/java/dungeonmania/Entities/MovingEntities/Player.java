package dungeonmania.Entities.MovingEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.MovementStrategies.PlayerMovementStrategy;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.Inventory;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.PotionBag;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends MovingEntity {
    Inventory inventory;
    PotionBag potionBag;

    public Player(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, Dungeon.getConfigValue("player_health"), isInteractable, new PlayerMovementStrategy(), Dungeon.getConfigValue("player_attack"));
        inventory = new Inventory();
        potionBag = new PotionBag();
    };

    @Override
    public void tick(Direction direction) {
        super.tick(direction);
        getMovementStrategy().move(direction);
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
}
