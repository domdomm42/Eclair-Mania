package main.java.dungeonmania.util;

public class EntityNoPosition extends Entity{
    private Position position;

    public EntityNoPosition(String id, String type, boolean isInteractable, Position position) {
        super(id, type, isInteractable);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
