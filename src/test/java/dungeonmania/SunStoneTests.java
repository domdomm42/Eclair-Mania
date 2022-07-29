package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
public class SunStoneTests {
    @Test
    @DisplayName("Test that the player picks up Sun Stone")
    public void testSunStonePickUp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_SunStoneTests_basicSunStonePickUp", "c_SunStoneTest_testSunStonePickUp");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        
        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);

        // move downward again
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        assertEquals(expectedPlayer, actualPlayer);

        // check if treasure is in the inventory
        assertEquals(1, getInventory(actualDungonRes, "sun_stone").size());

    }

    @Test
    @DisplayName("Test that if a player can open door if a Sun Stone is in inventory")
    public void testSunStoneDoorOpen() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_useSunStoneWalkThroughOpenDoor", "c_SunStoneTest_testSunStonePickUp");

        // Pick up Sun Stone
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(1, getInventory(res, "sun_stone").size());

        // walk through door and check Sun Stone is still there
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "sun_stone").size());
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());

    }

    @Test
    @DisplayName("Test that if a Sun Stone can interchange with treasure when building shield")
    public void testSunStoneBuildShield() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_SunStoneTest_SunStoneShieldTest", "c_SunStoneTest_testSunStonePickUp");

        // pick up items
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, getInventory(res, "wood").size());
        assertEquals(1, getInventory(res, "sun_stone").size());

        dmc.build("shield");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getInventory(res, "wood").size());
        assertEquals(1, getInventory(res, "sun_stone").size());
        assertEquals(1, getInventory(res, "shield").size());

    }

    @Test
    @DisplayName("Test that a Sun Stone cannot bribe a mercenary")
    public void testSunStoneBribe() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_SunStoneTest_SunStoneShieldBribeTest", "c_SunStoneTest_testSunStonePickUp");
        EntityResponse initMercenary = getEntities(initDungonRes, "mercenary").get(0);
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // Get expected positions after 3 movements to the right
        // Mercenary will be interactable when next to each other
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 2), false);
        EntityResponse expectedMercenary = new EntityResponse(initMercenary.getId(), initMercenary.getType(), new Position(5, 2), true);

        // Move player to right 3 times
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);

        // Get actual positions of Mercenary and Player
        EntityResponse actualMercenary = getEntities(actualDungonRes, "mercenary").get(0);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // Assert position of player and mercenary
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedMercenary, actualMercenary);

        // Assert that player has a Sun Stone in the inventory
        assertEquals(1 , getInventory(actualDungonRes, "sun_stone").size());

        // Player attempts to bribe mercenary with Sun Stone --> results in an exception
        String mercenaryId = getEntities(actualDungonRes, "mercenary").get(0).getId();
        assertThrows(InvalidActionException.class, () ->  dmc.interact(mercenaryId));

        // Assert that Sun Stone is not used
        assertEquals(1, getInventory(actualDungonRes, "sun_stone").size());

    }

}