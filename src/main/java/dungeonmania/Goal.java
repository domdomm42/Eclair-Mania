package dungeonmania;

import com.google.gson.JsonObject;

import dungeonmania.Goals.CompositeGoalExpression;
import dungeonmania.Goals.GoalExpression;
import dungeonmania.Goals.LeafGoalExpression;

public class Goal {
    private GoalExpression goal;

    public Goal(JsonObject goalJson) {
        if (goalJson.get("goal").getAsString().equals("AND") || goalJson.get("goal").getAsString().equals("OR")) goal = new CompositeGoalExpression(goalJson);
        else goal = new LeafGoalExpression(goalJson.get("goal").getAsString());
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
