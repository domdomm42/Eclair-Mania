package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class WallTests {
    @Test
    @DisplayName("Testing the wall stops movement of player")
    public void testPlayerMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_wallTest_playerMovement", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungeonRes).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 1), false);

        DungeonResponse actualDungeonResponse = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungeonResponse).get();

        assertEquals(expectedPlayer, actualPlayer);

        actualDungeonResponse = dmc.tick(Direction.UP);
        actualPlayer = getPlayer(actualDungeonResponse).get();

        assertEquals(expectedPlayer, actualPlayer);

        actualDungeonResponse = dmc.tick(Direction.LEFT);
        actualPlayer = getPlayer(actualDungeonResponse).get();

        assertEquals(expectedPlayer, actualPlayer);

        actualDungeonResponse = dmc.tick(Direction.RIGHT);
        actualPlayer = getPlayer(actualDungeonResponse).get();

        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Testing the wall enemy interactions")
    public void testEnemyMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_wallTest_enemyMovement", "c_movementTest_testMovementDown");

        List<EntityResponse> initSpider = getEntities(initDungeonRes, "spider");
        List<EntityResponse> initMercenary = getEntities(initDungeonRes, "mercenary");

        DungeonResponse actualDungeonResponse = dmc.tick(Direction.DOWN);

        EntityResponse expectedSpider = new EntityResponse(initSpider.get(0).getId(), initSpider.get(0).getType(), new Position(10, 9), false);
        EntityResponse expectedMercenary = new EntityResponse(initMercenary.get(0).getId(), initMercenary.get(0).getType(), new Position(20, 20), true);

        EntityResponse actualSpider = getEntities(actualDungeonResponse, "spider").get(0);
        EntityResponse actualMercenary = getEntities(actualDungeonResponse, "mercenary").get(0);

        assertEquals(expectedSpider, actualSpider);
        assertEquals(expectedMercenary, actualMercenary);

        actualDungeonResponse = dmc.tick(Direction.DOWN);

        expectedSpider = new EntityResponse(initSpider.get(0).getId(), initSpider.get(0).getType(), new Position(11, 9), false);
        expectedMercenary = new EntityResponse(initMercenary.get(0).getId(), initMercenary.get(0).getType(), new Position(20, 20), true);

        actualSpider = getEntities(actualDungeonResponse, "spider").get(0);
        actualMercenary = getEntities(actualDungeonResponse, "mercenary").get(0);

        assertEquals(expectedSpider, actualSpider);
        assertEquals(expectedMercenary, actualMercenary);

        actualDungeonResponse = dmc.tick(Direction.DOWN);

        expectedSpider = new EntityResponse(initSpider.get(0).getId(), initSpider.get(0).getType(), new Position(11, 10), false);
        expectedMercenary = new EntityResponse(initMercenary.get(0).getId(), initMercenary.get(0).getType(), new Position(20, 20), true);

        actualSpider = getEntities(actualDungeonResponse, "spider").get(0);
        actualMercenary = getEntities(actualDungeonResponse, "mercenary").get(0);

        assertEquals(expectedSpider, actualSpider);
        assertEquals(expectedMercenary, actualMercenary);
        
        actualDungeonResponse = dmc.tick(Direction.DOWN);

        expectedSpider = new EntityResponse(initSpider.get(0).getId(), initSpider.get(0).getType(), new Position(11, 11), false);
        expectedMercenary = new EntityResponse(initMercenary.get(0).getId(), initMercenary.get(0).getType(), new Position(20, 20), true);

        actualSpider = getEntities(actualDungeonResponse, "spider").get(0);
        actualMercenary = getEntities(actualDungeonResponse, "mercenary").get(0);

        assertEquals(expectedSpider, actualSpider);
        assertEquals(expectedMercenary, actualMercenary);
    }
}
