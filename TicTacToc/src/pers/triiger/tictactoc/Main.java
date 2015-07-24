package pers.triiger.tictactoc;

import javax.swing.*;
public class Main {

    public static void main(String[] args) {
	// write your code here
        TicFrame ticFrame = new TicFrame();
        ticFrame.pack();
        ticFrame.setVisible(true);
        ticFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
