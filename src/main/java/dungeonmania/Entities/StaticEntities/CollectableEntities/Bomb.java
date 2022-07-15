package dungeonmania.Entities.StaticEntities.CollectableEntities;

import dungeonmania.util.Position;

public class Bomb extends Usable {

    private boolean hasBeenPickedUp;

    public Bomb(Position position, String id) {
        super(false, 1, position, id, "bomb");
        this.hasBeenPickedUp = false;
    }

    @Override
    public void use() {
        // if this.isPickedUp() and bomb is in inventory
        // then drop the bomb at the players location and set ispicked
        // up to false and has been picked up to true and set the location
        // of the bomb to the location of the player
        if (this.isPickedUp() && )

    }
    
    // Denote any bombs at all ticks if they meet this criteria 
    // The bomb has been picked up and is not in the players inventory
    public void detonate() {
        // ensure bomb is not picked up
        // check if the bomb is next to an active switch at every tick
        // if it is then detonate and destroy all entities within its radius
        // ie remove from entitylist of dungeon those which are in position
    }




    public boolean isHasBeenPickedUp() {
        return hasBeenPickedUp;
    }

    public void setHasBeenPickedUp(boolean hasBeenPickedUp) {
        this.hasBeenPickedUp = hasBeenPickedUp;
    }

    
}
