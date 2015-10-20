package software.ryancook.gameengine;

import java.util.*;

public class GameStateCache
{
    private Map<Integer, Result> table;

    public GameStateCache()
    {
        table = new HashMap<>();
    }

    public void put(GameState gameState, int score, int evaluationDepth)
    {
        Result result = new Result(gameState.getPly(), score, evaluationDepth);
        table.put(gameState.hashCode(), result);
    }

    public int size()
    {
        return table.size();
    }

    public boolean hasPosition(GameState gameState)
    {
        return table.containsKey(gameState.hashCode());
    }

    public int getScore(GameState gameState)
    {
        return table.get(gameState.hashCode()).getScore();
    }

    public int getEvaluationDepth(GameState gameState)
    {
        return table.get(gameState.hashCode()).getEvaluationDepth();
    }

    private class Result
    {
        int ply;
        int score;
        int evaluationDepth;

        Result(int ply, int score, int evaluationDepth)
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
