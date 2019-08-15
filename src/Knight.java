/**
 * Knight chess piece class
 */
class Knight extends Piece {

    Knight(int _player, int _currentRow, int _currentColumn) {
        super(_player, _currentRow, _currentColumn);
    }

    int moveLogic(int moveToRow, int moveToColumn, ChessModel chessModel) {

        if (chessModel.outsideBoardBoundaries(moveToRow, moveToColumn))
            return -1;

        // Potential valid Knight moves
        boolean move1 = (getCurrentRow() + 2 == moveToRow && getCurrentColumn() + 1 == moveToColumn);
        boolean move2 = (getCurrentRow() + 2 == moveToRow && getCurrentColumn() - 1 == moveToColumn);
        boolean move3 = (getCurrentRow() + 1 == moveToRow && getCurrentColumn() + 2 == moveToColumn);
        boolean move4 = (getCurrentRow() + 1 == moveToRow && getCurrentColumn() - 2 == moveToColumn);
        boolean move5 = (getCurrentRow() - 1 == moveToRow && getCurrentColumn() + 2 == moveToColumn);
        boolean move6 = (getCurrentRow() - 1 == moveToRow && getCurrentColumn() - 2 == moveToColumn);
        boolean move7 = (getCurrentRow() - 2 == moveToRow && getCurrentColumn() + 1 == moveToColumn);
        boolean move8 = (getCurrentRow() - 2 == moveToRow && getCurrentColumn() - 1 == moveToColumn);

        //Enforce valid Knight moves
        if (!(move1 || move2 || move3 || move4 || move5 || move6 || move7 || move8))
            return -1;

        return captureSquare(moveToRow, moveToColumn, chessModel);
    }
}