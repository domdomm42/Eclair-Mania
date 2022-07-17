package dungeonmania.Entities.MovingEntities.MovementStrategies;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PathNode {
    private final Position position;
    private final Direction initialDirection;

    public PathNode(Position position, Direction initialDirection) {
        this.position = position;
        this.initialDirection = initialDirection;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getInitialDirection() {
        return initialDirection;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PathNode other = (PathNode) obj;
        if (initialDirection != other.initialDirection)
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }
    
}
