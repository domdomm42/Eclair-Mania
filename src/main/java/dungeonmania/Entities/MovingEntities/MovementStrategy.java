package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

abstract public class MovementStrategy {
    private Entity entity;

    public void move(Direction playerDirection) { };
    public void move() { };

    public Entity getEntity() {
        return entity;
    }
    
    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    
    public abstract boolean isValidMove(Position position);

}
