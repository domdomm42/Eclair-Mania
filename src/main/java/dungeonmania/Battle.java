package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.Enemies.Enemy;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.response.models.RoundResponse;

public class Battle {
    private Player player;
    private Enemy enemy;
    private int initialPlayerHp;
    private int initialEnemyHp;
    private ArrayList<Round> rounds;

    public Battle(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        initialPlayerHp = player.getHealth();
        initialEnemyHp = enemy.getHealth();
        rounds = new ArrayList<Round>();
        generateRounds();
    }

    public int getInitialPlayerHp() {
        return initialPlayerHp;
    }

    public int getInitialEnemyHp() {
        return initialEnemyHp;
    }

    private void generateRounds() {
        if (player.activePotionEffect() == "invincibility_potion") {
            rounds.add(new Round(0, initialEnemyHp, new ArrayList<CollectableEntity>()));
            return;
        }
        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            rounds.add(new Round(-enemy.getAttack(), -player.getAttack(), player.getWeaponryUsed()));
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public List<RoundResponse> getRoundResponses() {
        return rounds.stream().map(round -> round.toRoundResponse()).collect(Collectors.toList());
    }
}
