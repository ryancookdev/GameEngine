package software.ryancook.gameengine;

import java.util.*;

public class Negamax
{
    public static final int BEST_SCORE = 99999;
    public static final int WORST_SCORE = -BEST_SCORE;
    private static final int DEFAULT_MAX_TIME = 1000;
    private long maxTime;
    private long startTime;
    private int maxDepth;
    private int actualIterationDepth;
    private int startDepth;
    private final Evaluator evaluator;
    private GameStateCache cache;

    public Negamax(final Evaluator evaluator)
    {
        this.maxTime = DEFAULT_MAX_TIME;
        this.evaluator = evaluator;
        this.cache = new GameStateCache();
    }

    public void setMaxTime(final int maxTime)
    {
        this.maxTime = maxTime;
    }

    public int getDepth()
    {
        return maxDepth;
    }

    public Move findBestMove(final GameState gameState)
    {
        startTime();
        resetCache();
        Move bestMove = null;
        startDepth = gameState.getPly();
        for (maxDepth = 0; maxDepth >= 0; maxDepth++) {
            actualIterationDepth = 0;
            boolean discardIteration = false;
            int alpha = WORST_SCORE;
            List<Move> moves = getSortedMoves(gameState);
            Move bestMoveThisIteration = null;
            for (final Move move : moves) {
                GameState newGameState = gameState.playMove(move);
                int score = (-negamax(newGameState, WORST_SCORE, -alpha));
                if (score == BEST_SCORE) {
                    return move;
                }
                cache.put(newGameState, score, maxDepth);
                if (alpha < score || bestMoveThisIteration == null) {
                    bestMoveThisIteration = move;
                    alpha = score;
                }
                if (outOfTime()) {
                    discardIteration = true;
                    break;
                }
            }
            if (discardIteration) {
                break;
            }
            bestMove = bestMoveThisIteration;
            if (outOfTime()) {
                break;
            }
            if (actualIterationDepth < maxDepth) {
                break;
            }
        }
        return bestMove;
    }

    private void resetCache()
    {
        cache = new GameStateCache();
        assert cache.size() == 0;
    }

    private int negamax(final GameState gameState, int alpha, final int beta)
    {
        final List<Move> moves = getMoves(gameState);

        if (reachedMaximumDepth(gameState) && moves.size() == 0) { // Quiescent
            return getSubjectiveScore(gameState);
        }
        if (moves.size() == 0) {
            return getSubjectiveScore(gameState);
        }

        if (reachedMaximumDepth(gameState)) {
            moves.add(gameState.getNullMove());
        }

        for (final Move move : moves) {
            if (outOfTime()) {
                return beta;
            }
            final int score = getScore(gameState, move, alpha, beta);
            if (score >= beta) {
                return score; // fail hard beta-cutoff
            }
            if (score > alpha) {
                alpha = score;
            }
        }

        return alpha;
    }

    private int getSubjectiveScore(final GameState gameState)
    {
        updateActualIterationDepth(gameState);

        int score = evaluator.eval(gameState);
        if (!gameState.isFirstPlayerToMove()) {
            score = -score;
        }
        return score;
    }

    private void updateActualIterationDepth(final GameState gameState)
    {
        final int depthForBranch = gameState.getPly() - startDepth;
        if (actualIterationDepth < depthForBranch) {
            actualIterationDepth = depthForBranch;
        }
    }

    private List<Move> getMoves(final GameState gameState)
    {
        return (reachedMaximumDepth(gameState) ? getCriticalMoves(gameState) : getSortedMoves(gameState));
    }

    private int getScore(final GameState gameState, final Move move, final int alpha, final int beta)
    {
        if (move.isNull()) {
            return getSubjectiveScore(gameState);
        }

        final GameState newGameState = gameState.playMove(move);

        if (hasPositionAtMaximumDepth(newGameState)) {
            return cache.getScore(newGameState);
        }

        if (reachedMaximumDepth(newGameState)) {
            final int score = -getSubjectiveScore(newGameState);
            if (score < alpha) { // Delta pruning
                cache.put(newGameState, score, maxDepth);
                return score;
            }
        }

        final int score = -negamax(newGameState, -beta, -alpha);
        cache.put(newGameState, score, maxDepth);

        return score;
    }

    private boolean hasPositionAtMaximumDepth(final GameState gameState)
    {
        if (cache.hasPosition(gameState)) {
            if (cache.getEvaluationDepth(gameState) >= maxDepth) {
                return true;
            }
        }
        return false;
    }

    private void startTime()
    {
        startTime = System.currentTimeMillis();
    }

    private boolean outOfTime()
    {
        return System.currentTimeMillis() - startTime > maxTime;
    }

    private boolean reachedMaximumDepth(final GameState gameState)
    {
        return (gameState.getPly() > startDepth + maxDepth);
    }

    private List<Move> getSortedMoves(final GameState gameState)
    {
        final List<Move> moves = gameState.getMoves();
        return evaluator.sortMoves(gameState, moves);
    }

    private List<Move> getCriticalMoves(final GameState gameState)
    {
        final List<Move> moves = gameState.getCriticalMoves();
        return evaluator.sortMoves(gameState, moves);
    }
}
