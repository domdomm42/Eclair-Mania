package dungeonmania;

import org.json.JSONObject;

import dungeonmania.Goals.CompositeGoalExpression;
import dungeonmania.Goals.GoalExpression;
import dungeonmania.Goals.LeafGoalExpression;

public class Goal {
    private GoalExpression goal;

    public Goal(JSONObject goalJson) {
        if (goalJson.getString("goal").equals("AND") || goalJson.getString("goal").equals("OR")) goal = new CompositeGoalExpression(goalJson);
        else goal = new LeafGoalExpression(goalJson.getString("goal"));
    }

    public String toString() {
        return goal.toString();
    }

    public boolean isComplete() {
        return goal.isComplete();
    }

    public void computeComplete() {
        goal.computeComplete();
    }


}
