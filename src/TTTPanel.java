
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/**
 * TTTPanel will display and hold the logic for the game.
 *
 * @author yaw (adapted from mumey)
 * @version 23 Jan 2015
 */
public class TTTPanel extends JPanel {

    //board dimensions
    private int size = 300;
    // back-end: board data
    private String curPlayer, player, comp;
    private String[][] board;
    private boolean[] check;

    //constructor
    public TTTPanel() {
        setPreferredSize(new Dimension(size, size));
        addMouseListener(new TTTMouseListener());

        int selection = JOptionPane.showConfirmDialog(this, "Would you like to be Xs?");
        curPlayer = "X";
        if (selection == 0) {
            player = curPlayer;
            comp = "O";
        } else if (selection == 1) {
            comp = curPlayer;
            player = "O";
        } else {

        }

        //back-end:  initialize the board
        board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = " ";
            }
        }

        if (curPlayer.equals(comp)) {
            bleh();
        }
    }

    //back-end to front-end.  Get data from board and draw the GUI
    @Override
    public void paint(Graphics g) {
        //front-end.  Draw GUI
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.RED);
        g.drawLine(0, 100, 300, 100);   //first horizontal line
        g.drawLine(0, 200, 300, 200);   //second horizontal line
        g.drawLine(100, 0, 100, 300);   //first vertical line
        g.drawLine(200, 0, 200, 300);   //second vertical line

        Font f = new Font("Times", Font.PLAIN, 50);
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics();

        int a = fm.getAscent();
        int h = fm.getHeight();

        //back-end to front-end.  Populate GUI with values from array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String curSquare = board[i][j];
                int w = fm.stringWidth(curSquare);
                //slightly different from class notes, becuase of lower left hand starting point
                g.drawString(curSquare, 100 * i + 50 - w / 2, 100 * j + 50 + a - h / 2);
            }
        }
    }

    public boolean checkComp(String s) {
        int count;
        int a = 0;
        int b = 0;
        for (int i = 0; i < 3; i++) {
            count = 0;
            for (int j = 0; j < 3; j++) {
                if (s.equals(board[i][j])) {
                    count++;
                } else {
                    a = i;
                    b = j;
                }
            }
            if (count == 2 && " ".equals(board[a][b])) {
                board[a][b] = comp;
                //System.out.println("Vertical Win/Block");
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            count = 0;
            for (int j = 0; j < 3; j++) {
                if (s.equals(board[j][i])) {
                    count++;
                } else {
                    a = j;
                    b = i;
                }
            }
            if (count == 2 && " ".equals(board[a][b])) {
                board[a][b] = comp;
                //System.out.println("Horizontal Win/Block");
                return true;
            }
        }

        count = 0;
        for (int i = 0, j = 0; i < 3; i++, j++) {
            if (s.equals(board[i][j])) {
                count++;
            } else {
                a = i;
                b = j;
            }
        }
        if (count == 2 && " ".equals(board[a][b])) {
            board[a][b] = comp;
            //System.out.println("1st Diagonal Win/Block");
            return true;
        }

        count = 0;
        for (int i = 0, j = 2; i < 3; i++, j--) {
            if (s.equals(board[i][j])) {
                count++;
            } else {
                a = i;
                b = j;
            }
        }
        if (count == 2 && " ".equals(board[a][b])) {
            board[a][b] = comp;
            //System.out.println("2nd Diagonal Win/Block");
            return true;
        }
        return false;
    }

    public void bleh() {        
        if (checkComp(comp)) {
            
        } else if (checkComp(player)) {

        } else {
            Random r = new Random();
            boolean flag = false;
            while (!flag) {
                int c = r.nextInt(3);
                int d = r.nextInt(3);
                if (" ".equals(board[c][d])) {
                    board[c][d] = comp;
                    //System.out.println("Random Placement");
                    flag = true;
                } else {
                    flag = false;
                }
            }
        }        
        switchPlayer();
        checkForGameEnd();
    }

    // INNER CLASS for a Mouse events:
    private class TTTMouseListener implements MouseListener {

        @Override
        public void mousePressed(MouseEvent e) {
            //System.out.println("press");
        }

        public void mouseReleased(MouseEvent e) {
            //System.out.println("release");
        }

        public void mouseEntered(MouseEvent e) {
            //System.out.println("mouse entered");
        }

        public void mouseExited(MouseEvent e) {
            //System.out.println("mouse exited");
        }

        //front-end to back-end.
        public void mouseClicked(MouseEvent e) {
            //get click data from the GUI and convert to back end board spot reference
            int x = e.getX() / 100;
            int y = e.getY() / 100;

            //front-end to back-end.  process click
            if (" ".equals(board[x][y])) {
                board[x][y] = curPlayer;
                repaint();
                switchPlayer();
                checkForGameEnd();
                bleh();
            }
        }
    }

    //back-end
    public void switchPlayer() {
        if (player.equals(curPlayer)) {
            curPlayer = comp;
        } else {
            curPlayer = player;
        }
    }

    //back-end to front-end
    public void checkForGameEnd() {
        if (checkForWin("X")) {
            JOptionPane.showMessageDialog(this, "X wins!!!!");
            System.exit(0);
        } else if (checkForWin("O")) {
            JOptionPane.showMessageDialog(this, "O wins!!!!");
            System.exit(0);
        } else if (checkFullBoard()) {
            JOptionPane.showMessageDialog(this, "Game over, draw.");
            System.exit(0);
        }
    }

    //back-end
    private boolean checkForWin(String p) {
        boolean win = false;
        // check row wins:
        for (int i = 0; i < 3; i++) {
            win = win || (p.equals(board[i][0]) && p.equals(board[i][1]) && p.equals(board[i][2]));
        }
        // check column wins:
        for (int j = 0; j < 3; j++) {
            win = win || (p.equals(board[0][j]) && p.equals(board[1][j]) && p.equals(board[2][j]));
        }
        // check diagonal wins:
        win = win || (p.equals(board[0][0]) && p.equals(board[1][1]) && p.equals(board[2][2]));
        win = win || (p.equals(board[0][2]) && p.equals(board[1][1]) && p.equals(board[2][0]));

        return win;
    }

    //back-end
    private boolean checkFullBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (" ".equals(board[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
}
