package software.ryancook.gameengine;

import org.junit.*;
import software.ryancook.gameengine.tictactoe.*;
import software.ryancook.gameengine.tictactoe.TTTGameState.Piece;
import static org.junit.Assert.*;

public class TicTacToeTest
{
    Negamax negamax;
    GameState gameState;
    Evaluator evaluator;

    @Before
    public void setUp() throws Exception
    {
        gameState = new TTTGameState(3, 3);
        evaluator = new TTTEvaluator();
        negamax = new Negamax(evaluator);
        //negamax.setMaxTime(10000);
        negamax.setMaxTime(1000000);
    }

    @Test
    public void findEqualMove() throws Exception
    {
        gameState = gameState.playMove(new TTTMove(0, 0, Piece.X));
        final TTTMove move = (TTTMove) negamax.findBestMove(gameState);
        assertFalse(move.isNull());

        int expectedDepth = 9;
        //assertEquals(expectedDepth, negamax.getDepth());
    }

    @Test
    public void OBlocksX() throws Exception
    {
        // X O -
        // - ! -
        // - - X
        gameState = gameState.playMove(new TTTMove(0, 0, Piece.X));
        gameState = gameState.playMove(new TTTMove(1, 0, Piece.O));
        gameState = gameState.playMove(new TTTMove(2, 2, Piece.X));

        final int expectedX = 1;
        final int expectedY = 1;
        TTTMove move = (TTTMove) negamax.findBestMove(gameState);
        assertEquals(expectedX, move.getX());
        assertEquals(expectedY, move.getY());

        final int expectedDepth = 7;
        assertEquals(expectedDepth, negamax.getDepth());
    }

    @Test
    public void XBlocksO() throws Exception
    {
        // X O X
        // - O -
        // - ! -
        gameState = gameState.playMove(new TTTMove(0, 0, Piece.X));
        gameState = gameState.playMove(new TTTMove(1, 0, Piece.O));
        gameState = gameState.playMove(new TTTMove(2, 0, Piece.X));
        gameState = gameState.playMove(new TTTMove(1, 1, Piece.O));

        final int expectedX = 1;
        final int expectedY = 2;
        final TTTMove move = (TTTMove) negamax.findBestMove(gameState);
        assertEquals(expectedX, move.getX());
        assertEquals(expectedY, move.getY());

        final int expectedDepth = 6;
        assertEquals(expectedDepth, negamax.getDepth());
    }

    @Test
    public void XFindsWinInTwoPly() throws Exception
    {
        // O - !
        // - X !
        // - O X
        gameState = gameState.playMove(new TTTMove(1, 1, Piece.X));
        gameState = gameState.playMove(new TTTMove(0, 0, Piece.O));
        gameState = gameState.playMove(new TTTMove(2, 2, Piece.X));
        gameState = gameState.playMove(new TTTMove(1, 2, Piece.O));

        final int expectedX = 2;
        final TTTMove move = (TTTMove) negamax.findBestMove(gameState);
        assertEquals(expectedX, move.getX());

        final int expectedDepth = 2;
        assertEquals(expectedDepth, negamax.getDepth());
    }
}