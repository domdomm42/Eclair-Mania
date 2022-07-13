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
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PortalTests {
    @Test
    @DisplayName("Player teleports normally")
    public void PlayerTeleportsNormally() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_basicportal", "c_movementTest_testMovementDown");

        // move right to get into portal
        res = dmc.tick(Direction.RIGHT);

        // player ends up to the right of the next portal
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(pos, getEntities(res, "portal").get(1).getPosition());


    }

    @Test
    @DisplayName("Player cannot teleports")
    public void PlayerCannotTeleport() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_cannotTeleport", "c_movementTest_testMovementDown");

        // move right to get into portal
        Position pos = getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);

        // player ends up at the same spot
        assertEquals(pos, getEntities(res, "portal").get(0).getPosition());


    }
    
}
