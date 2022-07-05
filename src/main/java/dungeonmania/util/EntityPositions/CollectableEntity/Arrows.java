package main.java.dungeonmania.util.EntityPositions.CollectableEntity;

public class Arrows extends EntityPosition{

    //Can be collected by the Player.

    public Arrows(String id, String type, boolean isInteractable, Position position) {
        super(id, "arrow", true, position);

    }
}
