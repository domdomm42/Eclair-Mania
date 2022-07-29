package dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities;

import dungeonmania.Dungeon;

public class Sceptre extends BuildableEntity {

    /*
     * ASSUMPTIONS ABOUT SCEPTRE
     * 
     * Sceptre can only be used once, after it is used it is removed from inventory
     * When the sceptre is used, it mind controls ALL mercenaries and assassins
     * 
     * 
     * NOTES for others
     * Mercenaries mind control has been implemented
     * Assassin mind control has not been implemented (ask Rupin)
     * Need to keep track for how many ticks the mercenary/assassin has been mind controlled / become ally for
     * 
     * 
     */



    public Sceptre(String id) {
        super(true, 1, Dungeon.getPlayer().getPosition(), id, "sceptre");
    }
    
}
