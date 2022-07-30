package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getValueFromConfigFile;
import static dungeonmania.TestUtils.getPlayer;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.StaticEntities.LogicalEntities.SwitchDoor;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


import dungeonmania.util.Direction;

public class SwitchDoorTests {
    @Test
    @DisplayName("Test OR switch door opens when surrounding switch is activated")
    public void SimpleSwitchDoorTests() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SwitchDoorTest_basicOR", "c_bombTest_placeBombRadius2");

        // Activate Switch
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // go to door without pushing boulder
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        Position actualPlayer = getPlayer(res).get().getPosition();
        Position pos = getEntities(res, "switch_door").get(0).getPosition();
        
        assertEquals(pos, actualPlayer);

    }

    @Test
    @DisplayName("Test AND switch door opens when surrounding switch is activated")
    public void ANDSimpleSwitchDoorTests() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SwitchDoorTests_testSimpleAND", "c_bombTest_placeBombRadius2");

        // Activate Switch
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // go to door without pushing boulder
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);

        Position actualPlayer = getPlayer(res).get().getPosition();
        Position pos = getEntities(res, "switch_door").get(0).getPosition();
        
        assertEquals(pos, actualPlayer);

    }

    @Test
    @DisplayName("Test 1 switch enter xor door")
    public void XORSimpleSwitchDoorTests() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SwitchDoorTest_testSimpleXOR", "c_bombTest_placeBombRadius2");

        // Activate Switch
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // go to door without pushing boulder
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);

        Position actualPlayer = getPlayer(res).get().getPosition();
        Position pos = getEntities(res, "switch_door").get(0).getPosition();
        
        assertEquals(pos, actualPlayer);

    }




    @Test
    @DisplayName("Test 2 switches cant enter switch door")
    public void XORCannotSimpleSwitchDoorTests() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SwitchDoorTest_testSimpleXOR", "c_bombTest_placeBombRadius2");

        // Activate Switch
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // go to door without pushing boulder
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);

        Position actualPlayer = getPlayer(res).get().getPosition();
        Position pos = getEntities(res, "switch_door").get(0).getPosition();
        
        assertNotEquals(pos, actualPlayer);

    }


    

    @Test
    @DisplayName("Test CANNOT enter and switch door")
    public void CannotEnterANDSwitchDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SwitchDoorTests_testSimpleAND", "c_bombTest_placeBombRadius2");

        // Activate Switch
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // go to door without pushing boulder
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        Position actualPlayer = getPlayer(res).get().getPosition();
        Position pos = getEntities(res, "switch_door").get(0).getPosition();
        
        assertNotEquals(pos, actualPlayer);

    }

    @Test
    @DisplayName("Test AND switch door opens then closes user cant enter")
    public void BasicWireTest() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_WireTest_basicWireTest", "c_bombTest_placeBombRadius2");

        // Activate Switch
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // go to door without pushing boulder
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        Position actualPlayer = getPlayer(res).get().getPosition();
        Position pos = getEntities(res, "switch_door").get(0).getPosition();
        
        assertEquals(pos, actualPlayer);

    }






}
