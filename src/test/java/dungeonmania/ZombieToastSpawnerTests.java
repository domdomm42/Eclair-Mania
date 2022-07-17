package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getEntities;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class ZombieToastSpawnerTests {
    @Test
    @DisplayName("User destroys spawner")
    public void PlayerDestroysSpawner() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieToastTests_destroySpawn", "c_movementTest_testMovementDown");

        // player picks up sword, if player is next to zombie toast then destroy it
        res = dmc.tick(Direction.UP);
        assertEquals(1, getEntities(res, "sword").size());

        res = dmc.tick(Direction.LEFT);
        // res = dmc.tick(Direction.RIGHT);

        // String swordId = getInventory(res, "sword").get(0).getId();
        // res = assertDoesNotThrow(() -> dmc.tick(swordId));

       
        assertEquals(0, getEntities(res, "zombie_toast_spawner").size());

    }

    
}
