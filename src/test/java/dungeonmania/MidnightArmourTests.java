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

public class MidnightArmourTests {
    
    @Test
    @DisplayName("Player attempts to build Midnight Armour")
    public void testAttemptMakeMidnightArmour() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_midnightArmourTest_buildNoZombie", "c_midnightArmourTest");

        DungeonResponse res = dmc.tick(Direction.DOWN);

        // build Midnight Armour with nothing
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
        assertEquals(0, getInventory(res, "midnight_armour").size());
    }

    @Test
    @DisplayName("Player builds Spectre from 1 sword + 1 sun stone with no zombies in area")
    public void testMakeMidnightArmourNoZombie() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest_buildNoZombie", "c_midnightArmourTest");

        // pick up items
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(1, getInventory(res, "sword").size());
        assertEquals(1, getInventory(res, "sun_stone").size());

        res = dmc.build("midnight_armour");
        assertEquals(1, getInventory(res, "midnight_armour").size());
        assertEquals(0, getInventory(res, "sword").size());
        assertEquals(0, getInventory(res, "sun_stone").size());   
    }

    @Test
    @DisplayName("Player builds Spectre from 1 sword + 1 sun stone with zombies in area")
    public void testMakeMidnightArmourWithZombie() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest_buildWithZombie", "c_midnightArmourTest");

        // pick up items
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(1, getInventory(res, "sword").size());
        assertEquals(1, getInventory(res, "sun_stone").size());

        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
        assertEquals(0, getInventory(res, "midnight_armour").size());
        assertEquals(1, getInventory(res, "sword").size());
        assertEquals(1, getInventory(res, "sun_stone").size());
        
    }

}
