package main.java.dungeonmania.util;

public class Entity{
    private String id;
    private String type;
    private boolean isInteractable;

    public Entity(String id, String type, boolean isInteractable) {
        this.id = id;
        this.type = type;
        this.isInteractable = isInteractable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

}