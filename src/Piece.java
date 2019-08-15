/**
 * Abstract class modeled as a base class for a chess piece.
 */
abstract class Piece {

    private int player, currentRow, currentColumn;

    Piece(int _player, int _currentRow, int _currentColumn) {
        player = _player;
        currentRow = _currentRow;
        currentColumn = _currentColumn;
    }

    /**
     * @return 1 - Player one, 2 - Player two
     */
    int getPlayer() {
        return player;
    }

    /**
     * @return Row of the current piece
     */
    int getCurrentRow() {
        return currentRow;
    }

    /**
     * @return Column of the current piece
     */
    int getCurrentColumn() {
        return currentColumn;
    }

    /**
     * Setter for the current piece's row
     * @param _currentRow - Row to be updated
     */
    private void setCurrentRow(int _currentRow) {
        currentRow = _currentRow;
    }

    /**
     * Setter for the current piece's column
     * @param _currentColumn - column to be updated
     */
    private void setCurrentColumn(int _currentColumn) {
        currentColumn = _currentColumn;
    }

    /**
     * Updates both of a piece's coordinates
     * @param newRow
     * @param newColumn
     */
    void movePiece(int newRow, int newColumn) {
        setCurrentRow(newRow);
        setCurrentColumn(newColumn);
    }

    /**
     * Removes a piece from the grid after being captured
     */
    void removePiece() {
        setCurrentRow(-1);
        setCurrentColumn(-1);
    }

    /**
     * Attempts to move a piece to a new square and capture an enemy piece if required
     * @param newRow - Targeted row coordinate
     * @param newColumn - Targeted column coordinate
     * @return 0 - Targeted space successfully moved to, with enemy capture
     *         1 - Targeted space successfully moved to, without enemy capture
     *         -1 - Targeted space already occupied with friendly piece, no move made
     *
     */
    int captureSquare(int newRow, int newColumn, ChessModel chessModel) {

        Piece targetSquare = chessModel.getSpace(newRow, newColumn);

        if (targetSquare == null) {
            movePiece(newRow, newColumn);
            return 1;
        }

        if (targetSquare.getPlayer() != player) {
            targetSquare.removePiece();
            movePiece(newRow, newColumn);
            return 0;
        }

        return -1;
    }

    /**
     * Checks to see if a piece object has a clear path from its current position
     * to a new potential space
     * @param direction - Direction the piece wants to move in
     * @param distance - Distance the piece wants to travel
     * @return true if path is unobstructed by other pieces, false otherwise
     */
    private boolean isBlocked(String direction, int distance, ChessModel chessModel) {
        switch(direction) {
            case "Up":
                return isBlockedHelper(distance, -1, 0, chessModel);
            case "Down":
                return isBlockedHelper(distance, 1, 0, chessModel);
            case "Left":
                return isBlockedHelper(distance, 0, 1, chessModel);
            case "Right":
                return isBlockedHelper(distance, 0, 1, chessModel);
            case "Up-Left":
                return isBlockedHelper(distance, -1, -1, chessModel);
            case "Up-Right":
                return isBlockedHelper(distance, -1, 1, chessModel);
            case "Down-Left":
                return isBlockedHelper(distance, 1, -1, chessModel);
            case "Down-Right":
                return isBlockedHelper(distance, 1, 1, chessModel);
        }
        return true;
    }

    private boolean isBlockedHelper(int distance, int rowMultiplier, int columnMultiplier, ChessModel chessModel) {
        for (int i = 1; i < distance; i++) {
            Piece p = chessModel.getSpace(currentRow + (i * rowMultiplier), currentColumn + (i * columnMultiplier));
            if (p != null)
                return true;
        }
        return false;
    }

    /**
     * Checks for diagonal obstructions for a given piece
     * @param moveToRow
     * @param moveToColumn
     * @return true if blocked in a diagonal, false otherwise
     */
    boolean isDiagonallyBlocked(int moveToRow, int moveToColumn, ChessModel chessModel) {
        int distance = Math.abs(currentRow - moveToRow);
        if (moveToRow < currentRow) {
            if (moveToColumn > currentColumn)
                return (isBlocked("Up-Right", distance, chessModel));
            else
                return (isBlocked("Up-Left", distance, chessModel));
        }
        else {
            if (moveToColumn > currentColumn)
                return (isBlocked("Down-Right", distance, chessModel));
            else
                return (isBlocked("Down-Left", distance, chessModel));
        }
    }

    /**
     * Checks for horizontal and vertical obstructions for a given piece
     * @param moveToRow
     * @param moveToColumn
     * @return true if blocked in a row or column, false otherwise
     */
    boolean isHorizontallyVerticallyBlocked(int moveToRow, int moveToColumn, ChessModel chessModel) {
        int distance;
        if (moveToRow != currentRow) {
            distance = Math.abs(currentRow - moveToRow);
            if (moveToRow < currentRow)
                return (isBlocked("Up", distance, chessModel));
            else
                return (isBlocked("Down", distance, chessModel));
        }
        else {
            distance = Math.abs(currentColumn - moveToColumn);
            if (moveToColumn > currentColumn)
                return (isBlocked("Right", distance, chessModel));
            else
                return (isBlocked("Left", distance, chessModel));
        }
    }

    /**
     * Calculates if a requested move if legally and logically valid
     * @param moveToRow
     * @param moveToColumn
     * @return - 0 if requested move is possible and was completed,
     *             -1 if the requested move was invalid
     */
    abstract int moveLogic(int moveToRow, int moveToColumn, ChessModel chessModel);
}