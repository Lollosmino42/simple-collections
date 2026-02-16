import java.util.Comparator;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class SortedSet<T extends Comparable<T>> extends Set<T> implements Ordered<T> {
	private Comparator<T> comp = (T a, T b) -> a.compareTo(b);

	public SortedSet() { super(); }
	public SortedSet( int capacity) { super(capacity); }
	public SortedSet( T[] items) { super(items); }

	public SortedSet<T> sorted_with( Comparator<T> C) {
		comp = C;
		return this;
	}

	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int cursor = -1;
			public boolean hasNext() { return ++cursor < size; }
			public T next() { return (T) items[cursor]; }
		};
	}

	public void update( int __) {}

	@Override
	public void add( T value) {
		if (contains(value))
			return;

		if (++size == items.length)
			expand_by(1);
		
		int idx = 0;
		while ( items[idx] != null && comp.compare((T)items[idx], value) <= 0)
			++idx;

		if (items[idx] != null)
			for (int cursor = size - 1; cursor > idx; --cursor)
				items[cursor] = items[cursor - 1];

		items[idx] = value;
	}

	@Override
	public T remove( T value) {
		int cursor;
		for (cursor = 0; cursor < size; ++cursor)
			if ( items[cursor] == value)
				break;
		if (cursor == size)
			return null;
		
		var removed = items[cursor];
		while ( cursor < --size)
			items[cursor] = items[++cursor];
		items[cursor] = null;
		return (T) removed;
	}

	public T remove_index( int index) {
		if (index >= size)
			throw new IndexOutOfBoundsException(index);

		var removed = items[index];
		--size;
		for (int cursor = index; cursor < size; cursor++) {
			items[cursor] = items[cursor + 1];
		}
		items[size] = null;
		return (T) removed;
	}


	public void sort() {
		int idx = 1;
		Object temp;
		while (idx < size) {
			if ( comp.compare((T)items[idx], (T)items[idx-1]) >= 0) {
				++idx;
				continue;
			}
			int cursor = idx;
			do {
				temp = items[cursor-1];
				items[cursor-1] = items[cursor];
				items[cursor--] = temp;
			} while (cursor > 0 && comp.compare((T)items[cursor], (T)items[cursor-1]) < 0);
			++idx;
		}
	}
}
