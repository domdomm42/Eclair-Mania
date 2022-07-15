package dungeonmania.Entities.StaticEntities;

import dungeonmania.util.Position;


import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;

public class ZombieToastSpawner extends StaticEntity {

    public ZombieToastSpawner(Position position, String id, String type, boolean Isinteractable) {
        super(position, id, type, Isinteractable);
    }

    public void spawnZombie(Position position) {

        // takes in position of portal
        // checks if adjacent squares are empty

        checkIfSquareIsClearofEntity(position);



        // if left is empty spawn left
        // if right is empty spawn right
        // if up is empty spawn up
        // if down is empty spawn down
        
    }


    public boolean checkIfSquareIsClearofEntity(Position position) {
        List<Position> adjacentPosition = position.getAdjacentPositions();

        Entities.stream().filter(entity -> entity.getType() == "player").collect(Collectors.toList()).get(0);

        for (Position squares: adjacentPosition) {
            if (List<Entity> )
        }
    }




    
}
