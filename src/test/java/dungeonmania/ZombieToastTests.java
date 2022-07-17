package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getEntities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;

import dungeonmania.util.Direction;

public class ZombieToastTests {
    @Test
    @DisplayName("Spawnable at zombie spawners")
    public void ZombieToastSpawn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieToastTests_spawn", "c_movementTest_testMovementDown");

        // for each tick, spawn zombie
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);

        // check that there is 4 zombies
        assertEquals(4, getEntities(res, "zombie_toast").size());

    }

    // not done
    @Test
    @DisplayName("Portal has no effect on zombie hence zombie dont spawn as they are blocked")
    public void PortalNoEffectOnZombieToast() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_zombieDontPortal", "c_movementTest_testMovementDown");


        // for each tick, spawn zombie
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, getEntities(res, "zombie_toast").size());
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, getEntities(res, "zombie_toast").size());
    }


    
}
