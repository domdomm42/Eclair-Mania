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

public class SwordTests {
    @Test
    @DisplayName("Test sword is collectable")
    public void testCollectKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_pickup", "c_movementTest_testMovementDown");

        // move player upwards to pickup sword
        res = dmc.tick(Direction.UP);
        Position pos = getEntities(res, "player").get(0).getPosition();

        // checks if sword is in inventory
        assertEquals(1, getInventory(res, "sword").size());

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
