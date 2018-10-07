package Shlomi.Containers.NewSequence;

import javax.swing.JFrame;

import Shlomi.Core.Set;

public class SequenceGraphicsFrame extends JFrame{
	/**
	 * Create new SequenceGraphicsFrame.<br>
	 * Must have more than 0 end points.
	 * @param set Set to 'duplicate'
	 * @param amountOfSets How much duplications
	 */
	public SequenceGraphicsFrame(Set set, int amountOfSets) {
		setTitle("Sequence");
		
		SequenceGraphics sg = new SequenceGraphics(set,amountOfSets);
		add(sg);
		
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}

}
