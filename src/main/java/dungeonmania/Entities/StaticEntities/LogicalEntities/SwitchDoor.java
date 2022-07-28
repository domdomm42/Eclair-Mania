package dungeonmania.Entities.StaticEntities.LogicalEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.StaticEntities.FloorSwitch;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Position;

public class SwitchDoor extends StaticEntity{
    private boolean IsOpen;

    public SwitchDoor(Position position, String id) {
        super(position, id, "switch_door", false);
        this.IsOpen = false;
    }

    public boolean isIsOpen() {
        return IsOpen;
    }

    public void setIsOpen(boolean isOpen) {
        IsOpen = isOpen;
    }

    public void OpenSwitchDoor() {  

        List<Position> adjacentPositions = getPosition().getAdjacentPositions();

        // for the surronding areas of lightbulb
        for (Position pos: adjacentPositions) {

            // if nearby wire is triggered.
            if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire") != null) {
                Wire SurroundingWire = (Wire) Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire");
                if (SurroundingWire.isActivated() == true) {
                    setIsOpen(true);
                }
            }

            // if nearby floorswitch is triggered
            if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "FloorSwitch") != null) {
                FloorSwitch SurroundingSwitch = (FloorSwitch) Dungeon.getFirstEntityOfTypeOnPosition(pos, "FloorSwitch");
                if (SurroundingSwitch.isTriggered() == true) {
                    setIsOpen(true);
                }
            }

    }
    }

    public void CloseSwitchDoor() {  

        List<Position> adjacentPositions = getPosition().getAdjacentPositions();
        int NearbySwitch = 0;

        // for the surronding areas of lightbulb
        for (Position pos: adjacentPositions) {

            // if nearby wire is triggered.
            if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire") != null) {
                Wire SurroundingWire = (Wire) Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire");
                if (SurroundingWire.isActivated() == true) {
                    setIsOpen(true);
                    NearbySwitch++;
                }
            }

            // if nearby floorswitch is triggered
            if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "FloorSwitch") != null) {
                FloorSwitch SurroundingSwitch = (FloorSwitch) Dungeon.getFirstEntityOfTypeOnPosition(pos, "FloorSwitch");
                if (SurroundingSwitch.isTriggered() == true) {
                    setIsOpen(true);
                    NearbySwitch++;
                }
            }


        }
        if (NearbySwitch == 0) {
            setIsOpen(false);
        }
    }
}
