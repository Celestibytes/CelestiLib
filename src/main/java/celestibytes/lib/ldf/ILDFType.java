package celestibytes.lib.ldf;

public interface ILDFType {
	
	/** @return the data of this type in Little Endian byte order**/
	public byte[] getData();
	
	public int getByteCount();
	
}
