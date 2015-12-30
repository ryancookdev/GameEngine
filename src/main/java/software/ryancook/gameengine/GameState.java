package software.ryancook.gameengine;

import java.util.List;

public interface GameState
{
    /**
     * @return the current ply count
     */
    int getPly();

    /**
     * Tests if evaluations should be positive or negative.
     * This is a limitation on the number of players, as <code>Negamax</code> assumes that
     * a false return value implies a specific player (the second player) has the turn.
     * In games with more than two players, this assumption would be incorrect,
     * so <code>GameState</code> cannot represent a game with more than two players.
     *
     * @return true if the turn belongs to the first player, otherwise false
     */
    boolean isFirstPlayerToMove();

    /**
     *
     * @return the list of all legal moves in the current position
     */
    List<Move> getMoves();

    /**
     *
     * @return a null move
     */
    Move getNullMove();

    /**
     *
     * @param move  The move to be played in the current position
     * @return a new <code>GameState</code>, representing the position after the move has been played
     */
    GameState playMove(Move move);

    /**
     * Returns a list of moves that must be evaluated even if <code>Negamax</code> reaches maximum depth.
     * This method is used by the quiescence search, when <code>Negamax</code> tries to stop calculating
     * as soon as possible and begin evaluation of the resulting position.
     *
     * @return a list of critical moves
     */
    List<Move> getCriticalMoves();
}