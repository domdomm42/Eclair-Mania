
public class Bomb extends Entity{

    //Can be collected by the player. When removed from the inventory it is placed on 
    //the map at the player's location. When a bomb is cardinally adjacent to an active 
    //switch, it destroys all entities in diagonally and cardinally adjacent cells, except 
    //for the player. The bomb should detonate when it is placed next to an already active 
    //switch, or placed next to an inactive switch that then becomes active. The bomb explodes 
    //on the same tick it becomes cardinally adjacent to an active switch. A bomb cannot be 
    //picked up once it has been used.

    public Bomb(String id, String type, boolean isInteractable, Position position) {
        super(id, "bomb", position, true);

    }
}
