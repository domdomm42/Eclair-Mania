package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static dungeonmania.TestUtils.getInventory;

import java.io.FileNotFoundException;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import dungeonmania.response.models.DungeonResponse;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;


public class ShieldTests {

    @Test
    @DisplayName("Test making a shield using 2 wood + 1 treasure")
    public void testMakeShieldWithTreasure() throws InvalidActionException, IllegalArgumentException, FileNotFoundException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_shieldTest_withTreasure", "c_movementTest_testMovementDown");
        

        // pick up items
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, getInventory(res, "wood").size());
        assertEquals(1, getInventory(res, "treasure").size());

        dmc.build("shield");
        assertEquals(0, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "treasure").size());
        assertEquals(0, getInventory(res, "shield").size());
    }

    @Test
    @DisplayName("Test making a shield using 2 wood + 1 key")
    public void testMakeShieldWithKey() throws InvalidActionException, IllegalArgumentException, FileNotFoundException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_shieldTest_withKey", "c_movementTest_testMovementDown");

        // pick up items
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, getInventory(res, "wood").size());
        assertEquals(1, getInventory(res, "key").size());

        dmc.build("shield");
        assertEquals(0, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "key").size());
        assertEquals(0, getInventory(res, "shield").size());
    }

    @Test
    @DisplayName("Test making a shield using 2 wood")
    public void testMakeShieldWithInsufficientIngredients() throws InvalidActionException, IllegalArgumentException, FileNotFoundException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_shieldTest_withKey", "c_movementTest_testMovementDown");

        // pick up items
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "key").size());

        
        assertThrows(InvalidActionException.class, () -> dmc.build("shield"));
        assertEquals(2, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "key").size());
        assertEquals(0, getInventory(res, "shield").size());
    }
}
