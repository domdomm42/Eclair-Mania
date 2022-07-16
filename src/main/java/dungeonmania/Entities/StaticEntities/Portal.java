package dungeonmania.Entities.StaticEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    private Position endLocation;

    public Portal(Position position, String id, String type, boolean Isinteractable, Position endLocation) {
        super(position, id, "portal", false);
        this.endLocation = endLocation;
    }

    public Position getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Position endLocation) {
        this.endLocation = endLocation;
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

    public Position checkIfAdjacentSquareIsWall(Position position, Direction direction) {

        Position newPos = position.translateBy(direction);
        if (Dungeon.isEntityOnPosition(newPos, "wall")) {
            return null;
        }

        else {
            return newPos;
        }
    
    
        
    }



}
