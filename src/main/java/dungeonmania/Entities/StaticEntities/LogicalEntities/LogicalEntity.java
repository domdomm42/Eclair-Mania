package dungeonmania.Entities.StaticEntities.LogicalEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.StaticEntities.FloorSwitch;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Position;

public class LogicalEntity extends StaticEntity {

    private String LogicType;

    public LogicalEntity(Position position, String id, String type, boolean Isinteractable, String LogicType) {
        super(position, id, type, Isinteractable);
        this.LogicType = LogicType;
    }
    

    public String getLogicType() {
        return LogicType;
    }

    public void setLogicType(String logicType) {
        this.LogicType = logicType;
    }


    // if 2 or more switch types are available, all of them has to be activated
    public boolean AndIsActivated() {
        int NumSurroundingSwitches = 0;
        int NumActiveSwitches = 0;
        List<Position> adjacentPositions = getPosition().getAdjacentPositions();

        // for the surronding areas of lightbulb
        for (Position pos: adjacentPositions) {
            // if nearby wire OR floor_switch exists
            if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire") != null || Dungeon.getFirstEntityOfTypeOnPosition(pos, "switch") != null) {

                // if surrounding is wire
            if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire") != null) {
                Wire SurroundingWire = (Wire) Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire");
                if (SurroundingWire.isActivated() == true) {
                    NumActiveSwitches++;
                }

            }

            else if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "switch") != null) {
                FloorSwitch SurroundingFloorSwitch = (FloorSwitch) Dungeon.getFirstEntityOfTypeOnPosition(pos, "switch");
                if (SurroundingFloorSwitch.isTriggered() == true) {
                    NumActiveSwitches++;
                }

            }

            NumSurroundingSwitches++;

        }

        }

        if (NumActiveSwitches == NumSurroundingSwitches && NumSurroundingSwitches >= 2) {
            return true;
        }

        return false;




    }


    // 1 or more is activated
    public boolean OrIsActivated() {
        int NumActiveSwitches = 0;
        List<Position> adjacentPositions = getPosition().getAdjacentPositions();

        // for the surronding areas of entity
        for (Position pos: adjacentPositions) {

            // if nearby wire OR floor_switch exists
            if (Dungeon.isEntityOnPosition(pos, "wire") || Dungeon.isEntityOnPosition(pos, "switch")) {

            // if surrounding is wire
            if (Dungeon.isEntityOnPosition(pos, "wire")) {
                Wire SurroundingWire = (Wire) Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire");
                if (SurroundingWire.isActivated() == true) {
                    NumActiveSwitches++;
                }

            }

            // if surrounding is a switch
            else if (Dungeon.isEntityOnPosition(pos, "switch")) {
                FloorSwitch SurroundingFloorSwitch = (FloorSwitch) Dungeon.getFirstEntityOfTypeOnPosition(pos, "switch");

                // if switch is triggered then active switch increases
                if (SurroundingFloorSwitch.isTriggered() == true) {
                    NumActiveSwitches++;
                }

            }

            }

        }

        // if number of active switches around it is greater than or equal 1 then return true
        if (NumActiveSwitches >= 1) {
            return true;
        }

        return false;




    }

    public boolean XORIsActivated() {
        int NumActiveSwitches = 0;
        List<Position> adjacentPositions = getPosition().getAdjacentPositions();

        // for the surronding areas of lightbulb
        for (Position pos: adjacentPositions) {
        // if nearby wire OR floor_switch exists
            if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire") != null || Dungeon.getFirstEntityOfTypeOnPosition(pos, "switch") != null) {

            // if surrounding is wire
            if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire") != null) {
                Wire SurroundingWire = (Wire) Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire");
                if (SurroundingWire.isActivated() == true) {
                    NumActiveSwitches++;
                }

            }

            else if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "switch") != null) {
                FloorSwitch SurroundingFloorSwitch = (FloorSwitch) Dungeon.getFirstEntityOfTypeOnPosition(pos, "switch");
                if (SurroundingFloorSwitch.isTriggered() == true) {
                    NumActiveSwitches++;
                }

            }

            }

        }

        if (NumActiveSwitches == 1) {
            return true;
        }

        return false;




    }

    /* 
    public boolean COANDIsActivated() {
        int NumActiveSwitches = 0;
        List<Position> adjacentPositions = getPosition().getAdjacentPositions();

        // for the surronding areas of lightbulb
        for (Position pos: adjacentPositions) {
        // if nearby wire OR floor_switch exists
            if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire") != null || Dungeon.getFirstEntityOfTypeOnPosition(pos, "floor_switch") != null) {

            // if surrounding is wire
            if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire") != null) {
                Wire SurroundingWire = (Wire) Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire");
                if (SurroundingWire.isActivated() == true) {
                    NumActiveSwitches++;
                }

            }

            if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "floor_switch") != null) {
                FloorSwitch SurroundingFloorSwitch = (FloorSwitch) Dungeon.getFirstEntityOfTypeOnPosition(pos, "floor_switch");
                if (SurroundingFloorSwitch.isTriggered() == true) {
                    NumActiveSwitches++;
                }

            }

            }

        }

        if (NumActiveSwitches >= 1) {
            return true;
        }

        return false;




    }

    */

}
