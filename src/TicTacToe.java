
import javax.swing.*;


/**
 * In class TicTacToe example
 * 
 * @author yaw (adapted from mumey)
 * @version 13 Jan 2015
 */
public class TicTacToe extends JFrame {
    //constructor
    public TicTacToe() {
        super("Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(new TTTPanel());
        pack();
        setLocationRelativeTo(null);    //put the gui in the center of the screen
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        TicTacToe newGame = new TicTacToe(); 
    }
}
