
public final record Entry<K,V> (K key, V value) {
	public boolean equals( Entry<K,V> other) {
		return key.equals(other.key) && value.equals(other.value);
	}
}
