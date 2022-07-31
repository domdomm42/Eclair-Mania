package dungeonmania.Entities.StaticEntities.CollectableEntities;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.StaticEntities.FloorSwitch;
import dungeonmania.Entities.StaticEntities.LogicalEntities.Wire;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Bomb extends Usable {

    private boolean hasBeenPickedUp;
    private String LogicType;

    public Bomb(Position position, String id, String LogicType) {
        super(false, 1, position, id, "bomb");
        this.hasBeenPickedUp = false;
        this.LogicType = LogicType;
    }

    public String getLogicType() {
        return LogicType;
    }

    public void setLogicType(String logicType) {
        LogicType = logicType;
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
        if (LogicType == null) {
            this.detonate();
        }

        else if (LogicType.equals("co_and")) {
            if (CheckIfNotTriggeredCoAnd()) {
                super.tick();
                if (AndActivateBomb()) {
                    ArrayList<Entity> entities = Dungeon.getEntities();
                    for (Entity e : entities) {
                        destroyEntityByBomb(e);
                    }
                    Dungeon.addEntityToRemoveAfterTick(this);
                }

            }
        }

        else if (LogicType.equals("and")) {
            if (AndActivateBomb()) {
                ArrayList<Entity> entities = Dungeon.getEntities();
                    for (Entity e : entities) {
                        destroyEntityByBomb(e);
                    }
                    Dungeon.addEntityToRemoveAfterTick(this);
            }
        }

        else if (LogicType.equals("or")) {
            if (OrIsActivated()) {
                ArrayList<Entity> entities = Dungeon.getEntities();
                    for (Entity e : entities) {
                        destroyEntityByBomb(e);
                    }
                    Dungeon.addEntityToRemoveAfterTick(this);
            }
        }

        else if (LogicType.equals("xor")) {
            if (XORIsActivated()) {
                ArrayList<Entity> entities = Dungeon.getEntities();
                    for (Entity e : entities) {
                        destroyEntityByBomb(e);
                    }
                    Dungeon.addEntityToRemoveAfterTick(this);
            }
        }


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

    @Override
    public JsonObject toJsonObject() {
        JsonObject bombJson = super.toJsonObject();
        bombJson.addProperty("hasBeenPickedUp", hasBeenPickedUp);
        return bombJson;
    }


    public boolean AndActivateBomb() {
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

        if ((NumActiveSwitches == NumSurroundingSwitches) && NumSurroundingSwitches >= 2) {
            return true;
        }

        return false;

    }

       // 1 or more is activated
       public boolean OrIsActivated() {
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

    public boolean XORIsActivated() {
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
