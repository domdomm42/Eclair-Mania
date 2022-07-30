package dungeonmania.Entities.StaticEntities.LogicalEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.StaticEntities.FloorSwitch;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Position;

public class Wire extends StaticEntity {

    public boolean isActivated;

    public Wire(Position position, String id) {
        super(position, id, "wire", false);
        this.isActivated = false;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }

    public void ActivateWire() {

        // list of the areas surronding the wire
        List<Position> adjacentPositions = getPosition().getCardinallyAdjacentPositions();

        for (Position pos: adjacentPositions) {

            // if there are wires sorrounding it
            if (Dungeon.isEntityOnPosition(pos, "wire")) {
                Wire SurroundingWire = (Wire) Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire");
                if (SurroundingWire.isActivated() == true) {
                    setActivated(true);
                }

                else {
                    setActivated(false);
                }
            }
            
            // if there is a surrounding floorswitch
            else if (Dungeon.isEntityOnPosition(pos, "switch")) {
                FloorSwitch SurroundingSwitch = (FloorSwitch) Dungeon.getFirstEntityOfTypeOnPosition(pos, "switch");
                if (SurroundingSwitch.isTriggered() == true) {
                    setActivated(true);
                }

                else {
                    setActivated(false);
                }

            }
        }



    }

    @Override
    public void tick() {
        super.tick();
        ActivateWire();
    }

    
}
