package Shlomi.Containers.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Shlomi.Containers.Editor.EditorFrame;

public class MainFrame extends JFrame {

	private JMenuItem credits, license, createNewSet, instructions;
	private String creditsString, licenseString, instructionsString;
	private JMenu menu, submenu;
	private JMenuBar menuBar;

	public MainFrame() {
		setTitle("Tree Generator ~ ShL0Mi5000 ~ TF2Shows");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		creditsString = "Thanks for checking my program. \nI am Shlomi Domnenko, you are allowed to use this code freely with royalty-free!";
		licenseString = "License: By Shlomi Domnenko - Open Source and Royalty-free.\nUse for your own good, freely. No credits needed.";
		instructionsString = "To create set - press on Menu->New set.\nThen you can open it, edit it.\nFor more information visit my channel: TF2Shows";
		
		createMenu();

		add(new MainPanel());

		pack();
		
		setLocationRelativeTo(null);
	}

	private void createMenu() {
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		submenu = new JMenu("More");
		
		menuBar.add(menu);
		menuBar.add(submenu);

		credits = new JMenuItem("Credits");
		credits.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, creditsString, "Credits", 1);
			}
		});

		license = new JMenuItem("License");
		license.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null, licenseString, "License", 1);
			}
		});

		createNewSet = new JMenuItem("New set - Open editor");
		createNewSet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showCreateNewSet();
			}
		});
		
		instructions = new JMenuItem("How to use");
		instructions.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, instructionsString, "How to use program", 1);
			}
		});
		

		menu.add(createNewSet);
		menu.addSeparator();
		menu.add(license);
		menu.add(credits);
		
		submenu.add(instructions);

		setJMenuBar(menuBar);
	}

	private void showCreateNewSet() {
		EditorFrame frame = new EditorFrame();
		frame.setVisible(true);
	}

}
