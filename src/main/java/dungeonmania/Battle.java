package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.Enemies.Enemy;
import dungeonmania.Entities.MovingEntities.Enemies.Hydra;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.RoundResponse;

public class Battle {
    private Player player;
    private MovingEntity enemy;
    private double initialPlayerHp;
    private double initialEnemyHp;
    private ArrayList<Round> rounds;

    public Battle(Player player, MovingEntity enemy) {
        this.player = player;
        this.enemy = enemy;
        initialPlayerHp = player.getHealth();
        initialEnemyHp = enemy.getHealth();
        rounds = new ArrayList<Round>();
        generateRounds();
    }

    public double getInitialPlayerHp() {
        return initialPlayerHp;
    }

    public double getInitialEnemyHp() {
        return initialEnemyHp;
    }

    private void generateRounds() {
        if (rounds.size() > 0) return;
        if (player.activePotionEffect().equals("invincibility_potion")) {
            if (enemy instanceof Hydra) {
                if (((Hydra) enemy).isHydraHealthIncreasingBattle()) {
                    rounds.add(new Round(0, 0, player.getWeaponryUsed()));
                    player.setHealth(player.getHealth() - ((enemy.getAttack() - player.getDefence()) / 10));
                    enemy.setHealth(enemy.getHealth() + ((Hydra) enemy).getHydraHealthIncreaseAmount());
                }
            } else { 
                rounds.add(new Round(0, initialEnemyHp, player.getWeaponryUsed()));
                enemy.setHealth(0);
            }
        } else while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            rounds.add(new Round(-((enemy.getAttack() - player.getDefence()) / 10), -(player.getAttack() / 5), player.getWeaponryUsed()));
            player.setHealth(player.getHealth() - ((enemy.getAttack() - player.getDefence()) / 10));
            enemy.setHealth(enemy.getHealth() - (player.getAttack() / 10));
            if (enemy instanceof Hydra) {
                if (((Hydra) enemy).isHydraHealthIncreasingBattle()) {
                    enemy.setHealth(enemy.getHealth() + ((Hydra) enemy).getHydraHealthIncreaseAmount());
                }
            } 
        }
        
        if (player.getHealth() <= 0) Dungeon.addEntityToRemoveAfterTick(player);
        if (enemy.getHealth() <= 0) {
            Dungeon.addEntityToRemoveAfterTick(enemy);
            Dungeon.incrementKilledEntities();
        }
        player.getWeaponryUsed().forEach(weapon -> weapon.setDurability(weapon.getDurability() - 1));
    }

    public Player getPlayer() {
        return player;
    }

    public MovingEntity getEnemy() {
        return enemy;
    }

    public List<RoundResponse> getRoundResponses() {
        return rounds.stream().map(round -> round.toRoundResponse()).collect(Collectors.toList());
    }

    public BattleResponse toBattleResponse() {
        return new BattleResponse(enemy.getType(), getRoundResponses(), initialPlayerHp, initialEnemyHp);
    }
}
