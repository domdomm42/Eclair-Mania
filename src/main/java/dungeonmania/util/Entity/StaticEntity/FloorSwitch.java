
public class FloorSwitch extends EntityPosition{
        
    //Switches behave like empty squares, so other entities can appear on top of them. When a boulder 
    //is pushed onto a floor switch, it is triggered. Pushing a boulder off the floor switch untriggers.

    public FloorSwitch(String id, String type, boolean isInteractable, Position position) {
        super(id, "switch", position, true);
    }
        
}
