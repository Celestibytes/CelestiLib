package celestibytes.lib.resources;

import java.awt.image.BufferedImage;

public class ResourceBufferedImage extends ResourceImage {

	public ResourceBufferedImage(Respath path) {
		super(path, true);
	}

	@Override
	public boolean handleLoading(BufferedImage img) {return false;}

}
