package dungeonmania.Goals;

abstract public class GoalExpression {
    abstract public String toString();
    private String goal;
    private boolean isComplete;

    public GoalExpression(String goal) {
        this.goal = goal;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    abstract public void computeComplete();
}
