package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import dungeonmania.exceptions.InvalidActionException;

public class AssassinTests {
    @Test
    @DisplayName("Test basic assassin movement")
    public void testBasicAssassinMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_assassinTest_basicMovement", "c_SunStoneTest_testSunStonePickUp");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initAssassin = getEntities(initDungonRes, "assassin").get(0);
        
        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);
        EntityResponse expectedAssassin = new EntityResponse(initAssassin.getId(), initAssassin.getType(), new Position(3, 1), true);


        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualAssassin = getEntities(actualDungonRes, "assassin").get(0);
        
        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedAssassin, actualAssassin);

        // move player downwards again
        actualDungonRes = dmc.tick(Direction.DOWN);
        actualPlayer = getPlayer(actualDungonRes).get();
        actualAssassin = getEntities(actualDungonRes, "assassin").get(0);

        // create expected result 
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 3), false);
        expectedAssassin = new EntityResponse(initAssassin.getId(), initAssassin.getType(), new Position(2, 1), true);

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedAssassin, actualAssassin);

        // move player downwards again
        actualDungonRes = dmc.tick(Direction.DOWN);
        actualPlayer = getPlayer(actualDungonRes).get();
        actualAssassin = getEntities(actualDungonRes, "assassin").get(0);

        // create expected result 
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 4), false);
        expectedAssassin = new EntityResponse(initAssassin.getId(), initAssassin.getType(), new Position(1, 1), true);

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedAssassin, actualAssassin);

    }   


    @Test
    @DisplayName("Test that a assassin can be bribed in a certain radius")
    public void testMercenaryBribe() {
        /*
         *  exit   
         * player  trea  trea  trea  [  ]  [  ]  [  ]  [Merc]
         * 
         * bribe amount 3 radius = 1
         *                          
         */                     
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_assassinTest_bribeAssassin", "c_SunStoneTest_testSunStonePickUp");
        EntityResponse initassassin = getEntities(initDungonRes, "assassin").get(0);
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // Get expected positions after 3 movements to the right
        // assassin will be interactable when next to each other
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 2), false);
        EntityResponse expectedAssassin = new EntityResponse(initassassin.getId(), initassassin.getType(), new Position(5, 2), true);

        // Move player to right 3 times
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);

        // Get actual positions of assassin and Player
        EntityResponse actualMercenary = getEntities(actualDungonRes, "assassin").get(0);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // Assert position of player and assassin
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedAssassin, actualMercenary);

        // Assert that player has 3 treasure in the inventory
        assertEquals(3, getInventory(actualDungonRes, "treasure").size());

        // Player bribes assassin
        String assassinId = getEntities(actualDungonRes, "assassin").get(0).getId();
        actualDungonRes =  assertDoesNotThrow(() ->  dmc.interact(assassinId));

        // Assert that treasure is used
        assertEquals(0, getInventory(actualDungonRes, "treasure").size());

    }

    @Test
    @DisplayName("Test a assassin can't be bribed outside radius") 
    public void testMercenaryAutomaticBribeCase() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_assassinTest_bribeAssassin", "c_SunStoneTest_testSunStonePickUp");

        // Player attempts to bribes assassin outside of radius
        String assassinId = getEntities(initDungonRes, "assassin").get(0).getId();
        assertThrows(InvalidActionException.class, () ->  dmc.interact(assassinId));
        
    }

    
    @Test
    @DisplayName("Test a assassin bribe fail") 
    public void testAssasinBribeFail() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_assassinTest_bribeAssassin", "c_AssassinTest_testAssasinBribeFail");

        EntityResponse initassassin = getEntities(initDungonRes, "assassin").get(0);
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // Get expected positions after 3 movements to the right
        // assassin will be interactable when next to each other
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 2), false);
        EntityResponse expectedAssassin = new EntityResponse(initassassin.getId(), initassassin.getType(), new Position(5, 2), true);

        // Move player to right 3 times
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);

        // Get actual positions of assassin and Player
        EntityResponse actualMercenary = getEntities(actualDungonRes, "assassin").get(0);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // Assert position of player and assassin
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedAssassin, actualMercenary);

        // Assert that player has 3 treasure in the inventory
        assertEquals(3, getInventory(actualDungonRes, "treasure").size());

        // Player bribes assassin
        String assassinId = getEntities(actualDungonRes, "assassin").get(0).getId();
        actualDungonRes =  assertDoesNotThrow(() ->  dmc.interact(assassinId));

        // Assert that treasure is used
        assertEquals(0, getInventory(actualDungonRes, "treasure").size());

        // Assassin should remain enemy and thus should be interactable but should raise exception
        assertThrows(InvalidActionException.class, () ->  dmc.interact(assassinId));
    }

}
