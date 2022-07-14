package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Position;
import dungeonmania.util.Direction;

public class Player extends MovingEntity {
    // private Inventory inventory;
    // private PotionBag potionBag;

    public Player(String id, String type, Position position, int health, boolean isInteractable) {
        super(id, type, position, health, isInteractable);
    }

    public void move(Position position, Direction direction) {
        switch (direction) {
            case DOWN:
                position.setyCoordinate(position.getyCoordinate() - 1);break;
            case UP:
                position.setyCoordinate(position.getyCoordinate() + 1);break;
            case LEFT:
                position.setyCoordinate(position.getxCoordinate() - 1);break;
            case RIGHT:
                position.setyCoordinate(position.getxCoordinate() + 1);break;
        } 
    }

}
