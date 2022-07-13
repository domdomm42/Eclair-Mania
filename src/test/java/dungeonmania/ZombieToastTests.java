package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToastTests {
    @Test
    @DisplayName("Spawnable at zombie spawners")
    public void ZombieToastSpawn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieToastTests_spawn", "c_zombieToastTests_spawnZombies");

        // for each 2 tick, spawn zombie
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);

        // check that there are 2 zombie toasts as we have ran 4 ticks
        assertEquals(2, getEntities(res, "ZombieToast").size());

    }

    // not done
    @Test
    @DisplayName("Portal has no effect")
    public void PortalNoEffectOnZombieToast() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieToastTests_spawn", "c_zombieToastTests_spawnZombies");

        Position pos = getEntities(res, "zombie_toast").get(0).getPosition();
        // for each 2 tick, spawn zombie
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.DOWN);


        // one zombie exist checks that its location is not teleported
        assertEquals(1, getEntities(res, "zombie_toast").get(0).getPosition());

    }


    
}
