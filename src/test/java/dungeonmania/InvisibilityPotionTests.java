package dungeonmania;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.countEntityOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getGoals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dungeonmania.TestUtils.getInventory;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class InvisibilityPotionTests {
    
    @Test
    @DisplayName("Can move through enemies")
    private void testCanPlayerMoveThroughWithInvisibility() throws InvalidActionException, IllegalArgumentException {
        DungeonManiaController controller = new DungeonManiaController();
        potionPickupAndUseMercenarySequence(controller, "c_battleTests_basicMercenaryPlayerDies");
        DungeonResponse current = controller.tick(Direction.RIGHT);

        EntityResponse actualPlayer = getPlayer(current).get();
        EntityResponse expectedPlayer = new EntityResponse(actualPlayer.getId(), actualPlayer.getType(), new Position(3, 1), false);

        assertEquals(expectedPlayer, actualPlayer);

        current = controller.tick(Direction.RIGHT);
        
        actualPlayer = getPlayer(current).get();
        expectedPlayer = new EntityResponse(actualPlayer.getId(), actualPlayer.getType(), new Position(4, 1), false);

        assertEquals(expectedPlayer, actualPlayer);

        assertEquals("", getGoals(current));  
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
        EntityResponse expectedMercenary = new EntityResponse(actualMercenary.getId(), actualMercenary.getType(), new Position(2, 1), false);

        assertEquals(expectedMercenary, actualMercenary);

        ItemResponse potion = getInventory(potionPickupResponse, "invisibility_potion").get(0);
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
