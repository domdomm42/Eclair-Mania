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
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        // check that there is 4 zombies
        assertEquals(4, getEntities(res, "zombie_toast").size());
    }

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

    @Test
    @DisplayName("Zombie toast spawner cannot spawn zombies as its blocked by walls")
    public void spawnerBlockedByWalls() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_zombieDontWall", "c_movementTest_testMovementDown");

        // for each tick, spawn zombie
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, getEntities(res, "zombie_toast").size());
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, getEntities(res, "zombie_toast").size());
    }
}
