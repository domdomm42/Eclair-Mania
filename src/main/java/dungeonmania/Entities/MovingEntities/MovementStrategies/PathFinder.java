package dungeonmania.Entities.MovingEntities.MovementStrategies;

import java.util.ArrayList;
import java.util.Queue;

import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PathFinder {
    private final Position fromPosition;
    private final Position toPosition;
    private final MovingEntity entity;
    private Queue<PathNode> queue;
    private ArrayList<PathNode> visited;

    public PathFinder(Position fromPosition, Position toPosition, MovingEntity entity) {
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
        this.entity = entity;
        visited = new ArrayList<PathNode>();
        addInitialNodesToQueue();
    }

    public Direction computePath() {
        while (!queue.isEmpty()) {
            PathNode pathNode = queue.poll();
            if (visited.contains(pathNode)) continue;
            visited.add(pathNode);
            if (pathNode.getPosition().equals(toPosition)) {
                return pathNode.getInitialDirection();
            } else {
                addAdjacentNodesToQueue(pathNode);
            }
        }
        return null;
    }

    public boolean isNodeValid(PathNode pathNode) {
        return entity.getMovementStrategy().isValidMove(pathNode.getPosition()) && !visited.contains(pathNode);
    }

    private void addInitialNodesToQueue() {
        PathNode nodeUp = new PathNode(fromPosition.translateBy(Direction.UP), Direction.UP);
        PathNode nodeLeft = new PathNode(fromPosition.translateBy(Direction.LEFT), Direction.LEFT);
        PathNode nodeRight = new PathNode(fromPosition.translateBy(Direction.RIGHT), Direction.RIGHT);
        PathNode nodeDown = new PathNode(fromPosition.translateBy(Direction.DOWN), Direction.DOWN);
        if (isNodeValid(nodeUp)) queue.add(nodeUp);
        if (isNodeValid(nodeLeft)) queue.add(nodeLeft);
        if (isNodeValid(nodeRight)) queue.add(nodeRight);
        if (isNodeValid(nodeDown)) queue.add(nodeDown);
    }

    private void addAdjacentNodesToQueue(PathNode pathNode) {
        PathNode nodeUp = new PathNode(fromPosition.translateBy(Direction.UP), pathNode.getInitialDirection());
        PathNode nodeLeft = new PathNode(fromPosition.translateBy(Direction.LEFT), pathNode.getInitialDirection());
        PathNode nodeRight = new PathNode(fromPosition.translateBy(Direction.RIGHT), pathNode.getInitialDirection());
        PathNode nodeDown = new PathNode(fromPosition.translateBy(Direction.DOWN), pathNode.getInitialDirection());
        if (isNodeValid(nodeUp)) queue.add(nodeUp);
        if (isNodeValid(nodeLeft)) queue.add(nodeLeft);
        if (isNodeValid(nodeRight)) queue.add(nodeRight);
        if (isNodeValid(nodeDown)) queue.add(nodeDown);
    }
}
