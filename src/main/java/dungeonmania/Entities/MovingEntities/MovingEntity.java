package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Position;
import dungeonmania.util.Direction;

public class MovingEntity extends Entity implements Move {
    private int health;
    
    public MovingEntity(String id, String type, Position position, int health, boolean isInteractable) {
        super(id, type, position, isInteractable);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    public void move(Position position, Direction direction){}
    public boolean isValidMove(){
        return true;
    }
}
