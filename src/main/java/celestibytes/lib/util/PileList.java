package celestibytes.lib.util;

import java.util.Iterator;

public class PileList<VTYPE> {
	
	private PileListItem<VTYPE> first;
	private PileListItem<VTYPE> last;
	
	private PLIIter<VTYPE> iter;
	private RPLIIter<VTYPE> revIter;
	
	private ItemIter<VTYPE> iiter;
	private RItemIter<VTYPE> revIiter;
	
	
	public PileList() {
		iter = new PLIIter<VTYPE>();
		revIter = new RPLIIter<VTYPE>();
		
		iiter = new ItemIter<VTYPE>();
		revIiter = new RItemIter<VTYPE>();
	}
	
	
	public void addItem(VTYPE item) {
		PileListItem<VTYPE> buf = new PileListItem<VTYPE>(this, item, last, null);
		
		if(first == null) {
			first = buf;
		} else {
			last.next = buf;
		}
		last = buf;
	}
	
	public VTYPE getFirst() {
		if(first == null) {
			 return null;
		}
		return first.item;
	}
	
	public VTYPE getLast() {
		if(last == null) {
			 return null;
		}
		return last.item;
	}
	
	public void removeLast() {
		if(last == null) {
			return;
		}
		if(last.prev == null) {
			last = null;
			return;
		}
		
		last.prev.next = null;
		last = last.prev;
	}
	
	public void removeFirst() {
		if(first == null) {
			return;
		}
		if(first.next == null) {
			first = null;
			return;
		}
		first.next.prev = null;
		first = first.next;
	}
	
	public Iterator<PileListItem<VTYPE>> getPileListItemIterator(boolean reversed) {
		if(reversed) {
			revIter.setCurr(last);
			return revIter;
		}
		iter.setCurr(first);
		return iter;
	}
	
	public Iterator<VTYPE> getItemIterator(boolean reversed) {
		if(reversed) {
			revIiter.setCurr(last);
			return revIiter;
		}
		iiter.setCurr(first);
		return iiter;
	}
	
	public static class PileListItem<VTYPE> {
		private final PileList<VTYPE> owner;
		private VTYPE item;
		private PileListItem<VTYPE> prev;
		private PileListItem<VTYPE> next;
		
		public PileListItem(PileList<VTYPE> owner, VTYPE item, PileListItem<VTYPE> prev, PileListItem<VTYPE> next) {
			this.owner = owner;
			this.item = item;
			this.prev = prev;
			this.next = next;
		}
		
		public PileListItem<VTYPE> getNext() {
			return next;
		}
		
		public PileListItem<VTYPE> getPrev() {
			return prev;
		}
		
		public boolean hasNext() {
			return next != null;
		}
		
		public boolean hasPrev() {
			return prev != null;
		}
		
		public VTYPE getItem() {
			return item;
		}
		
		public void setItem(VTYPE item) {
			this.item = item;
		}
		
		public void remove() {
			if(owner.first == this) {
				owner.first = owner.first.next;
				owner.first.prev = null;
			} else if(owner.last == this) {
				owner.last = owner.last.prev;
				owner.last.next = null;
			} else {
				next.prev = prev;
				prev.next = next;
			}
		}
	}
	
	private static class PLIIter<VTYPE> implements Iterator<PileListItem<VTYPE>> {

		private PileListItem<VTYPE> curr = null;
		
		@Override
		public boolean hasNext() {
			if(curr != null) {
				return curr.hasNext();
			}
			
			return false;
		}

		@Override
		public PileListItem<VTYPE> next() {
			if(curr != null) {
				curr = curr.next;
				return curr;
			}
			
			return null;
		}

		@Override
		public void remove() {
//			curr.remove();
			System.err.println("[private class: CelestiLib.celestibytes.lib.util.PileList.PLIIter]: Not implemented");
		}
		
		private void setCurr(PileListItem<VTYPE> curr) {
			this.curr = curr;
		}
		
	}
	
	private static class RPLIIter<VTYPE> implements Iterator<PileListItem<VTYPE>> {

		private PileListItem<VTYPE> curr = null;
		
		@Override
		public boolean hasNext() {
			if(curr != null) {
				return curr.hasPrev();
			}
			
			return false;
		}

		@Override
		public PileListItem<VTYPE> next() {
			if(curr != null) {
				curr = curr.prev;
				return curr;
			}
			
			return null;
		}

		@Override
		public void remove() {
//			curr.remove();
			System.err.println("[private class: CelestiLib.celestibytes.lib.util.PileList.PLIIter]: Not implemented");
		}
		
		private void setCurr(PileListItem<VTYPE> curr) {
			this.curr = curr;
		}
		
	}
	
	private static class ItemIter<VTYPE> implements Iterator<VTYPE> {

		private PileListItem<VTYPE> curr = null;
		
		@Override
		public boolean hasNext() {
			if(curr != null) {
				return curr.hasNext();
			}
			
			return false;
		}

		@Override
		public VTYPE next() {
			if(curr != null) {
				curr = curr.next;
				return curr.item;
			}
			
			return null;
		}

		@Override
		public void remove() {
//			curr.remove();
			System.err.println("[private class: CelestiLib.celestibytes.lib.util.PileList.ItemIter]: Not implemented");
		}
		
		private void setCurr(PileListItem<VTYPE> curr) {
			this.curr = curr;
		}
		
	}
	
	private static class RItemIter<VTYPE> implements Iterator<VTYPE> {

		private PileListItem<VTYPE> curr = null;
		
		@Override
		public boolean hasNext() {
			if(curr != null) {
				return curr.hasPrev();
			}
			
			return false;
		}

		@Override
		public VTYPE next() {
			if(curr != null) {
				curr = curr.prev;
				return curr.item;
			}
			
			return null;
		}

		@Override
		public void remove() {
//			curr.remove();
			System.err.println("[private class: CelestiLib.celestibytes.lib.util.PileList.ItemIter]: Not implemented");
		}
		
		private void setCurr(PileListItem<VTYPE> curr) {
			this.curr = curr;
		}
		
	}
	
}
