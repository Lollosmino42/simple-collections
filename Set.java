@SuppressWarnings("unchecked")
public class Set<T> extends Collection<T> {

	public Set() { super(); }
	public Set( int capacity) { super(capacity); }
	public Set( T[] items) { super(items); }

	public T[] items() {
		var copy = new Object[size];
		for (int idx = 0; idx < size; ++idx)
			copy[idx] = items[idx];
		return (T[]) copy;
	}


	public void add( T value) {
		if (contains(value))
			return;
		super.add(value);
	}
}
