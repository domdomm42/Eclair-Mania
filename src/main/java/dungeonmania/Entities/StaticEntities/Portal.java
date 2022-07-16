package dungeonmania.Entities.StaticEntities;

import dungeonmania.Dungeon;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    private String color;

    public Portal(Position position, String id, String color) {
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
