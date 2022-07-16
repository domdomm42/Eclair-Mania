package dungeonmania.Entities;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class Entity {
    private String id, type;
    private Position position;
    private boolean isInteractable = false;
    
    public Entity(String id, String type, Position position, boolean isInteractable) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.isInteractable = isInteractable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPositionInDirection(Direction direction) {
        return this.position.translateBy(direction);
    }

    public boolean getIsInteractable() {
        return isInteractable;
    }

    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    public void tick() { };
    // if bomb next to active switch then explode

    public void tick(Direction movementDirection) { } // only for player

    public void tick(String itemId) throws InvalidActionException, IllegalArgumentException { };
    // use key
    // use potion
    // use drop bomb

    public void build(String buildable) throws InvalidActionException, IllegalArgumentException  { }

    public void interact(String entityId) throws InvalidActionException, IllegalArgumentException  { }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Entity other = (Entity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
