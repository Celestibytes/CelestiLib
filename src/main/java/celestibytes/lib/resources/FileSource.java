package celestibytes.lib.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileSource {
	
	private File file = null;
	private ResourceType resourceTypeHint;
	
	public FileSource(File file) {
		this.file = file;
	}
	
	/** Accepts TEXT and BINARY */
	public FileSource setResourceTypeHint(ResourceType hint) {
		this.resourceTypeHint = hint;
		return this;
	}
	
	public boolean exists() {
		return this.file != null && this.file.exists() && this.file.isFile();
	}
	
	/** Note: returns null if there are any exceptions */
	public FileInputStream getInputStream() {
		try {
			return new FileInputStream(this.file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public File getFile() {
		return this.file;
	}
}
