package dungeonmania.Entities.StaticEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.StaticEntities.LogicalEntities.Wire;
import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {

    private boolean isTriggered;
    private String LogicType;

    public FloorSwitch(Position position, String id, String LogicType) {
        super(position, id, "switch", false);
        this.isTriggered = false;
        this.LogicType = LogicType;
    }

    public boolean isTriggered() {
        return isTriggered;
    }

    public String getLogicType() {
        return LogicType;
    }

    public void setLogicType(String logicType) {
        LogicType = logicType;
    }

    public void setTriggered(boolean isTriggered) {
        this.isTriggered = isTriggered;
    }
    
    @Override
    public void tick() {
        super.tick();

        if (LogicType == null) {
            if (Dungeon.getFirstEntityOfTypeOnPosition(getPosition(), "boulder") == null) setTriggered(false);
            else {
                setTriggered(true);
            }
        }

        else if (!LogicType.equals(null)) {
            if (LogicType.equals("and")) {
                if (AndActivateSwitch()) {
                    setTriggered(true);
                }

                else {
                    setTriggered(false);
                }
            }

            else if (LogicType.equals("or")) {
                if (OrActivateSwitch()) {
                    setTriggered(true);
                }

                else {
                    setTriggered(false);
                }
            }

            else if (LogicType.equals("xor")) {
                if (XORActivateSwitch()) {
                    setTriggered(true);
                }
                else {
                setTriggered(false);
                }
            }
        }


    }

    public boolean AndActivateSwitch() {
        int NumSurroundingSwitches = 0;
        int NumActiveSwitches = 0;
        List<Position> adjacentPositions = getPosition().getCardinallyAdjacentPositions();

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
       public boolean OrActivateSwitch() {
        int NumActiveSwitches = 0;
        List<Position> adjacentPositions = getPosition().getCardinallyAdjacentPositions();

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

    public boolean XORActivateSwitch() {
        int NumActiveSwitches = 0;
        List<Position> adjacentPositions = getPosition().getCardinallyAdjacentPositions();

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

    public boolean CheckIfNotTriggeredCoAnd() {
        int NumActiveSwitches = 0;
        List<Position> adjacentPositions = getPosition().getCardinallyAdjacentPositions();

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

        if (NumActiveSwitches > 0) {
            return false;
        }

        return true;

    }





}
