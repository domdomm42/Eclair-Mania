package dungeonmania.Entities.StaticEntities;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.Inventory;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.exceptions.InvalidActionException;

public class ZombieToastSpawner extends StaticEntity {

    public ZombieToastSpawner(Position position, String id, String type, boolean Isinteractable) {
        super(position, id, "zombie_toast_spawner", true);
    }

    public void spawnZombie(Position position) {
        List<Position> adjacentPosition = position.getAdjacentPositions();

        for (Position squares: adjacentPosition) {
            List<Entity> entity = Dungeon.getEntitiesAtPosition(position);
            if (entity.isEmpty()) {

                // fill in once zombie toast is completed
                ZombieToast(squares, "zombie", 1);
                break;
            }
        }
        
    }

    // not sure if right
    // INTERACT player breaks zombietoastpawner at the tick
    public void interact() throws IllegalArgumentException, InvalidActionException {
        Inventory checkSword = Dungeon.getPlayer().getInventory();

        List<CollectableEntity> listOfSwords = checkSword.getItemsOfType("sword");

        // if player does not have sword then player cant destroy zombietoastspawner
        if (listOfSwords.isEmpty()) {
            return;
        }

        // if player has sword then destroy zombietoastspawner 
        else {
            if (Position.isAdjacent(Dungeon.getPlayer().getPosition(), this.getPosition())) {
                Dungeon.tick("sword");
                Dungeon.removeEntity(this);
            }
        }
    }

    
}
