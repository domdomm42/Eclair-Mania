package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getEntities;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.vfs.Vfs.Dir;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SpiderTests {
    @Test
    @DisplayName("Test Spider moves anti clockwise if boulder in the way")
    public void spiderAntiClockwise () {
        DungeonManiaController dmc; 
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_boulder", "c_spiderTest_basicMovement");
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

        for (int i = 0; i <= 1; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
            
            nextPositionElement++;
            if (nextPositionElement == 8){
                nextPositionElement = 0;
            }
        }

        nextPositionElement -= 2;
        res = dmc.tick(Direction.UP);
        assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
    }

    @Test
    @DisplayName("Test spider spawn rate")
    public void spiderSpawnRate () {
        DungeonManiaController dmc; 
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_spawnRate", "c_spiderTest_spawnRate");
        assertEquals(0, getEntities(res, "spider").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "spider").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, getEntities(res, "spider").size());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(3, getEntities(res, "spider").size());
    }

}
