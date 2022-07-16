package dungeonmania;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Goal {
    private boolean isComplex;
    private ArrayList<Goal> goals;
    private boolean isAnd;

    public Goal(JSONObject goalJson) {
        // JSONObject objective = goalJson.getJSONObject("goal_conditions");
        // objective.getString(key)
        goalJson.getString("goal");
        

    }

    //composite goal and goal
        //left right      leaf

    // check if complete by checking positons
    public boolean isComplete() {

    }


}
