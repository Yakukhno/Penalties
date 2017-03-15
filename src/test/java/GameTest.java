import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class GameTest {
    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @Test
    public void oneKick() throws Exception {
        game.kick(0, true);
    }

    @Test
    public void score10() throws Exception {
        game.kick(0, true);
        game.kick(1, false);
        game.kick(0, false);
        game.kick(1, false);
        game.kick(0, false);
        game.kick(1, false);
        game.kick(0, false);
        game.kick(1, false);
        game.kick(0, false);
        game.kick(1, false);
        assertEquals("1:0", game.score());
    }

    @Test
    public void score23() throws Exception {
        game.kick(0, true);
        game.kick(1, false);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, false);
        game.kick(1, false);
        game.kick(0, false);
        game.kick(1, true);
        game.kick(0, false);
        game.kick(1, true);
        assertEquals("2:3", game.score());
    }

    @Test
    public void winnerTeam1() throws Exception {
        game.kick(0, true);
        game.kick(1, false);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, false);
        game.kick(1, false);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, true);
        assertEquals("Team1", game.getWinner());
    }

    @Test
    public void winnerTeam2() throws Exception {
        game.kick(0, false);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, false);
        game.kick(1, false);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, true);
        assertEquals("Team2", game.getWinner());
    }

    @Test
    public void gameNotFinished() throws Exception {
        game.kick(0, false);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, false);
        game.kick(1, false);
        game.kick(0, true);
        game.kick(1, true);
        assertEquals("Not finished", game.getWinner());
    }

    @Test
    public void drawAndNotFinished() throws Exception {
        game.kick(0, false);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, false);
        game.kick(0, false);
        game.kick(1, false);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, true);
        assertEquals("Not finished", game.getWinner());
    }

    @Test
    public void winnerTeam1AfterSixKicks() throws Exception {
        game.kick(0, false);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, false);
        game.kick(0, false);
        game.kick(1, false);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, false);
        assertEquals("Team1", game.getWinner());
    }

    @Test(expected = IllegalStateException.class)
    public void notCountAfterFinished() throws Exception {
        game.kick(0, true);
        game.kick(1, false);
        game.kick(0, true);
        game.kick(1, false);
        game.kick(0, false);
        game.kick(1, false);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, false);
        game.kick(1, false);
        game.kick(0, true);
        game.kick(1, true);
    }

    @Test
    public void twoAdditionalKickForEach() throws Exception {
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, true);
        game.kick(1, true);
        game.kick(0, false);
        game.kick(1, true);
        assertEquals("6[0]:[0]7", game.score());
    }

    @Test
    public void getPlayerHistory() throws Exception {
        Game spyGame = spy(game);
        when(spyGame.getPlayerHistory("Messi"))
                .thenReturn(new boolean[]{true, true, true, false, false, true});
        assertArrayEquals(new boolean[]{true, true, true, false, false, true},
                spyGame.kick("Messi", 0, true));
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

        spyGame.kick("1A", 0, false);
        spyGame.kick("1B",1, false);
        spyGame.kick("2A",0, true);
        spyGame.kick("2B",1, false);
        spyGame.kick("3A",0, false);
        spyGame.kick("3B",1, true);
        spyGame.kick("4A",0, true);
        spyGame.kick("4B",1, true);
        spyGame.kick("5A",0, true);
        spyGame.kick("5B",1, true);
        spyGame.kick("1A",0, true);
        spyGame.kick("1B",1, true);
        spyGame.kick("2A",0, true);
        spyGame.kick("2B",1, false);

        assertEquals("5[4000]:[13000]4", spyGame.score());
    }

    @Test
    public void earlyFinish() throws Exception {
        game.kick(0, true);
        game.kick(1, false);
        game.kick(0, true);
        game.kick(1, false);
        game.kick(0, true);
        game.kick(1, false);
        assertEquals("Team1", game.getWinner());
    }
}