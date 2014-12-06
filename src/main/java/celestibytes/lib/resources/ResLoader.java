package celestibytes.lib.resources;

public class ResLoader implements IResLoader<IResource> {
	
	public static ResLoader INSTANCE = new ResLoader();

	@Override
	public IResource loadResource(IResource res) {
		if(res.getCustomResLoader() != null) {
			return res.getCustomResLoader().loadResource(res);
		}
		System.out.println("nulldd");
		return null;
	}

	@Override
	public IResLoader<IResource> getInstance() {
		return INSTANCE;
	}
	
}
