package software.ryancook.gameengine.tictactoe;

import org.junit.Test;
import software.ryancook.gameengine.*;
import software.ryancook.gameengine.tictactoe.TTTGameState.Piece;
import java.security.InvalidParameterException;
import java.util.*;
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
        final List<Move> moves = gameState.getMoves();
        final TTTMove move = (TTTMove) moves.get(0);
        assertFalse(move.isNull());
    }

    @Test
    public void nullMoveChangesPly() throws Exception
    {
        final Move nullMove = new TTTMove();
        gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(nullMove);
        assertEquals(1, gameState.getPly());
    }

    @Test(expected=TTTGameState.IllegalMoveException.class)
    public void moveMustBeInBounds() throws Exception
    {
        final Move outOfBoundsMove = new TTTMove(5, 5, TTTGameState.Piece.X);
        gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(outOfBoundsMove);
    }

    @Test
    public void nullMoveDoesNotDecreaseMoveCount() throws Exception
    {
        final Move nullMove = new TTTMove();
        gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(nullMove);
        assertEquals(9, gameState.getMoves().size());
    }

    @Test
    public void validMoveDecreasesMoveCount() throws Exception
    {
        final Move move = new TTTMove(0, 0, TTTGameState.Piece.X);
        gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(move);
        assertEquals(8, gameState.getMoves().size());
    }

    @Test(expected=TTTGameState.IllegalMoveException.class)
    public void moveMustBeOnEmptySquare() throws Exception
    {
        final Move move = new TTTMove(0, 0, TTTGameState.Piece.X);
        gameState = new TTTGameState(3, 3);
        gameState = gameState.playMove(move);
        gameState = gameState.playMove(move);
    }

    @Test
    public void compareSamePlyAndDifferentPositions() throws Exception
    {
        GameState gameState1 = new TTTGameState(3, 3);
        gameState1 = gameState1.playMove(new TTTMove(0, 0, Piece.X));

        GameState gameState2 = new TTTGameState(3, 3);
        gameState2 = gameState2.playMove(new TTTMove(0, 0, Piece.O));

        assertNotEquals(gameState1, gameState2);
        assertNotEquals(gameState1.hashCode(), gameState2.hashCode());
    }

    @Test
    public void compareDifferentPlyAndSamePosition() throws Exception
    {
        GameState gameState1 = new TTTGameState(3, 3);
        GameState gameState2 = new TTTGameState(3, 3);
        gameState2 = gameState2.playMove(new TTTMove());

        assertEquals(gameState1, gameState2);
        assertEquals(gameState1.hashCode(), gameState2.hashCode());
    }

    @Test
    public void someTest() throws Exception
    {
        GameState gameState1 = new TTTGameState(3, 3);
        gameState1 = gameState1.playMove(new TTTMove(0, 0, Piece.X));
        gameState1 = gameState1.playMove(new TTTMove(0, 1, Piece.O));
        gameState1 = gameState1.playMove(new TTTMove(0, 2, Piece.X));
        gameState1 = gameState1.playMove(new TTTMove(1, 0, Piece.O));
        gameState1 = gameState1.playMove(new TTTMove(2, 2, Piece.X));

        GameState gameState2 = new TTTGameState(3, 3);
        gameState2 = gameState2.playMove(new TTTMove(0, 0, Piece.X));
        gameState2 = gameState2.playMove(new TTTMove(0, 1, Piece.O));
        gameState2 = gameState2.playMove(new TTTMove(1, 1, Piece.X));
        gameState2 = gameState2.playMove(new TTTMove(1, 0, Piece.O));
        gameState2 = gameState2.playMove(new TTTMove(2, 2, Piece.X));

        assertNotEquals(gameState1, gameState2);
        assertNotEquals(gameState1.hashCode(), gameState2.hashCode());
    }
}