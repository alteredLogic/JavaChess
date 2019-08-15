/**
 * Pawn chess piece class
 */
class Pawn extends Piece {

    private boolean inStartingPosition;

    Pawn(int _player, int _currentRow, int _currentColumn, boolean _inStartingPosition) {
        super(_player, _currentRow, _currentColumn);
        setInStartingPosition(_inStartingPosition);
    }

    boolean startLogic() {
        if (inStartingPosition) {
            setInStartingPosition(false);
            return true;
        }
        return false;
    }

    boolean getInStartingPosition() {
        return inStartingPosition;
    }

    void setInStartingPosition(boolean _inStartingPosition) {
        inStartingPosition = _inStartingPosition;
    }

    int moveLogic(int moveToRow, int moveToColumn, ChessModel chessModel) {

        if (chessModel.outsideBoardBoundaries(moveToRow, moveToColumn))
            return -1;

        boolean row1, row2;

        boolean col1 = (getCurrentColumn() - 1 == moveToColumn);
        boolean col2 = (getCurrentColumn() == moveToColumn);
        boolean col3 = (getCurrentColumn() + 1 == moveToColumn);

        if (getPlayer() == 1) {
            row1 = (getCurrentRow() - 1 == moveToRow);
            row2 = (getCurrentRow() - 2 == moveToRow);
        } else {
            row1 = (getCurrentRow() + 1 == moveToRow);
            row2 = (getCurrentRow() + 2 == moveToRow);
        }

        Piece targetSquare = chessModel.getSpace(moveToRow, moveToColumn);
        boolean emptyTarget = (targetSquare == null);

        if (row1 && col2 && emptyTarget) { // Advance one square

            movePiece(moveToRow, moveToColumn);
            return 1;

        } else if ((row1 && col1 && !emptyTarget) || (row1 && col3 && !emptyTarget)) { // Capture diagonally

            if (targetSquare.getPlayer() == getPlayer())
                return -1 ;

            targetSquare.removePiece();
            movePiece(moveToRow, moveToColumn);
            return 0;

        } else if (row2 && col2 && getInStartingPosition() && emptyTarget) { // advance two squares on first move

            if (isHorizontallyVerticallyBlocked(moveToRow, moveToColumn, chessModel))
                return -1;

            movePiece(moveToRow, moveToColumn);
            return 2;
        }

        return -1;
    }
}