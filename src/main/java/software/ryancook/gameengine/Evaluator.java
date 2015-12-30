package software.ryancook.gameengine;

import java.util.List;

public interface Evaluator
{
    /**
     * Determines the value of the position.
     * <p>
     * Position evaluation is expected to be a static process.
     * <code>Negamax</code> is responsible for dynamic evaluation (calculation),
     * so ideally this should determine a value without considering legal moves.
     * There may be cases where looking one or two ply ahead can assist static
     * evaluation (eg. deciding if the current position is check or mate).
     * In such cases, it is appropriate to write a simple function for looking
     * ahead, without duplicating the work of <code>Negamax</code>.
     * <p>
     * Minimum and maximum values should be statically imported from
     * <code>Negamax.BEST_SCORE</code> and <code>Negamax.WORST_SCORE</code>.
     * Aside from these values, the <code>Evaluator</code> implementation is free to use
     * any integer based scale.
     * <p>
     * Negative values indicate a favorable position for the second player.
     * Positive values indicate a favorable position for the first player.
     * A value of zero indicates a position that is perfectly balanced or drawn.
     *
     * @param gameState  the position to be evaluated
     * @return the value of the position
     */
    int eval(GameState gameState);

    /**
     * Sorts a list of moves according to the evaluation of the resulting position.
     *
     * @param gameState  the base position, before each move is played and evaluated
     * @param moves  the unsorted list of moves corresponding to <code>gameState</code>
     * @return the list of moves sorted by evaluation of the resulting position
     */
    List<Move> sortMoves(GameState gameState, List<Move> moves);
}