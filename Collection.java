import java.io.Serializable;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class Collection<T> implements Sequence<T>, Serializable {
	protected final int DEFAULT_CAPACITY = 10;

	protected int size = 0;
	protected transient Object[] items;
	protected int available;

	public Collection( int capacity) {
		items = new Object[capacity];
		available = 0;
	}
	public Collection() {
		items = new Object[DEFAULT_CAPACITY];
		available = 0;
	}
	public Collection( T[] items) {
		this.items = items.clone();
		available = items.length;
		size = available;
	}

	public boolean equals( Collection<T> C) {
		if (C.size != C.size) return false;
		for (int idx = 0; idx < size; ++idx) {
			if ( !items[idx].equals(C.items[idx]))
				return false;
		}
		return true;
	}

	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int cursor = -1;
			public boolean hasNext() {
				while (++cursor < items.length && items[cursor] == null);
				if (cursor == items.length)
					return false;
				return true;
			}
			public T next() {
				return (T) items[cursor];
			}
		};
	}

	public int size() { return size; }
	public int capacity() { return items.length; }

	public T[] items() {
		var items = new Object[this.size];
		int cursor = 0;
		for (var item : this.items)
			if (item != null)
				items[cursor++] = item;
		return (T[]) items;
	}

	public void clear() {
		for (int idx = 0; idx < size; ++idx)
			items[idx] = null;
		size = 0;
		available = 0;
	}

	public void trim() {
		var trimmed = new Object[size];
		for (int idx = 0; idx < size; idx++)
			trimmed[idx] = items[idx];
		items = trimmed;
	}

	public void expand_by( int increment) {
		var expanded = new Object[items.length + increment];
		for (int idx = 0; idx < size; idx++)
			expanded[idx] = items[idx];
		items = expanded;
	}
	public void expand( int new_capacity) {
		var expanded = new Object[new_capacity];
		for (int idx = 0; idx < size; idx++)
			expanded[idx] = items[idx];
		items = expanded;
	}

	protected void update( int last_change) {
		if (items[last_change] == null && available > last_change) {
			available = last_change;
			return;
		}
		if (items[available] != null) {
			while (available < items.length && items[available] != null)
				++available;
		}
	}

	public boolean contains( T value) {
		for ( var item : items)
			if (item != null && item.equals(value))
				return true;
		return false;
	}

	public void reverse() {
		var buffer = items.clone();
		for (int idx = 0; idx < size; ++idx) {
			items[idx] = buffer[size - idx - 1];
		}
		available = 0;
		update(0);
	}

	public void add( T value) {
		if ( size == items.length)
			expand_by(1);
		items[available] = value;
		update(available);
		++size;
	}

	public T remove( T value) { 
		int idx;
		for (idx = 0; idx < size; ++idx) {
			if (items[idx].equals(value))
				break;
		}
		if (idx == size)
			return null;
		T removed = (T) items[idx];
		items[idx] = null;
		update(idx);
		--size;
		return removed;
	}
}
