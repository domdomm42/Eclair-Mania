package dungeonmania.Entities.StaticEntities;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;

public class ZombieToastSpawner extends StaticEntity {

    public ZombieToastSpawner(Position position, String id, String type, boolean Isinteractable) {
        super(position, id, "zombie_toast_spawner", true);
    }

    public void spawnZombie(Position position) {
        List<Position> adjacentPosition = position.getAdjacentPositions();

        for (Position squares: adjacentPosition) {
            List<Entity> entity = Dungeon.getEntityAtPosition(position);
            if (entity.isEmpty()) {

                // fill in once zombie toast is completed
                ZombieToast(squares, "zombie", 1);
                break;
            }
        }
        
    }

    
}
