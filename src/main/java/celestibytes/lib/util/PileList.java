package celestibytes.lib.util;

import java.util.Iterator;

public class PileList<VTYPE> {
	
	private PileListItem<VTYPE> first;
	private PileListItem<VTYPE> last;
	
	private PLIIter<VTYPE> iter;
	private RPLIIter<VTYPE> revIter;
	
	private ItemIter<VTYPE> iiter;
	private RItemIter<VTYPE> revIiter;
	
	private int limit = -1;
	private int count = 0;
	private boolean limitMethod;
	private boolean deleteLast;
	
	
	public PileList() {
		iter = new PLIIter<VTYPE>();
		revIter = new RPLIIter<VTYPE>();
		
		iiter = new ItemIter<VTYPE>();
		revIiter = new RItemIter<VTYPE>();
	}
	
	/** Set limitMethod to false to discard excess added items, true to delete the first or the last depending on deleteLast before adding new one */
	public PileList(int limit, boolean limitMethod, boolean deleteLast) {
		this();
		this.limit = limit;
		this.limitMethod = limitMethod;
		this.deleteLast = deleteLast;
	}
	
	public boolean isFull() {
		return limit == -1 ? false : count >= limit;
	}
	
	public void addItem(VTYPE item) {
		PileListItem<VTYPE> buf = new PileListItem<VTYPE>(this, item, last, null);
		
		if(count >= limit && limit != -1) {
			if(limitMethod) {
				if(deleteLast) {
					removeLast();
				} else {
					removeFirst();
				}
			} else {
				System.err.println("PileList full, remove the limit or remove items from the list!");
				return;
			}
		}
		
		if(first == null) {
			first = buf;
		} else {
			last.next = buf;
		}
		last = buf;
		count++;
	}
	
	/** Removes the first item on the list and then returns it */
	public VTYPE popFirst() {
		if(first == null) {
			return null;
		}
		VTYPE ret = first.item;
		removeFirst();
		return ret;
	}
	
	/** Removes the last item on the list and then returns it */
	public VTYPE popLast() {
		if(last == null) {
			return null;
		}
		VTYPE ret = last.item;
		removeLast();
		return ret;
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
		count--;
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
		count--;
	}
	
	public Iterator<PileListItem<VTYPE>> getPileListItemIterator(boolean reversed) {
		if(reversed) {
			revIter.setCurr(last);
			revIter.firstRun = true;
			return revIter;
		}
		iter.setCurr(first);
		iter.firstRun = true;
		return iter;
	}
	
	public Iterator<VTYPE> getItemIterator(boolean reversed) {
		if(reversed) {
			revIiter.setCurr(last);
			revIiter.firstRun = true;
			return revIiter;
		}
		iiter.setCurr(first);
		iiter.firstRun = true;
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
			owner.count--;
		}
		
	}
	
	private static class PLIIter<VTYPE> implements Iterator<PileListItem<VTYPE>> {

		private PileListItem<VTYPE> curr = null;
		private boolean firstRun = true;
		
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
				if(!firstRun) {
					curr = curr.next;
				} else {
					firstRun = false;
				}
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
		private boolean firstRun = true;
		
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
				if(!firstRun) {
					curr = curr.prev;
				} else {
					firstRun = false;
				}
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
		private boolean firstRun = true;
		
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
				if(!firstRun) {
					curr = curr.next;
				} else {
					firstRun = false;
				}
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
		private boolean firstRun = true;
		
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
				if(!firstRun) {
					curr = curr.prev;
				} else {
					firstRun = false;
				}
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
