package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getGoals;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class FloorSwitchTests {

    @Test
    @DisplayName("Test player pushes boulder on switch")
    public void testPushBoulderOnSwitch() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "d_floorSwitchGoal");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities(initDungonRes, "boulder").get(0);
        EntityResponse initSwitch = getEntities(initDungonRes, "switch").get(0);

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(1, 3), false);
        EntityResponse expectedSwitch = new EntityResponse(initSwitch.getId(), initSwitch.getType(), new Position(1, 3), false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualSwitch = getEntities(actualDungonRes, "switch").get(0);

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedSwitch, actualSwitch);
        assertFalse(getGoals(actualDungonRes).contains(":boulders"));
    }

    @Test
    @DisplayName("Test player pushes boulder on switch and pushes it off again")
    public void testPushBoulderOnSwitchAndOff() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "d_floorSwitchGoal");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities(initDungonRes, "boulder").get(0);
        EntityResponse initSwitch = getEntities(initDungonRes, "switch").get(0);

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 3), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(1, 4), false);
        EntityResponse expectedSwitch = new EntityResponse(initSwitch.getId(), initSwitch.getType(), new Position(1, 3), false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualSwitch = getEntities(actualDungonRes, "switch").get(0);

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedSwitch, actualSwitch);
        assertTrue(getGoals(actualDungonRes).contains(":boulders"));
        
    }
}
