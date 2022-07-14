package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class ShieldTests {

    @Test
    @DisplayName("Test making a shield using 2 wood + 1 treasure")
    public void testMakeShieldWithTreasure() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_shieldTest_withTreasure", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // pick up items
        res = dmc.tick(Direction.RIGHT);
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
    public void testMakeShieldWithKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_shieldTest_withKey", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // pick up items
        res = dmc.tick(Direction.RIGHT);
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
    public void testMakeShieldWithInsufficientIngredients() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_shieldTest_withKey", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // pick up items
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "key").size());

        
        assertThrows(InvalidActionException.class, () -> dmc.build("shield"));
        assertEquals(2, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "key").size());
        assertEquals(0, getInventory(res, "shield").size());
    }
}
