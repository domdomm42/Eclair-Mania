package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getEntities;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getPlayer;

public class ZombieToastSpawnerTests {

    @Test
    @DisplayName("User destroys spawner")
    public void PlayerDestroysSpawner() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieToastTests_destroySpawn", "c_zombieToastTests_noZombies");
        
        String spawnerId = getEntities(res, "zombie_toast_spawner").get(0).getId();
        assertEquals(1, getEntities(res, "zombie_toast_spawner").size());

        res = dmc.tick(Direction.DOWN);
        assertEquals(1, getInventory(res, "sword").size());
        
        // Destroy spawner
        assertEquals(1, getInventory(res, "sword").size());
        res = dmc.interact(spawnerId);
        assertEquals(0, getEntities(res, "zombie_toast_spawner").size());
    }
}
