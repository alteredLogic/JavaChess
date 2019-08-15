/**
 * Top level chess game class
 */
enum Direction {UP, RIGHT, DOWN, LEFT}

class ChessGame {

    /**
     * Top level main funcion
     * @param args Input strings
     */
    public static void main(String[] args) {
        
        ChessModel m = new ChessModel();
        ChessView v = new ChessView(m);
        ChessController c = new ChessController(m, v);

        v.setVisible(true);
    }
}