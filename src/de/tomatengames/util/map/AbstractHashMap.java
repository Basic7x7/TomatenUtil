package de.tomatengames.util.map;

import static de.tomatengames.util.RequirementUtil.requireNotNull;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A {@link HashMap}-like data structure that maps {@code K} keys to object values.
 	* Equality of keys is checked by using the abstract {@code keyEquals(key1, key2);} method.
 * <p>
 * This map does <b>not</b> allow {@code null} values.
 * This implementation does <b>not</b> allow concurrent modifications.
 * 
 * @param <V> The type of the values.
 * 
 * @author Basic7x7
 * @version
 * 2024-01-06
 * @since 1.5
 */
// !!! TextScript generated !!!
public abstract class AbstractHashMap<K, V> implements Iterable<AbstractEntry<K, V>> {
	private static final double ENLARGE_LOAD_FACTOR = 0.75;
	private static final int MIN_TABLE_SIZE = 16;
	private static final int MAX_TABLE_SIZE = 1 << 30;
	
	private int mask;
	private long enlargeThreshold;
	private Node[] table;
	private long size;
	private int modcount;
	
	/**
	 * Creates a new and empty {@link AbstractHashMap}.
	 */
	public AbstractHashMap() {
		this.size = 0L;
		this.mask = 0;
		this.enlargeThreshold = 0;
		this.modcount = 0;
		this.table = null;
	}
	
	/**
	 * Creates a new {@link AbstractHashMap} that contains all the mappings of the specified map.
	 * @param map The mappings that should be cloned.
	 */
	public AbstractHashMap(AbstractHashMap<K, V> map) {
		this();
		this.putAll(map);
	}
	
	/**
	 * Returns the number of entries in this map.
	 * @return The number of entries.
	 */
	public long size() {
		return size;
	}
	
	/**
	 * Returns if this map does not contain any entry.
	 * @return If this map has no entries.
	 */
	public boolean isEmpty() {
		return this.size <= 0;
	}
	
	/**
	 * Associates the specified key with the specified value.
	 * If the key is already present in this map, the previous value is replaced.
	 * @param key The key of the new mapping.
	 * @param value The value of the new mapping. Must not {@code null}.
	 * @return The value that was previously mapped to the key.
	 * If the key was not present in this map, {@code null} is returned. 
	 * @throws IllegalArgumentException If the value is {@code null}.
	 */
	public V put(K key, V value) {
		requireNotNull(value, "The value ...");
		this.modcount++;
		Node node = this.findOrCreate(key);
		V prev = node.value;
		node.value = value; // Might replace the previous value
		return prev;
	}
	
	/**
	 * Associates the specified key with the specified value.
	 * If the key is already present in this map, nothing happens.
	 * @param key The key of the new mapping.
	 * @param value The value of the new mapping. Must not {@code null}.
	 * @return The value that was previously mapped to the key.
	 * If {@code null}, the new value has been put into the map.
	 * Otherwise, the previous value is still present.
	 * @throws IllegalArgumentException If the value is {@code null}.
	 */
	public V putIfAbsent(K key, V value) {
		requireNotNull(value, "The value ...");
		Node node = this.findOrCreate(key);
		
		// If the Node did not exist before (value == null), then set the value.
		V prev = node.value;
		if (prev == null) {
			this.modcount++;
			node.value = value;
			return null;
		}
		// If the Node was already present, do nothing and return its value.
		return prev;
	}
	
	/**
	 * Returns the value that is mapped to the specified key.
	 * @param key The key whose value should be returned.
	 * @param key The key that should be checked.
	 * @return The value that the specified key is mapped to.
	 * If this map does not contain the key, {@code null} is returned.
	 */
	public V get(K key) {
		Node node = findNode(key, this.table, this.mask);
		return node != null ? node.value : null;
	}
	
	/**
	 * Returns if the specified key is present in this map.
	 * This method is semantically equivalent to
	 * <pre>get(key) != null</pre>
	 * @param key The key that should be checked.
	 * @return If the specified key is present in this map.
	 */
	public boolean containsKey(K key) {
		return findNode(key, this.table, this.mask) != null;
	}
	
	/**
	 * Removes the entry with the specified key from this map.
	 * If the map does not contain the key, nothing happens.
	 * @param key The key of the entry that should be removed.
	 * @return The value of the entry that was removed.
	 * If no entry was removed, {@code null} is returned.
	 */
	public V remove(K key) {
		Node[] table = this.table;
		if (table == null) {
			return null; // No elements in the map
		}
		
		int index = indexOf(key, this.mask);
		
		// Search for the key.
		Node node = table[index];
		Node prev = null;
		while (node != null) {
			if (keyEquals(node.key, key)) {
				// Unlink the found node.
				this.modcount++;
				if (prev != null) {
					prev.next = node.next;
				}
				else {
					table[index] = node.next;
				}
				this.size--;
				return node.value;
			}
			prev = node;
			node = node.next;
		}
		
		// If the key was not found.
		return null;
	}
	
	/**
	 * Removes all entries from this map.
	 */
	public void clear() {
		this.modcount++;
		this.size = 0L;
		
		Node[] table = this.table;
		if (table == null) {
			return; // Table is already empty
		}
		
		// Unlink all entries.
		int n = table.length;
		for (int i = 0; i < n; i++) {
			table[i] = null;
		}
	}
	
	@Override
	public void forEach(Consumer<? super AbstractEntry<K, V>> action) {
		Node[] table = this.table;
		if (table == null) {
			return; // The map is empty
		}
		
		// Iterate all nodes.
		int n = table.length;
		for (int i = 0; i < n; i++) {
			Node node = table[i];
			while (node != null) {
				action.accept(node);
				node = node.next;
			}
		}
	}
	
	/**
	 * Puts all mappings of the specified map into this map.
	 * Keys that are present in both maps are replaced in this map.
	 * If the specified map is {@code null} or this map, nothing happens.
	 * @param otherMap The map whose mappings should be put into this map. May be {@code null}.
	 */
	public void putAll(AbstractHashMap<K, V> otherMap) {
		// If the map is null, it is considered empty.
		// If the other map is this map, all entries are already present. Prevents concurrent modification.
		if (otherMap == null || otherMap == this) {
			return;
		}
		
		otherMap.forEach(entry -> this.put(entry.getKey(), entry.getValue()));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		
		// Checks the sizes.
		AbstractHashMap<?, ?> other = (AbstractHashMap<?, ?>) obj;
		if (this.size != other.size) {
			return false;
		}
		
		// Checks that the other map is a subset of this map.
		for (AbstractEntry<?, ?> entry : other) {
			// get() returns null if the key has the wrong type ==> The map are not equal
			@SuppressWarnings("unchecked")
			V thisValue = this.get((K) entry.getKey());
			if (thisValue == null) {
				return false; // This does not contain the current entry
			}
			if (!thisValue.equals(entry.getValue())) {
				return false; // The values for the current key differ.
			}
		}
		
		// If this map contains all entries of the other map and the sizes are the same,
		// the maps are equal.
		return true;
	}
	
	@Override
	public int hashCode() {
		// The order of the loop is undefined, but '+' is commutative.
		int result = 0;
		for (AbstractEntry<K, V> entry : this) {
			result += entry.hashCode();
		}
		return result;
	}
	
		/**
		 * Calculate the hash code of the specified key.
		 * @param key The key whose hash code should be calculated.
		 * @return The hash code.
		 */
		public abstract int keyHash(K key);
		
		/**
		 * Returns if the specified keys should be considered equal.
		 */
		public abstract boolean keyEquals(K key1, K key2);
		
	
	private final int indexOf(K key, int mask) {
		return keyHash(key) & mask;
	}
	
	private final Node findNode(K key, Node[] table, int mask) {
		if (table == null) {
			return null;
		}
		int index = indexOf(key, mask);
		Node node = table[index];
		while (node != null) {
			if (keyEquals(node.key, key)) {
				return node;
			}
			node = node.next;
		}
		return null;
	}
	
	private final Node findOrCreate(K key) {
		// If the key is already present in the map, it is returned.
		Node node = findNode(key, table, this.mask);
		if (node != null) {
			return node;
		}
		
		// Initialize the table if needed.
		Node[] table = this.table;
		if (table == null) {
			table = this.resize(MIN_TABLE_SIZE);
		}
		
		// If the key is not present, a new Node is inserted.
		// The size increases by 1.
		Node newNode = new Node(key);
		insertNode(newNode, table, this.mask);
		adjustTableSize(++this.size);
		return newNode;
	}
	
	/**
	 * Adjusts the table size if necessary.
	 * <p>
	 * It is assumed that the table has been initialized (not {@code null}).
	 * @param newSize The amount of elements in the map.
	 */
	private void adjustTableSize(long newSize) {
		if (newSize >= this.enlargeThreshold) {
			int tableSize = this.table.length;
			if (tableSize < MAX_TABLE_SIZE) {
				this.resize(tableSize << 1); // Double the table size
				return;
			}
			return; // Do nothing, if the table has reached its maximum size
		}
	}
	
	private Node[] resize(int newTableSize) {
		int newMask = newTableSize-1; // = 2^n-1 = 0b0..01..1
		this.mask = newMask;
		this.enlargeThreshold = (long) (newTableSize * ENLARGE_LOAD_FACTOR);
		
		Node[] oldTable = this.table;
		@SuppressWarnings("unchecked")
		Node[] newTable = new AbstractHashMap.Node[newTableSize];
		
		// Moves the nodes from the old table to the new one.
		if (oldTable != null) {
			for (Node rootNode : oldTable) {
				Node node = rootNode;
				while (node != null) {
					Node next = node.next;
					insertNode(node, newTable, newMask);
					node = next;
				}
			}
		}
		
		return this.table = newTable;
	}
	
	private final void insertNode(Node node, Node[] table, int mask) {
		int index = indexOf(node.key, mask);
		node.next = table[index];
		table[index] = node;
	}
	
	
	private class Node implements AbstractEntry<K, V> {
		private final K key;
		private V value;
		private Node next;
		
		private Node(K key) {
			this.key = key;
			this.value = null;
			this.next = null;
		}
		
		@Override
		public K getKey() {
			return this.key;
		}
		
		
		@Override
		public V getValue() {
			return this.value;
		}
		
		@Override
		public void setValue(V value) {
			requireNotNull(value, "The value ...");
			this.value = value;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj == null || obj.getClass() != this.getClass()) {
				return false;
			}
			
				@SuppressWarnings("unchecked")
			Node other = (Node) obj;
			return keyEquals(this.key, other.key) && Objects.equals(this.value, other.value);
		}
		
		@Override
		public int hashCode() {
			return Objects.hashCode(this.value) + keyHash(key);
		}
	}
	
	
	@Override
	public Iterator<AbstractEntry<K, V>> iterator() {
		return new NodeIterator();
	}
	
	private final class NodeIterator implements Iterator<AbstractEntry<K, V>> {
		private Node prevNode, currentNode, nextNode;
		private int initModCount;
		
		private NodeIterator() {
			this.initModCount = modcount;
			this.prevNode = null;
			this.currentNode = null;
			this.nextNode = findNextNode(null);
		}
		
		@Override
		public boolean hasNext() {
			return this.nextNode != null;
		}
		
		@Override
		public AbstractEntry<K, V> next() {
			Node next = this.nextNode;
			this.prevNode = this.currentNode;
			this.currentNode = next;
			this.nextNode = findNextNode(next);
			return next;
		}
		
		@Override
		public void remove() {
			Node current = this.currentNode;
			if (current == null) {
				throw new IllegalStateException("No element to remove!");
			}
			
			// Unlink the current node.
			// If the index of the prev node differs from the current node,
			// the prev node is in another bucket.
			int currentIndex = indexOf(current.key, mask);
			Node prev = this.prevNode;
			if (prev != null && indexOf(prev.key, mask) == currentIndex) {
				prev.next = current.next;
			}
			else {
				table[currentIndex] = current.next;
			}
			size--;
			
			// Mark the current node as removed.
			this.currentNode = null;
		}
		
		private Node findNextNode(Node startNode) {
			if (this.initModCount != modcount) {
				throw new ConcurrentModificationException();
			}
			
			// If the current node has a next node, return it.
			if (startNode != null) {
				Node next = startNode.next;
				if (next != null) {
					return next;
				}
			}
			
			// If the current node has no next node, the next buckets have to be checked.
			Node[] t = table;
			if (t == null) {
				return null; // The map is empty
			}
			
			// Start the search in the bucket after the current node.
			// If no current node exists, start at the beginning of the table.
			int n = t.length;
			int startIndex = startNode != null ? indexOf(startNode.key, mask)+1 : 0;
			for (int i = startIndex; i < n; i++) {
				Node el = t[i];
				if (el != null) {
					return el;
				}
			}
			// If no next node was found.
			return null;
		}
	}
	
}
