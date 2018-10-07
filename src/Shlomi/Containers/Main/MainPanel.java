package Shlomi.Containers.Main;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Shlomi.Containers.Editor.EditorFrame;
import Shlomi.Containers.NewSequence.SequenceFrame;
import Shlomi.Core.LoadedSave;
import Shlomi.Initalization.Settings;

public class MainPanel extends JPanel implements Settings {

	public JButton btn_EditSave, btn_NewSequence;
	// public SetGraphicsContainer container;
	public JLabel imageLabel;
	//Loaded save (At start its null, then when we open save and this catches the save.)
	private LoadedSave loadedSave;

	public MainPanel() {
		
		imageLabel = new JLabel();
		imageLabel.setToolTipText("Set preview image");
		imageLabel.setPreferredSize(new Dimension(Settings.GRAPHICS_CONTAINER_HEIGHT, Settings.GRAPHICS_CONTAINER_WIDTH));
		imageLabel.setBorder(BorderFactory.createTitledBorder("Preview"));

		JButton btn_ChooseFile = new JButton("Select set to load");
		btn_EditSave = new JButton("Edit");
		JButton btn_OpenSaveDirection = new JButton();
		this.btn_NewSequence = new JButton("New sequence");

		btn_ChooseFile.setToolTipText("Choose set to load and view");

		btn_EditSave.setToolTipText("Edit set");
		btn_EditSave.setEnabled(false);

		btn_OpenSaveDirection.setText("Open save direction");
		btn_OpenSaveDirection.setToolTipText("Open save direction");

		btn_NewSequence.setToolTipText("Create a sequence of sets.");
		btn_NewSequence.setEnabled(false);
		
		btn_ChooseFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser(save_directory);
				fc.setDialogTitle("Choose set");
				int returnVal = fc.showOpenDialog(null);
				File chosenFile = fc.getSelectedFile();

				// Check if file exists
				if (returnVal == JFileChooser.APPROVE_OPTION && chosenFile != null && chosenFile.exists()) {
					try {
						loadSave(chosenFile);
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
				} else
					System.out.println("Error loading.");
			}
		});

		btn_EditSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openEditor();
			}
		});

		btn_OpenSaveDirection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new File(Settings.save_directory));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btn_NewSequence.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openNewSequence();
			}
		});

		add(btn_ChooseFile);
		add(btn_EditSave);
		add(btn_OpenSaveDirection);
		add(btn_NewSequence);

		add(imageLabel);
		imageLabel.setVisible(true);
	}

	/**
	 * Private function only to load chosen file. Shows the image of the set.
	 * @param chosenFile
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void loadSave(File chosenFile) throws ClassNotFoundException, IOException {
		System.out.println("Chosen: " + chosenFile.getAbsolutePath());

		loadedSave = new LoadedSave(chosenFile);

		imageLabel.setIcon(loadedSave.getSave().getImage());
		btn_EditSave.setEnabled(true);
		btn_NewSequence.setEnabled(true);
	}

	/**
	 * This is called when we chose save to load, and then press "Edit" button.
	 * Opens editor with loaded set.
	 */
	public void openEditor() {
		EditorFrame frame = new EditorFrame();
		frame.loadSave(loadedSave);
	}
	
	/**
	 * Opens the 'new sequence' window.
	 */
	private void openNewSequence() {
		//If user loaded a save then we continue
		if(loadedSave != null) {
			SequenceFrame frame = new SequenceFrame(loadedSave.getSave().getSet());
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
	}
}
