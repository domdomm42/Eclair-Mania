package dungeonmania.Entities.StaticEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Bomb;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    private String color;

    public Portal(Position position, String id, String type, boolean Isinteractable, String color) {
        super(position, id, "portal", false);
        this.color = color;
    }

    
    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }


    // public Position checkIfAdjacentSquareIsWall(Position position) {
    //     List<Position> adjacentPosition = position.getAdjacentPositions();
    
    //     for (Position square: adjacentPosition) {
    //         List<Entity> entity = Dungeon.getEntitiesAtPosition(position);
    //         if (entity.contains("wall")) {
    //             continue;
    //         }

    //         else {
    //             return square;
    //         }
    //     }

    //     return null;
    // }

    public Position getTeleportLocation(Position position, Direction direction) {
        Position newPos = position.translateBy(direction);

        
        if (isPositionMovableForPlayer(newPos)) {
            return newPos;
        } else {
            return position;
        }
    }

    public boolean isPositionMovableForPlayer(Position newPos) {
        List<Entity> entitiesOnPosition = Dungeon.getEntitiesAtPosition(newPos);

        if (Dungeon.isEntityOnPosition(newPos, "wall")) {
            return false;
        } else if (Dungeon.isEntityOnPosition(newPos, "door")) {
            Door door = (Door) Dungeon.getFirstEntityOfTypeOnPosition(newPos, "door");
            if (!door.isUnlocked()) {
                return false;
            } 
        } else if (Dungeon.isEntityOnPosition(newPos, "bomb")) {
            Bomb bomb = (Bomb) entitiesOnPosition.stream().filter(entity -> entity instanceof Bomb).findFirst().get();
            if (bomb.isHasBeenPickedUp()) {
                return false;
            }
        } else if (Dungeon.isEntityOnPosition(newPos, "boulder")) {
            return false;
        } else if (Dungeon.isEntityOnPosition(newPos, "zombie_toast_spawner")) {
            return false;
        }   
        return true;
        
    }

    public Position getEndLocation(Portal portal) {
        // List<Entity> entitiesOnPosition = Dungeon.getEntitiesAtPosition(requestedPosition);

    }

}
