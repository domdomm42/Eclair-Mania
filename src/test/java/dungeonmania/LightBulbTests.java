package dungeonmania;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getPlayer;

import static dungeonmania.TestUtils.getEntities;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class LightBulbTests {

    @Test
    @DisplayName("Can turn on OR normally")
    public void testBasicTurnOn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LightBulbTest_basicTurnOn", "c_midnightArmourTest");

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        
        // String type = getEntities(res, "light_bulb_off").get(0).getType();
        assertEquals(0, getEntities(res, "light_bulb_off").size());
    }
    @Test
    @DisplayName("Turn on using AND")
    public void testAndTurnOn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LightBulbAnd", "c_midnightArmourTest");

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        assertEquals(0, getEntities(res, "light_bulb_off").size());
    }

    @Test
    @DisplayName("Turn on using XOR")
    public void testTurnOnUsingXOR() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LightBulbTest_basicXOR", "c_midnightArmourTest");

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(0, getEntities(res, "light_bulb_off").size());

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, getEntities(res, "light_bulb_off").size());



    }

    @Test
    @DisplayName("Cannot turn on cause not same tick co_and")
    public void testCoAndCannotTurnOn() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LightBulbTest_cannotCOAnd", "c_midnightArmourTest");

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(1, getEntities(res, "light_bulb_off").size());

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, getEntities(res, "light_bulb_off").size());


    }



    
}
