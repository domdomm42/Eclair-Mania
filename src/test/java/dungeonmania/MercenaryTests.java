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


public class MercenaryTests {
    @Test
    @DisplayName("Test basic Mercenary movement")
    public void testBasicMercenaryMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_mercenaryTest_basicMovement", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initMercenary = getEntities(initDungonRes, "mercenary").get(0);
        
        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);
        EntityResponse expectedMercenary = new EntityResponse(initMercenary.getId(), initMercenary.getType(), new Position(3, 1), false);


        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualMercenary = getEntities(actualDungonRes, "mercenary").get(0);
        
        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedMercenary, actualMercenary);

        // move player downwards again
        actualDungonRes = dmc.tick(Direction.DOWN);
        actualPlayer = getPlayer(actualDungonRes).get();
        actualMercenary = getEntities(actualDungonRes, "mercenary").get(0);

        // create expected result 
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 3), false);
        expectedMercenary = new EntityResponse(initMercenary.getId(), initMercenary.getType(), new Position(2, 1), false);

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedMercenary, actualMercenary);

        // move player downwards again
        actualDungonRes = dmc.tick(Direction.DOWN);
        actualPlayer = getPlayer(actualDungonRes).get();
        actualMercenary = getEntities(actualDungonRes, "mercenary").get(0);

        // create expected result 
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 4), false);
        expectedMercenary = new EntityResponse(initMercenary.getId(), initMercenary.getType(), new Position(2, 2), false);

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedMercenary, actualMercenary);

    }   

    @DisplayName("Test that a mercenary would stop if it cannot move closer to player (if it is blocked by a wall and there is no direct path)") 
    public void testMercenaryStopAtWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_mercenaryTest_enclosedMercenary", "c_movementTest_testMovementDown");
        EntityResponse initMercenary = getEntities(initDungonRes, "mercenary").get(0);

        // Expected result mercenary moves to left
        EntityResponse expectedMercenary = new EntityResponse(initMercenary.getId(), initMercenary.getType(), new Position(3, 4), false);
        
        // Move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualMercenary = getEntities(actualDungonRes, "mercenary").get(0);

        // Assert position of mercenary
        assertEquals(expectedMercenary, actualMercenary);

        // Move Player down again
        actualDungonRes = dmc.tick(Direction.DOWN);
        actualMercenary = getEntities(actualDungonRes, "mercenary").get(0);

        // Assert that the mercenary doesn't move 
        assertEquals(expectedMercenary, actualMercenary);

        
    }

    @DisplayName("Test that a mercenary can be bribed in a certain radius")
    public void testMercenaryBribe() {
        
    }

    @DisplayName("Test a mercenary isn't automatically bribed when it is in the radius") 
    public void testMercenaryAutomaticBribeCase() {
    }


    @DisplayName("Test a Mercenary Ally Movement, that it follows player correctly")
    public void testMercenaryAllyMovement() {

    }

    @DisplayName("Test that no new mercenaries spawn in the game once dungeon has been created")
    public void testMercenaryNoNewSpawn() {

    }
    // TEST SAME MOVEMENT CONSTRAINTS AS PLAYER --> TAKE PLAYER CONSTRAINT TESTS AND CHANGE THEM TO MERCENARY


}
