package Shlomi.Containers.NewSequence;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JPanel;

import Shlomi.Core.Set;
import Shlomi.Core.Basic.Point;
import Shlomi.Core.Basic.Point.STATUS;
import Shlomi.Initalization.Settings;

public class SequenceGraphics extends JPanel{
	private final Set origionalSet;
	
	/**
	 * All the sets in the screen that were duplicated. Not included the origional set.
	 */
	public ArrayList<Set> setsDuplicated;
	
	/**
	 * Current set to work with.
	 */
	private Set currentSet;
	
	public SequenceGraphics(Set set, int amountOfSets) {
		setPreferredSize(new Dimension(Settings.GRAPHICS_CONTAINER_WIDTH, Settings.GRAPHICS_CONTAINER_HEIGHT));
		
		this.origionalSet = set.duplicate();
		this.setsDuplicated = new ArrayList<Set>(amountOfSets);
		
		
		System.out.println("set hash: " + set.hashCode() + " , origionalSet hash: " + origionalSet.hashCode());

		//Set working set to original. (For the first time only)
		currentSet = origionalSet;
		
		//For each set to create...
		for(int i = 0; i < amountOfSets; i++) {
			
			//Offset the set
			currentSet.offset(50, -50);
			//Add to list
			setsDuplicated.add(currentSet);

			//Create new duplicated set
			currentSet = currentSet.duplicate();
			
			//TODO: Somehow original set is treated like duplicated, its moved.
		}
		
		
		//Print original set
		System.out.println("Printing origional set \n" + origionalSet);
		
		//Print all duplicated sets
		for(Set s : setsDuplicated)
			System.out.println("\nPrinting duplicated set \n" + s);
		
		
		repaint();
	}
	
	/**
	 * Draw the sequence.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		origionalSet.draw(g);
		
		for(Set s : setsDuplicated)
			s.draw(g);
	}



	
}
