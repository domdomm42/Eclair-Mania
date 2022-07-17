package dungeonmania.Entities.StaticEntities.CollectableEntities;

import java.util.ArrayList;
import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
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
            setPosition(Dungeon.getPlayer().getPosition());
            Dungeon.addEntityToAddAfterTick(this);
            Dungeon.getPlayer().getInventory().removeItem(this);
            this.detonate();
            this.hasBeenPickedUp = true;
        }
    }
    
    @Override
    public void tick() {
        this.detonate();
    }
    
    // Denotes a bomb if next to an active switch
    // Returns a list of the entities which now exist
    public void detonate() {

        ArrayList<Entity> entities = Dungeon.getEntities();

        if (isBombAdjacentToActiveSwitch(entities)) {
            for (Entity e : entities) {
                destroyEntityByBomb(e);
            }
            Dungeon.addEntityToRemoveAfterTick(this);
        }
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

    private void destroyEntityByBomb(Entity e) {

        // Player cannot be destroyed by bomb
        if (e.getType() == "player") {
            return;
        }

        int bombRadius = Dungeon.getConfigValue("bomb_radius");
        int entityPosX = e.getPosition().getX();
        int entityPosY = e.getPosition().getY();

        int bombPosX = this.getPosition().getX();
        int bombPosY = this.getPosition().getY();

        int bombRadiusLeftCoord = bombPosX - bombRadius;
        int bombRadiusRightCoord = bombPosX + bombRadius;
        int bombRadiusTopCoord = bombPosY + bombRadius;
        int bombRadiusBottomCoord = bombPosY - bombRadius;

        if (entityPosY >= bombRadiusBottomCoord && entityPosY <= bombRadiusTopCoord &&
            entityPosX <= bombRadiusRightCoord && entityPosX >= bombRadiusLeftCoord) {
            
            // Cannot destroy collectable entities which are in the players inventory
            Dungeon.addEntityToRemoveAfterTick(e);
        }
    }

    public boolean isHasBeenPickedUp() {
        return hasBeenPickedUp;
    }

    public void setHasBeenPickedUp(boolean hasBeenPickedUp) {
        this.hasBeenPickedUp = hasBeenPickedUp;
    }    
}
