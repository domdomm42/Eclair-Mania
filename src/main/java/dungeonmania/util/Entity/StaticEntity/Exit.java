
public class Exit extends EntityPosition{

    //If the Player goes through it, the puzzle is complete.

    public Exit(String id, String type, boolean isInteractable, Position position) {
        super(id, "exit", position, true);
    }
    
}
