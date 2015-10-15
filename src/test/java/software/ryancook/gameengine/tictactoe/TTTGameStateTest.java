package software.ryancook.gameengine.tictactoe;

import org.junit.Test;
import software.ryancook.gameengine.*;
import java.security.InvalidParameterException;
import java.util.List;

import static org.junit.Assert.*;

public class TTTGameStateTest
{
    GameState gameState;

    @Test(expected=InvalidParameterException.class)
    public void boardSizeMustHavePositiveValues() throws Exception
    {
        gameState = new TTTGameState(-1, -1);
    }

    @Test
    public void newGameHasPlyZero() throws Exception
    {
        gameState = new TTTGameState(3, 3);
        assertEquals(0, gameState.getPly());
    }

    @Test
    public void getNullMove() throws Exception
    {
        gameState = new TTTGameState(0, 0);
        assertTrue(gameState.getNullMove().isNull());
    }

    @Test
    public void newGameHasHeightTimesWidthMoves() throws Exception
    {
        gameState = new TTTGameState(3, 3);
        assertEquals(9, gameState.getMoves().size());
    }

    @Test
    public void movesMustNotBeNull() throws Exception
    {
        gameState = new TTTGameState(3, 3);
        List<Move> moves = gameState.getMoves();
        TTTMove move = (TTTMove) moves.get(0);
        assertFalse(move.isNull());
    }

    @Test
    public void nullMoveChangesPly() throws Exception
    {
        Move nullMove = new TTTMove();
        gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(nullMove);
        assertEquals(1, gameState.getPly());
    }

    @Test(expected=TTTGameState.IllegalMoveException.class)
    public void moveMustBeInBounds() throws Exception
    {
        Move outOfBoundsMove = new TTTMove(5, 5, TTTGameState.Piece.X);
        gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(outOfBoundsMove);
    }

    @Test
    public void nullMoveDoesNotDecreaseMoveCount() throws Exception
    {
        Move nullMove = new TTTMove();
        gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(nullMove);
        assertEquals(9, gameState.getMoves().size());
    }

    @Test
    public void validMoveDecreasesMoveCount() throws Exception
    {
        Move move = new TTTMove(0, 0, TTTGameState.Piece.X);
        gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(move);
        assertEquals(8, gameState.getMoves().size());
    }

    @Test(expected=TTTGameState.IllegalMoveException.class)
    public void moveMustBeOnEmptySquare() throws Exception
    {
        Move move = new TTTMove(0, 0, TTTGameState.Piece.X);
        gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(move);
        gameState = gameState.playMove(move);
    }
}