public class Game {
    private static final int BASIC_ATTEMPTS = 5;
    private static final int SERIES_TO_SHOW_COST = 7;
    private static final String GAME_IS_FINISHED = "Game is finished!";
    private static final String GAME_IS_NOT_FINISHED = "Game is not finished!";

    private Team team1;
    private Team team2;
    private int currentShot;

    public Game(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public void shot(Team team, boolean isGoal) {
        if ((currentShot < BASIC_ATTEMPTS) || isEqualGoals() && !isEarlyFinish()) {
            team.shot(isGoal);
            if (team == team2) {
                currentShot++;
            }
        } else {
            throw new IllegalStateException(GAME_IS_FINISHED);
        }
    }

    private boolean isEarlyFinish() {
        return isEarlyFinishTeam1() || isEarlyFinishTeam2();
    }

    private boolean isEqualGoals() {
        return team1.getGoals(currentShot) == team2.getGoals(currentShot);
    }

    private boolean isEarlyFinishTeam1() {
        return (team2.getCurrentShot() < BASIC_ATTEMPTS)
                && ((team1.getGoals() - team2.getGoals())
                        > (BASIC_ATTEMPTS - team2.getCurrentShot()));
    }

    private boolean isEarlyFinishTeam2() {
        return (team1.getCurrentShot() < BASIC_ATTEMPTS)
                && ((team2.getGoals() - team1.getGoals())
                        > (BASIC_ATTEMPTS - team1.getCurrentShot()));
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
        String winner = GAME_IS_NOT_FINISHED;
        if (currentShot >= BASIC_ATTEMPTS || isEarlyFinish()) {
            if (team1.getGoals() > team2.getGoals()) {
                winner = team1.getName();
            } else if (team1.getGoals() < team2.getGoals()) {
                winner = team2.getName();
            }
        }
        return winner;
    }

    public boolean[] shot(String player, Team team, boolean isScored) {
        shot(team, isScored);
        if (!isScored) {
            team.addLoserCost(getPlayerCost(player));
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
