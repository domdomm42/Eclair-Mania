package dungeonmania.Entities.StaticEntities;

import dungeonmania.util.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.Dungeon;
import dungeonmania.EntityFactory;
import dungeonmania.Entities.Entity;
import dungeonmania.exceptions.InvalidActionException;

public class ZombieToastSpawner extends StaticEntity {

    private int num_ticks = 0;
    private int zombie_spawn_rate = Dungeon.getConfigValue("zombie_spawn_rate");

    public ZombieToastSpawner(Position position, String id) {
        super(position, id, "zombie_toast_spawner", true);
    }

    public void spawnZombie() {
        List<Position> adjacentPosition = getPosition().getAdjacentPositions();
        Map<String, String> creationArguments = new HashMap<String, String>();

        
        for (Position squares: adjacentPosition) {
            List<Entity> entity = Dungeon.getEntitiesAtPosition(squares);
            if (entity.isEmpty()) {
                creationArguments.put("x", Integer.toString(squares.getX()));
                creationArguments.put("y", Integer.toString(squares.getY()));
                Dungeon.addEntityToAddAfterTick(EntityFactory.createEntity("zombie_toast", creationArguments));
                return;
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

    @Override
    public void interact() throws IllegalArgumentException, InvalidActionException {
        // if player does not have sword then player cant destroy zombietoastspawner
        if (!Dungeon.getPlayer().hasCollectable("sword")) {
            return;
        }

        // if player has sword then destroy zombietoastspawner 
        else {
            if (Position.isAdjacent(Dungeon.getPlayer().getPosition(), this.getPosition())) {
                Dungeon.getPlayer().useSwordToBreakZombieToastSpawner();
                Dungeon.removeEntity(this);
            }
        }
    }
}
