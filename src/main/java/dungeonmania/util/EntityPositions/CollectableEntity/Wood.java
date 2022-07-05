package main.java.dungeonmania.util.EntityPositions.CollectableEntity;

public class Wood extends EntityPosition{

    //Can be collected by the Player.

    public Wood(String id, String type, boolean isInteractable, Position position) {
        super(id, "wood", true, position);

    }
}
