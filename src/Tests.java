import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests class
 */
public class Tests {

    @Test
    public void testChessGame() {
        ChessGame g = new ChessGame();
        g.main(null);
    }

    @Test
    public void testCopyConstructor() {
        ChessModel m1 = new ChessModel();
        ChessModel m2 = new ChessModel(m1);

        assertEquals(m2.getCurrentPlayer(), 1);
        assertEquals(m2.getCurrentMove(), 0);
        assertEquals(m2.getMoveStartingSquareRow(), -1);
        assertEquals(m2.getMoveStartingSquareColumn(), -1);
        assertEquals(m2.getCheck(), 0);
    }

    @Test
    public void testPawn() {
        Piece[] piece = new Piece[7];
        piece[0] = new Pawn(1, 6, 1, false);
        piece[1] = new Pawn(1, 6, 6, true);
        piece[2] = new Rook(2, 3,5);
        piece[3] = new Rook(2, 3,6);
        piece[4] = new Pawn(2, 0, 0, true);
        piece[5] = new Pawn(2, 1, 1, true);
        piece[6] = new Rook(2, 1,0);
        ChessModel m = new ChessModel(piece);

        int move1 = piece[0].moveLogic(4, 1, m);
        int move2 = piece[1].moveLogic(4, 6, m);
        int move3 = piece[1].moveLogic(3, 6, m);
        int move4 = piece[1].moveLogic(3, 5, m);
        int move5 = piece[1].moveLogic(-1, -1, m);
        int move6 = piece[1].moveLogic(2, 5, m);
        int move7 = piece[4].moveLogic(1, 1, m);
        int move8 = piece[4].moveLogic(2, 0, m);

        assertEquals(-1, move1);
        assertEquals(2, move2);
        assertEquals(-1, move3);
        assertEquals(0, move4);
        assertEquals(-1, move5);
        assertEquals(1, move6);
        assertEquals(-1, move7);
        assertEquals(-1, move8);
    }

    @Test
    public void testCheck() {
        Piece[] piece = new Piece[4];
        piece[0] = new King(1, 3, 3);
        piece[1] = new King(2, 4, 5);
        piece[2] = new Queen(1, 0, 0);
        piece[3] = new Queen(2, 7, 4);
        ChessModel m = new ChessModel(piece);

        int check1 = m.checkForCheck();
        piece[0].moveLogic(4, 4, m);
        int check2 = m.checkForCheck();
        piece[0].moveLogic(3, 3, m);
        piece[2].moveLogic(0, 5, m);
        int check3 = m.checkForCheck();
        piece[2].moveLogic(0, 0, m);
        piece[3].moveLogic(3, 4, m);
        int check4 = m.checkForCheck();

        assertEquals(0, check1);
        assertEquals(3, check2);
        assertEquals(2, check3);
        assertEquals(1, check4);
    }

    @Test
    public void testCheckmate() {
        ChessModel m = new ChessModel();
        Piece pawn1 = m.getSpace(6, 5);
        Piece pawn2 = m.getSpace(6, 6);
        Piece pawn3 = m.getSpace(1, 4);
        Piece queen = m.getSpace(0, 3);

        int checkmate1 = m.checkForCheckmate();

        int move1 = pawn1.moveLogic(5, 5, m);
        int move2 = pawn3.moveLogic(2, 4, m);
        int move3 = pawn2.moveLogic(4, 6, m);
        int move4 = queen.moveLogic(4, 7, m);

        m.switchPlayer();
        int checkmate2 = m.checkForCheckmate();

        assertEquals(0, checkmate1);
        assertEquals(1, checkmate2);
    }


    @Test
    public void testKnight() {
        Piece[] piece = new Piece[3];
        piece[0] = new Knight(1, 3, 3);
        piece[1] = new Rook(1, 1, 4);
        piece[2] = new Rook(2, 1, 2);
        ChessModel m = new ChessModel(piece);

        int move1 = piece[0].moveLogic(1, 4, m);
        int move2 = piece[0].moveLogic(1, 2, m);
        int move3 = piece[0].moveLogic(3, 3, m);
        int move4 = piece[0].moveLogic(-1, -1, m);
        int move5 = piece[0].moveLogic(7, 6, m);

        assertEquals(-1, move1);
        assertEquals(0, move2);
        assertEquals(1, move3);
        assertEquals(-1, move4);
        assertEquals(-1, move5);
    }

    @Test
    public void testBishop() {
        Piece[] piece = new Piece[4];
        piece[0] = new Bishop(1, 3, 3);
        piece[1] = new Rook(2, 2, 2);
        piece[2] = new Rook(2, 1, 1);
        piece[3] = new Rook(1, 4, 4);
        ChessModel m = new ChessModel(piece);

        int move1 = piece[0].moveLogic(3, 3, m);
        int move2 = piece[0].moveLogic(1, 1, m);
        int move3 = piece[0].moveLogic(2, 2, m);
        int move4 = piece[0].moveLogic(3, 3, m);
        int move5 = piece[0].moveLogic(4, 4, m);
        int move6 = piece[0].moveLogic(-1, -1, m);
        int move7 = piece[0].moveLogic(7, 6, m);

        assertEquals(-1, move1);
        assertEquals(-1, move2);
        assertEquals(0, move3);
        assertEquals(1, move4);
        assertEquals(-1, move5);
        assertEquals(-1, move6);
        assertEquals(-1, move7);
    }

    @Test
    public void testRook() {
        Piece[] piece = new Piece[4];
        piece[0] = new Rook(1, 3, 3);
        piece[1] = new Rook(2, 2, 3);
        piece[2] = new Rook(2, 1, 3);
        piece[3] = new Rook(1, 4, 3);
        ChessModel m = new ChessModel(piece);

        int move1 = piece[0].moveLogic(3, 3, m);
        int move2 = piece[0].moveLogic(1, 3, m);
        int move3 = piece[0].moveLogic(2, 3, m);
        int move4 = piece[0].moveLogic(3, 3, m);
        int move5 = piece[0].moveLogic(4, 3, m);
        int move6 = piece[0].moveLogic(-1, -1, m);
        int move7 = piece[0].moveLogic(7, 6, m);

        assertEquals(-1, move1);
        assertEquals(-1, move2);
        assertEquals(0, move3);
        assertEquals(1, move4);
        assertEquals(-1, move5);
        assertEquals(-1, move6);
        assertEquals(-1, move7);
    }

    @Test
    public void testQueen() {
        Piece[] piece = new Piece[6];
        piece[0] = new Queen(1, 2, 3);
        piece[1] = new Rook(2, 0, 0);
        piece[2] = new Rook(2, 4, 5);
        piece[3] = new Rook(1, 3, 4);
        piece[4] = new Rook(1, 1, 3);
        piece[5] = new Rook(2, 0, 3);
        ChessModel m = new ChessModel(piece);

        int move6 = piece[0].moveLogic(1, 3, m);
        int move5 = piece[0].moveLogic(0, 3, m);
        int move0 = piece[0].moveLogic(4, 5, m);
        int move1 = piece[0].moveLogic(2, 2, m);
        int move2 = piece[0].moveLogic(0, 0, m);
        int move3 = piece[0].moveLogic(-1, -1, m);
        int move4 = piece[0].moveLogic(7, 6, m);

        assertEquals(-1, move6);
        assertEquals(-1, move5);
        assertEquals(-1, move0);
        assertEquals(1, move1);
        assertEquals(0, move2);
        assertEquals(-1, move3);
        assertEquals(-1, move4);
    }


    @Test
    public void testKing() {
        Piece[] piece = new Piece[2];
        piece[0] = new King(1, 2, 3);
        piece[1] = new Rook(2, 1, 1);
        ChessModel m = new ChessModel(piece);

        int move1 = piece[0].moveLogic(2, 2, m);
        int move2 = piece[0].moveLogic(1, 1, m);
        int move3 = piece[0].moveLogic(-1, -1, m);
        int move4 = piece[0].moveLogic(7, 7, m);

        assertEquals(1, move1);
        assertEquals(0, move2);
        assertEquals(-1, move3);
        assertEquals(-1, move4);
    }

    @Test
    public void testTourRook() {
        Piece[] piece = new Piece[4];
        piece[0] = new TourRook(1, 3, 3, Direction.UP);
        piece[1] = new Rook(2, 1, 3);
        piece[2] = new Rook(2, 0, 3);
        piece[3] = new Rook(1, 0, 0);
        ChessModel m = new ChessModel(piece);

        int move1 = piece[0].moveLogic(0, 3, m);
        int move2 = piece[0].moveLogic(1, 3, m);
        int move3 = piece[0].moveLogic(1, 4, m);
        int move4 = piece[0].moveLogic(7, 7, m);
        int move5 = piece[0].moveLogic(-7, -7, m);
        int move6 = piece[0].moveLogic(2, 4, m);
        int move7 = piece[0].moveLogic(2, 0, m);
        int move8 = piece[0].moveLogic(0, 0, m);

        assertEquals(-1, move1);
        assertEquals(0, move2);
        assertEquals(1, move3);
        assertEquals(-1, move4);
        assertEquals(-1, move5);
        assertEquals(1, move6);
        assertEquals(1, move7);
        assertEquals(-1, move8);
    }

    @Test
    public void testDeathKnight() {
        Piece[] piece = new Piece[5];
        piece[0] = new DeathKnight(1, 3, 3);
        piece[1] = new Rook(1, 2, 2);
        piece[2] = new Rook(2, 4, 4);
        piece[3] = new Rook(1, 5, 5);
        piece[4] = new Rook(2, 1, 1);
        ChessModel m = new ChessModel(piece);

        int move1 = piece[0].moveLogic(5, 5, m);
        int move2 = piece[0].moveLogic(1, 1, m);
        int move3 = piece[0].moveLogic(2, 2, m);
        int move4 = piece[0].moveLogic(4, 4, m);
        int move5 = piece[0].moveLogic(-7, -7, m);
        int move6 = piece[0].moveLogic(7, 7, m);
        int move7 = piece[0].moveLogic(6, 6, m);
        int move8 = piece[0].moveLogic(7, 7, m);
        int move9 = piece[0].moveLogic(6, 6, m);

        assertEquals(-1, move1);
        assertEquals(-1, move2);
        assertEquals(-1, move3);
        assertEquals(0, move4);
        assertEquals(-1, move5);
        assertEquals(-1, move6);
        assertEquals(1, move7);
        assertEquals(-1, move8);
        assertEquals(-1, move9);
    }

    @Test
    public void testMove() {
        ChessModel m = new ChessModel();
        Piece p = m.getSpace(6, 0);
        m.pushMoveList(p, 6, 0, 4, 0, null);

        ChessModel.Move move = m.popMoveList();

        assertEquals(p, move.moved);
        assertEquals(null, move.captured);
        assertEquals(true, move.pawnFirstMove);
        assertEquals(6, move.startRow);
        assertEquals(0, move.startColumn);
        assertEquals(4, move.endRow);
        assertEquals(0, move.endColumn);
    }

    @Test
    public void testUndo() {
        ChessModel m = new ChessModel();
        ChessView v = new ChessView(m);
        ChessController c = new ChessController(m, v);

        Piece p = m.getSpace(6, 0);
        p.moveLogic(4, 0, m);
        m.pushMoveList(p, 6, 0, 4, 0, null);

        ChessModel.Move move = m.popMoveList();
        c.undoMove(move);

        assertEquals(6, p.getCurrentRow());
        assertEquals(0, p.getCurrentColumn());
        assertEquals(true, ((Pawn) p).getInStartingPosition());
    }
}