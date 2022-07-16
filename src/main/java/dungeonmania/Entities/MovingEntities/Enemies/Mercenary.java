package dungeonmania.Entities.MovingEntities.Enemies;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.MovementStrategies.MercenaryMovementStrategy;
import dungeonmania.util.Position;

public class Mercenary extends Enemy {
    private int bribePrice = Dungeon.getConfigValue("bribe_amount");
    private int bribeRadius = Dungeon.getConfigValue("bribe_radius");
    private boolean isAlly = false;
    
    
    public Mercenary(String id, Position position) {
        super(id, "mercenary", position, Dungeon.getConfigValue("mercenary_health"), true, new MercenaryMovementStrategy(), Dungeon.getConfigValue("mercenary_attack"));
        getMovementStrategy().setEntity(this);
    }

    @Override
    public void tick() {
        getMovementStrategy().move();
    }

    // when mercenary turns into ally switch isinteractable to false
}
