package dungeonmania;

import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getPlayer;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class IntegrationTests {

    @Test
    @DisplayName("Integration test 1")
    public void integrationTest1() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_integrationTest_1", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(res).get();

        // Pick up sword
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "sword").size());
        
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        // Pick up key
        assertEquals(1, getInventory(res, "key").size());

        // Enter portal
        res = dmc.tick(Direction.RIGHT);

        // Unlock door
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getInventory(res, "key").size());

        Position expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(7, 4), false).getPosition();
        Position actualPlayer = getPlayer(res).get().getPosition();
        assertEquals(expectedPlayer, actualPlayer);
    }
    
    @Test
    @DisplayName("Integration test 2")
    public void integrationTest2() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_integrationTest_2", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(res).get();

        // Pick up key 2
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());
        
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Unlock door
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getInventory(res, "key").size());

        // Pick up key 2
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);

        // Pick up invincibility potion
        res = dmc.tick(Direction.DOWN);

        // Use potion
        String potionId = getInventory(res, "invincibility_potion").get(0).getId();
        res = dmc.tick(potionId);

        // Jump into portal
        res = dmc.tick(Direction.RIGHT);

        // Use door 2
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getInventory(res, "key").size());
    }

    @Test
    @DisplayName("Integration test 3")
    public void integrationTest3() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_integrationTest_3", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(res).get();

        // Pick up treasure and bomb
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "treasure").size());
        assertEquals(1, getInventory(res, "bomb").size());

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);

        // Push boulder onto floor switch
        res = dmc.tick(Direction.RIGHT);

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);

        // Drop bomb
        String bombId = getInventory(res, "bomb").get(0).getId();
        res = dmc.tick(bombId);
        assertEquals(0, getInventory(res, "bomb").size());

        // Walls are destroyed
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        Position expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(5, 4), false).getPosition();
        Position actualPlayer = getPlayer(res).get().getPosition();
        assertEquals(expectedPlayer, actualPlayer);
    }

    
    @Test
    @DisplayName("Integration test 4")
    public void integrationTest4() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_integrationTest_4", "c_movementTest_testMovementDown");

        DungeonResponse res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(3, getInventory(res, "wood").size());
        
        // Pick up 3 arrows
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        assertEquals(3, getInventory(res, "arrow").size());

        // Pick up treasure
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, getInventory(res, "treasure").size());

        // Build bow and shield
        res = dmc.build("shield");
        res = dmc.build("bow");
        assertEquals(1, getInventory(res, "shield").size());
        assertEquals(1, getInventory(res, "bow").size());
        // Build 

        // Get key
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());

        // Unlock door
        res = dmc.tick(Direction.RIGHT);
    }
}
