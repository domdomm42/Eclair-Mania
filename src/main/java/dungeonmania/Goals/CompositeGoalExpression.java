package dungeonmania.Goals;

import org.json.JSONObject;

public class CompositeGoalExpression extends GoalExpression {
    private GoalExpression left;
    private GoalExpression right;

    public CompositeGoalExpression(String goal, GoalExpression left, GoalExpression right) {
        super(goal);
        this.left = left;
        this.right = right;
    }

    public CompositeGoalExpression(JSONObject goalJson) {
        super(goalJson.getString("goal"));
        if (goalJson.getJSONArray("subgoals").getJSONObject(0).getString("goal").equals("AND") 
        || goalJson.getJSONArray("subgoals").getJSONObject(0).getString("goal").equals("OR")) {
            this.left = new CompositeGoalExpression(goalJson.getJSONArray("subgoals").getJSONObject(0));
        } else {
            this.left = new LeafGoalExpression(goalJson.getJSONArray("subgoals").getJSONObject(0).getString("goal"));
        }

        if (goalJson.getJSONArray("subgoals").getJSONObject(1).getString("goal").equals("AND") 
        || goalJson.getJSONArray("subgoals").getJSONObject(1).getString("goal").equals("OR")) {
            this.right = new CompositeGoalExpression(goalJson.getJSONArray("subgoals").getJSONObject(1));
        } else {
            this.right = new LeafGoalExpression(goalJson.getJSONArray("subgoals").getJSONObject(1).getString("goal"));
        }
    }

    public String toString() {
        if (isComplete()) return "";
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
