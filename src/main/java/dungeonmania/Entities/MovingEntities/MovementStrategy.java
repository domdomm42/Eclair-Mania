package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Direction;

abstract public class MovementStrategy {
    private Entity entity;

    public void move(Direction playerDirection) { };
    public void move() { };

    public MovementStrategy(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

}
