
public class Bow extends Entity{

    //Can be crafted with 1 wood + 3 arrows. The bow has a durability which deteriorates 
    //after a certain number of battles. Bows give the Player double damage in a single 
    //round, to simulate being able to attack an enemy at range (it can't actually attack an enemy at range).

    public Bow(String id, String type, boolean isInteractable, Position position) {
        super(id, "bow", position, false);
    }
    
}
