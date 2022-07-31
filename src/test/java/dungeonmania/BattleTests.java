package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getValueFromConfigFile;

public class BattleTests {
    
    private void assertBattleCalculations(String enemyType, BattleResponse battle, boolean enemyDies, String configFilePath) {
        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));

        for (RoundResponse round : rounds) {
            assertEquals(-(enemyAttack / 10), round.getDeltaCharacterHealth(), 0.001);
            assertEquals(-(playerAttack / 5), round.getDeltaEnemyHealth(), 0.001);
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
        }

        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }

    private static DungeonResponse genericSpiderSequence(DungeonManiaController controller, String configFile) {
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicSpider", configFile);
        int spiderCount = countEntityOfType(initialResponse, "spider");
        
        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, spiderCount);
        return controller.tick(Direction.DOWN);
    }
    
    @Test
    @DisplayName("Test player battles spider and player dies")
    public void spiderWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericSpiderSequence(controller, "c_battleTests_basicSpiderPlayerDies");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("spider", battle, false, "c_battleTests_basicSpiderPlayerDies");
    }

    @Test
    @DisplayName("Test basic battle calculations - spider - player wins")
    public void spiderLose() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericSpiderSequence(controller, "c_battleTests_basicSpiderSpiderDies");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations("spider", battle, true, "c_battleTests_basicSpiderSpiderDies");
    }

    private static DungeonResponse genericZombieSequence(DungeonManiaController controller, String configFile) {
       /*
         *  exit   wall  wall  wall
         * player  [  ]  merc  wall
         *  wall   wall  wall  wall
         */
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicZombie", configFile);
        int zombieCount = countEntityOfType(initialResponse, "zombie");
        
        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, zombieCount);
        return controller.tick(Direction.RIGHT);
    }
    
    @Test
    @DisplayName("Test player battles zombie and player dies")
    public void zombieWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericZombieSequence(controller, "c_battleTests_basicZombiePlayerDies");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("zombie", battle, false, "c_battleTests_basicZombiePlayerDies");
    }

    @Test
    @DisplayName("Test basic battle calculations - zombie - player wins")
    public void zombieLose() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericZombieSequence(controller, "c_battleTests_basicZombieZombieDies");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations("zombie", battle, true, "c_battleTests_basicZombieZombieDies");
    }

}
