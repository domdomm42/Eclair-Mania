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

public class ExitTests {
    @Test
    @DisplayName("Test if player goes through exit, the puzzle is complete")
    public void testExitPuzzleComplete() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_exitTests_basicExit", "c_battleTests_basicMercenaryMercenaryDies");
        EntityResponse initPlayer = getPlayer(res).get();

        // Expected player to the right
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);

        // Move to the right
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // Assert movement
        assertEquals(expectedPlayer, actualPlayer);

        // Move again to the right and check if player has exited the game
        actualDungonRes = dmc.tick(Direction.RIGHT);
        assertEquals("", getGoals(actualDungonRes));

    }
}
