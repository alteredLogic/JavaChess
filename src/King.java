/**
 * King chess piece class
 */
class King extends Piece {

    King(int _player, int _currentRow, int _currentColumn) {
        super(_player, _currentRow, _currentColumn);
    }

    int moveLogic(int moveToRow, int moveToColumn, ChessModel chessModel) {

        if (chessModel.outsideBoardBoundaries(moveToRow, moveToColumn))
            return -1;

        //Potential valid King moves
        boolean up = (getCurrentRow() + 1 == moveToRow) && (getCurrentColumn() == moveToColumn);
        boolean down = (getCurrentRow() - 1 == moveToRow) && (getCurrentColumn() == moveToColumn);
        boolean left = (getCurrentRow() == moveToRow) && (getCurrentColumn() - 1 == moveToColumn);
        boolean right  = (getCurrentRow() == moveToRow) && (getCurrentColumn() + 1 == moveToColumn);
        boolean upLeft = (getCurrentRow() + 1 == moveToRow) && (getCurrentColumn() - 1 == moveToColumn);
        boolean upRight = (getCurrentRow() + 1 == moveToRow) && (getCurrentColumn() + 1 == moveToColumn);
        boolean downLeft = (getCurrentRow()- 1 == moveToRow) && (getCurrentColumn() - 1 == moveToColumn);
        boolean downRight = (getCurrentRow() - 1 == moveToRow) && (getCurrentColumn() + 1 == moveToColumn);

        // Enforce valid King moves
        if (!(up || down || left || right || upLeft || upRight || downLeft || downRight))
            return -1;

        return captureSquare(moveToRow, moveToColumn, chessModel);
    }
}