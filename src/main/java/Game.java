public class Game {
    private boolean[] team1 = new boolean[100];
    private boolean[] team2 = new boolean[100];
    private int currentKickTeam1;
    private int currentKickTeam2;
    private int currentKick;

    private int costOfLosersTeam1;
    private int costOfLosersTeam2;

    public void kick(int team, boolean isScored) {
        if ((currentKick < 5) || (getTeamScore(team1) == getTeamScore(team2))
                && !(isEarlyFinishTeam1() || isEarlyFinishTeam2())) {
            if (team == 0) {
                team1[currentKickTeam1++] = isScored;
            } else if (team == 1) {
                team2[currentKickTeam2++] = isScored;
                currentKick++;
            }
        } else {
            throw new IllegalStateException("Game is finished!");
        }
    }

    private boolean isEarlyFinishTeam2() {
        return (currentKickTeam1 < 5) && (getTeamScore(team2) - getTeamScore(team1)) > (5 - currentKickTeam1);
    }

    private boolean isEarlyFinishTeam1() {
        return (currentKickTeam1 < 5) && (getTeamScore(team1) - getTeamScore(team2)) > (5 - currentKickTeam2);
    }

    public String score() {
        if (currentKick < 7) {
            return getTeamScore(team1) + ":" + getTeamScore(team2);
        } else {
            return getTeamScore(team1) + "[" + costOfLosersTeam1 + "]"
                    + ":"
                    + "[" + costOfLosersTeam2 + "]" + getTeamScore(team2);
        }
    }

    private int getTeamScore(boolean[] team) {
        int score = 0;
        for (int i = 0; i < currentKick; i++) {
            if (team[i]) {
                score++;
            }
        }
        return score;
    }


    public String getWinner() {
        String winner = "Not finished";
        if (currentKick >= 5
                || isEarlyFinishTeam1()
                || isEarlyFinishTeam2()) {
            int score1 = getTeamScore(team1);
            int score2 = getTeamScore(team2);
            if (score1 > score2) {
                winner = "Team1";
            } else if (score1 < score2) {
                winner = "Team2";
            }
        }
        return winner;
    }

    public boolean[] kick(String player, int team, boolean isScored) {
        kick(team, isScored);
        if (!isScored) {
            if (team == 0) {
                costOfLosersTeam1 += getPlayerCost(player);
            } else if (team == 1) {
                costOfLosersTeam2 += getPlayerCost(player);
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
