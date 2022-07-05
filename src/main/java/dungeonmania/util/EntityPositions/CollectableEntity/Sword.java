package main.java.dungeonmania.util.EntityPositions.CollectableEntity;

public class Sword extends EntityPosition{

    //A standard melee weapon. Swords can be collected by the Player and used in battles, 
    //increasing the amount of damage they deal by an additive factor. Each sword has a 
    //specific durability that dictates the number of battles it can be used before it 
    //deteriorates and is no longer usable.

    public Sword(String id, String type, boolean isInteractable, Position position) {
        super(id, "sword", true, position);

    }
}
