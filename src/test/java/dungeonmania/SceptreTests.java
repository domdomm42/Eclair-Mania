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

public class SceptreTests {
    
    @Test
    @DisplayName("Player builds Spectre from 1 wood + 1 key + 1 sun stone")
    public void testMakeSceptre1() {
    }

    @Test
    @DisplayName("Player builds Spectre from 1 wood + 1 treasure + 1 sun stone")
    public void testMakeSceptre2() {
    }

    @Test
    @DisplayName("Player builds Spectre from 2 arrows + 1 treasure + 1 sun stone")
    public void testMakeSceptre3() {
    }

    @Test
    @DisplayName("Player builds Spectre from 2 arrows + 1 key + 1 sun stone")
    public void testMakeSceptre4() {
    }
}
