package celestibytes.lib.ldf;

public class LDFInt implements ILDFType {

	public int value;
	
	public LDFInt(int value) {
		this.value = value;
	}
	
	public LDFInt(Integer value) {
		this.value = value.intValue();
	}
	
	@Override
	public byte[] getData() {
		return new byte[]{(byte)(value&0xFF), (byte)((value>>8)&0xFF), (byte)((value>>16)&0xFF), (byte)((value>>24)&0xFF)};
	}

	@Override
	public int getByteCount() {
		return 4;
	}

}
