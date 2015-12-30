package software.ryancook.gameengine;

public interface Move
{
    /**
     * Tests if the move is a null move.
     * <p>
     * A null move is any move that is expected to behave as follows when
     * passed to <code>gameState.playMove()</code>:
     * <p><ul>
     * <li>increase the ply
     * <li>advance the turn to the next player
     * <li>have no additional effects on the remainder of the position
     * </ul><p>
     *
     * @return true if the move is a null move, otherwise false
     */
    boolean isNull();
}