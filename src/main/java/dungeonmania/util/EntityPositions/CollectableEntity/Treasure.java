package main.java.dungeonmania.util.EntityPositions.CollectableEntity;

public class Treasure extends EntityPosition{

    //Can be collected by the Player.

    public Treasure(String id, String type, boolean isInteractable, Position position) {
        super(id, "treasure", true, position);

    }
}
