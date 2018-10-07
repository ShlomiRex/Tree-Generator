package Shlomi;

import javax.swing.UIManager;

import Shlomi.Containers.Main.MainFrame;

public class Start {
	public static void main(String[] args) throws Exception {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

}
