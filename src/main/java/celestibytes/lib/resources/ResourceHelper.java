package celestibytes.lib.resources;

import java.io.File;

public class ResourceHelper {
	
	public static boolean isDirectory(IResource res) {
		Respath path = res.getRespath();
		File file = null;
		if(path != null) {
			file = path.getFile();
			if(file != null) {
				if(file.exists()) {
					return file.isDirectory();
				}
			}
		}
		return false;
	}
}
