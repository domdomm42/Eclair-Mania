package main.java.dungeonmania.util.EntityPositions.CollectableEntity;

public class Key extends EntityPosition{

    //Can be picked up by the player when they move into the square containing it. 
    //The Player can carry only one key at a time, and only one door has a lock that 
    //fits the key. Keys disappear once used in any context i.e. opening a door, building 
    //an item. If a key is used before opening its door, its corresponding door will be locked forever.

    public Key(String id, String type, boolean isInteractable, Position position) {
        super(id, "key", true, position);
    }
}
