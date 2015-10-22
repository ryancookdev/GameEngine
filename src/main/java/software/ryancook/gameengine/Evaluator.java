package software.ryancook.gameengine;

import java.util.List;

public interface Evaluator
{
    int eval(GameState gameState);

    List<Move> sortMoves(GameState gameState, List<Move> moves);
}