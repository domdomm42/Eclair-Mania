package main.java.dungeonmania.util.EntityPositions.StaticEntity;

public class Boulder extends EntityPosition{

    // Acts like a wall in most cases. The only difference is that it can be pushed by the Player 
    // into cardinally adjacent squares. The Player is only strong enough to push one boulder at a 
    // time. When the player pushes a boulder, they move into the spot the boulder was previously in. 
    // Boulders can be pushed onto collectable entities.

    public Boulder(String id, String type, boolean isInteractable, Position position) {
        super(id, "boulder", true, position);
    }
}
