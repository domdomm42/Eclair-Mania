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

public class SceptreTests {
    
    @Test
    @DisplayName("Player attempts to build Sceptre")
    public void testAttemptMakeSceptre() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_sceptreTest_build", "c_sceptreTest");

        DungeonResponse res = dmc.tick(Direction.DOWN);

        // build Sceptre with nothing
        assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));
        assertEquals(0, getInventory(res, "sceptre").size());
    }

    @Test
    @DisplayName("Player builds Spectre from 1 wood + 1 key + 1 sun stone")
    public void testMakeSceptre1() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_build", "c_sceptreTest");

        // pick up items
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(1, getInventory(res, "wood").size());
        assertEquals(1, getInventory(res, "key").size());
        assertEquals(1, getInventory(res, "sun_stone").size());

        res = dmc.build("sceptre");
        assertEquals(1, getInventory(res, "sceptre").size());
        assertEquals(0, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "key").size());
        assertEquals(0, getInventory(res, "sun_stone").size());
        
    }

    @Test
    @DisplayName("Player builds Spectre from 1 wood + 1 treasure + 1 sun stone")
    public void testMakeSceptre2() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_sceptreTest_build", "c_sceptreTest");

        // pick up items
        DungeonResponse res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(1, getInventory(res, "wood").size());
        assertEquals(1, getInventory(res, "treasure").size());
        assertEquals(1, getInventory(res, "sun_stone").size());

        res = dmc.build("sceptre");
        
        assertEquals(0, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "treasure").size());
        assertEquals(0, getInventory(res, "sun_stone").size());
        assertEquals(1, getInventory(res, "sceptre").size());   
    }

    @Test
    @DisplayName("Player builds Spectre from 2 arrows + 1 treasure + 1 sun stone")
    public void testMakeSceptre3() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_sceptreTest_build", "c_sceptreTest");

        // pick up items
        DungeonResponse res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(2, getInventory(res, "arrow").size());
        assertEquals(1, getInventory(res, "treasure").size());
        assertEquals(1, getInventory(res, "sun_stone").size());

        res = dmc.build("sceptre");
        
        assertEquals(0, getInventory(res, "arrow").size());
        assertEquals(0, getInventory(res, "treasure").size());
        assertEquals(0, getInventory(res, "sun_stone").size());
        assertEquals(1, getInventory(res, "sceptre").size());   
    }

    @Test
    @DisplayName("Player builds Spectre from 2 arrows + 1 key + 1 sun stone")
    public void testMakeSceptre4() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_sceptreTest_build", "c_sceptreTest");

        // pick up items
        DungeonResponse res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(2, getInventory(res, "arrow").size());
        assertEquals(1, getInventory(res, "key").size());
        assertEquals(1, getInventory(res, "sun_stone").size());

        res = dmc.build("sceptre");
        
        assertEquals(0, getInventory(res, "arrow").size());
        assertEquals(0, getInventory(res, "key").size());
        assertEquals(0, getInventory(res, "sun_stone").size());
        assertEquals(1, getInventory(res, "sceptre").size()); 
    }

    @Test
    @DisplayName("Player mind controls mercenary for 5 ticks")
    public void testMindControlMercenary() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_sceptreTest_mercenary", "c_sceptreTest");
        EntityResponse initMercenary = getEntities(initDungonRes, "mercenary").get(0);

        // pick up items
        DungeonResponse res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        assertEquals(2, getInventory(res, "arrow").size());
        assertEquals(1, getInventory(res, "key").size());
        assertEquals(1, getInventory(res, "sun_stone").size());

        res = dmc.build("sceptre");
        
        assertEquals(0, getInventory(res, "arrow").size());
        assertEquals(0, getInventory(res, "key").size());
        assertEquals(0, getInventory(res, "sun_stone").size());
        assertEquals(1, getInventory(res, "sceptre").size()); 

        res = dmc.interact(initMercenary.getId());

        res = dmc.tick(Direction.UP);
        assertEquals(1, getEntities(res, "mercenary").size());

        res = dmc.tick(Direction.DOWN);
        assertEquals(1, getEntities(res, "mercenary").size());

        res = dmc.tick(Direction.UP);
        assertEquals(1, getEntities(res, "mercenary").size());

        // mercenary is no longer mind controlled
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, getEntities(res, "mercenary").size());

        
        
        
    }
}
