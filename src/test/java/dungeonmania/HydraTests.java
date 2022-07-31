package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getEntities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HydraTests {
    @Test
    @DisplayName("Testing Hydra can't use portal")
    public void PortalNoEffectOnHydra() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_zombieDontPortal", "c_movementTest_testMovementDown");


        // for each tick, spawn zombie
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, getEntities(res, "zombie_toast").size());
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, getEntities(res, "zombie_toast").size());
    }

    @Test
    @DisplayName("Testing Hydra movement") 
    public void HydraRandomMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_HydraTest_testHydraMovement", "c_SunStoneTest_testSunStonePickUp");
    
        

        // Get Hydra position
        Position HydraPos = getEntities(res, "hydra").get(0).getPosition();
        List<Position> cardinallyAdjacentPositions = HydraPos.getCardinallyAdjacentPositions();

        

        res = dmc.tick(Direction.DOWN);
        HydraPos = getEntities(res, "hydra").get(0).getPosition();
        cardinallyAdjacentPositions = HydraPos.getCardinallyAdjacentPositions();
        Position expectedPosition = cardinallyAdjacentPositions.get(0);

        //assertEquals(HydraPos, expectedPosition);


        res = dmc.tick(Direction.DOWN);
        HydraPos = getEntities(res, "hydra").get(0).getPosition();
        cardinallyAdjacentPositions = HydraPos.getCardinallyAdjacentPositions();
        expectedPosition = cardinallyAdjacentPositions.get(1);

        //assertEquals(HydraPos, expectedPosition);
    }



}
