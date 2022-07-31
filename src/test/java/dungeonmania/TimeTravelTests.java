package dungeonmania;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import java.util.ArrayList;
import java.util.List;

public class TimeTravelTests {
    @Test
    @DisplayName("Test basic movement of spiders Time Travel")
    public void spiderTimeTravel() {
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
        for (int i = 0; i <= 6; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());

            nextPositionElement++;
            if (nextPositionElement == 8){
                nextPositionElement = 0;
            }
        }


        res = dmc.rewind(5);
        nextPositionElement -= 5;

        for (int i = 0; i <= 10; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());

            nextPositionElement++;
            if (nextPositionElement == 8){
                nextPositionElement = 0;
            }
        }
    }

    @Test
    @DisplayName("Test older player exists")
    public void olderPlayerTimeTravel() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_integrationTest_3", "c_spiderTest_basicMovement");
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.rewind(5);
        assertEquals(1, getEntities(res, "older_player").size());

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
    }

    @Test
    @DisplayName("OlderPlayer destroys spawner")
    public void PlayerDestroysSpawner() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieToastTests_destroySpawn", "c_zombieToastTests_noZombies");
        
        String spawnerId = getEntities(res, "zombie_toast_spawner").get(0).getId();
        assertEquals(1, getEntities(res, "zombie_toast_spawner").size());

        res = dmc.tick(Direction.DOWN);
        assertEquals(1, getInventory(res, "sword").size());
        
        // Destroy spawner
        assertEquals(1, getInventory(res, "sword").size());
        res = dmc.interact(spawnerId);
        assertEquals(0, getEntities(res, "zombie_toast_spawner").size());

        res = dmc.rewind(2);

        assertEquals(1, getEntities(res, "zombie_toast_spawner").size());

        res = dmc.tick(Direction.DOWN);
        assertEquals(0, getInventory(res, "sword").size());
        
        // Destroy spawner
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, getEntities(res, "zombie_toast_spawner").size());

    }
}
