package celestibytes.lib.resources;

import java.io.File;

public class Respath {
	
	private String domain, path;
	
	public Respath(String domain, String path) {
		if(domain.endsWith("/")) {
			this.domain = domain;
		} else {
			this.domain = domain + "/";
		}
		this.path = path;
	}
	
	public Respath(String path) {
		this.domain = "res/";
		this.path = path;
	}
	
	public File getFile() {
		File ret;
		if(domain == null) {
			ret = new File(path);
		} else {
			ret = new File(domain+path);
		}
		System.out.println("getFile: path=\"" + ret.getAbsolutePath() + "\"");
		
		if(ret.exists()) {
			return ret;
		}
		
		return null;
	}
	
	public String getCombinedPath() {
		if(domain == null) {
			return path;
		}
		for(int i=domain.length()-1;i > -1;i++) {
			if(domain.charAt(i) != '/') {
				for(int q=0;q < path.length();q++) {
					if(path.charAt(q) != '/') {
						return domain.substring(0, i) + "/" + path.substring(q);
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public int hashCode() {
		return sun.misc.Hashing.stringHash32(getCombinedPath());
	}
	
	public static boolean exists(Respath rpth) {
		if(rpth.getFile() != null) {
			return rpth.getFile().exists();
		}
		
		return false;
	}
	
}
