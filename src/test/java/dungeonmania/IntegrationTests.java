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
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class FloorSwitchTests {

    @Test
    @DisplayName("Integration test 1")
    public void integrationTest1() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "d_integrationTest_1");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initSwitch = getEntities(initDungonRes, "switch").get(0);

        // Pick up sword
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "sword").size());
        
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Fight mercenary
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, countEntityOfType(res, "mercenary"));

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);

        // Pick up key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());

        // Enter portal
        res = dmc.tick(Direction.RIGHT);

        // Unlock door
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getInventory(res, "key").size());

        // Exit
        res = dmc.tick(Direction.RIGHT);
    }

    @Test
    @DisplayName("Integration test 2")
    public void integrationTest2() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "d_integrationTest_1");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initSwitch = getEntities(initDungonRes, "switch").get(0);

        // Pick up sword
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "sword").size());
        
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Fight mercenary
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, countEntityOfType(res, "mercenary"));

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);

        // Pick up key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());

        // Enter portal
        res = dmc.tick(Direction.RIGHT);

        // Unlock door
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getInventory(res, "key").size());

        // Exit
        res = dmc.tick(Direction.RIGHT);
    }
}
