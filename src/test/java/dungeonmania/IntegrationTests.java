package dungeonmania;

import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getPlayer;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;


public class IntegrationTests {

    @Test
    @DisplayName("Integration test 1")
    public void integrationTest1() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "d_integrationTest_1");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
    

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

    }

    @Test
    @DisplayName("Integration test 2")
    public void integrationTest2() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "d_integrationTest_2");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        assertEquals(2, countEntityOfType(initDungonRes, "mercenary"));

        // Pick up key
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());
        
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);

        // Fight mercenary 1
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, countEntityOfType(res, "mercenary"));

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);

        // Pick up invincibility potion
        res = dmc.tick(Direction.DOWN);

        // Use potion
        res = dmc.tick("invincibility_potion");

        // Jump into portal
        res = dmc.tick(Direction.DOWN);

        // Fight mercenary 2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, countEntityOfType(res, "mercenary"));

    }

    @Test
    @DisplayName("Integration test 3")
    public void integrationTest3() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "d_integrationTest_2");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        assertEquals(1, countEntityOfType(initDungonRes, "mercenary"));

        // Pick up treasure and bomb
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "treasure").size());
        assertEquals(1, getInventory(res, "bomb").size());
        
        // Fight mercenary
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, countEntityOfType(initDungonRes, "mercenary"));

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);

        // Push boulder onto floor switch
        res = dmc.tick(Direction.RIGHT);

        // Pick up sword
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, getInventory(res, "sword").size());

        // Drop bomb
        res = dmc.tick("bomb");
        assertEquals(0, getInventory(res, "bomb").size());

        // Pick up key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());

        // Go through door
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, countEntityOfType(res, "key"));

        // Fight zombies
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);

        // Go to exit
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
    }

    @Test
    @DisplayName("Integration test 4")
    public void integrationTest4() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "d_integrationTest_2");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        assertEquals(2, countEntityOfType(initDungonRes, "mercenary"));

        // Pick up 3 wood and fight mercenary
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(3, getInventory(res, "wood").size());
        assertEquals(1, countEntityOfType(initDungonRes, "mercenary"));
        
        // Pick up 3 arrows
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        assertEquals(3, countEntityOfType(initDungonRes, "arrow"));

        // Pick up treasure
        res = dmc.tick(Direction.DOWN);
        assertEquals(3, countEntityOfType(initDungonRes, "treasure"));

        // Get key
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, countEntityOfType(initDungonRes, "key"));

        // Unlock door
        res = dmc.tick(Direction.RIGHT);

        // Fight mercenary
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, countEntityOfType(initDungonRes, "mercenary"));
    }
}
