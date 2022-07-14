
public class Wall extends EntityPosition{

    //Blocks the movement of the Player, enemies and boulders.

    public Wall(String id, String type, boolean isInteractable, Position position) {
        super(id, "wall", position, true);
    }
    
}
