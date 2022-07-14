
public class InvincibilityPotion extends Entity{

    //When a Player picks up an Invincibility potion, they may consume it at any time. Any battles 
    //that occur when the Player has the effects of the potion end immediately after the first round, 
    //with the Player immediately winning. Because of this, Mercenaries and Zombies will run away from 
    //the Player when they are invincible. Movement of spiders and bribed mercenaries remains unaffected. 
    //The effects of the potion only last for a limited time.

    public InvincibilityPotion(String id, String type, boolean isInteractable, Position position) {
        super(id, "invincibility_potion", position, true);
    }
}
