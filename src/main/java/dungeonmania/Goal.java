package dungeonmania;

import org.json.JSONObject;

import dungeonmania.Goals.GoalExpression;

public class Goal {
    private GoalExpression goal;

    public Goal(JSONObject goalJson) {
        {
            "goal": "AND",
            "subgoals": [
                {
                    "goal": "AND",
                    "subgoals": [
                        {
                            "goal": "exit"
                        },
                        {
                            "goal": "treasure"
                        }
                    ]
                },
                {
                    "goal": "AND",
                    "subgoals": [
                        {
                            "goal": "boulders"
                        },
                        {
                            "goal": "enemies"
                        }
                    ]
                }]
        }
    }
}
