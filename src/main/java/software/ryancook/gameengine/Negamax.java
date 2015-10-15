package software.ryancook.gameengine;

import java.util.*;

public class Negamax
{
    private static final int DEFAULT_MAX_TIME = 1000;
    private static final int BEST_SCORE = 99999;
    private static final int WORST_SCORE = -BEST_SCORE;
    private long maxTime;
    private long startTime;
    private int maxDepth;
    private int actualIterationDepth;
    private int startDepth;
    private Evaluator evaluator;

    public Negamax(Evaluator evaluator)
    {
        this.maxTime = DEFAULT_MAX_TIME;
        this.evaluator = evaluator;
    }

    public void setMaxTime(int maxTime)
    {
        this.maxTime = maxTime;
    }

    public int getDepth()
    {
        return maxDepth;
    }

    public Move findBestMove(GameState gameState)
    {
        startTime();
        Move bestMove = null;
        startDepth = gameState.getPly();
        for (maxDepth = 0; maxDepth >= 0; maxDepth++) {
            actualIterationDepth = 0;
            boolean discardIteration = false;
            int alpha = WORST_SCORE;
            List<Move> moves = getSortedMoves(gameState);
            Move bestMoveThisIteration = null;
            for (Move move : moves) {
                GameState newGameState = gameState.playMove(move);
                int score = (-1 * negamax(newGameState, WORST_SCORE, -1 * alpha));
                if (score == BEST_SCORE) {
                    return move;
                }
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

    private int negamax(GameState gameState, int alpha, int beta)
    {
        List<Move> moves = getMoves(gameState);

        if (reachedMinimumDepth(gameState) && moves.size() == 0) { // Quiescent
            int score = getSubjectiveScore(gameState);
            return (score >= beta ? beta : score);
        } else if (moves.size() == 0) {
            return getSubjectiveScore(gameState);
        }
        if (reachedMinimumDepth(gameState)) {
            moves.add(gameState.getNullMove());
        }

        for (Move move : moves) {
            if (outOfTime()) {
                return beta;
            }
            int score = getScore(gameState, move, alpha, beta);
            if (score >= beta) {
                return beta; // fail hard beta-cutoff
            }
            if (score > alpha) {
                alpha = score;
            }
        }

        return alpha;
    }

    private int getSubjectiveScore(GameState gameState)
    {
        updateActualIterationDepth(gameState);

        int score = evaluator.eval(gameState);
        if (!gameState.isFirstPlayerToMove()) {
            score *= -1;
        }
        return score;
    }

    private void updateActualIterationDepth(GameState gameState)
    {
        int depthForBranch = gameState.getPly() - startDepth;
        if (actualIterationDepth < depthForBranch) {
            actualIterationDepth = depthForBranch;
        }
    }

    private List<Move> getMoves(GameState gameState)
    {
        return (reachedMinimumDepth(gameState) ? getCriticalMoves(gameState) : getSortedMoves(gameState));
    }

    private int getScore(GameState gameState, Move move, int alpha, int beta)
    {
        if (move.isNull()) {
            return getSubjectiveScore(gameState);
        }

        GameState newGameState = gameState.playMove(move);

        if (reachedMinimumDepth(newGameState)) {
            int score = -getSubjectiveScore(newGameState);
            if (score < alpha) { // Delta pruning
                score = alpha;
                return score;
            }
        }

        return -negamax(newGameState, -beta, -alpha);
    }

    private void startTime()
    {
        startTime = System.currentTimeMillis();
    }

    private boolean outOfTime()
    {
        return System.currentTimeMillis() - startTime > maxTime;
    }

    private boolean reachedMinimumDepth(GameState gameState)
    {
        return (gameState.getPly() > startDepth + maxDepth);
    }

    private List<Move> getSortedMoves(GameState gameState)
    {
        List<Move> moves = gameState.getMoves();
        return moves;
    }

    private List<Move> getCriticalMoves(GameState gameState)
    {
        List<Move> moves = gameState.getCriticalMoves();
        return moves;
    }
}
