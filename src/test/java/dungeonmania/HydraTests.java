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
        // Code would use seed 1 (number of ticks)
        Random rand = new Random(1);
        int randomNumber = rand.nextInt(5) + 1;

        HydraPos = getEntities(res, "hydra").get(0).getPosition();
        Position expectedPosition = cardinallyAdjacentPositions.get(randomNumber);

        assertEquals(HydraPos, expectedPosition);

        // Code would use seed 2
        rand.setSeed(2);
        randomNumber = rand.nextInt(5) + 1;

        HydraPos = getEntities(res, "hydra").get(0).getPosition();
        expectedPosition = cardinallyAdjacentPositions.get(randomNumber);

        assertEquals(HydraPos, expectedPosition);
    }


}
