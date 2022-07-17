package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getPlayer;

import static dungeonmania.TestUtils.getEntities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PortalTests {
    @Test
    @DisplayName("Player teleports normally")
    public void PlayerTeleportsNormally() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_PortalTest_basicTeleport", "c_movementTest_testMovementDown");
        // EntityResponse initPlayer = getPlayer(res).get();

        // move right to get into portal
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        // player ends up to the right of the next portal
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(pos, getEntities(res, "portal").get(1).getPosition().translateBy(Direction.DOWN));


    }

    @Test
    @DisplayName("Player cannot teleports")
    public void PlayerCannotTeleport() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_cannotTeleport", "c_movementTest_testMovementDown");
        // EntityResponse initPlayer = getPlayer(res).get();

        // move right to get into portal
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        // player ends up to the right of the next portal
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(pos, getEntities(res, "portal").get(0).getPosition().translateBy(Direction.UP));



    }
    
}
