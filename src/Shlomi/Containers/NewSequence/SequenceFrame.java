package Shlomi.Containers.NewSequence;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Shlomi.Core.Set;

public class SequenceFrame extends JFrame{
	private JPanel panel;
	private Set loadedSet;
	
	public SequenceFrame(Set loadedSet) {
		this.loadedSet = loadedSet;
		setTitle("New sequence");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		initPanel();
		add(panel);
		
		pack();
		
		setLocationRelativeTo(null);
	}

	private void initPanel() {
		panel = new JPanel();
		
		JButton btn_Ok = new JButton("Create");
		JLabel lbl_amountOfSets = new JLabel("Insert amount of sets: ");
		JTextField txt_amountOfSets = new JTextField();
		txt_amountOfSets.setPreferredSize(new Dimension(100, 25));
		
		btn_Ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				checkNumberOfSequenceValue(txt_amountOfSets.getText());
			}
		});
		
		panel.add(lbl_amountOfSets);
		panel.add(txt_amountOfSets);
		panel.add(btn_Ok);
	}
	
	/**
	 * Check the entered number of sets . (If its int, if its above 0, ect)
	 * @param numOfSetsValue
	 */
	private void checkNumberOfSequenceValue(String numOfSetsValue) { 
		try{
			int amountOfSets = Integer.parseInt(numOfSetsValue);
			System.out.println("amount = " + amountOfSets);
			if(amountOfSets > -1) {
				if(loadedSet.getEndPoints().size() != 0) {
					if(loadedSet.isConnected()) {
						SequenceGraphicsFrame frame = new SequenceGraphicsFrame(loadedSet, amountOfSets);
						frame.setVisible(true);
						
						setVisible(false);
						dispose();
					}
					else
						JOptionPane.showMessageDialog(null, "Some points are not connected in the set.");
				}
				else 
					JOptionPane.showMessageDialog(null, "No end point found in the set. Please edit the set.");
			}
			else 
				JOptionPane.showMessageDialog(null, "Must be positive number above -1.");
		}
		catch (NumberFormatException ex) {
			//ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Check again.");
		}
	}
}
