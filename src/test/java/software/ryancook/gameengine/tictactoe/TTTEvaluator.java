package software.ryancook.gameengine.tictactoe;

import software.ryancook.gameengine.*;
import software.ryancook.gameengine.tictactoe.TTTGameState.Piece;

import java.util.List;

public class TTTEvaluator implements Evaluator
{
    @Override
    public int eval(GameState g)
    {
        boolean winX = false;
        boolean winO = false;

        TTTGameState gameState = (TTTGameState) g;
        Piece[][] board = gameState.getBoard();

        Piece piece1;
        Piece piece2;
        Piece piece3;

        for (int i = 0; i < 3; i++) {
            piece1 = board[i][0];
            piece2 = board[i][1];
            piece3 = board[i][2];
            if (piece1 != null && piece1 == piece2 && piece1 == piece3) {
                if (piece1 == Piece.X) {
                    winX = true;
                } else {
                    winO = true;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            piece1 = board[0][i];
            piece2 = board[1][i];
            piece3 = board[2][i];
            if (piece1 != null && piece1 == piece2 && piece1 == piece3) {
                if (piece1 == Piece.X) {
                    winX = true;
                } else {
                    winO = true;
                }
            }
        }

        piece1 = board[0][0];
        piece2 = board[1][1];
        piece3 = board[2][2];
        if (piece1 != null && piece1 == piece2 && piece1 == piece3) {
            if (piece1 == Piece.X) {
                winX = true;
            } else {
                winO = true;
            }
        }

        piece1 = board[0][2];
        piece2 = board[1][1];
        piece3 = board[2][0];
        if (piece1 != null && piece1 == piece2 && piece1 == piece3) {
            if (piece1 == Piece.X) {
                winX = true;
            } else {
                winO = true;
            }
        }

        if (winX && winO) {
            throw new IllegalPositionException();
        }

        if (winX) {
            return 99999;
        }

        if (winO) {
            return -99999;
        }

        return 0;
    }

    @Override
    public List<Move> sortMoves(List<Move> moves)
    {
        return moves;
    }

    class IllegalPositionException extends RuntimeException {}
}

