package dungeonmania;

import java.util.ArrayList;

import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.Enemies.Enemy;

public class Battle {
    private Player player;
    private Enemy enemy;
    private int initialPlayerHp;
    private int initialEnemyHp = 0;
    private ArrayList<Round> rounds;

    public Battle(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        initialPlayerHp = player.getHealth();
        generateRounds();
    }

    public int getInitialPlayerHp() {
        return initialPlayerHp;
    }

    public int getInitialEnemyHp() {
        return initialEnemyHp;
    }

    private void generateRounds() {
        
    }
}
