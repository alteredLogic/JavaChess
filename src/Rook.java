/**
 * Rook chess piece class
 */
class Rook extends Piece {

    Rook(int _player, int _currentRow, int _currentColumn) {
        super(_player, _currentRow, _currentColumn);
    }

    int moveLogic(int moveToRow, int moveToColumn, ChessModel chessModel) {

        if (chessModel.outsideBoardBoundaries(moveToRow, moveToColumn))
            return -1;

        // Enforces Orthogonal movement
        if (!(moveToRow == getCurrentRow()) && !(moveToColumn == getCurrentColumn()))
            return -1;

        if (isHorizontallyVerticallyBlocked(moveToRow, moveToColumn, chessModel))
            return -1;

        return captureSquare(moveToRow, moveToColumn, chessModel);
    }
}