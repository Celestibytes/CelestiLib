package celestibytes.lib.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;

import javax.imageio.ImageIO;

public abstract class ResourceImage implements IResource {
	
	private BufferedImage img;
	private boolean cacheImage;
	private Respath path;
	private boolean lisLoaded = false;
	
	public ResourceImage(Respath path, boolean cacheImage) {
		this.cacheImage = cacheImage;
		this.path = path;
	}

	@Override
	public FilenameFilter getFileFilter() {
		return new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return false;
			}
		};
	}
	// return pathname.isFile() && pathname.getName().toLowerCase().endsWith(".png");

	@Override
	public IResLoader getCustomResLoader() {
		return ImageLoader.INSTANCE;
	}

	@Override
	public ResourceType getResourceType() {
		return null;
	}

	@Override
	public Respath getRespath() {
		return path;
	}

	@Override
	public void reload() {
		// TODO
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
	@Override
	public boolean isLoaded() {
		return lisLoaded;
	}
	
	public abstract boolean handleLoading(BufferedImage img);
	
	public static class ImageLoader implements IResLoader<ResourceImage> {
		
		private static ImageLoader INSTANCE = new ImageLoader();

		@Override
		/** @return the ResourceImage passed in */
		public ResourceImage loadResource(ResourceImage res) {
			if(!res.cacheImage) {
				try {
					if(res.getRespath().getFile().exists()) {
						if(res.getRespath().getFile().isDirectory()) {
							return res; // return something else?
						}
					}
				} catch(Throwable e) {
					e.printStackTrace();
				}
				
				try {
					if(res.handleLoading(ImageIO.read(res.getRespath().getFile()))) {
						res.lisLoaded = true;
					}
				} catch(Exception e) {
					System.err.println("Image loading failed with the following exception:");
					e.printStackTrace();
				}
				return res;
				
			} else {
				try {
					res.img = ImageIO.read(res.getRespath().getFile());
					res.lisLoaded = true;
				} catch(Exception e) {
					System.err.println("Image loading failed with the following exception:");
					e.printStackTrace();
				}
				return res;
			}
		}

		@Override
		public IResLoader<ResourceImage> getInstance() {
			return INSTANCE;
		}
		
	}

}
