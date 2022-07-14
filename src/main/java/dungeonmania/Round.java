package dungeonmania;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.response.models.RoundResponse;

public class Round {
    private double deltaPlayerHealth;
    private double deltaEnemyHealth;
    private List<CollectableEntity> weaponryUsed;

    public Round(double deltaPlayerHealth, double deltaEnemyHealth, List<CollectableEntity> weaponryUsed)
    {
        this.deltaPlayerHealth = deltaPlayerHealth;
        this.deltaEnemyHealth = deltaEnemyHealth;
        this.weaponryUsed = weaponryUsed;
    }

    public double getDeltaCharacterHealth(){
        return deltaPlayerHealth;
    }
    
    public double getDeltaEnemyHealth(){
        return deltaEnemyHealth;
    }

    public List<CollectableEntity> getWeaponryUsed() { return weaponryUsed; }

    public RoundResponse toRoundResponse() {
        return null;
        // return new RoundResponse(deltaPlayerHealth, deltaEnemyHealth, weaponryUsed.stream().map(weapon -> weapon.getId()).collect(Collectors.toList()));
    }
}
