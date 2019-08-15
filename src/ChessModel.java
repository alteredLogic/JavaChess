import java.util.ArrayList;

/**
 * Model portion of the MVC design
 */
class ChessModel {

    final int TOTAL_ROWS = 8; // Number of rows in board
    final int TOTAL_COLUMNS = 8; // Number of columns in board
    final int NUMBER_OF_PIECES = 36; // Number of pieces used in game

    private int currentPlayer, currentMove, moveStartingSquareRow, moveStartingSquareColumn, check;
    private Piece[] pieceList;
    private ArrayList<Move> moveList;

    void setStartingMoveSquare(int row, int column) {
        moveStartingSquareRow = row;
        moveStartingSquareColumn = column;
    }

    void setCurrentPlayer(int _currentPlayer) {
        currentPlayer = _currentPlayer;
    }

    void setCheck(int _check) {
        check = _check;
    }

    void setCurrentMove(int _currentMove) {
        currentMove = _currentMove;
    }

    int getCheck() {
        return check;
    }

    int getCurrentMove() {
        return currentMove;
    }

    int getMoveStartingSquareRow() {
        return moveStartingSquareRow;
    }

    int getMoveStartingSquareColumn() {
        return moveStartingSquareColumn;
    }

    int getCurrentPlayer() {
        return currentPlayer;
    }

    boolean undoAvailable() {
        return moveList.size() > 0;
    }

    boolean startingSquareSet() {
        return (getMoveStartingSquareRow() != -1) && (getMoveStartingSquareColumn() != -1);
    }

    boolean startingSquareNotSet() {
        return (getMoveStartingSquareRow() == -1) && (getMoveStartingSquareColumn() == -1);
    }

    /**
     * Alternate player
     */
    void switchPlayer() {
        if (getCurrentPlayer() == 2)
            setCurrentPlayer(1);
        else
            setCurrentPlayer(2);
    }

    /**
     * Default Constructor, sets up standard chess board with fairy pieces
     */
    ChessModel() {

        setCurrentPlayer(1);
        setCurrentMove(0);
        setStartingMoveSquare(-1, -1);
        setCheck(0);

        pieceList = new Piece[NUMBER_OF_PIECES];

        for (int i = 0; i < 8; i++) {
            pieceList[i] = new Pawn(1, 6, i, true);
            pieceList[i + 8] = new Pawn(2, 1, i, true);
        }

        pieceList[16] = new Knight(1, 7, 1);
        pieceList[17] = new Knight(1, 7, 6);
        pieceList[18] = new Knight(2, 0, 1);
        pieceList[19] = new Knight(2, 0, 6);

        pieceList[20] = new Bishop(1, 7, 2);
        pieceList[21] = new Bishop(1, 7, 5);
        pieceList[22] = new Bishop(2, 0, 2);
        pieceList[23] = new Bishop(2, 0, 5);

        pieceList[24] = new Rook(1, 7, 0);
        pieceList[25] = new Rook(1, 7, 7);
        pieceList[26] = new Rook(2, 0, 0);
        pieceList[27] = new Rook(2, 0, 7);

        pieceList[28] = new Queen(1, 7, 3);
        pieceList[29] = new Queen(2, 0, 3);

        pieceList[30] = new King(1, 7, 4);
        pieceList[31] = new King(2, 0, 4);

        pieceList[32] = new TourRook(1, 5, 0, Direction.UP);
        pieceList[33] = new TourRook(2, 2, 7, Direction.DOWN);

        pieceList[34] = new DeathKnight(1, 5, 1);
        pieceList[35] = new DeathKnight(2, 2, 6);

        moveList = new ArrayList<Move>();
    }

    /**
     * Copy constructor used for finding checkmate
     * @param oldChessModel ChessModel to copy
     */
    ChessModel(ChessModel oldChessModel) {

        setCurrentPlayer(oldChessModel.getCurrentPlayer());
        setCurrentMove(oldChessModel.getCurrentMove());
        setStartingMoveSquare(oldChessModel.getMoveStartingSquareRow(), oldChessModel.getMoveStartingSquareColumn());
        setCheck(oldChessModel.getCheck());

        pieceList = new Piece[NUMBER_OF_PIECES];

        Piece currPiece;
        String pieceType;
        int piecePlayer, pieceRow, pieceColumn;

        for (int i = 0; i < oldChessModel.pieceList.length; i++) {

            currPiece = oldChessModel.pieceList[i];

            if (currPiece == null)
                continue;

            piecePlayer = currPiece.getPlayer();
            pieceRow = currPiece.getCurrentRow();
            pieceColumn = currPiece.getCurrentColumn();
            pieceType = currPiece.getClass().getName();

            switch (pieceType) {
                case "Pawn":
                    boolean startingPosition = ((Pawn) currPiece).getInStartingPosition();
                    pieceList[i] = new Pawn(piecePlayer, pieceRow, pieceColumn, startingPosition);
                    break;
                case "Knight":
                    pieceList[i] = new Knight(piecePlayer, pieceRow, pieceColumn);
                    break;
                case "Bishop":
                    pieceList[i] = new Bishop(piecePlayer, pieceRow, pieceColumn);
                    break;
                case "Rook":
                    pieceList[i] = new Rook(piecePlayer, pieceRow, pieceColumn);
                    break;
                case "Queen":
                    pieceList[i] = new Queen(piecePlayer, pieceRow, pieceColumn);
                    break;
                case "King":
                    pieceList[i] = new King(piecePlayer, pieceRow, pieceColumn);
                    break;
                case "TourRook":
                    Direction direction = ((TourRook) currPiece).getNextDirection();
                    pieceList[i] = new TourRook(piecePlayer, pieceRow, pieceColumn, direction);
                    break;
                case "DeathKnight":
                    pieceList[i] = new DeathKnight(piecePlayer, pieceRow, pieceColumn);
            }
        }

        moveList = new ArrayList<Move>();

        for (int i = 0; i < oldChessModel.moveList.size(); i++) {
            Move move = oldChessModel.moveList.get(i);
            moveList.add(new Move(move.moved, move.startRow, move.startColumn, move.endRow, move.endColumn, move.captured, move.pawnFirstMove));
        }
    }

    ChessModel(Piece[] piece) {
        moveList = new ArrayList<Move>();
        currentMove = check = 0;
        setCurrentPlayer(1);
        moveStartingSquareRow = moveStartingSquareColumn = -1;
        pieceList = new Piece[NUMBER_OF_PIECES];

        for (int i = 0; i < piece.length; i++)
            pieceList[i] = piece[i];
    }

    /**
     * Locates the applicable piece object for a given grid coordinate pair
     * @param row - Input row
     * @param column - Input column
     * @return - A piece object for the given space. Returns null if the space is empty
     */
    Piece getSpace(int row, int column) {
        for (int i = 0; i < NUMBER_OF_PIECES; i++) {
            Piece currPiece = pieceList[i];

            if (currPiece == null)
                continue;

            if(row == currPiece.getCurrentRow() && column == currPiece.getCurrentColumn())
                return currPiece;
        }
        return null;
    }

    /**
     * Enforce the outer boundaries of the chess grid
     * @param moveToRow - Row coordinate to check
     * @param moveToColumn - Column coordinate to check
     * @return - true if the given space lies outside of the grid, false otherwise
     */
    boolean outsideBoardBoundaries(int moveToRow, int moveToColumn) {

        boolean bound1 = (moveToRow >= TOTAL_ROWS);
        boolean bound2 = (moveToRow < 0);
        boolean bound3 = (moveToColumn >= TOTAL_COLUMNS);
        boolean bound4 = (moveToColumn < 0);

        if (bound1 || bound2 || bound3 || bound4)
            return true;

        return false;
    }

    private int checkHelper(int enemyRow, int enemyColumn, int currPlayer, int playersInCheck) {

        Piece enemyPiece = getSpace(enemyRow, enemyColumn);

        if (enemyPiece != null) {

            String enemyType = enemyPiece.getClass().getName();
            int enemyPlayer = enemyPiece.getPlayer();

            if (enemyType.equals("King") && (enemyPlayer != currPlayer)) {

                if (currPlayer == 1) {
                    if (playersInCheck == 0)
                        return 2;
                    else if (playersInCheck == 1)
                        return 3;
                    else if (playersInCheck == 2)
                        return 2;
                    else
                        return 3;
                }
                else {
                    if(playersInCheck == 0)
                        return 1;
                    else if (playersInCheck == 1)
                        return 1;
                    else if (playersInCheck == 2)
                        return 3;
                    else
                        return 3;
                }
            }
        }
        return playersInCheck;
    }

    private int checkHelper(int currRow, int currColumn, int currPlayer, int rowMultiplier, int columnMultiplier, int playersInCheck) {

        for (int i = 1; !outsideBoardBoundaries(currRow + (i * rowMultiplier), currColumn + (i * columnMultiplier)); i++) {

            Piece enemyPiece = getSpace(currRow + (i * rowMultiplier), currColumn + (i * columnMultiplier));

            if (enemyPiece == null)
                continue;

            String enemyType = enemyPiece.getClass().getName();
            int enemyPlayer = enemyPiece.getPlayer();

            if (enemyType.equals("King") && (enemyPlayer != currPlayer)) {

                if (currPlayer == 1) {
                    if (playersInCheck == 0)
                        return 2;
                    else if (playersInCheck == 1)
                        return 3;
                    else if (playersInCheck == 2)
                        return 2;
                    else
                        return 3;
                }
                else {
                    if (playersInCheck == 0)
                        return 1;
                    else if (playersInCheck == 1)
                        return 1;
                    else if (playersInCheck == 2)
                        return 3;
                    else
                        return 3;
                }
            }
            break;
        }
        return playersInCheck;
    }

    /**
     * Determines if either player is in a state of check.
     * @return 0 - No players in check, 1 - Red player in check, 2 - Black player in check, 3 - Both players in check
     */
    int checkForCheck() {

        int playersInCheck = 0;

        for (int i = 0; i < NUMBER_OF_PIECES; i++) {

            Piece currPiece = pieceList[i];

            if(currPiece == null)
                continue;

            String currType = currPiece.getClass().getName();
            int currRow = currPiece.getCurrentRow();
            int currColumn = currPiece.getCurrentColumn();
            int currPlayer = currPiece.getPlayer();

            switch(currType) {

                case "Pawn":
                    int row = (currPlayer == 1) ? (currRow - 1) : (currRow + 1);
                    playersInCheck = checkHelper(row, currColumn - 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(row, currColumn + 1, currPlayer, playersInCheck);
                    break;

                case "Knight":
                    playersInCheck = checkHelper(currRow + 2, currColumn + 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow + 2, currColumn - 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow + 1, currColumn + 2, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow + 1, currColumn - 2, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow - 1, currColumn + 2, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow - 1, currColumn - 2, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow - 2, currColumn + 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow - 2, currColumn - 1, currPlayer, playersInCheck);
                    break;

                case "Bishop":
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, 1, 1, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, 1, -1, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, -1, -1, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, -1, 1, playersInCheck);
                    break;

                case "Rook":
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, 1, 0, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, -1, 0, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, 0, -1, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, 0, 1, playersInCheck);
                    break;

                case "Queen":
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, 1, 0, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, -1, 0, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, 0, -1, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, 0, 1, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, 1, 1, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, 1, -1, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, -1, -1, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn, currPlayer, -1, 1, playersInCheck);
                    break;

                case "King":
                    playersInCheck = checkHelper(currRow + 1, currColumn + 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn + 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow - 1, currColumn + 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow - 1, currColumn, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow - 1, currColumn - 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow , currColumn - 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow + 1, currColumn - 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow + 1, currColumn, currPlayer, playersInCheck);
                    break;

                case "TourRook":
                    Direction nextDirection = ((TourRook) currPiece).getNextDirection();
                    switch (nextDirection) {
                        case UP:
                            playersInCheck = checkHelper(currRow, currColumn, currPlayer, -1, 0, playersInCheck);
                            break;
                        case RIGHT:
                            playersInCheck = checkHelper(currRow, currColumn, currPlayer, 0, 1, playersInCheck);
                            break;
                        case DOWN:
                            playersInCheck = checkHelper(currRow, currColumn, currPlayer, 1, 0, playersInCheck);
                            break;
                        case LEFT:
                            playersInCheck = checkHelper(currRow, currColumn, currPlayer, 0, -1, playersInCheck);
                            break;
                    }
                    break;

                case "DeathKnight":
                    playersInCheck = checkHelper(currRow + 1, currColumn + 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow, currColumn + 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow - 1, currColumn + 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow - 1, currColumn, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow - 1, currColumn - 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow , currColumn - 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow + 1, currColumn - 1, currPlayer, playersInCheck);
                    playersInCheck = checkHelper(currRow + 1, currColumn, currPlayer, playersInCheck);
            }
        }
        return playersInCheck;
    }

    /**
     * Determines is checkmate has occured
     * @return 0 - No players in check, 1 - Red player is in checkmate, 2 - Black player is in checkmate
     */
    int checkForCheckmate() {

        ChessModel copy = new ChessModel(this);

        for (int p = 0; p < NUMBER_OF_PIECES; p++) {

            Piece currPiece = copy.pieceList[p];

            if (currPiece == null)
                continue;

            int currPlayer = currPiece.getPlayer();

            if (currPlayer == getCurrentPlayer())
                continue;

            int moveAttempt;
            for (int row = 0; row < TOTAL_ROWS; row++) {
                for (int column = 0; column < TOTAL_COLUMNS; column++) {


                    moveAttempt = currPiece.moveLogic(row, column, copy);

                    if (moveAttempt == -1) {
                        copy = new ChessModel(this);
                        continue;
                    }

                    int c = copy.checkForCheck();

                    if (c == 0)
                        return 0;
                    else if ((c == 1) && (getCurrentPlayer() == 1))
                        return 0;
                    else if ((c == 2) && (getCurrentPlayer() == 2))
                        return 0;

                    copy = new ChessModel(this);
                }
            }
        }
        if (getCurrentPlayer() == 1)
            return 2;
        else
            return 1;
    }

    /**
     * Pops last move off the list for the undo button
     * @return Last move made in Move object form
     */
    Move popMoveList() {
        Move undo = moveList.get(--currentMove);
        moveList.remove(currentMove);
        return undo;
    }

    /**
     * Pushes last made move on the move list in Move object form
     * @param moved Piece moved
     * @param startRow Row moved from
     * @param startColumn Column moved from
     * @param endRow Row moving to
     * @param endColumn Column moving to
     * @param captured Piece captured, null if moving to an empty space
     */
    void pushMoveList(Piece moved, int startRow, int startColumn, int endRow, int endColumn, Piece captured) {
        boolean pawnFirstMove  = false;
        if (moved.getClass().getName().equals("Pawn"))
            pawnFirstMove = ((Pawn) moved).startLogic();
        moveList.add(new Move(moved, startRow, startColumn, endRow, endColumn, captured, pawnFirstMove));
        currentMove++;
    }

    /**
     * Object saved in the moveList for easy undoing
     */
    class Move {
        final int startRow, startColumn, endRow, endColumn;
        final Piece moved, captured;
        final boolean pawnFirstMove;

        Move(Piece _moved, int _startRow, int _startColumn, int _endRow, int _endColumn, Piece _captured, boolean _pawnFirstMove) {
            moved = _moved;
            startRow = _startRow;
            startColumn = _startColumn;
            endRow = _endRow;
            endColumn = _endColumn;
            captured = _captured;
            pawnFirstMove = _pawnFirstMove;
        }
    }
}