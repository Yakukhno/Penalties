import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<Boolean> shots = new ArrayList<>();
    private int losersCost;

    public Team(String name) {
        this.name = name;
    }

    public void shot(boolean isGoal) {
        shots.add(isGoal);
    }

    public int getCurrentShot() {
        return shots.size();
    }

    public int getGoals() {
        return getGoals(shots.size());
    }

    public int getGoals(int toShot) {
        int score = 0;
        for (int i = 0; i < toShot; i++) {
            if (shots.get(i)) {
                score++;
            }
        }
        return score;
    }

    public void addLoserCost(int loserCost) {
        losersCost += loserCost;
    }

    public String getName() {
        return name;
    }

    public int getLosersCost() {
        return losersCost;
    }
}
