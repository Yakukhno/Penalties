import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class GameTest {
    private Game game;
    private Team team1;
    private Team team2;

    @Before
    public void setUp() throws Exception {
        team1 = new Team("Team1");
        team2 = new Team("Team2");
        game = new Game(team1, team2);
    }

    @Test
    public void oneKick() throws Exception {
        game.shot(team1, true);
    }

    @Test
    public void score10() throws Exception {
        series(true, false);
        series(false, false, 4);
        assertEquals("1:0", game.score());
    }

    @Test
    public void score23() throws Exception {
        series(true, false, 2);
        series(false, true, 3);
        assertEquals("2:3", game.score());
    }

    @Test
    public void winnerTeam1() throws Exception {
        series(true, true, 3);
        series(false, false);
        series(true, false);
        assertEquals("Team1", game.getWinner());
    }

    @Test
    public void winnerTeam2() throws Exception {
        series(false, true);
        series(false, false);
        series(true, true, 3);
        assertEquals("Team2", game.getWinner());
    }

    @Test
    public void gameNotFinished() throws Exception {
        series(true, true, 3);
        assertEquals("Game is not finished!", game.getWinner());
    }

    @Test
    public void drawAndNotFinished() throws Exception {
        series(true, true, 4);
        series(false, false);
        assertEquals("Game is not finished!", game.getWinner());
    }

    @Test
    public void winnerTeam1AfterSixKicks() throws Exception {
        series(true, true, 4);
        series(false, false);
        series(true, false);
        assertEquals("Team1", game.getWinner());
    }

    @Test(expected = IllegalStateException.class)
    public void notCountAfterFinished() throws Exception {
        series(false, false, 4);
        series(true, false);
        game.shot(team1, true);
    }

    @Test
    public void twoAdditionalKickForEach() throws Exception {
        series(true, true, 6);
        series(false, true);
        assertEquals("6[0]:[0]7", game.score());
    }

    @Test
    public void getPlayerHistory() throws Exception {
        Game spyGame = spy(game);
        when(spyGame.getPlayerHistory("Messi"))
                .thenReturn(new boolean[]{true, true, true, false, false, true});
        assertArrayEquals(new boolean[]{true, true, true, false, false, true},
                spyGame.shot("Messi", team1, true));
    }

    @Test
    public void sumCostOfLosers() throws Exception {
        Game spyGame = spy(game);
        when(spyGame.getPlayerCost("1A")).thenReturn(1000);
        when(spyGame.getPlayerCost("2A")).thenReturn(2000);
        when(spyGame.getPlayerCost("3A")).thenReturn(3000);
        when(spyGame.getPlayerCost("4A")).thenReturn(4000);
        when(spyGame.getPlayerCost("5A")).thenReturn(5000);

        when(spyGame.getPlayerCost("1B")).thenReturn(5000);
        when(spyGame.getPlayerCost("2B")).thenReturn(4000);
        when(spyGame.getPlayerCost("3B")).thenReturn(3000);
        when(spyGame.getPlayerCost("4B")).thenReturn(2000);
        when(spyGame.getPlayerCost("5B")).thenReturn(1000);

        spyGame.shot("1A", team1, false);
        spyGame.shot("1B",team2, false);
        spyGame.shot("2A",team1, true);
        spyGame.shot("2B",team2, false);
        spyGame.shot("3A",team1, false);
        spyGame.shot("3B",team2, true);
        spyGame.shot("4A",team1, true);
        spyGame.shot("4B",team2, true);
        spyGame.shot("5A",team1, true);
        spyGame.shot("5B",team2, true);
        spyGame.shot("1A",team1, true);
        spyGame.shot("1B",team2, true);
        spyGame.shot("2A",team1, true);
        spyGame.shot("2B",team2, false);

        assertEquals("5[4000]:[13000]4", spyGame.score());
    }

    @Test
    public void earlyFinish() throws Exception {
        series(true, false, 3);
        assertEquals("Team1", game.getWinner());
    }

    private void series(boolean isTeam1Scored, boolean isTeam2Scored) {
        game.shot(team1, isTeam1Scored);
        game.shot(team2, isTeam2Scored);
    }

    private void series(boolean isTeam1Scored, boolean isTeam2Scored, int times) {
        for (int i = 0; i < times; i++) {
            series(isTeam1Scored, isTeam2Scored);
        }
    }
}