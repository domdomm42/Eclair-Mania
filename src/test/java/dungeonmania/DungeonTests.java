package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;

public class DungeonTests {

    @Test
    @DisplayName("Create valid dungeon")
    public void testDungeon() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        assertDoesNotThrow(() -> dmc.newGame("d_floorSwitchGoal", "c_movementTest_testMovementDown"));
    }

    @Test
    @DisplayName("Config file does not exist")
    public void testInvalidConfig() throws IllegalArgumentException, InvalidActionException {
        
        DungeonManiaController dmc = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> dmc.newGame("d_floorSwitchGoal", "invalid"));
    }

    @Test
    @DisplayName("Dungeon does not exist")
    public void testInvalidDungeon() throws IllegalArgumentException, InvalidActionException {
        
        DungeonManiaController dmc = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> dmc.newGame("invalid", "c_movementTest_testMovementDown"));
    }
}
