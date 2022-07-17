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
    @DisplayName("User destroys spawner")
    public void PlayerDestroysSpawner() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_zombieToastTests_destroySpawn", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        
        // create the expected player position
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);

        // move up again
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        assertEquals(expectedPlayer, actualPlayer);

        // check if treasure is in the inventory
        assertEquals(1, getInventory(actualDungonRes, "sword").size());

        actualDungonRes = dmc.tick(Direction.DOWN);
        actualDungonRes = dmc.tick(Direction.DOWN);
        actualDungonRes= dmc.interact("5");

        assertEquals(0, getEntities(actualDungonRes, "zombie_toast_spawner").size());

    }

    
}
