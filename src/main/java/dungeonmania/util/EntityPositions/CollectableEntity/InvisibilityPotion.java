package main.java.dungeonmania.util.EntityPositions.CollectableEntity;

public class InvisibilityPotion extends EntityPosition{

    //When a player picks up an invisibility potion, they may consume it at any time and they 
    //immediately become invisible and can move past all other entities undetected. Battles do 
    //not occur when a player is under the influence of an invisibility potion. Since mercenaries 
    //typically follow the player, their movement becomes the same as a Zombie when the player is invisible.

    public InvisibilityPotion(String id, String type, boolean isInteractable, Position position) {
        super(id, "invisibility_potion", true, position);
    }
}
