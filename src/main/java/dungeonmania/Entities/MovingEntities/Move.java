package dungeonmania.Entities.MovingEntities;

import dungeonmania.util.Direction;
import dungeonmania.Entities.Position;

public interface Move {
    public void move(Position position, Direction direction);
    public boolean isValidMove();

    
}
