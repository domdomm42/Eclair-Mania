package dungeonmania.Goals;

public class CompositeGoalExpression extends GoalExpression {
    private GoalExpression left;
    private GoalExpression right;

    public CompositeGoalExpression(String goal, GoalExpression left, GoalExpression right) {
        super(goal);
        this.left = left;
        this.right = right;
    }

    public String toString() {
        return "( ".concat(left.toString().concat(" ").concat(getGoal()).concat(" ").concat(right.toString()).concat(" )"));
    }

    @Override
    public void computeComplete() {
        if (getGoal().equals("AND")) {
            setComplete(left.isComplete() && right.isComplete());
        } else if (getGoal().equals("OR")) {
            setComplete(left.isComplete() || right.isComplete());
        }
    }
}
