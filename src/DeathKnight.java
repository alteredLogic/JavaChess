class DeathKnight extends Piece {

    DeathKnight(int _player, int _currentRow, int _currentColumn) {
        super(_player, _currentRow, _currentColumn);
    }

    private boolean distanceFromCenter(int distance, int moveToRow, int moveToColumn) {
        return ((Math.abs(getCurrentRow() - moveToRow) <= distance) && (Math.abs(getCurrentColumn() - moveToColumn) <= distance));
    }

    int moveLogic(int moveToRow, int moveToColumn, ChessModel chessModel) {

        if (chessModel.outsideBoardBoundaries(moveToRow, moveToColumn))
            return -1;

        Piece targetSquare = chessModel.getSpace(moveToRow, moveToColumn);

        if (distanceFromCenter(0, moveToRow, moveToColumn)) {
            return -1;

        } else if (distanceFromCenter(1, moveToRow, moveToColumn)) { //Capture

            if (targetSquare == null)
                return -1;

            if (targetSquare.getPlayer() == getPlayer())
                return -1;

            targetSquare.removePiece();
            movePiece(moveToRow, moveToColumn);
            return 0;

        } else if (distanceFromCenter(2, moveToRow, moveToColumn)) { //MoveToEmpty

            if (targetSquare != null)
                return -1;

            movePiece(moveToRow, moveToColumn);
            return 1;
        }
        return -1;
    }
}