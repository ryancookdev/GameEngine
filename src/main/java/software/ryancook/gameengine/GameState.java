package software.ryancook.gameengine;

import software.ryancook.gameengine.tictactoe.TTTGameState;
import java.util.List;

public interface GameState
{
    int getPly();

    boolean isFirstPlayerToMove();

    List<Move> getMoves();

    Move getNullMove();

    GameState playMove(Move move);

    List<Move> getCriticalMoves();
}