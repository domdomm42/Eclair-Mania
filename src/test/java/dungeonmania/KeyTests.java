package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getPlayer;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class KeyTests {
    @Test
    @DisplayName("Test player can use a key to open and walk through a door")
    public void useKeyWalkThroughOpenDoor() {
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
    @DisplayName("Test player can't use wrong key to open and walk through a door")
    public void cantUseWrongKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_DoorsKeysTest_cantUseKey", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(res).get();

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        // Pick up key
        assertEquals(1, getInventory(res, "key").size());

        // Enter portal
        res = dmc.tick(Direction.RIGHT);

        // Unlock door
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());


    }
}