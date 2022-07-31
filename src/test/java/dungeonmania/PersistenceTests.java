package dungeonmania;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getGoals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersistenceTests {
    @Test
    @DisplayName("Test basic movement of spiders")
    public void spiderMovementSaveTest() {
        Date timestamp = new Date();
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_basicMovement", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();

        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x+1, y));
        movementTrajectory.add(new Position(x+1, y+1));
        movementTrajectory.add(new Position(x  , y+1));
        movementTrajectory.add(new Position(x-1, y+1));
        movementTrajectory.add(new Position(x-1, y));
        movementTrajectory.add(new Position(x-1, y-1));


        // Assert Circular Movement of Spider
        for (int i = 0; i <= 19; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
            dmc.saveGame("test_save ".concat(timestamp.toString()));
            dmc.loadGame("test_save ".concat(timestamp.toString()));

            nextPositionElement++;
            if (nextPositionElement == 8){
                nextPositionElement = 0;
            }
        }
    }

    @Test
    @DisplayName("Test that a mercenary can be bribed in a certain radius save test")
    public void testMercenaryBribe() {
        /*
         *  exit   
         * player  trea  trea  trea  [  ]  [  ]  [  ]  [Merc]
         * 
         * bribe amount 3 radius = 1
         *                          
         */                     
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_mercenaryTest_bribeMercenary", "c_mercenaryTest_bribeMercenary");
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

        // Assert that player has 3 treasure in the inventory
        assertEquals(3, getInventory(actualDungonRes, "treasure").size());

        // Player bribes mercenary
        String mercenaryId = getEntities(actualDungonRes, "mercenary").get(0).getId();
        actualDungonRes =  assertDoesNotThrow(() ->  dmc.interact(mercenaryId));

        assertEquals(0, getInventory(actualDungonRes, "treasure").size());

        Date timestamp = new Date();
        dmc.saveGame("test_save ".concat(timestamp.toString()));
        dmc.loadGame("test_save ".concat(timestamp.toString()));


        String mercenaryId2 = getEntities(actualDungonRes, "mercenary").get(0).getId();
        assertThrows(InvalidActionException.class , () ->  dmc.interact(mercenaryId2));

        // Assert that treasure is used
        assertEquals(0, getInventory(actualDungonRes, "treasure").size());
    }

    @Test
    @DisplayName("Integration test 3 save test")
    public void integrationTest3() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_integrationTest_3", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(res).get();

        // Pick up treasure and bomb
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "treasure").size());
        assertEquals(1, getInventory(res, "bomb").size());


        res = dmc.tick(Direction.RIGHT);

        // Drop bomb
        String bombId = getInventory(res, "bomb").get(0).getId();
        res = dmc.tick(bombId);
        assertEquals(0, getInventory(res, "bomb").size());

        Date timestamp = new Date();
        dmc.saveGame("test_save ".concat(timestamp.toString()));
        dmc.loadGame("test_save ".concat(timestamp.toString()));

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        Position expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 3), false).getPosition();
        Position actualPlayer = getPlayer(res).get().getPosition();
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(12, getEntities(res, "wall").size());
    }

    @Test
    @DisplayName("Test saves name correctly")
    public void testSavesNameCorrectly() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("d_integrationTest_3", "c_movementTest_testMovementDown");
        controller.tick(Direction.RIGHT);
        controller.saveGame("SAVE NAME TEST");
        assertTrue(controller.allGames().contains("SAVE NAME TEST"));
    }

    @Test
    @DisplayName("Can move through enemies")
    public void testCanPlayerMoveThroughWithInvisibility() throws InvalidActionException, IllegalArgumentException {
        DungeonManiaController controller = new DungeonManiaController();
        potionPickupAndUseMercenarySequence(controller, "c_battleTests_basicMercenaryPlayerDies");
        DungeonResponse current = controller.tick(Direction.RIGHT);

        EntityResponse actualPlayer = getPlayer(current).get();
        EntityResponse expectedPlayer = new EntityResponse(actualPlayer.getId(), actualPlayer.getType(), new Position(3, 1), false);

        Date timestamp = new Date();
        controller.saveGame("test_save ".concat(timestamp.toString()));
        controller.loadGame("test_save ".concat(timestamp.toString()));

        assertEquals(expectedPlayer, actualPlayer);

        current = controller.tick(Direction.RIGHT);
        
        actualPlayer = getPlayer(current).get();
        expectedPlayer = new EntityResponse(actualPlayer.getId(), actualPlayer.getType(), new Position(4, 1), false);

        assertEquals(expectedPlayer, actualPlayer);

        assertEquals(":exit", getGoals(current));  
    }

    private static DungeonResponse potionPickupAndUseMercenarySequence(DungeonManiaController controller, String configFile) throws InvalidActionException, IllegalArgumentException {
        /*
         *  exit   wall  wall  wall wall
         * player  [IP]  [  ]  merc [  ] wall
         *  wall   wall  wall  wall wall
         */
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicMercenaryInvincibilityPotion", configFile);
        int mercenaryCount = countEntityOfType(initialResponse, "mercenary");
        
        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, mercenaryCount);
        DungeonResponse potionPickupResponse = controller.tick(Direction.RIGHT);
        
        EntityResponse actualPlayer = getPlayer(potionPickupResponse).get();
        EntityResponse expectedPlayer = new EntityResponse(actualPlayer.getId(), actualPlayer.getType(), new Position(1, 1), false);

        assertEquals(expectedPlayer, actualPlayer);

        EntityResponse actualMercenary = getEntities(potionPickupResponse, "mercenary").get(0);
        EntityResponse expectedMercenary = new EntityResponse(actualMercenary.getId(), actualMercenary.getType(), new Position(2, 1), true);

        assertEquals(expectedMercenary, actualMercenary);

        ItemResponse potion = getInventory(potionPickupResponse, "invincibility_potion").get(0);
        DungeonResponse current = controller.tick(potion.getId());

        actualPlayer = getPlayer(current).get();
        expectedPlayer = new EntityResponse(actualPlayer.getId(), actualPlayer.getType(), new Position(1, 1), false);

        assertEquals(expectedPlayer, actualPlayer);

        current = controller.tick(Direction.RIGHT);

        actualPlayer = getPlayer(current).get();
        expectedPlayer = new EntityResponse(actualPlayer.getId(), actualPlayer.getType(), new Position(2, 1), false);

        assertEquals(expectedPlayer, actualPlayer);
        
        return current;
    }
}
