package software.ryancook.gameengine;

import java.util.*;

public class GameStateCache
{
    private final Map<Integer, Result> table;

    public GameStateCache()
    {
        table = new HashMap<>();
    }

    public void put(final GameState gameState, final int score, final int evaluationDepth)
    {
        final Result result = new Result(gameState.getPly(), score, evaluationDepth);
        table.put(gameState.hashCode(), result);
    }

    public int size()
    {
        return table.size();
    }

    public boolean hasPosition(final GameState gameState)
    {
        return table.containsKey(gameState.hashCode());
    }

    public int getScore(final GameState gameState)
    {
        return table.get(gameState.hashCode()).getScore();
    }

    public int getEvaluationDepth(final GameState gameState)
    {
        return table.get(gameState.hashCode()).getEvaluationDepth();
    }

    private class Result
    {
        final int ply;
        final int score;
        final int evaluationDepth;

        Result(final int ply, final int score, final int evaluationDepth)
        {
            this.ply = ply;
            this.score = score;
            this.evaluationDepth = evaluationDepth;
        }

        int getPly()
        {
            return ply;
        }

        int getScore()
        {
            return score;
        }

        int getEvaluationDepth()
        {
            return evaluationDepth;
        }
    }
}
