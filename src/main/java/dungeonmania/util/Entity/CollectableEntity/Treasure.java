
public class Treasure extends Entity{

    //Can be collected by the Player.

    public Treasure(String id, String type, boolean isInteractable, Position position) {
        super(id, "treasure", position, true);

    }
}
