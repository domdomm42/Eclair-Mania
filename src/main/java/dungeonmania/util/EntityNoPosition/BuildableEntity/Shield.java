package main.java.dungeonmania.util.EntityNoPosition.BuildableEntity;

public class Shield extends BuildableEntity{

    //Can be crafted with 2 wood + (1 treasure OR 1 key). Shields decrease the effect of 
    //enemy attacks. Each shield has a specific durability that dictates the number of battles 
    //it can be used before it deteriorates.

    public Shield(String id, String type, boolean isInteractable, Position position) {
        super(id, "shield", false, position);
    }
    
}
