package de.tomatengames.util.map;

import static de.tomatengames.util.RequirementUtil.requireNotNull;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A {@link HashMap}-like data structure that maps keys to values.
 * Equality of keys is checked by using the {@link Object#equals(Object)} method.
 * <p>
 * This map manages a doubly-linked list to store the entries in the order of insertion
 * and a hash table to speed up lookups.
 * The {@link Iterator} of this map iterates the entries in their original order of insertion.
 * <p>
 * This map does <b>not</b> allow {@code null} keys and values.
 * This implementation does <b>not</b> allow concurrent modifications.
 * 
 * @param <K> The type of the keys.
 * @param <V> The type of the values.
 * 
 * @author Basic7x7
 * @version 2024-11-17 created
 * @since 1.7
 */
public final class OrderedHashMap<K, V> implements Iterable<Entry<K, V>> {
	private static final double ENLARGE_LOAD_FACTOR = 0.75;
	private static final int MIN_TABLE_SIZE = 16;
	private static final int MAX_TABLE_SIZE = 1 << 30;
	
	private int mask;
	private long enlargeThreshold;
	private long size;
	private int modcount;
	
	private Node[] table;
	private Node listFirst, listLast;
	
	/**
	 * Creates a new and empty {@link OrderedHashMap}.
	 */
	public OrderedHashMap() {
		this.size = 0L;
		this.mask = 0;
		this.enlargeThreshold = 0;
		this.modcount = 0;
		this.table = null;
		this.listFirst = this.listLast = null;
	}
	
	/**
	 * Creates a new {@link OrderedHashMap} that contains all the mappings of the specified map.
	 * @param map The mappings that should be cloned. May be {@code null}.
	 */
	public OrderedHashMap(OrderedHashMap<K, V> map) {
		this();
		this.putAll(map);
	}
	
	
	/**
	 * Returns the number of entries in this map.
	 * @return The number of entries.
	 */
	public long size() {
		return this.size;
	}
	
	/**
	 * Returns if this map is empty.
	 * @return If this map has no entries.
	 */
	public boolean isEmpty() {
		return this.listFirst == null;
	}
	
	
	/**
	 * Associates the specified key with the specified value.
	 * If the key is not present in this map, a new entry is added to the end of the list.
	 * If the key is already present in this map, the value of the previous entry is replaced.
	 * @param key The key of the new mapping. Not {@code null}.
	 * @param value The value of the new mapping. Not {@code null}.
	 * @return The value that was previously mapped to the key.
	 * If the key was not present in this map, {@code null} is returned. 
	 * @throws IllegalArgumentException If the key or the value is {@code null}.
	 */
	public V put(K key, V value) {
		requireNotNull(key, "The key ...");
		requireNotNull(value, "The value ...");
		this.modcount++;
		Node node = this.findOrCreate(key);
		V prev = node.value;
		node.value = value; // Might replace the previous value
		return prev;
	}
	
	/**
	 * Associates the specified key with the specified value
	 * and adds the entry to the end of the list.
	 * If the key is already present in this map, nothing happens.
	 * @param key The key of the new mapping. Not {@code null}.
	 * @param value The value of the new mapping. Not {@code null}.
	 * @return The value that was previously mapped to the key.
	 * If {@code null}, the new value has been put into the map.
	 * Otherwise, the previous value is still present.
	 * @throws IllegalArgumentException If the key or the value is {@code null}.
	 */
	public V putIfAbsent(K key, V value) {
		requireNotNull(key, "The key ...");
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
	 * Inserts all mappings of the specified map into this map.
	 * Keys that are present in both maps are replaced in this map.
	 * If the specified map is {@code null} or this map, nothing happens.
	 * @param otherMap The map whose mappings should be put into this map. May be {@code null}.
	 */
	public void putAll(OrderedHashMap<K, V> otherMap) {
		// If the other map is null it is considered empty.
		// If otherMap == this, all entries are already present.
		if (otherMap == null || otherMap == this) {
			return;
		}
		for (Node node = otherMap.listFirst; node != null; node = node.listNext) {
			Node newNode = this.findOrCreate(node.key);
			newNode.value = node.value;
		}
		this.modcount++;
	}
	
	/**
	 * Removes the entry with the specified key from this map.
	 * If the map does not contain the key, nothing happens.
	 * @param key The key of the entry that should be removed. May be {@code null}.
	 * @return The value of the entry that was removed.
	 * If no entry was removed, {@code null} is returned.
	 */
	public V remove(K key) {
		if (key == null) {
			return null;
		}
		Node node = this.removeNodeFromTable(key, this.table, this.mask);
		if (node == null) {
			return null;
		}
		this.removeNodeFromList(node);
		this.modcount++;
		return node.value;
	}
	
	/**
	 * Removes all entries from this map.
	 */
	public void clear() {
		this.modcount++;
		this.size = 0L;
		this.listFirst = this.listLast = null;
		
		Node[] table = this.table;
		if (table != null) {
			Arrays.fill(table, null);
		}
	}
	
	/**
	 * Returns the value that is mapped to the specified key.
	 * @param key The key whose value should be returned. May be {@code null}.
	 * @return The value that the specified key is mapped to.
	 * If this map does not contain the key, {@code null} is returned.
	 */
	public V get(K key) {
		if (key == null) {
			return null;
		}
		Node node = findNode(key, this.table, this.mask);
		return node != null ? node.value : null;
	}
	
	/**
	 * Returns if the specified key is present in this map.
	 * This method is semantically equivalent to
	 * <pre>get(key) != null</pre>
	 * @param key The key that should be checked. May be {@code null}.
	 * @return If the specified key is present in this map.
	 */
	public boolean containsKey(K key) {
		if (key == null) {
			return false;
		}
		return findNode(key, this.table, this.mask) != null;
	}
	
	@Override
	public void forEach(Consumer<? super Entry<K, V>> action) {
		requireNotNull(action, "The action ...");
		for (Node node = this.listFirst; node != null; node = node.listNext) {
			action.accept(node);
		}
	}
	
	/**
	 * Returns an {@link Iterator} that iterates over the entries in this map
	 * in the order of their first insertion.
	 */
	@Override
	public Iterator<Entry<K, V>> iterator() {
		return this.new NodeIterator();
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
		OrderedHashMap<?, ?> other = (OrderedHashMap<?, ?>) obj;
		if (this.size != other.size) {
			return false;
		}
		
		// Iterates over both list structures and checks that all elements match (order is relevant).
		Node thisNode = this.listFirst;
		OrderedHashMap<?, ?>.Node otherNode = other.listFirst;
		while (!(thisNode == null && otherNode == null)) {
			if (!Objects.equals(thisNode, otherNode)) {
				return false;
			}
			thisNode = thisNode.listNext;
			otherNode = otherNode.listNext;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		// Iterates over all elements in order.
		int result = 0;
		int factor = 1;
		for (Node node = this.listFirst; node != null; node = node.listNext) {
			result += node.hashCode() * factor;
			factor *= 31;
		}
		return result;
	}
	
	
	// ---- The following helper methods are similar to the maps in the util.map package ----
	
	private static final int indexOf(Object key, int mask) {
		return key.hashCode() & mask;
	}
	
	private final Node findNode(K key, Node[] table, int mask) {
		if (table == null) {
			return null;
		}
		int index = indexOf(key, mask);
		for (Node node = table[index]; node != null; node = node.tableNext) {
			if (node.key.equals(key)) {
				return node;
			}
		}
		return null;
	}
	
	private final Node findOrCreate(K key) {
		// If the key is already present in the map, it is returned.
		Node node = findNode(key, this.table, this.mask);
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
		insertNodeIntoTable(newNode, table, this.mask);
		adjustTableSize(++this.size);
		
		// Inserts the new node into the list.
		Node prevLast = this.listLast;
		if (prevLast == null) {
			this.listFirst = this.listLast = newNode;
		}
		else {
			newNode.listPrev = prevLast;
			prevLast.listNext = newNode;
			this.listLast = newNode;
		}
		
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
		Node[] newTable = new OrderedHashMap.Node[newTableSize];
		
		// Moves the nodes from the old table to the new one.
		if (oldTable != null) {
			for (Node node = this.listFirst; node != null; node = node.listNext) {
				insertNodeIntoTable(node, newTable, newMask);
			}
		}
		
		return this.table = newTable;
	}
	
	private final void insertNodeIntoTable(Node node, Node[] table, int mask) {
		int index = indexOf(node.key, mask);
		node.tableNext = table[index];
		table[index] = node;
	}
	
	private final Node removeNodeFromTable(K key, Node[] table, int mask) {
		if (table == null) {
			return null;
		}
		
		// Search for the key.
		int tableIndex = indexOf(key, this.mask);
		Node node = table[tableIndex];
		Node prev = null;
		while (node != null) {
			if (node.key.equals(key)) {
				// Unlink the node from the table.
				if (prev != null) {
					prev.tableNext = node.tableNext;
				}
				else {
					table[tableIndex] = node.tableNext;
				}
				node.tableNext = null;
				
				this.size--;
				return node;
			}
			prev = node;
			node = node.tableNext;
		}
		
		// If the key was not found.
		return null;
	}
	
	private final void removeNodeFromList(Node node) {
		Node listPrev = node.listPrev;
		Node listNext = node.listNext;
		if (listPrev != null) {
			listPrev.listNext = listNext;
		}
		if (listNext != null) {
			listNext.listPrev = listPrev;
		}
		if (this.listFirst == node) {
			this.listFirst = listNext;
		}
		if (this.listLast == node) {
			this.listLast = listPrev;
		}
		node.listNext = node.listPrev = null;
	}
	
	
	private final class Node implements Entry<K, V> {
		private final K key;
		private V value;
		private Node tableNext;
		private Node listNext, listPrev;
		
		public Node(K key) {
			requireNotNull(key, "The key ...");
			this.key = key;
			this.value = null;
			this.tableNext = this.listNext = this.listPrev = null;
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
		public V setValue(V value) {
			V prevValue = this.value;
			this.value = value;
			return prevValue;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof Entry)) {
				return false;
			}
			
			Entry<?, ?> other = (Entry<?, ?>) obj;
			return Objects.equals(this.key, other.getKey()) && Objects.equals(this.value, other.getValue());
		}
		
		@Override
		public int hashCode() {
			return (this.key != null ? this.key.hashCode() : 0) ^ (this.value != null ? this.value.hashCode() : 0);
		}
	}
	
	
	private final class NodeIterator implements Iterator<Entry<K, V>> {
		private int expectedModcount;
		private Node cursor, next;
		
		public NodeIterator() {
			this.expectedModcount = modcount;
			this.cursor = null;
			this.next = listFirst;
		}
		
		@Override
		public boolean hasNext() {
			return this.next != null;
		}
		
		@Override
		public Entry<K, V> next() {
			if (this.expectedModcount != modcount) {
				throw new ConcurrentModificationException();
			}
			Node next = this.next;
			if (next == null) {
				throw new NoSuchElementException();
			}
			this.cursor = next;
			this.next = next.listNext;
			return next;
		}
		
		@Override
		public void remove() {
			if (this.expectedModcount != modcount) {
				throw new ConcurrentModificationException();
			}
			Node cursor = this.cursor;
			if (cursor == null) {
				throw new IllegalStateException("No current element to remove");
			}
			
			this.expectedModcount = ++modcount;
			this.cursor = null;
			
			Node removed = removeNodeFromTable(cursor.key, table, mask);
			if (removed != null) {
				removeNodeFromList(removed);
			}
			if (removed != cursor) { // Should not happen if the map is not corrupted
				throw new IllegalStateException("Iterator removed entry " + removed + " instead of the cursor " + cursor +
						", because both have the same key");
			}
		}
	}
}
