package dungeonmania.Entities.MovingEntities;

import dungeonmania.util.Direction;

abstract public class MovementStrategy {
    abstract public void move(Direction playerDirection);
}
