package dungeonmania.Goals;

import dungeonmania.Dungeon;

public class LeafGoalExpression extends GoalExpression {

    public LeafGoalExpression(String goal) {
        super(":".concat(goal));
    }

    public String toString() {
        if (isComplete()) return "";
        return getGoal();
    }

    @Override
    public void computeComplete() {
        if (Dungeon.getCompletedGoals().stream().anyMatch(goal -> goal.equals(getGoal()))) setComplete(true);
    }
}
