package celestibytes.lib.resources;

public interface IResLoader<RES extends IResource> {
	
	public RES loadResource(RES res);
	
	public IResLoader<RES> getInstance();
	
}
