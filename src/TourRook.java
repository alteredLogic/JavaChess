class TourRook extends Piece {

    private Direction nextDirection;

    TourRook(int _player, int _currentRow, int _currentColumn, Direction _nextDirection) {
        super(_player, _currentRow, _currentColumn);
        setNextDirection(_nextDirection);
    }

    Direction getNextDirection() {
        return nextDirection;
    }

    void setNextDirection(Direction _nextDirection) {
        nextDirection = _nextDirection;
    }

    private void incrementDirection() {
        switch (nextDirection) {
            case UP:
                setNextDirection(Direction.RIGHT);
                break;
            case RIGHT:
                setNextDirection(Direction.DOWN);
                break;
            case DOWN:
                setNextDirection(Direction.LEFT);
                break;
            case LEFT:
                setNextDirection(Direction.UP);
        }

    }

    void decrementDirection() {
        switch (nextDirection) {
            case UP:
                setNextDirection(Direction.LEFT);
                break;
            case RIGHT:
                setNextDirection(Direction.UP);
                break;
            case DOWN:
                setNextDirection(Direction.RIGHT);
                break;
            case LEFT:
                setNextDirection(Direction.DOWN);
        }
    }

    private int moveAttempt(int moveToRow, int moveToColumn, ChessModel chessModel) {

        if (isHorizontallyVerticallyBlocked(moveToRow, moveToColumn, chessModel))
            return -1;

        int cap = captureSquare(moveToRow, moveToColumn, chessModel);

        if (cap > -1) {
            incrementDirection();
            return cap;
        }
        return -1;
    }

    int moveLogic(int moveToRow, int moveToColumn, ChessModel chessModel) {

        if (chessModel.outsideBoardBoundaries(moveToRow, moveToColumn))
            return -1;

            if (nextDirection == Direction.UP) {
                if ((moveToRow < getCurrentRow()) && (moveToColumn == getCurrentColumn()))
                    return moveAttempt(moveToRow, moveToColumn, chessModel);
            }
            else if (nextDirection == Direction.RIGHT) {
                if ((moveToRow == getCurrentRow()) && (moveToColumn > getCurrentColumn()))
                    return moveAttempt(moveToRow, moveToColumn, chessModel);
            }
            else if (nextDirection == Direction.DOWN) {
                if ((moveToRow > getCurrentRow()) && (moveToColumn == getCurrentColumn()))
                    return moveAttempt(moveToRow, moveToColumn, chessModel);
            }
            else if (nextDirection == Direction.LEFT) {
                if ((moveToRow == getCurrentRow()) && (moveToColumn < getCurrentColumn()))
                    return moveAttempt(moveToRow, moveToColumn, chessModel);
            }

            return -1;
    }
}