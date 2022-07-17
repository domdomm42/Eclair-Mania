package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getValueFromConfigFile;
import static dungeonmania.TestUtils.getPlayer;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwordTests {
    @Test
    @DisplayName("Test sword is collectable")
    public void testCollectSword() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_swordTest_pickup", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        
        // create the expected player position
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);

        // move up again
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        assertEquals(expectedPlayer, actualPlayer);

        // check if treasure is in the inventory
        assertEquals(1, getInventory(actualDungonRes, "sword").size());

    }
    
    // does battle calculations
    private void assertBattleCalculations(String enemyType, BattleResponse battle, boolean enemyDies, String configFilePath) {
        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));
        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));

        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), enemyAttack / 10);
            assertEquals(round.getDeltaEnemyHealth(), playerAttack / 5);
            enemyHealth -= round.getDeltaEnemyHealth();
            playerHealth -= round.getDeltaCharacterHealth();
        }

        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }

    
    // not done yet
    @Test
    @DisplayName("Test sword increase damage")
    public void testSwordIncreaseDamage() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_multipleSwords", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

        // pick up sword by moving right
        res = dmc.tick(Direction.DOWN);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(1, getInventory(res, "sword").size());


        // move player up to pickup another sword
        res = dmc.tick(Direction.RIGHT);
        pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(2, getInventory(res, "sword").size());

        // // check if 2 swords are in inventory
        // assertEquals(2, getInventory(initDungeonRes, "sword").size());
        //simulate a battle

        BattleResponse battle = res.getBattles().get(0);
        // assert that player wins, taht means sword doubled in damage
        assertBattleCalculations("spider", battle, true, "c_swordTests_sworddamage.json");


    }

    @Test
    @DisplayName("Test sword durability")
    public void testSwordDurability() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_multipleSwords", "c_swordTests_sworddamage.json");

        // move player upwards to pickup sword
        res = dmc.tick(Direction.UP);
        Position pos = getEntities(res, "player").get(0).getPosition();

        // move player to the right to pickup another sword
        res = dmc.tick(Direction.RIGHT);
        pos = getEntities(res, "player").get(0).getPosition();

        // check if 2 swords are in inventory
        assertEquals(2, getInventory(res, "sword").size());

        //simulate a battle
        BattleResponse battle = res.getBattles().get(0);
        // assert that player wins, taht means sword doubled in damage
        assertBattleCalculations("spider", battle, true, "c_swordTests_sworddamage.json");

        // after win go right again
        res = dmc.tick(Direction.RIGHT);
        //fight again but this time sword cannot be used and hence player loses
        assertBattleCalculations("spider", battle, false, "c_swordTests_sworddamage.json");

    }
}
