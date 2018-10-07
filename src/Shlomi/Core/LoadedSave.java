package Shlomi.Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadedSave {
	private File file;
	private Save save;

	/**
	 * LoadedSave is class containing 2 components: File of the save. Save
	 * class. This class is only a container for save class, with saved file.
	 * 
	 * @see "Save class"
	 * @param file
	 *            of save.
	 */
	public LoadedSave(File file) throws ClassNotFoundException, IOException {
		this.file = file;
		this.save = getSaveFromFile();
	}

	public File getFile() {
		return file;
	}

	public Save getSave() {
		return save;
	}

	private Save getSaveFromFile() throws IOException, ClassNotFoundException {
		Save tmp = null;
		FileInputStream fileIn = new FileInputStream(file);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		tmp = (Save) in.readObject();
		in.close();
		fileIn.close();
		return tmp;
	}

	@Override
	public String toString() {
		return "[LoadedSave] -Save: \n" + save + "\n-File: \n" + file.getAbsolutePath();
	}
}
