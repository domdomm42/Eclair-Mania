package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dungeonmania.util.Position;

public class DungeonBuilder {

    public static void generateDungeon(int xStart, int yStart, int xEnd, int yEnd) {
        Dungeon.resetDungeon();
        Dungeon.setDungeonName("maze");

        int height = Math.abs(yEnd - yStart);
        int width = Math.abs(xEnd - xStart);

        boolean[][] maze = generateRandomMaze(height + 1, width + 1);

        JsonArray entities = new JsonArray();
        
        // Add player at START
        JsonObject player = new JsonObject();
        player.addProperty("type", "player");
        player.addProperty("x", xStart);
        player.addProperty("y", yStart);
        entities.add(player);
        
        // Add exit at END
        JsonObject exit = new JsonObject();
        exit.addProperty("type", "exit");
        exit.addProperty("x", xEnd);
        exit.addProperty("y", yEnd);
        entities.add(exit);

        // Create border of walls around maze
        for (int x = xStart - 1; x <= xEnd + 1; x++) {
            for (int y = yStart - 1; y <= yEnd + 1; y++) {
                if (x == xStart - 1 || y == yStart - 1 || x == xEnd + 1 || y == yEnd + 1) {
                    JsonObject wall = new JsonObject();
                    wall.addProperty("type", "wall");
                    wall.addProperty("x", x);
                    wall.addProperty("y", y);
                    entities.add(wall);
                }
            }
        }
        
        // Create maze
        for (int x = xStart, i = 0; x <= xEnd; x++, i++) {
            for (int y = yStart, j = 0; y <= yEnd; y++, j++) {
                if (!maze[i][j]) {
                    JsonObject wall = new JsonObject();
                    wall.addProperty("type", "wall");
                    wall.addProperty("x", x);
                    wall.addProperty("y", y);
                    entities.add(wall); 
                }
            }
        }

        Dungeon.loadEntities(entities);
    
        // Create the only goal: exit
        JsonObject exitGoal = new JsonObject();
        exitGoal.addProperty("goal", "exit");

        Dungeon.loadGoals(exitGoal);
    }

    private static boolean[][] generateRandomMaze(int height, int width) {
        int boundaryYBottom = height - 1;
        int boundaryXRight = width - 1;

        Random r = new Random();

        boolean[][] maze = new boolean[width][height];

        maze[0][0] = true;

        List<Position> options = getNeighbours(maze, new Position(0, 0), 2, true);

        while (options.size() > 0) {
            Position next = options.remove(r.nextInt(options.size()));

            List<Position> neighbours = getNeighbours(maze, next, 2, false);
            if (neighbours.size() != 0) {
                Position neigbour = neighbours.get(r.nextInt(neighbours.size()));

                maze[next.getX()][next.getY()] = true;
                maze[(next.getX() + neigbour.getX()) / 2][(next.getY() + neigbour.getY()) / 2] = true;
                maze[neigbour.getX()][neigbour.getY()] = true;
            }

            getNeighbours(maze, next, 2, true).forEach(pos -> options.add(pos));
        }

        if (!maze[boundaryXRight][boundaryYBottom]) {
            maze[boundaryXRight][boundaryYBottom] = true;

            List<Position> neighbours = new ArrayList<Position>();
            neighbours.add(new Position(boundaryXRight - 1, boundaryYBottom));
            neighbours.add(new Position(boundaryXRight, boundaryYBottom - 1));
            if (neighbours.stream().anyMatch(p -> !maze[p.getX()][p.getY()])) {
                Position neighbour = neighbours.get(r.nextInt(neighbours.size()));
                maze[neighbour.getX()][neighbour.getY()] = true;
            }
        }

        return maze;
    }

    // Returns a list of neighbours to a position
    private static List<Position> getNeighbours(boolean[][] maze, Position pos, int dist, boolean areWalls) {
        
        List<Position> possibleNeigbours = new ArrayList<Position>();

        // Add the four possible neighbours
        possibleNeigbours.add(pos.translateBy(0, dist));
        possibleNeigbours.add(pos.translateBy(0, -dist));
        possibleNeigbours.add(pos.translateBy(dist, 0));
        possibleNeigbours.add(pos.translateBy(-dist, 0));
        
        return possibleNeigbours.stream().filter(p -> p.getX() >= 0 && p.getX() < maze.length && p.getY() >= 0 && p.getY() < maze[0].length && ((areWalls) ? !maze[p.getX()][p.getY()] : maze[p.getX()][p.getY()])).collect(Collectors.toList());
    }
}
