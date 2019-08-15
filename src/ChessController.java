import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Controller portion of the MVC design
 */
class ChessController {

    private ChessModel chessModel;
    private ChessView chessView;

    ChessController(ChessModel _chessModel, ChessView _chessView) {

        chessModel = _chessModel;
        chessView = _chessView;

        for (int row = 0; row < chessModel.TOTAL_ROWS; row++) {
            for (int column = 0; column < chessModel.TOTAL_COLUMNS; column++) {
                chessView.addActionGrid(new ActionGrid(row, column), row, column);
                chessView.addMouseGrid(new MouseGrid(row, column), row, column);
            }
        }

        chessView.addActionUndo(new ActionUndo());
    }

    /**
     * Resets piece data during an undo operation
     * @param moveToUndo Move object to undo
     */
    void undoMove(ChessModel.Move moveToUndo) {

        if (chessModel.startingSquareSet()) {
            chessView.removeButtonColor(chessModel.getMoveStartingSquareRow(), chessModel.getMoveStartingSquareColumn());
            chessModel.setStartingMoveSquare(-1, -1);
        }

        Piece movedPiece = moveToUndo.moved;
        movedPiece.movePiece(moveToUndo.startRow, moveToUndo.startColumn);

        String pieceType = movedPiece.getClass().getName();

        if (pieceType.equals("Pawn") && moveToUndo.pawnFirstMove)
            ((Pawn) movedPiece).setInStartingPosition(true);
        else if (pieceType.equals("TourRook"))
            ((TourRook) movedPiece).decrementDirection();

        chessView.moveIcon(moveToUndo.endRow, moveToUndo.endColumn, moveToUndo.startRow, moveToUndo.startColumn);

        undoCapture(moveToUndo);

        chessView.updateCheckDisplay();
        chessView.updatePlayerDisplay();
    }

    /**
     * If a piece was captured, this will revive it during an undo
     * @param moveToUndo Move object to undo
     */
    private void undoCapture(ChessModel.Move moveToUndo) {
        Piece capturedPiece = moveToUndo.captured;
        if (capturedPiece != null) {
            capturedPiece.movePiece(moveToUndo.endRow, moveToUndo.endColumn);
            chessView.setButtonIcon(moveToUndo.endRow, moveToUndo.endColumn);
        }
    }

    /**
     * Actions for the chess board squares
     */
    class ActionGrid implements ActionListener {

        private int row, column;

        ActionGrid(int _row, int _column) {
            row = _row;
            column = _column;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            Piece currPiece = chessModel.getSpace(row, column);

            if (chessModel.startingSquareNotSet()) {

                if (currPiece == null) // Prevent selecting an empty space
                    return;

                if (currPiece.getPlayer() != chessModel.getCurrentPlayer()) // Can't capture friendly piece
                    return;

                chessModel.setStartingMoveSquare(row, column);
                chessView.setButtonColor(row, column, Color.BLUE);

            } else {

                chessView.removeButtonColor(chessModel.getMoveStartingSquareRow(), chessModel.getMoveStartingSquareColumn());
                Piece startPiece = chessModel.getSpace(chessModel.getMoveStartingSquareRow(), chessModel.getMoveStartingSquareColumn());

                if (startPiece.moveLogic(row, column, chessModel) != -1) {

                    chessView.moveIcon(chessModel.getMoveStartingSquareRow(), chessModel.getMoveStartingSquareColumn(), row, column);
                    chessView.updateCheckDisplay();
                    chessView.updatePlayerDisplay();

                    chessModel.pushMoveList(startPiece, chessModel.getMoveStartingSquareRow(), chessModel.getMoveStartingSquareColumn(), row, column, currPiece);
                }

                chessModel.setStartingMoveSquare(-1, -1);
            }
        }
    }

    /**
     * Colored highlighting for the chess board squares.
     */
    class MouseGrid implements MouseListener {

        private int row, column;

        MouseGrid(int _row, int _column) {
            row = _row;
            column = _column;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            if ((row != chessModel.getMoveStartingSquareRow()) || (column != chessModel.getMoveStartingSquareColumn()))
                chessView.setButtonColor(row, column, Color.CYAN);
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            if ((row != chessModel.getMoveStartingSquareRow()) || (column != chessModel.getMoveStartingSquareColumn()))
                chessView.removeButtonColor(row, column);
        }
    }

    /**
     * Implements the undo button.
     */
    class ActionUndo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (chessModel.undoAvailable()) {
                ChessModel.Move moveToUndo = chessModel.popMoveList();
                undoMove(moveToUndo);
            }
        }
    }
}