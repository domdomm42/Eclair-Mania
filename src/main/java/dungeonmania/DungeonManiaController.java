package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import java.io.IOException;
import java.util.List;

public class DungeonManiaController {
    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        try {
            Dungeon.setupConfigFile(configName);
            Dungeon.startNewGame(dungeonName);
        } catch (Exception exception) {
            throw new IllegalArgumentException();
        }
        return Dungeon.getDungeonResponse();
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return Dungeon.getDungeonResponse();
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        Dungeon.tick(itemUsedId);
        return Dungeon.getDungeonResponse();
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        Dungeon.tick(movementDirection);
        return Dungeon.getDungeonResponse();
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        Dungeon.build(buildable);
        return Dungeon.getDungeonResponse();
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Dungeon.interact(entityId);
        return Dungeon.getDungeonResponse();
    }

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        try {
            Dungeon.saveGame(name);
        } catch (IOException exception) {
            return null;
        }
        return Dungeon.getDungeonResponse();
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        try {
            Dungeon.loadGame(name);
        } catch (IOException exception) {
            return null;
        }
        return Dungeon.getDungeonResponse();
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        return FileLoader.listFileNamesInResourceDirectory(("saved_games"));
    }

    /**
     * /game/new/generate
     */
    public DungeonResponse generateDungeon(int xStart, int yStart, int xEnd, int yEnd, String configName) throws IllegalArgumentException {
        try {
            Dungeon.setupConfigFile(configName);
        } catch (Exception exception) {
            throw new IllegalArgumentException("config file does not exist");
        }
        Dungeon.generateDungeon(xStart, yStart, xEnd, yEnd);
        return Dungeon.getDungeonResponse();
    }
    
    /**
     * /games/all
     */
    public DungeonResponse rewind(int ticks) throws IllegalArgumentException {
        if (ticks <= 0) {
            throw new IllegalArgumentException("ticks < 0");
        }
        try {
            Dungeon.timeTravel(ticks);
        } catch (IOException exception) {
            return null;
        }
        return Dungeon.getDungeonResponse();
    }
}
