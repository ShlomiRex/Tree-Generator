package Shlomi.Core;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Save implements Serializable {
	private static final long serialVersionUID = 1L;

	private ImageIcon imageIcon;
	private Set set;

	/**
	 * Save class is simple class with 2 components: Set and image. Set is the
	 * set. Image is the image of the set. (When taken set)
	 * 
	 * Unlike loaded save, save does NOT have a saved file. in some way..
	 * 
	 * @param set
	 * @param image
	 */
	public Save(Set set, ImageIcon image) {
		this.set = set;
		this.imageIcon = image;
	}

	public ImageIcon getImage() {
		return imageIcon;
	}

	public Set getSet() {
		return set;
	}
}
