package dungeonmania.Goals;

import dungeonmania.Dungeon;

public class LeafGoalExpression extends GoalExpression {

    public LeafGoalExpression(String goal) {
        super(goal);
    }

    public String toString() {
        return this.getGoal();
    }

    @Override
    public void computeComplete() {
        if (Dungeon.getCompletedGoals().stream().anyMatch(goal -> goal.equals(getGoal())));
    }
}
