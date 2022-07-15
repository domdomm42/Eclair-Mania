package dungeonmania.Entities.StaticEntities.CollectableEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.StaticEntities.Boulder;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Bomb extends Usable {

    private boolean hasBeenPickedUp;

    public Bomb(Position position, String id) {
        super(false, 1, position, id, "bomb");
        this.hasBeenPickedUp = false;
    }

    @Override
    public void use() {
        if (this.isPickedUp()) {
            this.setPickedUp(false);
            this.hasBeenPickedUp = true;
        }
    }
    
    // Denotes a bomb if next to an active switch
    // Returns a list of the entities which now exist
    public ArrayList<Entity> detonate(ArrayList<Entity> entities) {

        // List of positions that the bomb will destroy
        


        // Check if there is an active switch adjacent to bomb
        if (isBombAdjacentToActiveSwitch(entities)) {

            ArrayList<Entity> entitiesRemain;

            entities.stream().filter(entity -> {

            })
            return 
        }

        // ensure only destroys things on map
        
        
        // probably need the dungeon class here to get all the entities

        // ensure bomb is not picked up
        // check if the bomb is next to an active switch at every tick
        // if it is then detonate and destroy all entities within its radius
        // ie remove from entitylist of dungeon those which are in position
    }

    private boolean isBombAdjacentToActiveSwitch(ArrayList<Entity> entities) {
        for (Entity e : entities) {
            for (Entity e2 : entities) {
                if (e.getType() == "boulder" && e2.getType() == "switch" && e.getPosition().equals(e2.getPosition()) &&
                    (e.getPosition().equals(this.getPosition().translateBy(Direction.UP)) || e.getPosition().equals(this.getPosition().translateBy(Direction.RIGHT))
                    || e.getPosition().equals(this.getPosition().translateBy(Direction.DOWN)) || e.getPosition().equals(this.getPosition().translateBy(Direction.LEFT)))) {
                    
                        return true;
                }
            }
        }
        return false;
    }

    public boolean isHasBeenPickedUp() {
        return hasBeenPickedUp;
    }

    public void setHasBeenPickedUp(boolean hasBeenPickedUp) {
        this.hasBeenPickedUp = hasBeenPickedUp;
    }

    
}
