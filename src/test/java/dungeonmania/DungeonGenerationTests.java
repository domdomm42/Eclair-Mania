package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getInventory;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static dungeonmania.TestUtils.getEntities;

public class DungeonGenerationTests {
    
    @Test
    @DisplayName("Config file does not exist")
    public void testInvalidConfig() throws IllegalArgumentException, InvalidActionException {
        
        DungeonManiaController dmc = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> dmc.generateDungeon(0, 0, 50, 50, "invalid_config"));
    }

    @Test
    @DisplayName("Create a dungeon")
    public void testCreateDungeon() throws IllegalArgumentException, InvalidActionException {
        
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initRes = dmc.generateDungeon(0, 0, 30, 30, "c_dungeonGeneratorTest");

        Position actualPlayerPos = getEntities(initRes, "player").get(0).getPosition();
        Position expectedPlayerPos = new Position(0, 0);
        assertEquals(actualPlayerPos, expectedPlayerPos);

        Position actualExitPos = getEntities(initRes, "exit").get(0).getPosition();
        Position expectedExitPos = new Position(30, 30);
        assertEquals(actualExitPos, expectedExitPos);

        DungeonResponse res = dmc.tick(Direction.UP);
        actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        expectedPlayerPos = new Position(0, 0);
        assertEquals(actualPlayerPos, expectedPlayerPos);
    }

    


}
