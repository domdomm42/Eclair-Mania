package dungeonmania.Entities.StaticEntities;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.Dungeon;
import dungeonmania.EntityFactory;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.Inventory;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.exceptions.InvalidActionException;

public class ZombieToastSpawner extends StaticEntity {

    private int num_ticks = 0;
    private int zombie_spawn_rate = Dungeon.getConfigValue("zombie_spawn_rate");

    public ZombieToastSpawner(Position position, String id, String type, boolean Isinteractable) {
        super(position, id, "zombie_toast_spawner", true);
    }

    public void spawnZombie() {
        List<Position> adjacentPosition = getPosition().getAdjacentPositions();

        //saves each tick and then mods it by rate
        //id is zombie-int

        Map<String, String> creationArguments = new HashMap<String, String>();
        
        // int zombie = entities.stream().filter(entity -> entity.getType().equals("zombie")).size();


        creationArguments.put("zombie_toast", "zombie_toast-".concat(getId()));

        
        for (Position squares: adjacentPosition) {
            List<Entity> entity = Dungeon.getEntitiesAtPosition(squares);
            if (entity.isEmpty()) {
                // int zombie_spawn_rate = Dungeon.getConfigValue("zombie_spawn_rate");
                EntityFactory.createEntity("zombie_toast", creationArguments);
            }
        }
    }

    @Override
    public void tick() {
        if (num_ticks % zombie_spawn_rate == 0) {
            spawnZombie();
        }

        num_ticks++;
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
                Dungeon.getEntities().remove("zombie_toast_spawner");
            }
        }
    }

    
}
