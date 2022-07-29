package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getInventory;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import static dungeonmania.TestUtils.getEntities;

public class DungeonGenerationTests {
    
    @Test
    @DisplayName("Config file does not exist")
    public void testAttemptMakeSceptre() throws IllegalArgumentException, InvalidActionException {
        
        DungeonManiaController dmc = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> dmc.generateDungeon(0, 0, 50, 50, "invalid_config"));
    }

    


}
