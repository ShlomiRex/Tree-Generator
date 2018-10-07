package Shlomi.Containers.Editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import Shlomi.Containers.Editor.ColorChooserButton.ColorChangedListener;
import Shlomi.Core.LoadedSave;
import Shlomi.Core.Save;
import Shlomi.Initalization.Settings;

public class EditorFrame extends JFrame implements Settings {

	public ColorChooserButton btn_Color;
	private Image endPointImage, newPointImage, lineImage, lineUndoImage, removePointImage;

	private GraphicsContainer graphicsContainer;

	private JToolBar toolbar;

	private LoadedSave loadedSave;
	/**
	 * New set frame is a window with toolbar and you can create a set, save the
	 * set, ect.
	 */
	public EditorFrame() {
		setTitle("Set editor");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Initialize toolbar (top bar, with icons n' shit)
		initToolbar();
		// Add toolbar to JFrame, border is north(Left)
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.NORTH);

		graphicsContainer = new GraphicsContainer(this);
		add(graphicsContainer,BorderLayout.CENTER);
		pack();
		
		setLocationRelativeTo(null);
	}

	/**
	 * Used in initializing button's icons.
	 * 
	 * @param path
	 * @param description
	 * @return
	 */
	private ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Save image of graphics container, save it as img.png.
	 * 
	 * @throws IOException
	 */
	private BufferedImage getSnapshot() throws IOException {
		System.out.println("Saving snapshot..");
		BufferedImage image = new BufferedImage(graphicsContainer.getPreferredSize().width,
				graphicsContainer.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		graphicsContainer.paint(g);

		// Save image for testing
		ImageIO.write(image, "png", new File("lastImage.png"));

		return image;
	}

	/**
	 * Initialize top tool bar.
	 */
	private void initToolbar() {
		int scaleDownIcon = 8;

		toolbar = new JToolBar();
		TitledBorder toolbarBorder = new TitledBorder(BorderFactory.createLoweredBevelBorder(), "Tools");
		toolbar.setBorder(toolbarBorder);

		// Initialize tools
		JButton btn_endPoint = new JButton();
		JButton btn_NewPoint = new JButton();
		JButton btn_Line = new JButton();
		JButton btn_LineUndo = new JButton();
		JButton btn_removePoint = new JButton();
		JButton btn_Save = new JButton();

		// Initialize icons and set icons for buttons
		ImageIcon icon_endPoint = createImageIcon(images_directory + "endPoint.png", "Create END point.");
		ImageIcon icon_NewPoint = createImageIcon(images_directory + "NewPoint.png", "Create NORMAL point.");
		ImageIcon icon_Line = createImageIcon(images_directory + "line.png", "Connect 2 points to create a line");
		ImageIcon icon_LineUndo = createImageIcon(images_directory + "lineUndo.png", "Undo a line.");
		ImageIcon icon_removePoint = createImageIcon(images_directory + "removePoint.png", "Select a point to remove.");
		btn_Save.setText("Save set");

		endPointImage = icon_endPoint.getImage();
		newPointImage = icon_NewPoint.getImage();
		lineImage = icon_Line.getImage();
		lineUndoImage = icon_LineUndo.getImage();
		removePointImage = icon_removePoint.getImage();

		// Scale
		endPointImage = endPointImage.getScaledInstance(endPointImage.getWidth(null) / scaleDownIcon,
				endPointImage.getHeight(null) / scaleDownIcon, Image.SCALE_SMOOTH);
		newPointImage = newPointImage.getScaledInstance(newPointImage.getWidth(null) / scaleDownIcon,
				newPointImage.getHeight(null) / scaleDownIcon, Image.SCALE_SMOOTH);
		lineImage = lineImage.getScaledInstance(lineImage.getWidth(null) / scaleDownIcon,
				lineImage.getHeight(null) / scaleDownIcon, Image.SCALE_SMOOTH);
		lineUndoImage = lineUndoImage.getScaledInstance(lineUndoImage.getWidth(null) / scaleDownIcon,
				lineUndoImage.getHeight(null) / scaleDownIcon, Image.SCALE_SMOOTH);
		removePointImage = removePointImage.getScaledInstance(removePointImage.getWidth(null) / scaleDownIcon,
				removePointImage.getHeight(null) / scaleDownIcon, Image.SCALE_SMOOTH);

		// Set icons insted of images (buttons can only have icons)
		icon_endPoint.setImage(endPointImage);
		icon_NewPoint.setImage(newPointImage);
		icon_Line.setImage(lineImage);
		icon_LineUndo.setImage(lineUndoImage);
		icon_removePoint.setImage(removePointImage);

		// Set buttons images
		btn_endPoint.setIcon(icon_endPoint);
		btn_NewPoint.setIcon(icon_NewPoint);
		btn_Line.setIcon(icon_Line);
		btn_LineUndo.setIcon(icon_LineUndo);
		btn_removePoint.setIcon(icon_removePoint);

		// Set text when hovering on button
		btn_endPoint.setToolTipText(icon_endPoint.getDescription());
		btn_NewPoint.setToolTipText(icon_NewPoint.getDescription());
		btn_Line.setToolTipText(icon_Line.getDescription());
		btn_LineUndo.setToolTipText(icon_LineUndo.getDescription());
		btn_removePoint.setToolTipText(icon_removePoint.getDescription());
		btn_Save.setToolTipText("Save set, you can later use it.");

		// Buttons events

		btn_endPoint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				graphicsContainer.clickedEndPoint();
			}
		});
		btn_NewPoint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				graphicsContainer.clickeNewPoint();
			}
		});
		btn_Line.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				graphicsContainer.clickedLine();
			}
		});
		btn_LineUndo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				graphicsContainer.undoLine();
			}
		});
		btn_removePoint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				graphicsContainer.clickedRemovePoint();
			}
		});
		btn_Save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					clickedSave(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// Add tools to toolbar
		toolbar.add(btn_endPoint);
		toolbar.add(btn_NewPoint);
		toolbar.add(btn_Line);
		toolbar.add(btn_LineUndo);
		toolbar.add(btn_removePoint);

		toolbar.addSeparator();

		// Default color is black. (White is not good :x )
		ColorChooserButton btn_Color_Chooser = new ColorChooserButton(Color.BLACK);
		btn_Color_Chooser.setToolTipText("Choose color for a line");
		btn_Color_Chooser.setPreferredSize(new Dimension(TABLE_BUTTON_WIDTH, TABLE_BUTTON_HEIGHT));
		btn_Color_Chooser.addColorChangedListener(new ColorChangedListener() {
			@Override
			public void colorChanged(Color newColor) {
				// In SetGraphicsContainer we then get the color chooser's
				// color.
				System.out.println("Changed color to: " + btn_Color_Chooser.getSelectedColor());
			}
		});
		toolbar.add(btn_Color_Chooser);

		JButton btn_Reset = new JButton("Clear set");
		btn_Reset.setToolTipText("Clear the set, including: Points, lines, colors, and mouse status.");
		btn_Reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				graphicsContainer.reset();
			}
		});
		toolbar.addSeparator();
		toolbar.add(btn_Reset);

		btn_Color = btn_Color_Chooser;

		toolbar.addSeparator();
		toolbar.add(btn_Save);
	}

	/**
	 * Called from outside of this class, it will load and paint the set.
	 * @param set
	 */
	public void loadSave(LoadedSave saveToLoad) {
		loadedSave = saveToLoad;
		graphicsContainer.loadSet(loadedSave.getSave().getSet());
		repaint();
		
		System.out.println("Loading set: ");
		graphicsContainer.printSet();
	}

	/**
	 * Save the set to .ser file. It can later be used to load the set. Create
	 * save directory if there isn't and create save file - class Save
	 * @param overrideSave 
	 * 
	 * @throws IOException
	 */
	private void clickedSave(boolean overrideSave) throws IOException {
		// Create a directory of saves. (If it doesn't exists)
		File saveDir = new File(save_directory);
		if (saveDir.exists() == false) {
			System.out.println("Creating save folder..");
			saveDir.mkdir();
		}

		int numOfSave = 0;
		String tmpSaveName = "save" + numOfSave + ".ser";
		File saveFile = new File("Saves/" + tmpSaveName);
		
		if(overrideSave) 
			saveFile = loadedSave.getFile();
		else
			while (saveFile.exists() == true) {
				numOfSave++;
				tmpSaveName = "save" + numOfSave + ".ser";
				saveFile = new File(save_directory + tmpSaveName);
			}

		System.out.println("Creating save " + saveFile.getAbsolutePath());

		ImageIcon img = new ImageIcon(getSnapshot());

		Save save = new Save(graphicsContainer.getSet(), img);

		FileOutputStream fileOut = new FileOutputStream(saveFile);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(save);
		out.close();
		fileOut.close();

		JOptionPane.showMessageDialog(this, "Saved your set as " + saveFile.getName(), "Save is complete!",
				JOptionPane.PLAIN_MESSAGE);
	}
}
