package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.MovingEntities.MovementStrategies.PlayerMovementStrategy;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.Inventory;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.PotionBag;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends MovingEntity {
    Inventory inventory;
    PotionBag potionBag;

    public Player(String id, String type, Position position, int health, boolean isInteractable) {
        super(id, type, position, health, isInteractable, new PlayerMovementStrategy());
        inventory = new Inventory();
        potionBag = new PotionBag();
    };

    @Override
    public void tick(Direction direction) {
        super.tick(direction);
        getMovementStrategy().move(direction);
    }
}
