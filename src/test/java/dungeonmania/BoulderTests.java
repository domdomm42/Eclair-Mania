package dungeonmania;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderTests {
    
    @Test
    @DisplayName("Test the boulder being pushed down")
    public void testMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_boulderTest_testMovement", "c_movementTest_testMovementDown");
        EntityResponse initBoulder = getEntities(initDungonRes, "boulder").get(0);

        // create the expected result
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(2, 3), false);

        // move player downward to push boulder
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);

        // assert after movement
        assertEquals(expectedBoulder, actualBoulder);
    }

    @Test
    @DisplayName("Test the boulder not moving due to being pushed into a wall")
    public void testInvalidBoulderMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_boulderTest_testInvalidMovement", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities(initDungonRes, "boulder").get(0);
        
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
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder1 = getEntities(initDungonRes, "boulder").get(0);
        EntityResponse initBoulder2 = getEntities(initDungonRes, "boulder").get(1);
        
        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);
        EntityResponse expectedBoulder1 = new EntityResponse(initBoulder1.getId(), initBoulder1.getType(), new Position(2, 2), false);
        EntityResponse expectedBoulder2 = new EntityResponse(initBoulder2.getId(), initBoulder2.getType(), new Position(2, 3), false);
    
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
