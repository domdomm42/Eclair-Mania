package dungeonmania.Entities.StaticEntities.LogicalEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.StaticEntities.FloorSwitch;
import dungeonmania.util.Position;

public class SwitchDoor extends LogicalEntity{
    private boolean IsOpen;
    private String LogicalType;

    public SwitchDoor(Position position, String id, String LogicalType) {
        super(position, id, "switch_door", false, LogicalType);
        this.IsOpen = false;
        this.LogicalType = LogicalType;
    }

    public boolean isIsOpen() {
        return IsOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.IsOpen = isOpen;
    }

    public String getLogicalType() {
        return LogicalType;
    }

    public void setLogicalType(String logicalType) {
        this.LogicalType = logicalType;
    }


    public void OpenSwitchDoorIfPossible() {
        if (LogicalType == "and") {
            if (AndIsActivated()) {
                setIsOpen(true);
            }

            else {
                setIsOpen(false);
            }
        }

        else if (LogicalType == "or") {
            if (OrIsActivated()) {
                setIsOpen(true);
            }

            else {
                setIsOpen(false);
            }
        }

        else if (LogicalType == "xor") {
            if (XORIsActivated()) {
                setIsOpen(true);
            }

            else {
                setIsOpen(false);
            }
        }


    }

    @Override
    public void tick() {
        super.tick();
        OpenSwitchDoorIfPossible();
    }
    
    }

