/**
 * Queen chess piece class
 */
class Queen extends Piece {

    Queen(int _player, int _currentRow, int _currentColumn) {
        super(_player, _currentRow, _currentColumn);
    }

    int moveLogic(int moveToRow, int moveToColumn, ChessModel chessModel) {

        if (chessModel.outsideBoardBoundaries(moveToRow, moveToColumn))
            return -1;

        if ((moveToRow == getCurrentRow()) || (moveToColumn == getCurrentColumn())) { // Up-Down-Left-Right movement
            if (isHorizontallyVerticallyBlocked(moveToRow, moveToColumn, chessModel))
                return -1;
        } else if (Math.abs(getCurrentRow() - moveToRow) == Math.abs(getCurrentColumn() - moveToColumn)) { // Diagonal movement
            if (isDiagonallyBlocked(moveToRow, moveToColumn, chessModel))
                return -1;
        } else
            return -1;

        return captureSquare(moveToRow, moveToColumn, chessModel);
    }
}