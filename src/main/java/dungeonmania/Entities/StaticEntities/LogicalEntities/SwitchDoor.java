package dungeonmania.Entities.StaticEntities.LogicalEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.StaticEntities.FloorSwitch;
import dungeonmania.util.Position;

public class SwitchDoor extends LogicalEntity {
    private boolean IsOpen;

    public SwitchDoor(Position position, String id, String LogicalType) {
        super(position, id, "switch_door", false, LogicalType);
        this.IsOpen = false;
    }

    public boolean isIsOpen() {
        return IsOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.IsOpen = isOpen;
    }



    public void OpenSwitchDoorIfPossible() {
        if (LogicType.equals("and")) {
            if (AndIsActivated()) {
                setIsOpen(true);
            }

            else {
                setIsOpen(false);
            }
        }

        else if (LogicType.equals("or")) {
            if (OrIsActivated()) {
                setIsOpen(true);
            }

            else {
                setIsOpen(false);
            }
        }

        else if (LogicType.equals("xor")) {
            if (XORIsActivated()) {
                setIsOpen(true);
            }

            else {
                setIsOpen(false);
            }
        }

        else if (LogicType.equals("co_and")) {
            if (AndIsActivated()) {
                setIsOpen(true);
            }

            else {
                setIsOpen(false);
            }
        }


    }

    @Override
    public void tick() {
        // co_and case
        if (LogicType.equals("co_and")) {
            if (CheckIfNotTriggeredCoAnd() == true) {
                System.out.print("hello");
                super.tick();
                if (AndIsActivated()) {
                    setIsOpen(true);
                    System.out.print("true");
                }

                else {
                    setIsOpen(false);
                    System.out.print("false");
                }
            }
        }
        
        // AND OR XOR case
        else {
            super.tick();
            OpenSwitchDoorIfPossible();
        }
    }
    
}


