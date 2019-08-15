import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * View portion of the MVC design
 */
class ChessView extends JFrame {

    private ChessModel chessModel;
    private JButton[][] buttons = new JButton[8][8];

    private JButton undo = new JButton();
    private JLabel check = new JLabel(), checkmate = new JLabel();

    ChessView(ChessModel _chessModel) {

        chessModel = _chessModel;

        setSize(1000, 840);
        setLocation(200, 10);

        initializeButtons();

        setTitle("Red's Move");
        JPanel panel = new JPanel();
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Sets up interactive buttons on the chess board
     */
    private void initializeButtons() {

        for (int row = 0; row < chessModel.TOTAL_ROWS; row++) {
            for (int column = 0; column < chessModel.TOTAL_COLUMNS; column++) {
                buttons[row][column] = new JButton();
                setButtonIcon(row, column);
                setButtonBackground(row, column);
                addButtonToFrame(row, column);
            }
        }

        undo.setText("UNDO");
        undo.setBounds(850, 50, 100, 100);
        add(undo);

        check.setBounds(850, 500, 100, 100);
        add(check);

        checkmate.setBounds(850, 700, 100, 100);
        add(checkmate);
    }

    /**
     * Moves icons on the board during a move
     * @param startRow Starting row
     * @param startColumn Starting column
     * @param endRow Ending row
     * @param endColumn Ending column
     */
    void moveIcon(int startRow, int startColumn, int endRow, int endColumn) {
        clearButtonIcon(startRow, startColumn);
        setButtonIcon(endRow, endColumn);
    }

    /**
     * Sets background for a given chess board square
     * @param row Row
     * @param col Column
     * @param color Color
     */
    void setButtonColor(int row, int col, Color color) {
        buttons[row][col].setBackground(color);
    }

    /**
     * Restores standard chess board background color for a given square
     * @param row Row
     * @param col Column
     */
    void removeButtonColor(int row, int col) {
        Color color;
        if ((row + col) % 2 != 0)
            color = Color.DARK_GRAY;
        else
            color = Color.WHITE;

        buttons[row][col].setBackground(color);
    }

    /**
     * Adds listener to a board button
     * @param action Action
     * @param row Row
     * @param column Column
     */
    void addActionGrid(ActionListener action, int row, int column) {
        buttons[row][column].addActionListener(action);
    }

    /**
     * Adds mouse listener to a board button
     * @param mouse MouseAction
     * @param row Row
     * @param column Column
     */
    void addMouseGrid(MouseListener mouse, int row, int column) {
        buttons[row][column].addMouseListener(mouse);
    }

    /**
     * Adds undo button listener
     * @param action Action
     */
    void addActionUndo(ActionListener action) {
        undo.addActionListener(action);
    }

    /**
     * Displays the proper icon for a given piece
     * @param row Row on board
     * @param column Column on board
     * @return Returs the icon to set into a JButton
     */
    private ImageIcon getButtonIcon(int row, int column) {

        ImageIcon icon;
        Piece piece = chessModel.getSpace(row, column);

        if (piece != null) {

            int currPlayer = piece.getPlayer();
            String pieceType = piece.getClass().getName();
            switch (pieceType) {
                case "Pawn":
                    if (currPlayer == 1)
                        icon = new ImageIcon("img/pawnRed.png");
                    else
                        icon = new ImageIcon("img/pawnBlack.png");
                    break;
                case "Knight":
                    if (currPlayer == 1)
                        icon = new ImageIcon("img/knightRed.png");
                    else
                        icon = new ImageIcon("img/knightBlack.png");
                    break;
                case "Bishop":
                    if (currPlayer == 1)
                        icon = new ImageIcon("img/bishopRed.png");
                    else
                        icon = new ImageIcon("img/bishopBlack.png");
                    break;
                case "Rook":
                    if (currPlayer == 1)
                        icon = new ImageIcon("img/rookRed.png");
                    else
                        icon = new ImageIcon("img/rookBlack.png");
                    break;
                case "Queen":
                    if (currPlayer == 1)
                        icon = new ImageIcon("img/queenRed.png");
                    else
                        icon = new ImageIcon("img/queenBlack.png");
                    break;
                case "King":
                    if (currPlayer == 1)
                        icon = new ImageIcon("img/kingRed.png");
                    else
                        icon = new ImageIcon("img/kingBlack.png");
                    break;
                case "TourRook":

                    Direction nextDirection = ((TourRook) piece).getNextDirection();

                    switch (nextDirection) {
                        case UP:
                            if (currPlayer == 1)
                                icon = new ImageIcon("img/tourRookUpRed.png");
                            else
                                icon = new ImageIcon("img/tourRookUpBlack.png");
                            break;
                        case RIGHT:
                            if (currPlayer == 1)
                                icon = new ImageIcon("img/tourRookRightRed.png");
                            else
                                icon = new ImageIcon("img/tourRookRightBlack.png");
                            break;
                        case DOWN:
                            if (currPlayer == 1)
                                icon = new ImageIcon("img/tourRookDownRed.png");
                            else
                                icon = new ImageIcon("img/tourRookDownBlack.png");
                            break;
                        case LEFT:
                            if (currPlayer == 1)
                                icon = new ImageIcon("img/tourRookLeftRed.png");
                            else
                                icon = new ImageIcon("img/tourRookLeftBlack.png");
                            break;
                        default:
                            icon = new ImageIcon(new BufferedImage(100, 100, BufferedImage.TRANSLUCENT));
                    }
                    break;
                case "DeathKnight":
                    if (currPlayer == 1)
                        icon = new ImageIcon("img/darkKnightRed.png");
                    else
                        icon = new ImageIcon("img/darkKnightBlack.png");
                    break;
                default:
                    icon = new ImageIcon(new BufferedImage(100, 100, BufferedImage.TRANSLUCENT));
            }
        } else
            icon = new ImageIcon(new BufferedImage(100, 100, BufferedImage.TRANSLUCENT));

        return icon;
    }

    /**
     * Updates a JButton icon
     * @param row Row on board
     * @param column Column on board
     */
    void setButtonIcon(int row, int column) {
        ImageIcon icon = getButtonIcon(row, column);
        buttons[row][column].setIcon(icon);
    }

    /**
     * Remove any JButton icon
     * @param row Row on board
     * @param column Column on board
     */
    private void clearButtonIcon(int row, int column) {
        ImageIcon translucentIcon = new ImageIcon(new BufferedImage(100, 100, BufferedImage.TRANSLUCENT));
        buttons[row][column].setIcon(translucentIcon);
    }

    /**
     * Black and grey color scheme for the chess board
     * @param row Row on board
     * @param column Column on board
     */
    private void setButtonBackground(int row, int column) {
        Color color;
        if ((row + column) % 2 != 0)
            color = Color.DARK_GRAY;
        else
            color = Color.WHITE;

        buttons[row][column].setBackground(color);
    }

    /**
     * Adds button into the main JFrame
     * @param row Row on board
     * @param column Column on board
     */
    private void addButtonToFrame(int row, int column) {
        buttons[row][column].setMargin(new Insets(-2, -2, -2, -2));
        JPanel panel = new JPanel();
        panel.setBounds(column * 100, row * 100, 100, 100);
        panel.add(buttons[row][column]);
        add(panel);
    }

    /**
     * Updates display information after a turn is made
     */
    void updateCheckDisplay() {
        int playerInCheck = chessModel.checkForCheck();

        chessModel.setCheck(playerInCheck);

        int playerInCheckmate = chessModel.checkForCheckmate();

        if (playerInCheckmate == 1) {
            checkmate.setForeground(Color.RED);
            checkmate.setText("Red Is In Mate");
        }
        else if (playerInCheckmate == 2) {
            checkmate.setForeground(Color.BLACK);
            checkmate.setText("Black Is In Mate");
        }
        else
            checkmate.setText("");

        if (playerInCheck == 1) {
            check.setForeground(Color.RED);
            check.setText("Red Is In Check");
        } else if (playerInCheck == 2) {
            check.setForeground(Color.BLACK);
            check.setText("Black Is In Check");
        } else
            check.setText("");
    }

    /**
     * Switches the player after a turn is made
     */
    void updatePlayerDisplay() {
        chessModel.switchPlayer();
        if (getTitle().equals("Red's Move"))
            setTitle("Black's Move");
        else
            setTitle("Red's Move");
    }
}