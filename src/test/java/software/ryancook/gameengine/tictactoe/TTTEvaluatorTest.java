package software.ryancook.gameengine.tictactoe;

import org.junit.Test;
import software.ryancook.gameengine.*;
import software.ryancook.gameengine.tictactoe.TTTGameState.Piece;
import static org.junit.Assert.*;

public class TTTEvaluatorTest
{
    @Test
    public void startPositionIsEven() throws Exception
    {
        final GameState gameState = new TTTGameState(3, 3);
        final Evaluator evaluator = new TTTEvaluator();
        final int score = evaluator.eval(gameState);
        assertEquals(0, score);
    }

    @Test
    public void winForX() throws Exception
    {
        GameState gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(new TTTMove(0, 0, Piece.X));
        gameState = gameState.playMove(new TTTMove(1, 0, Piece.X));
        gameState = gameState.playMove(new TTTMove(2, 0, Piece.X));

        final Evaluator evaluator = new TTTEvaluator();
        final int score = evaluator.eval(gameState);
        assertEquals(99999, score);
    }

    @Test
    public void winForO() throws Exception
    {
        GameState gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(new TTTMove(0, 0, Piece.O));
        gameState = gameState.playMove(new TTTMove(1, 1, Piece.O));
        gameState = gameState.playMove(new TTTMove(2, 2, Piece.O));

        final Evaluator evaluator = new TTTEvaluator();
        final int score = evaluator.eval(gameState);
        assertEquals(-99999, score);
    }

    @Test(expected=TTTEvaluator.IllegalPositionException.class)
    public void bothPlayersCanNotWin() throws Exception
    {
        GameState gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(new TTTMove(0, 0, Piece.X));
        gameState = gameState.playMove(new TTTMove(1, 0, Piece.X));
        gameState = gameState.playMove(new TTTMove(2, 0, Piece.X));

        gameState = gameState.playMove(new TTTMove(0, 2, Piece.O));
        gameState = gameState.playMove(new TTTMove(1, 2, Piece.O));
        gameState = gameState.playMove(new TTTMove(2, 2, Piece.O));

        final Evaluator evaluator = new TTTEvaluator();
        evaluator.eval(gameState);
    }

}