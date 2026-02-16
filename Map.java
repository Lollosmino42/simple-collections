import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class Map<K,V> extends Set<Entry<K,V>> {

	private Function<K,V> map = x -> null;

	public Map() { super(); }
	public Map( int capacity) { super(capacity); }
	public Map( Entry<K,V>[] entries) { super(entries); }

	public Map<K,V> with_map( Function<K,V> map) { 
		if (Objects.nonNull(map)) 
			this.map = map;
		return this;
	}


	public Set<K> keys() {
		var keys = new Set<K>(size);
		for (var entry : items) {
			if (Objects.isNull(entry))
				continue;
			 keys.add( ((Entry<K,V>) entry).key() );
		}
		return keys;
	}

	public Collection<V> values() {
		var values = new Set<V>(size);
		for (var entry : items) {
			if (Objects.isNull(entry))
				continue;
			values.add( ((Entry<K,V>) entry).value() );
		}
		return values;
	}


	public void add( K k, V v) { 
		super.add( new Entry<K,V>(k, (Objects.nonNull(v)) ? v : map.apply(k)) ); 
	}

	public V remove_key( K k) {
		int cursor;
		for (cursor = 0; cursor < items.length; ++cursor) {
			if (items[cursor] == null)
				continue;
			if ( k.equals( ((Entry<K,V>)items[cursor]).key()))
				break;
		}

		if (cursor == items.length)
			return null;

		V removed = ((Entry<K,V>)items[cursor]).value();
		items[cursor] = null;
		update(cursor);
		return removed;
	}

	public V get( K k) {
		for (int cursor = 0; cursor < items.length; ++cursor) {
			if (Objects.isNull(items[cursor]))
				continue;
			var entry = (Entry<K,V>) items[cursor];
			if (entry.key().equals(k))
				return entry.value();
		}
		return map.apply(k);
	}

	public V apply(K k) { return map.apply(k); }

	public V[] apply( K[] k) {
		var result = new Object[k.length];
		for (int idx = 0; idx < k.length; ++idx)
			result[idx] = map.apply(k[idx]);
		return (V[]) result;
	}

	public boolean contains(K k, V v) {
		return super.contains(new Entry<K,V>(k,v));
	}
}
