package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderTests {
    
    @Test
    @DisplayName("Test the boulder being pushed down")
    public void testMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_boulderTest_testMovement", "c_movementTest_testMovementDown");
        EntityResponse initBoulder = getEntities(res, "boulder").get(0);

        // create the expected result
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(2, 3), false);

        // move player downward to push boulder
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);

        // assert after movement
        assertEquals(expectedBoulder, actualBoulder);
    }

    @Test
    @DisplayName("Test the boulder not mvoing due to being pushed into a wall")
    public void testInvalidBoulderMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_boulderTest_testInvalidMovement", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(res).get();
        EntityResponse initBoulder = getEntities(res, "boulder").get(0);
        
        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(2, 2), false);
    
        // move player downward to attempt to push boulder
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);

        // assert after invalid movement
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test player trying to move more than one boulder")
    public void testMoveTwoBoulders() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_boulderTest_testMovingTwoBoulders", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(res).get();
        EntityResponse initBoulder1 = getEntities(res, "boulder").get(0);
        EntityResponse initBoulder2 = getEntities(res, "boulder").get(1);
        
        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);
        EntityResponse expectedBoulder1 = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(2, 2), false);
        EntityResponse expectedBoulder2 = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(2, 3), false);
    
        // move player downward to attempt to push boulders
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder1 = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualBoulder2 = getEntities(actualDungonRes, "boulder").get(1);

        // assert after invalid movement
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder1, actualBoulder1);
        assertEquals(expectedBoulder2, actualBoulder2);
    }

}
