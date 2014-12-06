package celestibytes.lib.ldf;

import java.util.HashMap;
import java.util.Map;

/** Lunar Data Format */
public class LDF implements ILDFType {
	
	private Map<String, ILDFType> data;
	
	public LDF() {
		data = new HashMap<String, ILDFType>();
	}
	
	public LDF(LDF old) {
		data = new HashMap<String, ILDFType>();
		data.putAll(old.data);
	}
	
	public void set(String name, ILDFType type) {
		
	}
	
	public ILDFType get(String name) {
		return null;
	}
	
	public boolean exists(String name) {
		return false;
	}

	@Override
	public byte[] getData() {
		return null;
	}

	@Override
	public int getByteCount() {
		return 0;
	}
}
