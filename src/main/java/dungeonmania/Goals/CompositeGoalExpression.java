package dungeonmania.Goals;

import com.google.gson.JsonObject;

public class CompositeGoalExpression extends GoalExpression {
    private GoalExpression left;
    private GoalExpression right;

    public CompositeGoalExpression(String goal, GoalExpression left, GoalExpression right) {
        super(goal);
        this.left = left;
        this.right = right;
    }

    public CompositeGoalExpression(JsonObject goalJson) {
        super(goalJson.get("goal").getAsString());
        if (goalJson.getAsJsonArray("subgoals").get(0).getAsJsonObject().get("goal").getAsString().equals("AND") 
        || goalJson.getAsJsonArray("subgoals").get(0).getAsJsonObject().get("goal").getAsString().equals("OR")) {
            this.left = new CompositeGoalExpression(goalJson.getAsJsonArray("subgoals").get(0).getAsJsonObject());
        } else {
            this.left = new LeafGoalExpression(goalJson.getAsJsonArray("subgoals").get(0).getAsJsonObject().get("goal").getAsString());
        }

        if (goalJson.getAsJsonArray("subgoals").get(1).getAsJsonObject().get("goal").getAsString().equals("AND") 
        || goalJson.getAsJsonArray("subgoals").get(1).getAsJsonObject().get("goal").getAsString().equals("OR")) {
            this.right = new CompositeGoalExpression(goalJson.getAsJsonArray("subgoals").get(1).getAsJsonObject());
        } else {
            this.right = new LeafGoalExpression(goalJson.getAsJsonArray("subgoals").get(1).getAsJsonObject().get("goal").getAsString());
        }
    }

    public String toString() {
        if (isComplete()) return "";
        if (left.isComplete() && !right.isComplete()) return right.toString();
        if (right.isComplete() && !left.isComplete()) return left.toString();
        return "( ".concat(left.toString().concat(" ").concat(getGoal()).concat(" ").concat(right.toString()).concat(" )"));
    }

    @Override
    public void computeComplete() {
        left.computeComplete();
        right.computeComplete();
        if (getGoal().equals("AND")) {
            setComplete(left.isComplete() && right.isComplete());
        } else if (getGoal().equals("OR")) {
            setComplete(left.isComplete() || right.isComplete());
        }
    }
}
