package software.ryancook.gameengine.tictactoe;

import software.ryancook.gameengine.Move;
import software.ryancook.gameengine.tictactoe.TTTGameState.Piece;

final public class TTTMove implements Move
{
    private final Piece piece;
    private int x;
    private int y;

    public TTTMove()
    {
        x = 0;
        y = 0;
        piece = null;
    }

    public TTTMove(int x, int y, Piece piece)
    {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Piece getPiece()
    {
        return piece;
    }

    @Override
    public boolean isNull()
    {
        return piece == null;
    }

    @Override
    public String toString()
    {
        String pieceString = (piece == Piece.X ? "X" : "O");
        return "{" + x + ", " + y + "} = " + piece;
    }
}
