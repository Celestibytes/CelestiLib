package celestibytes.lib.resources;

import java.io.FilenameFilter;



public interface IResource {

	public FilenameFilter getFileFilter();
	
	/** @return null to use the normal ResLoader */
	public IResLoader<IResource> getCustomResLoader();
	
	public ResourceType getResourceType();
	
	public Respath getRespath();
	
	public void reload();
	
	public boolean isLoaded();
	
}
