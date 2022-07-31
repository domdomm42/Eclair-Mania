package dungeonmania.Entities.MovingEntities.Enemies;

import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.MovementStrategies.ZombieMovementStrategy;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Hydra extends Enemy {
    private double hydraHealthIncreaseRate;
    private double hydraHealthIncreaseAmount;

    public Hydra(String id, Position position) {
        super(id, "hydra", position, Dungeon.getConfigValue("hydra_health"), false, new ZombieMovementStrategy(), Dungeon.getConfigValue("hydra_attack"));
        getMovementStrategy().setEntity(this);
        hydraHealthIncreaseRate = Dungeon.getConfigValue("hydra_health_increase_rate");
        hydraHealthIncreaseAmount = Dungeon.getConfigValue("hydra_health_increase_amount");
    }

    @Override
    public void tick(Direction playerMovementDirection) {
        getMovementStrategy().move();
    }

    @Override
    public void tick(String playerAction) {
        getMovementStrategy().move();
    }

    public double getHydraHealthIncreaseRate() {
        return hydraHealthIncreaseRate;
    }

    public double getHydraHealthIncreaseAmount() {
        return hydraHealthIncreaseAmount;
    }

    public boolean isHydraHealthIncreasingBattle() {
        if (hydraHealthIncreaseRate == 1) return true;

        Random rand = new Random();
        if (rand.nextInt(100) <= (hydraHealthIncreaseRate * 100)) return true;
        else return false;
    }

}

