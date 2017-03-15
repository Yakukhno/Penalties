public class Game {
    private static final int USUALLY_ATTEMPTS = 5;
    private static final int SERIES_TO_SHOW_COST = 7;

    private Team team1 = new Team("Team1");
    private Team team2 = new Team("Team2");
    private int currentShot;

    public void shot(int team, boolean isGoal) {
        if ((currentShot < USUALLY_ATTEMPTS)
                || (team1.getGoals(currentShot) == team2.getGoals(currentShot))
                && !(isEarlyFinishTeam1() || isEarlyFinishTeam2())) {
            if (team == 0) {
                team1.shot(isGoal);
            } else if (team == 1) {
                team2.shot(isGoal);
                currentShot++;
            }
        } else {
            throw new IllegalStateException("Game is finished!");
        }
    }

    private boolean isEarlyFinishTeam2() {
        return (team1.getCurrentShot() < USUALLY_ATTEMPTS)
                && ((team2.getGoals() - team1.getGoals())
                        > (USUALLY_ATTEMPTS - team1.getCurrentShot()));
    }

    private boolean isEarlyFinishTeam1() {
        return (team2.getCurrentShot() < USUALLY_ATTEMPTS)
                && ((team1.getGoals() - team2.getGoals())
                        > (USUALLY_ATTEMPTS - team2.getCurrentShot()));
    }

    public String score() {
        if (currentShot < SERIES_TO_SHOW_COST) {
            return team1.getGoals() + ":" + team2.getGoals();
        } else {
            return team1.getGoals() + "[" + team1.getLosersCost() + "]"
                    + ":"
                    + "[" + team2.getLosersCost() + "]" + team2.getGoals();
        }
    }

    public String getWinner() {
        String winner = "Not finished";
        if (currentShot >= USUALLY_ATTEMPTS
                || isEarlyFinishTeam1()
                || isEarlyFinishTeam2()) {
            if (team1.getGoals() > team2.getGoals()) {
                winner = team1.getName();
            } else if (team1.getGoals() < team2.getGoals()) {
                winner = team2.getName();
            }
        }
        return winner;
    }

    public boolean[] shot(String player, int team, boolean isScored) {
        shot(team, isScored);
        if (!isScored) {
            if (team == 0) {
                team1.addLoserCost(getPlayerCost(player));
            } else if (team == 1) {
                team2.addLoserCost(getPlayerCost(player));
            }
        }
        return getPlayerHistory(player);
    }

    public boolean[] getPlayerHistory(String player) {
        return null;
    }

    public int getPlayerCost(String player) {
        return 0;
    }
}
