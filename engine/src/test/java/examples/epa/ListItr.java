package examples.epa;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * An optimized version of AbstractList.ListItr
 */
public class ListItr implements ListIterator<Object> {

	/**
	 * 
	 */
	private MyArrayList arrayList;
	private int cursor; // index of next element to return
	private int lastRet; // index of last element returned; -1 if no such
	private int expectedModCount;

	public ListItr(MyArrayList arrayList, int index) {
		try {
			ctorListItr0(arrayList, index);
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	private void ctorListItr0(MyArrayList arrayList, int index) {
		if (index < 0 || index > arrayList.size())
			throw new IndexOutOfBoundsException("Index: " + index);

		this.arrayList = new MyArrayList(arrayList);
		this.lastRet = -1;
		this.expectedModCount = this.arrayList.getModCount();
		this.cursor = index;
	}

	public boolean hasPrevious() {
		try {
			return hasPrevious0();
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	private boolean hasPrevious0() {
		final boolean b = cursor != 0;
		return b;
	}

	public int nextIndex() {
		try {
			return nextIndex0();
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	private int nextIndex0() {
		return cursor;
	}

	public int previousIndex() {
		try {
			return previousIndex0();
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	private int previousIndex0() {
		final int i = cursor - 1;
		return i;
	}

	public boolean hasNext() {
		try {
			return hasNext0();
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	private boolean hasNext0() {
		boolean superHasNext = super_hasNext();
		return superHasNext;
	}

	public Object next() {
		try {
			return next0();
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	private Object next0() {
		final Object superNext = super_next();
		return superNext;
	}

	public void remove() {
		try {
			remove0();
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	public Object previous() {
		try {
			return previous0();
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	private Object previous0() {
		checkForComodification();
		int i = cursor - 1;
		if (i < 0)
			throw new NoSuchElementException();
		Object[] elementData = arrayList.getElementData();
		if (i >= elementData.length)
			throw new ConcurrentModificationException();
		cursor = i;

		Object object = (Object) elementData[lastRet = i];
		return object;
	}

	public void set(Object e) {
		try {
			set0(e);
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	private void set0(Object e) {
		if (lastRet < 0)
			throw new IllegalStateException();
		checkForComodification();

		try {
			arrayList.set(lastRet, e);
		} catch (IndexOutOfBoundsException ex) {
			throw new ConcurrentModificationException();
		}
	}

	public void add(Object e) {
		try {
			add0(e);
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	private void add0(Object e) {
		checkForComodification();

		try {
			int i = cursor;
			arrayList.add(i, e);
			cursor = i + 1;
			lastRet = -1;
			expectedModCount = this.arrayList.getModCount();
		} catch (IndexOutOfBoundsException ex) {
			throw new ConcurrentModificationException();
		}
	}

	private boolean super_hasNext() {
		final boolean b = cursor != this.arrayList.size();
		return b;
	}

	private Object super_next() {
		checkForComodification();
		int i = cursor;
		if (i >= this.arrayList.size())
			throw new NoSuchElementException();
		Object[] elementData = arrayList.getElementData();
		if (i >= elementData.length)
			throw new ConcurrentModificationException();
		cursor = i + 1;
		final Object o = (Object) elementData[lastRet = i];
		return o;
	}

	private void remove0() {
		if (lastRet < 0)
			throw new IllegalStateException();
		checkForComodification();

		try {
			arrayList.remove(lastRet);
			cursor = lastRet;
			lastRet = -1;
			expectedModCount = this.arrayList.getModCount();
		} catch (IndexOutOfBoundsException ex) {
			throw new ConcurrentModificationException();
		}
	}

	private final void checkForComodification() {
		if (this.arrayList.getModCount() != expectedModCount)
			throw new ConcurrentModificationException();
	}

	/*-------------------------------------------------------
	 * EPA Precondition Methods
	 */

	public boolean isHasPreviousEnabled() {
		return true;
	}

	public boolean isNextIndexEnabled() {
		return true;
	}

	public boolean isPreviousIndexEnabled() {
		return true;
	}

	public boolean isHasNextEnabled() {
		return true;
	}

	public boolean isNextEnabled() {
		return cursor < this.arrayList.size();
	}

	public boolean isRemoveEnabled() {
		return lastRet >= 0;
	}

	public boolean isPreviousEnabled() {
		return cursor - 1 >= 0;
	}

	public boolean isSetEnabled() {
		return lastRet >= 0;
	}

	public boolean isAddEnabled() { return true; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListItr)) return false;

        ListItr other = (ListItr) o;

        if (cursor != other.cursor) return false;
        return arrayList.equals(other.arrayList);
    }

}
