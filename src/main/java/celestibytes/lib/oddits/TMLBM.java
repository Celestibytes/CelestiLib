package celestibytes.lib.oddits;

public class TMLBM {
	
	private static final int MAX_INSTR_SIZE = 4;
	
	private int data, pos, instrPos, instrSize;
	private byte[] instrs = null;
	
	
	public TMLBM(int pos, int instrSize) {
		this.pos = pos;
		this.instrSize = Math.min(instrSize, MAX_INSTR_SIZE);
	}
	
	public int getNext() {
		return 0;
	}
	
	private void runInstr(byte instr) {
		
	}
	
	public int getInstr() {
		int ret = 0;
		for(int i=0;i<instrSize;i++) {
			ret |= getInstrBit(instrPos+i) << instrSize-1-i;
		}
		instrPos+=instrSize;
		instrPos %= instrs.length * MAX_INSTR_SIZE;
		return ret;
	}
	
	public int getInstrBit(int pos) {
		int instrsBitCount = MAX_INSTR_SIZE*instrs.length;
		if(instrsBitCount < MAX_INSTR_SIZE) {
			System.err.println("No instructions!");
			return 0;
		}
		int index = pos % instrsBitCount;
		int arrIndx = index/MAX_INSTR_SIZE;
		return (instrs[arrIndx] >> (MAX_INSTR_SIZE-1-(index%MAX_INSTR_SIZE))) & 1;
	}
	
	// private
	public int read(int amount) {
		amount = amount < 0 ? 1 : amount > 32 ? 32 : amount;
		int ret = 0;
		int idx = pos;
		for(int i=0;i<amount;i++) {
			idx %= 32;
			ret |= ((data >> (31-idx))&1) << (amount-1-i);
			idx++;
		}
		return ret;
	}
	
	public void generInstrs(int byteCount) {
		
	}
	
	/** Only 4 rightmost bits are used (4-bit instructions) */
	public void setInstrs(byte[] instrs) {
		this.instrs = instrs;
	}
	
	// Temp
	public void setData(int data) {
		this.data = data;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
		this.pos %= 32;
		if(this.pos < 0) {
			this.pos = 32-this.pos;
		}
	}
}
