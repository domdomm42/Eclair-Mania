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
        double swordAttack = Double.parseDouble(getValueFromConfigFile("sword_attack", configFilePath));

        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), -enemyAttack / 10);
            assertEquals(round.getDeltaEnemyHealth(), -(playerAttack + swordAttack) / 5);
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
        }

        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }

    @Test
    @DisplayName("Test sword damage and durability")
    public void testSwordIncreaseDamage() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_multipleSwords", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

        // pick up 2 swords by moving right
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "sword").size());
        assertEquals(1, getEntities(res, "mercenary").size());

        // kill mercenary with sword
        res = dmc.tick(Direction.RIGHT);

        BattleResponse battle = res.getBattles().get(0);
        assertBattleCalculations("mercenary", battle, true, "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        assertEquals(0, getEntities(res, "mercenary").size());
        assertEquals(0, getInventory(res, "sword").size());
    }
}
