package software.ryancook.gameengine;

import org.junit.*;
import software.ryancook.gameengine.tictactoe.*;
import software.ryancook.gameengine.tictactoe.TTTGameState.Piece;
import static org.junit.Assert.*;

public class GameStateCacheTest
{
    GameStateCache cache;
    final int evaluationDepth = 1;

    @Before
    public void setUp() throws Exception
    {
        cache = new GameStateCache();
    }

    @Test
    public void duplicateKeyIsOverwritten() throws Exception
    {
        final int firstScore = -100;
        final int secondScore = 100;
        cache.put(new TTTGameState(3, 3), firstScore, evaluationDepth);
        cache.put(new TTTGameState(3, 3), secondScore, evaluationDepth + 1);
        assertEquals(1, cache.size());
    }

    @Test
    public void uniqueKeyIsInserted() throws Exception
    {
        final int score = 0;

        GameState gameState = new TTTGameState(3, 3);
        cache.put(gameState, score, evaluationDepth);

        gameState = gameState.playMove(new TTTMove(0, 0, Piece.X));
        cache.put(gameState, score, evaluationDepth);

        assertEquals(2, cache.size());
    }

    @Test
    public void lookUpPosition() throws Exception
    {
        GameState gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(new TTTMove(0, 0, Piece.X));
        cache.put(gameState, 0, evaluationDepth);
        assertTrue(cache.hasPosition(gameState));
        assertFalse(cache.hasPosition(new TTTGameState(3, 3)));
    }

    @Test
    public void lookUpScore() throws Exception
    {
        cache.put(new TTTGameState(3, 3), 123, evaluationDepth);
        assertEquals(123, cache.getScore(new TTTGameState(3, 3)));
    }
}