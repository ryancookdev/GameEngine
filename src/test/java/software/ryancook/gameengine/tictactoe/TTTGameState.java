package software.ryancook.gameengine.tictactoe;

import software.ryancook.gameengine.*;
import java.security.InvalidParameterException;
import java.util.*;

public class TTTGameState implements GameState
{
    public enum Piece {X, O}
    private Piece[][] board;
    private int ply;

    public TTTGameState(final int height, final int width)
    {
        if (height < 0 || width < 0) {
            throw new InvalidParameterException();
        }
        board = new Piece[height][width];
    }

    @Override
    public int getPly()
    {
        return ply;
    }

    @Override
    public boolean isFirstPlayerToMove()
    {
        return ply % 2 == 0;
    }

    @Override
    public List<Move> getMoves()
    {
        final List<Move> moves = new ArrayList<>();

        final Evaluator evaluator = new TTTEvaluator();
        final int score = evaluator.eval(this);
        if (score != 0) {
            return moves;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == null) {
                    final Piece piece = (isFirstPlayerToMove() ? Piece.X : Piece.O);
                    moves.add(new TTTMove(i, j, piece));
                }
            }
        }
        return moves;
    }

    @Override
    public Move getNullMove()
    {
        return new TTTMove();
    }

    @Override
    public TTTGameState playMove(final Move m)
    {
        final TTTMove move = (TTTMove) m;
        validateMove(move);

        final TTTGameState newGame = new TTTGameState(getWidth(), getHeight());
        newGame.setPly(ply + 1);

        final Piece[][] newBoard = TTTGameState.copyBoard(board);
        newBoard[move.getX()][move.getY()] = move.getPiece();
        newGame.setBoard(newBoard);

        return newGame;
    }

    private void validateMove(final TTTMove move)
    {
        if (move.getX() > board.length) {
            throw new IllegalMoveException();
        }

        if (move.getY() > board[0].length) {
            throw new IllegalMoveException();
        }

        if (board[move.getX()][move.getY()] != null) {
            throw new IllegalMoveException();
        }
    }

    private int getHeight()
    {
        return board.length;
    }

    private int getWidth()
    {
        return board[0].length;
    }

    private static Piece[][] copyBoard(final Piece[][] board)
    {
        final int height = board.length;
        final int width = board[0].length;
        final Piece[][] newBoard = new Piece[height][width];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, newBoard[i].length);
        }
        return newBoard;
    }

    void setPly(final int newPly)
    {
        ply = newPly;
    }

    void setBoard(final Piece[][] board)
    {
        this.board = TTTGameState.copyBoard(board);
    }

    @Override
    public List<Move> getCriticalMoves()
    {
        return new ArrayList<>();
    }

    public Piece[][] getBoard()
    {
        return TTTGameState.copyBoard(board);
    }

    @Override
    public String toString()
    {
        String str = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null) {
                    str += "-";
                } else if (board[i][j] == Piece.X) {
                    str += "X";
                } else if (board[i][j] == Piece.O) {
                    str += "O";
                }
            }
            str += "\n";
        }
        return str;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TTTGameState gameState = (TTTGameState) o;

        return Arrays.deepEquals(board, gameState.getBoard());
    }

    @Override
    public int hashCode()
    {
        if (board == null) {
            return 0;
        }

        final int sizeX = board.length;
        final int sizeY = board[0].length;

        final Piece[] flatBoard = new Piece[sizeX * sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                flatBoard[(i * sizeY) + j] = board[i][j];
            }
        }

        return Arrays.deepHashCode(flatBoard);
    }

    class IllegalMoveException extends RuntimeException {}
}