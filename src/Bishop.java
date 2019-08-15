/**
 * Bishop chess piece class
 */

class Bishop extends Piece {

    Bishop(int _player, int _currentRow, int _currentColumn) {
        super(_player, _currentRow, _currentColumn);
    }

    int moveLogic(int moveToRow, int moveToColumn, ChessModel chessModel) {

        if (chessModel.outsideBoardBoundaries(moveToRow, moveToColumn))
            return -1;

        // Enforces diagonal movement
        if (!(Math.abs(getCurrentRow() - moveToRow) == Math.abs(getCurrentColumn() - moveToColumn)))
            return -1;

        if (isDiagonallyBlocked(moveToRow, moveToColumn, chessModel))
            return -1;

        return captureSquare(moveToRow, moveToColumn, chessModel);
    }
}